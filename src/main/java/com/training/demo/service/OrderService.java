package com.training.demo.service;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.training.demo.dto.OrderApprovalDto;
import com.training.demo.enums.OrderStatus;
import com.training.demo.enums.WalletType;
import com.training.demo.model.OrderTable;
import com.training.demo.model.Transaction;
import com.training.demo.model.User;
import com.training.demo.model.Wallet;
import com.training.demo.repository.CoinManagementRepository;
import com.training.demo.repository.OrderRepository;
import com.training.demo.repository.TransactionRepository;
import com.training.demo.repository.UserRepository;
import com.training.demo.repository.WalletRepository;

@Service
public class OrderService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	CoinManagementRepository coinManagementRepository;
	
	@Autowired
	OrderRepository orderdata;
	
	@Autowired
	UserRepository userData;
	
	@Autowired
	CoinManagementRepository coinRepository;
	
	@Autowired
	WalletRepository walletRepository;
	
	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	TransactionRepository transactionRepository;
		
	public String approveOrder(OrderApprovalDto orderApprovalDto)
	{   
		try
		{
			OrderTable orderTable = orderRepository.findOneByOrderId(orderApprovalDto.getOrderId());
			if(orderTable.getOrderStatus().equals(OrderStatus.PENDING))
			{
				User user = orderTable.getUser();
				if(orderTable != null || user !=null) 
				{
					boolean flag = false;
					Set<Wallet> wallets = new HashSet<Wallet>();
					wallets = user.getWallets();
					Wallet wallet;	
					Iterator<Wallet> iterator = wallets.iterator();
					while(iterator.hasNext())
					{
						wallet = iterator.next();
						String tempWalletType = wallet.getWalletType();
						if(tempWalletType.equals("FIAT"))
						{		
								flag = true;
								Transaction transaction = new Transaction();
								transaction.setTransactionId(orderTable.getOrderId());
								transaction.setTransactionStatus(orderApprovalDto.getStatus());
								transaction.setCoinName(orderTable.getCoinName());
								transaction.setCoinQuantity(orderTable.getNetAmount());
								transaction.setCoinType(WalletType.valueOf(tempWalletType));
								transaction.setGrossAmount(orderTable.getNetAmount());
								transaction.setNetAmount(orderTable.getNetAmount());
								transaction.setDescription(orderApprovalDto.getDescription());	
								transaction.setTransactionCreatedOn(new Date());
								transactionRepository.save(transaction);
								orderTable.setOrderStatus(transaction.getTransactionStatus());
								wallet.setCoinName(orderTable.getCoinName());
								Double availableBalance = wallet.getBalance();
								wallet.setBalance(availableBalance+orderTable.getNetAmount());
								Double availableShadowBalance = wallet.getShadowBalance();
								wallet.setShadowBalance(availableShadowBalance+orderTable.getNetAmount());
								userRepository.save(user);
						}
					}
					if(!flag)
					{
						return "There is no any wallet for the given wallet type.";
					}
				}
			}
			else
			{
				return "There is no any pending transaction for given order id : "+orderApprovalDto.getOrderId();
			}
		}	
		catch(Exception e)
		{
			return "There is no any order or user for the given order id = "+orderApprovalDto.getOrderId()+", "+e;
		}
		
		return "Transaction request has been approved";
		
	}
	
	public String createBuyOrder(OrderTable orderTable)
	{ 
			User user = orderTable.getUser();
			OrderTable tempOrderTable = new OrderTable();
			tempOrderTable.setUser(user);
			tempOrderTable.setCoinName((orderTable.getCoinName()));
			tempOrderTable.setCoinQuantity(orderTable.getCoinQuantity());
			tempOrderTable.setOrderStatus(OrderStatus.PENDING);
			tempOrderTable.setFee(orderTable.getFee());
			orderRepository.save(tempOrderTable);

	     return "Your Order Has Been  placed successfully, Wait for Approval.";
	}
	
	public String createSellOrder(OrderTable orderTable)
	{ 
			User user = orderTable.getUser();
			OrderTable tempOrderTable = new OrderTable();
			tempOrderTable.setUser(user);
			tempOrderTable.setCoinName((orderTable.getCoinName()));
			tempOrderTable.setCoinQuantity(orderTable.getCoinQuantity());
			tempOrderTable.setOrderStatus(OrderStatus.PENDING);

	     return "Your Order Has Been  placed successfully, Wait for Approval.";
	}
	
	public Set<OrderTable> showalldata(Integer userId) {
		User user = userData.findByUserId(userId);
		Set<OrderTable> allOrders = user.getOrderTable();
		return allOrders;
	}
	
}
