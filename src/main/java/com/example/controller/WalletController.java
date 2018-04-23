package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.UserWalletDto;
import com.example.model.User;
import com.example.service.WalletService;

@RestController
public class WalletController 
{
	@Autowired
	private WalletService walletservice;
	private User user;
    @RequestMapping(value="/addwallet",method=RequestMethod.POST)
	public String addWallet(@RequestBody UserWalletDto userwalletdto)
	{
    	
	  	return walletservice.addWallet(userwalletdto) ;
	}
    
    @RequestMapping(value="/depositamount",method=RequestMethod.POST)
	public String depositamount(@RequestBody UserWalletDto userwalletdto)
	{
    	
	  	return walletservice.depositamount(userwalletdto) ;
	}
    
}
