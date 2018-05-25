package com.dailynews.DailyNews.controllers;

import com.dailynews.DailyNews.models.rssfeeds.article.ArticleFetcher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String displayIndex(Model model){
		ArticleFetcher fetcher = new ArticleFetcher();
		fetcher.fetchFeed("https://www.nasa.gov/rss/dyn/breaking_news.rss");
		fetcher.fetchFeed("http://spacenews.com/feed/");
		model.addAttribute("feedList", fetcher.getArticleList());

		return "index";
	}

}
