package org.omegafrog.git_whois.user.application;

import org.omegafrog.git_whois.user.domain.AuthToken;
import org.omegafrog.git_whois.user.domain.User;
import org.omegafrog.git_whois.user.domain.UserInformation;
import org.omegafrog.git_whois.user.domain.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class UserRegisterService {

	private final ObjectMapper objectMapper;
	private final UserRepository userRepository;

	@Value("${spring.security.oauth2.client.registration.github.client-id}")
	private String clientId;

	@Value("${spring.security.oauth2.client.registration.github.client-secret}")
	private String clientSecret;

	public UserRegisterService(ObjectMapper objectMapper, UserRepository userRepository) {
		this.objectMapper = objectMapper;
		this.userRepository = userRepository;
	}
	private RestClient restClient = RestClient.create();



	private AuthToken requestAccessToken(String code) throws JsonProcessingException {
		String response = restClient
			.post()
			.uri("https://github.com/login/oauth/access_token?client_id=%s&client_secret=%s&code=%s&redirect_url=%s"
				.formatted(clientId, clientSecret, code, "http://localhost:8080/login/oauth2/code/github"))
			.header("Accept", "application/json")
			.retrieve()
			.body(String.class);

		String accessToken = objectMapper.readTree(response).get("access_token").asText();
		String tokenType = "Bearer";

		return new AuthToken(accessToken, tokenType);
	}

	public void registerOrLogin(String code) throws JsonProcessingException {
		AuthToken authToken = requestAccessToken(code);
		UserInformation userInfo = getUserInfo(authToken);
		Long id = userInfo.getGithubId();

		User user;

		if(canRegister(id, userRepository)){
			user = userRepository.save(new User( authToken, userInfo));
		}
		else{
			user = userRepository.findByGithubId(id);
		}

		// token 생성 및 저장


	}

	private UserInformation getUserInfo(AuthToken authToken) throws JsonProcessingException {
		String body = restClient
			.get()
			.uri("https://api.github.com/user")
			.header("Authorization", "%s %s"
				.formatted(authToken.getTokenType(), authToken.getAccessToken()))
			.retrieve()
			.body(String.class);

		JsonNode jsonNode = objectMapper.readTree(body);
		String loginName = jsonNode
			.get("login").asText();
		String email = jsonNode.get("email").asText();
		String avatarUrl = jsonNode.get("avatar_url").asText();
		String nodeId = jsonNode.get("node_id").asText();
		String name = jsonNode.get("name").asText();
		long id = jsonNode.get("id").asLong();
		return new UserInformation(id, loginName, email, avatarUrl, nodeId, name);
	}

	private boolean canRegister(Long githubId, UserRepository userRepository) {
		return !userRepository.existByGithubId(githubId);
	}
}
