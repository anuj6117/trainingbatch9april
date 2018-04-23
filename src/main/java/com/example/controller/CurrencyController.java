package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Currency;
import com.example.repository.CurrencyRepository;
import com.example.service.CurrencyService;

@RestController
public class CurrencyController
{
@Autowired
private CurrencyService currencyservice;
private Currency currency;
@Autowired
private CurrencyRepository currencyrepository;

@RequestMapping(value="/addcurrency",method=RequestMethod.POST)	
 public String addCurrency(@RequestBody Currency currency)
 {
	return currencyservice.addCurrency(currency);
 }
  

@GetMapping("/getallcurrency")
 public List<Currency> getallcurrency()
 {
	return currencyrepository.findAll();
	
 }


@RequestMapping(value="/updatecurrency",method=RequestMethod.POST)
 public void updatecurrency(@RequestBody Currency currency)
 {
	
	currencyrepository.save(currency);
 }

@GetMapping("/deletecurrency/{id}")
public String deletecurrency(@PathVariable("id") Integer coinid)
{
	 currency=currencyrepository.findByCoinId(coinid);
	 currencyrepository.delete(currency);
	 return "currency deleted";
	
}

}
