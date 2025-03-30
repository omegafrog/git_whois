package org.omegafrog.git_whois.user.domain;

import java.util.Objects;

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

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof UserInformation))
			return false;
		UserInformation that = (UserInformation)o;
		return Objects.equals(email, that.email) && Objects.equals(name, that.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(email, name);
	}
}
