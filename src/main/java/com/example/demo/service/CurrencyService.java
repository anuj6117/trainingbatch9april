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
		currency.setCoinName(currency.getCoinName().toLowerCase());
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
		if(currencyRepository.findBySymbol(currency.getSymbol())!=null)
			return "currency with same symbol already exist";
		if((currencyRepository.findOneByCoinName(currency.getCoinName()))==null)
			return (currencyRepository.save(currency)!=null)?"success":"failure";		
		else
			return "currency already exist";
	}

	public List<CurrencyModel> getAllCurrency() {
		return currencyRepository.findAll();
	}

	public String updateCurrency(CurrencyModel currency) {
		String coinName = currency.getCoinName().toLowerCase();
		CurrencyModel currencyModel = null;
		CurrencyModel chkCurrency = null;
		if(currency.getCoinId()==null)
			return "please enter coin id";
		if(currency.getCoinName()==null)
			return "please enter coin Name"; 
		if(currency.getCoinType()==null)
			return "Please enter coinType";
		if(currency.getFee()==null)
			return "please enter fee";
		if(currency.getSymbol()==null)
			return "please enter symbol";
		if(currency.getInitialSupply()==null)
			return "please enter initial supply";
		if(currency.getPrice()==null)
			return "please enter price";
		try {
			currencyModel = currencyRepository.findById(currency.getCoinId()).get();
		}catch(Exception e) {return "currency not exist";}
		if(currencyModel!=null) {
			if((chkCurrency = currencyRepository.findOneByCoinName(coinName))!=null) {
				if(chkCurrency.getCoinId()!=currencyModel.getCoinId())
					return "currency with same name already exist";
			}
				if((currencyRepository.findOneBySymbol(currency.getSymbol()))!=null) {
					if(chkCurrency.getCoinId()!=currencyModel.getCoinId())
						return "currency with same symbol already exist";
				}
					currencyModel.setCoinName(coinName);
					currencyModel.setSymbol(currency.getSymbol());
					currencyModel.setPrice(currency.getPrice());
					currencyModel.setInitialSupply(currency.getInitialSupply());
					return (currencyRepository.save(currencyModel)!=null)?"Success":"Failure";
				}
				else 
					return "currency not exist";
				
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
