package com.trading.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trading.Enum.OrderStatus;
import com.trading.Enum.OrderType;
import com.trading.domain.UserOrder;
import com.trading.repository.OrderRepository;

@Service
public class OrderService {
	
@Autowired
private OrderRepository orderRepository;



public String createBuyOrder(UserOrder userOrder)
{ 
	if( userOrder != null)
	{
	userOrder.setOrderCreatedOn(new Date());
    userOrder.setStatus(OrderStatus.PENDING);
	userOrder.setOrderType(OrderType.BUYER);
	orderRepository.save(userOrder);
	return "Succesfully created buy Order";
	}
	else
	{
		return " Failed to create buy Order";
	}
}

public String createSellOrder(UserOrder userOrder)
{
	if(userOrder != null)
	{
		
	userOrder.setOrderCreatedOn(new Date());
    userOrder.setStatus(OrderStatus.PENDING);
	userOrder.setOrderType(OrderType.SELLER);
	orderRepository.save(userOrder);
	return "Succesfully created sell order";
	}
	else
	{
		return " Failed to create sell order";
	}
}
}