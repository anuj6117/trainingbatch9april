package com.example.service;

import org.springframework.stereotype.Service;

import com.example.dto.UserOrderDto;
import com.example.model.UserOrder;

@Service
public class OrderService
{
	private UserOrder userorder;
 public String createBuyOrder(UserOrderDto userOrderDto)
 {
    try
    {
	 userorder.setCoinName(userOrderDto.getCoinName());
	 userorder.setPrice(userOrderDto.getPrice());
	 userorder.setCoinQuantity(userOrderDto.getCoinQuantity());
	 return "Details in order table is inserted";
    }
    catch(NullPointerException e)
    {
      return "value null";    	
    }

 }
 
 public String createSellOrder(UserOrderDto userOrderDto)
 {
    try
    {
	 userorder.setCoinName(userOrderDto.getCoinName());
	 userorder.setPrice(userOrderDto.getPrice());
	 userorder.setCoinQuantity(userOrderDto.getCoinQuantity());
	 return "Details in order table is inserted";
    }
    catch(NullPointerException e)
    {
      return "value null";    	
    }

 }
}
