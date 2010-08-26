package com.viviquity.readmy.entity;

import java.util.Collection;

import com.viviquity.readmy.db.utils.model.GenericEntity;

public class Series extends GenericEntity {

	private User user;
	
	private String name;
	
	private Collection<Work> works;

	public Collection<Work> getWorks() {
		return works;
	}

	public void setWorks(Collection<Work> works) {
		this.works = works;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
