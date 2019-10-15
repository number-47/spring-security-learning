package com.number47.learn.demo.filter;

import com.number47.learn.common.Constant;
import com.number47.learn.demo.controller.ValidateController;
import com.number47.learn.demo.entity.ImageCode;
import com.number47.learn.demo.exception.ValidateCodeException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;



import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author number47
 * @date 2019/10/16 01:30
 * @description 验证码校验的过滤器
 */
@Component
public class ValidateCodeFilter extends OncePerRequestFilter {

	@Autowired
	private AuthenticationFailureHandler authenticationFailureHandler;

	private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

	@Override
	protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
									FilterChain filterChain) throws ServletException, IOException {
		//判断了请求URL是否为/login 请求的方法是否为POST，是的话进行验证码校验逻辑
		if (StringUtils.equalsIgnoreCase("/login", httpServletRequest.getRequestURI())
				&& StringUtils.equalsIgnoreCase(httpServletRequest.getMethod(), "post")) {
			try {
				validateCode(new ServletWebRequest(httpServletRequest));
			} catch (ValidateCodeException e) {
				authenticationFailureHandler.onAuthenticationFailure(httpServletRequest, httpServletResponse, e);
				return;
			}
		}
		filterChain.doFilter(httpServletRequest, httpServletResponse);
	}

	/**
	 * 校验逻辑
	 * @param servletWebRequest
	 * @throws ServletRequestBindingException
	 */
	private void validateCode(ServletWebRequest servletWebRequest) throws ServletRequestBindingException {
		ImageCode codeInSession = (ImageCode) sessionStrategy.getAttribute(servletWebRequest, Constant.SESSION_KEY_IMAGE_CODE);
		String codeInRequest = ServletRequestUtils.getStringParameter(servletWebRequest.getRequest(), "imageCode");

		if (StringUtils.isBlank(codeInRequest)) {
			throw new ValidateCodeException("验证码不能为空！");
		}
		if (codeInSession == null) {
			throw new ValidateCodeException("验证码不存在！");
		}
		if (codeInSession.isExpire()) {
			sessionStrategy.removeAttribute(servletWebRequest, Constant.SESSION_KEY_IMAGE_CODE);
			throw new ValidateCodeException("验证码已过期！");
		}
		if (!StringUtils.equalsIgnoreCase(codeInSession.getCode(), codeInRequest)) {
			throw new ValidateCodeException("验证码不正确！");
		}
		sessionStrategy.removeAttribute(servletWebRequest, Constant.SESSION_KEY_IMAGE_CODE);

	}
}
