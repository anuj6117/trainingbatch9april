package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.CurrencyModel;


@Repository
public interface CurrencyRepository extends JpaRepository<CurrencyModel, Integer>{

	public CurrencyModel findOneByCoinName(String currency);
	public CurrencyModel findOneByCoinId(Integer currency);
	public CurrencyModel findOneBySymbol(String symbol);
	

}
