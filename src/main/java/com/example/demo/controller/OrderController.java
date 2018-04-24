package com.example.demo.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.dto.UserOrderDTO;
import com.example.demo.model.OrderDetails;
import com.example.demo.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@RequestMapping(value="/createbuyorder", method=RequestMethod.POST)
	public String createBuyOrder(@RequestBody UserOrderDTO userOrderDto) {
		return orderService.createBuyOrder(userOrderDto);
	}
	
	@RequestMapping(value="/createsellorder", method=RequestMethod.POST)
	public String createSellOrder(@RequestBody UserOrderDTO userOrderDto) {
		return orderService.createSellOrder(userOrderDto);
	}
	
	@RequestMapping(value="/getorderbyuserid", method=RequestMethod.GET)
	public List<OrderDetails> getOrderByUserId(@RequestParam("id")Integer id) {
		return orderService.getOrderByUserId(id);
	}
	
	@RequestMapping(value="/showallorder", method=RequestMethod.GET)
	public List<OrderDetails> showAllOrder() {
		return orderService.showAllOrder();
	}

}
