package com.number47.learn.demo.controller;

import com.number47.learn.common.Constant;
import com.number47.learn.demo.entity.ImageCode;
import com.number47.learn.demo.service.ValidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author number47
 * @date 2019/10/16 01:08
 * @description
 */
@RestController
public class ValidateController {

	@Autowired
	private ValidateService validateService;

	private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

	/**
	 * 生成的验证码对象存储到Session中
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@GetMapping("/code/image")
	public void createCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ImageCode imageCode = validateService.createImageCode();
		sessionStrategy.setAttribute(new ServletWebRequest(request), Constant.SESSION_KEY_IMAGE_CODE, imageCode);
		ImageIO.write(imageCode.getImage(), "jpeg", response.getOutputStream());
	}
}
