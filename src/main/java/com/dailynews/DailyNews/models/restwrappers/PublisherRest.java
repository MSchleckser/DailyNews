package com.dailynews.DailyNews.models.restwrappers;

import com.dailynews.DailyNews.models.rssfeeds.rsslink.RssLink;
import com.dailynews.DailyNews.models.rssfeeds.rsslink.publisher.Publisher;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;

@XmlRootElement
@XmlSeeAlso(RssLinkRest.class)
@XmlAccessorType(XmlAccessType.NONE)
public class PublisherRest {

	@XmlElement
	private int id;

	@XmlElement
	private String name;

	@XmlElementWrapper(name="links")
	@XmlElement(name="link")
	private ArrayList<RssLinkRest> rssLinks = new ArrayList<>();

	private PublisherRest(){

	}

	public static PublisherRest convertPublisher(Publisher publisher){
		PublisherRest nPub = new PublisherRest();

		nPub.id = publisher.getId();
		nPub.name = publisher.getName();

		for(RssLink link : publisher.getLinks()){
			nPub.rssLinks.add(RssLinkRest.convertRssLink(link));
		}

		return nPub;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<RssLinkRest> getRssLinks() {
		return rssLinks;
	}

	public void setRssLinks(ArrayList<RssLinkRest> rssLinks) {
		this.rssLinks = rssLinks;
	}
}
