package com.mallik.banktransfer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.mallik.banktransfer.exception.InsufficientFundsException;
import com.mallik.banktransfer.exception.UnknownAccountException;
import com.mallik.banktransfer.model.Account;
import com.mallik.banktransfer.model.Transaction;
import com.mallik.banktransfer.service.BankTransferServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest()
class BanktransferApplicationTests {

	@Autowired
	private BankTransferServiceImpl bankTransferService;

	// Below are the list of 2 test accounts added for testing purposes
    List<Account> accounts = Stream.of(
    				new Account(1L, "22", "Rama", new BigDecimal(250)), 
    				new Account(2L, "33", "Krishna", new BigDecimal(100)))
    																		.collect(Collectors.toList());
    
    @Test
    @DisplayName("Load Accounts And Verify They Exist")
    public void loadAccountsAndVerify() {
    	//saveAccounts is tested with 2 accounts
    	bankTransferService.saveAccounts(accounts);
        
    	//Test the getAllAccounts()
    	assertEquals(2, bankTransferService.getAllAccounts().size());
        
    	//Test findByAccountNumber()
    	assertNotNull(bankTransferService.findByAccountNumber("22"));
    }
    
    @Test
    @DisplayName("Perform a Transaction")
    public void completeTransactionTest() {
    	
    	//saveAccounts is tested with 2 accounts
    	bankTransferService.saveAccounts(accounts);
        
    	//Test performBankTransfer()
    	Transaction transaction = new Transaction("22", "33", new BigDecimal(45));
    	assertTrue(bankTransferService.performBankTransfer(transaction));
	      
    	//Test getAllTransactions()
    	assertThat(bankTransferService.getAllTransactions().size()).isEqualTo(1);
    }
    
    @Test
    @DisplayName("Negative Scenario - Insufficient Funds")
    public void testInsufficientFunds() {
    	
    	//saveAccounts is tested with 2 accounts
        bankTransferService.saveAccounts(accounts);
    	// Transfer more funds than the source account has
        assertThrows(InsufficientFundsException.class, () -> 
        								bankTransferService.performBankTransfer(new Transaction("22", "33", new BigDecimal(45444))));
    }
    
    @Test
    @DisplayName("Negative Scenario - Unknown Account")
    public void testUnknownAccountException() {
    	assertThrows(UnknownAccountException.class, () -> 
    									bankTransferService.findByAccountNumber("23434"));

    }

}
