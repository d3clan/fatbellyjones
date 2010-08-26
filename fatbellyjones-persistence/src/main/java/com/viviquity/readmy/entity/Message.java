package com.viviquity.readmy.entity;

public class Message {

	private String text;
	
	private User from;
	
	private User to;
	
	private String subject;
	
	private MessageType type;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public User getFrom() {
		return from;
	}

	public void setFrom(User from) {
		this.from = from;
	}

	public User getTo() {
		return to;
	}

	public void setTo(User to) {
		this.to = to;
	}

	public MessageType getType() {
		return type;
	}

	public void setType(MessageType type) {
		this.type = type;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getSubject() {
		return subject;
	}
	
}
