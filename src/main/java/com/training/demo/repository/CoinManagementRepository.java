package com.training.demo.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.training.demo.model.CoinManagement;

public interface CoinManagementRepository extends JpaRepository<CoinManagement,Integer> {
	
	public CoinManagement findOneByCoinId(Integer coinId);
	
	public CoinManagement findOneByCoinName(String coinName);
	
	public CoinManagement findAllByCoinName(String coinName);

	public CoinManagement findByCoinName(String coinName);
	
	public CoinManagement findBySymbol(String symbol);
/*	
	public Set<CoinManagement> findAllBySymbol(String symbol);
	*/
	boolean existsByCoinName(String coinName);
	
}