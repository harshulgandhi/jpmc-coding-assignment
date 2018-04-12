package com.jpmc.securitiesmanager.exceptions;

/**
 * Exception class for SecuritiesManager. Thrown if Input is of Invalid type 
 * 
 * @author Harshul Gandhi
 *
 */
public class InvalidInputException extends Exception {

	/**
	 * Auto generated Serial Version UID
	 */
	private static final long serialVersionUID = -8897366761926202372L;
	
	/**
	 * Exception Constructor
	 * 
	 * @param message
	 */
	public InvalidInputException(String message) {
		super(message);
	}

}
