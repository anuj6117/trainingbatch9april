package com.example.demo.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.DepositAmountDTO;
import com.example.demo.dto.UserWalletDTO;
import com.example.demo.dto.WalletDTO;
import com.example.demo.enums.OrderStatus;
import com.example.demo.enums.OrderType;
import com.example.demo.enums.UserStatus;
import com.example.demo.enums.WalletType;
import com.example.demo.model.Order;
import com.example.demo.model.User;
import com.example.demo.model.Wallet;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.WalletRepository;

@Service
public class WalletService 
{
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private WalletRepository walletRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	public Map<String, Object> addWallet(WalletDTO walletDTO)
	{
		Map<String, Object> result = new HashMap<String, Object>();
		User newUser=null;
		
		try
		{
			newUser = userRepository.findByUserId(walletDTO.getUserId());
			if(newUser == null)
			{
				result.put("isSuccess", false);
				result.put("message", "User does not exist.");
				return result;
			}
		}
		catch(Exception e)
		{
			result.put("isSuccess", false);
			result.put("message", "User does not exist.");
			return result;
		}
			
		if(newUser.getStatus().equals(UserStatus.ACTIVE))
		{
			Set<Wallet> wallets=newUser.getWallets();
			Iterator<Wallet> itr=wallets.iterator();
			while(itr.hasNext())
			{
				Wallet wallet=itr.next();
				if(wallet.getWalletType().equals(walletDTO.getWalletType()) && wallet.getCoinName().equals(walletDTO.getCoinName()))
				{
					result.put("isSuccess", false);
					result.put("message", "User has already this wallet.");
					return result;
				}
				else
				{
					Wallet userWallet=new Wallet();
					userWallet.setWalletType(walletDTO.getWalletType());
					userWallet.setCoinName(walletDTO.getCoinName());
					userWallet.setBalance(0.0);
					userWallet.setShadowBalance(0.0);
					userWallet.setUser(newUser);
					walletRepository.save(userWallet);
				
					result.put("isSuccess", true);
					result.put("message", "Your wallet has been created.");
					return result;
				}
			}
		}
		else
		{
			result.put("isSuccess", false);
			result.put("message", "User is INACTIVE please verify user then add wallet to user.");
			return result;
		}
		return result;
	}

	public String deposit(DepositAmountDTO depositAmountDTO)
	{
		User user=userRepository.findByUserId(depositAmountDTO.getUserId());
		
		Order order=new Order();
		order.setUser(user);
		order.setCoinName(depositAmountDTO.getCoinName());
		order.setCoinType(WalletType.FIAT);
		order.setNetAmount(depositAmountDTO.getAmount());
		order.setGrossAmount(depositAmountDTO.getAmount());
		order.setOrderStatus(OrderStatus.PENDING);
		order.setOrderType(OrderType.DEPOSIT);
		order.setDateCreated(new Date());
		order.setWalletType(WalletType.FIAT);
		
		orderRepository.save(order);
		
		return "Order saved successfully.";
		
	}
	
	public List<Wallet> getAllWallets()
	{
		return walletRepository.findAll();
	}

	public List<Order> getWalletHistory(UserWalletDTO userWalletDTO)
	{
		User user = userRepository.findByUserId(userWalletDTO.getUserId());
		//walletRepository.findOrderByUserAndCoinName(user, userWalletDTO.getWalletType());
		return null;
	}
}
