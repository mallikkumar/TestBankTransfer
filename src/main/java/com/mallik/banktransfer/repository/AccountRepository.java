package com.mallik.banktransfer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mallik.banktransfer.model.Account;

/**
 * This interface is like an entity manager for Account table. This manages the entities by detaching/ attaching entities to database and transaction context. 
 * This also enables built-in JPQL queries without even writing any implementation
 * @author Mallik Kumar
 *
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long>{
	Account findByAccountNumberEquals(String accountNumber);
}
