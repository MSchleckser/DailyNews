package com.dailynews.DailyNews.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/logout")
public class LogoutController {

	@RequestMapping(value = "")
	public String logoutUser(HttpSession session){
		session.removeAttribute("userId");
		session	.removeAttribute("role");

		return "redirect:/";
	}
}
