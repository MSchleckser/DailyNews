package com.dailynews.DailyNews.controllers;

import com.dailynews.DailyNews.models.user.User;
import com.dailynews.DailyNews.models.user.UserDao;
import com.dailynews.DailyNews.models.user.role.RoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.swing.text.View;
import javax.validation.Valid;
import java.security.URIParameter;

@Controller
@RequestMapping("register")
public class SignupController {

	@Autowired
	private UserDao uDao;

	@Autowired
	private RoleDao rDao;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String displayUserSignup(Model model){
		model.addAttribute("title", "Sign-up");
		model.addAttribute("user", new User());
		return "usersignup";
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	public String processUserSignup(@ModelAttribute @Valid User user){

		if(uDao.getUserId(user.getUsername()) != null){
			return "redirect:/register?errors=Username already exists&username="+user.getUsername();
		}

		user.setRole(rDao.findById(1).get());
		uDao.save(user);

		return "redirect:/login";
	}
}
