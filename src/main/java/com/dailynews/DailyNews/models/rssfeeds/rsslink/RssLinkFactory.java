package com.dailynews.DailyNews.models.rssfeeds.rsslink;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class RssLinkFactory {

	private ArrayList<RssLink> links = new ArrayList<>();

	private static RssLinkFactory ourInstance = new RssLinkFactory();

	public static RssLinkFactory getInstance() {
		return ourInstance;
	}

	private RssLinkFactory() {
		try {
			addRssLink("SpaceNews", new URL("http://spacenews.com/feed/"));
			addRssLink("NASA", new URL("https://www.nasa.gov/rss/dyn/breaking_news.rss"));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	public void addRssLink(String publisher, URL url){
		RssLink rssLink = new RssLink();
		rssLink.setPublisher(publisher);
		rssLink.setRssUrl(url);

		links.add(rssLink);
	}

	public RssLink getPublisherRss(String publisher){
		for (RssLink link: links) {
			if(link.getPublisher().equalsIgnoreCase(publisher)) return link;
		}

		return null;
	}

	public ArrayList<String> getPublishers(){
		ArrayList<String> publishers = new ArrayList<>(links.size());

		for(RssLink link : links){
			publishers.add(link.getPublisher());
		}

		return publishers;
	}
}
