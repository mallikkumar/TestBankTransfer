package com.mallik.banktransfer.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * 
 * @author Mallik Kumar
 * ErrorHandler class extends the ResponseEntityExceptionHandler that handles validation errors, 
 * user defined runtime exceptions and unknown exceptions.
 * This then generates a proper understandable error messages to send to the API consumer. 
 *
 */
@ControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

	/**
	 * This method handles when the request body is invalid. An example of such scenario is the request is not in JSON format 
	 */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
        final String message = "JSON request has an invalid format";
        return buildResponseEntity(PayloadError.builder().message(message).debugMessage(message).status(HttpStatus.BAD_REQUEST).build());
    }
    
    /**
     * This method handles the input data validation errors.
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
    	final StringBuilder sb = new StringBuilder();
    	e.getAllErrors().forEach(error -> {
    		sb.append(error.getDefaultMessage());
    	});
    	return buildResponseEntity(PayloadError.builder().message(sb.toString()).debugMessage(sb.toString()).status(HttpStatus.BAD_REQUEST).build());
    }

    /**
     * This is an application handler for defined exception UnknownAccountException. It occurs when the given account number doesn't exist in system.
     */
    @ExceptionHandler(UnknownAccountException.class)
    protected ResponseEntity<Object> handleAccountNumberNotFound(UnknownAccountException e) {
    	return buildResponseEntity(PayloadError.builder().message(e.getMessage()).debugMessage(e.getLocalizedMessage()).status(HttpStatus.BAD_REQUEST).build());
    }

    /**
     * This is an application handler for defined exception InsufficientFundsException. It occurs when the the source account doesn't have enough funds to transfer.
     */
    @ExceptionHandler(InsufficientFundsException.class)
    protected ResponseEntity<Object> handleInsufficientFunds(InsufficientFundsException e) {
    	return buildResponseEntity(PayloadError.builder().message(e.getMessage()).debugMessage(e.getLocalizedMessage()).status(HttpStatus.BAD_REQUEST).build());
    }

    /**
     * This is a private method that builds the error response to the API consumer
     */
    private ResponseEntity<Object> buildResponseEntity(PayloadError error) {
        return new ResponseEntity<>(error, error.getStatus());
    }
  
}
