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
		if(coinManagement != null)
		{
			return coinManagementService.addAllCoin(coinManagement);
		}
		else
		{
			throw new NullPointerException("insufficient information.");
		}
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
		String result=coinManagementService.update(data);
		if(result != null)
		{
			return "Your Coin Has Been Updated Successfully ";
		}
		else
		{
			throw new NullPointerException("insufficient information");
		}
	}
	
	@RequestMapping(value="/deletecurrency", method=RequestMethod.GET)
	public String delete(@RequestParam("coinId") Integer coinId)
	{
		if(coinId != null)
		{
			coinManagementService.delete(coinId);
			return "Your Coin Has Been Deleted Successfully ";
		}
		else
		{
			throw new NullPointerException("insufficient information");
		}	
	}
}