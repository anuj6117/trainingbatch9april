package com.example.demo.controller;

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

import com.example.demo.model.CoinManagement;
import com.example.demo.service.CoinManagementService;
import com.example.demo.utility.ResponseHandler;

@RestController
public class CoinManagementController {

	@Autowired
	private CoinManagementService coinManagementService;
	
	
	@RequestMapping(value = "/addcurrency", method = RequestMethod.POST)
	public ResponseEntity<Object> addCurrency(@RequestBody CoinManagement coinManagement)
	{
		Map<String, Object> result = null;
		try {
			result = coinManagementService.addCurrency(coinManagement);

			if (result.get("isSuccess").equals(true)) {
				return ResponseHandler.generateResponse(HttpStatus.OK, true, result.get("message").toString(), result);
			} else {
				return ResponseHandler.generateResponse(HttpStatus.OK, false, result.get("message").toString(), result);
			}

		} catch (Exception e) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), result);
		}
	}
	
	@RequestMapping(value = "/updatecurrency", method = RequestMethod.POST)
	public ResponseEntity<Object> updateCurrency(@RequestBody CoinManagement coinManagement)
	{
		Map<String, Object> result = null;
		
		try {
			result = coinManagementService.updateCurrency(coinManagement);

			if (result.get("isSuccess").equals(true)) {
				return ResponseHandler.generateResponse(HttpStatus.OK, true, result.get("message").toString(), result);
			} else {
				return ResponseHandler.generateResponse(HttpStatus.OK, false, result.get("message").toString(), result);
			}

		} catch (Exception e) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), result);
		}
		
	}
	
	@RequestMapping(value = "/deletecurrency", method = RequestMethod.GET)
	public ResponseEntity<Object> deleteCurrency(@RequestParam("coinId") Integer coinId)
	{
		Map<String, Object> result = null;
		
		try {
			result = coinManagementService.deleteCurrency(coinId);

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
	public List<CoinManagement> getAllCurrencies() {
		List<CoinManagement> list = coinManagementService.getAllCurrencies();
		return list;
	}

}
