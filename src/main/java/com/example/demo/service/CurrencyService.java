package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.CurrencyModel;
import com.example.demo.repository.CurrencyRepository;


@Service
public class CurrencyService {
	
	@Autowired 
	private CurrencyRepository currencyRepository;

	private CurrencyModel currencyModel;

	public CurrencyModel addCurrency(CurrencyModel currency) {
		if((currencyRepository.findOneByCoinName(currency.getCoinName()))==null)
			return currencyRepository.save(currency);
		else
			return null;
	}

	public List<CurrencyModel> getAllCurrency() {
		return currencyRepository.findAll();
	}

	public CurrencyModel updateCurrency(CurrencyModel currency) {
		
		if((currencyModel=currencyRepository.findOneByCoinId(currency.getCoinId()))!=null){
			currencyModel.setCoinName(currency.getCoinName());
			currencyModel.setSymbol(currency.getSymbol());
			currencyModel.setPrice(currency.getPrice());
			currencyModel.setInitialSupply(currency.getInitialSupply());
			return currencyRepository.save(currency);
		}

		else 
			return null;
	}

	public boolean deleteCurrency(Integer id) {
		if(currencyRepository.findById(id)!=null){
			currencyRepository.deleteById(id);
			return true;
		}

		else		
			return false;

	}

}
