package com.viviquity.readmy.bean;

import org.apache.log4j.Logger;

public class TuneBean {

    private static final Logger logger = Logger.getLogger(TuneBean.class);

    private Long id;
    private String title;
    private String artist;
    private String className;

    /**
     * @return the id
     */
    public Long getId() {
	return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(Long id) {
	this.id = id;
    }

    /**
     * @return the title
     */
    public String getTitle() {
	return title;
    }

    /**
     * @param title
     *            the title to set
     */
    public void setTitle(String title) {
	this.title = title;
    }

    /**
     * @return the artist
     */
    public String getArtist() {
	return artist;
    }

    /**
     * @param artist
     *            the artist to set
     */
    public void setArtist(String artist) {
	this.artist = artist;
    }

    /**
     * @return the className
     */
    public String getClassName() {
	return className;
    }

    /**
     * @param className
     *            the className to set
     */
    public void setClassName(String className) {
	this.className = className;
    }

}
