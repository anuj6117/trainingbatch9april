package com.trading.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.trading.domain.UserOrder;
import com.trading.services.OrderService;

@RestController
public class OrderController {
	
	
		
		@Autowired
		private	OrderService orderService;
			
			@RequestMapping(value = "/createbuyorder", method = RequestMethod.POST)
			public String buyOrder(@RequestBody UserOrder userOrder) throws Exception
			{
				return orderService.createBuyOrder(userOrder);		
				}
		
			@RequestMapping(value = "/createsellorder", method = RequestMethod.POST)
			public String sellOrder(@RequestBody UserOrder userOrder) throws Exception
			{
				return orderService.createSellOrder(userOrder);		
				}
		
			
			
	/*		@RequestMapping(value = "/getorderbyuserid", method = RequestMethod.GET)
			public String getOrder(@RequestBody (UserI)) throws Exception
			{
				return orderService.getOrderByUserId(userOrder);		
				}
			*/
	}



