package com.viviquity.readmy.entity;

import java.util.Collection;

import com.viviquity.readmy.db.utils.model.GenericEntity;

public class Work extends GenericEntity {
	
	private String name;

	private Collection<Category> categories;

	private Boolean ready;
	
	private Collection<Section> sections;
	
	private Collection<Rating> ratings;
	
	private Collection<Comment> comments;
	
	private Series series;
	
	private Boolean published;
	
	private Collection<String> languages;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collection<Category> getCategories() {
		return categories;
	}

	public void setCategories(Collection<Category> categories) {
		this.categories = categories;
	}

	public Boolean getReady() {
		return ready;
	}

	public void setReady(Boolean ready) {
		this.ready = ready;
	}

	public Collection<Section> getSections() {
		return sections;
	}

	public void setSections(Collection<Section> sections) {
		this.sections = sections;
	}

	public Collection<Rating> getRatings() {
		return ratings;
	}

	public void setRatings(Collection<Rating> ratings) {
		this.ratings = ratings;
	}

	public Collection<Comment> getComments() {
		return comments;
	}

	public void setComments(Collection<Comment> comments) {
		this.comments = comments;
	}

	public Series getSeries() {
		return series;
	}

	public void setSeries(Series series) {
		this.series = series;
	}

	public void setPublished(Boolean published) {
		this.published = published;
	}

	public Boolean getPublished() {
		return published;
	}

	public void setLanguages(Collection<String> languages) {
		this.languages = languages;
	}

	public Collection<String> getLanguages() {
		return languages;
	}
	
	
	
}
