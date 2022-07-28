package com.mallik.banktransfer.service;

import java.math.BigDecimal;
import java.util.List;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mallik.banktransfer.exception.InsufficientFundsException;
import com.mallik.banktransfer.exception.UnknownAccountException;
import com.mallik.banktransfer.model.Account;
import com.mallik.banktransfer.model.Transaction;
import com.mallik.banktransfer.repository.AccountRepository;
import com.mallik.banktransfer.repository.TransactionRepository;

/**
 * This is a facade implementation to the service layer that injects various container-managed services like 
 * transaction context, timer service, batch capability etc.
 * @author Mallik Kumar
 *
 */
@Service
public class BankTransferServiceImpl implements BankTransferService {

	private static final Logger LOGGER = LoggerFactory.getLogger(BankTransferServiceImpl.class);
	
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private TransactionRepository transactionRepository;
	
	/**
	 * Transactional method that saves the list of accounts to the table
	 */
	@Override
	@Transactional(value=TxType.REQUIRED)
	public void saveAccounts(List<Account> accounts) {
		accountRepository.saveAll(accounts);
	}

	/**
	 * This method retrieves the list of all accounts from the table
	 */
	@Override
	public List<Account> getAllAccounts() {
		return accountRepository.findAll();
	}

	/**
	 * This method retrieves the Account details using the accountNumber.
	 * @return Account details. If account is not found, it returns null
	 */
	@Override
	public Account findByAccountNumber(String accountNumber) {
		return getValidatedAccount(accountNumber);
	}

	/**
	 * This returns the list of bank transfer transactions that are performed between the accounts
	 */
	@Override
	public List<Transaction> getAllTransactions() {
		return transactionRepository.findAll();
	}
	
	/**
	 * Atomic transactional operation that performs the bank transfer between accounts. 
	 * Also, records the transaction to the Transaction table
	 * @return true if successful else false
	 */
	@Override
	@Transactional(value = TxType.REQUIRED)
	public boolean performBankTransfer(Transaction transaction) {
		final BigDecimal amount = transaction.getAmount();
		final Account sourceAccount = getValidatedAccount(transaction.getSourceAccountNumber());
		final Account destinationAccount = getValidatedAccount(transaction.getDestinationAccountNumber());
		
		if(amount.compareTo(sourceAccount.getBalance()) == 1) {
			LOGGER.error("Insufficient funds in the account to transfer");
			throw new InsufficientFundsException("Insufficient funds to perofrm the transaction");
		} else {
			
			//perform the transfer
			destinationAccount.setBalance(destinationAccount.getBalance().add(amount));
			sourceAccount.setBalance(sourceAccount.getBalance().subtract(amount));
			
			//persist the changes to accounts and also persist the transaction to transaction table
			accountRepository.save(destinationAccount);
			accountRepository.save(sourceAccount);
			transactionRepository.save(transaction);

			LOGGER.debug("An amount of {} transferred successfully from {} to {}", 
					amount, transaction.getSourceAccountNumber(), transaction.getDestinationAccountNumber());

			return true;
		}
	}
		
	private Account getValidatedAccount(String accountNumber) {
		final Account account = accountRepository.findByAccountNumberEquals(accountNumber);
		if(account == null) {
			LOGGER.error("Unable to get the account details for: " + accountNumber);
			throw new UnknownAccountException("Unable to get the account details for: " + accountNumber);
		}
		return account;
	}

}
