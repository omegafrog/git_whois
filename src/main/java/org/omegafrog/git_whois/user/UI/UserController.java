package org.omegafrog.git_whois.user.UI;

import org.omegafrog.git_whois.global.response.ApiResponse;
import org.omegafrog.git_whois.user.application.UserAuthService;
import org.omegafrog.git_whois.user.domain.AuthToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {

	private final UserAuthService userRegisterService;

	public UserController(UserAuthService userRegisterService) {
		this.userRegisterService = userRegisterService;
	}

	@GetMapping("/login/oauth2/code")
	public ApiResponse<AuthToken> registerOrLogin(@RequestParam(name = "code") String code) {
		AuthToken tokens = userRegisterService.registerOrLoginWithGithub(code);

		return ApiResponse.success("로그인 성공.", tokens);
	}

}
