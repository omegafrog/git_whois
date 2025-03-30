package org.omegafrog.git_whois.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AccessToken {

	@Column(nullable = false)
	private String accessToken;
	private String refreshToken;
	@Column(nullable = false)
	private String tokenType;

	public String getAccessToken() {
		return accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public String getTokenType() {
		return tokenType;
	}

	protected AccessToken() {
	}

	public AccessToken(String accessToken, String tokenType) {
		this.accessToken = accessToken;
		this.tokenType = tokenType;
	}

	public AccessToken(String accessToken, String refreshToken, String tokenType) {
		this(accessToken, tokenType);
		this.refreshToken = refreshToken;
	}
}
