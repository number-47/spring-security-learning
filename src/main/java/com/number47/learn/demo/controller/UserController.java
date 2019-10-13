package com.number47.learn.demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

/**
 * @author number47
 * @date 2019/10/14 01:27
 * @description
 */
@Controller
public class UserController {

	@GetMapping("/user")
	public String user(@AuthenticationPrincipal Principal principal, Model model){
		model.addAttribute("username", principal.getName());
		return "user/user";
	}

}