package com.training.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.training.demo.dto.UserDepositDto;
import com.training.demo.dto.UserWalletDto;
import com.training.demo.enums.WalletType;
import com.training.demo.model.User;
import com.training.demo.model.Wallet;
import com.training.demo.repository.UserRepository;
import com.training.demo.repository.WalletRepository;

@Service
public class WalletService {

	@Autowired
	private WalletRepository walletRepository;

	@Autowired
	private WalletService walletService;
	@Autowired
	private UserRepository userrepository;

	public String addWallet(UserWalletDto userWalletdto) {
		System.out.println(userWalletdto.getUserId());
		User user = userrepository.findByUserId(userWalletdto.getUserId());
		if (user != null) {
			Wallet wallet = new Wallet();
			wallet.setWalletType(WalletType.valueOf(userWalletdto.getWalletType()));
			wallet.setUser(user);
			walletRepository.save(wallet);
			return "wallet add sucees";

		} else {
			return "failed to add new wallet";
		}

	}

	public String depositAmount(UserDepositDto userdepositdto) {
		Wallet wallet = new Wallet();
		User user = userrepository.findByUserId(userdepositdto.getUserId());
		wallet = walletRepository.findByWalletType(userdepositdto.getWalletType());
		if (wallet != null) {
			long moneyBalance = userdepositdto.getAmount() + wallet.getBalance();
			wallet.setBalance(moneyBalance);
			wallet.setShadowBalance(moneyBalance);
			walletRepository.save(wallet);
			return "sucess";
		} else {
			return "failue";
		}

	}

	public String withDrawAmount(UserDepositDto userdepositdto) {
		Wallet wallet = new Wallet();
		User user = userrepository.findByUserId(userdepositdto.getUserId());
		wallet = walletRepository.findByWalletType(userdepositdto.getWalletType());
		if ((wallet != null) &&(userdepositdto.getAmount()<wallet.getBalance()))
				{
			long moneyBalance = wallet.getBalance() - userdepositdto.getAmount();
			wallet.setBalance(moneyBalance);
			wallet.setShadowBalance(moneyBalance);
			walletRepository.save(wallet);
			return "sucess";
		} else {
			return "failue";
		}

	}

}
