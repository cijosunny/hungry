package org.hni.organization.om;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
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
import javax.persistence.Table;

import org.hni.common.om.Persistable;
import org.hni.user.om.Address;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * An organization is an entity that provides customer support
 * e.g. NI Personnel, NGO's, Schools, etc
 * 
 * All users are aligned to an organization in some way via a Role
 * 
 * @author jeff3parker
 *
 */
@Entity
@Table(name = "organizations")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Organization implements Persistable, Serializable {
	private static final long serialVersionUID = 2775752378663345293L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	protected Long id;
	
	@Column(name="name") private String name;
	@Column(name="phone") private String phone;
	@Column(name="email") private String email;
	@Column(name="website") private String website;
	@Column(name="logo") private String logo;
	@Column(name="created") private Date created;
	@Column(name="created_by") private Long createdById;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "organization_addresses", joinColumns = { @JoinColumn(name = "organization_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "address_id", referencedColumnName = "id") })
	protected Set<Address> addresses = new HashSet<Address>();

	public Organization() {}
	public Organization(Long id) {
		this.id = id;
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public Set<Address> getAddresses() {
		return addresses;
	}
	public void setAddresses(Set<Address> addresses) {
		this.addresses = addresses;
	}

	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Long getCreatedById() {
		return createdById;
	}
	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}

	

}
