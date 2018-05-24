package com.dailynews.DailyNews.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("register")
public class SignupController {

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String displayUserSignup(Model model){
		model.addAttribute("title", "Sign-up");
		return "usersignup";
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	public String processUserSignup(){
		return "redirect:/Register";
	}
}
