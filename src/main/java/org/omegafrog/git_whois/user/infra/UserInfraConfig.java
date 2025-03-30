package org.omegafrog.git_whois.user.infra;

import org.omegafrog.git_whois.user.domain.OAuthProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class UserInfraConfig {

	@Bean
	public RestClient restClient() {
		return RestClient.create();
	}

	@Bean
	public OAuthProvider oauthProvider(RestClient restClient, ObjectMapper objectMapper) {
		return new GithubOAuthProvider(restClient, objectMapper);
	}
}
