package com.trainingproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trainingproject.domain.User;
import com.trainingproject.domain.Wallet;
import com.trainingproject.enums.CoinType;

@Repository
public interface WalletRepository extends JpaRepository<Wallet,Integer>{

	Wallet findByBalance(Integer amount);
	
	Wallet findByCoinTypeAndUser(CoinType coinType, User user);

	Wallet findByCoinType(CoinType coinType);

	Wallet findByCoinTypeAndCoinNameAndUser(CoinType coinType, String coinName, User user);

	Wallet findByCoinNameAndUser(String coinName, User user);

}
