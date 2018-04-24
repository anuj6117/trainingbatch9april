package com.example.demo.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.UserOrderDTO;
import com.example.demo.enums.OrderEnum;
import com.example.demo.enums.OrderStatusEnum;
import com.example.demo.model.User;
import com.example.demo.model.UserOrder;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.UserRepository;

@Service
public class OrderService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OrderRepository orderRepository;

	public Map<String, Object> buyOrder(UserOrderDTO userOrderDTO) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		User user=userRepository.findByUserId(userOrderDTO.getUserId());
		if(user != null)
		{
			UserOrder userOrder=new UserOrder();
			userOrder.setUser(user);
			userOrder.setCoinName(userOrderDTO.getCoinName());
			userOrder.setOrderStatusEnum(OrderStatusEnum.PENDING);
			userOrder.setOrderType(OrderEnum.BUYER);
			userOrder.setCoinQuantity(userOrderDTO.getCoinQuantity());
			userOrder.setPrice(userOrderDTO.getPrice());
			userOrder.setDateCreated(new Date());
			orderRepository.save(userOrder);
			
			result.put("isSuccess", true);
			result.put("message", "SUCCESS");
			return result;	
		}
		else
		{
			result.put("isSuccess", false);
			result.put("message", "User Already Exists.");
			return result;
		}	
	}
}
