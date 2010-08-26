package com.viviquity.readmy.entity;

import java.util.Collection;

import com.viviquity.readmy.db.utils.model.GenericEntity;

public class Role extends GenericEntity {

	private String name;
	
	private Collection<AccessRight> accessRights;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collection<AccessRight> getAccessRights() {
		return accessRights;
	}

	public void setAccessRights(Collection<AccessRight> accessRights) {
		this.accessRights = accessRights;
	}
	
}
