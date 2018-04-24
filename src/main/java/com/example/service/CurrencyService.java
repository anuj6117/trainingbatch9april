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
	  if(!(currencyrepository.save(currency)==null))
	  {
		  return "saved";
	  }	
	  else
		  return "not saved, its null";
	}
	/*public String updatecurrency(Currency currency)
	{
	  if(!(currencyrepository.save(currency)==null))
	  {
		  return "update";
	  }	
	  else
		  return "not";
	}
*/	
}
