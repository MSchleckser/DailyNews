package com.dailynews.DailyNews.models.rssfeeds.rsslink;

import java.net.URL;

public class RssLink {

	private String publisher;
	private URL rssUrl;

	RssLink(){

	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public URL getRssUrl() {
		return rssUrl;
	}

	public void setRssUrl(URL rssUrl) {
		this.rssUrl = rssUrl;
	}
}
