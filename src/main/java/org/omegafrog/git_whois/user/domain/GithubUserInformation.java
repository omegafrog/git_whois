package org.omegafrog.git_whois.user.domain;

import java.util.Objects;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;

@Embeddable
public class GithubUserInformation extends UserInformation {
	@Embedded
	@AttributeOverrides(
		@AttributeOverride(name = "id", column = @Column(name = "github_id", nullable = false, unique = true))
	)
	private GithubId githubId;
	@Column(nullable = false)
	private String loginName;
	@Column(nullable = false)
	private String avatarUrl;
	@Column(nullable = false)
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

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof GithubUserInformation that))
			return false;
		return Objects.equals(githubId, that.githubId) && Objects.equals(loginName, that.loginName)
			&& Objects.equals(avatarUrl, that.avatarUrl) && Objects.equals(nodeId, that.nodeId)
			&& Objects.equals(name, that.name) && Objects.equals(email, that.email);
	}

	@Override
	public int hashCode() {
		return Objects.hash(githubId, loginName, avatarUrl, nodeId, email, name);
	}
}
