package com.training.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.training.demo.model.CoinManagement;

public interface CoinManagementRepository extends JpaRepository<CoinManagement,Integer> {
	
	public CoinManagement findOneByCoinId(Integer coinId);
	
	public CoinManagement findOneByCoinName(String coinName);
	
	public CoinManagement findAllByCoinName(String coinName);

	public CoinManagement findByCoinName(String coinName);
	
	boolean existsByCoinName(String coinName);
}