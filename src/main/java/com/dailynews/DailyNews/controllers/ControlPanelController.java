package com.dailynews.DailyNews.controllers;

import com.dailynews.DailyNews.models.user.User;
import com.dailynews.DailyNews.models.user.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

@Controller
@RequestMapping("ControlPanel")
public class ControlPanelController {

	@Autowired
	private UserDao uDao;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String displayControlPanel(Model model, HttpSession session) {
		if((session.getAttribute("userId")) == null)
			return "redirect:";

		model.addAttribute("title", "Control Panel");
		return "ControlPanel";
	}

	@RequestMapping(value = "ResetPassword", method = RequestMethod.POST)
	@ResponseBody
	public String resetPassword(HttpServletRequest request, HttpSession session){
		String oldPassword;
		String newPassword;

		if((oldPassword = request.getParameter("OldPassword")) == null ||
				(newPassword = request.getParameter("newPassword")) == null)
			return "null parameter";

		Optional<User> optionalUser = uDao.findById((Integer)session.getAttribute("userId"));

		if(!(optionalUser.isPresent()))
			return "user does not exist";

		User u = optionalUser.get();

		if(!u.getPassword().equals(oldPassword))
			return "Old password does not match";

		u.setPassword(newPassword);
		uDao.save(u);

		return "success";
	}
}
