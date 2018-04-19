package com.trading.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trading.domain.Currency;
import com.trading.repository.CurrencyRepo;

@Service
public class CurrencyService {
	
	
	
@Autowired
private CurrencyRepo coinmanagementrepo;



public String insertDetails(Currency coinmanagement)
{
	if(coinmanagement.getCoinName()==null)
	{
		return "Coin Name Cant be Null";
	}
	
	if(coinmanagementrepo.findBycoinName(coinmanagement.getCoinName())==null) {
		
	
	if(coinmanagementrepo.save(coinmanagement)!= null) {
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
	
	return coinmanagementrepo.findAll();
}


public Currency updateDetails(Currency coinmanagement)
{
	
	return coinmanagementrepo.save(coinmanagement);
}

public String deleteById(long coinId)
{
	coinmanagementrepo.deleteById(coinId);
	return "Deleted";
}
}


