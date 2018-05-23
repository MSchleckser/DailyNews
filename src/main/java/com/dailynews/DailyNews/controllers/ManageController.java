package com.dailynews.DailyNews.controllers;


import jdk.nashorn.internal.ir.RuntimeNode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("ManageFeeds")
public class ManageController {

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String displayManageFeedForm(Model model){
		model.addAttribute("title", "Manage Forms");
		return "managefeeds";
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	public String processManageFeedForm(){
		return "redirect:/ManageFeeds";
	}
}
