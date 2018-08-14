package com.dailynews.DailyNews.models.rssfeeds.article;

import com.dailynews.DailyNews.models.comments.CommentContainer;
import com.dailynews.DailyNews.models.comments.CommentContainerDao;
import com.dailynews.DailyNews.models.rssfeeds.rsslink.RssLink;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

public class ArticleFetcher {

	private ArrayList<Article> articleList = new ArrayList<>();

	public ArticleFetcher(){

	}

	public void fetchFeed(RssLink link){

		try {
			URL url = link.getRssUrl();

			SyndFeedInput input = new SyndFeedInput();
			SyndFeed syndFeed = input.build(new XmlReader(url));
			for (SyndEntry rawArticle : syndFeed.getEntries()) {
				Article freshArticle = new Article();

				freshArticle.setTitle(rawArticle.getTitle());
				freshArticle.setDescription(rawArticle.getDescription().getValue());
				freshArticle.setAuthor(rawArticle.getAuthor());
				freshArticle.setLink(rawArticle.getLink());
				freshArticle.setDatePublished(rawArticle.getPublishedDate());
				freshArticle.setPublisher(link.getPublisher().getName());

				articleList.add(freshArticle);
			}
		} catch (Exception e){
			System.out.println(e);
		}

		Collections.sort(articleList, new Comparator<Article>() {
			@Override
			public int compare(Article o1, Article o2) {
				if(o1.getDate() == null && o2.getDate() == null)
					return 0;

				if(o1.getDate() == null)
					return -1;

				if(o2.getDate() == null)
					return 1;
				
				return o1.getDate().compareTo(o2.getDate()) * -1;
			}
		});
	}

	public ArrayList<Article> getArticleList(){
		return articleList;
	}
}
