package com.trading.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trading.domain.Currency;
import com.trading.repository.CurrencyRepository;

@Service
public class CurrencyService {
	
	
	
@Autowired
private CurrencyRepository coinmanagementrepository;



public String insertDetails(Currency coinmanagement)
{
	if(coinmanagement.getCoinName()==null)
	{
		return "Coin Name Cant be Null";
	}
	
	if(coinmanagementrepository.findBycoinName(coinmanagement.getCoinName())==null) {
		
	
	if(coinmanagementrepository.save(coinmanagement)!= null) {
		return "Your coin has been added";
		
	}
	else {
		return "Failed to add coinmanagement";
	}}
	else {
		return "coinmanagement Type Already Exists";
	}
}

public Iterable <Currency> getDetails(){
	
	return coinmanagementrepository.findAll();
}


public Currency updateDetails(Currency coinmanagement)
{
	
	return coinmanagementrepository.save(coinmanagement);
}

public String deleteById(long coinId)
{
	coinmanagementrepository.deleteById(coinId);
	return "Deleted";
}
}


