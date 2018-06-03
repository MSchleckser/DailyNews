package com.dailynews.DailyNews.controllers.rest;

import com.dailynews.DailyNews.models.restwrappers.PublisherRest;
import com.dailynews.DailyNews.models.restwrappers.UserRest;
import com.dailynews.DailyNews.models.rssfeeds.rsslink.RssLink;
import com.dailynews.DailyNews.models.rssfeeds.rsslink.RssLinkDao;
import com.dailynews.DailyNews.models.restwrappers.RssLinkRest;
import com.dailynews.DailyNews.models.rssfeeds.rsslink.publisher.Publisher;
import com.dailynews.DailyNews.models.rssfeeds.rsslink.publisher.PublisherDao;
import com.dailynews.DailyNews.models.user.User;
import com.dailynews.DailyNews.models.user.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.util.List;

@Controller
@RequestMapping("rest")
public class ManageRestController {

	@Autowired
	private RssLinkDao rssDao;

	@Autowired
	private UserDao uDao;

	@Autowired
	private PublisherDao pDao;

	@RequestMapping(value = "feed/{feedId}")
	@ResponseBody
	public String getFeed(@PathVariable int feedId){
		StringWriter retString = new StringWriter();

		try {
			JAXBContext context = JAXBContext.newInstance(RssLinkRest.class);
			RssLinkRest restLink = RssLinkRest.convertRssLink(rssDao.findById(feedId).get());
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
			JAXBContext context = JAXBContext.newInstance(PublisherRest.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			m.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);

			Iterable<Publisher> publishers = pDao.findAll();

			for(Publisher p : publishers){
				PublisherRest pRest = PublisherRest.convertPublisher(p);

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
			JAXBContext context = JAXBContext.newInstance(UserRest.class);
			UserRest userRest = UserRest.convertUser(uDao.findById(userId).get());
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			m.marshal(userRest, retString);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return retString.toString();
	}

	@RequestMapping(value="user/unsubscribe/{feedId}")
	@ResponseBody
	public boolean unsubscribe(@PathVariable int feedId, HttpSession session){
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

	@RequestMapping(value="user/subscribe/{feedId}")
	@ResponseBody
	public boolean subscribe(@PathVariable int feedId, HttpSession session){
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
}
