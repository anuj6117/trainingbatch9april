package io.oodles.springboot1.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.oodles.springboot1.model.Currency;
import io.oodles.springboot1.service.CurrencyService;

@RestController
public class CurrencyController {
	@Autowired
	CurrencyService currencyservice;
	
	@GetMapping("/getallcurrency")
	public List<Currency> getall(){
         return currencyservice.getallcurrency();       	
	}
	@PostMapping("/addcurrency")
	public String createCurrency(@RequestBody Currency currency) {
		
		return currencyservice.create(currency);
	}
	@GetMapping("/getbycurrencyid")
	public Optional<Currency> getbyid(@RequestParam int coinId){
		return currencyservice.searchbyid(coinId);
	}
	
	
	@PostMapping("/updatecurrency")
	public String updatecurrency(@RequestBody Currency currency) {
		return currencyservice.update(currency);
	}
	
	@GetMapping("/deletecurrency")
	public String deleteuser(@RequestParam int coinId) {
	    return currencyservice.delete(coinId);
	}

}
