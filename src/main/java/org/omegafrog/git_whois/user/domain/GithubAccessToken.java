package org.omegafrog.git_whois.user.domain;

import jakarta.persistence.Embeddable;

@Embeddable
public class GithubAccessToken {
	private String accessToken;
	private String tokenType;

	protected GithubAccessToken() {
	}

	public GithubAccessToken(String accessToken, String tokenType) {
		this.accessToken = accessToken;
		this.tokenType = tokenType;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public String getTokenType() {
		return tokenType;
	}
}
