package org.hni.security.om;

import org.hni.user.om.User;

public class AuthenticationResult {

	private int status;
	private User user;
	private String token;
	private String message;
	private String roleName;
	
	public AuthenticationResult() {}
	public AuthenticationResult(int status, String message) {
		this.status = status;
		this.message = message;
	}
	public AuthenticationResult(int status, User user, String token, String message, String roleName) {
		this.status = status;
		this.message = message;
		this.user = user;
		this.token = token;
		this.message = message;
		this.roleName = roleName;
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	
}
