package com.training.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.training.demo.dto.OrderApprovalDto;
import com.training.demo.dto.SellBuyTransactionDto;
import com.training.demo.service.OrderService;

@RestController
public class OrderController {

	@Autowired
	private OrderService orderService;
		
	@RequestMapping(value="/createbuyorder", method = RequestMethod.POST)
	public String createBuyOrder(@RequestBody SellBuyTransactionDto sellBuyTransactionDto){
		System.out.println("price = "+sellBuyTransactionDto.getPrice()+"/t"+"userId = "+sellBuyTransactionDto.getUserId()+"/t coinName = "+sellBuyTransactionDto.getCoinName()+"/t coinQuantity = "+sellBuyTransactionDto.getCoinQuantity());
		System.out.println("IIIIIIIIIIIIIIIIIIIIIIIIIInnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn");
		return orderService.createBuyOrder(sellBuyTransactionDto);
		
	}
	
	@RequestMapping(value="/createsellorder", method = RequestMethod.POST)
	public String createSellOrder(@RequestBody SellBuyTransactionDto sellBuyTransactionDto) {
		if(sellBuyTransactionDto != null)
		{
		return orderService.createSellOrder(sellBuyTransactionDto);
		}
		else
		{
			throw new NullPointerException("Insufficient information or null.");
		}
	}
	
	@RequestMapping(value="/approveorder", method = RequestMethod.POST)
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
	
	@RequestMapping(value="/showallorder", method=RequestMethod.GET)
	public Object showAllOrder()
	{
		return orderService.showAllOrder();
	}
	
	@RequestMapping(value="/getorderbyuserid", method=RequestMethod.GET)
	public Object getUserOrderById(@RequestParam("userId") Integer userId)
	{
		return orderService.getUserOrderById(userId);
	}

}