package com.trainingproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.trainingproject.domain.Transaction;
import com.trainingproject.service.TransactionService;
import com.trainingproject.service.TransactionService2;

@RestController
public class TransactionController {

	@Autowired
	TransactionService transactionService;
	
	@Autowired
	TransactionService2 transactionService2;
	
	
	@RequestMapping(value="approvetransaction",method=RequestMethod.GET)
	public String approveTransaction() {
		
		return transactionService2.approveTransaction();
	}
	
	@RequestMapping(value="getAllTransaction",method=RequestMethod.GET)
	public List<Transaction> getAllTransaction() {
		
		return transactionService.getAllTransaction();
	}
}
