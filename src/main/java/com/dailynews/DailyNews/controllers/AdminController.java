package com.dailynews.DailyNews.controllers;

import com.dailynews.DailyNews.models.rssfeeds.rsslink.RssLink;
import com.dailynews.DailyNews.models.rssfeeds.rsslink.RssLinkDao;
import com.dailynews.DailyNews.models.rssfeeds.rsslink.publisher.Publisher;
import com.dailynews.DailyNews.models.rssfeeds.rsslink.publisher.PublisherDao;
import jdk.nashorn.internal.runtime.options.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

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

	@RequestMapping(value = "/addFeed", method = RequestMethod.POST)
	@ResponseBody
	public String processAddFeed(HttpServletRequest request){
		int publisherId;
		String title;
		URL link;

		try{
			publisherId = Integer.valueOf(request.getParameter("publisherId"));
			title = request.getParameter("title");
			link = new URL(request.getParameter("link"));
		} catch (NumberFormatException nfe){
			return "Admin.addFeed could not parse publisherId to number";
		} catch (MalformedURLException e) {
			return "Admin.AddFeed badUrl provided";
		} catch (Exception e){
			System.out.println(e);
			return "unexpected error";
		}

		RssLink feed = new RssLink();
		feed.setRssUrl(link);
		feed.setTitle(title);

		Optional<Publisher> pOptional;

		if((pOptional = pDao.findById(publisherId)) != null){
			rssDao.save(feed);
			Publisher p = pOptional.get();
			p.links.add(feed);
			pDao.save(p);
		} else {
			System.out.println("Admin.AddFeed Unable to locate publisher with provided Id");
			return "Admin.AddFeed Unable to locate publisher with provided Id";
		}

		return Integer.toString(feed.getId());
	}

	@RequestMapping(value = "/addPublisher", method = RequestMethod.POST)
	@ResponseBody
	public String processAddPublisher(HttpServletRequest request){

		String publisherName = request.getParameter("publisher");

		if(pDao.doesPublisherExist(publisherName))
			return publisherName + " already exists.";

		Publisher publisher = new Publisher();
		publisher.setName(publisherName);

		pDao.save(publisher);

		return Integer.toString(publisher.getId());
	}

	@RequestMapping(value = "/removeFeed", method = RequestMethod.POST)
	@ResponseBody
	public String removeFeed(HttpServletRequest request){
		int id;

		try {
			id = Integer.valueOf(request.getParameter("id"));
		} catch (NumberFormatException nfe) {
			System.out.println(nfe);

			return "AdminController.RemoveFeed: Improperly formatted number";
		}

		if(rssDao.findById(id) == null){
			return "AdminController.RemoveFeed: Rss Feed does not exist.";
		}

		rssDao.deleteById(id);
		return "deleted";
	}

	@RequestMapping(value = "/removePublisher", method = RequestMethod.POST)
	@ResponseBody
	public  String processRemovePublisher(ServletRequest request){
		String error = "";

		int pubId = Integer.parseInt(request.getParameter("id"));

		Optional<Publisher> optionalPub = pDao.findById(pubId);

		if(optionalPub.isPresent()){
			pDao.deleteById(pubId);
			return "deleted";
		} else {
			error = "publisher does not exist.";
		}

		return error;
	}
}
