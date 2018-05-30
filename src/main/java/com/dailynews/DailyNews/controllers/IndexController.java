package com.dailynews.DailyNews.controllers;

import com.dailynews.DailyNews.models.rssfeeds.article.Article;
import com.dailynews.DailyNews.models.rssfeeds.article.ArticleFetcher;
import com.dailynews.DailyNews.models.rssfeeds.rsslink.RssLink;
import com.dailynews.DailyNews.models.rssfeeds.rsslink.RssLinkDao;
import com.dailynews.DailyNews.models.user.User;
import com.dailynews.DailyNews.models.user.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Random;

@Controller
public class IndexController {

	@Autowired
	private UserDao uDao;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String displayIndex(Model model, HttpSession session){

		ArrayList<Article> articles;
		if(session.getAttribute("userId") != null) {
			articles = getArticles((Integer) session.getAttribute("userId"));
			model.addAttribute("feedList", articles);
		}

		model.addAttribute("title", "Daily News");
		model.addAttribute("username", session.getAttribute("username"));

		return "index";
	}

	private ArrayList<Article> getArticles(Integer id){
		ArticleFetcher fetcher = new ArticleFetcher();
		User u = uDao.findById(id).get();

		for(RssLink link : u.getRssLinks()){
			fetcher.fetchFeed(link.getRssUrl());
		}

		return fetcher.getArticleList();
	}
}
