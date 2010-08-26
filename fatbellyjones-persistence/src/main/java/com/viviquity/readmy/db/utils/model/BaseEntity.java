package com.viviquity.readmy.db.utils.model;

import java.util.Date;

public class BaseEntity {

	private Date created;
	
	private Date lastUpdated;

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getCreated() {
		return created;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}
	
}
