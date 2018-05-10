package com.training.demo.service;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.training.demo.dto.OrderApprovalDto;
import com.training.demo.dto.SellBuyTransactionDto;
import com.training.demo.enums.OrderStatus;
import com.training.demo.enums.OrderType;
import com.training.demo.enums.WalletType;
import com.training.demo.model.CoinManagement;
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
		OrderTable orderTable;
			
			if((orderTable = orderRepository.findOneByOrderId(orderApprovalDto.getOrderId()))==null){
				return "Invalid Order Id.";
			}
			if(!(orderTable.getUser().getUserId() == orderApprovalDto.getUserId())) {
				return "Invalid User Id.";
			}
			
			if(!(orderApprovalDto.getStatus().equalsIgnoreCase("APPROVED") || orderApprovalDto.getStatus().equalsIgnoreCase("FAILED") || orderApprovalDto.getStatus().equalsIgnoreCase("REJECTED"))) { 
				return "Invalid Order Status.";
			}
			
		boolean currencyFlag = false;
		List<CoinManagement> currencyList = coinRepository.findAll();
		if(currencyList != null) {
			Iterator<CoinManagement> currencyIterator = currencyList.iterator();
			
			while(currencyIterator.hasNext())
			{
				CoinManagement tempCoinManagement= currencyIterator.next();
				if(tempCoinManagement.getCoinName().equals(orderTable.getCoinName())){
					currencyFlag = true;
				}
			}
			if(currencyFlag) {
				return "you can only approve FIAT orders but not CRYPTO orders.";
			}
		}
		
			if(orderTable.getOrderStatus().equals(OrderStatus.PENDING))
			{
				User user ;
				try 
				{	
					user = userRepository.findByUserId(orderApprovalDto.getUserId());
				}
				catch(Exception e)
				{
					return "invalid user id";
				}
				
				if(user == null)
				{
					return "invalid user id";
				}
				
				if(orderTable != null && user !=null) 
				{
					boolean flag = false;
					Set<Wallet> wallets = new HashSet<Wallet>();
					wallets = user.getWallets();
					Wallet wallet;	
					Iterator<Wallet> iterator = wallets.iterator();
					while(iterator.hasNext())
					{
						wallet = iterator.next();
						String tempWalletType = wallet.getCoinType();
						if(tempWalletType.equals("FIAT"))
						{		
								flag = true;
								Transaction transaction = new Transaction();
								transaction.setBuyerId(orderTable.getUser().getUserId());
								transaction.setTransactionId(orderTable.getOrderId());
								transaction.setTransactionStatus(OrderStatus.valueOf(orderApprovalDto.getStatus().toUpperCase()));
								transaction.setCoinName(orderTable.getCoinName());
								transaction.setCoinQuantity(orderTable.getNetAmount());
								transaction.setCoinType(WalletType.valueOf(tempWalletType));
								transaction.setGrossAmount(orderTable.getNetAmount());
								transaction.setExchangeRate(0d);
								transaction.setFees(0d);
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
						return "invalid coin name.";
					}
				}
			}
			else
			{
				return "There is no any pending transaction for given order id : "+orderApprovalDto.getOrderId();
			}
			return "Transaction request has been approved";	
	}	
	
	public String createBuyOrder(SellBuyTransactionDto sellBuyTransactionDto)
	{
	
			if(sellBuyTransactionDto.getCoinQuantity()<=0)
			{
				return "coin quantity can not be 0 or less.";
			}
			boolean flag = false;

			Integer userId = sellBuyTransactionDto.getUserId();
			User user;
			CoinManagement coinManagement; 
			Double shadowBalance = 0.0;
			Wallet fiatWallet = null;
			Wallet tempWallet = null;
			Set<Wallet> wallets ;
			Iterator<Wallet> iterator ;
			
			try {
				user = userRepository.findByUserId(userId);
				System.out.println("  >> "+user.getUserId());
				wallets = user.getWallets();
				iterator = wallets.iterator();
				while(iterator.hasNext()) {
				 tempWallet = iterator.next();

					if(tempWallet.getCoinType().equals(WalletType.CRYPTO.toString()) 
							&& 
							tempWallet.getCoinName().equals(sellBuyTransactionDto.getCoinName())) 
					{
						flag  = true;
					}
				 
					if(tempWallet.getCoinType().equals(WalletType.FIAT.toString()))
					{	fiatWallet = tempWallet;
						shadowBalance = fiatWallet.getShadowBalance();
					}					
				}
			}
			catch(Exception e)
			{
				return "invalid user id.";
			}
			if(!flag)
			{
				return "create wallet first.";
			}
			try 
			{
				coinManagement = coinManagementRepository.findOneByCoinName(sellBuyTransactionDto.getCoinName());
			}
			catch(Exception e)
			{
				return "invalid coin name.";
			}
			Double fees = coinManagement.getFees();
			Double price = sellBuyTransactionDto.getPrice();
			System.out.println(price+" = price");
			Double quantity = sellBuyTransactionDto.getCoinQuantity();
			System.out.println(quantity+" = quantity");
			Double netAmount = (quantity * price);
			System.out.println(netAmount+" = netAmount");
			fees = ((netAmount * fees)/100);
			System.out.println(fees+" = fees");
			Double grossAmount = (netAmount + fees);
			System.out.println(grossAmount+" = GrossAmount");
			if(shadowBalance >= grossAmount) 
			{
				OrderTable tempOrderTable = new OrderTable();
				tempOrderTable.setUser(user);
				tempOrderTable.setCoinName((sellBuyTransactionDto.getCoinName()));
				tempOrderTable.setCoinQuantity(sellBuyTransactionDto.getCoinQuantity());
				tempOrderTable.setOrderType(OrderType.BUYER);
				tempOrderTable.setOrderStatus(OrderStatus.PENDING);
				tempOrderTable.setFees(fees);
				tempOrderTable.setPrice(price);
				tempOrderTable.setNetAmount(netAmount);
				tempOrderTable.setGrossAmount(grossAmount);	
				tempOrderTable.setOrderCreatedOn(new Date());
				orderRepository.save(tempOrderTable);
				shadowBalance = (shadowBalance - grossAmount);
				System.out.println(shadowBalance);
				fiatWallet.setShadowBalance(shadowBalance);
				walletRepository.save(tempWallet);
				return "Your Order Has Been  placed successfully, Wait for Approval.";
			}
			else if(shadowBalance < grossAmount)
			{
				return "Your Order can not be place due to Insufficient Balance.";
			}
			else 
			{
				return "user is inactive.";
			}
	}
	
	public String createSellOrder(SellBuyTransactionDto sellBuyTransactionDto)
	{ 
		
		if(sellBuyTransactionDto.getCoinQuantity()<=0)
		{
			return "coin quantity can not be 0 or less.";
		}
		
		Integer userId = sellBuyTransactionDto.getUserId();
		boolean flag = false;
		Double shadowBalance= 0.0;
		User user = userRepository.findByUserId(userId);
		Set<Wallet> wallets = user.getWallets();
		Iterator<Wallet> iterator = wallets.iterator();
		Wallet wallet = null;
		while(iterator.hasNext())
		{
			wallet = iterator.next();
			String walletType = wallet.getCoinType();
			String coinName = wallet.getCoinName();
			if(walletType.equals("CRYPTO") && coinName.equals(sellBuyTransactionDto.getCoinName())) {
				if(wallet.getBalance()>=sellBuyTransactionDto.getCoinQuantity())
				{
					flag = true;
					shadowBalance = wallet.getShadowBalance()-sellBuyTransactionDto.getCoinQuantity();
					break;
				}
			}
		}
		
		if(flag) {
		CoinManagement coinManagement = coinManagementRepository.findOneByCoinName(sellBuyTransactionDto.getCoinName());	
		OrderTable tempOrderTable = new OrderTable();
		tempOrderTable.setUser(user);
		tempOrderTable.setCoinName((sellBuyTransactionDto.getCoinName()));
		tempOrderTable.setCoinQuantity(sellBuyTransactionDto.getCoinQuantity());
		tempOrderTable.setNetAmount(sellBuyTransactionDto.getCoinQuantity()*sellBuyTransactionDto.getPrice());
		tempOrderTable.setGrossAmount(sellBuyTransactionDto.getCoinQuantity()*sellBuyTransactionDto.getPrice());
		tempOrderTable.setPrice(sellBuyTransactionDto.getPrice());
		tempOrderTable.setFees(0d);
		tempOrderTable.setOrderCreatedOn(new Date());
		tempOrderTable.setOrderType(OrderType.SELLER);
		tempOrderTable.setOrderStatus(OrderStatus.PENDING);
		orderRepository.save(tempOrderTable);
		wallet.setShadowBalance(shadowBalance);
		walletRepository.save(wallet);
		return "Your Order Has Been  placed successfully, Wait for Approval.";
		}
		else
		{
			return "Your Order could not be processed.";
		}
		
	}
	
	public Object showAllOrder() {
		List<OrderTable> allOrders = orderRepository.findAll();
		if(allOrders == null)
		{
			return "There is no any order.";
		}
		return allOrders;
	}

	public Object getUserOrderById(Integer userId) {
		User user = userData.findByUserId(userId);
		if(user == null)
		{
			return "invalid user id";
		}
		
		Set<OrderTable> allOrders = user.getOrders();
		if(allOrders.isEmpty())
		{
			return "No Any Orders For Given User Id.";
		}
		return allOrders;
	}
	
}