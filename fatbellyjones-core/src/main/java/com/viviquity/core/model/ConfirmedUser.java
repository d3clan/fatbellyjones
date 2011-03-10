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
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "confirmed_user", uniqueConstraints = { @UniqueConstraint(columnNames = { "event_id", "user_id" }) })
public class ConfirmedUser extends BaseEntity<Long> {

    private Long id;
    private Event event;
    private User user;
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
     * @return the user
     */
    @ManyToOne(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
    public User getUser() {
	return user;
    }

    /**
     * @param user
     *            the user to set
     */
    public void setUser(User user) {
	this.user = user;
    }

}
