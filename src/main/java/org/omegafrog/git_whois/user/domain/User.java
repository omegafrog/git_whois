package org.omegafrog.git_whois.user.domain;

import java.util.UUID;


import jakarta.persistence.Id;

public class User {

	@Id
	private final String id = "user-"+ UUID.randomUUID();

	private UserInformation metaData;

	// github api 호출을 위해 사용
	private GithubAccessToken githubAccessToken;

	public User( GithubAccessToken githubAccessToken, UserInformation metaData) {
		this.githubAccessToken = githubAccessToken;
		this.metaData = metaData;
	}

	public String getId() {
		return id;
	}

	public UserInformation getMetaData() {
		return metaData;
	}

	public GithubAccessToken getGithubAccessToken() {
		return githubAccessToken;
	}


}
