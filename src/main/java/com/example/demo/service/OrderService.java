package com.example.demo.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dto.DepositAmountApprovalDTO;
import com.example.demo.dto.OrderDTO;
import com.example.demo.enums.TransactionType;
import com.example.demo.enums.WalletType;
import com.example.demo.enums.OrderStatus;
import com.example.demo.enums.OrderType;
import com.example.demo.model.User;
import com.example.demo.model.Wallet;
import com.example.demo.model.Order;
import com.example.demo.model.Transaction;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.TransactionRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.WalletRepository;

@Service
public class OrderService {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	WalletRepository walletRepository;
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	private Order orderTable = new Order();

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
					
					walletUpdate.setBalance(order.getNetAmount());
					
					walletUpdate.setShadowBalance(order.getNetAmount());
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
	
	public Map<String, Object> buyOrder(OrderDTO orderDTO) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		User user=userRepository.findByUserId(orderDTO.getUserId());
		
		if(user.getWallets().contains(WalletType.CRYPTO))
		{
			Order userOrder=new Order();
			userOrder.setUser(user);
			userOrder.setCoinName(orderDTO.getCoinName());
			userOrder.setCoinType(WalletType.CRYPTO);
			userOrder.setOrderStatus(OrderStatus.PENDING);
			userOrder.setTransactionType(TransactionType.BUYER);
			userOrder.setCoinQuantity(orderDTO.getCoinQuantity());
			userOrder.setFee(orderDTO.getFee());
			userOrder.setPrice(orderDTO.getPrice());
			userOrder.setDateCreated(new Date());
			orderRepository.save(userOrder);
			
			result.put("isSuccess", true);
			result.put("message", "Order is in pending status and has to be approved by admin.");
			return result;	
		}
		if(!(user.getWallets().contains(WalletType.CRYPTO)))
		{
			Wallet wallet = new Wallet();
			wallet.setWalletType(WalletType.CRYPTO);
			wallet.setBalance(0.0);
			wallet.setShadowBalance(0.0);
			wallet.setCoinName(orderDTO.getCoinName());
			wallet.setUser(user);
			walletRepository.save(wallet);
			
			Order userOrder=new Order();
			userOrder.setUser(user);
			userOrder.setCoinName(orderDTO.getCoinName());
			userOrder.setCoinType(WalletType.CRYPTO);
			userOrder.setOrderStatus(OrderStatus.PENDING);
			userOrder.setTransactionType(TransactionType.BUYER);
			userOrder.setCoinQuantity(orderDTO.getCoinQuantity());
			userOrder.setPrice(orderDTO.getPrice());
			userOrder.setDateCreated(new Date());
			orderRepository.save(userOrder);
			
			result.put("isSuccess", true);
			result.put("message", "Order is in pending status and has to be approved by admin.");
			return result;
		}
		else
		{
			result.put("isSuccess", false);
			result.put("message", "order not completed.");
			return result;
		}	
	}
	
	public Map<String, Object> sellOrder(OrderDTO orderDTO) 
	{
		Map<String, Object> result = new HashMap<String, Object>();
		
		User user=userRepository.findByUserId(orderDTO.getUserId());
		if(user != null)
		{
			Order sellUserOrder=new Order();
			sellUserOrder.setUser(user);
			sellUserOrder.setCoinName(orderDTO.getCoinName());
			sellUserOrder.setCoinType(WalletType.CRYPTO);
			sellUserOrder.setOrderStatus(OrderStatus.PENDING);
			sellUserOrder.setTransactionType(TransactionType.SELLER);
			sellUserOrder.setCoinQuantity(orderDTO.getCoinQuantity());
			sellUserOrder.setPrice(orderDTO.getPrice());
			sellUserOrder.setDateCreated(new Date());
			orderRepository.save(sellUserOrder);
			
			result.put("isSuccess", false);
			result.put("message", "Order is in pending status and has to be approved by admin.");
			return result;
		}
	
		else
		{
			result.put("isSuccess", false);
			result.put("message", "Order not completed");
			return result;
		}
	}
	
	public String transactionManagement(@RequestParam("coinName") String coinName)
	{
		List<Order> orders=orderRepository.findByCoinName(coinName);
		Iterator itr=orders.iterator();
		while(itr.hasNext())
		{
			Order order = (Order)itr.next();
			
		}
		return null;
	}
}
