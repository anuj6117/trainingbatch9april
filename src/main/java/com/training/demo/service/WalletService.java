package com.training.demo.service;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.training.demo.dto.OrderDto;
import com.training.demo.dto.UserTransactionDto;
import com.training.demo.dto.WalletDto;
import com.training.demo.enums.OrderStatus;
import com.training.demo.enums.OrderType;
import com.training.demo.enums.UserStatus;
import com.training.demo.enums.WalletType;
import com.training.demo.model.OrderTable;
import com.training.demo.model.User;
import com.training.demo.model.Wallet;
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
	
	public String addWallet(WalletDto walletDto) {
		boolean flag = false;
		User user = userRepository.findByUserId(walletDto.getUserId());
		Set walletSet = user.getWallets();
		Iterator walletIterator = walletSet.iterator();
		while(walletIterator.hasNext())
		{
			Wallet tempwallet = (Wallet)walletIterator.next();
			if(tempwallet.getWalletType().equals(walletDto.getWalletType()) && tempwallet.getCoinName().equals(walletDto.getCoinName()))
			{
				flag = true;
			}
		}
		if(!flag)
		{		
			Wallet wallet = new Wallet();
			wallet.setWalletType(WalletType.valueOf(walletDto.getWalletType()));
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
	public String depositAmount(OrderDto orderDto) {
		User user = userRepository.findByUserId(orderDto.getUserId());
		if(user.getUserStatus().equals(UserStatus.ACTIVE))
		{
			OrderTable tempOrderTable = new OrderTable();
			tempOrderTable.setOrderType(OrderType.DEPOSIT);
			tempOrderTable.setCoinName(orderDto.getCoinName());
			tempOrderTable.setAmount(orderDto.getAmount());
			tempOrderTable.setCoinQuantity(orderDto.getAmount());
			tempOrderTable.setGrossAmount(orderDto.getAmount());
			tempOrderTable.setOrderCreatedOn(new Date());
			tempOrderTable.setOrderStatus(OrderStatus.PENDING);
			tempOrderTable.setUser(user);
			orderRepository.save(tempOrderTable); 
			return "deposit request is successfully processed, kindly wait for the admin approval.";
		}
		else
		{
			return "User can not deposit until his/her status is not active.";
		}
	}
			
	public String toWithdrawn(UserTransactionDto utd ) {
		 Integer userId = utd.getUserId();
		 String walletType = utd.getWalletType();
		 Double amount = utd.getAmount();		 

		 User user = userRepository.findByUserId(userId);
		 Set<Wallet> wallet = user.getWallets();
		 Iterator itr = wallet.iterator();
		 while(itr.hasNext()) {
			Wallet tempWallet = (Wallet) itr.next();
			 if(tempWallet == walletRepository.findByWalletType(WalletType.valueOf(walletType))) {
				 Double availBalance = tempWallet.getBalance();
				 
				 if(amount <= availBalance)
				 {
					 Double totalAmount = tempWallet.getBalance() - amount;
					 tempWallet.setBalance(totalAmount);
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
}