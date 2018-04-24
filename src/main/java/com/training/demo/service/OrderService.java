package com.training.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import com.training.demo.dto.OrderDto;
import com.training.demo.model.CoinManagement;
import com.training.demo.model.User;
import com.training.demo.repository.CoinManagementRepository;
import com.training.demo.repository.UserRepository;

public class OrderService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	CoinManagementRepository coinManagementRepository;
	
	public String createBuyOrder(OrderDto orderDto){
		String coinName = orderDto.getCoinName();
		Integer coinQuantity = orderDto.getCoinQuantity();
		Double price = orderDto.getPrice();
		Integer userId = orderDto.getUserId();
		User user = userRepository.findByUserId(userId);
		CoinManagement coinManagement = coinManagementRepository.findOneByCoinName(coinName);
		coinManagement.setCoinQuantity(coinQuantity);
		coinManagement.setCoinName(coinName);
		coinManagement.setPrice(price);
		return "Buy Order created successfully....";
	}
	
	public String createSellOrder(OrderDto orderDto) {
		
		return "Sell Order created successfully.....";
	}
	
}