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
	private	CurrencyService currencyService;
		
		@RequestMapping(value = "/addcurrency", method = RequestMethod.POST)
		public String addCurrency(@RequestBody Currency currency) throws Exception
		{
			return currencyService.insertDetails(currency);
		}
		
		@RequestMapping(value = "/getallcurrency", method = RequestMethod.GET)
		public Iterable <Currency> getAllCurrency() throws Exception
		{
			return currencyService.getDetails();
		}
		
		
		@RequestMapping(value = "/updatecurrency", method = RequestMethod.POST)
		public Currency updateCurrency(@RequestBody Currency currency) {
			return currencyService.updateDetails(currency);
		}
		
		@RequestMapping(value = "/deletecurrency", method = RequestMethod.GET)
		public String deleteCurrency(@RequestParam("coinId") long coinId)
		{
			return currencyService.deleteById(coinId);
		}
		
		@RequestMapping(value = "/getcurrencybyid", method = RequestMethod.POST)
		public Currency getDetailsById(@RequestParam("coinId") long coinId)
		{
			return currencyService.getCurrencyById(coinId);
		}	
	}


