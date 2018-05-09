package com.example.demo.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.CurrencyClass;
import com.example.demo.repository.CurrencyRepository;

@Service
public class CurrencyService {
	@Autowired
	  CurrencyRepository currencyRepository;
     CurrencyClass currency;
		
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
		List<CurrencyClass> currencyclass=currencyRepository.findAll();
		Iterator<CurrencyClass>itr=currencyclass.iterator();
	   while(itr.hasNext())
	   {
		   if(itr.next().getCoinId()==id)
		   {
			   currencyRepository.deleteById(id);
			   return "Your Coin has been deleted Successfully";
			   
		   }
	   }
	   return "Invalid Coin id";
	}
	public String updateCurrency(CurrencyClass currency) {
		if(currency!=null)
		{
			CurrencyClass tempcurrency=currencyRepository.findByCoinId(currency.getCoinId());
			tempcurrency.setCoinName(currency.getCoinName());
			tempcurrency.setInitialSupply(currency.getInitialSupply());
			tempcurrency.setFees(currency.getFees());
			tempcurrency.setSymbol(currency.getSymbol());
			tempcurrency.setPrice(currency.getPrice());
			currencyRepository.save(tempcurrency);
			return "Your Coin Has been Updated successfully";
		}
		 return "Coin Not present";
		 
	}

}
