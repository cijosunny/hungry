package org.hni.user.service;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.apache.shiro.util.ThreadContext;
import org.hni.admin.service.converter.HNIConverter;
import org.hni.admin.service.converter.HNIValidator;
import org.hni.admin.service.dto.NgoBasicDto;
import org.hni.common.Constants;
import org.hni.common.HNIUtils;
import org.hni.common.dao.BaseDAO;
import org.hni.common.om.FoodBank;
import org.hni.common.om.FoodService;
import org.hni.common.om.MealDonationSource;
import org.hni.common.om.MealFundingSource;
import org.hni.common.om.NgoFundingSource;
import org.hni.common.service.AbstractService;
import org.hni.user.dao.ClientDAO;
import org.hni.user.dao.NGOGenericDAO;
import org.hni.user.dao.UserDAO;
import org.hni.user.dao.UserOnboardingDAO;
import org.hni.user.dao.VolunteerAvailabilityDAO;
import org.hni.user.dao.VolunteerDao;
import org.hni.user.om.Address;
import org.hni.user.om.BoardMember;
import org.hni.user.om.BrandPartner;
import org.hni.user.om.Client;
import org.hni.user.om.Endrosement;
import org.hni.user.om.Invitation;
import org.hni.user.om.LocalPartner;
import org.hni.user.om.Ngo;
import org.hni.user.om.User;
import org.hni.user.om.Volunteer;
import org.hni.user.om.VolunteerAvailability;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Component
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class DefaultUserOnboardingService extends AbstractService<Invitation> implements UserOnboardingService{
	
	private static final String SUCCESS = "Success";

	ObjectMapper mapper = new ObjectMapper();
	@Inject
	private UserOnboardingDAO invitationDAO;

	
	@Inject
	private NGOGenericDAO ngoGenericDAO;
	
	@Inject
	private VolunteerDao volunteerDao;
	
	@Inject
	private ClientDAO clientDAO;
	
	@Inject
	private UserDAO userDao;
	
	@Inject
	private UserOnboardingDAO userOnboardDao;
	
	@Inject
	private VolunteerAvailabilityDAO volunteerAvailabilityDAO;

	public DefaultUserOnboardingService(BaseDAO<Invitation> dao) {
		super(dao);
	}

	@Override
	public Collection<Invitation> validateInvitationCode(String invitationCode) {
		return invitationDAO.validateInvitationCode(invitationCode);
	}


	@Override
	public String buildInvitationAndSave(Long orgId, Long invitedBy, String email,String data) {
		User user = userDao.byEmailAddress(email);
		if (user == null) {
			String UUID = HNIUtils.getUUID();
			Invitation invitation = new Invitation();
			invitation.setOrganizationId(orgId.toString());
			invitation.setInvitationCode(UUID);
			invitation.setInvitedBy(invitedBy);
			invitation.setEmail(email);
			invitation.setCreatedDate(new Date());
			invitation.setActivated(0);
			invitation.setData(data);
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_MONTH, 5);
			invitation.setExpirationDate(cal.getTime());
			invitationDAO.save(invitation);
			return UUID;
		} else {
			return null;
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class )
	public Map<String, String> ngoSave(ObjectNode onboardData, User user) {
		Map<String, String> messages = new HashMap<>();
		validateNGO(onboardData, messages);
		if (messages != null && messages.isEmpty()) {
			saveNGOData(onboardData, user);
		} else {
			return messages;
		}
		return messages;
	}
	
	private void validateNGO(ObjectNode onboardData, Map<String, String> errors) {
		HNIValidator.validateNgo(HNIConverter.getNGOFromJson(onboardData),errors);
		HNIValidator.validateBoardMembers(HNIConverter.getBoardMembersFromJson(onboardData,null),errors);
		HNIValidator.validateBrandPartners(HNIConverter.getBrandPartnersFromJson(onboardData,null),errors);
		HNIValidator.validateLocalPartners(HNIConverter.getLocalPartnersFromJson(onboardData,null),errors);
		HNIValidator.validateFoodBank(HNIConverter.getFoodBankFromJson(onboardData,null),errors);
		HNIValidator.validateFoodServices(HNIConverter.getFoodServicesFromJson(onboardData,null),errors);
		HNIValidator.validateMealDonationSources( HNIConverter.getMealDonationSourceFromJson(onboardData,null),errors);
		HNIValidator.validateMealFundingSources(HNIConverter.getMealFundingSourcesFromJson(onboardData,null),errors);
		HNIValidator.validateNgoFundingSources(HNIConverter.getNgoFundingSourcesFromJson(onboardData,null),errors);
	}
	
	private String saveNGOData(ObjectNode onboardData, User user){
		
		Ngo ngoExst = ngoGenericDAO.findUnique(Ngo.class,"select x from Ngo x where x.userId=?1 ", user.getId());
		Ngo ngo = HNIConverter.getNGOFromJson(onboardData);
		if(ngoExst!=null ){
			ngo.setId(ngoExst.getId());
			ngo = ngoGenericDAO.update(Ngo.class ,ngo);
		}
		else{
			Invitation invitation = invitationDAO.getInvitedBy(user.getEmail());
			ngo.setCreatedBy(invitation.getInvitedBy());
			ngo = ngoGenericDAO.save(Ngo.class ,ngo);
		}
		user.setAddresses(HNIConverter.getAddressSet(onboardData));
		userDao.update(user);
		
		ngoGenericDAO.saveBatch(BoardMember.class ,(HNIConverter.getBoardMembersFromJson(onboardData,ngo.getId())), ngo.getId());
		ngoGenericDAO.saveBatch(BrandPartner.class ,HNIConverter.getBrandPartnersFromJson(onboardData,ngo.getId()), ngo.getId());
		ngoGenericDAO.saveBatch(LocalPartner.class ,HNIConverter.getLocalPartnersFromJson(onboardData,ngo.getId()), ngo.getId());
		ngoGenericDAO.saveBatch(FoodBank.class ,HNIConverter.getFoodBankFromJson(onboardData,ngo.getId()), ngo.getId());
		ngoGenericDAO.saveBatch(FoodService.class ,HNIConverter.getFoodServicesFromJson(onboardData,ngo.getId()), ngo.getId());
		ngoGenericDAO.saveBatch(MealDonationSource.class ,HNIConverter.getMealDonationSourceFromJson(onboardData,ngo.getId()), ngo.getId());
		ngoGenericDAO.saveBatch( MealFundingSource.class,HNIConverter.getMealFundingSourcesFromJson(onboardData,ngo.getId()), ngo.getId());
		ngoGenericDAO.saveBatch(NgoFundingSource.class ,HNIConverter.getNgoFundingSourcesFromJson(onboardData,ngo.getId()), ngo.getId());
		ngoGenericDAO.saveBatch(Endrosement.class, HNIConverter.getEndrosement(onboardData,ngo.getId()), ngo.getId());
		ngoGenericDAO.updateStatus(ngo.getUserId());
		return SUCCESS;
		
	}
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class )
	public Map<String,String> buildVolunteerAndSave(Volunteer volunteer, User user) {
		Map<String, String> error = new HashMap<>();
		
		
		Volunteer extVolunteer = volunteerDao.getByUserId(user.getId());
		Long volunteerId = extVolunteer != null ? extVolunteer.getId() : null;
		if (volunteerId != null) {
			volunteer.setId(extVolunteer.getId());
			volunteer.setCreatedBy(extVolunteer.getCreatedBy());
			volunteer.setCreated(new Date());
		} else {

			
			Long createdBy = getInvitedBy(user.getEmail());
			if(createdBy==null){

				createdBy = user.getId();
				
			}
			volunteer.setCreatedBy(createdBy);
			volunteer.setCreated(new Date());
		}
		volunteer.setUserId(user.getId());
		
		HNIValidator.validateVolunteer(volunteer, error);
		if (error != null && !error.isEmpty()) {
			return error;
		} else {
			volunteer = volunteerDao.save(volunteer);
		}
		
		if (volunteer.getId() != null) {
			ngoGenericDAO.updateStatus(volunteer.getUserId());
		}
		return error;
	}
	
	private Long getInvitedBy(String email) {
		Invitation invitedBy = invitationDAO.getInvitedBy(email);
		return invitedBy.getInvitedBy();
	} 

	
	@SuppressWarnings("deprecation")
	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class )
	public ObjectNode getNGODetail(Long ngoId, User user) {
		ObjectNode parentJSON = mapper.createObjectNode();
		ObjectNode overViewNode = mapper.createObjectNode();
		// set Address to overview
		// TODO if user is null try to get from user table using the user_id of NGO table
		if (user != null) {
			overViewNode.put("address", HNIConverter.getAddress(mapper.createObjectNode(), user.getAddresses()));
			overViewNode.put("name", user.getFirstName() + " " + user.getLastName());
			overViewNode.put("mobilePhone", user.getMobilePhone());
			overViewNode.put("genderCode", user.getGenderCode());
			 
			 
		}

		parentJSON.set("overview", overViewNode);
		parentJSON.set("stakeHolder", mapper.createObjectNode());
		parentJSON.set("service", mapper.createObjectNode());
		parentJSON.set("funding", mapper.createObjectNode());
		parentJSON.set("client", mapper.createObjectNode());

		HNIConverter.convertEndrosementToJSON(ngoGenericDAO.find(Endrosement.class, "select x from Endrosement x where x.ngoId=?1 ", ngoId), overViewNode);
		HNIConverter.convertNGOToJSON((Ngo) ngoGenericDAO.get(Ngo.class, ngoId), parentJSON);
		HNIConverter.convertBoardMembersToJSON(ngoGenericDAO.find(BoardMember.class, "select x from BoardMember x where x.ngoId=?1 ", ngoId), parentJSON);
		HNIConverter.convertBrandPartnersToJSON(ngoGenericDAO.find(BrandPartner.class, "select x from BrandPartner x where x.ngoId=?1 ", ngoId), parentJSON);
		HNIConverter.convertLocalPartnerToJSON(ngoGenericDAO.find(LocalPartner.class, "select x from LocalPartner x where x.ngoId=?1 ", ngoId), parentJSON);
		HNIConverter.convertFoodBanksToJSON(ngoGenericDAO.find(FoodBank.class, "select x from FoodBank x where x.ngoId=?1 ", ngoId), parentJSON);
		HNIConverter.convertFoodServiceToJSON(ngoGenericDAO.find(FoodService.class, "select x from FoodService x where x.ngoId=?1 ", ngoId), parentJSON);
		HNIConverter.convertMealDonationToJSON(ngoGenericDAO.find(MealDonationSource.class, "select x from MealDonationSource x where x.ngoId=?1 ", ngoId), parentJSON);
		HNIConverter.convertMealFundingToJSON(ngoGenericDAO.find(MealFundingSource.class, "select x from MealFundingSource x where x.ngoId=?1 ", ngoId), parentJSON);
		HNIConverter.convertFundingSourceToJSON(ngoGenericDAO.find(NgoFundingSource.class, "select x from NgoFundingSource x where x.ngoId=?1 ", ngoId), parentJSON);
		
		return parentJSON;
	}
	
	@Override
	public List<NgoBasicDto> getAllNgo() {
		return ngoGenericDAO.getAllNgo();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class )
	public Map<String,String> clientSave(Client client, User user) {
		Client extClient = clientDAO.getByUserId(user.getId());
		client.setUserId(user.getId());
		Map<String, String> error = new HashMap<>();
		
		if (extClient == null) {
			client.setUserId(user.getId());
			Invitation invitedBy = invitationDAO.getInvitedBy(user.getEmail());
			if (invitedBy != null) {
				client.setCreatedBy(invitedBy.getInvitedBy());
			} else {
				client.setCreatedBy(1L);
			}
			
		} else {
			client.setId(extClient.getId());
			client.setCreatedBy(extClient.getCreatedBy());
		}
		
		HNIValidator.validateClient(client, error);
		HNIConverter.processFoodPreference(client);
		if(error!=null && error.isEmpty()) {
			clientDAO.save(client);
			if (client.getId() != null) {
				ngoGenericDAO.updateStatus(client.getUserId());
			}
			
		}
		return error;
	}

	@Override
	public Map<String, Object> getUserProfiles(String type, Long userId) {
		Long id = findIdByType(userId,type);
		User user = userDao.get(userId);
		
		Map<String,Object> response = new HashMap<>();
		
		if(type!=null && type.equalsIgnoreCase("ngo")){
			ObjectNode ngoInfo = this.getNGODetail(id, user);
			response.put("response", ngoInfo);
		} else if(type.equalsIgnoreCase("Volunteer")){
			Volunteer volunteer = volunteerDao.get(id);
			volunteer.setUser(user);
			volunteer.setAddress(getAddress(user.getAddresses()));
			response.put("response", volunteer);
		} else if(type.equalsIgnoreCase("Client")){
			Client client = clientDAO.get((Object)id.intValue());
			client.setAddress(getAddress(user.getAddresses()));
			client.setUser(user);
			client.setFoodPreferenceList(HNIConverter.getFoodPreferenceList(client.getFoodPreference()));
			response.put("response", client);
		}
		
		return response;
	}
	 private  Long findIdByType(Long userId,String type) {
		 return userDao.findTypeIdByUser(userId, type);
	 }
	 
	 private Address getAddress(Set<Address> userAddresses) {
		 if (userAddresses != null && !userAddresses.isEmpty()) {
			 return userAddresses.iterator().next();
		 } else {
			 return new Address();
		 }
	 }

	@Override
	public Invitation finalizeRegistration(String activationCode) {
		return invitationDAO.updateInvitationStatus(activationCode);
	}

	@Override
	public Map<String, String> saveVolunteerAvailability(ObjectNode availableJSON) {
		Map<String, String> response = new HashMap<>();
		
		int volunteerId = findIdByType(((Long)ThreadContext.get(Constants.USERID)), "volunteer").intValue();
		Volunteer volunteer = volunteerDao.get((long) volunteerId);
		volunteer.setAvailable(availableJSON.get("available").asBoolean());
		volunteerDao.update(volunteer);
		
		List<VolunteerAvailability> volunteerAvailabilities = volunteerAvailabilityDAO.getVolunteerAvailabilityByVolunteerId(volunteerId);
		if(!volunteerAvailabilities.isEmpty()){
			for (VolunteerAvailability volunteerAvailability : volunteerAvailabilities) {
				if(volunteerAvailability.getTimeline()==1 && availableJSON.has("shiftOne")){
					volunteerAvailability.setWeekday(jsonArrayToCommaSeperatedString((ArrayNode) availableJSON.get("shiftOne")));
					volunteerAvailabilityDAO.update(volunteerAvailability);
				}
				else if(volunteerAvailability.getTimeline()==2 && availableJSON.has("shiftTwo")){
					volunteerAvailability.setWeekday(jsonArrayToCommaSeperatedString((ArrayNode) availableJSON.get("shiftTwo")));
					volunteerAvailabilityDAO.update(volunteerAvailability);
				}
				else if(volunteerAvailability.getTimeline()==3 && availableJSON.has("shiftThree")){
					volunteerAvailability.setWeekday(jsonArrayToCommaSeperatedString((ArrayNode) availableJSON.get("shiftThree")));
					volunteerAvailabilityDAO.update(volunteerAvailability);
				}
				else if(volunteerAvailability.getTimeline()==4 && availableJSON.has("shiftFour")){
					volunteerAvailability.setWeekday(jsonArrayToCommaSeperatedString((ArrayNode) availableJSON.get("shiftFour")));
					volunteerAvailabilityDAO.update(volunteerAvailability);
				}
				else if(volunteerAvailability.getTimeline()==5 && availableJSON.has("shiftFour")){
					volunteerAvailability.setWeekday(jsonArrayToCommaSeperatedString((ArrayNode) availableJSON.get("shiftFive")));
					volunteerAvailabilityDAO.update(volunteerAvailability);
				}
				else if(volunteerAvailability.getTimeline()==6 && availableJSON.has("shiftSix")){
					volunteerAvailability.setWeekday(jsonArrayToCommaSeperatedString((ArrayNode) availableJSON.get("shiftSix")));
					volunteerAvailabilityDAO.update(volunteerAvailability);
				}
				else if(volunteerAvailability.getTimeline()==7 && availableJSON.has("shiftSeven")){
					volunteerAvailability.setWeekday(jsonArrayToCommaSeperatedString((ArrayNode) availableJSON.get("shiftSeven")));
					volunteerAvailabilityDAO.update(volunteerAvailability);
				}
			}
		}
		else{
			if(availableJSON.has("shiftOne")){
				saveVolunteerAvailability(1, jsonArrayToCommaSeperatedString((ArrayNode) availableJSON.get("shiftOne")));
			}
			if(availableJSON.has("shiftTwo")){
				saveVolunteerAvailability(2, jsonArrayToCommaSeperatedString((ArrayNode) availableJSON.get("shiftTwo")));
			}
			if(availableJSON.has("shiftThree")){
				saveVolunteerAvailability(3, jsonArrayToCommaSeperatedString((ArrayNode) availableJSON.get("shiftThree")));
			}
			if(availableJSON.has("shiftFour")){
				saveVolunteerAvailability(4, jsonArrayToCommaSeperatedString((ArrayNode) availableJSON.get("shiftFour")));
			}
			if(availableJSON.has("shiftFive")){
				saveVolunteerAvailability(5, jsonArrayToCommaSeperatedString((ArrayNode) availableJSON.get("shiftFive")));
			}
			if(availableJSON.has("shiftSix")){
				saveVolunteerAvailability(6, jsonArrayToCommaSeperatedString((ArrayNode) availableJSON.get("shiftSix")));
			}
			if(availableJSON.has("shiftSeven")){
				saveVolunteerAvailability(7, jsonArrayToCommaSeperatedString((ArrayNode) availableJSON.get("shiftSeven")));
			}
		}
		
		response.put("response", "success");
		return response;
	}
	
	private void saveVolunteerAvailability(int timeline, String availableDays) {
		VolunteerAvailability volunteerAvailability = new VolunteerAvailability();
		volunteerAvailability.setTimeline(timeline);
		volunteerAvailability.setWeekday(availableDays);
		volunteerAvailability.setVolunteerId(findIdByType(((Long)ThreadContext.get(Constants.USERID)), "volunteer").intValue());
		volunteerAvailability.setCreatedBy(((Long)ThreadContext.get(Constants.USERID)).intValue());
		volunteerAvailability.setCreated(new Date());
		volunteerAvailabilityDAO.save(volunteerAvailability);
	}
	 
	 private String jsonArrayToCommaSeperatedString(ArrayNode jsonArray){
		 StringBuilder value = new StringBuilder("");
		 if(jsonArray != null && jsonArray.size()>0){
			for (JsonNode jsonNode : jsonArray) {
				value.append(jsonNode.asText());
				value.append(",");
			}
			return value.substring(0, value.length()-1);
		 }
		 return value.toString();
	 }

	@Override
	public ObjectNode getVolunteerAvailability(Long userId) {
		if(userId>0){
			int volunteerId = findIdByType(((Long)ThreadContext.get(Constants.USERID)), "volunteer").intValue();
			
			List<VolunteerAvailability> volunteerAvailabilities = volunteerAvailabilityDAO.getVolunteerAvailabilityByVolunteerId(volunteerId);
			ObjectNode availableJSON = new ObjectMapper().createObjectNode();
			
			Volunteer volunteer = volunteerDao.get((long) volunteerId);
			availableJSON.put("available", volunteer.getAvailable());
			
			for (VolunteerAvailability volunteerAvailability : volunteerAvailabilities) {
				if(volunteerAvailability.getTimeline()==1){
					availableJSON.set("shiftOne", commaSeperatedStringToJSONArray(volunteerAvailability.getWeekday()));
				}
				if(volunteerAvailability.getTimeline()==2){
					availableJSON.set("shiftTwo", commaSeperatedStringToJSONArray(volunteerAvailability.getWeekday()));
				}
				if(volunteerAvailability.getTimeline()==3){
					availableJSON.set("shiftThree", commaSeperatedStringToJSONArray(volunteerAvailability.getWeekday()));
				}
				if(volunteerAvailability.getTimeline()==4){
					availableJSON.set("shiftFour", commaSeperatedStringToJSONArray(volunteerAvailability.getWeekday()));
				}
				if(volunteerAvailability.getTimeline()==5){
					availableJSON.set("shiftFive", commaSeperatedStringToJSONArray(volunteerAvailability.getWeekday()));
				}
				if(volunteerAvailability.getTimeline()==6){
					availableJSON.set("shiftSix", commaSeperatedStringToJSONArray(volunteerAvailability.getWeekday()));
				}
				if(volunteerAvailability.getTimeline()==7){
					availableJSON.set("shiftSeven", commaSeperatedStringToJSONArray(volunteerAvailability.getWeekday()));
				}
			}
			return availableJSON;
		}
		return null;
	}
	
	private ArrayNode commaSeperatedStringToJSONArray(String value){
		ArrayNode arrayNode = new ObjectMapper().createArrayNode();
		if(!value.isEmpty()){
			if(value.contains(",")){
				String[] strArray = value.split(",");
				for (int i = 0; i < strArray.length; i++) {
					arrayNode.add(strArray[i]);
				}
			}
			else{
				arrayNode.add(value);
			}
		}
		return arrayNode;
	}
	
	@Override
	public Invitation createInvitation(Invitation invite) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, 5);
		invite.setExpirationDate(cal.getTime());
		
		String UUID = HNIUtils.getUUID();
		invite.setInvitationCode(UUID);
		
		invite.setCreatedDate(new Date());
		invite.setActivated(0);
		invite.setPhone(HNIConverter.convertPhoneNumberFromUiFormat(invite.getPhone()));
		invitationDAO.save(invite);
		
		
		return invite;
	}

	@Override
	public User getUserByEmail(String email) {
		return userDao.byEmailAddress(email);
	}


	@Override
	public String isValidInvitation(String phoneNumber) {
		List<Invitation> invitations = userOnboardDao.isValidInvitation(phoneNumber);
		if(invitations == null || invitations.isEmpty()){
			return "Unrecognized number! Please register with hungernotimpossible.";
		}else if(invitations.get(0).getActivated() == 1){
			return "You have already registered! Please respond with HUNGRY to place an order.";
		}
		return null;
	}

	@Override
	public Invitation getInvitationByPhoneNumber(String phoneNumber) {
		List<Invitation> invitations = userOnboardDao.isValidInvitation(phoneNumber);
		return invitations != null && !invitations.isEmpty() ? invitations.get(0) : null;
	}
}
