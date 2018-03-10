package org.hni.provider.om;

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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hni.common.om.Persistable;
import org.hni.user.om.Address;
import org.hni.user.om.User;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Represents a physical location for a provider generally where food is
 * prepared and provided to clients.
 *
 */
@Entity
@Table(name = "provider_locations")
public class ProviderLocation implements Persistable, Serializable {

	private static final long serialVersionUID = 304579212529448435L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Column(name = "name")
	private String name;
	@Column(name = "website")
	private String website;
	@Column(name = "created")
	private Date created;
	@Column(name = "created_by")
	private Long createdById;
	@Column(name = "is_active")
	private Boolean isActive;
	@Column(name = "last_updated")
	private Date lastUpdated;
	
	@ManyToOne
	@JoinColumn(name = "last_updated_by", referencedColumnName = "id")
	private User lastUpdatedBy;

	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "address_id", referencedColumnName = "id", insertable = true, updatable = true)
	private Address address;

	@ManyToOne
	@JoinColumn(name = "provider_id", referencedColumnName = "id")
	private Provider provider;

	@ManyToOne
	@JoinColumn(name = "menu_id", referencedColumnName = "id")
	private Menu menu;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "providerLocation", cascade = { CascadeType.ALL })
	// @OneToMany(fetch=FetchType.EAGER, mappedBy="providerLocation", cascade =
	// {CascadeType.ALL}, orphanRemoval=true)
	private Set<ProviderLocationHour> providerLocationHours = new HashSet<ProviderLocationHour>();

	public ProviderLocation() {
	}

	public ProviderLocation(Long id) {
		this.id = id;
	}

	@Override
	public Long getId() {
		return this.id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	
	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	
	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}

	public Set<ProviderLocationHour> getProviderLocationHours() {
		return providerLocationHours;
	}

	public void setProviderLocationHours(Set<ProviderLocationHour> providerLocationHours) {
		this.providerLocationHours = providerLocationHours;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public User getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(User lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	
}
