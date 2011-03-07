package com.viviquity.readmy.bean;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class UserBean {

    private Long id;
    private String email;
    private String instrument;
    private String firstName;
    private String surname;
    private String town;
    private String mobile;
    private CommonsMultipartFile image;
    private String photo;

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
     * @return the email
     */
    public String getEmail() {
	return email;
    }

    /**
     * @param email
     *            the email to set
     */
    public void setEmail(String email) {
	this.email = email;
    }

    /**
     * @return the instrument
     */
    public String getInstrument() {
	return instrument;
    }

    /**
     * @param instrument
     *            the instrument to set
     */
    public void setInstrument(String instrument) {
	this.instrument = instrument;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
	return firstName;
    }

    /**
     * @param firstName
     *            the firstName to set
     */
    public void setFirstName(String firstName) {
	this.firstName = firstName;
    }

    /**
     * @return the surname
     */
    public String getSurname() {
	return surname;
    }

    /**
     * @param surname
     *            the surname to set
     */
    public void setSurname(String surname) {
	this.surname = surname;
    }

    /**
     * @return the town
     */
    public String getTown() {
	return town;
    }

    /**
     * @param town
     *            the town to set
     */
    public void setTown(String town) {
	this.town = town;
    }

    /**
     * @return the mobile
     */
    public String getMobile() {
	return mobile;
    }

    /**
     * @param mobile
     *            the mobile to set
     */
    public void setMobile(String mobile) {
	this.mobile = mobile;
    }

    /**
     * @return the image
     */
    public CommonsMultipartFile getImage() {
	return image;
    }

    /**
     * @param image
     *            the image to set
     */
    public void setImage(CommonsMultipartFile image) {
	this.image = image;
    }

    /**
     * @return the photo
     */
    public String getPhoto() {
	return photo;
    }

    /**
     * @param photo
     *            the photo to set
     */
    public void setPhoto(String photo) {
	this.photo = photo;
    }

}
