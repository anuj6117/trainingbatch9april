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
	
	
	public Currency addCurrency(Currency cur) {
		Currency currency=currencyRepository.save(cur);
		return currency;
	}

	public List<Currency> getAllCurrency() {
	  return currencyRepository.findAll();
		
	}

	public Currency updateCurrency(Currency cur) {
		return currencyRepository.save(cur);
		
	}

	public void deleteCurrency(Integer id) {
		currencyRepository.deleteById(id);
	}
}
