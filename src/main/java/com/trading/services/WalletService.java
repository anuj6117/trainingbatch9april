package com.trading.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trading.domain.User;
import com.trading.domain.Wallet;
import com.trading.dto.UserWalletDto;
import com.trading.repository.UserRepository;
import com.trading.repository.WalletRepository;

@Service
public class WalletService {

@Autowired 
private WalletRepository walletrepository;

@Autowired
private UserRepository userrepository;

	public String insertWallet(UserWalletDto userwalletdto)
	{
		
		User user= userrepository.findOneByUserId(userwalletdto.getUserId());
		Wallet walletdb = walletrepository.findByWalletTypeAndUser(userwalletdto.getwalletType(), user);
			if(user != null && walletdb == null)
				{
					Wallet wallet = new Wallet();
					wallet.setwalletType(userwalletdto.getwalletType());
					wallet.setuser(user);
					walletrepository.save(wallet);
					return "New wallet has been added and assigned";
				}
				
			else 
			{
				return " Failed to add new wallet";
			}
	}
	public String depositAmount(UserWalletDto userwalletdto)
	{
		Wallet wallet = new Wallet();
		User user = userrepository.findOneByUserId(userwalletdto.getUserId());
		wallet = walletrepository.findByWalletTypeAndUser(userwalletdto.getwalletType(), user);
		if(wallet !=null)	{
			wallet.setBalance(userwalletdto.getamount() + wallet.getBalance());
			walletrepository.save(wallet);
			return "Success";
		}
		else 
		{
			return "Failure";
		}
	} 
	public String withdrawAmount(UserWalletDto userwalletdto)
	{ 
		Wallet wallet = new Wallet();
		User user = userrepository.findOneByUserId(userwalletdto.getUserId());
		wallet = walletrepository.findByWalletTypeAndUser(userwalletdto.getwalletType(), user);
		if(wallet.getBalance()== 0)
		{
			return " No Balance in wallet ";
		}
		if(wallet !=null)	{
	
			if(userwalletdto.getamount() >= wallet.getBalance()) {
				wallet.setBalance(userwalletdto.getamount() - wallet.getBalance());
				walletrepository.save(wallet);
			}
			else 
			{
				wallet.setBalance(wallet.getBalance() - userwalletdto.getamount() );
				walletrepository.save(wallet);
			}
			return "Success";
		}
		else
		{
			return "Failure";
		} 
	}
}