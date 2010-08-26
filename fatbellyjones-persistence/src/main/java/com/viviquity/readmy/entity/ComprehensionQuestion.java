package com.viviquity.readmy.entity;

import java.util.Collection;

public class ComprehensionQuestion {
	
	private String question;
	
	private Collection<String> correctAnswers;
	
	private Collection<String> wrongAnswers;
	
	private Long workId;

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public Collection<String> getCorrectAnswers() {
		return correctAnswers;
	}

	public void setCorrectAnswers(Collection<String> correctAnswers) {
		this.correctAnswers = correctAnswers;
	}

	public Collection<String> getWrongAnswers() {
		return wrongAnswers;
	}

	public void setWrongAnswers(Collection<String> wrongAnswers) {
		this.wrongAnswers = wrongAnswers;
	}

	public Long getWorkId() {
		return workId;
	}

	public void setWorkId(Long workId) {
		this.workId = workId;
	}
	
}
