package org.omegafrog.git_whois.user.application;

import org.omegafrog.git_whois.user.domain.AuthToken;
import org.omegafrog.git_whois.user.domain.GithubAccessToken;
import org.omegafrog.git_whois.user.domain.GithubId;
import org.omegafrog.git_whois.user.domain.GithubUserInformation;
import org.omegafrog.git_whois.user.domain.OAuthProvider;
import org.omegafrog.git_whois.user.domain.User;
import org.omegafrog.git_whois.user.domain.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserAuthService {

	private final UserRepository userRepository;
	private final OAuthProvider oauthProvider;


	@Value("${custom.jwt.secret}")
	private String secret;

	@Value("${custom.jwt.exp}")
	private int exp;

	@Value("${custom.jwt.refresh-exp}")
	private int refreshExp;

	public UserAuthService(UserRepository userRepository, OAuthProvider oauthProvider) {
		this.userRepository = userRepository;
		this.oauthProvider = oauthProvider;
	}

	public AuthToken registerOrLogin(String code) {

		GithubAccessToken githubAccessToken = (GithubAccessToken)oauthProvider.requestAccessToken(code);

		GithubUserInformation userInfo = (GithubUserInformation)oauthProvider.getUserInformation(githubAccessToken);
		GithubId id = userInfo.getGithubId();

		User user;

		if(canRegister(id, userRepository)){
			user = userRepository.save(new User(userInfo, githubAccessToken));
		}
		else{
			user = userRepository.findByGithubId(id);
		}

		// token 생성
		return user.generateTokens(exp, refreshExp, secret);
	}


	private boolean canRegister(GithubId githubId, UserRepository userRepository) {
		return !userRepository.existByGithubId(githubId);
	}
}
