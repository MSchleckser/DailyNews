package com.dailynews.DailyNews.controllers;

import com.dailynews.DailyNews.models.user.User;
import com.dailynews.DailyNews.models.user.UserDao;
import com.dailynews.DailyNews.models.user.salt.SaltDao;
import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Controller
@RequestMapping("login")
public class LoginController {

	@Autowired
	private UserDao uDao;

	@Autowired
	private SaltDao sDao;

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

		if(((username = request.getParameter("username")) == null) ||
			((password = request.getParameter("password")) == null))
			return "improperly formatted POST request.";

		id = uDao.getUserId(username);

		if(id == null)
			return "Username or password is invalid";

		User user = uDao.findById(id).get();

		if(!user.comparePassword(password, sDao))
			return "Username or password is invalid";

		session.setAttribute("userId", id);
		session.setAttribute("role", uDao.findById(id).get().getRole().getName());
		return "success";
	}

	@RequestMapping(value = "getUsername", method = RequestMethod.GET)
	@ResponseBody
	public String getUsername(HttpSession session){
		if(session.getAttribute("userId") == null)
			return "not logged in";

		Optional<User> optionalUser = uDao.findById((Integer) session.getAttribute("userId"));

		if(!optionalUser.isPresent())
			return "Unable to find user";

		return optionalUser.get().getUsername();
	}

	@RequestMapping(value = "getRole", method = RequestMethod.GET)
	@ResponseBody
	public String getRole(HttpSession session){
		if(session.getAttribute("role") == null)
			return "Not logged in.";

		return (String)session.getAttribute("role");
	}

	@RequestMapping(value = "check", method = RequestMethod.GET)
	@ResponseBody
	public String checkLoggedIn(HttpSession session){
		return session.getAttribute("userId") == null ? "false" : "true";
	}
}
