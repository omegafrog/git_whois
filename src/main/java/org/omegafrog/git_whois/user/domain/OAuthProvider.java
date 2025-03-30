package org.omegafrog.git_whois.user.domain;

import org.springframework.beans.factory.annotation.Value;

public abstract class OAuthProvider {

	@Value("${spring.security.oauth2.client.registration.github.client-id}")
	protected String clientId;

	@Value("${spring.security.oauth2.client.registration.github.client-secret}")
	protected String clientSecret;

	abstract public AccessToken requestAccessToken(String code);

	abstract public UserInformation getUserInformation(AccessToken accessToken);
}
