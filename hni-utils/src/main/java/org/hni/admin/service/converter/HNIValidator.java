package org.hni.admin.service.converter;

import java.util.List;
import java.util.Map;

import org.hni.common.om.FoodBank;
import org.hni.common.om.FoodService;
import org.hni.common.om.MealDonationSource;
import org.hni.common.om.MealFundingSource;
import org.hni.common.om.NgoFundingSource;
import org.hni.user.om.Address;
import org.hni.user.om.BoardMember;
import org.hni.user.om.BrandPartner;
import org.hni.user.om.Client;
import org.hni.user.om.LocalPartner;
import org.hni.user.om.Ngo;
import org.hni.user.om.Volunteer;

public class HNIValidator {
	/**
	 * Validation logic of NGO
	 * 
	 * @param ngo
	 * @param errors
	 * @return
	 */
	public static Map<String, String> validateNgo(Ngo ngo, Map<String, String> errors) {

		if (ngo.getWebsite() == null) {
			errors.put("Ngo -> website", "cannot be null");
		}
		if (ngo.getOverview() == null) {
			errors.put("Ngo -> overview", "cannot be null");
		}
		if (ngo.getMission() == null) {
			errors.put("Ngo -> mission", "cannot be null");
		}
		if (ngo.getMonthlyBudget() == null) {
			errors.put("Ngo -> monthlyBudget", "cannot be null");
		}
		if (ngo.getFoodStampAssist() == null) {
			errors.put("Ngo -> foodStampAssist", "cannot be null");
		}
		if (ngo.getFoodBank() == null) {
			errors.put("Ngo -> foodBank", "cannot be null");
		}
	
		if (ngo.getIndividualsServedDaily() == null) {
			errors.put("Ngo -> individualsServedDaily", "cannot be null");
		}
		if (ngo.getIndividualsServedMonthly() == null) {
			errors.put("Ngo -> individualsServedMonthly", "cannot be null");
		}
		if (ngo.getIndividualsServedAnnually() == null) {
			errors.put("Ngo -> individualsServedAnnually", "cannot be null");
		}
		if (ngo.getClientInfo() == null) {
			errors.put("Ngo -> clientInfo", "cannot be null");
		}
		if (ngo.getClientsUnSheltered() == null) {
			errors.put("Ngo -> clientsUnSheltered", "cannot be null");
		}
		if (ngo.getClientsEmployed() == null) {
			errors.put("Ngo -> clientsEmployed", "cannot be null");
		}
		
		if (ngo.getContactName() == null) {
			errors.put("Ngo -> ContactName", "cannot be null");
		}


		return errors;

	}

	/**
	 * Validation logic for BoardMember
	 * 
	 * @param boardMembers
	 * @param errors
	 * @return
	 */
	public static Map<String, String> validateBoardMembers(List<BoardMember> boardMembers, Map<String, String> errors) {
		boardMembers.forEach(b -> {

			if (b.getFirstName() == null) {
				errors.put("boardMember -> firstName", "cannot be null");
			}
			if (b.getCompany() == null) {
				errors.put("boardMember -> company", "cannot be null");
			}
		});

		return errors;

	}

	/**
	 * Validation logic for BrandPartner
	 * 
	 * @param brandPartners
	 * @param errors
	 * @return
	 */
	public static Map<String, String> validateBrandPartners(List<BrandPartner> brandPartners,
			Map<String, String> errors) {

		brandPartners.forEach(b -> {

			if (b.getPhone() == null) {
				errors.put("brandPartner -> phone", "Cannot be null");
			}
			if (b.getCompany() == null) {
				errors.put("brandPartner -> company", "Cannot be null");
			}

		});
		return errors;
	}

	/**
	 * Validation logic for LocalPartner
	 * 
	 * @param localPartners
	 * @param errors
	 * @return
	 */
	public static Map<String, String> validateLocalPartners(List<LocalPartner> localPartners,
			Map<String, String> errors) {

		localPartners.forEach(b -> {
			if (b.getPhone() == null) {
				errors.put("localPartner -> phone", "Cannot be null");
			}
			if (b.getCompany() == null) {
				errors.put("localPartner -> company", "Cannot be null");
			}

		});
		return errors;
	}

	/**
	 * Validation logic for FoodBank
	 * 
	 * @param FoodBank
	 * @param errors
	 * @return
	 */
	public static Map<String, String> validateFoodBank(List<FoodBank> FoodBank, Map<String, String> errors) {

		FoodBank.forEach(b -> {
			if (b.getFoodBankName() == null) {
				errors.put("foodBank -> foodBankName", "Cannot be null");
			}

		});
		return errors;
	}

	/**
	 * Validation logic for FoodService
	 * 
	 * @param FoodServices
	 * @param errors
	 * @return
	 */
	public static Map<String, String> validateFoodServices(List<FoodService> FoodServices, Map<String, String> errors) {

		FoodServices.forEach(b -> {
			if (b.getServiceType() == null) {
				errors.put("foodService -> serviceType", "Cannot be null");
			}
			if (b.getTotalCount() == null) {
				errors.put("foodService -> totalCount", "Cannot be null");
			}

		});
		return errors;
	}

	/**
	 * Validation logic for MealDonationSources
	 * 
	 * @param MealDonationSources
	 * @param errors
	 * @return
	 */
	public static Map<String, String> validateMealDonationSources(List<MealDonationSource> MealDonationSources,
			Map<String, String> errors) {

		MealDonationSources.forEach(b -> {
			if (b.getSource() == null) {
				errors.put("mealDonationSource -> source", "Cannot be null");
			}
			if (b.getFrequency() == null) {
				errors.put("mealDonationSource -> frequency", "Cannot be null");
			}

		});
		return errors;
	}

	/**
	 * Validation logic for MealFundingSource
	 * 
	 * @param MealFundingSources
	 * @param errors
	 * @return
	 */
	public static Map<String, String> validateMealFundingSources(List<MealFundingSource> MealFundingSources,
			Map<String, String> errors) {

		MealFundingSources.forEach(b -> {
			if (b.getAmount() == null) {
				errors.put("mealFundingSource -> amount", "Cannot be null");
			}
			if (b.getSource() == null) {
				errors.put("mealFundingSource -> source", "Cannot be null");
			}

		});
		return errors;
	}

	/**
	 * Validation logic for NgoFundingSource
	 * 
	 * @param NgoFundingSources
	 * @param errors
	 * @return
	 */
	public static Map<String, String> validateNgoFundingSources(List<NgoFundingSource> NgoFundingSources,
			Map<String, String> errors) {

		NgoFundingSources.forEach(b -> {
			if (b.getAmount() == null) {
				errors.put("ngoFundingSource -> amount", "Cannot be null");
			}
			if (b.getSource() == null) {
				errors.put("ngoFundingSource -> source", "Cannot be null");
			}

		});
		return errors;
	}
	
	public static Map<String,String> validateVolunteer(Volunteer volunteer, Map<String, String> error){
		
		validateAddress(volunteer.getAddress(), error);
		if(volunteer.getBirthday()==null){
			error.put("Volunteer Birthday", "Cannot be null");
		}
		if(volunteer.getEducation() == null){
			volunteer.setEducation(0L);
		}
		if(volunteer.getEmployer() == null){
			volunteer.setEmployer("Not Specified");
		}
		if(volunteer.getIncome() == null){
			volunteer.setIncome(0L);
		}
		if(volunteer.getKids()==null){
			volunteer.setKids(0L);
		}
		if(volunteer.getMaritalStatus() == null){
			volunteer.setMaritalStatus(1l);
		}
		if(volunteer.getNonProfit() == null){
			volunteer.setNonProfit("Y");
		}
		if(volunteer.getRace() == null){
			volunteer.setRace(2l);
		}
		if(volunteer.getSex() == null){
			volunteer.setSex("O");
		}
		
		return error;
	}
	
	public static Map<String,String> validateAddress(Address address, Map<String, String> error){
		if(address.getName()==null){
			error.put("Address Name", "Cannot be null");
		}
		if(address.getAddress1()==null){
			error.put("Address address1","Cannot be null");
		}
		if(address.getCity()==null){
			error.put("Address city","Cannot be null");
		}
		if(address.getState()==null || address.getState().length() >2){
			error.put("Address state","Invalid entry");
		}
		if(address.getZip()==null){
			error.put("Address zip","Cannot be null");
		}
		
		return error;
	}

	public static Map<String,String> validateClient(Client client, Map<String,String> error){
		if(client.getUserId()==null){
			error.put("Client userID", "Cannot be null");
		}
		if(client.getCreatedBy()==null){
			error.put("Client createdBy", "Cannot be null");
		}
		if(client.getRace()==null){
			client.setRace(2L);
		}
		
		if (client.getEthnicity() == null) {
			client.setEthnicity(0);
		}
		
		return error;
	}
}
