package com.dailynews.DailyNews.controllers;


import com.dailynews.DailyNews.models.rssfeeds.article.ArticleFetcher;
import com.dailynews.DailyNews.models.rssfeeds.rsslink.RssLinkFactory;
import com.dailynews.DailyNews.models.user.User;
import jdk.nashorn.internal.ir.RuntimeNode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("managefeeds")
public class ManageController {

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String displayManageFeedForm(Model model){
		model.addAttribute("title", "Manage Forms");
		model.addAttribute("publishers", RssLinkFactory.getInstance().getPublishers());
		return "managefeeds";
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	public String processManageFeedForm(){
		User user = User.getInstance();



		return "redirect:/ManageFeeds";
	}
}
