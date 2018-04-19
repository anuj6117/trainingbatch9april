package com.trainingproject.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.trainingproject.domain.Currency;
import com.trainingproject.service.CurrencyService;
@RestController
public class CurrencyController {
	@Autowired
	private CurrencyService currencyService;
	
	/*@RequestMapping(value="createcurrency",method=RequestMethod.POST)
	   public String func(@RequestBody Currency currency) {
		 Currency currencyCreated=currencyService.addCurrency(currency);
		if(currencyCreated!=null) {
			return "success";
		}
			else
				return "Failure";

		}*/
	
	@RequestMapping(value="createcurrency",method=RequestMethod.POST)
	public void addCurrency(@RequestBody Currency currency) {
		
		currencyService.addCurrency(currency);
	}
	
	@RequestMapping(value="/getallcurrency")
	public List<Currency> getAllCurrency(){
		return currencyService.getAllCurrency();
	}
	
	@RequestMapping(value="/getbycurrencyid",method=RequestMethod.GET)
	public Optional<Currency> getById(Integer coinId){
		return currencyService.getById(coinId);
	}
	@RequestMapping(value="/updatecurrency",method=RequestMethod.POST)
	public void updateCurrency(@RequestBody Currency currency){
		currencyService.updateCurrency(currency);
	}

	@RequestMapping(value="/deletecurrency",method=RequestMethod.GET)
	public void deleteCurrency(Integer coinId){
		currencyService.deleteCurrency(coinId);
	}
	


}
