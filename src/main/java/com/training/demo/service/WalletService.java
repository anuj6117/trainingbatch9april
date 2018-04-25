package com.training.demo.service;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.training.demo.dto.UserTransactionDto;
import com.training.demo.dto.WalletDto;
import com.training.demo.enums.OrderStatus;
import com.training.demo.enums.OrderType;
import com.training.demo.enums.WalletType;
import com.training.demo.model.User;
import com.training.demo.model.Wallet;
import com.training.demo.repository.UserRepository;
import com.training.demo.repository.WalletRepository;

@Service
public class WalletService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private WalletRepository walletRepository;

	public String addWalletToUser(WalletDto walletDto) {
		
		User user = userRepository.findByUserId(walletDto.getUserId());
		Wallet wallet = new Wallet();
		wallet.setWalletType(WalletType.valueOf(walletDto.getWalletType()));
		wallet.setUser(user);
		wallet.setBalance(0.0);
		wallet.setShadowBalance(0.0);
		walletRepository.save(wallet);
		return "success";
	}
	public String depositAmount(UserWalletDto userwalletdto) {
		User user = userRepository.findOneByUserId(userwalletdto.getuserId());
		UserOrder userOrder = new UserOrder();
			
		userOrder.setOrderType(OrderType.DEPOSIT);
		userOrder.setCoinName(WalletType.FIAT);
		userOrder.setPrice(userwalletdto.getamount());
		userOrder.setOrderCreatedOn(new Date());
		userOrder.setStatus(OrderStatus.PENDING);
		userOrder.setUser(user);
		orderRepository.save(userOrder); 
		;
		if (userOrder.getStatus() == OrderStatus.COMPLETE) {
			
			return "Amount added";
		} else {
			return "Status Pending";
		}
}
	public void toDepositAmount(UserTransactionDto utd) {
		Integer userId = utd.getUserId();
		String walletType = utd.getWalletType();
		Double amount = utd.getAmount();
		
		User user = userRepository.findByUserId(userId);
		List<Wallet> wallet = user.getWallets();
		Iterator itr = wallet.iterator();
		while(itr.hasNext()) {
			Wallet tempWallet = (Wallet)itr.next();

			 if( tempWallet == walletRepository.findByWalletType(WalletType.valueOf(walletType))) {
				Double totalAmount = tempWallet.getBalance()+amount;
				 tempWallet.setBalance(totalAmount);
				userRepository.save(user);
			}
		}
		}
			
	public String toWithdrawn(UserTransactionDto utd ) {
		 Integer userId = utd.getUserId();
		 String walletType = utd.getWalletType();
		 Double amount = utd.getAmount();		 

		 User user = userRepository.findByUserId(userId);
		 List<Wallet> wallet = user.getWallets();
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
