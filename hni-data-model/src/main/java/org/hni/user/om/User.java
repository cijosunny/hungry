package org.hni.user.om;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hni.common.om.Persistable;
import org.hni.user.om.type.Gender;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Represents any user defined in the system. Users can play several different
 * roles such as Administrator, Treasurer, Customer, etc
 * 
 * Every User in the system MUST be associated to an Organization or Provider
 * through the mapping tables or they will have no permission within the system.
 * 
 * @author jeff3parker
 *
 */
@Entity
@Table(name = "users")
public class User implements Persistable, Serializable {
	private static final long serialVersionUID = 7553475738921092329L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Column(name = "first_name")
	private String firstName;
	@Column(name = "last_name")
	private String lastName;
	@Column(name = "gender_code")
	private String genderCode;
	@Column(name = "mobile_phone")
	private String mobilePhone;
	@Column(name = "email")
	private String email;
	@Column(name = "deleted")
	private boolean deleted;
	@Column(name = "created")
	private Date created;

	@Column(name = "hashed_secret")
	private String hashedSecret;
	@Column(name = "salt")
	private String salt;
	@Column(name = "active")
	private Boolean isActive;
	@ManyToOne
	@JoinColumn(name = "created_by", referencedColumnName = "id")
	private User createdBy;
	@ManyToOne
	@JoinColumn(name = "updated_by", referencedColumnName = "id")
	private User updatedBy;
	
	private transient String organizationName;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinTable(name = "user_address", joinColumns = { @JoinColumn(name = "user_id", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "address_id",  referencedColumnName = "id", nullable = false, updatable = false) })	
	private Set<Address> addresses = new HashSet<>(0);
	
	private transient String password;
	private transient String token;
	private transient Long organizationId;
	private transient Map<String, Object> additionalInfo;

	public Map<String, Object> getAdditionalInfo() {
		return additionalInfo != null ? additionalInfo : new HashMap<>();
	}

	public void setAdditionalInfo(Map<String, Object> additionalInfo) {
		this.additionalInfo = additionalInfo;
	}
	public User() {
	}

	public User(Long id) {
		this.id = id;
	}

	public User(String firstName, String lastName, String mobilePhone) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.mobilePhone = mobilePhone;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getGenderCode() {
		return this.genderCode;
	}

	public void setGenderCode(String genderCode) {
		this.genderCode = genderCode;
	}

	public Gender getGender() {
		return Gender.get(this.genderCode);
	}

	public void setGender(Gender gender) {
		if (null != gender) {
			this.genderCode = gender.getId();
		}
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	@JsonIgnore
	public String getHashedSecret() {
		return hashedSecret;
	}

	public void setHashedSecret(String hashedSecret) {
		this.hashedSecret = hashedSecret;
	}

	@JsonIgnore
	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}
	
	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}
	
	public Set<Address> getAddresses() {
		return this.addresses;
	}

	public void setAddresses(Set<Address> addresses) {
		this.addresses = addresses;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public User getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(User updatedBy) {
		this.updatedBy = updatedBy;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("User [id=");
		builder.append(id);
		builder.append(", firstName=");
		builder.append(firstName);
		builder.append(", lastName=");
		builder.append(lastName);
		builder.append(", mobilePhone=");
		builder.append(mobilePhone);
		builder.append(", email=");
		builder.append(email);
		builder.append(", deleted=");
		builder.append(deleted);
		builder.append(", isActive=");
		builder.append(isActive);
		builder.append(", createdBy=");
		builder.append(createdBy);
		builder.append("]");
		return builder.toString();
	}
	
	

	
}