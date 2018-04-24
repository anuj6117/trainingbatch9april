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
		
	public CurrencyClass addCurrency(CurrencyClass currency)
		{   
		
			CurrencyClass currencyCreated=currencyRepository.save(currency);
			return currencyCreated;
					
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
	public void deleteCurrency(Integer id)
	{
		currencyRepository.deleteById(id);
	}
	public CurrencyClass updateCurrency(CurrencyClass currency) {
		return currencyRepository.save(currency);
	}

}
