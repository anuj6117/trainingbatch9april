package com.trading.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trading.domain.Wallet;
import com.trading.repository.WalletRepo;

@Service
public class WalletService {

@Autowired 
private WalletRepo walletrepo;

public String insertWallet(Wallet wallet)
{
	if(walletrepo.save(wallet)!= null)
	{
	
		return "New wallet has been added";
		
	}
	else {
		
		return " Failed to add new wallet";
		 }
}
}
