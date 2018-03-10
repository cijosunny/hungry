package org.hni.sms.service.model;

public class SmsMessage {

	private String toNumber;
	private String text;
	private String fromNumber;
	private boolean status;

	public String getToNumber() {
		return toNumber;
	}

	public void setToNumber(String toNumber) {
		this.toNumber = toNumber;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getFromNumber() {
		return fromNumber;
	}

	public void setFromNumber(String fromNumber) {
		this.fromNumber = fromNumber;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "SmsMessage [toNumber=" + toNumber + ", text=" + text + ", fromNumber=" + fromNumber + ", status="
				+ status + "]";
	}
	
	

}
