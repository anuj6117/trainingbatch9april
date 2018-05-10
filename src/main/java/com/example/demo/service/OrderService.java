package com.example.demo.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.DepositAmountApprovalDTO;
import com.example.demo.dto.OrderDTO;
import com.example.demo.enums.OrderStatus;
import com.example.demo.enums.OrderType;
import com.example.demo.enums.WalletType;
import com.example.demo.model.CoinManagement;
import com.example.demo.model.Order;
import com.example.demo.model.Transaction;
import com.example.demo.model.User;
import com.example.demo.model.Wallet;
import com.example.demo.repository.CoinManagementRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.TransactionRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.WalletRepository;

@Service
public class OrderService {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private WalletRepository walletRepository;
	@Autowired
	private CoinManagementRepository coinManagementRepository;
	@Autowired
	private TransactionRepository transactionRepository;
	@Autowired
	private OrderRepository orderRepository;	
	
	private Wallet wallet = null;

	public Map<String, Object> approveDeposit(DepositAmountApprovalDTO depositAmountApprovalDTO) 
	{
		Map<String, Object> result = new HashMap<String, Object>();
		
		Order order=orderRepository.findByOrderId(depositAmountApprovalDTO.getOrderId());
		User user=order.getUser();
		if(depositAmountApprovalDTO.getOrderStatus().equals(OrderStatus.APPROVED))
		{
			order.setOrderStatus(OrderStatus.APPROVED);
			Transaction transaction=new Transaction();
			transaction.setTransactionId(order.getOrderId());
			transaction.setCoinType(WalletType.FIAT);
			transaction.setNetAmount(order.getNetAmount());
			transaction.setCoinQuantity(order.getCoinQuantity());
			transaction.setGrossAmount(order.getGrossAmount());
			transaction.setTransactionCreatedOn(order.getDateCreated());
			transaction.setDescription(depositAmountApprovalDTO.getDescription());
			transaction.setOrderType(OrderType.DEPOSIT);
			transaction.setOrderStatus(OrderStatus.APPROVED);
			
			transactionRepository.save(transaction);
			Set<Wallet> wallet=user.getWallets();
			Iterator<Wallet> itr=wallet.iterator();
			while(itr.hasNext())
			{
				Wallet walletUpdate=itr.next();
				if(walletUpdate.getWalletType().equals(WalletType.FIAT))
				{
					Double previousBalance=walletUpdate.getBalance();
					Double previousShadowBalance = walletUpdate.getShadowBalance();
					
					walletUpdate.setBalance(order.getNetAmount() + previousBalance);
					walletUpdate.setShadowBalance(order.getNetAmount() + previousShadowBalance);
					walletUpdate.setCoinName(order.getCoinName());
					walletRepository.save(walletUpdate);
					userRepository.save(user);
				}
			}
			result.put("isSuccess", true);
			result.put("message", "Deposit Approval Done.");
			return result;	
		}
		else
		{
			result.put("isSuccess", false);
			result.put("message", "Deposit Approval Failed.");
			return result;
		}	
	}
	
	public String buyOrder(OrderDTO orderDTO) 
	{
		boolean flag = false;
		if(orderDTO.getUserId() == null)
		{
			return "User does not exist";
		}
		User user = userRepository.findByUserId(orderDTO.getUserId()); 
		Double shadowBalance = null;
		if(user == null)
		{
			return "null user";
		}
		for(Wallet wallet1 : user.getWallets())
		{
			if(wallet1.getCoinName().equals(orderDTO.getCoinName())) 
			{
				flag = true;
				wallet = wallet1;
			}
			if(wallet1.getCoinName().equals("INR"))
			{
				shadowBalance = wallet1.getShadowBalance();
			}
			
		}
		if(flag) 
		{
			CoinManagement coinManagementCoinName = coinManagementRepository.findByCoinName(orderDTO.getCoinName());
			if(coinManagementCoinName != null)
			{
				Double totalAmount = ((orderDTO.getPrice() * orderDTO.getCoinQuantity() * coinManagementCoinName.getFee()) / 100) + orderDTO.getPrice() * orderDTO.getCoinQuantity();
										
				if(shadowBalance >= totalAmount)
				{
					for(Wallet wallet1 : user.getWallets())
					{
						if(wallet1.getCoinName().equals("INR")) 
						{
							wallet1.setShadowBalance(shadowBalance - totalAmount);
							walletRepository.save(wallet);
							break;
						}
					}					
					Order order = new Order();
					order.setCoinType(WalletType.CRYPTO);
					order.setCoinName(orderDTO.getCoinName());
					order.setCoinQuantity(orderDTO.getCoinQuantity());
					order.setPrice(orderDTO.getPrice());
					order.setOrderType(OrderType.BUYER);
					order.setOrderStatus(OrderStatus.PENDING);
					order.setGrossAmount(totalAmount);
					order.setFee(coinManagementCoinName.getFee());
					order.setDateCreated(new Date());
					order.setNetAmount(orderDTO.getCoinQuantity()*orderDTO.getPrice());
					order.setUser(user);
				
					orderRepository.save(order);
					
					return "Your order has been placed and need for approval.";
				}
				else
				{
					return "You do not have enough balance to buy coin.";
				}
			}
			return "currency does not exist.";
		}
		return "User does not have this wallet.";
	}
		
	public String sellOrder(OrderDTO orderDTO) 
	{
		boolean flag = false;
	
		if(orderDTO.getUserId() == null)
		{
			return "User does not exist";
		}
		User user = userRepository.findByUserId(orderDTO.getUserId());
		if(user==null)
		{
			return "null user";
		}
		for(Wallet wallet1 : user.getWallets())
		{
			if(wallet1.getCoinName().equals(orderDTO.getCoinName()))
			{			
				if(wallet1.getShadowBalance() >= orderDTO.getCoinQuantity())
				{
					wallet1.setShadowBalance(wallet1.getShadowBalance() - orderDTO.getCoinQuantity());
					wallet1.setUser(user);
					walletRepository.save(wallet1);
					flag = true;
					break;
				}
				else
				{
					return "insufficient coin";
				}
			}
		}
		if(flag)
		{
			double netAmount = orderDTO.getCoinQuantity() * orderDTO.getPrice();
			Order order = new Order();
			order.setCoinType(WalletType.CRYPTO);
			order.setCoinName(orderDTO.getCoinName());
			order.setCoinQuantity(orderDTO.getCoinQuantity());
			order.setPrice(orderDTO.getPrice());
			order.setOrderType(OrderType.SELLER);
			order.setOrderStatus(OrderStatus.PENDING);
			order.setGrossAmount(netAmount);
			order.setFee(0.0);
			order.setDateCreated(new Date());
			order.setNetAmount(netAmount);
			order.setUser(user);
		
			orderRepository.save(order);
			
			return "Your order has been placed and need for approval.";
		}
		else
		{
			return "wallet not exist";
		}
	}
}
