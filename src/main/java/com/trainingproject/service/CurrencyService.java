package com.trainingproject.service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trainingproject.domain.Currency;
import com.trainingproject.enums.CoinType;
import com.trainingproject.repository.CurrencyRepository;

@Service
public class CurrencyService {

	@Autowired
	CurrencyRepository currencyRepository;
	
	
	
	public String addCurrency(Currency cur) {
		
		if(cur.getCoinName()==null||cur.getCoinName().length()==0)
			return "coin name cannot be null";
		if(cur.getCoinType()==null)
			return "coin type cannot be null";
		if(cur.getSymbol()==null)
			return "symbol cannot be null";
		if(cur.getInitialSupply()==0)
			return "initial supply cannot be null";
		if(cur.getPrice()==0)
			return "price cannot be null";
		if(currencyRepository.findBycoinName(cur.getCoinName())!=null)
			return "coin name already exists!";
		if(currencyRepository.findBysymbol(cur.getSymbol())!=null)
			return "symbol already exists!";
		
	
		Pattern p = Pattern.compile("^[a-zA-Z]{1,}$", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(cur.getCoinName());
		
		 if(!m.find())
			 return "invalid coin name";
		 
		
		if(cur.getCoinType().equals(CoinType.CRYPTO)||cur.getCoinType().equals(CoinType.FIAT)) {

		currencyRepository.save(cur);
		return "success";
		}
		else return "invalid cointype";
	}

	public List<Currency> getAllCurrency() {
	  return currencyRepository.findAll();
		
	}

	public String updateCurrency(Currency cur) {
		
		if(cur.getCoinName()==null||cur.getCoinName().length()==0)
			return "coin name cannot be null";
		if(cur.getCoinType()==null)
			return "coin type cannot be null";
		if(cur.getSymbol()==null)
			return "symbol cannot be null";
		if(cur.getInitialSupply()==0)
			return "initial supply cannot be null";
		if(cur.getPrice()==0)
			return "price cannot be null";
		if(currencyRepository.findBycoinName(cur.getCoinName())!=null)
			return "coin name already exists!";
		if(currencyRepository.findBysymbol(cur.getSymbol())!=null)
			return "symbol already exists!";
		
		Pattern p = Pattern.compile("^[a-zA-Z]{1,}$", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(cur.getCoinName());
		
		 if(!m.find())
			 return "invalid coin name";
		
		if(cur.getCoinType().equals(CoinType.CRYPTO)||cur.getCoinType().equals(CoinType.FIAT)) {
		 Currency currency=getCurrencyById(cur.getCoinId()).get();
		 if(currency==null)
			 return "currency not found";
		  currency.setInitialSupply(currency.getInitialSupply()+cur.getInitialSupply());
		  currency.setFee(cur.getFee());
		  currency.setSymbol(cur.getSymbol());
		  currency.setCoinName(cur.getCoinName());
		  currency.setCoinType(cur.getCoinType());
		  currency.setPrice(cur.getPrice());
		  currency.setCoinInINR(cur.getCoinInINR());
		 currencyRepository.save(currency);
		 return "your coin has been updated successfully";
		}
		else return "invalid coin type";
		
	}

	public String deleteCurrency(Integer coinId) {
		
		Currency coin=currencyRepository.findById(coinId).get();
		if(coin==null)
			return "no currency to delete";
		currencyRepository.deleteById(coinId);
		return "success";
	}

	public Optional<Currency> getCurrencyById(Integer coinId) {
		return currencyRepository.findById(coinId);
		
	}
}
