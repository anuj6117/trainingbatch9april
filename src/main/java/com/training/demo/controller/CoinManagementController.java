package com.training.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.training.demo.model.CoinManagement;
import com.training.demo.service.CoinManagementService;

@RestController
public class CoinManagementController {
	
	@Autowired
	private CoinManagementService coinManagementService;
	
	@RequestMapping(value="/addcurrency", method=RequestMethod.POST)
	public String addCoin(@RequestBody CoinManagement coinManagement)
	{	
			return coinManagementService.addAllCoin(coinManagement);	
	}
	
	@RequestMapping(value="getallcurrency", method=RequestMethod.GET)
	public List<CoinManagement> getAllCurrency()
	{
		List<CoinManagement> list=coinManagementService.getCurrencies();
		return list;
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
}