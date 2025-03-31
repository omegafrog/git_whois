package org.omegafrog.git_whois.user.domain;

import java.util.Objects;

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

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof GithubId githubId))
			return false;
		return Objects.equals(id, githubId.id);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}
}
