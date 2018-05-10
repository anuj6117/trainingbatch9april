package com.trainingproject.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.trainingproject.domain.Transaction;
import com.trainingproject.service.TransactionService;


@RestController
public class TransactionController {

	@Autowired
	TransactionService transactionService;

	
	@RequestMapping(value="approvetransaction",method=RequestMethod.GET)
	public String approveTransaction() {
		
		return transactionService.approveTransaction();
	}
	
	@RequestMapping(value="getAllTransaction",method=RequestMethod.GET)
	public List<Transaction> getAllTransaction() {
		
		return transactionService.getAllTransaction();
	}
}
