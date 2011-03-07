package com.viviquity.core.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "playlist_entry")
public class PlaylistEntry extends BaseEntity<Long> {

    private Long id;
    private Event event;
    private Tune tune;
    private Integer order;
    private Integer set;
    private String response;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    /**
     * @return the response
     */
    @Column(name = "response")
    public String getResponse() {
	return response;
    }

    /**
     * @param response
     *            the response to set
     */
    public void setResponse(String response) {
	this.response = response;
    }

    /**
     * @return the event
     */
    @ManyToOne(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
    public Event getEvent() {
	return event;
    }

    /**
     * @param event
     *            the event to set
     */
    public void setEvent(Event event) {
	this.event = event;
    }

    /**
     * @return the tune
     */
    @ManyToOne(cascade = { CascadeType.MERGE }, fetch = FetchType.LAZY)
    public Tune getTune() {
	return tune;
    }

    /**
     * @param tune
     *            the tune to set
     */
    public void setTune(Tune tune) {
	this.tune = tune;
    }

    /**
     * @return the order
     */
    @Column(name = "playlist_order")
    public Integer getOrder() {
	return order;
    }

    /**
     * @param order
     *            the order to set
     */
    public void setOrder(Integer order) {
	this.order = order;
    }

    /**
     * @return the set
     */
    @Column(name = "set")
    public Integer getSet() {
	return set;
    }

    /**
     * @param set
     *            the set to set
     */
    public void setSet(Integer set) {
	this.set = set;
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
	result = prime * result + ((id == null) ? 0 : id.hashCode());
	return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	PlaylistEntry other = (PlaylistEntry) obj;
	if (id == null) {
	    if (other.id != null)
		return false;
	} else if (!id.equals(other.id))
	    return false;
	return true;
    }

}
