package org.omegafrog.git_whois.user.domain;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;

@Embeddable
public class UserInformation {
	@Embedded
	@AttributeOverrides(@AttributeOverride(name = "id", column = @Column(name = "github_id")))
	private  GithubId githubId;
	private  String loginName;
	private  String email;
	private  String avatarUrl;
	private  String nodeId;
	private  String name;

	public UserInformation(GithubId githubId, String loginName, String email, String avatarUrl, String nodeId,
		String name) {

		this.githubId = githubId;
		this.loginName = loginName;
		this.email = email;
		this.avatarUrl = avatarUrl;
		this.nodeId = nodeId;
		this.name = name;
	}

	protected UserInformation() {

	}

	public GithubId getGithubId() {
		return githubId;
	}

	public String getLoginName() {
		return loginName;
	}

	public String getEmail() {
		return email;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public String getNodeId() {
		return nodeId;
	}

	public String getName() {
		return name;
	}
}
