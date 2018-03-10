package org.hni.user.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hni.admin.service.dto.NgoBasicDto;
import org.hni.common.service.BaseService;
import org.hni.user.om.Client;
import org.hni.user.om.Invitation;
import org.hni.user.om.User;
import org.hni.user.om.Volunteer;

import com.fasterxml.jackson.databind.node.ObjectNode;

public interface UserOnboardingService extends BaseService<Invitation>{
	String buildInvitationAndSave(Long orgId, Long invitedBy, String email,String data);
	Collection<Invitation> validateInvitationCode(String invitationCode);
	Map<String,String> ngoSave(ObjectNode onboardData, User user);
	ObjectNode getNGODetail(Long ngoId, User user);
	List<NgoBasicDto> getAllNgo();
	Map<String,String> buildVolunteerAndSave(Volunteer volunteer,  User user);
	Map<String,String> saveVolunteerAvailability(ObjectNode availableJSON);
	ObjectNode getVolunteerAvailability(Long userId);
	Map<String,String> clientSave(Client client, User user);
	Map<String, Object> getUserProfiles(String type, Long user);
	Invitation finalizeRegistration(String activationCode);
	
	Invitation createInvitation(Invitation invite);
	User getUserByEmail(String email);
	String isValidInvitation(String phoneNumber);
	Invitation getInvitationByPhoneNumber(String phoneNumber);
}
