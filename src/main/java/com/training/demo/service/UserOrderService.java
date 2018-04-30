package com.training.demo.service;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.training.demo.dto.UserOrderDto;
import com.training.demo.enums.OrderType;
import com.training.demo.enums.UserOrderStatus;
import com.training.demo.model.CoinManagement;
import com.training.demo.model.User;
import com.training.demo.model.UserOrder;
import com.training.demo.repository.CoinManagementRepository;
import com.training.demo.repository.UserOrderRepository;
import com.training.demo.repository.UserRepository;


import ch.qos.logback.core.status.Status;

@Service
public class UserOrderService {
	@Autowired
	private UserOrderRepository userOrderRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CoinManagementRepository currencyRepository;
	
	User user;
	CoinManagement currency;
	Long netAmount;
	Long grossAmount;
	List<UserOrder> userOrderList = new ArrayList<>();
	
	
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

	public String createBuyOrder(UserOrderDto userOrderDto) {
		// TODO Auto-generated method stub
	    user = userRepository.findByUserId(userOrderDto.getUserId());
	    currency = currencyRepository.findByCoinName(userOrderDto.getCoinName());
	    UserOrder  userOrder = new UserOrder();
	    if(user != null) {
		userOrder.setOrderType(OrderType.BUYER);
		userOrder.setCoinName(userOrderDto.getCoinName());
		userOrder.setCoinType(userOrderDto.getCoinType());
		userOrder.setCoinQuantity(userOrderDto.getCoinQuantity());
		userOrder.setFee(currency.getFee());
		userOrder.setPrice(userOrderDto.getPrice());
		netAmount = (userOrderDto.getCoinQuantity() * userOrderDto.getPrice());
		userOrder.setNetAmmount(netAmount);
		grossAmount = netAmount + (((userOrderDto.getCoinQuantity() * 
				userOrderDto.getPrice())*currency.getFee())/100);
		userOrder.setGrossAmmount(grossAmount);
		userOrder.setOrderCreatedOn(new Date());
		userOrder.setStatus(UserOrderStatus.PENDING);
		userOrder.setUser(user);
		userOrderRepository.save(userOrder);
		return "success";
	    }
	    else 
	    	return "failed";
	}
	    

	public String createSellOrder(UserOrderDto userOrderDto) {
		// TODO Auto-generated method stub
		user = userRepository.findByUserId(userOrderDto.getUserId());
		com.training.demo.model.UserOrder  userOrder = new UserOrder();
		if(user != null) {
		userOrder.setOrderType(OrderType.SELLER);
		userOrder.setCoinName(userOrderDto.getCoinName());
		userOrder.setCoinType(userOrderDto.getCoinType());
		userOrder.setCoinQuantity(userOrderDto.getCoinQuantity());
		userOrder.setPrice(userOrderDto.getPrice());
		Long netAmount = (userOrderDto.getCoinQuantity() * userOrderDto.getPrice());
		userOrder.setNetAmmount(netAmount);
		userOrder.setGrossAmmount(netAmount);
		userOrder.setOrderCreatedOn(new Date());
		userOrder.setStatus(UserOrderStatus.PENDING);
		userOrder.setUser(user);
		userOrderRepository.save(userOrder);
		return "success";
		}
		else
			return "failed";
	}

	public String getOrderByUserId(Integer userId) {
		// TODO Auto-generated method stub
		return null;
	}
}



