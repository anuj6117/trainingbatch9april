package com.example.demo.service;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.TransactionDto;
import com.example.demo.dto.WalletDTO;
import com.example.demo.enums.WalletEnum;
import com.example.demo.model.User;
import com.example.demo.model.Wallet;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.WalletRepository;

@Service
public class WalletService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private WalletRepository walletRepository;

	public String addWalletToUser(WalletDTO walletDTO) {

		User user = userRepository.findByUserId(walletDTO.getUserId());
		/* Wallet wallet = walletRepository.findById(walletDTO.getUserId()).get(); */
		Wallet wallet = new Wallet();
		wallet.setWalletType(WalletEnum.valueOf(walletDTO.getWalletType()));
		wallet.setUser(user);
		wallet.setBalance(0.0);
		wallet.setShadowBalance(0.0);
		try {
			walletRepository.save(wallet);
		} catch (Exception e) {
			System.out.println("wallet not added==========" + e);
		}
		return "success";

	}

	public String deposit(TransactionDto transactionDto)
	{
		
		Integer userId=transactionDto.getUserId();
		Double amount=transactionDto.getAmount();
		String walletType=transactionDto.getWalletType();
		System.out.println(walletType);
		
		User user=userRepository.findByUserId(userId);
		List<Wallet> wallets=user.getWallets();
		Iterator itr=wallets.iterator();
		while(itr.hasNext())
		{
			Wallet wallet=(Wallet)itr.next();
			System.out.println(wallet+"=================================================");
			if( wallet == walletRepository.findByWalletType(WalletEnum.valueOf(walletType))) {
				Double walletBalance=wallet.getBalance();
				wallet.setBalance(transactionDto.getAmount()+walletBalance);
				userRepository.save(user);
			}
		}
		return "success";
		
	}

	public String withdraw(TransactionDto transactionDto) {
		String walletType=transactionDto.getWalletType();
		User user=userRepository.findByUserId(transactionDto.getUserId());
		List<Wallet> wallets=user.getWallets();
		Iterator itr=wallets.iterator();
		while(itr.hasNext())
		{
			Wallet wallet=(Wallet)itr.next();
			if(wallet == walletRepository.findByWalletType(WalletEnum.valueOf(walletType)))
			{
				Double walletBalance=wallet.getBalance();
				
				
				if(walletBalance >= transactionDto.getAmount())
				{
					walletBalance = walletBalance-transactionDto.getAmount();
					wallet.setBalance(walletBalance);
					
					userRepository.save(user);
				}
				else
				{
					return "insufficient balance to withdraw";
				}
				
			}
			
		}
		return "success";
		
	}
}
