package com.mallik.banktransfer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mallik.banktransfer.model.Transaction;

/**
 * This interface is like an entity manager for Transaction table. This manages the entities by detaching/ attaching entities to database and transaction context. 
 * This also enables built-in JPQL queries without even writing any implementation
 * @author Mallik Kumar
 *
 */
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
