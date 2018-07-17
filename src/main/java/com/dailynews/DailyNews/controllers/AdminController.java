package com.dailynews.DailyNews.controllers;

import com.dailynews.DailyNews.models.rssfeeds.rsslink.RssLink;
import com.dailynews.DailyNews.models.rssfeeds.rsslink.RssLinkDao;
import com.dailynews.DailyNews.models.rssfeeds.rsslink.publisher.Publisher;
import com.dailynews.DailyNews.models.rssfeeds.rsslink.publisher.PublisherDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private PublisherDao pDao;

	@Autowired
	private RssLinkDao rssDao;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String displayAdmin(Model model, HttpSession session){
		Object role = session.getAttribute("role");
		if(role == null || !role.equals("ADMIN"))
			return "redirect:/";

		model.addAttribute("title", "Admin Panel");
		model.addAttribute(new Publisher());
		model.addAttribute("publishers", pDao.findAll());
		return "admin";
	}

	@RequestMapping(value = "/addPublisher", method = RequestMethod.POST)
	@ResponseBody
	public String processAddPublisher(@RequestParam("publisher") String publisherName){

		if(pDao.doesPublisherExist(publisherName))
			return publisherName + " already exists.";

		Publisher publisher = new Publisher();
		publisher.setName(publisherName);

		pDao.save(publisher);

		return "added";
	}


	@RequestMapping(value = "/removeLink", method = RequestMethod.POST)
	public String removeLink(@RequestParam ArrayList<Integer> rssIds){

		for(Integer id : rssIds){
			rssDao.deleteById(id);
		}

		return "redirect:/admin";
	}
}
