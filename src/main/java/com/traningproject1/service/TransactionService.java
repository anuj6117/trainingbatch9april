package com.traningproject1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.traningproject1.domain.Transaction;
import com.traningproject1.repository.TransactionRepository;

@Service
public class TransactionService {

@Autowired
TransactionRepository transactionRepository;
public List<Transaction> getAllTransaction()
{
	
	return transactionRepository.findAll();
}
}
