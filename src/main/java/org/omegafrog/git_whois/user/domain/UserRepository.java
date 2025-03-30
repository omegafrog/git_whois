package org.omegafrog.git_whois.user.domain;

public interface UserRepository {
	boolean existByGithubId(GithubId id);

	GithubUser save(GithubUser user);

	GithubUser findByGithubId(GithubId id);
}
