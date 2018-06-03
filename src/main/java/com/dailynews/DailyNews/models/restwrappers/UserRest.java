package com.dailynews.DailyNews.models.restwrappers;

import com.dailynews.DailyNews.models.rssfeeds.rsslink.RssLink;
import com.dailynews.DailyNews.models.user.User;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;

@XmlRootElement(name = "user")
@XmlSeeAlso(RssLinkRest.class)
@XmlAccessorType(XmlAccessType.NONE)
public class UserRest {

	@XmlElement
	private int id;

	@XmlElement
	private String username;

	@XmlElement
	private String role;

	private ArrayList<RssLinkRest> rssLinks = new ArrayList<>();

	public static UserRest convertUser(User user){
		UserRest userRest = new UserRest();

		userRest.id = user.getId();
		userRest.username = user.getUsername();
		userRest.role = user.getRole().getName();

		for(RssLink link : user.getRssLinks()){
			userRest.rssLinks.add(RssLinkRest.convertRssLink(link));
		}

		return userRest;
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
	public RssLinkRest[] getRssLinks() {
		RssLinkRest[] retArr = new RssLinkRest[rssLinks.size()];

		for(int i = 0; i < rssLinks.size(); i++){
			retArr[i] = rssLinks.get(i);
		}

		return retArr;
	}
}
