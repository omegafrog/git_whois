package org.omegafrog.git_whois.user.domain;

public class UserInformation {
	private final String loginName;
	private final String email;
	private final String avatarUrl;
	private final String nodeId;
	private final String name;


	public UserInformation(String loginName, String email, String avatarUrl, String nodeId, String name) {
		this.loginName = loginName;
		this.email = email;
		this.avatarUrl = avatarUrl;
		this.nodeId = nodeId;
		this.name = name;
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
