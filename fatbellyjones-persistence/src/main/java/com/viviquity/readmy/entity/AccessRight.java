package com.viviquity.readmy.entity;

import com.viviquity.readmy.db.utils.model.GenericEntity;

public class AccessRight extends GenericEntity {

	/* public static finals here for rights, can't think of any right now */
	
	private String right;

	public void setRight(String right) {
		this.right = right;
	}

	public String getRight() {
		return right;
	}
}
