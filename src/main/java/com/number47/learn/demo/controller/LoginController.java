package com.number47.learn.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author number47
 * @date 2019/10/14 03:13
 * @description
 */
@Controller
public class LoginController {

	@PostMapping("/login")
	public String login(){
		return "user/user";
	}

	@GetMapping({"/", "/index"})
	public String root(){
		return "index";
	}
}
