package org.omegafrog.git_whois.user.application;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.omegafrog.git_whois.global.Util;
import org.omegafrog.git_whois.user.domain.AuthToken;
import org.omegafrog.git_whois.user.domain.GithubAccessToken;
import org.omegafrog.git_whois.user.domain.GithubId;
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
public class UserAuthService {

	private final ObjectMapper objectMapper;
	private final UserRepository userRepository;

	@Value("${spring.security.oauth2.client.registration.github.client-id}")
	private String clientId;

	@Value("${spring.security.oauth2.client.registration.github.client-secret}")
	private String clientSecret;

	@Value("${custom.jwt.secret}")
	private String secret;

	@Value("${custom.jwt.exp}")
	private int exp;

	@Value("${custom.jwt.refresh-exp}")
	private int refreshExp;


	public UserAuthService(ObjectMapper objectMapper, UserRepository userRepository) {
		this.objectMapper = objectMapper;
		this.userRepository = userRepository;
	}
	private RestClient restClient = RestClient.create();

	private GithubAccessToken requestAccessToken(String code) throws JsonProcessingException {
		// TODO : 외부 API 호출 분리 필요
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
	}

	public AuthToken registerOrLogin(String code) throws JsonProcessingException {
		GithubAccessToken githubAccessToken = requestAccessToken(code);
		UserInformation userInfo = getUserInfo(githubAccessToken);
		GithubId id = userInfo.getGithubId();

		User user;

		if(canRegister(id, userRepository)){
			user = userRepository.save(new User(githubAccessToken, userInfo));
		}
		else{
			user = userRepository.findByGithubId(id);
		}

		// token 생성
		return generateToken(user);
	}

	private AuthToken generateToken(User user) {
		Map<String, Object> claims = new HashMap<>();

		claims.put("id", user.getId());
		claims.put("name", user.getMetaData().getName());
		claims.put("nickName", user.getMetaData().getLoginName());
		claims.put("avatarUrl", user.getMetaData().getAvatarUrl());
		claims.put("email", user.getMetaData().getEmail());
		claims.put("jti", UUID.randomUUID().toString());

		String[] tokens = Util.Jwt.generateTokens(claims, exp, refreshExp, secret);

		return new AuthToken(tokens[0], tokens[1]);

	}

	private UserInformation getUserInfo(GithubAccessToken githubAccessToken) throws JsonProcessingException {
		// TODO : 외부 API 호출 분리 필요
		String body = restClient
			.get()
			.uri("https://api.github.com/user")
			.header("Authorization", "%s %s"
				.formatted(githubAccessToken.getTokenType(), githubAccessToken.getAccessToken()))
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

		return new UserInformation(githubId, loginName, email, avatarUrl, nodeId, name);
	}

	private boolean canRegister(GithubId githubId, UserRepository userRepository) {
		return !userRepository.existByGithubId(githubId);
	}
}
