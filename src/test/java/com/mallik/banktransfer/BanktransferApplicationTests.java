package com.mallik.banktransfer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.mallik.banktransfer.exception.InsufficientFundsException;
import com.mallik.banktransfer.exception.UnknownAccountException;
import com.mallik.banktransfer.model.Transaction;
import com.mallik.banktransfer.service.BankTransferServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest()
class BanktransferApplicationTests {

	@Autowired
	private BankTransferServiceImpl bankTransferService;

    @Test
    @DisplayName("Get All Accounts")
    public void testGetAllAccounts() {
    	//Test the getAllAccounts()
    	assertEquals(3, bankTransferService.getAllAccounts().size());
    }

    @Test
    @DisplayName("Find Account By Account Number")
    public void testFindByAccountNumber() {
    	//Test findByAccountNumber()
    	assertNotNull(bankTransferService.findByAccountNumber("12345"));
    }
    
    @Test
    @DisplayName("Perform a Transaction")
    public void testperformTransaction() {
    	
    	//Test performBankTransfer()
    	Transaction transaction = new Transaction("12345", "23456", new BigDecimal(45));
    	assertTrue(bankTransferService.performBankTransfer(transaction));

    	//Test getAllTransactions()
    	assertThat(bankTransferService.getAllTransactions().size()).isEqualTo(1);
    }
    
    @Test
    @DisplayName("Negative Scenario - Insufficient Funds")
    public void testInsufficientFunds() {
    	
    	// Transfer more funds than the source account has
        assertThrows(InsufficientFundsException.class, () -> 
        						bankTransferService.performBankTransfer(new Transaction("12345", "23456", new BigDecimal(45444))));
    }
    
    @Test
    @DisplayName("Negative Scenario - Unknown Account")
    public void testUnknownAccountException() {
    	assertThrows(UnknownAccountException.class, () -> 
    							bankTransferService.findByAccountNumber("23434"));

    }

}
