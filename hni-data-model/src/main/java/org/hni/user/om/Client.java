package org.hni.user.om;


import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hni.common.om.Persistable;

@Entity
@Table(name = "client")
public class Client implements Persistable, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	
	transient private Long userId;
	@ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name = "ngo_id", referencedColumnName = "id")
	private Ngo ngo;
	
	@Column(name = "created_by", nullable = false)
	private Long createdBy;
	@Column(name = "race", nullable = false)
	private Long race;
	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;
	transient private Address address;
	@Column(name = "bday")
	private Date bday;
	@Column(name = "age")
	private Integer age;
	@Column(name = "been_arrested", length = 1)
	private boolean beenArrested;
	@Column(name = "been_convicted", length = 1)
	private boolean beenConvicted;
	@Column(name = "has_smart_phone", length = 1)
	private boolean hasSmartPhone;
	@Column(name = "service_provider", length = 50)
	private String serviceProvider;
	@Column(name = "model", length = 50)
	private String model;
	@Column(name = "have_monthly_plan", length = 1)
	private boolean haveMonthlyPlan;
	@Column(name = "monthly_plan_minute", length = 50)
	private String monthlyPlanMinute;
	@Column(name = "monthly_plan_data", length = 50)
	private String monthlyPlanData;
	@Column(name = "monthly_plan_cost", length = 50)
	private String monthlyPlanCost;
	@Column(name = "alt_monthly_plan")
	private Integer altMonthlyPlan;
	@Column(name = "alt_monthly_plan_together", length = 50)
	private String altMonthlyPlanTogether;
	@Column(name = "sliblings")
	private Integer sliblings;
	@Column(name = "kids")
	private Integer kids;
	@Column(name = "live_at_home", length = 1)
	private boolean liveAtHome;
	@Column(name = "sheltered")
	private Boolean sheltered;
	@Column(name = "parent_education")
	private Integer parentEducation;
	@Column(name = "education")
	private Integer education;
	@Column(name = "enrollment_status")
	private Integer enrollmentStatus;
	@Column(name = "enrollment_location", length = 50)
	private String enrollmentLocation;
	@Column(name = "work_status")
	private Integer workStatus;
	@Column(name = "time_to_workplace")
	private Integer timeToWorkplace;
	@Column(name = "no_of_job")
	private Integer noOfJob;
	@Column(name = "employer", length = 50)
	private String employer;
	@Column(name = "job_title", length = 50)
	private String jobTitle;
	@Column(name = "duration_of_employement")
	private Integer durationOfEmployement;
	@Column(name = "unemployment_benfits", length = 1)
	private boolean unemploymentBenfits;
	@Column(name = "reason_unemployment_benefits", length = 100)
	private String reasonUnemploymentBenefits;
	@Column(name = "total_income", precision = 22, scale = 0)
	private Double totalIncome;
	@Column(name = "rate_amount")
	private Integer rateAmount;
	@Column(name = "rate_type")
	private Integer rateType;
	@Column(name = "avg_hours_per_week")
	private String avgHoursPerWeek;
	@Column(name = "resident_status")
	private Integer residentStatus;
	@Column(name = "dollar_spend_food")
	private Integer dollarSpendFood;
	@Column(name = "dollar_spend_clothes")
	private Integer dollarSpendClothes;
	@Column(name = "dollar_spend_entertainment")
	private Integer dollarSpendEntertainment;
	@Column(name = "dollar_spend_transport")
	private Integer dollarSpendTransport;
	@Column(name = "dollar_spend_savings")
	private Integer dollarSpendSavings;
	@Column(name = "meals_per_day")
	private Integer mealsPerDay;
	@Column(name = "food_preference")
	private String foodPreference;
	@Column(name = "food_source", length = 50)
	private String foodSource;
	@Column(name = "cook", length = 1)
	private boolean cook;
	@Column(name = "travel_for_food_distance")
	private Integer travelForFoodDistance;
	@Column(name = "traval_for_food_time")
	private Integer travalForFoodTime;
	@Column(name = "sub_food_program", length = 1)
	private boolean subFoodProgram;
	@Column(name = "sub_food_program_entity", length = 50)
	private String subFoodProgramEntity;
	@Column(name = "sub_food_program_duration")
	private Integer subFoodProgramDuration;
	@Column(name = "sub_food_program_renew")
	private Integer subFoodProgramRenew;
	@Column(name = "sub_food_program_exp", length = 256)
	private String subFoodProgramExp;
	@Column(name = "allergies", length = 256)
	private String allergies;
	@Column(name = "addiction", length = 1)
	private boolean addiction;
	@Column(name = "addiction_type", length = 50)
	private String addictionType;
	@Column(name = "mental_health_issue", length = 1)
	private String mentalHealthIssue;
	@Column(name = "mental_health_issue_history", length = 256)
	private String mentalHealthIssueHistory;
	@Column(name = "height", length = 50)
	private String height;
	@Column(name = "weight", length = 50)
	private String weight;
	@Column(name = "exercise_per_week")
	private Integer exercisePerWeek;
	@Column(name = "last_visit_doctor")
	private Integer lastVisitDoctor;
	@Column(name = "last_visit_dentist")
	private Integer lastVisitDentist;
	@Column(name = "live_with")
	private Integer liveWith;
	@Column(name = "ethinicity")
	private Integer ethnicity;
	@Column(name = "max_order_allowed")
	private Integer maxOrderAllowed;
	@Column(name = "max_meals_allowed_per_day")
	private Integer maxMealsAllowedPerDay;
	
	transient private List<Integer> foodPreferenceList = new ArrayList<>();
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "client",  cascade = { CascadeType.ALL})
	private Set<Dependent> dependents = new HashSet<>(0);
	
	public Client() {
	}

	public Client(Long userId, Long createdBy, Long race) {
		this.userId = userId;
		this.createdBy = createdBy;
		this.race = race;
	}

	public Client(Long userId, Long createdBy, Long race,
			Date bday, Integer age, boolean beenArrested, boolean beenConvicted,
			boolean hasSmartPhone, String serviceProvider, String model,
			boolean haveMonthlyPlan, String monthlyPlanMinute,
			String monthlyPlanData, String monthlyPlanCost,
			Integer altMonthlyPlan, String altMonthlyPlanTogether,
			Integer sliblings, Integer kids, boolean liveAtHome,
			Boolean sheltered, Integer liveWith, Integer parentEducation, Integer education,
			Integer enrollmentStatus, String enrollmentLocation,
			Integer workStatus, Integer timeToWorkplace, Integer noOfJob,
			String employer, String jobTitle, Integer durationOfEmployement,
			boolean unemploymentBenfits, String reasonUnemploymentBenefits,
			Double totalIncome, Integer rateAmount, Integer rateType,
			String avgHoursPerWeek, Integer residentStatus,
			Integer dollarSpendFood, Integer dollarSpendClothes,
			Integer dollarSpendEntertainment, Integer dollarSpendTransport,
			Integer dollarSpendSavings, Integer mealsPerDay,
			String foodPreference, String foodSource, boolean cook,
			Integer travelForFoodDistance, Integer travalForFoodTime,
			boolean subFoodProgram, String subFoodProgramEntity,
			Integer subFoodProgramDuration, Integer subFoodProgramRenew,
			String subFoodProgramExp, String allergies, boolean addiction,
			String addictionType, String mentalHealthIssue,
			String mentalHealthIssueHistory, String height, String weight,
			Integer exercisePerWeek, Integer lastVisitDoctor,
			Integer lastVisitDentist) {
		this.userId = userId;
		this.createdBy = createdBy;
		this.race = race;
		this.bday = bday;
		this.age = age;
		this.beenArrested = beenArrested;
		this.beenConvicted = beenConvicted;
		this.hasSmartPhone = hasSmartPhone;
		this.serviceProvider = serviceProvider;
		this.model = model;
		this.haveMonthlyPlan = haveMonthlyPlan;
		this.monthlyPlanMinute = monthlyPlanMinute;
		this.monthlyPlanData = monthlyPlanData;
		this.monthlyPlanCost = monthlyPlanCost;
		this.altMonthlyPlan = altMonthlyPlan;
		this.altMonthlyPlanTogether = altMonthlyPlanTogether;
		this.sliblings = sliblings;
		this.kids = kids;
		this.liveAtHome = liveAtHome;
		this.sheltered = sheltered;
		this.liveWith = liveWith;
		this.parentEducation = parentEducation;
		this.education = education;
		this.enrollmentStatus = enrollmentStatus;
		this.enrollmentLocation = enrollmentLocation;
		this.workStatus = workStatus;
		this.timeToWorkplace = timeToWorkplace;
		this.noOfJob = noOfJob;
		this.employer = employer;
		this.jobTitle = jobTitle;
		this.durationOfEmployement = durationOfEmployement;
		this.unemploymentBenfits = unemploymentBenfits;
		this.reasonUnemploymentBenefits = reasonUnemploymentBenefits;
		this.totalIncome = totalIncome;
		this.rateAmount = rateAmount;
		this.rateType = rateType;
		this.avgHoursPerWeek = avgHoursPerWeek;
		this.residentStatus = residentStatus;
		this.dollarSpendFood = dollarSpendFood;
		this.dollarSpendClothes = dollarSpendClothes;
		this.dollarSpendEntertainment = dollarSpendEntertainment;
		this.dollarSpendTransport = dollarSpendTransport;
		this.dollarSpendSavings = dollarSpendSavings;
		this.mealsPerDay = mealsPerDay;
		this.foodPreference = foodPreference;
		this.foodSource = foodSource;
		this.cook = cook;
		this.travelForFoodDistance = travelForFoodDistance;
		this.travalForFoodTime = travalForFoodTime;
		this.subFoodProgram = subFoodProgram;
		this.subFoodProgramEntity = subFoodProgramEntity;
		this.subFoodProgramDuration = subFoodProgramDuration;
		this.subFoodProgramRenew = subFoodProgramRenew;
		this.subFoodProgramExp = subFoodProgramExp;
		this.allergies = allergies;
		this.addiction = addiction;
		this.addictionType = addictionType;
		this.mentalHealthIssue = mentalHealthIssue;
		this.mentalHealthIssueHistory = mentalHealthIssueHistory;
		this.height = height;
		this.weight = weight;
		this.exercisePerWeek = exercisePerWeek;
		this.lastVisitDoctor = lastVisitDoctor;
		this.lastVisitDentist = lastVisitDentist;
	}

	
	
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	
	public Long getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	
	public Long getRace() {
		return this.race;
	}

	public void setRace(Long race) {
		this.race = race;
	}
	
	public Address getAddress() {
		return address;
	}
	
	public void setAddress(Address address) {
		this.address = address;
	}

	
	public Date getBday() {
		return this.bday;
	}

	public void setBday(Date bday) {
		this.bday = bday;
	}

	
	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public boolean getBeenArrested() {
		return this.beenArrested;
	}

	public void setBeenArrested(boolean beenArrested) {
		this.beenArrested = beenArrested;
	}

	
	public boolean getBeenConvicted() {
		return this.beenConvicted;
	}

	public void setBeenConvicted(boolean beenConvicted) {
		this.beenConvicted = beenConvicted;
	}

	
	public boolean getHasSmartPhone() {
		return this.hasSmartPhone;
	}

	public void setHasSmartPhone(boolean hasSmartPhone) {
		this.hasSmartPhone = hasSmartPhone;
	}

	
	public String getServiceProvider() {
		return this.serviceProvider;
	}

	public void setServiceProvider(String serviceProvider) {
		this.serviceProvider = serviceProvider;
	}

	
	public String getModel() {
		return this.model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	
	public boolean getHaveMonthlyPlan() {
		return this.haveMonthlyPlan;
	}

	public void setHaveMonthlyPlan(boolean haveMonthlyPlan) {
		this.haveMonthlyPlan = haveMonthlyPlan;
	}


	public String getMonthlyPlanMinute() {
		return this.monthlyPlanMinute;
	}

	public void setMonthlyPlanMinute(String monthlyPlanMinute) {
		this.monthlyPlanMinute = monthlyPlanMinute;
	}

	
	public String getMonthlyPlanData() {
		return this.monthlyPlanData;
	}

	public void setMonthlyPlanData(String monthlyPlanData) {
		this.monthlyPlanData = monthlyPlanData;
	}

	
	public String getMonthlyPlanCost() {
		return this.monthlyPlanCost;
	}

	public void setMonthlyPlanCost(String monthlyPlanCost) {
		this.monthlyPlanCost = monthlyPlanCost;
	}

	
	public Integer getAltMonthlyPlan() {
		return this.altMonthlyPlan;
	}

	public void setAltMonthlyPlan(Integer altMonthlyPlan) {
		this.altMonthlyPlan = altMonthlyPlan;
	}

	
	public String getAltMonthlyPlanTogether() {
		return this.altMonthlyPlanTogether;
	}

	public void setAltMonthlyPlanTogether(String altMonthlyPlanTogether) {
		this.altMonthlyPlanTogether = altMonthlyPlanTogether;
	}

	
	public Integer getSliblings() {
		return this.sliblings;
	}

	public void setSliblings(Integer sliblings) {
		this.sliblings = sliblings;
	}

	
	public Integer getKids() {
		return this.kids;
	}

	public void setKids(Integer kids) {
		this.kids = kids;
	}

	
	public boolean getLiveAtHome() {
		return this.liveAtHome;
	}

	public void setLiveAtHome(boolean liveAtHome) {
		this.liveAtHome = liveAtHome;
	}

	
	public Boolean getSheltered() {
		return this.sheltered;
	}

	public void setSheltered(Boolean sheltered) {
		this.sheltered = sheltered;
	}

	public Integer getLiveWith() {
		return liveWith;
	}

	public void setLiveWith(Integer liveWith) {
		this.liveWith = liveWith;
	}
	public Integer getParentEducation() {
		return this.parentEducation;
	}

	public void setParentEducation(Integer parentEducation) {
		this.parentEducation = parentEducation;
	}

	
	public Integer getEducation() {
		return this.education;
	}

	public void setEducation(Integer education) {
		this.education = education;
	}

	
	public Integer getEnrollmentStatus() {
		return this.enrollmentStatus;
	}

	public void setEnrollmentStatus(Integer enrollmentStatus) {
		this.enrollmentStatus = enrollmentStatus;
	}

	
	public String getEnrollmentLocation() {
		return this.enrollmentLocation;
	}

	public void setEnrollmentLocation(String enrollmentLocation) {
		this.enrollmentLocation = enrollmentLocation;
	}

	
	public Integer getWorkStatus() {
		return this.workStatus;
	}

	public void setWorkStatus(Integer workStatus) {
		this.workStatus = workStatus;
	}

	
	public Integer getTimeToWorkplace() {
		return this.timeToWorkplace;
	}

	public void setTimeToWorkplace(Integer timeToWorkplace) {
		this.timeToWorkplace = timeToWorkplace;
	}

	
	public Integer getNoOfJob() {
		return this.noOfJob;
	}

	public void setNoOfJob(Integer noOfJob) {
		this.noOfJob = noOfJob;
	}

	
	public String getEmployer() {
		return this.employer;
	}

	public void setEmployer(String employer) {
		this.employer = employer;
	}

	
	public String getJobTitle() {
		return this.jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	
	public Integer getDurationOfEmployement() {
		return this.durationOfEmployement;
	}

	public void setDurationOfEmployement(Integer durationOfEmployement) {
		this.durationOfEmployement = durationOfEmployement;
	}

	
	public boolean getUnemploymentBenfits() {
		return this.unemploymentBenfits;
	}

	public void setUnemploymentBenfits(boolean unemploymentBenfits) {
		this.unemploymentBenfits = unemploymentBenfits;
	}

	
	public String getReasonUnemploymentBenefits() {
		return this.reasonUnemploymentBenefits;
	}

	public void setReasonUnemploymentBenefits(String reasonUnemploymentBenefits) {
		this.reasonUnemploymentBenefits = reasonUnemploymentBenefits;
	}

	
	public Double getTotalIncome() {
		return this.totalIncome;
	}

	public void setTotalIncome(Double totalIncome) {
		this.totalIncome = totalIncome;
	}

	
	public Integer getRateAmount() {
		return this.rateAmount;
	}

	public void setRateAmount(Integer rateAmount) {
		this.rateAmount = rateAmount;
	}

	
	public Integer getRateType() {
		return this.rateType;
	}

	public void setRateType(Integer rateType) {
		this.rateType = rateType;
	}

	
	public String getAvgHoursPerWeek() {
		return this.avgHoursPerWeek;
	}

	public void setAvgHoursPerWeek(String avgHoursPerWeek) {
		this.avgHoursPerWeek = avgHoursPerWeek;
	}

	
	public Integer getResidentStatus() {
		return this.residentStatus;
	}

	public void setResidentStatus(Integer residentStatus) {
		this.residentStatus = residentStatus;
	}

	
	public Integer getDollarSpendFood() {
		return this.dollarSpendFood;
	}

	public void setDollarSpendFood(Integer dollarSpendFood) {
		this.dollarSpendFood = dollarSpendFood;
	}

	
	public Integer getDollarSpendClothes() {
		return this.dollarSpendClothes;
	}

	public void setDollarSpendClothes(Integer dollarSpendClothes) {
		this.dollarSpendClothes = dollarSpendClothes;
	}

	
	public Integer getDollarSpendEntertainment() {
		return this.dollarSpendEntertainment;
	}

	public void setDollarSpendEntertainment(Integer dollarSpendEntertainment) {
		this.dollarSpendEntertainment = dollarSpendEntertainment;
	}

	
	public Integer getDollarSpendTransport() {
		return this.dollarSpendTransport;
	}

	public void setDollarSpendTransport(Integer dollarSpendTransport) {
		this.dollarSpendTransport = dollarSpendTransport;
	}

	
	public Integer getDollarSpendSavings() {
		return this.dollarSpendSavings;
	}

	public void setDollarSpendSavings(Integer dollarSpendSavings) {
		this.dollarSpendSavings = dollarSpendSavings;
	}

	
	public Integer getMealsPerDay() {
		return this.mealsPerDay;
	}

	public void setMealsPerDay(Integer mealsPerDay) {
		this.mealsPerDay = mealsPerDay;
	}

	
	public String getFoodPreference() {
		return this.foodPreference;
	}

	public void setFoodPreference(String foodPreference) {
		this.foodPreference = foodPreference;
	}

	
	public String getFoodSource() {
		return this.foodSource;
	}

	public void setFoodSource(String foodSource) {
		this.foodSource = foodSource;
	}

	
	public boolean getCook() {
		return this.cook;
	}

	public void setCook(boolean cook) {
		this.cook = cook;
	}

	
	public Integer getTravelForFoodDistance() {
		return this.travelForFoodDistance;
	}

	public void setTravelForFoodDistance(Integer travelForFoodDistance) {
		this.travelForFoodDistance = travelForFoodDistance;
	}

	
	public Integer getTravalForFoodTime() {
		return this.travalForFoodTime;
	}

	public void setTravalForFoodTime(Integer travalForFoodTime) {
		this.travalForFoodTime = travalForFoodTime;
	}

	
	public boolean getSubFoodProgram() {
		return this.subFoodProgram;
	}

	public void setSubFoodProgram(boolean subFoodProgram) {
		this.subFoodProgram = subFoodProgram;
	}

	
	public String getSubFoodProgramEntity() {
		return this.subFoodProgramEntity;
	}

	public void setSubFoodProgramEntity(String subFoodProgramEntity) {
		this.subFoodProgramEntity = subFoodProgramEntity;
	}

	
	public Integer getSubFoodProgramDuration() {
		return this.subFoodProgramDuration;
	}

	public void setSubFoodProgramDuration(Integer subFoodProgramDuration) {
		this.subFoodProgramDuration = subFoodProgramDuration;
	}

	
	public Integer getSubFoodProgramRenew() {
		return this.subFoodProgramRenew;
	}

	public void setSubFoodProgramRenew(Integer subFoodProgramRenew) {
		this.subFoodProgramRenew = subFoodProgramRenew;
	}

	
	public String getSubFoodProgramExp() {
		return this.subFoodProgramExp;
	}

	public void setSubFoodProgramExp(String subFoodProgramExp) {
		this.subFoodProgramExp = subFoodProgramExp;
	}

	
	public String getAllergies() {
		return this.allergies;
	}

	public void setAllergies(String allergies) {
		this.allergies = allergies;
	}

	
	public boolean getAddiction() {
		return this.addiction;
	}

	public void setAddiction(boolean addiction) {
		this.addiction = addiction;
	}

	
	public String getAddictionType() {
		return this.addictionType;
	}

	public void setAddictionType(String addictionType) {
		this.addictionType = addictionType;
	}

	
	public String getMentalHealthIssue() {
		return this.mentalHealthIssue;
	}

	public void setMentalHealthIssue(String mentalHealthIssue) {
		this.mentalHealthIssue = mentalHealthIssue;
	}

	
	public String getMentalHealthIssueHistory() {
		return this.mentalHealthIssueHistory;
	}

	public void setMentalHealthIssueHistory(String mentalHealthIssueHistory) {
		this.mentalHealthIssueHistory = mentalHealthIssueHistory;
	}

	
	public String getHeight() {
		return this.height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	
	public String getWeight() {
		return this.weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	
	public Integer getExercisePerWeek() {
		return this.exercisePerWeek;
	}

	public void setExercisePerWeek(Integer exercisePerWeek) {
		this.exercisePerWeek = exercisePerWeek;
	}

	
	public Integer getLastVisitDoctor() {
		return this.lastVisitDoctor;
	}

	public void setLastVisitDoctor(Integer lastVisitDoctor) {
		this.lastVisitDoctor = lastVisitDoctor;
	}

	
	public Integer getLastVisitDentist() {
		return this.lastVisitDentist;
	}

	public void setLastVisitDentist(Integer lastVisitDentist) {
		this.lastVisitDentist = lastVisitDentist;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getEthnicity() {
		return ethnicity;
	}

	public void setEthnicity(Integer ethnicity) {
		this.ethnicity = ethnicity;
	}

	public List<Integer> getFoodPreferenceList() {
		return foodPreferenceList;
	}

	public void setFoodPreferenceList(List<Integer> foodPreferenceList) {
		this.foodPreferenceList = foodPreferenceList;
	}

	public Set<Dependent> getDependents() {
		return dependents;
	}

	public void setDependants(Set<Dependent> dependents) {
		this.dependents = dependents;
	}

	public Ngo getNgo() {
		return ngo;
	}

	public void setNgo(Ngo ngo) {
		this.ngo = ngo;
	}

	public void setDependents(Set<Dependent> dependents) {
		this.dependents = dependents;
	}

	public Integer getMaxOrderAllowed() {
		return maxOrderAllowed;
	}

	public void setMaxOrderAllowed(Integer maxOrderAllowed) {
		this.maxOrderAllowed = maxOrderAllowed;
	}

	public Integer getMaxMealsAllowedPerDay() {
		return maxMealsAllowedPerDay;
	}

	public void setMaxMealsAllowedPerDay(Integer maxMealsAllowedPerDay) {
		this.maxMealsAllowedPerDay = maxMealsAllowedPerDay;
	}
	
	
}
