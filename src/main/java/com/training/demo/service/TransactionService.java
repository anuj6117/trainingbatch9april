package com.training.demo.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.training.demo.repository.OrderRepository;
import com.training.demo.repository.UserRepository;

public class TransactionService {
	
	@Autowired 
	OrderRepository orderRepository;
	
	@Autowired
	UserRepository userRepository;
	

}
