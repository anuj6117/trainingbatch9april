package com.training.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.training.demo.dto.OrderDto;
import com.training.demo.service.OrderService;

@RestController
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@RequestMapping(value="/createbuyorder", method = RequestMethod.POST)
	public String createBuyOrder(OrderDto orderDto){
		return orderService.createBuyOrder(orderDto);
	}
	
	@RequestMapping(value="createSellOrder", method = RequestMethod.POST)
	public String createSellOrder(OrderDto orderDto) {
		return orderService.createSellOrder(orderDto);
	}

}
