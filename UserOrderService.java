package com.trainingproject.service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trainingproject.domain.Currency;
import com.trainingproject.domain.User;
import com.trainingproject.domain.UserOrder;
import com.trainingproject.domain.Wallet;
import com.trainingproject.dto.UserOrderBuySellDto;
import com.trainingproject.enums.CoinType;
import com.trainingproject.enums.OrderType;
import com.trainingproject.enums.Status;
import com.trainingproject.repository.CurrencyRepository;
import com.trainingproject.repository.UserOrderRepository;
import com.trainingproject.repository.UserRepository;
import com.trainingproject.repository.WalletRepository;

@Service
public class UserOrderService {
	@Autowired
	private UserOrderRepository userOrderRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CurrencyRepository currencyRepository;
	@Autowired
	private WalletRepository walletRepository;
	
	User user;
	Currency currency;
	Integer netAmount;
	Integer grossAmount;
	Set<Wallet> walletSet = new HashSet<Wallet>();
	
	
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
		UserOrder  userOrder = new UserOrder();
	    user = userRepository.findByUserId(userOrderBuySellDto.getUserId());
	    currency = currencyRepository.findByCoinName(userOrderBuySellDto.getCoinName());
	    netAmount = (userOrderBuySellDto.getCoinQuantity() * userOrderBuySellDto.getPrice());
	    grossAmount = netAmount + (((userOrderBuySellDto.getCoinQuantity() * 
				userOrderBuySellDto.getPrice())*currency.getFees())/100);
	    String coinName = userOrderBuySellDto.getCoinName();
	    Wallet wallet = walletRepository.findByCoinTypeAndCoinNameAndUser(CoinType.FIAT, "INR", user);
	    Wallet cryptoWallet = walletRepository.findByCoinTypeAndCoinNameAndUser(CoinType.CRYPTO, coinName, user);
	    if(cryptoWallet != null) {
	    if(wallet.getBalance() >= grossAmount || user != null || currency != null) {
	    	//if(user != null) {
	    	userOrder.setOrderType(OrderType.Buyer);
	    	userOrder.setCoinName(userOrderBuySellDto.getCoinName());
	    	userOrder.setCoinType(userOrderBuySellDto.getCoinType());
	    	userOrder.setCoinQuantity(userOrderBuySellDto.getCoinQuantity());
	    	userOrder.setFee(currency.getFees());
	    	userOrder.setPrice(userOrderBuySellDto.getPrice());
	    	userOrder.setNetAmount(netAmount);
	    	userOrder.setGrossAmount(grossAmount);
	    	userOrder.setOrderCreatedOn(new Date());
	    	userOrder.setStatus(Status.PENDING);
	    	userOrder.setUser(user);
	    	userOrderRepository.save(userOrder);
	    	wallet.setShadowBalance(wallet.getShadowBalance() - grossAmount);
	    	walletSet.add(wallet);
	    	walletRepository.save(wallet);
	   // }
		
	    	return "createBuyOrder success";
	    } 
	    	else 
	    		return "User have not sufficient balance or User does not exist or currency does not exist";
	 }
	    else
	    	return "You can not createbuyorder, Please add crypto wallet";
  }
	    
	    

	public String createSellOrder(UserOrderBuySellDto userOrderBuySellDto) {
		// TODO Auto-generated method stub
		user = userRepository.findByUserId(userOrderBuySellDto.getUserId());
		CoinType coinType = userOrderBuySellDto.getCoinType();
	    String coinName = userOrderBuySellDto.getCoinName();
	    Wallet wallet = walletRepository.findByCoinTypeAndCoinNameAndUser(coinType, coinName, user);
		UserOrder  userOrder = new UserOrder();
		if(wallet != null) {
		if(wallet.getBalance() >= userOrderBuySellDto.getCoinQuantity() && user != null ) {
			userOrder.setOrderType(OrderType.Seller);
			userOrder.setCoinName(userOrderBuySellDto.getCoinName());
			userOrder.setCoinType(userOrderBuySellDto.getCoinType());
			userOrder.setCoinQuantity(userOrderBuySellDto.getCoinQuantity());
			userOrder.setPrice(userOrderBuySellDto.getPrice());
			netAmount = (userOrderBuySellDto.getCoinQuantity() * userOrderBuySellDto.getPrice());
			userOrder.setNetAmount(netAmount);
			userOrder.setGrossAmount(netAmount);
			userOrder.setOrderCreatedOn(new Date());
			userOrder.setStatus(Status.PENDING);
			userOrder.setUser(user);
			userOrderRepository.save(userOrder);
			wallet.setShadowBalance(wallet.getShadowBalance() - userOrderBuySellDto.getCoinQuantity());
			walletSet.add(wallet);
			walletRepository.save(wallet);
			return "Createsellorder success";
		}	
			else
				return "User have not sufficient Quantity or User does not exist";
	}	
		else
			return "No wallet found, Please create wallet first";
}

	public String getOrderByUserId(Integer userId) {
		// TODO Auto-generated method stub
		return null;
	}
}
