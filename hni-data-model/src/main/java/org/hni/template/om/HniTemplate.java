package org.hni.template.om;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hni.common.om.Persistable;

import com.fasterxml.jackson.annotation.JsonInclude;


@Entity
@Table(name="hni_templates")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HniTemplate implements Persistable, Serializable {


	private static final long serialVersionUID = 435871577597384034L;

	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "created_by")
	private Long createdBy;
	
	@Column(name = "template")
	private String template;
	
	@Column(name = "type")
	private String type;
	
	@Column(name = "last_modified")
	private Date lastModified;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}
}
