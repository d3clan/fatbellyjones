package com.viviquity.readmy.bean;

import java.net.URL;

public class PosterResponse {

    private Long eventId;
    private String image;
    private String result;
    private URL link;
    private String message;

    /**
     * @return the result
     */
    public String getResult() {
	return result;
    }

    /**
     * @return the eventId
     */
    public Long getEventId() {
	return eventId;
    }

    /**
     * @param eventId
     *            the eventId to set
     */
    public void setEventId(Long eventId) {
	this.eventId = eventId;
    }

    /**
     * @return the image
     */
    public String getImage() {
	return image;
    }

    /**
     * @param image
     *            the image to set
     */
    public void setImage(String image) {
	this.image = image;
    }

    /**
     * @param result
     *            the result to set
     */
    public void setResult(String result) {
	this.result = result;
    }

    /**
     * @return the link
     */
    public URL getLink() {
	return link;
    }

    /**
     * @param link
     *            the link to set
     */
    public void setLink(URL link) {
	this.link = link;
    }

    /**
     * @return the message
     */
    public String getMessage() {
	return message;
    }

    /**
     * @param message
     *            the message to set
     */
    public void setMessage(String message) {
	this.message = message;
    }

}
