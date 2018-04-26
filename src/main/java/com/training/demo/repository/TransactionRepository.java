package com.training.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.training.demo.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction,Integer> {

}
