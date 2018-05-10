package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Transaction;
import com.example.demo.service.TransactionService;

@RestController
public class TransactionController 
{	
	@Autowired
	private TransactionService transactionService;
	
	@RequestMapping(value = "/transaction", method = RequestMethod.GET)
	public String approveTransaction()
	{
		return transactionService.transactionApproval();
	}
	
	@RequestMapping(value = "/getalltransaction", method = RequestMethod.GET)
	public List<Transaction> getAllTransaction()
	{
		return transactionService.getAllTransaction();
	}
}
