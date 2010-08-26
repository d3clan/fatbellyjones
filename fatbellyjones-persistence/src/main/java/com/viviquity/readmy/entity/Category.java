package com.viviquity.readmy.entity;

import com.viviquity.readmy.db.utils.model.GenericEntity;

public class Category extends GenericEntity {

	private String name;

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
