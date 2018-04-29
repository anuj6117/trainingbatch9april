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
	
	

	public String addCurrency(CurrencyModel currency) {
		currency.getCoinType();
		if(currency.getCoinName()==null)
			return "invalid coin name";
		if(currency.getInitialSupply()==null)
			return "invalid initial supply";
		if(currency.getPrice()==null)
			return "invalid price";
		if(currency.getSymbol()==null)
			return "invalid symbol";
		if(currency.getCoinType()==null)
			return "invalid coinType";
		if((currencyRepository.findOneByCoinName(currency.getCoinName()))==null)
			return (currencyRepository.save(currency)!=null)?"success":"failure";		
		else
			return "currency already exist";
	}

	public List<CurrencyModel> getAllCurrency() {
		return currencyRepository.findAll();
	}

	public String updateCurrency(CurrencyModel currency) {
		CurrencyModel currencyModel = null;
		try {
			currencyModel = currencyRepository.findById(currency.getCoinId()).get();
		}catch(Exception e) {return "currency not exist";}
		if(currencyModel!=null) {
			if((currencyRepository.findOneByCoinName(currency.getCoinName()))==null) {
				if((currencyRepository.findOneBySymbol(currency.getSymbol()))==null) {
					currencyModel.setCoinName(currency.getCoinName());
					currencyModel.setSymbol(currency.getSymbol());
					currencyModel.setPrice(currency.getPrice());
					currencyModel.setInitialSupply(currency.getInitialSupply());
					return (currencyRepository.save(currency)!=null)?"Success":"Failure";
				}
				else 
					return "currency with same symbol already exist";
			}
			else
				return "coin with same name already exist";
		}
		else 
			return "currency doesn't exist";		
	}

	public String deleteCurrency(Integer id) {
		if(currencyRepository.findById(id)!=null){
			currencyRepository.deleteById(id);
			return "Currency deleted Successfully";
		}
		
		else		
			return "currency not exist";
	}

	public CurrencyModel getCurrencyById(Integer id) {
		return currencyRepository.findOneByCoinId(id);
	}

}
