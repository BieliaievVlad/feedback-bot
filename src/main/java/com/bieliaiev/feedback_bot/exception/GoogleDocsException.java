package com.bieliaiev.feedback_bot.exception;

public class GoogleDocsException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public GoogleDocsException (String message) {
		super(message);
	}
	
	public GoogleDocsException (String message, Throwable cause) {
		super(message, cause);
	}
}
