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
@Table(name = "invitation")
public class Invitation implements Persistable, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5776557324122709332L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	@Column(name = "org_id")
	private String organizationId;
	@Column(name = "invite_code")
	private String invitationCode;
	@Column(name = "token_expire_date")
	private Date expirationDate;
	@Column(name = "created_date")
	private Date createdDate;
	@Column(name = "email")
	private String email;
	@Column(name = "invited_by")
	private Long invitedBy;
	@Column(name = "activated")
	private Integer activated;
	@Column(name = "phone")
	private String phone;
	@Column(name = "dependants_count")
	private Integer dependantsCount;
	@Column(name = "message")
	private String message;
	@Column(name = "name")
	private String name;
	@Column(name = "invitation_type")
	private String invitationType;
	@Column(name = "state")
	private String stateCode;
	@Column(name = "data")
	private String data;
	@Column(name = "website")
	private String website;
	@Column(name = "ngo_id")
	private Long ngoId;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public String getInvitationCode() {
		return invitationCode;
	}

	public void setInvitationCode(String invitationCode) {
		this.invitationCode = invitationCode;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getInvitedBy() {
		return invitedBy;
	}

	public void setInvitedBy(Long invitedBy) {
		this.invitedBy = invitedBy;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Integer getActivated() {
		return activated;
	}

	public void setActivated(Integer activated) {
		this.activated = activated;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getDependantsCount() {
		return dependantsCount;
	}

	public void setDependantsCount(Integer dependantsCount) {
		this.dependantsCount = dependantsCount;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInvitationType() {
		return invitationType;
	}

	public void setInvitationType(String invitationType) {
		this.invitationType = invitationType;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public Long getNgoId() {
		return ngoId;
	}

	public void setNgoId(Long ngoId) {
		this.ngoId = ngoId;
	}
	
	

}
