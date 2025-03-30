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

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof GithubAccessToken that))
			return false;
		return super.equals(o);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}
}
