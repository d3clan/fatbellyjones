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
    private User user;

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
    @ManyToOne(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
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
