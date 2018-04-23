package com.trading.repository;

import org.springframework.data.repository.CrudRepository;

import com.trading.domain.Currency;

public interface CurrencyRepository extends CrudRepository<Currency, Long> {
	
	public Currency findBycoinName(String coinName);

	public Currency findOneByCoinId(long coinId);

}
