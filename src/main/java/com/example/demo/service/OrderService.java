package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.UserOrderDTO;
import com.example.demo.enums.OrderType;
import com.example.demo.model.OrderDetails;
import com.example.demo.model.User;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.UserRepository;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	private User user;
	private OrderDetails orderDetails;

	public String createBuyOrder(UserOrderDTO userOrderDto) {		
		if((user=userRepository.findOneById(userOrderDto.getUserId()))==null)
			return "invalid user";
		else {			
			orderDetails = new OrderDetails(userOrderDto);
			orderDetails.setUser(user);
			orderDetails.setOrderType(OrderType.BUYER);
			return orderRepository.save(orderDetails)!=null?"success":"failed";
		}			
	}

	public String createSellOrder(UserOrderDTO userOrderDto) {
		if((user = userRepository.findOneById(userOrderDto.getUserId()))==null)
			return "invalid user";
		orderDetails = new OrderDetails(userOrderDto);
		orderDetails.setUser(user);
		orderDetails.setOrderType(OrderType.SELLER);
		return orderRepository.save(orderDetails)!=null?"success":"failed";
	}

	public List<OrderDetails> getOrderByUserId(Integer id) {
		return orderRepository.findAllByUserId(id);
	}

	public List<OrderDetails> showAllOrder() {
		return orderRepository.findAll();
	}
	
	

}
