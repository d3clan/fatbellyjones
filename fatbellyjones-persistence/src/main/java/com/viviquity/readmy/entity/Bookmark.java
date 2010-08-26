package com.viviquity.readmy.entity;

import com.viviquity.readmy.db.utils.model.GenericEntity;

public class Bookmark extends GenericEntity {

	private Page page;
	
	private User user;

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
