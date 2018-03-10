package org.hni.admin.service.dto;

public class PaymentInstrumentDto {
	Long id;
	String cardNumber;
	String cardSerialId;
	String status;
	Double originalBalance;
	Double balance;
	String stateCode;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public String getCardSerialId() {
		return cardSerialId;
	}
	public void setCardSerialId(String cardSerialId) {
		this.cardSerialId = cardSerialId;
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
	public String getStateCode() {
		return stateCode;
	}
	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}
	

}
