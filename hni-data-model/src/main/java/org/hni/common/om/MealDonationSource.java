package org.hni.common.om;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="meal_donation_sources")
public class MealDonationSource  implements Persistable, Serializable{
	private static final long serialVersionUID = 7553475738921092329L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "ngo_id")
	private Long ngoId;

	@Column(name = "source")
	private String source;
	
	@Column(name = "quantity")
	private Long mealQuantity;
	
	@Column(name = "frequency")
	private String frequency;
	
	@Column(name = "created")
	private Date created;

	@Column(name = "created_by")
	private Long createdBy;

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

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Long getMealQuantity() {
		return mealQuantity;
	}

	public void setMealQuantity(Long mealQuantity) {
		this.mealQuantity = mealQuantity;
	}
	
	
	
}
