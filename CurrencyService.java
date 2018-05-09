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
				result.put("message", "Hyphan & space are not allowed or Maximum character allowed for this field is 25.");
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
			result.put("message", "Coin Type can not be null");
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

	public Currency getById(Integer coinId) {
		// TODO Auto-generated method stub
		Currency currencyGet = currencyRepository.findByCoinId(coinId);
		if(currencyGet != null ) {
			return currencyGet;
		}
		else {
			throw new NullPointerException("Coin id can not exist");
		}
	}

	public Map<String,Object> updateCurrency(Currency currency) {
		// TODO Auto-generated method stub
		/*currencyobj = currencyRepository.findById(currency.getCoinId()).get();
        initialSupply = currency.getInitialSupply();
		initialSupply = initialSupply + currencyobj.getInitialSupply();
		currency.setInitialSupply(initialSupply);*/
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
				result.put("message", "Hyphan & space are not allowed or Maximum character allowed for this field is 25.");
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
			result.put("message", "Coin Type can not be null");
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

	public String deleteCurrency(Integer coinId) {
		// TODO Auto-generated method stub
		Currency deleteCurrency = currencyRepository.findByCoinId(coinId);
		if(deleteCurrency != null) {
			currencyRepository.deleteById(coinId);
			return "Delete currency success";
		}
		else {
			throw new NullPointerException("Coin id can not exist");
		}
	}

}
