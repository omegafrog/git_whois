package org.omegafrog.git_whois.user.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.omegafrog.git_whois.global.Util;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "member")
public class User {

	@Id
	private final String id = "user-"+ UUID.randomUUID();

	@Embedded
	private GithubUserInformation metaData;

	// github api 호출을 위해 사용
	@Embedded
	private GithubAccessToken githubAccessToken;

	public User() {
	}

	public User(GithubUserInformation metaData, GithubAccessToken githubAccessToken) {
		this.githubAccessToken = githubAccessToken;
		this.metaData = metaData;
	}

	public String getId() {
		return id;
	}

	public GithubUserInformation getMetaData() {
		return metaData;
	}

	public GithubAccessToken getGithubAccessToken() {
		return githubAccessToken;
	}

	public AuthToken generateTokens(int exp, int refreshExp, String secret) {
		Map<String, Object> claims = new HashMap<>();

		claims.put("id", this.getId());
		claims.put("name", this.getMetaData().getName());
		claims.put("nickName", this.getMetaData().getLoginName());
		claims.put("avatarUrl", this.getMetaData().getAvatarUrl());
		claims.put("email", this.getMetaData().getEmail());
		claims.put("jti", UUID.randomUUID().toString());
		String[] tokens = Util.Jwt.generateTokens(claims, exp, refreshExp, secret);
		return new AuthToken(tokens[0], tokens[1]);
	}

}
