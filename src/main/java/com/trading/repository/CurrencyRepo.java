package com.trading.repository;

import org.springframework.data.repository.CrudRepository;

import com.trading.domain.Currency;

public interface CurrencyRepo extends CrudRepository<Currency, Long> {
	
	public Currency findBycoinName(String coinName);

}
