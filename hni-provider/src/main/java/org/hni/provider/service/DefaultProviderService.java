package org.hni.provider.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import org.hni.common.Constants;
import org.hni.common.service.AbstractService;
import org.hni.order.om.Order;
import org.hni.organization.om.UserOrganizationRole;
import org.hni.organization.service.OrganizationUserService;
import org.hni.provider.dao.ProviderDAO;
import org.hni.provider.om.Provider;
import org.hni.provider.om.ProviderLocation;
import org.hni.type.HNIRoles;
import org.hni.user.om.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class DefaultProviderService extends AbstractService<Provider> implements ProviderService {
	private static final Logger _LOGGER = LoggerFactory.getLogger(DefaultProviderService.class);
	
	private ProviderDAO providerDao;
	
	@Inject
	private OrganizationUserService organizationUserService;
	
	@Inject
	private ProviderLocationService providerLocationService;
	
	@Inject
	public DefaultProviderService(ProviderDAO providerDao) {
		super(providerDao);
		this.providerDao = providerDao;
	}

	@Override
	public Provider getProviderDetails(Long providerId, User loggedInUser) {
		_LOGGER.debug("Starting process for retrieve provider");
		Provider toProvider = get(providerId);
		if (isAllowed(loggedInUser, toProvider)) {
			return toProvider;
		}
		return null;
	}
	
	private boolean isAllowed(User loggedInUser, Provider toProvider){
		boolean isAllowed = false;
		if(toProvider.getCreatedById().equals(loggedInUser.getId())){
			_LOGGER.debug("Access granted for created user {}", loggedInUser.getId());
			isAllowed =  true;
		}else if(isAdminRole(loggedInUser)){
			_LOGGER.debug("Access granted for logged in user {}", loggedInUser.getId());
			isAllowed =  true;
		}else{
			_LOGGER.debug("Unauthorized access , logged in user {}", loggedInUser.getId());
			isAllowed =  false;
		}
		
		return isAllowed;
	}
	
	private boolean isAdminRole(User loggedInUser){
		boolean isAdminRole = false;
		List<UserOrganizationRole> performerRoles = (List<UserOrganizationRole>) organizationUserService
				.getUserOrganizationRoles(loggedInUser);

		if (!performerRoles.isEmpty()) {
			UserOrganizationRole performerRole = performerRoles.get(0);
			Long performerRoleId = performerRole.getId().getRoleId();
			isAdminRole = checkPermission(performerRoleId);
		}
		return isAdminRole;
	}
	
	private boolean checkPermission(Long performerRoleId){
		if (performerRoleId.equals(HNIRoles.CLIENT.getRole())) {
			// No action allowed for a client/participant
			return false;
		} else if (performerRoleId.equals(HNIRoles.SUPER_ADMIN.getRole())) {
			return true;
		} 
		return false;
	}

	@Override
	public List<ProviderLocation> getProviderLocations(Long providerId,
			User loggedInUser) {
		_LOGGER.debug("Starting process for retrieve provider locations");
		Provider toProvider = get(providerId);

		if (isAllowed(loggedInUser, toProvider)) {
			return providerLocationService.locationsOf(providerId);
		}
		return null;
	}

	@Override
	public Map<String, String> deleteProviders(List<Long> providerIds,
			User loggedInUser) {
		Map<String, String> response = new HashMap<>();
		StringBuilder undeleted = new StringBuilder();
		for(Long id:providerIds){
			Provider provider = get(id);
			_LOGGER.debug("Starting process for retrieve provider " +id);
			if(isAllowed(loggedInUser,provider)){
				List<Order> providerOrders = getProviderOrders(id);
				if(providerOrders.isEmpty() && providerOrders.size() == 0){
					provider.setDeleted(true);
					provider.setActive(false);
					save(provider);
					List<ProviderLocation> providerLocations = providerLocationService.locationsOf(id);
					if(!providerLocations.isEmpty()){
						for(ProviderLocation providerLocation: providerLocations){
							ProviderLocation pl = providerLocationService.get(providerLocation.getId());
							pl.setIsActive(false);
							providerLocationService.save(pl);
							_LOGGER.debug("Deactivated providerLocation : " +providerLocation.getId());
						}
					}
					_LOGGER.debug("Provider " +id+" deleted.");
				}else {
					undeleted.append(provider.getName()).append(",");
					_LOGGER.debug("Exisiting order found for provider : "+provider.getId());
				}
			} else {
				undeleted.append(provider.getName()).append(",");
				_LOGGER.debug("No permissions to modify provider: "+provider.getId());
			}
		}
		
		if(undeleted.length() == 0){
			response.put(Constants.MESSAGE, "Provider(s) Deleted");
		} else {
			undeleted.deleteCharAt(undeleted.length()-1);
			response.put(Constants.MESSAGE, "Error deleting provider(s) : "+undeleted);
		}
		return response;
	}

	@Override
	public Map<String, String> activateProviders(List<Long> providerIds,
			Boolean value, User loggedInUser) {
		Map<String, String> response = new HashMap<>();
		StringBuilder undeleted = new StringBuilder();
		for(Long id:providerIds){
			Provider provider = get(id);
			_LOGGER.debug("Starting process for retrieve provider " +id);
			if(isAllowed(loggedInUser,provider)){
				List<Order> providerOrders = getOpenOrders(id);
				if(providerOrders.isEmpty() && providerOrders.size() == 0){
					provider.setActive(value);
					save(provider);
					_LOGGER.debug("Provider " +id+" status changed : "+value);
					if(!value){
						List<ProviderLocation> providerLocations = providerLocationService.locationsOf(id);
						if(!providerLocations.isEmpty()){
							for(ProviderLocation providerLocation: providerLocations){
								ProviderLocation pl = providerLocationService.get(providerLocation.getId());
								pl.setIsActive(false);
								providerLocationService.save(pl);
								_LOGGER.debug("Deactivated providerLocation : " +providerLocation.getId());
							}
						} else {
							_LOGGER.debug("No providerLocations found for provider : "+provider.getId());
						}
					}
				} else {
					undeleted.append(provider.getName()).append(",");
					_LOGGER.debug("Existing order found for provider : "+provider.getId());
				}
			} else {
				undeleted.append(provider.getName()).append(",");
				_LOGGER.debug("No permissions to modify provider: "+provider.getId());
			}
		}
		
		if(undeleted.length() == 0){
			response.put(Constants.MESSAGE, "Your request was submitted.");
		} else {
			undeleted.deleteCharAt(undeleted.length()-1);
			response.put(Constants.MESSAGE, "Error activate/de-activate provider(s) : "+undeleted);
		}
		return response;
	}

	@Override
	public List<Order> getProviderOrders(Long providerId) {
		return providerDao.getProviderOrders(providerId);
	}
	
	private List<Order> getOpenOrders(Long providerId){
		return providerDao.getOpenOrders(providerId);
	}

}
