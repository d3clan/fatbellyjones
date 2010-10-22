package com.viviquity.core.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "fatbelly_user")
public class User extends BaseEntity<Long> {

    private Long id;
    private String email;
    private String username;
    private String firstName;
    private String surname;
    private String mobile;
    private String country;
    private String city;
    private String password;
    private String statement;
    private String photo;
    private Role role;
    private List<Event> events;
    private List<Tune> tunes;
    private List<PlaylistEntry> playlistEntries;
    private String className;

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

    @Column(name = "email")
    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    @Column(name = "password")
    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
    }

    @Column(name = "statement")
    public String getStatement() {
	return statement;
    }

    public void setStatement(String statement) {
	this.statement = statement;
    }

    @Column(name = "photo")
    public String getPhoto() {
	return photo;
    }

    public void setPhoto(String photo) {
	this.photo = photo;
    }

    @Column(name = "username")
    public String getUsername() {
	return username;
    }

    public void setUsername(String username) {
	this.username = username;
    }

    @Column(name = "first_name")
    public String getFirstName() {
	return firstName;
    }

    public void setFirstName(String firstName) {
	this.firstName = firstName;
    }

    @Column(name = "surname")
    public String getSurname() {
	return surname;
    }

    public void setSurname(String surname) {
	this.surname = surname;
    }

    /**
     * @return the mobile
     */
    @Column(name = "mobile")
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
     * @return the role
     */
    @ManyToOne(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    public Role getRole() {
	return role;
    }

    /**
     * @param role
     *            the role to set
     */
    public void setRole(Role role) {
	this.role = role;
    }

    @Column(name = "country")
    public String getCountry() {
	return country;
    }

    public void setCountry(String country) {
	this.country = country;
    }

    @Column(name = "city")
    public String getCity() {
	return city;
    }

    public void setCity(String city) {
	this.city = city;
    }

    /**
     * @return the events
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.LAZY, orphanRemoval = true)
    public List<Event> getEvents() {
	return events;
    }

    /**
     * @param events
     *            the events to set
     */
    public void setEvents(List<Event> events) {
	this.events = events;
    }

    /**
     * @return the tunes
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "uploadedBy", fetch = FetchType.LAZY, orphanRemoval = true)
    public List<Tune> getTunes() {
	return tunes;
    }

    /**
     * @param tunes
     *            the tunes to set
     */
    public void setTunes(List<Tune> tunes) {
	this.tunes = tunes;
    }

    /**
     * @return the playlistEntries
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.LAZY, orphanRemoval = true)
    public List<PlaylistEntry> getPlaylistEntries() {
	return playlistEntries;
    }

    /**
     * @param playlistEntries
     *            the playlistEntries to set
     */
    public void setPlaylistEntries(List<PlaylistEntry> playlistEntries) {
	this.playlistEntries = playlistEntries;
    }

    /**
     * @return the className
     */
    @Column(name = "className")
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

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((city == null) ? 0 : city.hashCode());
	result = prime * result + ((className == null) ? 0 : className.hashCode());
	result = prime * result + ((country == null) ? 0 : country.hashCode());
	result = prime * result + ((email == null) ? 0 : email.hashCode());
	result = prime * result + ((events == null) ? 0 : events.hashCode());
	result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
	result = prime * result + ((id == null) ? 0 : id.hashCode());
	result = prime * result + ((mobile == null) ? 0 : mobile.hashCode());
	result = prime * result + ((password == null) ? 0 : password.hashCode());
	result = prime * result + ((photo == null) ? 0 : photo.hashCode());
	result = prime * result + ((role == null) ? 0 : role.hashCode());
	result = prime * result + ((statement == null) ? 0 : statement.hashCode());
	result = prime * result + ((surname == null) ? 0 : surname.hashCode());
	result = prime * result + ((username == null) ? 0 : username.hashCode());
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
	User other = (User) obj;
	if (city == null) {
	    if (other.city != null)
		return false;
	} else if (!city.equals(other.city))
	    return false;
	if (className == null) {
	    if (other.className != null)
		return false;
	} else if (!className.equals(other.className))
	    return false;
	if (country == null) {
	    if (other.country != null)
		return false;
	} else if (!country.equals(other.country))
	    return false;
	if (email == null) {
	    if (other.email != null)
		return false;
	} else if (!email.equals(other.email))
	    return false;
	if (events == null) {
	    if (other.events != null)
		return false;
	} else if (!events.equals(other.events))
	    return false;
	if (firstName == null) {
	    if (other.firstName != null)
		return false;
	} else if (!firstName.equals(other.firstName))
	    return false;
	if (id == null) {
	    if (other.id != null)
		return false;
	} else if (!id.equals(other.id))
	    return false;
	if (mobile == null) {
	    if (other.mobile != null)
		return false;
	} else if (!mobile.equals(other.mobile))
	    return false;
	if (password == null) {
	    if (other.password != null)
		return false;
	} else if (!password.equals(other.password))
	    return false;
	if (photo == null) {
	    if (other.photo != null)
		return false;
	} else if (!photo.equals(other.photo))
	    return false;
	if (role == null) {
	    if (other.role != null)
		return false;
	} else if (!role.equals(other.role))
	    return false;
	if (statement == null) {
	    if (other.statement != null)
		return false;
	} else if (!statement.equals(other.statement))
	    return false;
	if (surname == null) {
	    if (other.surname != null)
		return false;
	} else if (!surname.equals(other.surname))
	    return false;
	if (username == null) {
	    if (other.username != null)
		return false;
	} else if (!username.equals(other.username))
	    return false;
	return true;
    }

}
