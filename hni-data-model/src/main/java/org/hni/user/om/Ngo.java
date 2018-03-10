package org.hni.user.om;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hni.common.om.Persistable;

@Entity
@Table(name = "ngo")
public class Ngo implements Persistable, Serializable {

	private static final long serialVersionUID = 7553475738921092329L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Column(name = "user_id")
	private Long userId;

	@Column(name = "website")
	private String website;

	@Column(name = "fte")
	private Integer fte;
	 
	@Column(name = "overview")
	private String overview;
	
	@Column(name = "contact_name")
	private String contactName;

	@Column(name = "mission")
	private String mission;

	@Column(name = "monthly_budget")
	private Integer monthlyBudget;

	@Column(name = "operating_cost")
	private Integer operatingCost;

	@Column(name = "personal_cost")
	private Integer personalCost;
	
	@Column(name = "kitchen_volunteers")
	private Integer kitchenVolunteers;
	
	@Column(name = "food_stamp_assist")
	private Integer foodStampAssist;
	
	@Column(name = "food_bank")
	private Integer foodBank;
	
	@Column(name = "resources_to_clients")
	private String resourcesToClients;
	
	@Column(name = "ind_serv_daily")
	private Integer individualsServedDaily;
	
	@Column(name = "ind_serv_monthly")
	private Integer individualsServedMonthly;
	
	@Column(name = "ind_serv_annual")
	private Integer individualsServedAnnually;
	
	@Column(name = "client_info")
	private Integer clientInfo;
	
	@Column(name = "store_client_info")
	private String storeClientInfo;
	
	@Column(name = "clients_unsheltered")
	private Integer clientsUnSheltered;
	
	@Column(name = "clients_employed")
	private Integer clientsEmployed;
	
	@Column(name = "created")
	private Date created;
	
	@Column(name = "created_by")
	private Long createdBy;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public Integer getFte() {
		return fte;
	}

	public void setFte(Integer fte) {
		this.fte = fte;
	}

	public String getOverview() {
		return overview;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}

	public String getMission() {
		return mission;
	}

	public void setMission(String mission) {
		this.mission = mission;
	}

	public Integer getMonthlyBudget() {
		return monthlyBudget;
	}

	public void setMonthlyBudget(Integer monthlyBudget) {
		this.monthlyBudget = monthlyBudget;
	}

	public Integer getOperatingCost() {
		return operatingCost;
	}

	public void setOperatingCost(Integer operatingCost) {
		this.operatingCost = operatingCost;
	}

	public Integer getPersonalCost() {
		return personalCost;
	}

	public void setPersonalCost(Integer personalCost) {
		this.personalCost = personalCost;
	}

	public Integer getKitchenVolunteers() {
		return kitchenVolunteers;
	}

	public void setKitchenVolunteers(Integer kitchenVolunteers) {
		this.kitchenVolunteers = kitchenVolunteers;
	}

	public Integer getFoodStampAssist() {
		return foodStampAssist;
	}

	public void setFoodStampAssist(Integer foodStampAssist) {
		this.foodStampAssist = foodStampAssist;
	}

	public Integer getFoodBank() {
		return foodBank;
	}

	public void setFoodBank(Integer foodBank) {
		this.foodBank = foodBank;
	}

	public String getResourcesToClients() {
		return resourcesToClients;
	}

	public void setResourcesToClients(String resourcesToClients) {
		this.resourcesToClients = resourcesToClients;
	}

	public Integer getIndividualsServedDaily() {
		return individualsServedDaily;
	}

	public void setIndividualsServedDaily(Integer individualsServedDaily) {
		this.individualsServedDaily = individualsServedDaily;
	}

	public Integer getIndividualsServedMonthly() {
		return individualsServedMonthly;
	}

	public void setIndividualsServedMonthly(Integer individualsServedMonthly) {
		this.individualsServedMonthly = individualsServedMonthly;
	}

	public Integer getIndividualsServedAnnually() {
		return individualsServedAnnually;
	}

	public void setIndividualsServedAnnually(Integer individualsServedAnnually) {
		this.individualsServedAnnually = individualsServedAnnually;
	}

	public Integer getClientInfo() {
		return clientInfo;
	}

	public void setClientInfo(Integer clientInfo) {
		this.clientInfo = clientInfo;
	}

	public String getStoreClientInfo() {
		return storeClientInfo;
	}

	public void setStoreClientInfo(String storeClientInfo) {
		this.storeClientInfo = storeClientInfo;
	}

	public Integer getClientsUnSheltered() {
		return clientsUnSheltered;
	}

	public void setClientsUnSheltered(Integer clientsUnSheltered) {
		this.clientsUnSheltered = clientsUnSheltered;
	}

	public Integer getClientsEmployed() {
		return clientsEmployed;
	}

	public void setClientsEmployed(Integer clientsEmployed) {
		this.clientsEmployed = clientsEmployed;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

}
