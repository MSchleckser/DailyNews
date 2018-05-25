package com.dailynews.DailyNews.models.user;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class User {

	private ArrayList<URL> feeds = new ArrayList<>();

	private static User ourInstance = new User();

	public static User getInstance() {
		return ourInstance;
	}

	private User() {
	}

	public void addRssUrl(String url){
		try {
			feeds.add(new URL(url));
		} catch (MalformedURLException e) {
			System.out.println("Bad url");
		}
	}

	public void addRssUrl(URL url){
		feeds.add(url);
	}

	public ArrayList<URL> getRssUrls(){
		return feeds;
	}
}
