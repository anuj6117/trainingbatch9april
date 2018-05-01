package com.trainingproject.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trainingproject.domain.Currency;
import com.trainingproject.repository.CurrencyRepository;
@Service
public class CurrencyService {

	@Autowired
	private CurrencyRepository currencyRepository;
	Currency currencyobj;
	
	Integer initialSupply;
	public Currency addCurrency(Currency currency) {
		// TODO Auto-generated method stub
		Currency addedCurrency = currencyRepository.save(currency);
		return addedCurrency;
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
