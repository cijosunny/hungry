package org.hni.payment.om;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hni.common.om.Persistable;
import org.hni.user.om.User;

@Entity
@Table(name = "hni_audit")
public class HniAudit implements Serializable, Persistable {

	private static final long serialVersionUID = -1036619624568269599L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "payment_instrument_id", referencedColumnName = "id")
	private PaymentInstrument paymentInstrument;
	
	@Column(name="action")
	private Long action;
	
	@Column(name="action_amount")
	private Double actionAmount;
	
	@Column(name="net_balance")
	private Double netBalance;
	
	@Column(name="previous_amount")
	private Double previousAmount;
	
	@ManyToOne
	@JoinColumn(name = "created_by", referencedColumnName = "id")
	private User createdBy;
	
	@Column(name="created_date")
	private Date createdDate;
	
	@ManyToOne
	@JoinColumn(name = "modified_by", referencedColumnName = "id")
	private User modifiedBy;
	
	@Column(name="modified_date")
	private Date modifiedDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PaymentInstrument getPaymentInstrument() {
		return paymentInstrument;
	}

	public void setPaymentInstrument(PaymentInstrument paymentInstrument) {
		this.paymentInstrument = paymentInstrument;
	}

	public Long getAction() {
		return action;
	}

	public void setAction(Long action) {
		this.action = action;
	}

	public Double getActionAmount() {
		return actionAmount;
	}

	public void setActionAmount(Double actionAmount) {
		this.actionAmount = actionAmount;
	}

	public Double getNetBalance() {
		return netBalance;
	}

	public void setNetBalance(Double netBalance) {
		this.netBalance = netBalance;
	}

	public Double getPreviousAmount() {
		return previousAmount;
	}

	public void setPreviousAmount(Double previousAmount) {
		this.previousAmount = previousAmount;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public User getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(User modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
}
