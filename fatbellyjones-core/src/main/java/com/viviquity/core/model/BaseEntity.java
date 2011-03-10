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

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((dateCreated == null) ? 0 : dateCreated.hashCode());
	result = prime * result + ((lastModified == null) ? 0 : lastModified.hashCode());
	return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	BaseEntity<T> other = (BaseEntity<T>) obj;
	if (dateCreated == null) {
	    if (other.dateCreated != null)
		return false;
	} else if (!dateCreated.equals(other.dateCreated))
	    return false;
	if (lastModified == null) {
	    if (other.lastModified != null)
		return false;
	} else if (!lastModified.equals(other.lastModified))
	    return false;
	return true;
    }

}
