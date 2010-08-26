package com.viviquity.readmy.entity;

import com.viviquity.readmy.db.utils.model.GenericEntity;

public class MessageType extends GenericEntity {

	public static final String PUBLISHER_TO_USER = "PUBLISHER_TO_USER";
	
	public static final String USER_TO_ADMIN = "USER_TO_ADMIN";
	
	public static final String PUBLISHER_TO_ADMIN = "PUBLISHER_TO_ADMIN";
	
	public static final String ADMIN_TO_USER = "ADMIN_TO_USER";
	
	public static final String ADMIN_TO_PUBLISHER = "ADMIN_TO_PUBLISHER";
	
	private String type;

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
}
