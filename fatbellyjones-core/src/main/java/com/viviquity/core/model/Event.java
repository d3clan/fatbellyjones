package com.viviquity.core.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * A class that's wraps much of the functionality of the necessary elements for
 * the calendar
 * 
 * @author Declan Newman
 * 
 */
@Entity
@Table(name = "event")
public class Event extends BaseEntity<Long> {

    private Long id;
    private String title;
    private Date start;
    private Date endDate;
    private User user;
    private boolean allDay = true;
    private String link;
    private String host;
    private String location;
    private String city;
    private String street;
    private String phone;
    private String email;
    private String description;
    private String tagline;
    private boolean isGig = false;
    private boolean tentative = true;
    private boolean confirmed = false;
    private BigDecimal charge;
    private Long facebookId;
    private String poster;
    private String facebookImage;
    private String facebookEvent;
    private String tweetId;
    private List<ConfirmedUser> confirmedUsers;
    private List<PlaylistEntry> playlist;

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
     * @return the start
     */
    @Column(name = "start_date", nullable = false)
    public Date getStart() {
	return start;
    }

    /**
     * @param start
     *            the start to set
     */
    public void setStart(Date start) {
	this.start = start;
    }

    /**
     * @return the end
     */
    @Column(name = "end_date", nullable = false)
    public Date getEndDate() {
	return endDate;
    }

    /**
     * @param end
     *            the end to set
     */
    public void setEndDate(Date end) {
	this.endDate = end;
    }

    /**
     * @return the user
     */
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
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

    /**
     * @return the allDay
     */
    @Column(name = "is_all_day")
    public boolean isAllDay() {
	return allDay;
    }

    /**
     * @param allDay
     *            the allDay to set
     */
    public void setAllDay(boolean allDay) {
	this.allDay = allDay;
    }

    /**
     * @return the link
     */
    @Column(name = "link")
    public String getLink() {
	return link;
    }

    /**
     * @param link
     *            the link to set
     */
    public void setLink(String link) {
	this.link = link;
    }

    /**
     * @return the host
     */
    @Column(name = "host")
    public String getHost() {
	return host;
    }

    /**
     * @param host
     *            the host to set
     */
    public void setHost(String host) {
	this.host = host;
    }

    /**
     * @return the street
     */
    @Column(name = "street")
    public String getStreet() {
	return street;
    }

    /**
     * @param street
     *            the street to set
     */
    public void setStreet(String street) {
	this.street = street;
    }

    /**
     * @return the phone
     */
    @Column(name = "phone")
    public String getPhone() {
	return phone;
    }

    /**
     * @param phone
     *            the phone to set
     */
    public void setPhone(String phone) {
	this.phone = phone;
    }

    /**
     * @return the email
     */
    @Column(name = "email")
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
     * @return the description
     */
    @Column(name = "description")
    public String getDescription() {
	return description;
    }

    /**
     * @param description
     *            the description to set
     */
    public void setDescription(String description) {
	this.description = description;
    }

    /**
     * @return the tagline
     */
    @Column(name = "tagline")
    public String getTagline() {
	return tagline;
    }

    /**
     * @param tagline
     *            the tagline to set
     */
    public void setTagline(String tagline) {
	this.tagline = tagline;
    }

    /**
     * @return the location
     */
    @Column(name = "location")
    public String getLocation() {
	return location;
    }

    /**
     * @param location
     *            the location to set
     */
    public void setLocation(String location) {
	this.location = location;
    }

    /**
     * @return the city
     */
    @Column(name = "city")
    public String getCity() {
	return city;
    }

    /**
     * @param city
     *            the city to set
     */
    public void setCity(String city) {
	this.city = city;
    }

    /**
     * @return the isGig
     */
    @Column(name = "is_gig")
    public boolean getIsGig() {
	return isGig;
    }

    /**
     * @param isGig
     *            the isGig to set
     */
    public void setIsGig(boolean isGig) {
	this.isGig = isGig;
    }

    /**
     * @return the confirmedUsers
     */
    @OneToMany(cascade = { CascadeType.ALL })
    public List<ConfirmedUser> getConfirmedUsers() {
	return confirmedUsers;
    }

    /**
     * @param confirmedUsers
     *            the confirmedUsers to set
     */
    public void setConfirmedUsers(List<ConfirmedUser> confirmedUsers) {
	this.confirmedUsers = confirmedUsers;
    }

    /**
     * @return the tentative
     */
    @Column(name = "is_tentative")
    public boolean isTentative() {
	return tentative;
    }

    /**
     * @param tentative
     *            the tentative to set
     */
    public void setTentative(boolean tentative) {
	this.tentative = tentative;
    }

    /**
     * @return the confirmed
     */
    @Column(name = "is_confirmed")
    public boolean isConfirmed() {
	return confirmed;
    }

    /**
     * @param confirmed
     *            the confirmed to set
     */
    public void setConfirmed(boolean confirmed) {
	this.confirmed = confirmed;
    }

    /**
     * @return the charge
     */
    @Column(name = "charge", precision = 8, scale = 2)
    public BigDecimal getCharge() {
	return charge;
    }

    /**
     * @param charge
     *            the charge to set
     */
    public void setCharge(BigDecimal charge) {
	this.charge = charge;
    }

    /**
     * @return the facebookId
     */
    @Column(name = "facebook_id")
    public Long getFacebookId() {
	return facebookId;
    }

    /**
     * @param facebookId
     *            the facebookId to set
     */
    public void setFacebookId(Long facebookId) {
	this.facebookId = facebookId;
    }

    /**
     * @return the poster
     */
    @Column(name = "poster")
    public String getPoster() {
	return poster;
    }

    /**
     * @param poster
     *            the poster to set
     */
    public void setPoster(String poster) {
	this.poster = poster;
    }

    /**
     * @return the facebookImage
     */
    @Column(name = "facebook_image")
    public String getFacebookImage() {
	return facebookImage;
    }

    /**
     * @param facebookImage
     *            the facebookImage to set
     */
    public void setFacebookImage(String facebookImage) {
	this.facebookImage = facebookImage;
    }

    /**
     * @return the facebookEvent
     */
    @Column(name = "facebook_event")
    public String getFacebookEvent() {
	return facebookEvent;
    }

    /**
     * @param facebookEvent
     *            the facebookEvent to set
     */
    public void setFacebookEvent(String facebookEvent) {
	this.facebookEvent = facebookEvent;
    }

    /**
     * @return the tweet
     */
    @Column(name = "tweet_id")
    public String getTweet() {
	return tweetId;
    }

    /**
     * @param tweet
     *            the tweet to set
     */
    public void setTweet(String tweetId) {
	this.tweetId = tweetId;
    }

    /**
     * @return the playlist
     */
    @OneToMany(cascade = { CascadeType.ALL })
    public List<PlaylistEntry> getPlaylist() {
	return playlist;
    }

    /**
     * @param playlist
     *            the playlist to set
     */
    public void setPlaylist(List<PlaylistEntry> playlist) {
	this.playlist = playlist;
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
	result = prime * result + (allDay ? 1231 : 1237);
	result = prime * result + ((city == null) ? 0 : city.hashCode());
	result = prime * result + ((confirmedUsers == null) ? 0 : confirmedUsers.hashCode());
	result = prime * result + ((description == null) ? 0 : description.hashCode());
	result = prime * result + ((email == null) ? 0 : email.hashCode());
	result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
	result = prime * result + ((host == null) ? 0 : host.hashCode());
	result = prime * result + ((id == null) ? 0 : id.hashCode());
	result = prime * result + (isGig ? 1231 : 1237);
	result = prime * result + ((link == null) ? 0 : link.hashCode());
	result = prime * result + ((location == null) ? 0 : location.hashCode());
	result = prime * result + ((phone == null) ? 0 : phone.hashCode());
	result = prime * result + ((start == null) ? 0 : start.hashCode());
	result = prime * result + ((street == null) ? 0 : street.hashCode());
	result = prime * result + ((tagline == null) ? 0 : tagline.hashCode());
	result = prime * result + ((title == null) ? 0 : title.hashCode());
	result = prime * result + ((user == null) ? 0 : user.hashCode());
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
	Event other = (Event) obj;
	if (allDay != other.allDay)
	    return false;
	if (city == null) {
	    if (other.city != null)
		return false;
	} else if (!city.equals(other.city))
	    return false;
	if (confirmedUsers == null) {
	    if (other.confirmedUsers != null)
		return false;
	} else if (!confirmedUsers.equals(other.confirmedUsers))
	    return false;
	if (description == null) {
	    if (other.description != null)
		return false;
	} else if (!description.equals(other.description))
	    return false;
	if (email == null) {
	    if (other.email != null)
		return false;
	} else if (!email.equals(other.email))
	    return false;
	if (endDate == null) {
	    if (other.endDate != null)
		return false;
	} else if (!endDate.equals(other.endDate))
	    return false;
	if (host == null) {
	    if (other.host != null)
		return false;
	} else if (!host.equals(other.host))
	    return false;
	if (id == null) {
	    if (other.id != null)
		return false;
	} else if (!id.equals(other.id))
	    return false;
	if (isGig != other.isGig)
	    return false;
	if (link == null) {
	    if (other.link != null)
		return false;
	} else if (!link.equals(other.link))
	    return false;
	if (location == null) {
	    if (other.location != null)
		return false;
	} else if (!location.equals(other.location))
	    return false;
	if (phone == null) {
	    if (other.phone != null)
		return false;
	} else if (!phone.equals(other.phone))
	    return false;
	if (start == null) {
	    if (other.start != null)
		return false;
	} else if (!start.equals(other.start))
	    return false;
	if (street == null) {
	    if (other.street != null)
		return false;
	} else if (!street.equals(other.street))
	    return false;
	if (tagline == null) {
	    if (other.tagline != null)
		return false;
	} else if (!tagline.equals(other.tagline))
	    return false;
	if (title == null) {
	    if (other.title != null)
		return false;
	} else if (!title.equals(other.title))
	    return false;
	if (user == null) {
	    if (other.user != null)
		return false;
	} else if (!user.equals(other.user))
	    return false;
	return true;
    }

}
