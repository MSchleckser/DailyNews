package com.dailynews.DailyNews.models.restwrappers;

import com.dailynews.DailyNews.models.rssfeeds.rsslink.RssLink;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "link")
@XmlAccessorType(XmlAccessType.NONE)
public class RssLinkRest {

	private int id;

	private String title;

	private String url;

	private String publisher;

	private RssLinkRest(){

	}

	public static RssLinkRest convertRssLink(RssLink rssLink){
		RssLinkRest nRest = new RssLinkRest();

		nRest.id = rssLink.getId();
		nRest.title = rssLink.getTitle();
		nRest.url = rssLink.getRssUrl().toString();
		nRest.publisher = rssLink.getPublisher().getName();

		return nRest;
	}

	@XmlElement
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@XmlElement
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@XmlElement
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@XmlElement
	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	@Override
	public String toString(){
		String retString = "id: " + id + ", ";
		retString += "title: " + title + ", ";
		retString += "url: " + url + ", ";
		retString += "publisher: " + publisher;

		return retString;
	}
}
