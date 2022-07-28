package com.mallik.banktransfer.exception;

/**
 * This is a runtime exception that occurs when the the the given account number is not found in the system.
 * @author Mallik Kumar
 *
 */
public class UnknownAccountException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public UnknownAccountException() {
		super("Unable to get the account details from the database");
	}
	public UnknownAccountException(String errorMessage) {
		super(errorMessage);
	}

}
