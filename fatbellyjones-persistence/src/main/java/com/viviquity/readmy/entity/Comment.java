package com.viviquity.readmy.entity;

public class Comment {

	private String text;
	
	private Long workId;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Long getWorkId() {
		return workId;
	}

	public void setWorkId(Long workId) {
		this.workId = workId;
	}
	
}
