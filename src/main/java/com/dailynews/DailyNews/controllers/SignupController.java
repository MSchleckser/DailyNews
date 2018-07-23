package com.dailynews.DailyNews.controllers;

import com.dailynews.DailyNews.models.user.User;
import com.dailynews.DailyNews.models.user.UserDao;
import com.dailynews.DailyNews.models.user.role.RoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
	@ResponseBody
	public String processUserSignup(HttpServletRequest request){

		String username = request.getParameter("username");
		String password = request.getParameter("password");

		String error = "";
		if(uDao.getUserId(username) != null){
			error+="Username already exists.";
		} else if (username.isEmpty()){
			error += "Please enter a valid username.";
		}

		if(password.length() < 8){
			if(!error.isEmpty()){
				error += " ";
			}
			error += "Password must be over 8 characters long.";
		}

		if(error.isEmpty()){
			error = registerUser(username, password);
		}

		return error.isEmpty() ? registerUser(username, password) : error;
	}

	private String registerUser(String username, String password){
		User u = new User();
		u.setUsername(username);
		u.setPassword(password);
		u.setRole(rDao.findById(1).get());

		uDao.save(u);

		return "success";
	}
}
