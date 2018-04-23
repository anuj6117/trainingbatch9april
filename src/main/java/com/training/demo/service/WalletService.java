package com.training.demo.service;
import java.util.Iterator;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.training.demo.dto.UserTransactionDto;
import com.training.demo.dto.WalletDto;
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

	public void toDepositAmount(UserTransactionDto utd) {
		Integer userId = utd.getUserId();
		String walletType = utd.getWalletType();
		Double amount = utd.getAmount();
		User user = userRepository.findByUserId(userId);
		Set<Wallet> wallet = user.getWallet();
		Iterator itr = wallet.iterator();
		while(itr.hasNext()) {
			Wallet tempWallet = (Wallet)itr.next();
			System.out.println("itr.hasNext() :::::::: "+tempWallet);
			if( tempWallet == walletRepository.findByWalletType(WalletType.valueOf(walletType))) {
				tempWallet.setBalance(utd.getAmount());
				userRepository.save(user);
			}
		}
	}
}