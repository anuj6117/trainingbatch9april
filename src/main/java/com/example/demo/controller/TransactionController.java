package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.UserTransactionDTO;
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
	
	@RequestMapping(value="approvedDepositTransaction", method=RequestMethod.POST)
	public String approvedDepositTransaction(@RequestBody UserTransactionDTO userTransactionDto) {
		return transactionService.approvedDepositTransaction(userTransactionDto);
	}

}
