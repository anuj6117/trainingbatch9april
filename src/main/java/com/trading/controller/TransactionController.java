package com.trading.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.trading.domain.Transaction;
import com.trading.handler.ResponseHandler;
import com.trading.services.TransactionService;

@RestController

public class TransactionController {
	
	@Autowired
	private TransactionService transactionService;
	
	@RequestMapping(value = "/transaction", method = RequestMethod.GET)
	
		public ResponseEntity<Object> transaction()
		{
		
		Map<String, Object> result = null;

		try {
			result = transactionService.transaction();
			if (result.get("isSuccess").equals(true)) {
				return ResponseHandler.generateResponse(HttpStatus.OK, true, result.get("message").toString(), result);
			} else {
				return ResponseHandler.generateResponse(HttpStatus.OK, false, result.get("message").toString(), result);
			}
		} catch (Exception e) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), result); 
		} 

		
		}
	
	@RequestMapping(value = "/showalltransaction", method = RequestMethod.GET)
	
	public Iterable <Transaction> showAllTransaction()
	{
		return transactionService.showAllTransaction();
	}

}




