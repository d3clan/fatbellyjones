package com.viviquity.readmy.entity;

import java.util.Collection;

import com.viviquity.readmy.db.utils.model.GenericEntity;

public class User extends GenericEntity {

	private String email;
	
	private String username;
	
	private String firstName;
	
	private String surname;
	
	private String country;
	
	private String city;

	private String password;

	private String statement;

	private String photo;

	private Account account;
	
	private Collection<Series> allSeries;
	
	private Collection<Bookmark> bookmarks;
	
	private Collection<Favourite> favourites;
	
	private Collection<AccessRight> accessRights;
	
	private Role role;
	
	public Collection<AccessRight> getUserAccessRights() {
		return accessRights;
	}

	public void setUserAccessRights(Collection<AccessRight> userAccessRights) {
		this.accessRights = userAccessRights;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Collection<Series> getAllSeries() {
		return allSeries;
	}

	public void setAllSeries(Collection<Series> allSeries) {
		this.allSeries = allSeries;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getStatement() {
		return statement;
	}

	public void setStatement(String statement) {
		this.statement = statement;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public void setBookmarks(Collection<Bookmark> bookmarks) {
		this.bookmarks = bookmarks;
	}

	public Collection<Bookmark> getBookmarks() {
		return bookmarks;
	}

	public void setFavourites(Collection<Favourite> favourites) {
		this.favourites = favourites;
	}

	public Collection<Favourite> getFavourites() {
		return favourites;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Collection<AccessRight> getAccessRights() {
		return accessRights;
	}

	public void setAccessRights(Collection<AccessRight> accessRights) {
		this.accessRights = accessRights;
	}
	
	public Integer getNumberWorksPublished() {
		int counter = 0;
		for (Series series : allSeries) {
			for (Work work : series.getWorks()) {
				if (work.getPublished())
					counter++;
			}
		}
		return counter;
	}
	
	public Boolean getPublished() {
		for (Series series : getAllSeries()) {
			for (Work work : series.getWorks()) {
				if (work.getPublished()) {
					return true;
				}
			}
		}
		return false;
	}
}
