package com.number47.learn.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author number47
 * @date 2019/10/14 03:13
 * @description
 */
@Controller
public class LoginController {

	@GetMapping("/login")
	public String login(){
		return "login";
	}

	@GetMapping({"/", "/index"})
	public String root(){
		return "index";
	}
}
