package com.dailynews.DailyNews.models.xmlwrappers;

import com.dailynews.DailyNews.models.rssfeeds.rsslink.RssLink;
import com.dailynews.DailyNews.models.user.User;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;

@XmlRootElement(name = "user")
@XmlSeeAlso(RssLinkXML.class)
@XmlAccessorType(XmlAccessType.NONE)
public class UserXML {

	@XmlElement
	private int id;

	@XmlElement
	private String username;

	@XmlElement
	private String role;

	private ArrayList<RssLinkXML> rssLinks = new ArrayList<>();

	public static UserXML convertUser(User user){
		UserXML userXml = new UserXML();

		userXml.id = user.getId();
		userXml.username = user.getUsername();
		userXml.role = user.getRole().getName();

		for(RssLink link : user.getRssLinks()){
			userXml.rssLinks.add(RssLinkXML.convertRssLink(link));
		}

		return userXml;
	}

	public int getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getRole() {
		return role;
	}

	@XmlElementWrapper(name = "links")
	@XmlElement(name = "link")
	public RssLinkXML[] getRssLinks() {
		RssLinkXML[] retArr = new RssLinkXML[rssLinks.size()];

		for(int i = 0; i < rssLinks.size(); i++){
			retArr[i] = rssLinks.get(i);
		}

		return retArr;
	}
}
