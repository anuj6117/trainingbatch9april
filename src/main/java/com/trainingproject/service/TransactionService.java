package com.trainingproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trainingproject.repository.UserOrderRepository;

@Service
public class TransactionService {

	@Autowired
	UserOrderRepository userorderRepository;
	
	public String approveTransaction() {
	
		
		return "success";
	}

}
