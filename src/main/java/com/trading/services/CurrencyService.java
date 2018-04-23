package com.trading.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trading.domain.Currency;
import com.trading.repository.CurrencyRepository;

@Service
public class CurrencyService {
		
@Autowired
private CurrencyRepository currencyrepository;

	public String insertDetails(Currency currency)
	{
		if(currency.getCoinName()==null)
			{
				return "Coin Name Cant be Null";
			}
	
		if(currencyrepository.findBycoinName(currency.getCoinName())==null) 
		{
			if(currencyrepository.save(currency)!= null) 
			{
				return "Your coin has been added";
			}
			else 
			{
				return "Failed to add currency";
			}		
		}
		else 
		{
		return "currency Type Already Exists";
		}
	}

	public Iterable <Currency> getDetails()
	{
		return currencyrepository.findAll();
	}
		
	public Currency updateDetails(Currency currency)
	{
		return currencyrepository.save(currency);
	}

	public String deleteById(long coinId)
	{
		currencyrepository.deleteById(coinId);
		return "Deleted";
	}
	public Currency getCurrencyById(long coinId)
	{
	return currencyrepository.findOneByCoinId(coinId);
	}
}


