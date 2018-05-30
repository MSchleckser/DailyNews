package com.dailynews.DailyNews.controllers;

import com.dailynews.DailyNews.models.rssfeeds.rsslink.RssLink;
import com.dailynews.DailyNews.models.rssfeeds.rsslink.RssLinkDao;
import com.dailynews.DailyNews.models.rssfeeds.rsslink.publisher.Publisher;
import com.dailynews.DailyNews.models.rssfeeds.rsslink.publisher.PublisherDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
	public String processAddPublisher(@ModelAttribute @Valid Publisher publisher){

		pDao.save(publisher);

		return "redirect:/admin";
	}

	@RequestMapping(value = "/removePublisher", method = RequestMethod.POST)
	public String processRemovePublisher(@RequestParam ArrayList<Integer> idList){

		for(Integer id : idList){
			pDao.deleteById(id);
		}

		return "redirect:/admin";
	}

	@RequestMapping(value = "/addLink", method = RequestMethod.POST)
	public String processAddLink(@RequestParam Integer publisherId,
								 @RequestParam String linkStr,
								 @RequestParam String linkTitle){
		if(!pDao.existsById(publisherId))
			return "redirect:/admin";

		RssLink link = new RssLink();

		try {
			link.setRssUrl(new URL(linkStr));
			link.setTitle(linkTitle);
			rssDao.save(link);
		} catch (MalformedURLException e) {
			return "redirect:/admin";
		}

		Publisher p = pDao.findById(publisherId).get();
		p.links.add(link);
		pDao.save(p);

		return "redirect:/admin";
	}

	@RequestMapping(value = "/removeLink", method = RequestMethod.POST)
	public String removeLink(@RequestParam ArrayList<Integer> rssIds){

		for(Integer id : rssIds){
			rssDao.deleteById(id);
		}

		return "redirect:/admin";
	}
}
