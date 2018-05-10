package com.trainingproject.controller;

import java.util.List;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
import com.trainingproject.utils.ResponseHandler;

@RestController
public class TransactionController {
	@Autowired
	private TransactionService transactionService;

	@RequestMapping(value = "/gettransaction", method = RequestMethod.GET)
	public String startTransaction () {
		

		transactionService.startTransaction();
		
		return "transaction success";
	}
	/*public ResponseEntity<Object> transaction() {
		Transaction result = null;
		try {

			result = transactionService.getTransaction();
		} catch (Exception e) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), result);
		}
		return ResponseHandler.generateResponse(HttpStatus.OK, true, "success", result);

	}

	/*@GetMapping(value = "showalltransaction")
	public ResponseEntity<Object> getalltrancation() {
		List<Transaction> result = null;
		try {
			result = transactionService.getAllTransaction();
		} catch (Exception e) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), result);
		}
		return ResponseHandler.generateResponse(HttpStatus.OK, true, "success", result);
	}*/
	@RequestMapping(value = "/getalltransaction", method = RequestMethod.GET)
	public List<Transaction> getAllTransaction() {
		return transactionService.getAllTransaction();
	}

}
