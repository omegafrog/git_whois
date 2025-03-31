package org.omegafrog.git_whois.user.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.security.Key;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

class UserTest {

	@Test
	@DisplayName("User 생성 시에는 metaData, githubAccessToken이 필수로 주어져야 한다.")
	void createUser() {
		//given
		GithubUserInformation userInfo = new GithubUserInformation(
			new GithubId(1L),
			"loginName",
			"email",
			"avatarUrl",
			"nodeId",
			"name"
		);
		GithubAccessToken accessTokens = new GithubAccessToken(
			"accessToken", "Bearer"
		);
		// when
		GithubUser user = new GithubUser(userInfo, accessTokens);

		// then

		Assertions.assertNotNull(user);
		Assertions.assertNotNull(user.getId());
		Assertions.assertNotNull(user.getMetaData());
		Assertions.assertNotNull(user.getGithubAccessToken());
		Assertions.assertNotNull(user.getMetaData().getAvatarUrl());
		Assertions.assertNotNull(user.getMetaData().getName());
		Assertions.assertNotNull(user.getMetaData().getEmail());
		Assertions.assertNotNull(user.getMetaData().getGithubId());
		Assertions.assertNotNull(user.getMetaData().getNodeId());
		Assertions.assertNotNull(user.getMetaData().getLoginName());

		Assertions.assertNotNull(user.getGithubAccessToken().getAccessToken());
		Assertions.assertNotNull(user.getGithubAccessToken().getTokenType());

	}

	@Test
	@DisplayName("인증/인가에 사용할 토큰을 생성할 수 있다.")
	void createTokens() {
		//given
		int exp = 60 * 60 * 30;
		int refreshExp = 60 * 60 * 24;
		String secret = "abcedfghijklmnopqrstuvwxyzabcedfghijklmnopqrstuvwxyzabcedfghijklmnopqrstuvwxyzabcedfghijklmnopqrstuvwxyzabcedfghijklmnopqrstuvwxyzabcedfghijklmnopqrstuvwxyz";
		GithubUserInformation userInfo = new GithubUserInformation(
			new GithubId(1L),
			"loginName",
			"email",
			"avatarUrl",
			"nodeId",
			"name"
		);
		GithubAccessToken accessTokens = new GithubAccessToken(
			"accessToken", "Bearer"
		);
		GithubUser user = new GithubUser(userInfo, accessTokens);

		// when

		AuthToken authToken = user.generateTokens(exp, refreshExp, secret);

		// then
		Key key = Keys.hmacShaKeyFor(secret.getBytes());

		Claims payload = Jwts.parser()
			.setSigningKey(key)
			.build()
			.parseClaimsJws(authToken.getAccessToken())
			.getPayload();
		assertNotNull(authToken);
		assertThat(payload.get("nickName")).isEqualTo(user.getMetaData().getLoginName());
		assertThat(payload.get("email")).isEqualTo(user.getMetaData().getEmail());
		assertThat(payload.get("avatarUrl")).isEqualTo(user.getMetaData().getAvatarUrl());
		assertThat(payload.get("name")).isEqualTo(user.getMetaData().getName());
		assertThat(payload.get("id")).isEqualTo(user.getId());

	}

}