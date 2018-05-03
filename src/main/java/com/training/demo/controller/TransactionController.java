
package com.training.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.training.demo.model.Transaction;
import com.training.demo.repository.OrderRepository;
import com.training.demo.repository.TransactionRepository;
import com.training.demo.service.TransactionService;

@RestController
public class TransactionController {
	
	@Autowired
	OrderRepository orderRepository;
	@Autowired
	TransactionRepository transactionRepository;
	
	@Autowired
	TransactionService transactionService;
	
	@RequestMapping(value="/transaction", method=RequestMethod.GET)
	public String transactionApproval() {	
		
		return transactionService.gettransaction();
			}
	
	@RequestMapping(value="/showalltransaction")
	public List<Transaction> getAllTransactions()
	{
			return transactionService.getAllTransactions();
	}
}
