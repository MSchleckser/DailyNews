package com.dailynews.DailyNews.controllers.rest;

import com.dailynews.DailyNews.models.comments.CommentContainer;
import com.dailynews.DailyNews.models.comments.CommentContainerDao;
import com.dailynews.DailyNews.models.rssfeeds.article.Article;
import com.dailynews.DailyNews.models.rssfeeds.article.ArticleFetcher;
import com.dailynews.DailyNews.models.xmlwrappers.ArticleXml;
import com.dailynews.DailyNews.models.xmlwrappers.PublisherXml;
import com.dailynews.DailyNews.models.xmlwrappers.UserXML;
import com.dailynews.DailyNews.models.rssfeeds.rsslink.RssLink;
import com.dailynews.DailyNews.models.rssfeeds.rsslink.RssLinkDao;
import com.dailynews.DailyNews.models.xmlwrappers.RssLinkXML;
import com.dailynews.DailyNews.models.rssfeeds.rsslink.publisher.Publisher;
import com.dailynews.DailyNews.models.rssfeeds.rsslink.publisher.PublisherDao;
import com.dailynews.DailyNews.models.user.User;
import com.dailynews.DailyNews.models.user.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("rest")
public class ManageRestController {

	@Autowired
	private RssLinkDao rssDao;

	@Autowired
	private CommentContainerDao ccDao;

	@Autowired
	private UserDao uDao;

	@Autowired
	private PublisherDao pDao;

	@RequestMapping(value = "feed/{feedId}")
	@ResponseBody
	public String getFeed(@PathVariable int feedId){
		StringWriter retString = new StringWriter();

		try {
			JAXBContext context = JAXBContext.newInstance(RssLinkXML.class);
			RssLinkXML restLink = RssLinkXML.convertRssLink(rssDao.findById(feedId).get());
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			m.marshal(restLink, retString);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return retString.toString();
	}

	@RequestMapping(value = "feed/all")
	@ResponseBody
	public String getAllFeeds(){

		String feeds = "<subscriptions>";
		StringWriter retString = new StringWriter();

		try{
			JAXBContext context = JAXBContext.newInstance(PublisherXml.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			m.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);

			Iterable<Publisher> publishers = pDao.findAll();

			for(Publisher p : publishers){
				PublisherXml pRest = PublisherXml.convertPublisher(p);

				m.marshal(pRest, retString);
			}
		} catch (Exception e) {
			System.out.println(e);
		}

		feeds += retString;
		feeds += "</subscriptions>";
		return feeds;
	}

	@RequestMapping(value="user/{userId}")
	@ResponseBody
	public String getUserFeeds(@PathVariable int userId){
		StringWriter retString = new StringWriter();
		try {
			JAXBContext context = JAXBContext.newInstance(UserXML.class);
			UserXML userXml = UserXML.convertUser(uDao.findById(userId).get());
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			m.marshal(userXml, retString);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return retString.toString();
	}

	@RequestMapping(value="managefeeds/user/unsubscribe")
	@ResponseBody
	public boolean unsubscribe(HttpServletRequest request, HttpSession session){
		int feedId = Integer.valueOf(request.getParameter("id"));

		User u = uDao.findById((Integer) session.getAttribute("userId")).get();
		RssLink link = rssDao.findById(feedId).get();
		boolean success = false;

		if(u.getRssLinks().contains(link)){
			u.removeRssLink(link);
			uDao.save(u);
			success = true;
		}

		return success;
	}

	@RequestMapping(value="managefeeds/user/subscribe")
	@ResponseBody
	public boolean subscribe(HttpServletRequest request, HttpSession session){
		int feedId = Integer.valueOf(request.getParameter("id"));

		User u = uDao.findById((Integer) session.getAttribute("userId")).get();
		RssLink link = rssDao.findById(feedId).get();

		boolean success = false;

		if(link != null){
			u.addRssLink(link);
			uDao.save(u);
			success = true;
		}

		return success;
	}

	@RequestMapping(value="user/feeds")
	@ResponseBody
	public String getUserFeedIds(HttpSession session){
		String retStr = "";

		retStr += "<feedIds>\n";

		List<Integer> ids = uDao.getUserRssLinkIds(((Integer)session.getAttribute("userId")).toString());

		for(Integer id : ids){
			retStr += "<id>" + id + "</id>\n";
		}

		retStr += "</feedIds>";

		return retStr;
	}

	@RequestMapping(value="feeds/articles")
	@ResponseBody
	public String getUserArticles(HttpSession session){
		StringWriter retStr = new StringWriter();
		retStr.write("<Articles>");

		try {
			JAXBContext context = JAXBContext.newInstance(ArticleXml.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			m.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);

			User u = uDao.findById((Integer)session.getAttribute("userId")).get();

			ArticleFetcher fetcher = new ArticleFetcher();
			for(RssLink link : u.getRssLinks()){
				fetcher.fetchFeed(link);
			}

			for(Article article : fetcher.getArticleList()){
				ArticleXml xml = ArticleXml.convertArticle(article);
				m.marshal(xml, retStr);
			}
		} catch (Exception e) {
			System.out.println(e);
		}

		return retStr.toString();
	}

	@RequestMapping(value="feeds/random")
	@ResponseBody
	public String getRandomFeed(){
		StringWriter retStr = new StringWriter();
		RssLinkXML linkXml = RssLinkXML.convertRssLink(rssDao.getRandom());

		try{
			JAXBContext context = JAXBContext.newInstance(RssLinkXML.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			m.marshal(linkXml, retStr);
		} catch (Exception e) {
			System.out.println(e);
		}

		return retStr.toString();
	}

	@RequestMapping("feed/articles")
	@ResponseBody
	public String returnArticles(HttpSession session){
		String retBody = "";
		Integer userId = (Integer)session.getAttribute("userId");
		if(userId != null){
			retBody = getArticles(userId);
		} else {
			retBody = getArticles();
		}

		return retBody;
	}

	public String getArticles(int id){
		StringWriter retStr = new StringWriter();

		retStr.write("<Articles>");

		try {
			JAXBContext context = JAXBContext.newInstance(ArticleXml.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			m.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);

			User u = uDao.findById(id).get();

			ArticleFetcher fetcher = new ArticleFetcher();

			for(RssLink link : u.getRssLinks()){
				fetcher.fetchFeed(link);
			}

			for(Article article : fetcher.getArticleList()){
				fetchComments(article);
				ArticleXml xml = ArticleXml.convertArticle(article);
				m.marshal(xml, retStr);
			}
		} catch (Exception e) {
			System.out.println("getArticles("+id+"): "+e);
		}
		retStr.write("</Articles>");
		return retStr.toString();
	}

	public String getArticles(){
		StringWriter retString = new StringWriter();

		try {
			RssLink link = rssDao.getRandom();

			ArticleFetcher fetcher = new ArticleFetcher();
			fetcher.fetchFeed(link);

			retString.write("<Articles>");

			JAXBContext context = JAXBContext.newInstance(ArticleXml.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			m.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);

			for(Article article : fetcher.getArticleList()){
				fetchComments(article);
				ArticleXml articleXml = ArticleXml.convertArticle(article);

				m.marshal(articleXml, retString);
			}
			retString.write("</Articles>");
		} catch (Exception e) {
			System.out.println(e);
		}

		return retString.toString();
	}

	private void fetchComments(Article article){
		CommentContainer commentContainer = new CommentContainer();
		commentContainer.generateId(article);
		Optional<CommentContainer> optionalCommentContainer = ccDao.findById(new Integer(commentContainer.getContainerID()));

		if(optionalCommentContainer.isPresent()){
			commentContainer = optionalCommentContainer.get();
		} else {
			commentContainer.setArticlePublisher(article.getPublisher());
			commentContainer.setArticleTitle(article.getTitle());
			System.out.println("save");
			ccDao.save(commentContainer);
			commentContainer.setComments(new ArrayList<>());
		}

		article.setId(commentContainer.getContainerID());
		article.setNumberOfComments(commentContainer.getComments().size());
	}
}
