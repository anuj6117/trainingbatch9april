package io.oodles.springboot1.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.oodles.springboot1.enums.WalletType;
import io.oodles.springboot1.model.AddWallet;
import io.oodles.springboot1.model.Deposit;
import io.oodles.springboot1.model.Users;
import io.oodles.springboot1.model.Wallet;
import io.oodles.springboot1.repository.UsersRepository;
import io.oodles.springboot1.repository.WalletRepository;

@Service
public class WalletService {
	@Autowired
	WalletRepository walletRepository;
	//Wallet wallet=new Wallet();
	Users user;
	WalletType walletType;
	
	@Autowired
	UsersRepository usersRepository;

	public List<Wallet> getallwallet() {
		// TODO Auto-generated method stub
		return walletRepository.findAll();
	} 
	
	public Optional<Wallet> searchbyid(int id) {
		// TODO Auto-generated method stub
		return walletRepository.findById(id);

	}

	public Wallet update(Wallet wallet) {
		// TODO Auto-generated method stub
		return walletRepository.save(wallet);
	}

	public void delete(int id) {
		// TODO Auto-generated method stub
		
		walletRepository.deleteById(id);
	}

	public Users add(AddWallet addwallet) {
		// TODO Auto-generated method stub
		user=usersRepository.findByUserid(addwallet.getUserid());
		Set<Wallet> walletsets=new HashSet<Wallet>();
		Wallet wallet=new Wallet();
		wallet.setUsers(user);
		
		wallet.setWallet(addwallet.getWalletType());
		
		walletsets.add(wallet);
		
		walletRepository.save(wallet);
		user.setWallet(walletsets);
		System.out.println("/////////////////"+wallet.getWallet());
		if(user!=null && wallet!=null) {
		user.getWallet().add(wallet);
	
		usersRepository.save(user);}
		return user;
		
	}

	public Wallet newwallet1(Wallet wallet) {
		// TODO Auto-generated method stub
		return walletRepository.save(wallet);
		
	}

	public Users deposit(Deposit deposit) {
		// TODO Auto-generated method stub
		user=usersRepository.findByUserid(deposit.getUserid());
		Wallet wallet=walletRepository.findByWallet((deposit.getWalletType()));
		Set<Wallet> walletset=new HashSet<Wallet>();
		
        wallet.setUsers(user);
		wallet.setWallet(deposit.getWalletType());
		
		Integer newlabalnce=wallet.getBalance()+deposit.getBalance();
		wallet.setBalance(newlabalnce);
		wallet.setShadowbalance(newlabalnce);
		walletset.add(wallet);
		
		walletRepository.save(wallet);
		user.setWallet(walletset);
		user.getWallet().add(wallet);
	
		usersRepository.save(user);
		return user;
	
	}

}
