package com.trainingproject.controller;

import java.util.List;

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
		Currency curr=currencyService.addCurrency(cur);
		return curr==null?"failure":"success";
	}
	
	@RequestMapping(value="/getallcurrency",method=RequestMethod.GET)
	public List<Currency> getAllCurrency() {
		return currencyService.getAllCurrency();
	}
	
	@RequestMapping(value="/getcurrencybyid",method=RequestMethod.GET)
	public List<Currency> getCurrencyById(@RequestParam Integer coinId) {
		return currencyService.getCurrencyById(coinId);
	}
	
	@RequestMapping(value="/updatecurrency",method=RequestMethod.POST)
	public Currency updateCurrency(@RequestBody Currency cur) {
		Currency currn=currencyService.updateCurrency(cur);
		return currn;
	}
	
	@RequestMapping(value="/deletecurrency",method=RequestMethod.GET)
	public String deleteCurrency(@RequestParam Integer id) {
		currencyService.deleteCurrency(id);
		return "success";
	}
	
}
