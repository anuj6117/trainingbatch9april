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
		try
		{
			orderTable = orderRepository.findOneByOrderId(orderApprovalDto.getOrderId());
		}
		catch(Exception e) {
			return "invalid order id.";
		}
		
		boolean currencyFlag = false;
		List<CoinManagement> currencyList = coinRepository.findAll();
		System.out.println("curency listtttttttttttttt "+currencyList);
		if(currencyList != null) {
			Iterator<CoinManagement> currencyIterator = currencyList.iterator();
			while(currencyIterator.hasNext())
			{
				System.out.println("in currency whileeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
				CoinManagement tempCoinManagement= currencyIterator.next();
				if(tempCoinManagement.getCoinName().equals(orderTable.getCoinName())){
					System.out.println("in currency ifffffffffffffffffffffffffffffffffffff");
					currencyFlag = true;
					System.out.println("curency flag set trueeeeeeeeeeeeeeeeeeeeeeeee");
				}
			}
			if(currencyFlag) {
				System.out.println("order can not be approved........................its not fiat.");
				return "you can only approve FIAT orders but not CRYPTO orders.";
			}
		}
		
			System.out.println("11111111111111111111111111111111111");
			if(orderTable.getOrderStatus().equals(OrderStatus.PENDING))
			{
				System.out.println("1111111111111111111111111111111111122222222222222222222222");
				User user ;
				try 
				{	System.out.println("USERrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr1111111111111111111111111111111111122222222222222222222222");
					user = userRepository.findByUserId(orderApprovalDto.getUserId());
				}
				catch(Exception e)
				{
					return "invalid user id";
				}
				System.out.println(user+"/t"+orderTable);
				if(user == null)
				{
					return "invalid user id";
				}
				System.out.println(user+"/t"+orderTable);
				if(orderTable != null && user !=null) 
				{
					System.out.println("111111111111111333333333333333333333333333333333");
					boolean flag = false;
					Set<Wallet> wallets = new HashSet<Wallet>();
					wallets = user.getWallets();
					Wallet wallet;	
					Iterator<Wallet> iterator = wallets.iterator();
					while(iterator.hasNext())
					{
						System.out.println("11111111111111111111111111111111111444444444444444444444444");
						wallet = iterator.next();
						String tempWalletType = wallet.getCoinType();
						if(tempWalletType.equals("FIAT"))
						{		
							System.out.println("11111111111111111111111111111111111555555555555555555");
								flag = true;
								Transaction transaction = new Transaction();
								System.out.println("11111111111111111111111111111111111aaaaaaa");
								transaction.setBuyerId(orderTable.getUser().getUserId());
								transaction.setTransactionId(orderTable.getOrderId());
								System.out.println("11111111111111111111111111111111111bbbbbbbb");
								transaction.setTransactionStatus(orderApprovalDto.getStatus());
								System.out.println("11111111111111111111111111111111111ccccccccccc");
								transaction.setCoinName(orderTable.getCoinName());
								System.out.println("11111111111111111111111111111111111dddddddd");
								transaction.setCoinQuantity(orderTable.getNetAmount());
								System.out.println("11111111111111111111111111111111111eeeeeeeeeee");
								transaction.setCoinType(WalletType.valueOf(tempWalletType));
								System.out.println("11111111111111111111111111111111111ffffffffffffffff");
								transaction.setGrossAmount(orderTable.getNetAmount());
								System.out.println("11111111111111111111111111111111111ggggggggggggggggg");
		
								//TodayUpgradations
								transaction.setExchangeRate(0d);
								transaction.setFees(0d);
								
								transaction.setNetAmount(orderTable.getNetAmount());
								System.out.println("11111111111111111111111111111111111hhhhhhhhhhhhhhhhhhhhh");
								transaction.setDescription(orderApprovalDto.getDescription());	
								System.out.println("11111111111111111111111111111111111iiiiiiiiiiiiiiiiiii");
								transaction.setTransactionCreatedOn(new Date());
								System.out.println("11111111111111111111111111111111111jjjjjjjjjjjjj");
								transactionRepository.save(transaction);
								System.out.println("1111111111111111111111111111111111166666666666666666666");
								orderTable.setOrderStatus(transaction.getTransactionStatus());
								wallet.setCoinName(orderTable.getCoinName());
								Double availableBalance = wallet.getBalance();
								wallet.setBalance(availableBalance+orderTable.getNetAmount());
								Double availableShadowBalance = wallet.getShadowBalance();
								wallet.setShadowBalance(availableShadowBalance+orderTable.getNetAmount());
								userRepository.save(user);
								System.out.println("111111111111111111111111111111111117777777777777777777");
						}
					}
					if(!flag)
					{
						System.out.println("1111111111111111111111111111111111188888888888888888888888888");
						return "invalid coin name.";
					}
				}
			}
			else
			{
				System.out.println("111111111111111111111111111111111119999999999999999999999999");
				return "There is no any pending transaction for given order id : "+orderApprovalDto.getOrderId();
			}
			return "Transaction request has been approved";	
	}	
	
	public String createBuyOrder(SellBuyTransactionDto sellBuyTransactionDto)
	{
	
			boolean flag = false;

			Integer userId = sellBuyTransactionDto.getUserId();
			System.out.println("user iddddddddddddddddddddddddddddddddddddddddd >> "+sellBuyTransactionDto.getUserId());
			User user;
			CoinManagement coinManagement; 
			Double shadowBalance = 0.0;
			Wallet tempWallet = null;
			Set<Wallet> wallets ;
			Iterator<Wallet> iterator ;
			
			try {
				System.out.println(sellBuyTransactionDto.getUserId()+"///////////////////");
				user = userRepository.findByUserId(userId);
				System.out.println("  >> "+user.getUserId());
				wallets = user.getWallets();
				iterator = wallets.iterator();
				while(iterator.hasNext()) {
				 tempWallet = iterator.next();

					if(tempWallet.getCoinType().equals(WalletType.CRYPTO.toString()) && tempWallet.getCoinName().equals(sellBuyTransactionDto.getCoinName())) 
					{
						flag  = true;
					}
				 
					if(tempWallet.getCoinType().equals(WalletType.FIAT.toString()))
					{
						shadowBalance = tempWallet.getShadowBalance();
						System.out.println("11111111111111111111111111111111111"+shadowBalance);
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
				System.out.println(sellBuyTransactionDto.getCoinName()+"///////////////////");
				coinManagement = coinManagementRepository.findOneByCoinName(sellBuyTransactionDto.getCoinName());
				System.out.println("///////////////////"+coinManagement.getCoinName());
			}
			catch(Exception e)
			{
				return "invalid coin name.";
			}
			Double fees = coinManagement.getFees();
			Double price = sellBuyTransactionDto.getPrice();
			Double quantity = sellBuyTransactionDto.getCoinQuantity();
			Double netAmount = quantity * price;
			fees = (netAmount * fees/100);
			Double grossAmount = netAmount + fees;
			System.out.println("2222222222222222222222222222222222222222222"+shadowBalance);
			if(shadowBalance >= grossAmount) 
			{
				System.out.println("33333333333333333333333333333333333333333333"+shadowBalance);
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
				shadowBalance = shadowBalance - grossAmount;
				tempWallet.setShadowBalance(shadowBalance);
				walletRepository.save(tempWallet);
				
				while(iterator.hasNext()) {
					 tempWallet = iterator.next();
						if(tempWallet.getCoinType().equals(WalletType.CRYPTO.toString()) && tempWallet.getCoinName().equals(sellBuyTransactionDto.getCoinName()))
						{
							shadowBalance = tempWallet.getShadowBalance();
							System.out.println("11111111111111111111111111111111111"+shadowBalance);
							break;
						}					
					}
				shadowBalance = shadowBalance + sellBuyTransactionDto.getCoinQuantity();
				tempWallet.setShadowBalance(shadowBalance);
				walletRepository.save(tempWallet);
				
				return "Your Order Has Been  placed successfully, Wait for Approval.";
			}
			else if(shadowBalance < grossAmount)
			{
				System.out.println("44444444444444444444444444444444444444444444444444444"+shadowBalance);
				return "Your Order can not be place due to Insufficient Balance.";
			}
			else 
			{
				return "user is inactive.";
			}
	}
	
	public String createSellOrder(SellBuyTransactionDto sellBuyTransactionDto)
	{ 
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
		Double fees = coinManagement.getFees();				
		OrderTable tempOrderTable = new OrderTable();
		tempOrderTable.setUser(user);
		tempOrderTable.setCoinName((sellBuyTransactionDto.getCoinName()));
		tempOrderTable.setCoinQuantity(sellBuyTransactionDto.getCoinQuantity());
		tempOrderTable.setOrderStatus(OrderStatus.PENDING);
		tempOrderTable.setFees(fees);
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