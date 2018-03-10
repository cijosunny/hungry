package org.hni.admin.service.converter;

import static org.hni.common.Constants.AMOUNT;
import static org.hni.common.Constants.BAGGED_CHK;
import static org.hni.common.Constants.BAGGED_QTY;
import static org.hni.common.Constants.BOARD_MEMBERS;
import static org.hni.common.Constants.BRAND_PARTNERS;
import static org.hni.common.Constants.BRKFST_AVAILABILTY;
import static org.hni.common.Constants.BRKFST_CHK;
import static org.hni.common.Constants.BRKFST_QTY;
import static org.hni.common.Constants.CLIENT_NODE;
import static org.hni.common.Constants.COMPANY;
import static org.hni.common.Constants.CONTACT_PERSON;
import static org.hni.common.Constants.DINNER_AVAILABILTY;
import static org.hni.common.Constants.DINNER_CHK;
import static org.hni.common.Constants.DINNER_QTY;
import static org.hni.common.Constants.EMPLOYEED_CLIENT_PERCENTAGE;
import static org.hni.common.Constants.FOOD_BANK_SELECT;
import static org.hni.common.Constants.FOOD_BANK_VALUE;
import static org.hni.common.Constants.FOOD_STAMP;
import static org.hni.common.Constants.FREQUENCY;
import static org.hni.common.Constants.FUNDING;
import static org.hni.common.Constants.FUNDING_SOURCE;
import static org.hni.common.Constants.INDIVIDUALS_SERVED_ANNUALLY;
import static org.hni.common.Constants.INDIVIDUALS_SERVED_DAILY;
import static org.hni.common.Constants.INDIVIDUALS_SERVED_MONTHLY;
import static org.hni.common.Constants.INDIVIDUAL_CLIENT_INFO_COLLECTED;
import static org.hni.common.Constants.LOCAL_PARTNERS;
import static org.hni.common.Constants.LUNCH_AVAILABILTY;
import static org.hni.common.Constants.LUNCH_CHK;
import static org.hni.common.Constants.LUNCH_QTY;
import static org.hni.common.Constants.MEAL_DONATION;
import static org.hni.common.Constants.MEAL_FUNDING;
import static org.hni.common.Constants.MISSION;
import static org.hni.common.Constants.MONTHLY_BUDGET;
import static org.hni.common.Constants.NAME;
import static org.hni.common.Constants.OPERATING_COST;
import static org.hni.common.Constants.OVERVIEW;
import static org.hni.common.Constants.PERSONAL_COST;
import static org.hni.common.Constants.PHONE_NUMBER;
import static org.hni.common.Constants.SERVICE;
import static org.hni.common.Constants.SOURCE;
import static org.hni.common.Constants.STAKE_HOLDER;
import static org.hni.common.Constants.UNSHELTERED_CLIENT_PERCENTAGE;
import static org.hni.common.Constants.VOLUNTEER_NBR;
import static org.hni.common.Constants.WEBSITE;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.shiro.util.ThreadContext;
import org.hni.admin.service.dto.HniServicesDto;
import org.hni.common.Constants;
import org.hni.common.om.FoodBank;
import org.hni.common.om.FoodService;
import org.hni.common.om.MealDonationSource;
import org.hni.common.om.MealFundingSource;
import org.hni.common.om.NgoFundingSource;
import org.hni.organization.om.HniServices;
import org.hni.user.om.Address;
import org.hni.user.om.BoardMember;
import org.hni.user.om.BrandPartner;
import org.hni.user.om.Client;
import org.hni.user.om.Endrosement;
import org.hni.user.om.LocalPartner;
import org.hni.user.om.Ngo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class HNIConverter {

	private static final String RESOURCE = "resource";

	private static final String MEAL_QTY = "mealQty";

	private static final String PROMOTERS = "promoters";

	private static final String EMPLOYEES = "employees";

	private static final String STORE_CLIENT_INFO = "storeClientInfo";

	private static final Logger logger = LoggerFactory.getLogger(HNIConverter.class);

	public static final String USERID = "userId";

	private static ObjectMapper mapper = new ObjectMapper();
	
	public static String convertJSONArrayToString(ArrayNode jsonArray){
		StringBuilder stringBuilder = new StringBuilder("");
		jsonArray.forEach(b -> {
			stringBuilder.append(b.asText());
			stringBuilder.append(",");
		});
		return stringBuilder.substring(0, stringBuilder.length()-1).trim();
	}

	public static Collection<HniServicesDto> convertToServiceDtos(Collection<HniServices> hniServices) {
		Collection<HniServicesDto> hniServicesDtoList = new ArrayList<>();
		for (HniServices hniService : hniServices) {
			HniServicesDto hniServiceDto = new HniServicesDto();
			hniServiceDto.setServiceName(hniService.getServiceName());
			hniServiceDto.setServicePath(hniService.getServicePath());
			hniServiceDto.setServiceImg(hniService.getServiceImg());
			hniServiceDto.setActive(hniService.getActive());

			hniServicesDtoList.add(hniServiceDto);
		}
		return hniServicesDtoList;
	}

	/**
	 * Method For Building NGO object from JSON
	 * 
	 * @param objectNode
	 * @return
	 */
	public static Ngo getNGOFromJson(ObjectNode objectNode) {
		JsonNode overviewNode = objectNode.get(OVERVIEW);
		JsonNode serviceNode = objectNode.get(SERVICE);
		JsonNode clientNode = objectNode.get(CLIENT_NODE);

		Ngo ngo = new Ngo();
		ngo.setUserId((Long) ThreadContext.get(Constants.USERID));
		
		ngo.setWebsite(overviewNode.has(WEBSITE) ? overviewNode.get(WEBSITE).asText() : "");
		ngo.setFte(overviewNode.has(EMPLOYEES) ? overviewNode.get(EMPLOYEES).asInt() : 0);
		ngo.setOverview(overviewNode.has(OVERVIEW) ? overviewNode.get(OVERVIEW).asText(): "");
		ngo.setMission(overviewNode.has(MISSION) ? overviewNode.get(MISSION).asText(): "");
		ngo.setContactName(overviewNode.has(CONTACT_PERSON) ? overviewNode.get(CONTACT_PERSON).asText(): "");
		
		ngo.setMonthlyBudget(serviceNode.has(MONTHLY_BUDGET) ? serviceNode.get(MONTHLY_BUDGET).asInt() : 0);
		ngo.setOperatingCost(serviceNode.has(OPERATING_COST) ? serviceNode.get(OPERATING_COST).asInt() : 0);
		ngo.setPersonalCost(serviceNode.has(PERSONAL_COST) ? serviceNode.get(PERSONAL_COST).asInt() : 0);
		ngo.setKitchenVolunteers(serviceNode.has(VOLUNTEER_NBR) ? serviceNode.get(VOLUNTEER_NBR).asInt() : 0);
		ngo.setFoodStampAssist(serviceNode.has(FOOD_STAMP) ? serviceNode.get(FOOD_STAMP).asInt() : 0);
		ngo.setFoodBank(serviceNode.has(FOOD_BANK_SELECT) ? serviceNode.get(FOOD_BANK_SELECT).asInt() : 0);

		//ngo.setResourcesToClients(1);
		ngo.setResourcesToClients(getResourceToClients(serviceNode));
		ngo.setIndividualsServedDaily(clientNode.has(INDIVIDUALS_SERVED_DAILY) ? clientNode.get(INDIVIDUALS_SERVED_DAILY).asInt() : 0);
		ngo.setIndividualsServedMonthly(clientNode.has(INDIVIDUALS_SERVED_MONTHLY) ? clientNode.get(INDIVIDUALS_SERVED_MONTHLY).asInt() :0);
		ngo.setIndividualsServedAnnually(clientNode.has(INDIVIDUALS_SERVED_ANNUALLY) ? clientNode.get(INDIVIDUALS_SERVED_ANNUALLY).asInt() :0);
		ngo.setClientInfo(clientNode.has(INDIVIDUAL_CLIENT_INFO_COLLECTED) ? clientNode.get(INDIVIDUAL_CLIENT_INFO_COLLECTED).asInt() : 0) ;
		
		ngo.setStoreClientInfo(clientNode.has(STORE_CLIENT_INFO) ? clientNode.get(STORE_CLIENT_INFO).asText() : "");
		ngo.setClientsUnSheltered(clientNode.has(UNSHELTERED_CLIENT_PERCENTAGE) ? clientNode.get(UNSHELTERED_CLIENT_PERCENTAGE).asInt() : 0);
		ngo.setClientsEmployed(clientNode.has(EMPLOYEED_CLIENT_PERCENTAGE) ? clientNode.get(EMPLOYEED_CLIENT_PERCENTAGE).asInt() : 0);
		ngo.setCreated(new Date());
		ngo.setCreatedBy((Long) ThreadContext.get(Constants.USERID));
		
		

		return ngo;
	}

	private static String getResourceToClients(JsonNode serviceNode) {
		String resource = null;
		if (serviceNode.has(RESOURCE) && serviceNode.get(RESOURCE).isArray()) {
			Iterator<JsonNode> resourceIterator = serviceNode.get(RESOURCE).iterator();
			while(resourceIterator.hasNext()) {
				String temp = resourceIterator.next().asText();
				if (resource == null) {
					resource = temp;
				} else {
					resource += "," + temp;
				}
			}
		} 
		return resource;
	}
	
	private static ArrayNode setResourceToClients(ArrayNode resourceArrayNode, String resources) {
		
		if (resources != null) {
			String[] resourceArray = resources.split(",");
			for (String res : resourceArray) {
				resourceArrayNode.add(res);
			}
		}
		
		return resourceArrayNode;
	}

	/**
	 * Method For Building BoardMember from JSON
	 * 
	 * @param objectNode
	 * @param ngoId
	 * @return
	 */
	public static List<BoardMember> getBoardMembersFromJson(ObjectNode objectNode, Long ngoId) {
		List<BoardMember> boardMembers = new ArrayList<>();
		if(objectNode.has(STAKE_HOLDER)){
			if(objectNode.get(STAKE_HOLDER).has(BOARD_MEMBERS)){
				JsonNode boardMemberArray = objectNode.get(STAKE_HOLDER).get(BOARD_MEMBERS);
				if (boardMemberArray.isArray()) {
					Iterator<JsonNode> boardMemberItr = boardMemberArray.iterator();
					while (boardMemberItr.hasNext()) {
						JsonNode boardMemberJSON = boardMemberItr.next();
		
						BoardMember boardMember = new BoardMember();
						boardMember.setNgoId(ngoId);
						boardMember.setFirstName(boardMemberJSON.get(NAME).asText());
							boardMember.setLastName("");
						boardMember.setCompany(boardMemberJSON.get(COMPANY).asText());
						boardMember.setCreated(new Date());
						boardMember.setCreatedBy((Long) ThreadContext.get(Constants.USERID));
						boardMember.setNgoId(ngoId);
						boardMembers.add(boardMember);
					}
				}
			}
		}
		return boardMembers;
	}

	/**
	 * Method For Building BrandPartners from JSON
	 * 
	 * @param objectNode
	 * @param ngo_id
	 * @return
	 */
	public static List<BrandPartner> getBrandPartnersFromJson(ObjectNode objectNode, Long ngo_id) {
		List<BrandPartner> brandPartners = new ArrayList<>();
		if(objectNode.has(STAKE_HOLDER)){
			if(objectNode.get(STAKE_HOLDER).has(BRAND_PARTNERS)){
				JsonNode brandPartnerArray = objectNode.get(STAKE_HOLDER).get(BRAND_PARTNERS);
				if (brandPartnerArray.isArray()) {
					Iterator<JsonNode> brandPartnerItr = brandPartnerArray.iterator();
					while (brandPartnerItr.hasNext()) {
						JsonNode brandPartnerJSON = brandPartnerItr.next();
		
						BrandPartner brandPartner = new BrandPartner();
						brandPartner.setNgoId(ngo_id);
						if (brandPartnerJSON.has(WEBSITE)) {
							brandPartner.setPhone(String.valueOf(brandPartnerJSON.get(WEBSITE).asText()));
						}
						brandPartner.setCompany(brandPartnerJSON.get(COMPANY).asText());
						brandPartner.setCreated(new Date());
						brandPartner.setCreatedBy((Long) ThreadContext.get(Constants.USERID));
						brandPartners.add(brandPartner);
					}
				}
			}
		}
		return brandPartners;
	}

	/**
	 * Method For Building LocalPartners from JSON
	 * 
	 * @param objectNode
	 * @param ngo
	 *            Id
	 * @return
	 */
	public static List<LocalPartner> getLocalPartnersFromJson(ObjectNode objectNode, Long ngoId) {
		List<LocalPartner> localPartners = new ArrayList<>();
		if(objectNode.has(STAKE_HOLDER)){
		JsonNode localPartnerArray = objectNode.get(STAKE_HOLDER).get(LOCAL_PARTNERS);
		if (localPartnerArray.isArray()) {
			Iterator<JsonNode> localPartnerItr = localPartnerArray.iterator();
			while (localPartnerItr.hasNext()) {
				JsonNode localPartnerJSON = localPartnerItr.next();

				LocalPartner localPartner = new LocalPartner();
				localPartner.setNgoId(ngoId);
				if (localPartnerJSON.has(WEBSITE)) {
					localPartner.setPhone(String.valueOf(localPartnerJSON.get(WEBSITE).asText()));
				}
				localPartner.setCompany(localPartnerJSON.get(COMPANY).asText());
				localPartner.setCreated(new Date());
				localPartner.setCreatedBy((Long) ThreadContext.get(Constants.USERID));
				localPartners.add(localPartner);
			}
		}
		}
		return localPartners;
		
	}

	/**
	 * Method For Building FoodBank from JSON
	 * 
	 * @param objectNode
	 * @param ngoId
	 * @return
	 */
	public static List<FoodBank> getFoodBankFromJson(ObjectNode objectNode, Long ngoId) {
		List<FoodBank> foodBanks = new ArrayList<>();
		if(objectNode.has(SERVICE)){
			JsonNode serviceNode = objectNode.get(SERVICE);
	
			if (serviceNode.has(FOOD_BANK_SELECT) && serviceNode.get(FOOD_BANK_SELECT).asBoolean()) {
				JsonNode foodBankArray = serviceNode.get(FOOD_BANK_VALUE);
				if (foodBankArray.isArray()) {
					Iterator<JsonNode> foodBankItr = foodBankArray.iterator();
					while (foodBankItr.hasNext()) {
						FoodBank foodBank = new FoodBank();
						foodBank.setNgoId(ngoId);
						foodBank.setFoodBankName(foodBankItr.next().asText());
						foodBank.setCreated(new Date());
						foodBank.setCreatedBy((Long) ThreadContext.get(Constants.USERID));
						foodBanks.add(foodBank);
					}
				}
			}
		}
		return foodBanks;
	}

	/**
	 * Method For Building FoodService from JSON
	 * 
	 * @param objectNode
	 * @param ngoId
	 * @return
	 */
	public static List<FoodService> getFoodServicesFromJson(ObjectNode objectNode, Long ngoId) {
		List<FoodService> foodServices = new ArrayList<>();
		JsonNode serviceNode = objectNode.get(SERVICE);

		if (serviceNode.has(BRKFST_CHK) && serviceNode.get(BRKFST_CHK).asBoolean()) {
			FoodService foodService = new FoodService();
			foodService.setNgoId(ngoId);
			foodService.setServiceType(Constants.BREAKFAST_ID);
			foodService.setTotalCount(serviceNode.get(BRKFST_QTY).asLong());
			if(serviceNode.has(BRKFST_AVAILABILTY)){
				foodService.setWeekdays(convertJSONArrayToString((ArrayNode) serviceNode.get(BRKFST_AVAILABILTY)));
			}
				foodService.setOther("");
			foodService.setCreated(new Date());
			foodService.setCreatedBy((Long) ThreadContext.get(Constants.USERID));
			foodServices.add(foodService);
		}
		if (serviceNode.has(LUNCH_CHK) && serviceNode.get(LUNCH_CHK).asBoolean()) {
			FoodService foodService = new FoodService();
			foodService.setNgoId(ngoId);
			foodService.setServiceType(Constants.LUNCH_ID);
			foodService.setTotalCount(serviceNode.get(LUNCH_QTY).asLong());
			if(serviceNode.has(LUNCH_AVAILABILTY)){
				foodService.setWeekdays(convertJSONArrayToString((ArrayNode) serviceNode.get(LUNCH_AVAILABILTY)));
			}
			foodService.setOther("");
			foodService.setCreated(new Date());
			foodService.setCreatedBy((Long) ThreadContext.get(Constants.USERID));
			foodServices.add(foodService);
		}
		if (serviceNode.has(DINNER_CHK) && serviceNode.get(DINNER_CHK).asBoolean()) {
			FoodService foodService = new FoodService();
			foodService.setNgoId(ngoId);
			foodService.setServiceType(Constants.DINNER_ID);
			foodService.setTotalCount(serviceNode.get(DINNER_QTY).asLong());
			if(serviceNode.has(DINNER_AVAILABILTY)){
				foodService.setWeekdays(convertJSONArrayToString((ArrayNode) serviceNode.get(DINNER_AVAILABILTY)));
			}
			foodService.setOther("");
			foodService.setCreated(new Date());
			foodService.setCreatedBy((Long) ThreadContext.get(Constants.USERID));
			foodServices.add(foodService);
		}

		return foodServices;
	}

	/**
	 * Method For Building MealDonationSource from JSON
	 * 
	 * @param objectNode
	 * @param ngoId
	 * @return
	 */
	public static List<MealDonationSource> getMealDonationSourceFromJson(ObjectNode objectNode, Long ngoId) {
		List<MealDonationSource> mealDonationSources = new ArrayList<>();
		if(objectNode.has(FUNDING)){
			if(objectNode.get(FUNDING).has(MEAL_DONATION)){
			JsonNode mealDonationSourceArray = objectNode.get(FUNDING).get(MEAL_DONATION);
			if (mealDonationSourceArray.isArray()) {
				Iterator<JsonNode> mealDonationSourceItr = mealDonationSourceArray.iterator();
				try{
					while (mealDonationSourceItr.hasNext()) {
						JsonNode mealDonationSourceJSON = mealDonationSourceItr.next();
						MealDonationSource mealDonationSource = new MealDonationSource();
						mealDonationSource.setNgoId(ngoId);
						mealDonationSource.setSource(mealDonationSourceJSON.has(SOURCE) ? mealDonationSourceJSON.get(SOURCE).asText() : "");
						mealDonationSource.setFrequency(mealDonationSourceJSON.has(FREQUENCY) ? mealDonationSourceJSON.get(FREQUENCY).asText() : "");
						mealDonationSource.setMealQuantity(mealDonationSourceJSON.has(MEAL_QTY) ? mealDonationSourceJSON.get(MEAL_QTY).asLong() : 0);
						mealDonationSource.setCreated(new Date());
						mealDonationSource.setCreatedBy((Long) ThreadContext.get(Constants.USERID));
						mealDonationSources.add(mealDonationSource);
					}
				
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			}
		}
		return mealDonationSources;
	}

	/**
	 * Method For Building MealFundingSource from JSON
	 * 
	 * @param objectNode
	 * @param ngoId
	 * @return
	 */
	public static List<MealFundingSource> getMealFundingSourcesFromJson(ObjectNode objectNode, Long ngoId) {
		List<MealFundingSource> mealFundingSources = new ArrayList<>();
		if(objectNode.has(FUNDING)){
			if(objectNode.get(FUNDING).has(MEAL_FUNDING)){
				JsonNode mealFundingSourceArray = objectNode.get(FUNDING).get(MEAL_FUNDING);
				if (mealFundingSourceArray.isArray()) {
					Iterator<JsonNode> mealFundingSourceItr = mealFundingSourceArray.iterator();
					while (mealFundingSourceItr.hasNext()) {
						JsonNode mealFundingSourceJSON = mealFundingSourceItr.next();
						MealFundingSource mealFundingSource = new MealFundingSource();
						mealFundingSource.setNgoId(ngoId);
						mealFundingSource.setAmount(mealFundingSourceJSON.get(AMOUNT).asDouble());
						mealFundingSource.setSource(mealFundingSourceJSON.get(SOURCE).asText());
						mealFundingSource.setCreated(new Date());
						mealFundingSource.setCreatedBy((Long) ThreadContext.get(Constants.USERID));
						mealFundingSources.add(mealFundingSource);
					}
				}
			}
		}
		return mealFundingSources;
	}

	/**
	 * Method For Building NgoFundingSource from JSON
	 * 
	 * @param objectNode
	 * @param ngoId
	 * @return
	 */
	public static List<NgoFundingSource> getNgoFundingSourcesFromJson(ObjectNode objectNode, Long ngoId) {
		List<NgoFundingSource> ngoFundingSources = new ArrayList<>();
		if(objectNode.has(FUNDING)){
			if(objectNode.get(FUNDING).has(FUNDING_SOURCE)){
				JsonNode ngoFundingSourceArray = objectNode.get(FUNDING).get(FUNDING_SOURCE);
				if (ngoFundingSourceArray.isArray()) {
					Iterator<JsonNode> ngoFundingSourceItr = ngoFundingSourceArray.iterator();
					while (ngoFundingSourceItr.hasNext()) {
						JsonNode ngoFundingSourceJSON = ngoFundingSourceItr.next();
						NgoFundingSource ngoFundingSource = new NgoFundingSource();
						ngoFundingSource.setNgoId(ngoId);
						ngoFundingSource.setAmount(ngoFundingSourceJSON.get(AMOUNT).asDouble());
						ngoFundingSource.setSource(ngoFundingSourceJSON.get(SOURCE).asText());
						ngoFundingSource.setCreated(new Date());
						ngoFundingSource.setCreatedBy((Long) ThreadContext.get(Constants.USERID));
						ngoFundingSources.add(ngoFundingSource);
					}
				}
			}
		}
		return ngoFundingSources;
	}

	/**
	 * Method For Building NGO object to JSON
	 * 
	 * @param ngo
	 * @param parentJSON
	 * @return
	 */
	public static ObjectNode convertNGOToJSON(Ngo ngo, ObjectNode parentJSON) {
		ObjectNode overview = (ObjectNode) parentJSON.get(OVERVIEW);
		overview.put(WEBSITE, ngo.getWebsite());
		overview.put(OVERVIEW, ngo.getOverview());
		overview.put(MISSION, ngo.getMission());
		
		overview.put(CONTACT_PERSON, ngo.getContactName());
		overview.put(EMPLOYEES, ngo.getFte());
		
		parentJSON.set(OVERVIEW, overview);
        
		ObjectNode service = mapper.createObjectNode();
		service.put(MONTHLY_BUDGET, ngo.getMonthlyBudget());
		service.put(OPERATING_COST, ngo.getOperatingCost());
		service.put(PERSONAL_COST, ngo.getPersonalCost());
		service.put(VOLUNTEER_NBR, ngo.getKitchenVolunteers());
		service.put(FOOD_BANK_SELECT, ngo.getFoodBank());
		service.put(FOOD_STAMP, ngo.getFoodStampAssist());
		service.put(FOOD_BANK_SELECT, ngo.getFoodBank());
		
		ArrayNode resourceArray = service.putArray(RESOURCE);
		
		setResourceToClients(resourceArray, ngo.getResourcesToClients());
		
		
		parentJSON.set(SERVICE, service);

		ObjectNode client = mapper.createObjectNode();
		client.put(INDIVIDUALS_SERVED_DAILY, ngo.getIndividualsServedDaily());
		client.put(INDIVIDUALS_SERVED_MONTHLY, ngo.getIndividualsServedMonthly());
		client.put(INDIVIDUALS_SERVED_ANNUALLY, ngo.getIndividualsServedAnnually());
		client.put(INDIVIDUAL_CLIENT_INFO_COLLECTED, ngo.getClientInfo());
		client.put(UNSHELTERED_CLIENT_PERCENTAGE, ngo.getClientsUnSheltered());
		client.put(EMPLOYEED_CLIENT_PERCENTAGE, ngo.getClientsEmployed());
		client.put(STORE_CLIENT_INFO, ngo.getStoreClientInfo());
		
		parentJSON.set(CLIENT_NODE, client);

		return parentJSON;
	}

	/**
	 * Method For Building BoardMember object to JSON
	 * 
	 * @param ngo
	 * @param parentJSON
	 * @return
	 */
	public static ObjectNode convertBoardMembersToJSON(List<BoardMember> boardMembers, ObjectNode parentJSON) {
		ObjectNode stakeHolderNode = (ObjectNode) parentJSON.get(STAKE_HOLDER);
		ArrayNode boardMemberJSONArray = mapper.createArrayNode();
		boardMembers.forEach(bm -> {
			ObjectNode boardMemberJSON = mapper.createObjectNode();
			boardMemberJSON.put(COMPANY, bm.getCompany());
			boardMemberJSON.put(NAME, bm.getFirstName());
			boardMemberJSONArray.add(boardMemberJSON);
		});
		stakeHolderNode.set(BOARD_MEMBERS, boardMemberJSONArray);
		return parentJSON;
	}

	/**
	 * Method For Building BrandPartner object to JSON
	 * 
	 * @param ngo
	 * @param parentJSON
	 * @return
	 */
	public static ObjectNode convertBrandPartnersToJSON(List<BrandPartner> brandPartners, ObjectNode parentJSON) {
		ObjectNode stakeHolderNode = (ObjectNode) parentJSON.get(STAKE_HOLDER);
		ArrayNode brandPartnerJSONArray = mapper.createArrayNode();
		brandPartners.forEach(bp -> {
			ObjectNode brandPartnerJSON = mapper.createObjectNode();
			brandPartnerJSON.put(COMPANY, bp.getCompany());
			brandPartnerJSON.put(WEBSITE, bp.getPhone());
			brandPartnerJSONArray.add(brandPartnerJSON);
		});
		stakeHolderNode.set(BRAND_PARTNERS, brandPartnerJSONArray);
		return parentJSON;
	}

	/**
	 * Method For Building LocalPartner object to JSON
	 * 
	 * @param ngo
	 * @param parentJSON
	 * @return
	 */
	public static ObjectNode convertLocalPartnerToJSON(List<LocalPartner> localPartners, ObjectNode parentJSON) {
		ObjectNode stakeHolderNode = (ObjectNode) parentJSON.get(STAKE_HOLDER);
		ArrayNode localPartnerJSONArray = mapper.createArrayNode();
		localPartners.forEach(lp -> {
			ObjectNode localPartnerJSON = mapper.createObjectNode();
			localPartnerJSON.put(COMPANY, lp.getCompany());
			localPartnerJSON.put(WEBSITE, lp.getPhone());
			localPartnerJSONArray.add(localPartnerJSON);
		});
		stakeHolderNode.set(LOCAL_PARTNERS, localPartnerJSONArray);
		return parentJSON;
	}


	/**
	 * Method For Building FoodBank object to JSON
	 * 
	 * @param ngo
	 * @param parentJSON
	 * @return
	 */
	public static ObjectNode convertFoodBanksToJSON(List<FoodBank> foodBanks, ObjectNode parentJSON) {
		ObjectNode serviceNode = (ObjectNode) parentJSON.get(SERVICE);
		if (!foodBanks.isEmpty()) {
			serviceNode.put(FOOD_BANK_SELECT, Boolean.TRUE);
			ArrayNode foodBankArray = new ObjectMapper().createArrayNode();
			for (int i = 0; i < foodBanks.size(); i++) {
				foodBankArray.add(foodBanks.get(i).getFoodBankName());
			}
			serviceNode.set(FOOD_BANK_VALUE, foodBankArray);
		}
		return parentJSON;
	}

	/**
	 * Method For Building FoodService object to JSON
	 * 
	 * @param ngo
	 * @param parentJSON
	 * @return
	 */
	public static ObjectNode convertFoodServiceToJSON(List<FoodService> foodServices, ObjectNode parentJSON) {
		ObjectNode serviceNode = (ObjectNode) parentJSON.get(SERVICE);
		foodServices.forEach(fs -> {
			if (fs.getServiceType().equals(Constants.BREAKFAST_ID)) {
				serviceNode.put(BRKFST_CHK, true);
				serviceNode.put(BRKFST_QTY, fs.getTotalCount());
				serviceNode.put(BRKFST_AVAILABILTY, fs.getWeekdays());
			}
			if (fs.getServiceType().equals(Constants.LUNCH_ID)) {
				serviceNode.put(LUNCH_CHK, true);
				serviceNode.put(LUNCH_QTY, fs.getTotalCount());
				serviceNode.put(LUNCH_AVAILABILTY, fs.getWeekdays());
			}
			if (fs.getServiceType().equals(Constants.DINNER_ID)) {
				serviceNode.put(DINNER_CHK, true);
				serviceNode.put(DINNER_QTY, fs.getTotalCount());
				serviceNode.put(DINNER_AVAILABILTY, fs.getWeekdays());
			}
			if (fs.getServiceType().equals(4L)) {
				serviceNode.put(BAGGED_CHK, true);
				serviceNode.put(BAGGED_QTY, fs.getTotalCount());
			}
		});
		return parentJSON;
	}

	/**
	 * Method For Building MealDonationSource object to JSON
	 * 
	 * @param ngo
	 * @param parentJSON
	 * @return
	 */
	public static ObjectNode convertMealDonationToJSON(List<MealDonationSource> mealDonations, ObjectNode parentJSON) {
		ObjectNode fundingJSON = (ObjectNode) parentJSON.get(FUNDING);
		ArrayNode mealDonationJSONArray = mapper.createArrayNode();
		mealDonations.forEach(md -> {
			ObjectNode mealDonationJSON = mapper.createObjectNode();
			mealDonationJSON.put(SOURCE, md.getSource());
			mealDonationJSON.put(FREQUENCY, md.getFrequency());
			mealDonationJSON.put(MEAL_QTY, md.getMealQuantity());
			mealDonationJSONArray.add(mealDonationJSON);
		});

		fundingJSON.set(MEAL_DONATION, mealDonationJSONArray);
		return parentJSON;
	}

	/**
	 * Method For Building MealFundingSource object to JSON
	 * 
	 * @param ngo
	 * @param parentJSON
	 * @return
	 */
	public static ObjectNode convertMealFundingToJSON(List<MealFundingSource> mealFundings, ObjectNode parentJSON) {
		ObjectNode fundingJSON = (ObjectNode) parentJSON.get(FUNDING);
		ArrayNode mealFundingJSONArray = mapper.createArrayNode();
		mealFundings.forEach(mf -> {
			ObjectNode mealFundingJSON = mapper.createObjectNode();
			mealFundingJSON.put(SOURCE, mf.getSource());
			mealFundingJSON.put(AMOUNT, mf.getAmount());
			mealFundingJSONArray.add(mealFundingJSON);
		});

		fundingJSON.set(MEAL_FUNDING, mealFundingJSONArray);
		return parentJSON;
	}

	/**
	 * Method For Building NgoFundingSource object to JSON
	 * 
	 * @param ngo
	 * @param parentJSON
	 * @return
	 */
	public static ObjectNode convertFundingSourceToJSON(List<NgoFundingSource> fundingSources, ObjectNode parentJSON) {
		ObjectNode fundingJSON = (ObjectNode) parentJSON.get(FUNDING);
		ArrayNode fundingSourceJSONArray = mapper.createArrayNode();
		fundingSources.forEach(fs -> {
			ObjectNode fundingSourceJSON = mapper.createObjectNode();
			fundingSourceJSON.put(SOURCE, fs.getSource());
			fundingSourceJSON.put(AMOUNT, fs.getAmount());
			fundingSourceJSONArray.add(fundingSourceJSON);
		});

		fundingJSON.set(FUNDING_SOURCE, fundingSourceJSONArray);
		return parentJSON;
	}
	
	public static ObjectNode getAddress(ObjectNode addressNode, Set<Address> addresses) {
		if (addresses != null && !addresses.isEmpty()) {
			Address address = addresses.iterator().next();
			addressNode.put("address1", address.getAddress1());
			addressNode.put("address2", address.getAddress2());
			addressNode.put("name", address.getName());
			addressNode.put("city", address.getCity());
			addressNode.put("state", address.getState());
			addressNode.put("zip", address.getZip());
		}
		
		return addressNode;
	}

	public static Set<Address> getAddressSet(ObjectNode onboardData) {
		// TODO Auto-generated method stub
		JsonNode jsonNode = onboardData.get(OVERVIEW);
		JsonNode addressNode = jsonNode.get("address");
		Address addr = new Address();
		addr.setAddress1(addressNode.get("address1").asText());
		
		addr.setAddress2(addressNode.has("address2") ? addressNode.get("address2").asText() : "");
		addr.setName(addressNode.get("name").asText());
		addr.setCity(addressNode.get("city").asText());
		addr.setState(addressNode.get("state").asText());
		addr.setZip(addressNode.get("zip").asText());
		
		Set<Address> addresses = new HashSet<>();
		addresses.add(addr);
		
		return addresses;
	}
	
	/**
	 * Method For Building Endrosement from JSON
	 * 
	 * @param objectNode
	 * @param ngoId
	 * @return
	 */
	public static List <Endrosement> getEndrosement(ObjectNode objectNode, Long ngoId){
		List <Endrosement> endrosement =new ArrayList<>();
		JsonNode overview = objectNode.get(OVERVIEW);
		
		if(overview.has(PROMOTERS)){
			JsonNode endrosementArray = overview.get(PROMOTERS);
			if(endrosementArray.isArray()){
				Iterator<JsonNode> endrosementArrayItr = endrosementArray.iterator();
				while(endrosementArrayItr.hasNext()){
					Endrosement endrosmentObject =new Endrosement();
					endrosmentObject.setNgoId(ngoId);
					endrosmentObject.setEndrosement(endrosementArrayItr.next().asText());
					endrosement.add(endrosmentObject);
				}
			}
		}
		return endrosement;
		
	}
	public static ObjectNode convertEndrosementToJSON(List<Endrosement> endrosement, ObjectNode parentJSON) {
		ArrayNode endrosementJSONArray = mapper.createArrayNode();
		endrosement.forEach(es -> {
			endrosementJSONArray.add(es.getEndrosement());
		});

		parentJSON.set(PROMOTERS, endrosementJSONArray);
		return parentJSON;
	}

	public static String convertPhoneNumberToUiFormat(String phone) {
		try {
			if (phone != null) {
				StringBuilder sb = new StringBuilder();
				phone = phone.trim().replaceAll("-", "");
				if (!phone.isEmpty()) {
					sb.append(phone.substring(0, 3));
					sb.append("-");
					sb.append(phone.substring(3, 6));
					sb.append("-");
					sb.append(phone.substring(6));
					return sb.toString();
				}
			} else {
				return phone;
			}
		} catch (Exception e) {
				logger.error("Exception while converting phone number to UI format "+ phone, e);
		}
		
		return phone;
	}
	
	public static String convertPhoneNumberFromUiFormat(String phone) {
		try {
			if (phone != null) {
				return phone.replaceAll("-", "");
			}
		} catch (Exception e) {
			logger.error("Exception while converting phone number from UI format "+ phone, e);
		}
		return  phone;
	}

public static Client processFoodPreference(Client client) {
		
		String foodPreference = null;
		List<Integer> foodPreferenceList = client.getFoodPreferenceList();
		if (foodPreferenceList != null && !foodPreferenceList.isEmpty()) {
			for (Integer preference : foodPreferenceList) {
				if (foodPreference == null) {
					foodPreference = String.valueOf(preference);
				} else {
					foodPreference += "," + preference.toString();
				}
			}
		}
		client.setFoodPreference(foodPreference);
		return client;
	}

public static List<Integer> getFoodPreferenceList(String foodPreference) {
	List<Integer> foodPreferenceList = new ArrayList<Integer>();
	if(foodPreference != null){
		String[] resourceArray = foodPreference.split(",");
		for (String res : resourceArray) {
			foodPreferenceList.add(Integer.parseInt(res));
		}
	}
	return foodPreferenceList;
}
}
