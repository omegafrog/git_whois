package org.omegafrog.git_whois.user.application;

import org.omegafrog.git_whois.user.domain.AuthToken;
import org.omegafrog.git_whois.user.domain.GithubAccessToken;
import org.omegafrog.git_whois.user.domain.GithubId;
import org.omegafrog.git_whois.user.domain.GithubUser;
import org.omegafrog.git_whois.user.domain.GithubUserInformation;
import org.omegafrog.git_whois.user.domain.OAuthProvider;
import org.omegafrog.git_whois.user.domain.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
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

	/**
	 * 깃허브 OAuth2를 이용해 user 정보를 가져와 서버 api 인증/인가를 위한 AuthToken을 반환하는 메서드
	 * @param code : github 로부터 리다이렉트한 결과로 받은 임시 code
	 * @return {@link AuthToken} 서버 인증/인가에 사용할 AccessToken/RefreshToken
	 */
	public AuthToken registerOrLoginWithGithub(String code) {

		GithubAccessToken githubAccessToken = (GithubAccessToken)oauthProvider.requestAccessToken(code);

		GithubUserInformation userInfo = (GithubUserInformation)oauthProvider.getUserInformation(githubAccessToken);
		GithubId id = userInfo.getGithubId();

		GithubUser user = new GithubUser(userInfo, githubAccessToken);

		if (user.canRegister(id, userRepository)) {
			user = userRepository.save(user);
		} else {
			user = userRepository.findByGithubId(id);
			user.changeAccessToken(githubAccessToken);
		}

		// token 생성
		return user.generateTokens(exp, refreshExp, secret);
	}

}
