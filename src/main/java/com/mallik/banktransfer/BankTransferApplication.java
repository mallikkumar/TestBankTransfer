package com.mallik.banktransfer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * This is the class where the execution of bank transfer Restful Application starts and exposes itself over HTTP
 * @author Mallik Kumar
 *
 */
@SpringBootApplication
public class BankTransferApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankTransferApplication.class, args);
	}
}
