package io.oodles.springboot1.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	public Currency createCurrency(@RequestBody Currency currency) {
		//System.out.println("Done1");
		return currencyservice.create(currency);
	}
	@GetMapping("/getbycurrencyid/{id}")
	public Optional<Currency> getbyid(@PathVariable int id){
		return currencyservice.searchbyid(id);
	}
	
	
	@PostMapping("/updatecurrency")
	public Currency updatecurrency(@RequestBody Currency currency) {
		return currencyservice.update(currency);
	}
	
	@GetMapping("/deletecurrency/{id}")
	public void deleteuser(@PathVariable int id) {
	    currencyservice.delete(id);
	}

}
