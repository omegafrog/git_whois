package org.omegafrog.git_whois.user.infra;

import org.omegafrog.git_whois.user.domain.User;
import org.omegafrog.git_whois.user.domain.UserRepository;
import org.springframework.stereotype.Repository;

@Repository
public class JpaUserRepositoryImpl implements UserRepository {



	@Override
	public boolean existByGithubId(Long id) {
		return false;
	}

	@Override
	public User save(User user) {
		return null;
	}

	@Override
	public User findByGithubId(Long id) {
		return null;
	}
}
