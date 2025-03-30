package org.omegafrog.git_whois.user.domain;

public abstract class UserInformation {
	protected String email;
	protected String name;

	public String getEmail() {
		return email;
	}

	public String getName() {
		return name;
	}

	public UserInformation() {
	}

	public UserInformation(String email, String name) {
		this.email = email;
		this.name = name;
	}
}
