package com.trainingproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trainingproject.domain.Currency;
import com.trainingproject.repository.CurrencyRepository;

@Service
public class CurrencyService {

	@Autowired
	CurrencyRepository currencyRepository;
	
	
	
	public String addCurrency(Currency cur) {
		if(cur.getCoinName()==null||cur.getCoinName().length()==0)
			return "coin name cannot be null";
		if(cur.getSymbol()==null)
			return "symbol cannot be null";
		if(cur.getInitialSupply()==null)
			return "initial supply cannot be null";
		if(cur.getPrice()==null)
			return "price cannot be null";
		if(currencyRepository.findBycoinName(cur.getCoinName())!=null)
			return "coin name already exists!";
		if(currencyRepository.findBysymbol(cur.getSymbol())!=null)
			return "symbol already exists!";
		cur.setFees(cur.getFees());
		cur.setCoinInINR(cur.getCoinInINR());
		currencyRepository.save(cur);
		return "success";
	}

	public List<Currency> getAllCurrency() {
	  return currencyRepository.findAll();
		
	}

	public String updateCurrency(Currency cur) {
		
		if(cur.getCoinName()==null||cur.getCoinName().length()==0)
			return "coin name cannot be null";
		if(cur.getSymbol()==null)
			return "symbol cannot be null";
		if(cur.getInitialSupply()==null)
			return "initial supply cannot be null";
		if(cur.getPrice()==null)
			return "price cannot be null";
		if(currencyRepository.findBycoinName(cur.getCoinName())!=null)
			return "coin name already exists!";
		if(currencyRepository.findBysymbol(cur.getSymbol())!=null)
			return "symbol already exists!";
		
		 Currency currency=currencyRepository.findBycoinName(cur.getCoinName());
		  currency.setInitialSupply(currency.getInitialSupply()+cur.getInitialSupply());
		  currency.setFees(cur.getFees());
		  currency.setSymbol(cur.getSymbol());
		  currency.setCoinName(cur.getCoinName());
		 currencyRepository.save(currency);
		 return "success";
		
	}

	public void deleteCurrency(Integer coinId) {
		currencyRepository.deleteById(coinId);
	}

	public List<Currency> getCurrencyById(Integer coinId) {
		currencyRepository.findById(coinId);
		return null;
	}
}
