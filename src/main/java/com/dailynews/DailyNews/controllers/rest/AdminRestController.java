package com.dailynews.DailyNews.controllers.rest;

import com.dailynews.DailyNews.models.rssfeeds.rsslink.RssLink;
import com.dailynews.DailyNews.models.rssfeeds.rsslink.RssLinkDao;
import com.dailynews.DailyNews.models.rssfeeds.rsslink.publisher.Publisher;
import com.dailynews.DailyNews.models.rssfeeds.rsslink.publisher.PublisherDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.MalformedURLException;
import java.net.URL;

@Controller
@RequestMapping("rest/admin")
public class AdminRestController {

	@Autowired
	PublisherDao pDao;

	@Autowired
	RssLinkDao rssDao;

	@RequestMapping("addLink")
	@ResponseBody
	public String addLink(@RequestParam("PubId") int publisherId,
						  @RequestParam("Title") String title,
						  @RequestParam("Link") String link){

		if(!pDao.existsById(publisherId))
			return "Link already exists";

		RssLink rssLink = new RssLink();

		try {
			rssLink.setRssUrl(new URL(link));
			rssLink.setTitle(title);
			rssDao.save(rssLink);
		} catch (MalformedURLException e) {
			System.out.println(e);
			return "bad URL";
		}

		Publisher p = pDao.findById(publisherId).get();
		p.links.add(rssLink);
		pDao.save(p);

		return "added";
	}

}
