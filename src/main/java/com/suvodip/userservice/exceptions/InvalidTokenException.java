package com.suvodip.userservice.exceptions;

public class InvalidTokenException extends Exception {

	private static final long serialVersionUID = 5376789362645396086L;

	public InvalidTokenException(String message) {
		super(message);
	}
}
