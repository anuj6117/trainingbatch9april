package com.example.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.UserOrderDto;
import com.example.model.User;
import com.example.model.UserOrder;
import com.example.repository.UserRepository;
import com.example.service.OrderService;

@RestController
public class OrderController
{
 private User user;
 private UserOrder userorder;
 @Autowired
 private UserRepository userRepository;
 @Autowired
 private OrderService orderService;
	
   @RequestMapping(value="/createbuyorder",method=RequestMethod.POST)
	public String createBuyOrder(@RequestBody UserOrderDto userOrderDto )
	{
		user=userRepository.findByUserId(userOrderDto.getUserId());
		if(user!=null)
		{
		  userorder.setOrderId(userOrderDto.getUserId());
		  orderService.createBuyOrder(userOrderDto);	
		}
		return "";
	}
   
   
   @RequestMapping(value="/createSellorder",method=RequestMethod.POST)
	public String createSellOrder(@RequestBody UserOrderDto userOrderDto )
	{
		user=userRepository.findByUserId(userOrderDto.getUserId());
		if(user!=null)
		{
		  userorder.setOrderId(userOrderDto.getUserId());
		  orderService.createSellOrder(userOrderDto);	
		}
		return "user is null..........";
	}
}
