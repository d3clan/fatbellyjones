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

    private Long id;
    private String title;
    private String author;
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
    @Column(name = "author")
    public String getAuthor() {
	return author;
    }

    /**
     * @param author
     *            the author to set
     */
    public void setAuthor(String author) {
	this.author = author;
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
