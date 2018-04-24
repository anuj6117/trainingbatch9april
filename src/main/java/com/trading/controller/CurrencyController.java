package com.trading.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trading.domain.Currency;
import com.trading.handler.ResponseHandler;
import com.trading.services.CurrencyService;

@RestController
public class CurrencyController {

	@Autowired
	private CurrencyService currencyService;

	@RequestMapping(value = "/addcurrency", method = RequestMethod.POST)
	public ResponseEntity<Object> addCurrency(@Valid @RequestBody Currency currency) throws Exception {
		Map<String, Object> result = null;
		try {
			result = currencyService.addCurrency(currency);
			if (result.get("isSuccess").equals(true)) {
				return ResponseHandler.generateResponse(HttpStatus.OK, true, result.get("message").toString(), result);
			} else {
				return ResponseHandler.generateResponse(HttpStatus.OK, false, result.get("message").toString(), result);
			}
		} catch (Exception e) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), result);
		}
	}

	@RequestMapping(value = "/getallcurrency", method = RequestMethod.GET)
	public Iterable<Currency> getAllCurrency() throws Exception {
		return currencyService.getDetails();
	}

	@RequestMapping(value = "/updatecurrency", method = RequestMethod.POST)
	public Currency updateCurrency(@Valid @RequestBody Currency currency) {
		return currencyService.updateDetails(currency);
	}

	@RequestMapping(value = "/deletecurrency", method = RequestMethod.GET)
	public ResponseEntity<Object> deleteCurrency(@Valid @RequestParam("coinId") long coinId) {
		Map<String, Object> result = null;
		try {
			result = currencyService.deleteById(coinId);
			if (result.get("isSuccess").equals(true)) {
				return ResponseHandler.generateResponse(HttpStatus.OK, true, result.get("message").toString(), result);
			} else {
				return ResponseHandler.generateResponse(HttpStatus.OK, false, result.get("message").toString(), result);
			}
		} catch (Exception e) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), result);
		}
	}

	@RequestMapping(value = "/getcurrencybyid", method = RequestMethod.POST)
	public Currency getDetailsById(@Valid @RequestParam("coinId") long coinId) {
		return currencyService.getCurrencyById(coinId);
	}
}
