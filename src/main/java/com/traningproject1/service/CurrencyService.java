package com.traningproject1.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.traningproject1.domain.CurrencyClass;
import com.traningproject1.repository.CurrencyRepository;

@Service
public class CurrencyService {
	@Autowired
	 private CurrencyRepository currencyRepository;
	
//	@Autowired
//	 private CurrencyClass currency;
		
	public String addCurrency(CurrencyClass currency)
		{   
			Integer coinininr=currency.getPrice()*currency.getInitialSupply();
			currency.setCoinInINR(coinininr);
			currencyRepository.save(currency);
			return "Your Coin has been Added Successfully";
					
		}
	public ArrayList<CurrencyClass> getAllCurrency()
	{
		ArrayList<CurrencyClass> list=new ArrayList<>();
		currencyRepository.findAll()
		.forEach(list::add);
		return list;
	}
	public Optional<CurrencyClass> getByUserId(Integer id)
	{
	  Optional<CurrencyClass> currencyGet=currencyRepository.findById(id);
	  return currencyGet;
	}
	public String deleteCurrency(Integer id)
	{
		currencyRepository.deleteById(id);
		return "Your Coin has been deleted Successfully";
	}
	public String updateCurrency(CurrencyClass currency) {
		
		 currencyRepository.save(currency);
		 return "Your Coin Has been Updated successfully";
	}

}
