package com.bieliaiev.feedback_bot.exception;

public class OpenAiException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public OpenAiException (String message) {
		super(message);
	}
	
	public OpenAiException (String message, Throwable cause) {
		super(message, cause);
	}
}
