package com.training.demo.service;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.training.demo.dto.OrderDto;
import com.training.demo.dto.WalletDto;
import com.training.demo.enums.OrderStatus;
import com.training.demo.enums.OrderType;
import com.training.demo.enums.UserStatus;
import com.training.demo.enums.WalletType;
import com.training.demo.model.CoinManagement;
import com.training.demo.model.OrderTable;
import com.training.demo.model.User;
import com.training.demo.model.Wallet;
import com.training.demo.repository.CoinManagementRepository;
import com.training.demo.repository.OrderRepository;
import com.training.demo.repository.UserRepository;
import com.training.demo.repository.WalletRepository;

@Service
public class WalletService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private WalletRepository walletRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private CoinManagementRepository coinRepository;
	
	public String addWallet(WalletDto walletDto) {
		boolean flag = false;
		User user;
		System.out.println(walletDto.getCoinName()+", \t"+walletDto.getUserId()+",\t"+walletDto.getCoinType());
		
		//walletDto.getWalletType().toUpperCase();
		
		if((user = userRepository.findByUserId(walletDto.getUserId())) == null)
		{
			return "Invalid User Id";
		}
		
		if(!(walletDto.getCoinType().equalsIgnoreCase(WalletType.CRYPTO.toString())))
		{
			return "Invalid Coin Type.";
		}	
			
		String tempCoinName = walletDto.getCoinName();
		
		if(tempCoinName == null || tempCoinName.equals(""))
		{
			return "Please Enter Coin Name.";
		}
		
			if(!coinRepository.existsByCoinName(tempCoinName))
			{
				return "Invalid Coin Name";
			}
			
		if(user.getUserStatus().equals(UserStatus.ACTIVE))
		{
		Set<Wallet> walletSet = user.getWallets();
		Iterator<Wallet> walletIterator = walletSet.iterator();
		while(walletIterator.hasNext())
		{
			Wallet tempwallet = walletIterator.next();
				System.out.println(tempwallet.getCoinName()+",\t"+tempwallet.getCoinType()+",\t"+tempwallet.getWalletId()+",\t"+tempwallet.getUser());
			if(tempwallet.getCoinName() != null)	
			{
				if(tempwallet.getCoinType().equals(walletDto.getCoinType()) && (tempwallet.getCoinName().equals(walletDto.getCoinName())))
				{
					System.out.println(tempwallet);
					flag = true;
				}
			}
		}
		if(!flag)
		{		
			Wallet wallet = new Wallet();
			wallet.setCoinType(WalletType.valueOf(walletDto.getCoinType()));
			wallet.setUser(user);
			wallet.setBalance(0.0);
			wallet.setShadowBalance(0.0);
			wallet.setCoinName(walletDto.getCoinName());
			walletRepository.save(wallet);
			return "Wallet is successfully created.";
		}
		else {
			return "Existing wallet type for the given coin name.";
		}
		}
		else
		{
			return "Please activate your account first and than only you can create wallet.";
		}
	}
	
	//DepositConfigurationssssss
	
	public String depositAmount(OrderDto orderDto) {
		User user ;
		
		try {
		user = userRepository.findByUserId(orderDto.getUserId());
		}
		catch(Exception e)
		{
			return "invalid user id.";
		}
		
		if(user.getUserStatus().equals(UserStatus.ACTIVE))
		{
			OrderTable tempOrderTable = new OrderTable();
			tempOrderTable.setOrderType(OrderType.DEPOSIT);
			tempOrderTable.setCoinName(orderDto.getCoinName());
			tempOrderTable.setNetAmount(orderDto.getAmount());
			tempOrderTable.setCoinQuantity(orderDto.getAmount());
			tempOrderTable.setFees(0d);
			tempOrderTable.setPrice(0d);
			tempOrderTable.setGrossAmount(orderDto.getAmount());
			tempOrderTable.setOrderCreatedOn(new Date());
			tempOrderTable.setOrderStatus(OrderStatus.PENDING);
			tempOrderTable.setUser(user);
			orderRepository.save(tempOrderTable); 
			return "Your deposit request is generated. Please wait for approval";
		}
		else
		{
			return "User can not deposit until his/her status is not active.";
		}
	}
			
	public String toWithdrawn(OrderDto orderDto ) {
		 Integer userId = orderDto.getUserId();
		 String walletType = orderDto.getWalletType();
		 Double amount = orderDto.getAmount();		 

		 User user = userRepository.findByUserId(userId);
		 Set<Wallet> wallet = user.getWallets();
		 Iterator<Wallet> itr = wallet.iterator();
		 while(itr.hasNext()) {
			Wallet tempWallet = itr.next();
			 if(tempWallet == walletRepository.findByCoinType(WalletType.valueOf(walletType))) {
				 Double availBalance = tempWallet.getBalance();
				 
				 if(amount <= availBalance)
				 {
					 Double totalAmount = tempWallet.getBalance() - amount;
					 tempWallet.setBalance(totalAmount);
					 Double shadowBalance = tempWallet.getShadowBalance() - amount;
					 tempWallet.setShadowBalance(shadowBalance);
					 userRepository.save(user);					
				 }
				 else
				 {
					 return "Sorry insufficient balance.";
				 }
			 }
		 }
		 return "Successfully withdrawn.";
	 }

	public Object showWalletHistoryByUserIdAndCoinName(Integer userId, String coinName){
		
		try {
			User user = userRepository.findByUserId(userId);
			if(user == null)
			{
				return "Invalid User Name";
			}
		}
		catch(Exception e) {
			System.out.println("user does not exist with the given id........... showWalletHistory.");
			}
		
		try {
			CoinManagement cm= coinRepository.findAllByCoinName(coinName);
			if(cm == null)
			{
				return "Invalid Coin Name";
			}
		}
		catch(Exception e) {
			System.out.println("coinName does not exist with the given coinName........... showWalletHistory.");
			}
				
		List<OrderTable> userWalletHistory = orderRepository.getWalletHistory(userId,coinName);
		if(userWalletHistory.isEmpty())
		{
			return "There is no any wallet history for the given user id and coin name.";
		}
		return userWalletHistory;
	}
}