package com.dailynews.DailyNews.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("Login")
public class LoginController {

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String displayLoginForm(Model model) {
		model.addAttribute("title", "Login");
		return "login";
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	public String processLoginForm() {
		return "redirect:/Login";
	}
}
