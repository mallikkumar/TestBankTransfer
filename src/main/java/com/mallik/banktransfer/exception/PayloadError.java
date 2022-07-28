package com.mallik.banktransfer.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class defines the structure of the error response when an exception or validation error occurs 
 * @author Mallik Kumar
 *
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayloadError {
    private HttpStatus status;
    private String message;
    private String debugMessage;
}
