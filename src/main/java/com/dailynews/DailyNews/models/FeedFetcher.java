package com.dailynews.DailyNews.models;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

public class FeedFetcher {

	private LinkedList<Feed> feedList = new LinkedList<>();

	public FeedFetcher(){

	}

	public void fetchFeed(String feedUrl){

		try{
			URL url = new URL(feedUrl);
			SyndFeedInput input = new SyndFeedInput();
			SyndFeed syndFeed = input.build(new XmlReader(url));
			int i = 0;
			for (SyndEntry article : syndFeed.getEntries()) {
				Feed f = new Feed();

				f.setTitle(article.getTitle());
				f.setDescription(article.getDescription().getValue());
				f.setAuthor(article.getAuthor());
				f.setLink(article.getLink());
				f.setDatePublished(article.getPublishedDate());

				feedList.add(f);
			}
		} catch (Exception ex){
			System.out.println(ex);
		}

		Collections.sort(feedList, new Comparator<Feed>() {
			@Override
			public int compare(Feed o1, Feed o2) {
				return o1.getDate().compareTo(o2.getDate()) * -1;
			}
		});
	}

	public LinkedList<Feed> getFeedList(){
		return feedList;
	}
}
