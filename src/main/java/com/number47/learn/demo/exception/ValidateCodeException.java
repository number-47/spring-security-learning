package com.number47.learn.demo.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author number47
 * @date 2019/10/16 01:28
 * @description 验证码类型的异常类
 */
public class ValidateCodeException extends AuthenticationException {

	private static final long serialVersionUID = -892502267111876015L;

	public ValidateCodeException(String message) {
		super(message);
	}
}
