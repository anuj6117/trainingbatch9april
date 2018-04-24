package io.oodles.springboot1.service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.oodles.springboot1.enums.OrderStatus;
import io.oodles.springboot1.enums.OrderType;
import io.oodles.springboot1.model.BuyOrder;
import io.oodles.springboot1.model.UserOrder;
import io.oodles.springboot1.model.Users;
import io.oodles.springboot1.repository.OrderRepository;
import io.oodles.springboot1.repository.UsersRepository;
@Service
public class OrderService {
	Users user=new Users();
	Date date=new Date();
	
	@Autowired
	UsersRepository usersRepository;
	
	@Autowired
	OrderRepository orderRepository;
	public UserOrder buy(BuyOrder buyOrder) {
		// TODO Auto-generated method stub
		UserOrder userOrder=new UserOrder();
		
		user=usersRepository.findByUserid(buyOrder.getUserid());
		Set<UserOrder> orderset=new HashSet<UserOrder>();
		
		userOrder.setCoinName(buyOrder.getCoinName());
		userOrder.getCoinName();
		userOrder.setPrice(buyOrder.getPrice());
		userOrder.getPrice();
		userOrder.setOrderCreatedOn(date);
		userOrder.getOrderCreatedOn();
		userOrder.setOrderStatus(OrderStatus.PENDING);
		userOrder.getOrderStatus();
		userOrder.setOrdertype(OrderType.BUY);
		userOrder.getOrdertype();
		
		//orderset.add(userOrder);
		return orderRepository.save(userOrder);
		
	}

}
