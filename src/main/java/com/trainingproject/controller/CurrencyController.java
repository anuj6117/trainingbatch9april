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
	CurrencyService currencyService;
	
	@RequestMapping(value="/addcurrency",method=RequestMethod.POST)
	public String addCurrency(@RequestBody Currency cur) {
		return currencyService.addCurrency(cur);
		
	}
	
	@RequestMapping(value="/getallcurrency",method=RequestMethod.GET)
	public List<Currency> getAllCurrency() {
		return currencyService.getAllCurrency();
	}
	
	@RequestMapping(value="/getcurrencybyid",method=RequestMethod.GET)
	public Optional<Currency> getCurrencyById(@RequestParam Integer coinId) {
		return currencyService.getCurrencyById(coinId);
	}
	
	@RequestMapping(value="/updatecurrency",method=RequestMethod.POST)
	public String updateCurrency(@RequestBody Currency cur) {
		return currencyService.updateCurrency(cur);
		
	}
	
	@RequestMapping(value="/deletecurrency",method=RequestMethod.GET)
	public String deleteCurrency(@RequestParam Integer coinId) {
		currencyService.deleteCurrency(coinId);
		return "success";
	}
	
}
