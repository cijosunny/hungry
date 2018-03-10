package org.hni.service.helpers;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import org.hni.common.Constants;
import org.hni.order.om.Order;
import org.hni.order.service.OrderService;
import org.hni.organization.om.UserOrganizationRole;
import org.hni.organization.service.OrganizationUserService;
import org.hni.provider.om.Provider;
import org.hni.provider.om.ProviderLocation;
import org.hni.provider.service.ProviderLocationService;
import org.hni.provider.service.ProviderService;
import org.hni.type.HNIRoles;
import org.hni.user.om.Client;
import org.hni.user.om.User;
import org.hni.user.service.ClientService;
import org.hni.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ConfigurationServiceHelper extends AbstractServiceHelper {

	private static final Logger _LOGGER = LoggerFactory.getLogger(ConfigurationServiceHelper.class);

	@Inject
	protected OrganizationUserService organizationUserService;
	@Inject
	@Named("defaultUserService")
	private UserService userService;
	@Inject
	private OrderService orderService;	
	@Inject
	private ClientService clientService;
	@Inject
	private OrderServiceHelper orderServiceHelper;
	@Inject
	private DependentServiceHelper dependentServiceHelper;
	@Inject
	private ProviderService providerService;
	@Inject
	private ProviderLocationService providerLocationService;

	public Map<String, String> activateUser(Long userId, User loggedInUser) {
		_LOGGER.debug("Starting process for activate user");
		Map<String, String> response = new HashMap<>();
		User toUser = userService.get(userId);

		if (isAllowed(loggedInUser, toUser)) {
			toUser.setIsActive(true);
			toUser.setDeleted(false);

			toUser.setUpdatedBy(loggedInUser);
			userService.update(toUser);

			response.put(Constants.STATUS, Constants.SUCCESS);
			response.put(Constants.MESSAGE, "User is been activated");
		} else {
			response.put(Constants.STATUS, Constants.ERROR);
			response.put(Constants.MESSAGE, "You don't have to permission to excute this action");
		}

		return response;
	}

	public Map<String, String> deActivateUser(Long userId, User loggedInUser) {
		_LOGGER.debug("Starting process for de-activate user");
		Map<String, String> response = new HashMap<>();
		User toUser = userService.get(userId);

		if (isAllowed(loggedInUser, toUser)) {
			_LOGGER.info("Logged in user is allowed to perform this action");
			if (findRoleOfUser(toUser).equals(HNIRoles.CLIENT.getRole())) {
				cancelOpenOrdersIfExists(toUser);
			}
			toUser.setIsActive(false);

			toUser.setUpdatedBy(loggedInUser);
			userService.update(toUser);

			response.put(Constants.STATUS, Constants.SUCCESS);
			response.put(Constants.MESSAGE, "User is been de-activated");
		} else {
			response.put(Constants.STATUS, Constants.ERROR);
			response.put(Constants.MESSAGE, "You don't have to permission to excute this action");
		}

		return response;
	}

	private void cancelOpenOrdersIfExists(User user) {
		_LOGGER.info("Finding Open orders for user");
		Collection<Order> openOrders = orderService.getOpenOrdersFor(user);
		if (!openOrders.isEmpty()) {
			_LOGGER.info("Sending order cancellation notification to user : ");
			openOrders.forEach(order -> {
				_LOGGER.info("Sending cancel notification for Order ID (sys): " + order.getId());
				orderServiceHelper.sendOrderCancelNotification(order, Constants.CANCEL_REASON_USER_IS_NOT_ACTIVE);
				_LOGGER.info("Completed cancel notification for Order ID (sys): " + order.getId());
			});
		}
		
	}

	private Long findRoleOfUser(User user) {
		_LOGGER.info("Finding user role for id  "+ user.getEmail());
		List<UserOrganizationRole> userRoles = (List<UserOrganizationRole>) organizationUserService.getUserOrganizationRoles(user);
		if (!userRoles.isEmpty()) {
			return userRoles.get(0).getId().getRoleId();
		} else {
			return HNIRoles.USER.getRole();
		}
		
	}
	/**
	 * Check the given user has the permission to enable or disable such actions
	 * on the given user
	 * 
	 * @return
	 */
	public boolean isAllowed(User performer, User toUser) {
		boolean isAllowed = false;
		_LOGGER.info("Logged User {}, User to modify {} ", performer.getId(), toUser.getId());
		List<UserOrganizationRole> performerRoles = (List<UserOrganizationRole>) organizationUserService
				.getUserOrganizationRoles(performer);

		if (!performerRoles.isEmpty()) {
			UserOrganizationRole performerRole = performerRoles.get(0);
			// Now get the roles of user who need to be modified
			List<UserOrganizationRole> toUserRoles = (List<UserOrganizationRole>) organizationUserService
					.getUserOrganizationRoles(toUser);
			if (!toUserRoles.isEmpty()) {
				UserOrganizationRole toUserRole = toUserRoles.get(0);

				Long roleOfPerformer = performerRole.getId().getRoleId();
				Long roleOfToUser = toUserRole.getId().getRoleId();

				isAllowed = checkPermission(roleOfPerformer, roleOfToUser);
			}
		}
		return isAllowed;
	}

	private boolean checkPermission(Long roleOfPerformer, Long roleOfToUser) {

		if (roleOfPerformer.equals(HNIRoles.CLIENT.getRole())) {
			// No action allowed for a client/participant
			return false;
		} else if (roleOfPerformer.equals(HNIRoles.SUPER_ADMIN.getRole())) {
			return true;
		} else if ((roleOfPerformer.equals(HNIRoles.NGO_ADMIN.getRole())
				|| roleOfPerformer.equals(HNIRoles.NGO.getRole()))
				&& (roleOfToUser.equals(HNIRoles.CLIENT.getRole())
						|| roleOfToUser.equals(HNIRoles.VOLUNTEERS.getRole()))) {
			return true;
		}
		return false;
	}


	public Map<Object, Object> activateUsers(List<Long> userIds, User loggedInUser) {
		_LOGGER.debug("Starting process for activate multiple user");
		Map<Object, Object> response = new HashMap<>();

		userIds.stream().parallel().forEach(userId -> {
			User toUser = userService.get(userId);

			if (isAllowed(loggedInUser, toUser)) {
				toUser.setIsActive(true);
				toUser.setDeleted(false);

				toUser.setUpdatedBy(loggedInUser);
				userService.update(toUser);

				response.put(userId, Constants.SUCCESS);
			} else {
				response.put(userId, Constants.ERROR);
			}
		});

		return response;
	}

	public Map<Object, Object> deActivateUsers(List<Long> userIds, User loggedInUser) {
		_LOGGER.debug("Starting process for de-activate multiple user");
		Map<Object, Object> response = new HashMap<>();

		userIds.stream().parallel().forEach(userId -> {
			User toUser = userService.get(userId);

			if (isAllowed(loggedInUser, toUser)) {
				if (findRoleOfUser(toUser).equals(HNIRoles.CLIENT.getRole())) {
					cancelOpenOrdersIfExists(toUser);
				}
				toUser.setIsActive(false);

				toUser.setUpdatedBy(loggedInUser);
				userService.update(toUser);

				response.put(userId, Constants.SUCCESS);
			} else {
				response.put(userId, Constants.ERROR);
			}
		});

		return response;
	}

	public Map<String, String> deleteUser(Long userId, User loggedInUser) {
		_LOGGER.debug("Starting process for delete user");
		Map<String, String> response = new HashMap<>();
		User toUser = userService.get(userId);

		if (isAllowed(loggedInUser, toUser)) {
			if (findRoleOfUser(toUser).equals(HNIRoles.CLIENT.getRole())) {
				cancelOpenOrdersIfExists(toUser);
			}
			toUser.setIsActive(false);
			toUser.setDeleted(true);

			toUser.setUpdatedBy(loggedInUser);
			userService.update(toUser);

			response.put(Constants.STATUS, Constants.SUCCESS);
			response.put(Constants.MESSAGE, "User is been deleted");
		} else {
			response.put(Constants.STATUS, Constants.ERROR);
			response.put(Constants.MESSAGE, "You don't have to permission to excute this action");
		}

		return response;
	}
	
	public Map<Object, Object> deleteUsers(List<Long> userIds, User loggedInUser) {
		_LOGGER.debug("Starting process for delete user");
		Map<Object, Object> response = new HashMap<>();
		userIds.stream().parallel().forEach(userId -> {
			User toUser = userService.get(userId);
	
			if (isAllowed(loggedInUser, toUser)) {
				if (findRoleOfUser(toUser).equals(HNIRoles.CLIENT.getRole())) {
					cancelOpenOrdersIfExists(toUser);
				}
				toUser.setIsActive(false);
				toUser.setDeleted(true);
	
				toUser.setUpdatedBy(loggedInUser);
				userService.update(toUser);
	
				response.put(userId, Constants.SUCCESS);
			} else {
				response.put(userId, Constants.ERROR);
			}
		});
		return response;
	}
	
	@Transactional
	public Map<String, String> shelterUsers(List<Long> userIds, User loggedInUser, Boolean isSheltered) {
		_LOGGER.debug("Starting process for shelter user");
		Map<String, String> response = new HashMap<>();
		User parent = userService.get(loggedInUser.getId());
		userIds.forEach(userId -> {
			User toUser = userService.get(userId);
			if (isAllowed(loggedInUser, toUser)) {
				Client client = clientService.getByUserId(userId);
				if(client == null){
					client = new Client();
					client.setRace(0L);
					client.setUserId(toUser.getId());
					client.setUser(toUser);
					client.setCreatedBy(loggedInUser.getId());
				}
				client.setSheltered(isSheltered);
				client.getUser().setUpdatedBy(parent);
				
				clientService.update(client);
	
				response.put(Constants.STATUS, Constants.SUCCESS);
				response.put(Constants.MESSAGE, "User is been sheltered");
			
			} else {
				response.put(Constants.STATUS, Constants.ERROR);
				response.put(Constants.MESSAGE, "You don't have to permission to excute this action");
			}
		});
		return response;
	}
	
	public Client getParticipantDetails(Long userId, User loggedInUser) {
		_LOGGER.debug("Starting process for retrieve user");
		Map<String, String> response = new HashMap<>();
		User toUser = userService.get(userId);

		if (isAllowed(loggedInUser, toUser)) {
			return clientService.getByUserId(userId);
		}
		return null;
	}
	
	@Transactional
	public Map<String, String> saveParticipantDetails(Client client, User loggedInUser) {
		_LOGGER.debug("Starting process for save user");
		Long userId = client.getUser().getId();
		User toUser = userService.get(userId);
		Map<String, String> response = new HashMap<>();
		if (isAllowed(loggedInUser, toUser)) {
			Client existingClient = clientService.getByUserId(userId);
			existingClient.getUser().setGenderCode(client.getUser().getGenderCode());
			existingClient.setAge(client.getAge());
			dependentServiceHelper.modifyDependents(existingClient, client, loggedInUser);
			
			response.put(Constants.STATUS, Constants.SUCCESS);
			response.put(Constants.MESSAGE, "User profile updated");
		} else {
			response.put(Constants.STATUS, Constants.ERROR);
			response.put(Constants.MESSAGE, "You don't have to permission to excute this action");
		}

		return response;
	}
}
