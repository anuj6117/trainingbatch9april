package com.trading.services;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trading.Enum.OrderType;
import com.trading.Enum.TransactionOrderStatus;
import com.trading.Enum.WalletType;
import com.trading.domain.User;
import com.trading.domain.UserOrder;
import com.trading.domain.Wallet;
import com.trading.dto.UserWalletDto;
import com.trading.repository.OrderRepository;
import com.trading.repository.UserRepository;
import com.trading.repository.WalletRepository;

@Service
public class WalletService {

	@Autowired
	private WalletRepository walletRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private OrderRepository orderRepository;

	public Map<String, Object> insertWallet(UserWalletDto userwalletdto) {
		Map<String, Object> result = new HashMap<String, Object>();

		User user = userRepository.findOneByUserId(userwalletdto.getuserId());
		Wallet walletdb = walletRepository.findByCoinNameAndUser(userwalletdto.getCoinName(), user);
		if(user ==null)
		{
			result.put("isSuccess", true);
			result.put("message", "User does not exist");
			return result;
		}
		if (walletdb == null) {
			Wallet wallet = new Wallet();
			wallet.setCoinType(userwalletdto.getCoinType());
			wallet.setuser(user);
			wallet.setCoinName(userwalletdto.getCoinName());
			wallet.setBalance(userwalletdto.getBalance());
			wallet.setShadowBalance(userwalletdto.getShadowBalance());
			walletRepository.save(wallet);
			result.put("isSuccess", true);
			result.put("message", "New wallet has been added and assigned");
			return result;
		}

		else {
			result.put("isSuccess", false);
			result.put("message", "CoinName already exist");
			return result;
		}
	}

	public Map<String, Object> depositAmount(UserWalletDto userwalletdto) {
		Map<String, Object> result = new HashMap<String, Object>();

		User user = userRepository.findOneByUserId(userwalletdto.getuserId());
		UserOrder userOrder = new UserOrder();
		Wallet wallet = walletRepository.findByCoinNameAndUser(userwalletdto.getCoinName(), user);

			
			if(user != null) {
				if(wallet != null) {
		userOrder.setOrderType(OrderType.DEPOSIT);
		userOrder.setCoinType(WalletType.FIAT);
		userOrder.setCoinName(userwalletdto.getCoinName());
		userOrder.setPrice(userwalletdto.getamount());
		userOrder.setOrderCreatedOn(new Date().toString());
		userOrder.setStatus(TransactionOrderStatus.PENDING);
		userOrder.setUser(user);
		orderRepository.save(userOrder);
		}
		else
		{
			result.put("isSuccess", false);
			result.put("message", "Wallet does not  exist");
			return result;
		}}
			else {
				result.put("isSuccess", false);
				result.put("message", "User does not  exist");
				return result;
			}
			
		if (userOrder.getStatus() == TransactionOrderStatus.APPROVED) {

			result.put("isSuccess", true);
			result.put("message", "Amount added to wallet balance");
			return result;
		} else {
			result.put("isSuccess", true);
			result.put("message", "Successfully created entry in order table with status pending");
			return result;
		}
	}

	public Map<String, Object> withdrawAmount(UserWalletDto userwalletdto) {
		Map<String, Object> result = new HashMap<String, Object>();
		Wallet wallet = new Wallet();
		User user = userRepository.findOneByUserId(userwalletdto.getuserId());

		wallet = walletRepository.findByCoinNameAndUser(userwalletdto.getCoinName(), user);
		
		
		UserOrder userOrder = new UserOrder();
		
				
			//if(orderRepository.findByCoinNameAndUser(userwalletdto.getCoinName(), user)== null) {
			if(user != null) {
				if(wallet != null) {
			userOrder.setOrderType(OrderType.WITHDRAW);
			userOrder.setCoinType(WalletType.FIAT);
			userOrder.setCoinName(userwalletdto.getCoinName());
			userOrder.setPrice(userwalletdto.getamount());
			userOrder.setOrderCreatedOn(new Date().toString());
			userOrder.setStatus(TransactionOrderStatus.PENDING);
			userOrder.setUser(user);
			orderRepository.save(userOrder);
			}
			else
			{
				result.put("isSuccess", false);
				result.put("message", "Wallet does not exist");
				return result;
			}}
			else
			{
				result.put("isSuccess", false);
				result.put("message", "User does not  exist");
				return result;	
			}
			if (userOrder.getStatus() == TransactionOrderStatus.APPROVED) {

				result.put("isSuccess", true);
				result.put("message", "Amount deducted from wallet balance");
				return result;
			} else {
				result.put("isSuccess", true);
				result.put("message", "Successfully created entry in order table with status pending");
				return result;
			}
		
		
		
	}

	public UserOrder walletHistory(long userId, String coinName)
	{
		
		User user = userRepository.findOneByUserId(userId);
		if(user != null)
		{
			
		
		UserOrder userorder = orderRepository.findByCoinNameAndUser(coinName, user);
		return userorder;
	}
		else {
			return null;
		}
}}


		
		
		
		
		
		
		
		
		
		
		
		
		
		