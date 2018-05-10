package com.trainingproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trainingproject.domain.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Integer> {

}
