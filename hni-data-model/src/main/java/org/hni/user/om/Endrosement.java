package org.hni.user.om;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hni.common.om.Persistable;

@Entity
@Table(name = "endrosement")
public class Endrosement implements Persistable, Serializable {

	private static final long serialVersionUID = 7553475738921092329L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "ngo_id")
	private Long ngoId;
	
	@Column(name = "endrosement")
	private String endrosement;
	
	

	public Endrosement() {
		super();
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public Long getNgoId() {
		return ngoId;
	}



	public void setNgoId(Long ngoId) {
		this.ngoId = ngoId;
	}



	public String getEndrosement() {
		return endrosement;
	}



	public void setEndrosement(String endrosement) {
		this.endrosement = endrosement;
	}

	}
