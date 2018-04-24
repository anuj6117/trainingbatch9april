package com.example.service;

import org.springframework.stereotype.Service;

import com.example.dto.UserOrderDto;
import com.example.model.Order;

@Service
public class OrderService
{
	private Order order;
 public String createBuyOrder(UserOrderDto userOrderDto)
 {
    try
    {
	 order.setCoinName(userOrderDto.getCoinName());
	 order.setPrice(userOrderDto.getPrice());
	 order.setCoinQuantity(userOrderDto.getCoinQuantity());
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
	 order.setCoinName(userOrderDto.getCoinName());
	 order.setPrice(userOrderDto.getPrice());
	 order.setCoinQuantity(userOrderDto.getCoinQuantity());
	 return "Details in order table is inserted";
    }
    catch(NullPointerException e)
    {
      return "value null";    	
    }

 }
}
