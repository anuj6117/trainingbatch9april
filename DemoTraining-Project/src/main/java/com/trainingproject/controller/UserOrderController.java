package com.trainingproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trainingproject.domain.UserOrder;
import com.trainingproject.dto.UserOrderBuySellDto;
import com.trainingproject.enums.OrderType;
import com.trainingproject.service.UserOrderService;

@RestController
public class UserOrderController {
	@Autowired
	private UserOrderService userOrderService;
	
	
	
	@RequestMapping(value = "/createbuyorder", method = RequestMethod.POST)
	public String createBuyOrder(@RequestBody UserOrderBuySellDto userOrderBuySellDto) { 
		return userOrderService.createBuyOrder(userOrderBuySellDto);
	}
	
	
	@RequestMapping(value = "/createsellorder", method = RequestMethod.POST)
	public String createSellOrder(@RequestBody UserOrderBuySellDto userOrderBuySellDto) { 
		return userOrderService.createSellOrder(userOrderBuySellDto);
	}
	
	@RequestMapping(value = "/showallorder")
	public List<UserOrder> getAllOrders() {
		return userOrderService.getAllOrders();
	}

	@RequestMapping(value = "/getallbuyingorders")
	public List<UserOrder> getAllBuyingOrders(OrderType orderType) {
		return userOrderService.getAllBuyingOrders(orderType);
	}
	
	@RequestMapping(value = "/getallsellingorders")
	public List<UserOrder> getAllSellingOrders(OrderType orderType) {
		return userOrderService.getAllSellingOrders(orderType);
	}
		
	@RequestMapping(value = "/getallavailableorders")
	public List<UserOrder> getAllAvailableOrders() {
		return userOrderService.getAllAvailableOrders();	
	}
	
	@RequestMapping(value = "/getorderbyuserid", method = RequestMethod.GET)
	public Object getOrderByUserId(@RequestParam Integer userId) { 
		try {
		return userOrderService.getOrderByUserId(userId);
		} catch(Exception e) {
			return "OrderByUserId can't be display as :"+e.getMessage();
		}
	}
	
}
