package com.suvodip.userservice.exceptions;

public class UserAlreadyExistException extends Exception {

	private static final long serialVersionUID = 1L;

	public UserAlreadyExistException(String exception) {
		super(exception);
	}
}
