package com.viviquity.readmy.entity;

import com.viviquity.readmy.db.utils.model.BaseEntity;

public class CommentAgreement extends BaseEntity {

	private User user;
	
	private Comment comment;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Comment getComment() {
		return comment;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
	}
	
}
