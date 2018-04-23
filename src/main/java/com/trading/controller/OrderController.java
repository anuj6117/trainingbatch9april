package com.trading.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trading.domain.UserOrder;
import com.trading.dto.UserOrderDto;
import com.trading.services.OrderService;

@RestController
public class OrderController {
	
	
		
		@Autowired
		private	OrderService orderService;
			
			@RequestMapping(value = "/createbuyorder", method = RequestMethod.POST)
			public String buyOrder(@RequestBody UserOrderDto userOrderDto) throws Exception
			{
				return orderService.createBuyOrder(userOrderDto);		
				}
		
			@RequestMapping(value = "/createsellorder", method = RequestMethod.POST)
			public String sellOrder(@RequestBody UserOrderDto userOrderDto) throws Exception
			{
				return orderService.createSellOrder(userOrderDto);		
				}
			
		
			
			
		@RequestMapping(value = "/getorderbyuserid", method = RequestMethod.GET)
			public UserOrder getOrder(@RequestParam("userId") long userId) throws Exception
			{
				return orderService.getOrderByUserId(userId);		
				}
			
	}



