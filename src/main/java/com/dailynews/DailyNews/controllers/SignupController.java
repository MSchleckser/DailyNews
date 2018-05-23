package com.dailynews.DailyNews.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("Register")
public class SignupController {

	@RequestMapping(value = "")
	public String displayUserSignup(Model model){
		model.addAttribute("title", "Sign-up");
		return "usersignup";
	}
}
