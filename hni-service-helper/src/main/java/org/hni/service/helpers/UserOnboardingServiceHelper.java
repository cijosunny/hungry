/**
 * 
 */
package org.hni.service.helpers;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.hni.common.Constants;
import org.hni.common.email.service.EmailComponent;
import org.hni.organization.om.Organization;
import org.hni.organization.om.UserOrganizationRole;
import org.hni.organization.service.OrganizationService;
import org.hni.organization.service.OrganizationUserService;
import org.hni.sms.service.provider.PushMessageService;
import org.hni.sms.service.provider.SmsServiceLoader;
import org.hni.sms.service.provider.om.SmsProvider;
import org.hni.user.om.Invitation;
import org.hni.user.om.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author Rahul
 *
 */
@Component
public class UserOnboardingServiceHelper extends AbstractServiceHelper {

	private static final String _INVITE = "_INVITE";

	private static final String CLIENT = "Client";

	private static final String FAILED_TO_CREATE_A_INVITATION = "Failed to create a invitation";

	private static final String SOMETHING_WENT_WRONG_INVITATION = "Something went wrong while creating invitation, please try after sometime";

	private static final String HNI_BASE_NUMBER = "3143000305";

	private static final Logger _LOGGER = LoggerFactory.getLogger(UserOnboardingServiceHelper.class);

	@Inject
	protected OrganizationUserService organizationUserService;

	@Inject
	private OrganizationService organizationService;

	@Inject
	private SmsServiceLoader smsServiceLoader;

	@Inject
	private PushMessageService smsMessageService;

	@Inject
	private EmailComponent emailComponent;

	public Map<String, Object> inviteUser(Invitation inviteRequest, User loggerInUser, String userRole) {
		Map<String, Object> map = new HashMap<>();
		map.put(Constants.RESPONSE, Constants.ERROR);
		try {

			if (inviteRequest.getOrganizationId() == null) {
				Long organizationId = getOrganizationId(loggerInUser);
				inviteRequest.setOrganizationId(String.valueOf(organizationId));
			}

			if (!isUserExists(inviteRequest.getEmail())) {
				inviteRequest.setInvitedBy(loggerInUser.getId());
				inviteRequest.setInvitationType(userRole.toUpperCase() + _INVITE);
				
				inviteRequest = userOnBoardingService.createInvitation(inviteRequest);
				if (inviteRequest.getId() != null) {
					// Invitation record is been created
					Map<String,SmsProvider> phoneNumbers = smsServiceLoader.getProviders();
					SmsProvider smsProvider = (SmsProvider) phoneNumbers.get(inviteRequest.getStateCode());
					if(smsProvider != null){
						String phoneNumber = smsProvider.getLongCode();
						emailComponent.sendEmail(inviteRequest, userRole,phoneNumber);
						sendSmsInvitation(inviteRequest, userRole, phoneNumber);
						map.put(Constants.RESPONSE, Constants.SUCCESS);
					} else{
						emailComponent.sendEmail(inviteRequest, userRole,null);
						map.put(Constants.RESPONSE, Constants.SUCCESS);
					}
				} else {
					map.put(Constants.MESSAGE, FAILED_TO_CREATE_A_INVITATION);
				}
			} else {
				StringBuilder stringBuilder = new StringBuilder();

				stringBuilder.append("A user with ");
				stringBuilder.append(inviteRequest.getEmail());
				stringBuilder.append(" already exists");

				map.put(Constants.MESSAGE, stringBuilder.toString());
			}
			return map;
		} catch (Exception e) {
			map.put(Constants.MESSAGE, SOMETHING_WENT_WRONG_INVITATION);
			_LOGGER.error("Serializing User object:" + e.getMessage(), e);
		}
		return map;

	}

	public Map<String, Object> inviteNGO(Organization org, User loggedInUser) {
		Map<String, Object> response = new HashMap<>();
		response.put(Constants.RESPONSE, Constants.ERROR);
		try {
			org.setCreatedById(loggedInUser.getId());
			org.setCreated(new Date());
			
			boolean ors = organizationService.isAlreadyExists(org);
			if (!ors) {
				Organization organization = organizationService.save(org);
				Invitation inviteRequest = new Invitation();
				inviteRequest.setPhone(org.getPhone());
				inviteRequest.setEmail(organization.getEmail());
				inviteRequest.setInvitationType("NGO Invitation");
				inviteRequest.setName(organization.getName());
				inviteRequest.setOrganizationId(String.valueOf(organization.getId()));

				response = inviteUser(inviteRequest, loggedInUser, "NGO");
				return response;
			} else {
				response.put(Constants.MESSAGE, "An organization with same email address already exist");
			}
		} catch (Exception e) {
			_LOGGER.error("Serializing User object:" + e.getMessage(), e);
			response.put(Constants.MESSAGE, "Something went wrong, please try after sometime");
		}
		return response;
	}

	private void sendSmsInvitation(Invitation inviteRequest, String userRole, String phoneNumber) {

		if (userRole.equalsIgnoreCase(CLIENT)) {
			StringBuilder message = new StringBuilder("Hi ");
			message.append(inviteRequest.getName());
			message.append(" ");
			message.append("Thank you for your interest in becoming a Hunger Not Impossible Participant!"
					+ " To register with us, please reply ENROLL to this number");

			smsMessageService.sendMessage(message.toString(), phoneNumber != null ? phoneNumber : HNI_BASE_NUMBER, inviteRequest.getPhone());
		}

	}

	protected List<SmsProvider> getSmsProviders(String stateCode) {

		return stateCode != null ? smsServiceLoader.getSmsProvidersByState(stateCode) : new ArrayList<>(1);
	}

	/**
	 * @param loggerInUser
	 * @return
	 */
	private Long getOrganizationId(User loggerInUser) {
		Long organizationId;

		List<UserOrganizationRole> userOrganizationRoles = (List<UserOrganizationRole>) organizationUserService
				.getUserOrganizationRoles(loggerInUser);

		if (!userOrganizationRoles.isEmpty()) {
			organizationId = userOrganizationRoles.get(0).getId().getOrgId();
		} else {
			// Sets org to super user - hack
			organizationId = 1L;
		}
		return organizationId;
	}
}
