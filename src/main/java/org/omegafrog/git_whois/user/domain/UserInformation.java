package org.omegafrog.git_whois.user.domain;

public class UserInformation {
	private final Long githubId;
	private final String loginName;
	private final String email;
	private final String avatarUrl;
	private final String nodeId;
	private final String name;

	public UserInformation(Long githubId, String loginName, String email, String avatarUrl, String nodeId, String name) {
		this.githubId = githubId;
		this.loginName = loginName;
		this.email = email;
		this.avatarUrl = avatarUrl;
		this.nodeId = nodeId;
		this.name = name;
	}

	public Long getGithubId() {
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
