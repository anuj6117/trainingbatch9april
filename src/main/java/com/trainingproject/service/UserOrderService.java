package com.trainingproject.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trainingproject.domain.User;
import com.trainingproject.domain.UserOrder;
import com.trainingproject.domain.Wallet;
import com.trainingproject.dto.BuySellBean;
import com.trainingproject.enums.CoinType;
import com.trainingproject.enums.OrderType;
import com.trainingproject.enums.UserOrderStatus;
import com.trainingproject.enums.UserStatus;
import com.trainingproject.repository.CurrencyRepository;
import com.trainingproject.repository.UserOrderRepository;
import com.trainingproject.repository.WalletRepository;

@Service
public class UserOrderService {

	
	@Autowired
	UserOrderRepository userorderRepository;
	
	@Autowired
	UserService userservice;
	
	@Autowired
	WalletRepository walletRepository;
	
	@Autowired
	CurrencyRepository currencyRepository;
	
	public String createBuyOrder(BuySellBean bsb) {
		
		User user =userservice.getUserById(bsb.getUserId()).get();
		if(user==null)
			return "user is null";
		
		if(user.getStatus()==null)
			return "user is inactive";
		if(user.getStatus().equals(UserStatus.INACTIVE))
			return "user is inactive";
		if(bsb.getUserId()==null)
      return "userId cannot be null";
		
		if(bsb.getCoinName()==null||bsb.getCoinName().length()==0)
		      return "coin name cannot be null";
		
		if(bsb.getCoinQuantity()==null||bsb.getCoinQuantity()==0)
		      return "coin quantity cannot be null";
		
		if(bsb.getPrice()==null||bsb.getPrice()==0)
		      return "price cannot be null";
		 if(bsb.getCoinQuantity()==0)
	    	 return "0 quantity to buy";
		
		Integer totamount=bsb.getCoinQuantity()*bsb.getPrice();
		long fee=currencyRepository.findBycoinName(bsb.getCoinName()).getFees();
		long gross=(totamount*fee)/100+totamount;
		Wallet buyerWallet=walletRepository.findBycoinTypeAndUser(CoinType.FIAT, user);
		
	  if(buyerWallet.getShadowBal()<gross)
		  return "Insufficient funds!";
		
		UserOrder userorder=new UserOrder();
		userorder.setCoinName(bsb.getCoinName());
		userorder.setCoinQuantity(bsb.getCoinQuantity());
		userorder.setDate(new Date());
		userorder.setOrderType(OrderType.BUY);
		userorder.setOrderStatus(UserOrderStatus.PENDING);
		userorder.setUser(user);
		userorder.setPrice(bsb.getPrice());
		userorder.setUserId(user.getUserId());
		userorder.setCoinType(bsb.getCoinType());
		userorderRepository.save(userorder);
		
		return "your order has been placed successfully! wait for approval";
	}



	public String createSellOrder(BuySellBean bsb) {

		
	     User user =userservice.getUserById(bsb.getUserId()).get();
	      Wallet sellerWallet=walletRepository.findBycoinNameAndUser(bsb.getCoinName(), user);
	      
	 	 if(sellerWallet==null) {
	 		 return "seller dont have this currency";
	 	 }
	     if(user==null)
				return "user is null";
	     
	     if(bsb.getCoinQuantity()==0)
	    	 return "0 quantity to sell";
	     if(user.getStatus()==null)
				return "user is inactive";
			if(user.getStatus().equals(UserStatus.INACTIVE))
				return "user is inactive";
			if(bsb.getUserId()==null)
	         return "userId cannot be null";
			
			if(bsb.getCoinName()==null||bsb.getCoinName().length()==0)
			      return "coin name cannot be null";
			
			if(bsb.getCoinQuantity()==null||bsb.getCoinQuantity()==0)
			      return "coin quantity cannot be null";
			
			if(bsb.getPrice()==null||bsb.getPrice()==0)
			      return "price cannot be null";
			
	     
			UserOrder userorder=new UserOrder();
			userorder.setCoinName(bsb.getCoinName());
			userorder.setCoinQuantity(bsb.getCoinQuantity());
			userorder.setDate(new Date());
			userorder.setOrderType(OrderType.SELL);
			userorder.setOrderStatus(UserOrderStatus.PENDING);
			userorder.setUser(user);
			userorder.setPrice(bsb.getPrice());
			userorder.setUserId(user.getUserId());
			userorder.setCoinType(bsb.getCoinType());
		
			userorderRepository.save(userorder);
			
			return "your order has been placed successfully! wait for approval";
		}
		
	
	
	public UserOrder getUserOrderById(Integer orderId) {
		UserOrder userorder=userorderRepository.findByuserorderId(orderId);
		return userorder;
	}



//	public List<UserOrder> getAllOrdersByUserId(Integer userId) {
//	
//		return userorderRepository.findByuserId(userId);
//		
//	}
	
	
	
}
