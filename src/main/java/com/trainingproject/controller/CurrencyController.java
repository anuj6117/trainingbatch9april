package com.trainingproject.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trainingproject.domain.Currency;
import com.trainingproject.service.CurrencyService;
@RestController
public class CurrencyController {
	@Autowired
	private CurrencyService currencyService;
	
	@RequestMapping(value = "/addcurrency",method = RequestMethod.POST)
	   public String func(@RequestBody Currency currency) {
		 Currency addedCurrency = currencyService.addCurrency(currency);
		if(addedCurrency != null) {
			return "success";
		}
			else
				return "Failure";

		}
	
	
	
	@RequestMapping(value = "/getallcurrency",method = RequestMethod.GET)
	public List<Currency> getAllCurrency() {
		return currencyService.getAllCurrency();
	}
	
	@RequestMapping(value = "/getcurrencybyid",method = RequestMethod.GET)
	public Optional<Currency> getById(@RequestParam("coinId") Integer coinId) {
		return currencyService.getById(coinId);
	}
	@RequestMapping(value  ="/updatecurrency",method = RequestMethod.POST)
	public String updateCurrency(@RequestBody Currency currency) {
		currencyService.updateCurrency(currency);
		return "success";
	}

	@RequestMapping(value = "/deletecurrency",method = RequestMethod.GET)
	public String deleteCurrency(@RequestParam("coinId") Integer coinId) {
		currencyService.deleteCurrency(coinId);
		return "success";
	}
	


}
