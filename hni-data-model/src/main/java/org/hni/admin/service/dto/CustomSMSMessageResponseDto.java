package org.hni.admin.service.dto;

import java.util.Map;

public class CustomSMSMessageResponseDto {

	private Long totalSuccess;
	private Long totalFailed;
	private Map<String, String> details;

	public Long getTotalSuccess() {
		return totalSuccess;
	}

	public void setTotalSuccess(Long totalSuccess) {
		this.totalSuccess = totalSuccess;
	}

	public Long getTotalFailed() {
		return totalFailed;
	}

	public void setTotalFailed(Long totalFailed) {
		this.totalFailed = totalFailed;
	}

	public Map<String, String> getDetails() {
		return details;
	}

	public void setDetails(Map<String, String> details) {
		this.details = details;
	}

}
