package com.store.model;

import java.util.Date;

public class Response {
	private Date timestamp;
	private String message;
	private String details;
	private int status;

	public Response(Date timestamp, String message, String details, int status) {
		this.timestamp = timestamp;
		this.status = status;
		this.message = message;
		this.details = details;
		
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public String getMessage() {
		return message;
	}

	public String getDetails() {
		return details;
	}
	
	public int getStatus() {
		return status;
	}
	

}