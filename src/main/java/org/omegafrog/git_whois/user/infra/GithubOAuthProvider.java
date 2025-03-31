package org.omegafrog.git_whois.user.infra;

import org.omegafrog.git_whois.user.domain.AccessToken;
import org.omegafrog.git_whois.user.domain.GithubAccessToken;
import org.omegafrog.git_whois.user.domain.GithubId;
import org.omegafrog.git_whois.user.domain.GithubUserInformation;
import org.omegafrog.git_whois.user.domain.OAuthProvider;
import org.omegafrog.git_whois.user.domain.UserInformation;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GithubOAuthProvider extends OAuthProvider {

	private RestClient restClient;
	private ObjectMapper objectMapper;

	public GithubOAuthProvider(RestClient restClient, ObjectMapper objectMapper) {
		this.restClient = restClient;
		this.objectMapper = objectMapper;
	}

	@Override
	public AccessToken requestAccessToken(String code) {
		try {
			String response = restClient
				.post()
				.uri("https://github.com/login/oauth/access_token?client_id=%s&client_secret=%s&code=%s&redirect_url=%s"
					.formatted(clientId, clientSecret, code, "http://localhost:8080/login/oauth2/code/github"))
				.header("Accept", "application/json")
				.retrieve()
				.body(String.class);

			String accessToken = objectMapper.readTree(response).get("access_token").asText();
			String tokenType = "Bearer";
			return new GithubAccessToken(accessToken, tokenType);

		} catch (JsonProcessingException ex) {
			log.error(ex.getMessage());
			return null;
		}

	}

	@Override
	public UserInformation getUserInformation(AccessToken accessToken) {
		try {
			String body = restClient
				.get()
				.uri("https://api.github.com/user")
				.header("Authorization", "%s %s"
					.formatted(accessToken.getTokenType(), accessToken.getAccessToken()))
				.retrieve()
				.body(String.class);
			// TODO : json parsing 분리 필요
			JsonNode jsonNode = objectMapper.readTree(body);
			String loginName = jsonNode
				.get("login").asText();
			String email = jsonNode.get("email").asText();
			String avatarUrl = jsonNode.get("avatar_url").asText();
			String nodeId = jsonNode.get("node_id").asText();
			String name = jsonNode.get("name").asText();
			GithubId githubId = new GithubId(jsonNode.get("id").asLong());
			return new GithubUserInformation(githubId, loginName, email, avatarUrl, nodeId, name);
		} catch (JsonProcessingException ex) {
			log.error(ex.getMessage());
			return null;
		}

	}
}
