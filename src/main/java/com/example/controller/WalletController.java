package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.UserWalletDto;
import com.example.dto.WalletApprovalDto;
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
    
   /* @RequestMapping(value="/depositamount",method=RequestMethod.POST)
	public String depositamount(@RequestBody UserWalletDto userwalletdto)
	{
    	System.out.println("::::::::::::::::::::::::::::::::");
    	if(userwalletdto==null)
    	{
    		System.out.println("::::::::::::::::::::::::::::::::111111111");
    		return "null here::::::::::::::::::::::::::::::";
    	}
    	
    	else {
    		System.out.println("::::::::::::::::::::::::::::::::222222222");
	  	return walletservice.depositamount(userwalletdto) ;
	}
	}
    */
    @RequestMapping(value="/withdrawamount",method=RequestMethod.POST)
	public String withdrawamount(@RequestBody UserWalletDto userwalletdto)
	{
    	
	  	return walletservice.withdrawamount(userwalletdto) ;
	}
    
    @RequestMapping(value="/walletapproval",method=RequestMethod.POST)
    public String walletApprovalStatus(@RequestBody WalletApprovalDto walletApprovalDto)
    {
    	System.out.println("wallllllllllllllllllllllllllllllllll");
    	return walletservice.walletApprovalStatus(walletApprovalDto);
    }
}
