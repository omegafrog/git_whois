package org.omegafrog.git_whois.user.infra;

import org.omegafrog.git_whois.user.domain.GithubId;
import org.omegafrog.git_whois.user.domain.User;
import org.omegafrog.git_whois.user.domain.UserRepository;
import org.springframework.stereotype.Repository;

@Repository
public class CustomUserRepositoryImpl implements UserRepository {

	private final JpaUserRepository jpaUserRepository;

	public CustomUserRepositoryImpl(JpaUserRepository jpaUserRepository) {
		this.jpaUserRepository = jpaUserRepository;
	}

	@Override
	public boolean existByGithubId(GithubId id) {
		return jpaUserRepository.existsByMetaData_GithubId(id);
	}

	@Override
	public User save(User user) {
		return jpaUserRepository.save(user);
	}

	@Override
	public User findByGithubId(GithubId id) {
		return jpaUserRepository.findByMetaData_GithubId(id);
	}
}
