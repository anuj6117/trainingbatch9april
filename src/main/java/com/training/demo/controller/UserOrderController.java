package com.training.demo.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.training.demo.dto.UserOrderDto;
import com.training.demo.enums.OrderType;
import com.training.demo.model.User;
import com.training.demo.model.UserOrder;
import com.training.demo.repository.UserOrderRepository;
import com.training.demo.repository.UserRepository;
import com.training.demo.service.UserOrderService;

@RestController
public class UserOrderController {
	@Autowired
	private UserOrderService userOrderService;
	@Autowired
	private UserOrderRepository userorderrepo;
	@Autowired
	 private UserRepository userrepo;
	
	
	
	@RequestMapping(value = "/createbuyorder", method = RequestMethod.POST)
	public String createBuyOrder(@RequestBody UserOrderDto userOrderDto) { 
		return userOrderService.createBuyOrder(userOrderDto);
	}
	
	
	@RequestMapping(value = "/createsellorder", method = RequestMethod.POST)
	public String createSellOrder(@RequestBody UserOrderDto userOrderDto) { 
		return userOrderService.createSellOrder(userOrderDto);
	}
	
	@RequestMapping(value = "/showallorders")
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
	
	
	@RequestMapping(value="/getorderbyuserid", method=RequestMethod.GET)
	public Set<UserOrder> getorderByuserId(@RequestParam("userId") Integer userId)
	{
		User user=userrepo.findByUserId(userId);
		if(user!=null)
		{
		
		System.out.println("user"+user.getUserId());
		
		Set<UserOrder> setOrder=user.getUserOrder();
		System.out.println("setorder" +setOrder);
		return setOrder;
		}
		else
		{
			return null;
		}
		
		
	}	
	
	
		

	
	
	

	
}


