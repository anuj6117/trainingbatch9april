package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Transaction;
import com.example.repository.TransactionRepository;
import com.example.service.TransactionService;

@RestController
public class TransactionController
{   @Autowired
	private TransactionService transactionService;

    @Autowired
    private TransactionRepository transactionRepository;
   @RequestMapping("/transaction")
	public void transaction()
	{
       transactionService.transactionMethod();
	}
   @RequestMapping("/showalltransaction")
   public List<Transaction> showAllTransaction()
   {
	   return transactionRepository.findAll();
   }
}
