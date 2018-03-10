package org.hni.admin.service.dto;

import java.util.List;

public class CustomSMSMessageDto {

	private List<Long> userId;
	private String message;

	public List<Long> getUserId() {
		return userId;
	}

	public void setUserId(List<Long> userId) {
		this.userId = userId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
