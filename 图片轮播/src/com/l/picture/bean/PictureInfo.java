package com.l.picture.bean;

public class PictureInfo {

	private int id;
	private String message;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public PictureInfo(int id, String message) {
		super();
		this.id = id;
		this.message = message;
	}

}
