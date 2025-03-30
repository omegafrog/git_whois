package org.omegafrog.git_whois.user.domain;

public interface UserRepository {
	boolean existByGithubId(Long id);

	User save(User user);

	User findByGithubId(Long id);
}
