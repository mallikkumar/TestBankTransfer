package com.mallik.banktransfer.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This is the entity class that maps to the entity (table) in the database. This holds financial account details of the given customer
 * @author Mallik Kumar
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long accountId;
	private String accountNumber;
	private String name;
	private BigDecimal balance;
	
	public Account(String accountNumber, String name, BigDecimal balance) {
		this.accountNumber = accountNumber;
		this.name = name;
		this.balance = balance;
	}
}
