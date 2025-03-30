package org.omegafrog.git_whois.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class UserInformation {
	@Column(unique = true, nullable = false)
	protected String email;
	@Column(nullable = false)
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
