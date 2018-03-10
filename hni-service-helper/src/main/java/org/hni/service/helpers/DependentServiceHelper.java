package org.hni.service.helpers;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import org.hni.admin.service.converter.HNIConverter;
import org.hni.user.dao.ClientDAO;
import org.hni.user.om.Client;
import org.hni.user.om.Dependent;
import org.hni.user.om.Ngo;
import org.hni.user.om.User;
import org.hni.user.service.DependentService;
import org.hni.user.service.NGOGenericService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DependentServiceHelper extends AbstractServiceHelper {
	private static final Logger _LOGGER = LoggerFactory.getLogger(DependentServiceHelper.class);
	
	@Inject
	private NGOGenericService ngoGenericService;
	
	@Inject
	private ClientDAO clientDao;
	
	@Inject
	@Named("defaultDependentService")
	private DependentService dependentService;
	
	public void modifyDependents(Client existingClient, Client client, User loggedInUser){
		addOrRemoveDependents(client.getDependents(), existingClient.getDependents(), existingClient, loggedInUser);
		
		existingClient.getUser().setFirstName(client.getUser().getFirstName());
		existingClient.getUser().setLastName(client.getUser().getLastName());
		existingClient.getUser().setMobilePhone(HNIConverter.convertPhoneNumberFromUiFormat(client.getUser().getMobilePhone()));
		existingClient.getUser().setIsActive(client.getUser().getIsActive());
		existingClient.setMaxOrderAllowed(client.getMaxOrderAllowed());
		existingClient.setMaxMealsAllowedPerDay(client.getMaxMealsAllowedPerDay());
		
		Ngo ngo = ngoGenericService.get(client.getNgo().getId());
		existingClient.setNgo(ngo);
		
		existingClient.getUser().setIsActive(client.getUser().getIsActive());
		existingClient.getUser().setUpdatedBy(loggedInUser);
		
		clientDao.update(existingClient);
		
	}
	
	private void addOrRemoveDependents(Set<Dependent> newDependentsSet,
			Set<Dependent> extDependents, Client client, User loggedInUser) {
		List<Dependent> dependentsToDelete = new ArrayList<>();
		Set<Dependent> dependentsToAdd = new HashSet<>();
		if (extDependents != null && !extDependents.isEmpty()) {
			for (Dependent extDep : extDependents) {
				boolean matchFound = false;
				for (Dependent newDep : newDependentsSet) {
					if (extDep.getId().equals(newDep.getId())) {
						matchFound = true;
						break;
					} else {
						_LOGGER.debug("Saving new dependent");
						dependentsToAdd.add(newDep);
					}
				}
				
				if (!matchFound) {
					_LOGGER.debug("Deleting dependent");
					dependentsToDelete.add(extDep);
				}
			}
			
			deleteDependents(dependentsToDelete, client);
			addDependentsToClient(dependentsToAdd, client, loggedInUser);
		} else {
			_LOGGER.debug("Adding new dependents");
			addDependentsToClient(newDependentsSet, client, loggedInUser);
		}
		
		
	}

	private void addDependentsToClient(Set<Dependent> dependentsToAdd, Client existingClient, User loggedInUser) {
		Iterator<Dependent> iterator = dependentsToAdd.iterator();
		while(iterator.hasNext()){
			Dependent dependent = iterator.next();
			dependent.setCreatedBy(loggedInUser);
			dependent.setIsActive(true);
			dependent.setCreatedDate(new Date());
			dependent.setModifiedDate(new Date());
			dependent.setClient(existingClient);
			dependent.setGender(dependent.getGender());
			dependentService.save(dependent);
		}
		
	}

	private void deleteDependents(List<Dependent> dependentsToDelete, Client existingClient) {
		Iterator<Dependent> iterator = dependentsToDelete.iterator();
		while(iterator.hasNext()){
			Dependent dependent = iterator.next();
			dependentService.delete(dependent);
			existingClient.getDependents().remove(dependent);
		}
	}

}
