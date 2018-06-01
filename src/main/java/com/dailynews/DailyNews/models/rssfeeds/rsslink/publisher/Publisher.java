package com.dailynews.DailyNews.models.rssfeeds.rsslink.publisher;

import com.dailynews.DailyNews.models.rssfeeds.rsslink.RssLink;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Publisher {

	@Id
	@GeneratedValue
	private int id;

	@NotNull
	private String name;

	@OneToMany
	@JoinColumn(name = "publisher_id")
	public List<RssLink> links = new ArrayList<>();

	//<editor-fold desc="id Getter">
	public int getId(){
		return id;
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

	//<editor-fold desc="links Getters and Setters">
	public List<RssLink> getLinks() {
		return links;
	}

	public void setLinks(List<RssLink> links) {
		this.links = links;
	}
	//</editor-fold>
}
