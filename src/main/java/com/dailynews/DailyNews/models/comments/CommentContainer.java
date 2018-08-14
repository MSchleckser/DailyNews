package com.dailynews.DailyNews.models.comments;

import com.dailynews.DailyNews.models.comments.Comment.Comment;
import com.dailynews.DailyNews.models.rssfeeds.article.Article;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "CommentConatiner")
public class CommentContainer {

	@Id
	private int containerID;

	private String articleTitle;
	private String articlePublisher;

	@OneToMany
	@JoinColumn(name = "comment_id")
	private List<Comment> comments;

	public void generateId(Article article){
		containerID = article.getTitle().hashCode() + article.getTitle().hashCode() + article.getDatePublished().hashCode();
	}

	public int getContainerID() {
		return containerID;
	}

	public void setContainerID(int containerID) {
		this.containerID = containerID;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public String getArticleTitle() {
		return articleTitle;
	}

	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}

	public String getArticlePublisher() {
		return articlePublisher;
	}

	public void setArticlePublisher(String articlePublisher) {
		this.articlePublisher = articlePublisher;
	}
}
