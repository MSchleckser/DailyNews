package com.dailynews.DailyNews.controllers;

import com.dailynews.DailyNews.models.user.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@RequestMapping("login")
public class LoginController {

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String displayLoginForm(Model model) {
		System.out.println("display");
		model.addAttribute("title", "Login");
		model.addAttribute(new User());
		return "login";
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	public String processLoginForm(@ModelAttribute @Valid User nUser, Errors errors, Model model) {
		if(errors.hasErrors()){
			System.out.println(errors.getErrorCount());
		}
		System.out.println("test");

		model.addAttribute("title", "Login");
		return "redirect:/login";
	}
}
