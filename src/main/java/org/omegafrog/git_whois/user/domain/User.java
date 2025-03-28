package org.omegafrog.git_whois.user.domain;

import org.omegafrog.git_whois.user.domain.exception.UserException;

import jakarta.persistence.Id;

public class User {

	@Id
	private final String id;

	private UserInformation metaData;

	private AuthToken authToken;


	public User(String id, AuthToken authToken, UserInformation metaData) {
		this.id = id;
		this.authToken = authToken;
		this.metaData = metaData;
	}

	public String getId() {
		return id;
	}

	public UserInformation getMetaData() {
		return metaData;
	}

	public AuthToken getAuthToken() {
		return authToken;
	}


}
