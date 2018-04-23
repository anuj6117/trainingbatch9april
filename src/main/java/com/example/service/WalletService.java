package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dto.UserWalletDto;
import com.example.model.User;
import com.example.model.Wallet;
import com.example.repository.UserRepository;
import com.example.repository.WalletRepository;

@Service
public class WalletService
{   @Autowired
	private UserRepository userrepository;
	private User user;
	@Autowired
	private WalletRepository walletrepository;
	//private UserWalletDto userwalletdto;
	
 public String addWallet(UserWalletDto userwalletdto)
 {
	 user=userrepository.findByUserId(userwalletdto.getUserId());
	 Wallet wallet=new Wallet();
	 wallet.setWalletType(userwalletdto.getWalletType());
	 wallet.setUser(user);
	 walletrepository.save(wallet);
	 return "wallet added in service";
 }
 public String depositamount(UserWalletDto userwalletdto)
 {
	 user=userrepository.findByUserId(userwalletdto.getUserId());
	(!(userrepository.findByWalletType(userwalletdto.getWalletType())==null) )
			{
		
			}
	 /* (walletrepository.findByWalletType(userwalletdto.getWalletType())!=null)
	 {
		 
	 }*/
	 return "";
 }
}
