package com.viviquity.readmy.entity;

import java.util.Collection;

import com.viviquity.readmy.db.utils.model.GenericEntity;

public class Section extends GenericEntity {

	private Collection<Page> pages;

	private Integer number;

	private String name;
	
	private Work work;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collection<Page> getPages() {
		return pages;
	}

	public void setPages(Collection<Page> pages) {
		this.pages = pages;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public void setWorkId(Work work) {
		this.work = work;
	}

	public Work getWork() {
		return work;
	}
}
