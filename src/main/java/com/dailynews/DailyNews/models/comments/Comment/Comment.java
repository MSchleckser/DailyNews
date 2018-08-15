package com.dailynews.DailyNews.models.comments.Comment;

import com.dailynews.DailyNews.models.comments.CommentContainer;
import com.dailynews.DailyNews.models.user.User;

import javax.persistence.*;

@Entity
@Table(name = "Comments")
public class Comment {

	@Id
	@GeneratedValue
	private int id;

	@OneToOne
	CommentContainer commentContainer;

	@OneToOne
	private User user;

	private String commentBody;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public CommentContainer getCommentContainer() {
		return commentContainer;
	}

	public void setCommentContainer(CommentContainer commentContainer) {
		this.commentContainer = commentContainer;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getCommentBody() {
		return commentBody;
	}

	public void setCommentBody(String commentBody) {
		this.commentBody = commentBody;
	}
}
