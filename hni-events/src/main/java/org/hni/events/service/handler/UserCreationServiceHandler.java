/**
 * 
 */
package org.hni.events.service.handler;

import java.util.Optional;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.hni.common.Constants;
import org.hni.organization.service.OrganizationUserService;
import org.hni.security.utils.HNISecurityUtils;
import org.hni.type.HNIRoles;
import org.hni.user.dao.ClientDAO;
import org.hni.user.om.Client;
import org.hni.user.om.Invitation;
import org.hni.user.om.User;
import org.hni.user.service.NGOGenericService;
import org.hni.user.service.UserOnboardingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author Rahul K
 *
 */
@Component
public class UserCreationServiceHandler {

	private static final Logger _LOGGER = LoggerFactory.getLogger(UserCreationServiceHandler.class);

	private static final Integer MAX_MEAL_PER_DEPENDENT = 2;
	private static final Integer MAX_MEAL_ALLOWED_FOR_PARTICIPANT = 180;
	private static final Integer MAX_MEAL_ALLOWED_PER_DAY = 2;

	@Inject
	protected OrganizationUserService orgUserService;
	@Inject
	protected ClientDAO clientDao;
	@Inject
	protected NGOGenericService ngoGenericService;
	@Inject
	protected UserOnboardingService userOnBoardingService;
	
	
	public boolean createUserForSmsChannel(User user) {
		_LOGGER.info("Started adding new user to system from SMS channgel");
		_LOGGER.debug("User Info : {}", user.toString());
		_LOGGER.debug("Password Hashing started");
		HNISecurityUtils.setHashSecret(user, user.getPassword());
		_LOGGER.debug("Password Hashing completed");

		setDefaultValuesToUser(user);

		Optional<Invitation> invitation = Optional.ofNullable(getInvitationByMobile(user.getMobilePhone()));
		if (invitation.isPresent()) {
			createUser(user, HNIRoles.CLIENT, invitation.get());
			invitation.get().setActivated(1);
			userOnBoardingService.update(invitation.get());
			// Send welcome notification to users mobile
			return true;
		} else {
			_LOGGER.debug("Unable to find a valid invitation record for the user");
			return false;
		}

	}

	@Transactional
	private void createUser(User user, HNIRoles role, Invitation invitation) {
		_LOGGER.info("Saving User account details to database");
		
		user.setOrganizationId(invitation.getOrganizationId() != null ? Long.valueOf(invitation.getOrganizationId()) : 1L);
		
		orgUserService.register(user, role.getRole());
		_LOGGER.info("Completed saving User account details to database");
		if (isParticipant(role)) {
			_LOGGER.info("Creating default client profile for participant");
			createDefaultProfile(user, invitation);
			_LOGGER.info("Completed default client profile creation for participant");
		}

	}

	private void createDefaultProfile(User user, Invitation invitation) {
		Client client = new Client();
		client.setRace(0L);
		client.setUserId(user.getId());
		client.setUser(user);
		client.setSheltered(Boolean.TRUE);
		client.setMaxMealsAllowedPerDay(getMaxAllowedValueOrDefault(invitation, 2));
		client.setMaxOrderAllowed(MAX_MEAL_ALLOWED_FOR_PARTICIPANT);
		client.setCreatedBy(invitation.getInvitedBy() != null ? invitation.getInvitedBy() : 1);
		if (invitation.getNgoId() != null && !invitation.getNgoId().equals(0)) {
			client.setNgo(ngoGenericService.get(invitation.getNgoId()));
		}

		clientDao.save(client);

	}

	private Integer getMaxAllowedValueOrDefault(Invitation invitation, Integer defaultValue) {
		Integer dependentCount = invitation.getDependantsCount() != null ? invitation.getDependantsCount() : 0;
		// TODO: Get default property value for dependent multiplication from DB
		return (dependentCount * MAX_MEAL_PER_DEPENDENT) + 2;
	}

	private void setDefaultValuesToUser(User user) {
		user.setIsActive(Boolean.TRUE);
		user.setDeleted(Boolean.FALSE);
		user.setPassword(Constants.EMPTY_STR);
	}
	

	protected Invitation getInvitationByMobile(String mobilePhone) {
		return userOnBoardingService.getInvitationByPhoneNumber(mobilePhone);
	}
	
	protected boolean isParticipant(HNIRoles role) {
		if (HNIRoles.CLIENT.equals(role)) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}
}
