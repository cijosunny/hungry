package org.hni.provider.om;

import java.io.Serializable;
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

import com.fasterxml.jackson.annotation.JsonIgnore;
/**
 * Maps the name of a menu and when the menu is applicable
 * during the day.  Hours should be represented in 24hr format.
 *
 * For example a menu might be "breakfast" with hours between 6am - 10am.
 * 
 */
@Entity
@Table(name="menus")
public class Menu implements Persistable, Serializable {

	private static final long serialVersionUID = -441007028990038177L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	protected Long id;
	
	@Column(name="name") private String name;
	@Column(name="start_hour") private Long startHourAvailable;
	@Column(name="end_hour") private Long endHourAvailable;

	@OneToMany(cascade={CascadeType.ALL}, mappedBy="menu", fetch=FetchType.EAGER)
	private Set<MenuItem> menuItems = new HashSet<MenuItem>();

	@ManyToOne
	@JoinColumn(name="provider_id", referencedColumnName = "id")
	private Provider provider;
	
	public Menu() {}
	public Menu(Long id) {
		this.id = id;
	}
	
	@Override
	public Object getId() {
		return this.id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getStartHourAvailable() {
		return startHourAvailable;
	}

	public void setStartHourAvailable(Long startHourAvailable) {
		this.startHourAvailable = startHourAvailable;
	}

	public Long getEndHourAvailable() {
		return endHourAvailable;
	}

	public void setEndHourAvailable(Long endHourAvailable) {
		this.endHourAvailable = endHourAvailable;
	}

	@JsonIgnore
	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<MenuItem> getMenuItems() {
		return menuItems;
	}

	public void setMenuItems(Set<MenuItem> menuItems) {
		this.menuItems = menuItems;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Menu other = (Menu) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	
}
