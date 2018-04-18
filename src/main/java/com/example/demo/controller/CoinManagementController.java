package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.model.CoinManagement;
import com.example.demo.service.CoinManagementService;

@Controller
public class CoinManagementController {
	
	@Autowired
	private CoinManagementService coinManagementService;
	
	@RequestMapping(value="/addcurrency", method=RequestMethod.POST)
	public String addCoin(@RequestBody CoinManagement data)
	{
		System.out.println("AddCoin");
		return coinManagementService.addAllCoin(data);
	}
	
	@RequestMapping(value="getallcurrency", method=RequestMethod.GET)
	public List<CoinManagement> getAllCurrency()
	{
		List<CoinManagement> list=coinManagementService.getCurrencies();
		return list;
	}
	
	/*@RequestMapping(value="/updatecurrency", method=RequestMethod.POST)
	public CoinManagement updateCurrency()
	{
		
	}*/

}
