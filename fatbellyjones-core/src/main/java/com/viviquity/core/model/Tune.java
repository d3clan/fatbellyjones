package com.viviquity.core.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tune")
public class Tune extends BaseEntity<Long> {

    public static final String BREAK_STATUS = "break";
    public static final String INITIAL_STATUS = "initial";
    public static final String PRACTICE_STATUS = "practice";
    public static final String AGREED_STATUS = "agreed";
    public static final String PLAYING_STATUS = "playing";

    private Long id;
    private String title;
    private String artist;
    private String countIn;
    private String key;
    private String status = INITIAL_STATUS;
    private String notes;
    private Long duration = new Long((60 * 1000) * 5);
    private String amazonKey;
    private User uploadedBy;

    /**
     * @return the id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
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
    @Column(name = "title")
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
     * @return the author
     */
    @Column(name = "author", length = 100)
    public String getArtist() {
	return artist;
    }

    /**
     * @param author
     *            the author to set
     */
    public void setArtist(String artist) {
	this.artist = artist;
    }

    /**
     * @return the countIn
     */
    @Column(name = "count_in", length = 40)
    public String getCountIn() {
	return countIn;
    }

    /**
     * @param countIn
     *            the countIn to set
     */
    public void setCountIn(String countIn) {
	this.countIn = countIn;
    }

    /**
     * @return the key
     */
    @Column(name = "key", length = 40)
    public String getKey() {
	return key;
    }

    /**
     * @param key
     *            the key to set
     */
    public void setKey(String key) {
	this.key = key;
    }

    /**
     * @return the status
     */
    @Column(name = "status", length = 55)
    public String getStatus() {
	return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus(String status) {
	this.status = status;
    }

    /**
     * @return the notes
     */
    @Column(name = "notes", length = 2000)
    public String getNotes() {
	return notes;
    }

    /**
     * @param notes
     *            the notes to set
     */
    public void setNotes(String notes) {
	this.notes = notes;
    }

    /**
     * @return the duration
     */
    @Column(name = "duration")
    public Long getDuration() {
	return duration;
    }

    /**
     * @param duration
     *            the duration to set
     */
    public void setDuration(Long duration) {
	this.duration = duration;
    }

    /**
     * @return the amazonKey
     */
    @Column(name = "s2_key")
    public String getAmazonKey() {
	return amazonKey;
    }

    /**
     * @param amazonKey
     *            the amazonKey to set
     */
    public void setAmazonKey(String amazonKey) {
	this.amazonKey = amazonKey;
    }

    /**
     * @return the uploadedBy
     */
    @ManyToOne(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public User getUploadedBy() {
	return uploadedBy;
    }

    /**
     * @param uploadedBy
     *            the uploadedBy to set
     */
    public void setUploadedBy(User uploadedBy) {
	this.uploadedBy = uploadedBy;
    }

}
