package com.url.shorter.api.exception;

public class UrlException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;

	public UrlException(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}
