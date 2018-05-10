package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.enums.WalletType;
import com.example.demo.model.Order;
import com.example.demo.model.User;
import com.example.demo.model.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, Integer> {
	public Wallet findByWalletType(WalletType walletEnum);

	public User findByUser(User user);

	public Wallet findByUserAndCoinName(User user, String coinName);
	
	public List<Order> findOrderByUserAndCoinName(User user, String CoinName);

}
