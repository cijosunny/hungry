package org.hni.payment.service;

public class PaymentsExceededException extends Exception {

	public PaymentsExceededException(String message) {
		super(message);
	}
}
