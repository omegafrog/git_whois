package org.omegafrog.git_whois.user.domain;

public class GithubId {
	private Long id;

	public GithubId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	protected GithubId() {
	}
}
