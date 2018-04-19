package com.trading.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trading.domain.Currency;
import com.trading.services.CurrencyService;

@RestController
public class CurrencyController {
	
	@Autowired
	private	CurrencyService currencyservice;
		
		@RequestMapping(value = "/addcurrency", method = RequestMethod.POST)
		public String addCurrency(@RequestBody Currency currency) throws Exception
		{
			return currencyservice.insertDetails(currency);
		}
		
		@RequestMapping(value = "/getallcurrency", method = RequestMethod.GET)
		public Iterable <Currency> getAllCurrency() throws Exception
		{
			return currencyservice.getDetails();
		}
		
		
		
		
		@RequestMapping(value = "/updatecurrency", method = RequestMethod.POST)
		public Currency updateCurrency(@RequestBody Currency Currency) {
			return currencyservice.updateDetails(currency);
		}
		
		@RequestMapping(value = "/deletecurrency", method = RequestMethod.GET)
		public String deleteCurrency(@RequestParam("coinId") long coinId)
		{
			return currencyservice.deleteById(coinId);
		}
		
	}


