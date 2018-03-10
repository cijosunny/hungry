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
import org.hni.provider.om.Provider;
import org.hni.user.om.User;

@Entity
@Table(name = "payment_instruments")
public class PaymentInstrument implements Serializable, Persistable {

	private static final long serialVersionUID = -1036619624568269599L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "provider_id", referencedColumnName = "id")
	private Provider provider;

	@Column(name = "card_type")
	private String cardType;
	@Column(name = "card_number")
	private String cardNumber;
	@Column(name = "card_serial_id")
	private String cardSerialId;
	@Column(name = "status")
	private String status;
	@Column(name = "orginal_balance")
	private Double originalBalance;
	@Column(name = "balance")
	private Double balance;
	@Column(name = "last_used_datetime")
	private Date lastUsedDatetime;
	@Column(name = "pin_number")
	private String pinNumber;
	@Column(name = "state_code")
	private String stateCode;
	@Column(name = "allow_topup")
	private Boolean allowTopup;
	@ManyToOne
	@JoinColumn(name = "created_by", referencedColumnName = "id")
	private User createdBy;
	@ManyToOne
	@JoinColumn(name = "modified_by", referencedColumnName = "id")
	private User modifiedBy;

	@Column(name = "created_date")
	private Date createdDate;

	public PaymentInstrument() {
	}

	public PaymentInstrument(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Double getOriginalBalance() {
		return originalBalance;
	}

	public void setOriginalBalance(Double originalBalance) {
		this.originalBalance = originalBalance;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Date getLastUsedDatetime() {
		return lastUsedDatetime;
	}

	public void setLastUsedDatetime(Date lastUsedDatetime) {
		this.lastUsedDatetime = lastUsedDatetime;
	}

	public String getPinNumber() {
		return pinNumber;
	}

	public void setPinNumber(String pinNumber) {
		this.pinNumber = pinNumber;
	}

	public String getCardSerialId() {
		return cardSerialId;
	}

	public void setCardSerialId(String cardSerialId) {
		this.cardSerialId = cardSerialId;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public Boolean getAllowTopup() {
		return allowTopup;
	}

	public void setAllowTopup(Boolean allowTopup) {
		this.allowTopup = allowTopup;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public User getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(User modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

}
