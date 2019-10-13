package com.number47.learn.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author number47
 * @date 2019/10/14 02:29
 * @description
 */
@RestController
public class BrowserSecurityController {
	//Spring Security提供的用于缓存请求的对象，通过调用它的getRequest方法可以获取到本次请求的HTTP信息
	private RequestCache requestCache = new HttpSessionRequestCache();

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@GetMapping("/authentication/require")
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public String requireAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		if (savedRequest != null) {
			String targetUrl = savedRequest.getRedirectUrl();
			if (StringUtils.endsWithIgnoreCase(targetUrl, ".html"))
				redirectStrategy.sendRedirect(request, response, "/login.html");
		}
		return "访问的资源需要身份认证！";
	}
}
