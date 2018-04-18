package com.trading.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trading.domain.Currency;
import com.trading.repository.CurrencyRepo;

@Service
public class CurrencyService {
	
	
	
@Autowired
private CurrencyRepo currencyrepo;



public String insertDetails(Currency currency)
{
	
	if(currencyrepo.findBycoinName(currency.getCoinName())==null) {
		
	
	if(currencyrepo.save(currency)!= null) {
		return "Succesfully added new currency";
		
	}
	else {
		return "Failed to add currency";
	}}
	else {
		return "Currency Type Already Exists";
	}
}

public Iterable <Currency> getDetails(){
	
	return currencyrepo.findAll();
}


public Currency updateDetails(Currency currency)
{
	
	return currencyrepo.save(currency);
}

public String deleteById(long coinId)
{
	currencyrepo.deleteById(coinId);
	return "Deleted";
}
}


