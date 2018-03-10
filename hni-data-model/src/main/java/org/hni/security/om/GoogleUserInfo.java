package org.hni.security.om;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GoogleUserInfo {

	private String id;
	private String email;
	private boolean verifiedEmail; // verified_email
	private String name;
	private String givenName; // given_name
	private String familyName; // family_name
	private String link;
	private String picture;
	private String gender;
	private String locale;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@JsonProperty("verified_email")
	public boolean isVerifiedEmail() {
		return verifiedEmail;
	}
	
	@JsonProperty("verified_email")
	public void setVerifiedEmail(boolean verifiedEmail) {
		this.verifiedEmail = verifiedEmail;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@JsonProperty("given_name")
	public String getGivenName() {
		return givenName;
	}
	
	@JsonProperty("given_name")
	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}
	
	@JsonProperty("family_name")
	public String getFamilyName() {
		return familyName;
	}
	
	@JsonProperty("family_name")
	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getLocale() {
		return locale;
	}
	public void setLocale(String locale) {
		this.locale = locale;
	}
	
	
}
