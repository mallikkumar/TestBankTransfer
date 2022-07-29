package com.mallik.banktransfer.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mallik.banktransfer.model.Account;
import com.mallik.banktransfer.model.Transaction;
import com.mallik.banktransfer.service.BankTransferService;
/**
 * The BankTransferController is a Restful API endpoint provider class that orchestrates the control flow of the application. 
 * @author Mallik Kumar
 *
 */
@RestController
@RequestMapping(path="api/bankTransfer")
public class BankTransferController {

	@Autowired
	private BankTransferService service;
	
	/**
	 * Gets the list of all the accounts in the bank
	 * @return List<Account> 
	 */
	@GetMapping("/getAllAccounts")
	public List<Account> getAllAccounts() {
		return service.getAllAccounts();
	}
	
	/**
	 * This method gets the list of all the bank transfer transactions
	 * @return List<Transaction>
	 */
	@GetMapping("/getAllTransactions")
	public List<Transaction> getAllTransactions() {
		return service.getAllTransactions();
	}
	
	/**
	 * This method performs the atomic transaction of bank transfer from one account to another
	 * @param Transaction
	 * @return true if the transfer is successful else false
	 */
	@PostMapping("/performTransfer")
	public boolean performBankTransfer(@Valid @RequestBody Transaction transaction) {
		return service.performBankTransfer(transaction);
	}
}
