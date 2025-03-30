package org.omegafrog.git_whois.user.domain;

import java.util.UUID;

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
	private UserInformation metaData;

	// github api 호출을 위해 사용
	@Embedded
	private GithubAccessToken githubAccessToken;

	public User() {
	}

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
