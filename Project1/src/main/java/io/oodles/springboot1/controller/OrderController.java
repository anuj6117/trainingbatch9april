package io.oodles.springboot1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.oodles.springboot1.model.BuyOrder;
import io.oodles.springboot1.model.UserOrder;
import io.oodles.springboot1.service.OrderService;

@RestController
public class OrderController {
	
	@Autowired
	OrderService orderService;
	
	@PostMapping("/createbuyorder")
	public String buyorder(@RequestBody BuyOrder buyOrder) {
		return orderService.buy(buyOrder);
	}
	
	@PostMapping("/createsellorder")
	public String sellorder(@RequestBody BuyOrder buyOrder) {
		return orderService.sell(buyOrder);
	}
	
	@GetMapping("/getorderbyuserid/{id}")
	public UserOrder getorder(@PathVariable Integer id) {
		return orderService.get(id);
	}
	
	@GetMapping("/transaction")
	public String maketransaction() {
		return orderService.transaction();
	}

}
