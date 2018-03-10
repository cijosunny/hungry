package org.hni.user.om;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hni.common.om.Persistable;

import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Table(name = "volunteer")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Volunteer implements Persistable, Serializable  {
	private static final long serialVersionUID = 7553475738921092329L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Column(name = "user_id")
	private Long userId;
	
	@Column(name = "created")
	private Date created;
	
	@Column(name = "created_by")
	private Long createdBy;
	
	@Column(name = "birthday")
	private Date birthday;
	
	@Column(name = "sex")
	private String sex;
	
	@Column(name = "race")
	private Long race;
	
	@Column(name = "education")
	private Long education;
	
	@Column(name = "marital_status")
	private Long maritalStatus;
	
	@Column(name = "income")
	private Long income;
	
	@Column(name = "kids")
	private Long kids;
	
	@Column(name = "employer")
	private String employer;

	@Column(name = "non_profit")
	private String nonProfit;
	
	@Column( name = "available_for_place_order")
	private Boolean available;
	
	@Transient
	private Address address;
	
	@Transient
	private String phoneNumber;
	
	@Transient
	private String email;
	
	@Transient
	private String firstName;
	
	@Transient
	private String lastName;
	
	@Transient
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Long getRace() {
		return race;
	}

	public void setRace(Long race) {
		this.race = race;
	}

	public Long getEducation() {
		return education;
	}

	public void setEducation(Long education) {
		this.education = education;
	}

	public Long getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(Long maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public Long getIncome() {
		return income;
	}

	public void setIncome(Long income) {
		this.income = income;
	}

	public Long getKids() {
		return kids;
	}

	public void setKids(Long kids) {
		this.kids = kids;
	}

	public String getEmployer() {
		return employer;
	}

	public void setEmployer(String employer) {
		this.employer = employer;
	}

	public String getNonProfit() {
		return nonProfit;
	}

	public void setNonProfit(String nonProfit) {
		this.nonProfit = nonProfit;
	}
	
	
	public Boolean getAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
	

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
