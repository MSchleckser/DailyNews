package com.dailynews.DailyNews.controllers.rest;

import com.dailynews.DailyNews.models.rssfeeds.rsslink.RssLink;
import com.dailynews.DailyNews.models.rssfeeds.rsslink.RssLinkDao;
import com.dailynews.DailyNews.models.rssfeeds.rsslink.restwrapper.RssLinkRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;

@Controller
@RequestMapping("feed")
public class FeedController {

	@Autowired
	private RssLinkDao rssDao;

	@RequestMapping(value = "{feedId}")
	@ResponseBody
	public String retFeed(@PathVariable int feedId){
		StringWriter retString = new StringWriter();

		try {
			JAXBContext context = JAXBContext.newInstance(RssLinkRest.class);
			RssLinkRest restLink = RssLinkRest.convertRssLink(rssDao.findById(feedId).get());
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			System.out.println(restLink);
			m.marshal(restLink, retString);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "<pre>" + retString.toString() + "</pre>";
	}
}
