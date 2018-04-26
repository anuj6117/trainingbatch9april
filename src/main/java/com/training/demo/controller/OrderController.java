package com.training.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.training.demo.dto.OrderApprovalDto;
import com.training.demo.model.OrderTable;
import com.training.demo.service.OrderService;

@RestController
public class OrderController {

	@Autowired
	private OrderService orderService;
		
	@RequestMapping(value="/createbuyorder", method = RequestMethod.POST)
	public String createBuyOrder(@RequestBody OrderTable orderTable){
		if(orderTable != null) 
		{
		return orderService.createBuyOrder(orderTable);
		}
		else
		{
			throw new NullPointerException("Insufficient information or null.");
		}
	}
	
	@RequestMapping(value="createSellOrder", method = RequestMethod.POST)
	public String createSellOrder(@RequestBody OrderTable orderTable) {
		if(orderTable != null)
		{
		return orderService.createSellOrder(orderTable);
		}
		else
		{
			throw new NullPointerException("Insufficient information or null.");
		}
	}
	
	@RequestMapping(value="/approveOrder", method = RequestMethod.POST)
	public String approveOrder(@RequestBody OrderApprovalDto orderApprovalDto)
	{
		if(orderApprovalDto != null)
		{
		return 	orderService.approveOrder(orderApprovalDto);
		}
		else
		{
			throw new NullPointerException("Insufficient order approval information.");
		}
	}
}
