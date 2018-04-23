package com.trainingproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trainingproject.domain.Wallet;
import com.trainingproject.repository.WalletRepository;

@Service
public class WalletService {

	@Autowired
	WalletRepository walletRepository;
	
	
	public Wallet createWallet(Wallet wallet) {
		Wallet createdWallet=walletRepository.save(wallet);
		return createdWallet;
	}
}
