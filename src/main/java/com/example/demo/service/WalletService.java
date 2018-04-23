package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		/*Wallet wallet = walletRepository.findById(walletDTO.getUserId()).get();*/
		Wallet wallet = new Wallet();
		wallet.setWalletType(WalletEnum.valueOf(walletDTO.getWalletType()));
		wallet.setUser(user);
		wallet.setBalance(0.0);
		wallet.setShadowBalance(0.0);
		walletRepository.save(wallet);
		return "success";
		
		
		/*System.out.println("From service "+walletDTO.getUserId());
		if (user == null) {
			throw new NullPointerException("User id not found");
		}
		List<Wallet> walletType = user.getWallets();
		System.out.println(walletType);
		for (Wallet getWalletType : walletType) {
			if (walletDTO.getWalletType().equals(getWalletType.getWalletType())) {
				b = false;
				throw new RuntimeException("Wallet Already exist");
			}
		}
		if (b) {
			Wallet wallet1 = new Wallet();
			wallet1.setBalance(walletDTO.getBalance());
			// wallet1.setWalletType(walletDto.getWalletType());
			wallet1.setUser(user);
			 wallet.setRandomId(randemId); 
			user.getWallets().add(wallet1);
			User result = userRepository.save(user);
			if (result != null)
				return "success";
		}*/
		
	}

	/*
	 * public String addWalletToUser(WalletDTO walletDTO) {
	 * System.out.println(walletDTO.getUserId());
	 * 
	 * User user = userRepository.findByUserId(walletDTO.getUserId());
	 * System.out.println(user); if (user != null) {
	 * System.out.println(user.getWallets()); List<Wallet> walletTypeUser =
	 * user.getWallets();
	 * 
	 * }
	 * 
	 * return "Wallet Service"; }
	 */
}
