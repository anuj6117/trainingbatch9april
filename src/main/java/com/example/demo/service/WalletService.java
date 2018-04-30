 package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.UserWalletDTO;
import com.example.demo.enums.CoinType;
import com.example.demo.model.User;
import com.example.demo.model.Wallet;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.WalletRepository;


@Service
public class WalletService {
	
	@Autowired
	private WalletRepository walletRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public String addWallet(UserWalletDTO userWalletDto) {
		String coinName = userWalletDto.getCoinName().toLowerCase();
		User user = userRepository.findOneByUserId(userWalletDto.getUserId());
		Wallet wallet = new Wallet();
		if(user!=null) {
			for(Wallet existwallet:user.getWallet()) {
				if(existwallet.getCoinName().equals(coinName))
					return "wallet already exist";
			}
			try {
			CoinType coinType = CoinType.valueOf(userWalletDto.getCoinType());
				wallet.setCoinType(coinType);
				wallet.setCoinName(coinName);
		}catch(Exception e) {return "invalid wallet type";}
				wallet.setUser(user);
				return walletRepository.save(wallet)!=null?"success":"failure";			
		}	
		return "wallet not added";					
	}

	public List<Wallet> getAllWallet() {
		return walletRepository.findAll();
	}

}
