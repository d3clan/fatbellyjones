package com.viviquity.readmy.entity;

import com.viviquity.readmy.db.utils.model.GenericEntity;

public class AccountType extends GenericEntity {

	public static final String PUBLISHER = "PUBLISHER";
	
	public static final String ADMIN = "ADMIN";
	
	public static final String FREE = "FREE";
	
	public static final String PREMIUM = "PREMIUM";
	
	private String type;

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
	
}
