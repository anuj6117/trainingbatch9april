package com.trainingproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trainingproject.domain.UserOrder;
import com.trainingproject.dto.BuySellBean;
import com.trainingproject.service.UserOrderService;

@RestController
public class UserOrderController {

	@Autowired
	UserOrderService userorderservice;
	
	@RequestMapping(value="createbuyorder",method=RequestMethod.POST)
	public String createBuyOrder(@RequestBody BuySellBean bsb) {
		return userorderservice.createBuyOrder(bsb);
	}
	
	@RequestMapping(value="/createsellorder",method=RequestMethod.POST)
	public String createSellOrder(@RequestBody BuySellBean bsb) {
		return userorderservice.createSellOrder(bsb);
		
	}
	@RequestMapping(value="/getuserorderbyorderid",method=RequestMethod.GET)
	public UserOrder getUserOrderById(@RequestParam Integer orderId) {
		return userorderservice.getUserOrderById(orderId);
	}
//	@RequestMapping(value="getorderbyuserid",method=RequestMethod.GET)
//	public List<UserOrder> getAllOrderByUserId(@RequestParam Integer userId) {
//		
//		 return  userorderservice.getAllOrdersByUserId(userId);
//	}
}
