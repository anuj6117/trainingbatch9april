package com.trainingproject.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trainingproject.domain.Currency;
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
	Currency currency;
	Long netAmount;
	Long grossAmount;
	
	
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
			//Currency currency = new Currency();
		User user = userRepository.findByUserId(userOrderBuySellDto.getUserId());
		UserOrder  userOrder = new UserOrder();
		userOrder.setOrderType(OrderType.Buyer);
		userOrder.setCoinName(userOrderBuySellDto.getCoinName());
		userOrder.setCoinQuantity(userOrderBuySellDto.getCoinQuantity());
		userOrder.setFee(currency.getFees());
		userOrder.setPrice(userOrderBuySellDto.getPrice());
		netAmount = (userOrderBuySellDto.getCoinQuantity() * userOrderBuySellDto.getPrice());
		userOrder.setNetAmount(netAmount);
		grossAmount = netAmount + (((userOrderBuySellDto.getCoinQuantity() * 
				userOrderBuySellDto.getPrice())*currency.getFees())/100);
		userOrder.setGrossAmount(grossAmount);
		userOrder.setOrderCreatedOn(new Date());
		userOrder.setStatus(Status.PENDING);
		userOrder.setUser(user);
		userOrderRepository.save(userOrder);
		return "success";
	}

	public String createSellOrder(UserOrderBuySellDto userOrderBuySellDto) {
		// TODO Auto-generated method stub
		User user = userRepository.findByUserId(userOrderBuySellDto.getUserId());
		UserOrder  userOrder = new UserOrder();
		userOrder.setOrderType(OrderType.Buyer);
		userOrder.setCoinName(userOrderBuySellDto.getCoinName());
		userOrder.setCoinQuantity(userOrderBuySellDto.getCoinQuantity());
		userOrder.setPrice(userOrderBuySellDto.getPrice());
		Long netAmount = (userOrderBuySellDto.getCoinQuantity() * userOrderBuySellDto.getPrice());
		userOrder.setNetAmount(netAmount);
		userOrder.setGrossAmount(netAmount);
		userOrder.setOrderCreatedOn(new Date());
		userOrder.setStatus(Status.PENDING);
		userOrder.setUser(user);
		userOrderRepository.save(userOrder);
		return "success";
	}

	public String getOrderByUserId(Integer userId) {
		// TODO Auto-generated method stub
		return null;
	}
}
