package org.hni.provider.om;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hni.common.om.Persistable;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Provides information about when a ProviderLocation is open for
 * business on any day of week. 
 *
 */
@Entity
@Table(name="provider_location_hours")
public class ProviderLocationHour implements Serializable, Persistable {

	private static final long serialVersionUID = 1037150455719358692L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	protected Long id;
	
	@Column(name="dow") private String dow;
	@Column(name="open_hour") private Long openHour; // 24 hr format
	@Column(name="close_hour") private Long closeHour;
	
	@ManyToOne
	@JoinColumn(name="provider_location_id", referencedColumnName = "id")
	private ProviderLocation providerLocation;
	
	@Override
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getDow() {
		return dow;
	}

	public void setDow(String dow) {
		this.dow = dow;
	}

	public Long getOpenHour() {
		return openHour;
	}

	public void setOpenHour(Long openHour) {
		this.openHour = openHour;
	}

	public Long getCloseHour() {
		return closeHour;
	}

	public void setCloseHour(Long closeHour) {
		this.closeHour = closeHour;
	}

	@JsonIgnore
	public ProviderLocation getProviderLocation() {
		return providerLocation;
	}

	public void setProviderLocation(ProviderLocation providerLocation) {
		this.providerLocation = providerLocation;
	}
	
	
}
