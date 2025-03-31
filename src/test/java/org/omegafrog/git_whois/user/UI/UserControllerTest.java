package org.omegafrog.git_whois.user.UI;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.omegafrog.git_whois.user.application.UserReadService;
import org.omegafrog.git_whois.user.domain.GithubAccessToken;
import org.omegafrog.git_whois.user.domain.GithubId;
import org.omegafrog.git_whois.user.domain.GithubUser;
import org.omegafrog.git_whois.user.domain.GithubUserInformation;
import org.omegafrog.git_whois.user.domain.OAuthProvider;
import org.omegafrog.git_whois.user.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private OAuthProvider oAuthProvider;

	@Autowired
	private UserReadService userReadService;

	@Autowired
	private UserRepository userRepository;

	@BeforeEach
	void tearDown() {
		userRepository.clear();
	}

	@Test
	@DisplayName("처음 github에 로그인하려고 할 때는 회원가입 이후에 로그인이 진행되어야 한다.")
	void githubLogin() throws Exception {
		// given
		// github로부터 리다이렉트된 임시 code가 포함된 값으로 요청이 들어온다.
		String code = "b80274f8103a43a60f01";
		String redirectedUrl = "http://localhost:8080/login/oauth2/code?code=" + code;

		// when

		GithubAccessToken githubAccessToken = new GithubAccessToken("githubAccessToken", "Bearer");
		Mockito.when(oAuthProvider.requestAccessToken(code))
			.thenReturn(githubAccessToken);

		GithubUserInformation userInformation = new GithubUserInformation(
			new GithubId(1L),
			"loginName",
			"email",
			"avatarUrl",
			"nodeId",
			"name"
		);
		Mockito.when(oAuthProvider.getUserInformation(githubAccessToken))
			.thenReturn(userInformation);

		ResultActions result = mockMvc.perform(get(redirectedUrl));

		//then
		// 회원가입하여 생성된 유저가 제대로 생성되었는지 확인
		GithubUser newUser = userReadService.loadGithubUserByGithubId(new GithubId(1L));

		assertThat(newUser).isNotNull();
		assertThat(newUser.getGithubAccessToken()).isEqualTo(githubAccessToken);
		assertThat(newUser.getMetaData()).isEqualTo(userInformation);

		// 로그인의 결과로 accessToken, refreshToken이 올바르게 반환되었는지 확인
		result
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.message").value("로그인 성공."))
			.andExpect(jsonPath("$.data.accessToken").isNotEmpty())
			.andExpect(jsonPath("$.data.refreshToken").isNotEmpty());

	}

	@Test
	@DisplayName("이미 등록된 유저로 로그인하는 경우 회원가입하지 말아야 한다. 또한 재로그인시 액세스 토큰이 변경되어야 한다.")
	void doNotRegisterExistUserTest() throws Exception {
		// given

		// 회원가입한 유저를 생성 및 저장
		GithubUserInformation userInformation = new GithubUserInformation(
			new GithubId(1L),
			"loginName",
			"email",
			"avatarUrl",
			"nodeId",
			"name"
		);
		GithubAccessToken githubAccessToken = new GithubAccessToken("githubAccessToken", "Bearer");

		GithubUser user = userRepository.save(new GithubUser(userInformation, githubAccessToken));

		// when

		// 재로그인시 새로운 accessToken을 얻도록 모킹
		GithubAccessToken newGithubAccessToken = new GithubAccessToken("newGithubAccessToken", "Bearer");

		String code = "b80274f8103a43a60f01";
		String redirectedUrl = "http://localhost:8080/login/oauth2/code?code=" + code;
		Mockito.when(oAuthProvider.requestAccessToken(code))
			.thenReturn(newGithubAccessToken);

		// 이미 존재하는 유저의 정보를 새로 발급받은 accessToken으로 호출하도록 모킹
		Mockito.when(oAuthProvider.getUserInformation(newGithubAccessToken))
			.thenReturn(userInformation);

		// then
		ResultActions action = mockMvc.perform(get(redirectedUrl));

		// 회원가입된 사람은 한명뿐이어야 함.
		Long count = userRepository.count();
		assertThat(count).isEqualTo(1L);

		// 새로운 액세스 토큰으로 변경되었는지 확인
		GithubUser existedUser = userReadService.loadGithubUserByGithubId(userInformation.getGithubId());
		assertThat(existedUser.getGithubAccessToken()).isEqualTo(newGithubAccessToken);

		action
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.message").value("로그인 성공."))
			.andExpect(jsonPath("$.data.accessToken").isNotEmpty())
			.andExpect(jsonPath("$.data.refreshToken").isNotEmpty());

	}
}