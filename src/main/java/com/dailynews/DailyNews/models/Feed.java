package com.dailynews.DailyNews.models;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Objects;

public class Feed implements Comparator<Feed>, Comparable<Feed> {
	private String title;
	private String description;
	private String author;
	private String link;
	private Date datePublished;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getDatePublished(){
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern("EEE, MMM d");
		return sdf.format(datePublished);
	}

	Date getDate(){
		return datePublished;
	}

	public void setDatePublished(Date datePublished){
		this.datePublished = datePublished;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Feed feed = (Feed) o;
		return Objects.equals(title, feed.title) &&
				Objects.equals(description, feed.description) &&
				Objects.equals(author, feed.author) &&
				Objects.equals(link, feed.link);
	}

	@Override
	public int hashCode() {

		return Objects.hash(title, description, author, link);
	}

	@Override
	public int compareTo(Feed feed) {
		return feed.datePublished.compareTo(feed.datePublished);
	}

	@Override
	public int compare(Feed o1, Feed o2) {
		return o1.compareTo(o2);
	}
}
