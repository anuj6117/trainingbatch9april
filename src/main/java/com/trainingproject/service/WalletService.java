package com.trainingproject.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trainingproject.domain.Wallet;
import com.trainingproject.repository.WalletRepository;
@Service
public class WalletService {
	@Autowired
	private WalletRepository walletRepository;

	public void addWallet(Wallet wallet) {
		// TODO Auto-generated method stub
		walletRepository.save(wallet);
	}

	public List<Wallet> getAllWallet() {
		// TODO Auto-generated method stub
		List<Wallet> list = new ArrayList<Wallet>();
		walletRepository.findAll()
		.forEach(list::add);
		return list;	
	}

	public Optional<Wallet> getById(Integer walletId) {
		// TODO Auto-generated method stub
		return walletRepository.findById(walletId);
	}

	public void updateWallet(Wallet wallet) {
		// TODO Auto-generated method stub
		walletRepository.save(wallet);	
	}

	public void deleteWallet(Integer walletId) {
		// TODO Auto-generated method stub
		walletRepository.deleteById(walletId);
	}

}
