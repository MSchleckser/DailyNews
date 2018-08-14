package com.dailynews.DailyNews.models.xmlwrappers;

import com.dailynews.DailyNews.models.rssfeeds.article.Article;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class ArticleXml {

	@XmlElement
	private int articleId;

	@XmlElement
	private int numberOfComments;

	@XmlElement
	private String title;

	@XmlElement
	private String description;

	@XmlElement
	private String author;

	@XmlElement
	private String link;

	@XmlElement
	private String date;

	@XmlElement
	private String publisher;

	private ArticleXml(){

	}

	public static ArticleXml convertArticle(Article article){
		ArticleXml retArticle = new ArticleXml();

		retArticle.articleId = article.getId();
		retArticle.numberOfComments = article.getNumberOfComments();
		retArticle.title = article.getTitle();
		retArticle.author = article.getAuthor();
		retArticle.description = article.getDescription();
		retArticle.link = article.getLink();
		retArticle.date = article.getDatePublished();
		retArticle.publisher = article.getPublisher();

		return retArticle;
	}

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

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public int getArticleId() {
		return articleId;
	}

	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}

	public int getNumberOfComments() {
		return numberOfComments;
	}

	public void setNumberOfComments(int numberOfComments) {
		this.numberOfComments = numberOfComments;
	}
}
