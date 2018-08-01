package com.dailynews.DailyNews.controllers;

import com.dailynews.DailyNews.models.user.User;
import com.dailynews.DailyNews.models.user.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
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
	@ResponseBody
	public String loginUser(HttpServletRequest request,
							HttpSession session){

		String username;
		String password;
		Integer id = null;

		if(((username = request.getParameter("username")) != null) &&
			((password = request.getParameter("password")) != null)){

			id = uDao.getUserId(username, password);
		} else {
			return "improperly formatted POST request.";
		}

		if(id == null) {
			return "Username or password is invalid";
		}

		session.setAttribute("userId", id);
		session.setAttribute("role", uDao.findById(id).get().getRole().getName());
		return "success";
	}

	@RequestMapping(value = "check", method = RequestMethod.GET)
	@ResponseBody
	public String isLoggedIn(HttpSession session){
		if(session.getAttribute("userId") != null)
			return "true";

		return "false";
	}
}
