package com.training.demo.service;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.training.demo.dto.UserOrderDto;
import com.training.demo.enums.OrderType;
import com.training.demo.enums.UserOrderStatus;
import com.training.demo.enums.WalletType;
import com.training.demo.model.CoinManagement;
import com.training.demo.model.User;
import com.training.demo.model.UserOrder;
import com.training.demo.model.Wallet;
import com.training.demo.repository.CoinManagementRepository;
import com.training.demo.repository.UserOrderRepository;
import com.training.demo.repository.UserRepository;
import com.training.demo.repository.WalletRepository;

import ch.qos.logback.core.status.Status;

@Service
public class UserOrderService {
	@Autowired
	private UserOrderRepository userOrderRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CoinManagementRepository currencyRepository;
	@Autowired
	private WalletRepository walletRepository;
	
	User user;
	//@Autowired
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
	    try {
	    currency = currencyRepository.findOneByCoinName(userOrderDto.getCoinName());
	    }
	    catch(Exception e) {
	    	e.printStackTrace();
	    	return "currency not exist";
	    	
	    }
	  // int  fees=currency.getFee();
	  // long cal=userOrderDto.getCoinQuantity()* userOrderDto.getPrice();
	    //long calc=(cal*fees)/100;
	    //long gross=calc+cal;
	    //Wallet wallet=walletRepository.findByUser(user,WalletType.FIAT);
	    
	    //double l=wallet.getShadowBalance();
	    //if(l>=gross) {
	    
	    UserOrder  userOrder = new UserOrder();
	    if(user != null) {
		userOrder.setOrderType(OrderType.BUYER);
		userOrder.setCoinName(userOrderDto.getCoinName());
		userOrder.setCoinType(userOrderDto.getCoinType());
		userOrder.setCoinQuantity(userOrderDto.getCoinQuantity());
		System.out.println("++++++++++++++++++++++++++++++++++++"+currency.getFee());
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
	
	    
	    else {
	    	return "failed";
	}
	}
	  //  }
	  //  else
	   // {
	    	//return"insufficient balence";
	   // }
	
	    

	public String createSellOrder(UserOrderDto userOrderDto) {
		// TODO Auto-generated method stub
		user = userRepository.findByUserId(userOrderDto.getUserId());
		UserOrder  userOrder = new UserOrder();
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



