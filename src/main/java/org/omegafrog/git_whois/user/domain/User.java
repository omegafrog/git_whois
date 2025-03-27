package org.omegafrog.git_whois.user.domain;

import org.omegafrog.git_whois.user.domain.exception.UserException;

import jakarta.persistence.Id;

public class User {

	@Id
	private final String id;

	private UserInformation metaData;

	private AuthToken authToken;

	private OAuth2Credential credential;

	public User(String id, OAuth2Credential credential, AuthToken authToken, UserInformation metaData) {
		this.id = id;
		this.credential = credential;
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

	public OAuth2Credential getCredential() {
		return credential;
	}
}
