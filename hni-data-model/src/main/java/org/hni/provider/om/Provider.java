package org.hni.provider.om;

import org.hni.common.om.Persistable;
import org.hni.user.om.Address;

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

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * A Provider is an entity that provides meals to clients. There will be one or
 * more ProviderLocations associated with a Provider. Each provier also has one
 * or more Menus associated with them that can be fulfilled at a location.
 *
 */
@Entity
@Table(name = "providers")
public class Provider implements Serializable, Persistable {

	private static final long serialVersionUID = -6983145009930349810L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Column(name = "name")
	private String name;
	@Column(name = "website_url")
	private String websiteUrl;
	@Column(name = "created")
	private Date created;
	@Column(name = "created_by")
	private Long createdById;
	@Column(name = "deleted")
	private boolean isDeleted;
	@Column(name = "active")
	private boolean isActive;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "address_id", referencedColumnName = "id", insertable = true, updatable = true)
	private Address address;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "provider", cascade = { CascadeType.ALL })
	// @OneToMany(fetch= FetchType.EAGER, mappedBy="provider", cascade =
	// {CascadeType.ALL}, orphanRemoval=true)
	private Set<Menu> menus = new HashSet<>();

	public Provider() {
	}

	public Provider(Long id) {
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

	public String getWebsiteUrl() {
		return websiteUrl;
	}

	public void setWebsiteUrl(String websiteUrl) {
		this.websiteUrl = websiteUrl;
	}

	public Set<Menu> getMenus() {
		return menus;
	}

	public void setMenus(Set<Menu> menu) {
		this.menus = menu;
	}

	
	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Provider [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", websiteUrl=");
		builder.append(websiteUrl);
		builder.append(", created=");
		builder.append(created);
		builder.append(", createdById=");
		builder.append(createdById);
		builder.append(", address=");
		builder.append(address);
		builder.append(", menus=");
		builder.append(menus);
		builder.append("]");
		return builder.toString();
	}

}
