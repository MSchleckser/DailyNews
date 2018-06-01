package com.dailynews.DailyNews.models.rssfeeds.rsslink;

import com.dailynews.DailyNews.models.rssfeeds.rsslink.publisher.Publisher;
import com.dailynews.DailyNews.models.user.User;
import com.dailynews.DailyNews.models.user.role.Role;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class RssLink {

	@Id
	@GeneratedValue
	private int id;

	@NotNull
	private String title;

	private URL rssUrl;

	@ManyToMany(mappedBy = "rssLinks")
	private List<User> users;

	@OneToOne(fetch = FetchType.EAGER)
	private Publisher publisher;

	public RssLink(){

	}

	void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public URL getRssUrl() {
		return rssUrl;
	}

	public void setRssUrl(URL rssUrl) {
		this.rssUrl = rssUrl;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(ArrayList<User> users) {
		this.users = users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Publisher getPublisher() {
		return publisher;
	}

	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		RssLink rssLink = (RssLink) o;
		return id == rssLink.id &&
				Objects.equals(rssUrl, rssLink.rssUrl);
	}

	@Override
	public int hashCode() {

		return Objects.hash(id, rssUrl);
	}
}
