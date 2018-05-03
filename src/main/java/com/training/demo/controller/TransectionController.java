package com.training.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.training.demo.model.Transection;
import com.training.demo.model.User;
import com.training.demo.service.TransectionService;

@RestController
public class TransectionController {

	@Autowired
	TransectionService transactionService;

	@RequestMapping(value = "/transaction", method = RequestMethod.GET)
	// public String createBuyOrder(@RequestBody UserOrderDto userOrderDto
	public String transaction() {
		return transactionService.approveTransaction();
	}
		
		
		@RequestMapping  (value="/getalltransaction",method=RequestMethod.GET)
		
		public List<Transection> getAllTransection() {
		
			return transactionService.getalltransaction();
		}
		

	}

