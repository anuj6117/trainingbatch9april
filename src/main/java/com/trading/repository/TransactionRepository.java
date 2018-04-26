package com.trading.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trading.domain.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	

}
