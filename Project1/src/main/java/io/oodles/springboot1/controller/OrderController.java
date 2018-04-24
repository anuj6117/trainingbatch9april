package io.oodles.springboot1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import io.oodles.springboot1.model.BuyOrder;
import io.oodles.springboot1.model.UserOrder;
import io.oodles.springboot1.service.OrderService;

@RestController
public class OrderController {
	
	@Autowired
	OrderService orderService;
	
	@PostMapping("/createbuyorder")
	public UserOrder buyorder(BuyOrder buyOrder) {
		return orderService.buy(buyOrder);
	}

}
