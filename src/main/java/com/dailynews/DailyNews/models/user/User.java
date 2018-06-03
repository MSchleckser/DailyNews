package com.dailynews.DailyNews.models.user;

import com.dailynews.DailyNews.models.rssfeeds.rsslink.RssLink;
import com.dailynews.DailyNews.models.user.role.Role;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="user")
public class User {

	@Id
	@GeneratedValue
	private int id;

	@NotNull
	private String username;

	@NotNull
	private String password;

	@ManyToOne
	private Role role;

	@ManyToMany(fetch = FetchType.EAGER)
	private List<RssLink> rssLinks = new ArrayList<>();

	public User() {

	}

	public void addRssLink(RssLink rssLink){
		if(rssLinks.contains(rssLink))
			return;

		rssLinks.add(rssLink);
	}

	public void removeRssLink(RssLink rssLink){
		rssLinks.remove(rssLink);
	}

	public int getId() {
		return id;
	}

	//<editor-fold desc="username Getters and Setters">
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	//</editor-fold>

	//<editor-fold desc="password Getters and Setters">
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	//</editor-fold>

	//<editor-fold desc="role Getters">
	public Role getRole(){
		return role;
	}

	public void setRole(Role role){
		this.role = role;
	}

	public void removeAllRssLinksById(ArrayList<Integer> removeRssIds) {

		for(Integer id : removeRssIds){
			for(RssLink link : rssLinks){
				if(id.equals(Integer.valueOf(link.getId()))){
					rssLinks.remove(link);
					break;
				}
			}
		}
	}
	//</editor-fold>

	//<editor-fold desc="rssLinks Getters and Setters">
	public void setRssLinks(List<RssLink> rssLinks) {
		this.rssLinks = rssLinks;
	}

	public List<RssLink> getRssLinks(){
		return rssLinks;
	}
	//</editor-fold>
}
