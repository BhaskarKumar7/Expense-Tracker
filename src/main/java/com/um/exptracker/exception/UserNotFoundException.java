package com.um.exptracker.exception;

public class UserNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7465991969787860952L;
	
	public UserNotFoundException(String message) {
		super(message);
	} 

}
