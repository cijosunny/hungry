/**
 * 
 */
package org.hni.service.helpers;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.hni.common.service.HniTemplateService;
import org.hni.organization.service.OrganizationUserService;
import org.hni.sms.service.provider.PushMessageService;
import org.hni.type.HNIRoles;
import org.hni.user.dao.ClientDAO;
import org.hni.user.om.Invitation;
import org.hni.user.om.User;
import org.hni.user.service.NGOGenericService;
import org.hni.user.service.UserOnboardingService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.monitorjbl.json.JsonView;
import com.monitorjbl.json.JsonViewSerializer;

/**
 * @author Rahul
 *
 */
public abstract class AbstractServiceHelper {
	
	@Inject
	protected UserOnboardingService userOnBoardingService;
	@Inject
	protected PushMessageService smsMessageService;
	@Inject
	protected HniTemplateService hniTemplateService;
	@Inject
	protected OrganizationUserService orgUserService;
	@Inject
	protected ClientDAO clientDao;
	@Inject
	protected NGOGenericService ngoGenericService;
	
	protected ObjectMapper mapper = new ObjectMapper();
	protected SimpleModule module = new SimpleModule();
	
	@PostConstruct
	public void configureJackson() {
		module.addSerializer(JsonView.class, new JsonViewSerializer());
		mapper.registerModule(module);
	}
	
	protected boolean isUserExists(String email) {
		return getUserByEmail(email) != null;
	}
	
	protected User getUserByEmail(String email) {
		return userOnBoardingService.getUserByEmail(email);
	}

	protected String getFromNumber(String stateCode) {
		return smsMessageService.getProviderNumberForState(stateCode);
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
