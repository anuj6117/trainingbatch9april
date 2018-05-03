package com.example.demo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.DepositAmountApprovalDTO;
import com.example.demo.dto.OrderDTO;

import com.example.demo.service.OrderService;
import com.example.demo.utility.ResponseHandler;

@RestController
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@RequestMapping(value = "/depositApproval", method = RequestMethod.POST )
	public ResponseEntity<Object> depositApprove(@RequestBody DepositAmountApprovalDTO depositAmountApprovalDTO)
	{
		Map<String, Object> result=null;
		try
		{
			result=orderService.approveDeposit(depositAmountApprovalDTO);
			if (result.get("isSuccess").equals(true))
			{
				return ResponseHandler.generateResponse(HttpStatus.OK, true, result.get("message").toString(), result);
			} else
			{
				return ResponseHandler.generateResponse(HttpStatus.OK, false, result.get("message").toString(), result);
			}

		} 
		catch (Exception e) 
		{
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), result);
		}
		
	}
	
	@RequestMapping(value="/createbuyorder", method = RequestMethod.POST)
	public String buyOrder(@RequestBody OrderDTO orderDTO)
	{
		return orderService.buyOrder(orderDTO);			
	}
	
	@RequestMapping(value="/createsellorder", method = RequestMethod.POST)
	public String sellOrder(@RequestBody OrderDTO orderDTO)
	{
		/*return "hello";*/
			return orderService.sellOrder(orderDTO);
			
	}
}
