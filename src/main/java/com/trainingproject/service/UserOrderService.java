package com.trainingproject.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trainingproject.domain.User;
import com.trainingproject.domain.UserOrder;
import com.trainingproject.dto.UserOrderBuySellDto;
import com.trainingproject.enums.OrderType;
import com.trainingproject.enums.Status;
import com.trainingproject.repository.UserOrderRepository;
import com.trainingproject.repository.UserRepository;

@Service
public class UserOrderService {
	@Autowired
	private UserOrderRepository userOrderRepository;
	@Autowired
	private UserRepository userRepository;

	
	public List<UserOrder> getAllOrders() {

		return userOrderRepository.findAll();		
	}

	public List<UserOrder> getAllBuyingOrders(OrderType orderType) {
		// TODO Auto-generated method stub
		return userOrderRepository.findAll();
	}
	
	public List<UserOrder> getAllSellingOrders(OrderType orderType) {
		// TODO Auto-generated method stub
		return userOrderRepository.findAll();
	}

	public List<UserOrder> getAllAvailableOrders() {
		// TODO Auto-generated method stub
		return null;
	}

	public String createBuyOrder(UserOrderBuySellDto userOrderBuySellDto) {
		// TODO Auto-generated method stub
		    User user = userRepository.findByUserId(userOrderBuySellDto.getUserId());
			UserOrder  userOrder = new UserOrder();
			userOrder.setOrderType(OrderType.buyer);
			userOrder.setUser(user);
			userOrder.setCoinQuantity(userOrderBuySellDto.getCoinQuantity());
			userOrder.setPrice(userOrderBuySellDto.getPrice());
			userOrder.setCoinName(userOrderBuySellDto.getCoinName());
			userOrder.setStatus(Status.pending);
			userOrder.setOrderCreatedOn(new Date());
			userOrderRepository.save(userOrder);
		return "success";
	}

	public String createSellOrder(UserOrderBuySellDto userOrderBuySellDto) {
		// TODO Auto-generated method stub
		User user = userRepository.findByUserId(userOrderBuySellDto.getUserId());
		UserOrder  userOrder = new UserOrder();
		userOrder.setOrderType(OrderType.seller);
		userOrder.setUser(user);
		userOrder.setCoinQuantity(userOrderBuySellDto.getCoinQuantity());
		userOrder.setPrice(userOrderBuySellDto.getPrice());
		userOrder.setCoinName(userOrderBuySellDto.getCoinName());
		userOrder.setStatus(Status.pending);
		userOrder.setOrderCreatedOn(new Date());
		userOrderRepository.save(userOrder);
		return "success";
	}

	public String getOrderByUserId(Integer userId) {
		// TODO Auto-generated method stub
		return null;
	}
}
