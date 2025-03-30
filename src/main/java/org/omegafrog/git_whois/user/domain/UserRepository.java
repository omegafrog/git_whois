package org.omegafrog.git_whois.user.domain;

public interface UserRepository {
	boolean existByGithubId(GithubId id);

	User save(User user);

	User findByGithubId(GithubId id);
}
