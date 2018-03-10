package org.hni.sms.service.provider.om;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hni.common.om.Persistable;
import org.hni.sms.service.provider.ServiceProvider;

/**
 * @author Rahul
 *
 */
@Entity
@Table(name="sms_provider")
public class SmsProvider  implements Serializable, Persistable  {

	private static final long serialVersionUID = -7881695070129357979L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;
	
	@Column(name="provider") 
	@Enumerated(EnumType.STRING)
	private ServiceProvider providerName;
	@Column(name="long_code") private String longCode;
	@Column(name="short_code")private String shortCode;
	@Column(name="state_code")private String stateCode;
	@Column(name="description")private String description;
	@Column(name="created")private String created;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ServiceProvider getProviderName() {
		return providerName;
	}

	public void setProviderName(ServiceProvider providerName) {
		this.providerName = providerName;
	}

	public String getLongCode() {
		return longCode;
	}

	public void setLongCode(String longCode) {
		this.longCode = longCode;
	}

	public String getShortCode() {
		return shortCode;
	}

	public void setShortCode(String shortCode) {
		this.shortCode = shortCode;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

}
