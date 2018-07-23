package com.dailynews.DailyNews.controllers;


import com.dailynews.DailyNews.models.rssfeeds.rsslink.RssLink;
import com.dailynews.DailyNews.models.rssfeeds.rsslink.RssLinkDao;
import com.dailynews.DailyNews.models.rssfeeds.rsslink.publisher.PublisherDao;
import com.dailynews.DailyNews.models.user.User;
import com.dailynews.DailyNews.models.user.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Controller
@RequestMapping("managefeeds")
public class ManageController {

	@Autowired
	private PublisherDao pDao;

	@Autowired
	private UserDao uDao;

	@Autowired
	private RssLinkDao rssDao;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String displayManageFeedForm(Model model, HttpSession session){
		if(session.getAttribute("userId") == null)
			return "redirect:";

		model.addAttribute("title", "Manage Forms");
		model.addAttribute("publishers", pDao.findAll());
		model.addAttribute("userFeeds", uDao.findById((Integer)session.getAttribute("userId")).get().getRssLinks());
		return "managefeeds";
	}

	@RequestMapping(value = "add", method = RequestMethod.POST)
	public String processManageFeedForm(@RequestParam ArrayList<Integer> addRssIds, HttpSession session){

		User u = uDao.findById((Integer)session.getAttribute("userId")).get();

		rssDao.findAllById(addRssIds).forEach(u.getRssLinks()::add);
		uDao.save(u);

		return "redirect:/managefeeds";
	}

	@RequestMapping(value = "remove", method = RequestMethod.POST)
	public String removeManageFeedForm(@RequestParam ArrayList<Integer> removeRssIds, HttpSession session){
		User u = uDao.findById((Integer)session.getAttribute("userId")).get();
		u.removeAllRssLinksById(removeRssIds);
		uDao.save(u);

		return "redirect:/managefeeds";
	}
}
