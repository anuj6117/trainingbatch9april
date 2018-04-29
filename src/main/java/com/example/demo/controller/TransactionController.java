package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.OrderDetails;
import com.example.demo.model.Transaction;
import com.example.demo.service.TransactionService;

@RestController
public class TransactionController {
	
	@Autowired
	private TransactionService transactionService;
	
	@RequestMapping("/showalltransaction")
	public List<Transaction> showAllTransaction(){
		return transactionService.showAllTransaction();
	}
	
	@RequestMapping(value="transaction", method=RequestMethod.GET)
	public List<OrderDetails> approvedDepositTransaction(/*@RequestBody UserTransactionDTO userTransactionDto*/) {
		return transactionService.approvedDepositTransaction(/*userTransactionDto*/);
	}

}
