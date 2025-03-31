package org.omegafrog.git_whois.user.application;

import org.omegafrog.git_whois.user.domain.GithubId;
import org.omegafrog.git_whois.user.domain.GithubUser;
import org.omegafrog.git_whois.user.domain.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserReadService {

	private final UserRepository userRepository;

	public UserReadService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public GithubUser loadGithubUserByGithubId(GithubId githubId) {
		return userRepository.findByGithubId(githubId);
	}
}
