package com.traningproject1.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.traningproject1.domain.Transaction;
import com.traningproject1.service.TransactionService;

@RestController
public class TransactionController {
	@Autowired
	TransactionService transactionService;
@RequestMapping(value="/showalltransaction",method=RequestMethod.GET)
public List<Transaction>getAllTransaction()
{
	return transactionService.getAllTransaction();
}
}
