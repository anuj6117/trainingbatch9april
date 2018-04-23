package com.trainingproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trainingproject.domain.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction,Integer>{

}
