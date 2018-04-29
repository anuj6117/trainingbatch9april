package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.CurrencyModel;
import com.example.demo.service.CurrencyService;

@RestController
public class CurrencyController {
	
	@Autowired
	private CurrencyService currencyService;
	
	@RequestMapping(value="/addcurrency", method=RequestMethod.POST)
	public String addCurrency(@RequestBody CurrencyModel currency) {
		return currencyService.addCurrency(currency);			
	}
	
	@RequestMapping("/getallcurrency")
	public List<CurrencyModel> getAllCurrency() {
		return currencyService.getAllCurrency();
	}
	
	@RequestMapping("/getcurrencybyid")
	public CurrencyModel getCurrencyById(@RequestParam("id")Integer id) {
		return currencyService.getCurrencyById(id);
	}
	
	@RequestMapping(name="/updatecurrency", method=RequestMethod.POST)
	public String updateCurrency(@RequestBody CurrencyModel currency) {
		return (currencyService.updateCurrency(currency));		
	}
	
	@RequestMapping(name="/deletecurrency", method=RequestMethod.GET)
	public String deleteCurrency(@RequestParam("id")Integer id) {
		return currencyService.deleteCurrency(id);
	}
	
	
}
