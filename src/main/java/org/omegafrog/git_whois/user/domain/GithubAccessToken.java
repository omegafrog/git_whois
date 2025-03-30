package org.omegafrog.git_whois.user.domain;

import jakarta.persistence.Embeddable;

@Embeddable
public class GithubAccessToken extends AccessToken {

	protected GithubAccessToken() {
		super();
	}

	public GithubAccessToken(String accessToken, String tokenType) {
		super(accessToken, tokenType);
	}
}
