package com.dailynews.DailyNews.models.user.role;

import com.dailynews.DailyNews.models.rssfeeds.rsslink.RssLink;
import com.dailynews.DailyNews.models.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Role {

	@Id
	@GeneratedValue
	private int id;

	@NotNull
	private String name;

	@OneToMany
	@JoinColumn(name = "role_id")
	private List<User> users = new ArrayList<>();

	//<editor-fold desc="id Getters and Setters">
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	//</editor-fold>

	//<editor-fold desc="name Getters and Setters">
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	//</editor-fold>

	//<editor-fold desc="users Getters and Setters">
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
	//</editor-fold>
}
