package com.dailynews.DailyNews.controllers;

import com.dailynews.DailyNews.models.user.User;
import com.dailynews.DailyNews.models.user.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("login")
public class LoginController {

	@Autowired
	private UserDao uDao;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String displayLoginForm(Model model) {
		model.addAttribute(new User());
		model.addAttribute("title", "Login");
		return "login";
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	public String processLoginForm(@RequestParam String username, @RequestParam String password,
								   HttpSession session) {

		Integer id = uDao.getUserId(username, password);

		if(id == null) {
			return "redirect:/login?errors=Password or Username is invalid&username=" + username;
		}

		session.setAttribute("userId", id);
		session.setAttribute("role", uDao.findById(id).get().getRole().getName());
		return "redirect:/";
	}
}
