package com.dailynews.DailyNews.models.rssfeeds.article;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import java.net.URL;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

public class ArticleFetcher {

	private LinkedList<Article> articleList = new LinkedList<>();

	public ArticleFetcher(){

	}

	public void fetchFeed(String feedUrl){

		try{
			URL url = new URL(feedUrl);
			SyndFeedInput input = new SyndFeedInput();
			SyndFeed syndFeed = input.build(new XmlReader(url));
			int i = 0;
			for (SyndEntry article : syndFeed.getEntries()) {
				Article f = new Article();

				f.setTitle(article.getTitle());
				f.setDescription(article.getDescription().getValue());
				f.setAuthor(article.getAuthor());
				f.setLink(article.getLink());
				f.setDatePublished(article.getPublishedDate());

				articleList.add(f);
			}
		} catch (Exception ex){
			System.out.println(ex);
		}

		Collections.sort(articleList, new Comparator<Article>() {
			@Override
			public int compare(Article o1, Article o2) {
				return o1.getDate().compareTo(o2.getDate()) * -1;
			}
		});
	}

	public LinkedList<Article> getArticleList(){
		return articleList;
	}
}
