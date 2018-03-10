package org.hni.admin.service.dto;

public class NgoBasicDto {
	
	Long id;
	Long userId;
	String name;
	String phone;
	String website;
	Long createdUsers;
	String address;
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getPhone() {
		return phone;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public Long getCreatedUsers() {
		return createdUsers;
	}
	public void setCreatedUsers(Long createdUsers) {
		this.createdUsers = createdUsers;
	}

}
