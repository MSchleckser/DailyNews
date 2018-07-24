package com.dailynews.DailyNews.controllers;

import com.dailynews.DailyNews.models.rssfeeds.article.Article;
import com.dailynews.DailyNews.models.rssfeeds.article.ArticleFetcher;
import com.dailynews.DailyNews.models.rssfeeds.rsslink.RssLink;
import com.dailynews.DailyNews.models.rssfeeds.rsslink.publisher.Publisher;
import com.dailynews.DailyNews.models.rssfeeds.rsslink.publisher.PublisherDao;
import com.dailynews.DailyNews.models.user.User;
import com.dailynews.DailyNews.models.user.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Random;

@Controller
public class IndexController {

	@Autowired
	private UserDao uDao;

	@Autowired
	private PublisherDao pDao;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String displayIndex(Model model, HttpSession session){
		model.addAttribute("title", "Daily News");
		model.addAttribute("username", session.getAttribute("username"));

		return "index";
	}
}
