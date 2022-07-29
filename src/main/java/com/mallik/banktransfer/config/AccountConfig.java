package com.mallik.banktransfer.config;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import com.mallik.banktransfer.model.Account;
import com.mallik.banktransfer.repository.AccountRepository;
import com.mallik.banktransfer.service.BankTransferService;

/**
 * This is a configuration file that loads the account details file from the externalized file to the database. 
 * It is always better to decouple the data from the code to external file as application.yaml is for the application configuration
 * @author Mallik Kumar
 *
 */
@Configuration
public class AccountConfig {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AccountConfig.class);
	@Value("${application.accounts.file}")
	private String filePath;
	
	@Autowired
	BankTransferService transferService;
	
	@Autowired
	private ResourceLoader resourceLoader;

	/**
	 * This method parses the pipe delimited account records from the external file and loads them to the database
	 * 
	 */
    @Bean
    CommandLineRunner commandLineRunner(AccountRepository accountRepository){
        return args -> {
    		try {
    			final Resource fileResource = resourceLoader.getResource("classpath:" + filePath);
    			List<String> lines = Files.readAllLines(Paths.get(fileResource.getURI()));
    			List<Account> accounts = lines.stream().map(s -> s.split("[|]"))
    													.map(vals -> new Account(null, vals[0], vals[1], new BigDecimal(vals[2])))
    													.collect(Collectors.toList());
    			if(accounts != null) {
    				LOGGER.info("Number of accounts loaded to table are: " + accounts.size());
    				transferService.saveAccounts(accounts);
    			}
    		} catch (IOException e) {
    			throw new RuntimeException("Starup exception... Unable to load the accounts to the table");
    		}
        };
    }
}
