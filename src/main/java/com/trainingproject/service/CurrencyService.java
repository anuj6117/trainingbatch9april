package com.trainingproject.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trainingproject.domain.Currency;
import com.trainingproject.enums.CoinType;
import com.trainingproject.repository.CurrencyRepository;
@Service
public class CurrencyService {

	@Autowired
	private CurrencyRepository currencyRepository;
	Currency currencyobj;
	
	Double initialSupply;
	public Map<String,Object> addCurrency(Currency currency) {
		// TODO Auto-generated method stub
		Map<String,Object> result = new HashMap<>();
		if ((currencyRepository.findByCoinName(currency.getCoinName()) == null)) {
		if(currency.getCoinName() != null) {
			if(currency.getCoinName().equals("")) {
				result.put("isSuccess", false);
				result.put("message", "Coin Name can't be blank.");
				//result.put("data", null);
				return result;
			}
			if(!(Pattern.compile("^[A-Za-z]{1,25}$").matcher(currency.getCoinName()).matches())) {
				result.put("isSuccess", false);
				result.put("message", "Maximum character allowed for this field is 25.");
				return result;
			}
		}
		   else {
			result.put("isSuccess", false);
			result.put("message", "Coin Name can not be null");
			return result;
		}
		if(currency.getCoinType() != null) {
			if(!(currency.getCoinType().equals(CoinType.CRYPTO))) {
				result.put("isSuccess", false);
				result.put("message", "Coin Type not correct.");
				//result.put("data", null);
				return result;
			}
		}
		   else {
			result.put("isSuccess", false);
			result.put("message", "Coin Name can not be null");
			return result;
		  }
		if((currencyRepository.findBySymbol(currency.getSymbol()) == null)) {
		if(currency.getSymbol() != null) {
			if(currency.getSymbol().equals("")) {
				result.put("isSuccess", false);
				result.put("message", "Symbol can't be blank.");
				//result.put("data", null);
				return result;
			}
		}
		   else {
			result.put("isSuccess", false);
			result.put("message", "Symbol can not be null");
			return result;
		    }
	    }	
		else {
			result.put("isSuccess", false);
			result.put("message", "Symbol Already Exist");
			return result;
		}
		if(currency.getFees() == null) {
			result.put("isSuccess", false);
			result.put("message", "Fees can't be null.");
			return result;
		}
		if(currency.getInitialSupply() == null) {
			result.put("isSuccess", false);
			result.put("message", "Initial Supply can't be null.");
			return result;
		}
		if(currency.getPrice() == null) {
			result.put("isSuccess", false);
			result.put("message", "Price can't be null.");
			return result;
		}
			currencyRepository.save(currency);
		if(!(currencyRepository.save(currency) == null)) {
			result.put("isSuccess", true);
			result.put("message", "Your Coin Has Been Added Successfully.");
			return result;
		}    
	  }
		else {
			result.put("isSuccess", false);
			result.put("message", "Coin Name or Symbol Already Exist");
			return result;
		}
		return result;
   }

	public List<Currency> getAllCurrency() {
		// TODO Auto-generated method stub
		List<Currency> list = new ArrayList<Currency>();
		currencyRepository.findAll()
		.forEach(list::add);
		return list;
	}

	public Optional<Currency> getById(Integer coinId) {
		// TODO Auto-generated method stub
		return currencyRepository.findById(coinId);
	}

	public void updateCurrency(Currency currency) {
		// TODO Auto-generated method stub
		currencyobj = currencyRepository.findById(currency.getCoinId()).get();
        initialSupply = currency.getInitialSupply();
		initialSupply = initialSupply + currencyobj.getInitialSupply();
		currency.setInitialSupply(initialSupply);
		currencyRepository.save(currency);
	}

	public void deleteCurrency(Integer coinId) {
		// TODO Auto-generated method stub
		currencyRepository.deleteById(coinId);	
	}

}
