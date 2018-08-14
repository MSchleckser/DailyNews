package com.dailynews.DailyNews.models.comments.Comment;

import com.dailynews.DailyNews.models.comments.CommentContainer;

import javax.persistence.*;

@Entity
@Table(name = "Comments")
public class Comment {

	@Id
	@GeneratedValue
	private int id;

	@OneToOne
	CommentContainer commentContainer;

	private int userId;

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

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
}
