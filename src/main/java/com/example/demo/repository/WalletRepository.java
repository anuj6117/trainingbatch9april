package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.enums.CoinType;
import com.example.demo.model.User;
import com.example.demo.model.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, Integer> {

	public Wallet findByCoinType(CoinType coinType);
	public Wallet findOneById(Integer id);
	public Wallet findByCoinTypeAndUser(CoinType coinType, User user);
	public Wallet findByCoinNameAndUser(String coinName, User user);

}
