package com.swt.amc.api;

public class UserInformationResponse {

	private String givenName;
	private String lastName;

	public UserInformationResponse() {

	}

	public UserInformationResponse(String givenName, String lastName) {
		this.givenName = givenName;
		this.lastName = lastName;
	}

	public String getGivenName() {
		return givenName;
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}
