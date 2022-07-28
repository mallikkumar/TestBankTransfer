package com.mallik.banktransfer.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This is the entity class that maps to the entity (table) in the database. 
 * This keeps a log of all bank transfer transactions between accounts 
 * @author Mallik Kumar
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long transactionId;
	
	@NotBlank (message="Source Account cannot be blank")
	@Pattern(regexp="^[0-9]*$", message = "Invalid Source Account")
	private String sourceAccountNumber;
	
	@NotBlank (message="Destination Account cannot be blank")
	@Pattern(regexp="^[0-9]*$", message = "Invalid Destination Account")
	private String destinationAccountNumber;
	
	@Digits(fraction=2, integer=20)
	private BigDecimal amount;
	
	public Transaction(String sourceAccountNumber, String destinationAccountNumber, BigDecimal amount) {
		this.amount = amount;
		this.destinationAccountNumber = destinationAccountNumber;
		this.sourceAccountNumber = sourceAccountNumber;
	}
}
