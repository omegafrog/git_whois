package org.omegafrog.git_whois.user.domain;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;

@Embeddable
public class GithubUserInformation extends UserInformation {
	@Embedded
	@AttributeOverrides(@AttributeOverride(name = "id", column = @Column(name = "github_id")))
	private GithubId githubId;
	private String loginName;
	private String avatarUrl;
	private String nodeId;

	public GithubUserInformation(GithubId githubId, String loginName, String email, String avatarUrl, String nodeId,
		String name) {
		super(email, name);
		this.githubId = githubId;
		this.loginName = loginName;
		this.avatarUrl = avatarUrl;
		this.nodeId = nodeId;
	}

	protected GithubUserInformation() {

	}

	public GithubId getGithubId() {
		return githubId;
	}

	public String getLoginName() {
		return loginName;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public String getNodeId() {
		return nodeId;
	}

}
