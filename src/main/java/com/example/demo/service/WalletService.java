package com.example.demo.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.DepositAmountDTO;
import com.example.demo.dto.WalletDTO;
import com.example.demo.enums.OrderStatus;
import com.example.demo.enums.OrderType;
import com.example.demo.enums.WalletType;
import com.example.demo.model.User;
import com.example.demo.model.Order;
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
		
		User newUser=userRepository.findByUserId(walletDTO.getUserId());
		
		Wallet wallet=new Wallet();
		//wallet.setWalletType(WalletType.FIAT);
		wallet.setUser(newUser);
		wallet.setBalance(0.0);
		wallet.setShadowBalance(0.0);
		walletRepository.save(wallet);
		
		/*Set<Wallet> wallets=newUser.getWallets();
		Iterator<Wallet> itr=wallets.iterator();
		while(itr.hasNext())
		{
			Wallet w=itr.next();
			System.out.println(w);
		}*/
		
		return result;
		
	}
	
	
	
	
	

	/*public String addWalletToUser(WalletDTO walletDTO) 
	{

		User user = userRepository.findByUserId(walletDTO.getUserId());
		
		Wallet wallet = new Wallet();
		wallet.setWalletType(WalletType.FIAT);
		wallet.setUser(user);
		wallet.setBalance(0.0);
		wallet.setShadowBalance(0.0);
		try
		{
			walletRepository.save(wallet);
		} catch (Exception e) 
		{
			System.out.println("wallet not added to User." + e);
		}
		return "Wallet Added to User.";

	}*/

	public String deposit(DepositAmountDTO depositAmountDTO)
	{
		User user=userRepository.findByUserId(depositAmountDTO.getUserId());
		
		Order order=new Order();
		order.setUser(user);
		//order.setCoinQuantity(depositAmountDTO.getCoinQuantity());
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
}
