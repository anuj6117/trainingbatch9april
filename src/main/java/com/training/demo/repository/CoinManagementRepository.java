package com.training.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.training.demo.model.CoinManagement;
import com.training.demo.model.User;

public interface CoinManagementRepository extends JpaRepository<CoinManagement, Integer> {

	public CoinManagement findOneByCoinId(long coinId);

	public CoinManagement findByCoinName(String coinName);

	public CoinManagement findBySymbol(String symbol);

	public CoinManagement findBycoinName(String coinName);

	public CoinManagement findOneByCoinName(String coinName);

//	public void deleteByCoinId(Integer coinId);
	//public CoinManagement findByCoinNameAndSymbol(String coinName, String sm);

}