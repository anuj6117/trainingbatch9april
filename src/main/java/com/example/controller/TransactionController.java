package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.service.TransactionService;

@RestController
public class TransactionController
{   @Autowired
	private TransactionService transactionService;
   @RequestMapping("/transaction")
	public String transaction()
	{
    	return transactionService.transactionMethod();
	}
}
