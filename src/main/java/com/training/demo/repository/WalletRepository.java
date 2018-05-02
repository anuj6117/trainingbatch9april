package com.training.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.training.demo.enums.WalletType;
import com.training.demo.model.User;
import com.training.demo.model.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

	Wallet findByCoinType(WalletType getCoinType);

	Wallet findByCoinTypeAndCoinNameAndUser(WalletType coinType, String coinName, User user);
	//Wallet findByCoinTypeAndCoinNameAndUser(WalletType coinType, String coinName, User user);

	Wallet findBycoinNameAndUser(String coinName, User user);

	Wallet findBycoinTypeAndUser(WalletType fiat, User user);


	//Wallet findByUser(User user, WalletType fiat);
	
	
	             // WalletType existsByWalletType(WalletType walletType);


}