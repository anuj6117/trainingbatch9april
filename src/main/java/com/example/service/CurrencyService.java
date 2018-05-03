package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Currency;
import com.example.repository.CurrencyRepository;

@Service
public class CurrencyService
{
    @Autowired
	private CurrencyRepository currencyrepository;
    
	public String addCurrency(Currency currency)
	{
		System.out.println("...................................");
		currency.setCoinInINR(0);
		currency.setProfit(0);
		  currencyrepository.save(currency);
		  return "Your coin has been added successfully";
	  	
	  
	}
	public String updatecurrency(Currency currency)
	{
	  if(!(currencyrepository.save(currency)==null))
	  {
		  return "update";
	  }	
	  else
		  return "not";
	}
	
}
