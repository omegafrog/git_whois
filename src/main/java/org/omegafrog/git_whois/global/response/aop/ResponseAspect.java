package org.omegafrog.git_whois.global.response.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.omegafrog.git_whois.global.response.ApiResponse;

import jakarta.servlet.http.HttpServletResponse;

@Aspect
public class ResponseAspect {

	private final HttpServletResponse response;

	public ResponseAspect(HttpServletResponse response) {
		this.response = response;
	}

	@Around("execution(* org.omegafrog.git_whois.user.UI.UserController.*(..))")
	public Object responseAspect(ProceedingJoinPoint joinPoint) throws Throwable {
		Object result = joinPoint.proceed();

		if (result instanceof ApiResponse<?> apiResponse) {
			response.setStatus(apiResponse.getCode());
		}
		return result;
	}
}
