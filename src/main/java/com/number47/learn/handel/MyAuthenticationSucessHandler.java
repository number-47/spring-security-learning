package com.number47.learn.handel;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author number47
 * @date 2019/10/14 02:55
 * @description 自定义登录成功逻辑
 */
@Component
public class MyAuthenticationSucessHandler implements AuthenticationSuccessHandler {

	// private RequestCache requestCache = new HttpSessionRequestCache();

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	//
	// @Autowired
	// private ObjectMapper mapper;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
										Authentication authentication) throws IOException {
		// response.setContentType("application/json;charset=utf-8");
		// response.getWriter().write(mapper.writeValueAsString(authentication));
		// SavedRequest savedRequest = requestCache.getRequest(request, response);
		// System.out.println(savedRequest.getRedirectUrl());
		// redirectStrategy.sendRedirect(request, response, savedRequest.getRedirectUrl());
		redirectStrategy.sendRedirect(request, response, "/user");
	}
}
