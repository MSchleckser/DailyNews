package com.dailynews.DailyNews.models.xmlwrappers;

import com.dailynews.DailyNews.models.rssfeeds.rsslink.RssLink;
import com.dailynews.DailyNews.models.rssfeeds.rsslink.publisher.Publisher;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;

@XmlRootElement
@XmlSeeAlso(RssLinkXML.class)
@XmlAccessorType(XmlAccessType.NONE)
public class PublisherXml {

	@XmlElement
	private int id;

	@XmlElement
	private String name;

	@XmlElementWrapper(name="links")
	@XmlElement(name="link")
	private ArrayList<RssLinkXML> rssLinks = new ArrayList<>();

	private PublisherXml(){

	}

	public static PublisherXml convertPublisher(Publisher publisher){
		PublisherXml nPub = new PublisherXml();

		nPub.id = publisher.getId();
		nPub.name = publisher.getName();

		for(RssLink link : publisher.getLinks()){
			nPub.rssLinks.add(RssLinkXML.convertRssLink(link));
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

	public ArrayList<RssLinkXML> getRssLinks() {
		return rssLinks;
	}

	public void setRssLinks(ArrayList<RssLinkXML> rssLinks) {
		this.rssLinks = rssLinks;
	}
}
