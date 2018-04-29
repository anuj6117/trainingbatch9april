package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.CurrencyModel;


@Repository
public interface CurrencyRepository extends JpaRepository<CurrencyModel, Integer>{

	public CurrencyModel findOneByCoinName(String coinName);
	public CurrencyModel findOneByCoinId(Integer coinId);
	public CurrencyModel findOneBySymbol(String symbol);
	public Optional<CurrencyModel> findByCoinName(String coinName);
	public Optional<CurrencyModel> findBySymbol(String symbol);
	

}
