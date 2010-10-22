package com.viviquity.core.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@MappedSuperclass
@Embeddable
public abstract class BaseEntity<T> {

    private Date lastModified;
    private Date dateCreated;

    @Column(name = "last_modified")
    public Date getLastModified() {
	return lastModified;
    }

    public void setLastModified(Date lastModified) {
	this.lastModified = lastModified;
    }

    @Column(name = "date_created")
    public Date getDateCreated() {
	return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
	this.dateCreated = dateCreated;
    }

    @PreUpdate
    @PrePersist
    public void updateTimeStamps() {
	lastModified = new Date();
	if (dateCreated == null) {
	    dateCreated = new Date();
	}
    }
}
