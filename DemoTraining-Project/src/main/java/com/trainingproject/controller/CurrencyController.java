package com.trainingproject.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trainingproject.domain.Currency;
import com.trainingproject.service.CurrencyService;
import com.trainingproject.utils.ResponseHandler;
@RestController
public class CurrencyController {
	@Autowired
	private CurrencyService currencyService;
	
	@RequestMapping(value = "/addcurrency",method = RequestMethod.POST)
	public ResponseEntity<Object> addCurrency(@RequestBody Currency currency) {
		Map<String,Object> result = null;
		try {
			result = currencyService.addCurrency(currency);	
			if(result.get("isSuccess").equals(true)) {
				return ResponseHandler.generateResponse(HttpStatus.OK, true, result.get("message").toString(), result);
			}
			else {
				return ResponseHandler.generateResponse(HttpStatus.OK, false, result.get("message").toString(), result);
			}
		}
		catch(Exception e) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), result);
		}
	}
	
	
	
	@RequestMapping(value = "/getallcurrency",method = RequestMethod.GET)
	public List<Currency> getAllCurrency() {
		return currencyService.getAllCurrency();
	}
	
	@RequestMapping(value = "/getcurrencybyid",method = RequestMethod.GET)
	public Object getById(@RequestParam("coinId") Integer coinId) {
		try {
		return currencyService.getById(coinId);
		} catch(Exception e) {
			return "Currency can't be display as :"+e.getMessage();
		}
	}
	@RequestMapping(value  ="/updatecurrency",method = RequestMethod.POST)
	public ResponseEntity<Object> updateCurrency(@RequestBody Currency currency) {
		Map<String,Object> result = null;
		try {
			result = currencyService.updateCurrency(currency);
			if(result.get("isSuccess").equals(true)) {
				return ResponseHandler.generateResponse(HttpStatus.OK, true, result.get("message").toString(), result);
			}
			else {
				return ResponseHandler.generateResponse(HttpStatus.OK, false, result.get("message").toString(), result);
			}
		}
		catch(Exception e) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), result);
		}
	}

	@RequestMapping(value = "/deletecurrency",method = RequestMethod.GET)
	public String deleteCurrency(@RequestParam("coinId") Integer coinId) {
		try {
		return currencyService.deleteCurrency(coinId);
		}catch (Exception e) {
			return "Currency Cannot be deleted as : "+e.getMessage();
		}
	}
	


}
