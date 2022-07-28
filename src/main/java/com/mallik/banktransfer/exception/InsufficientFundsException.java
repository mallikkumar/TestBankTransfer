package com.mallik.banktransfer.exception;

/**
 * 
 * @author Mallik Kumar
 * This is a runtime exception that occurs when the the source account doesn't have enough funds to transfer to the destination account. 
 *
 */
public class InsufficientFundsException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public InsufficientFundsException() {
		super("Insufficient account balance to transfer");
	}
	public InsufficientFundsException(String errorMessage) {
		super(errorMessage);
	}
	
}
