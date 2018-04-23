package com.trading.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trading.Enum.OrderStatus;
import com.trading.Enum.OrderType;
import com.trading.domain.User;
import com.trading.domain.UserOrder;
import com.trading.dto.UserOrderDto;
import com.trading.repository.OrderRepository;
import com.trading.repository.UserRepository;

@Service
public class OrderService {
	
@Autowired
private OrderRepository orderRepository;

@Autowired
private UserRepository userRepository;


	public String createBuyOrder(UserOrderDto userOrderDto)
	{ 
		User user = userRepository.findOneByUserId(userOrderDto.getUserId());
	
		if( user != null && userOrderDto != null)
			{
				UserOrder userOrder = new UserOrder();
				userOrder.setOrderCreatedOn(new Date());
				userOrder.setStatus(OrderStatus.PENDING);
				userOrder.setOrderType(OrderType.BUYER);
				userOrder.setUser(user);
				userOrder.setCoinQuantity(userOrderDto.getCoinQuantity());
				userOrder.setCoinName(userOrderDto.getCoinName());
				userOrder.setPrice(userOrderDto.getPrice());
				orderRepository.save(userOrder);
				return "Succesfully created buy Order";
			}
		else
		{
			return " Failed to create buy Order";
		}
	}

	public String createSellOrder(UserOrderDto userOrderDto)
	{
		User user = userRepository.findOneByUserId(userOrderDto.getUserId());
			if(user != null && userOrderDto != null)
				{		
					UserOrder userOrder = new UserOrder();
					userOrder.setOrderCreatedOn(new Date());
					userOrder.setStatus(OrderStatus.PENDING);
					userOrder.setOrderType(OrderType.SELLER);
					userOrder.setUser(user);
					userOrder.setCoinQuantity(userOrderDto.getCoinQuantity());
					userOrder.setCoinName(userOrderDto.getCoinName());
					userOrder.setPrice(userOrderDto.getPrice());
					orderRepository.save(userOrder);
					return "Succesfully created sell order";
				}
			else
				{
					return " Failed to create sell order";
				}
	}


	public UserOrder getOrderByUserId(long userId)
	{
		User user = userRepository.findOneByUserId(userId);
		return orderRepository.findOneByUser(user);
	}
}