package com.viviquity.readmy.entity;

import com.viviquity.readmy.db.utils.model.BaseEntity;

public class Favourite extends BaseEntity {

	private User user;
	
	private Work work;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Work getWork() {
		return work;
	}

	public void setWork(Work work) {
		this.work = work;
	}
}
