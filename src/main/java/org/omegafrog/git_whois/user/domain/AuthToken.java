package org.omegafrog.git_whois.user.domain;

public class AuthToken {
	private final String accessToken;
	private final String refreshToken;

	public AuthToken(String accessToken, String refreshToken) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}
}
