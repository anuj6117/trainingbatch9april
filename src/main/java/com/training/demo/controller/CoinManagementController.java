package com.training.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.training.demo.model.CoinManagement;
import com.training.demo.repository.CoinManagementRepository;
import com.training.demo.service.CoinManagementService;

@RestController
public class CoinManagementController {
	
	@Autowired
	private CoinManagementService coinManagementService;
	
	@Autowired
	private CoinManagementRepository coinRepository;
	
	@RequestMapping(value="/addcurrency", method=RequestMethod.POST)
	public String addCoin(@RequestBody CoinManagement coinManagement)
	{	
			return coinManagementService.addAllCoin(coinManagement);	
	}
	
	@RequestMapping(value="/getallcurrency", method=RequestMethod.GET)
	public Object getAllCurrency()
	{
		return  coinManagementService.getCurrencies();
	}
	
	@RequestMapping(value="/updatecurrency", method=RequestMethod.POST)
	public String updateCurrency(@RequestBody CoinManagement data)
	{
		return  coinManagementService.update(data);
	}
	
	@RequestMapping(value="/deletecurrency", method=RequestMethod.GET)
	public String delete(@RequestParam("coinId") Integer coinId)
	{
			return coinManagementService.delete(coinId);	
	}
	
	@RequestMapping(value="/getcurrencybyid", method=RequestMethod.GET)
	public Object getCurrencyById(@RequestParam("coinId") Integer coinId)
	{
		CoinManagement coinManagement;
		if((coinManagement = coinRepository.findOneByCoinId(coinId)) == null) {
			return "invalid coin id.";
		}
		return coinManagement;
	}	
}