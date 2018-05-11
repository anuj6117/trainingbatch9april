package com.trainingproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
/*import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
*/

import com.trainingproject.domain.Transaction;
import com.trainingproject.service.TransactionService;

@RestController
public class TransactionController {
	@Autowired
	private TransactionService transactionService;

	@RequestMapping(value = "/transaction", method = RequestMethod.GET)
	public String startTransaction () {
		try {
			return transactionService.startTransaction();
		} catch(Exception e) {
			return "Transaction can't be done :"+e.getMessage();
		}
	}
	
	@RequestMapping(value = "/showalltransaction", method = RequestMethod.GET)
	public List<Transaction> getAllTransaction() {
		return transactionService.getAllTransaction();
	}

}
