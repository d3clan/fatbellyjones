package com.viviquity.readmy.entity;

import com.viviquity.readmy.db.utils.model.GenericEntity;

public class Rating extends GenericEntity {
	
	private Integer score;

	private Long workId;
	
	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public void setWorkId(Long workId) {
		this.workId = workId;
	}

	public Long getWorkId() {
		return workId;
	}
	
}
