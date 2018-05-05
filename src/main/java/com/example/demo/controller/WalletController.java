package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.UserWalletDTO;
import com.example.demo.model.OrderDetails;
import com.example.demo.model.Wallet;
import com.example.demo.service.WalletService;


@RestController

public class WalletController {
	
	@Autowired
	private WalletService walletService;
	
	@RequestMapping(value="/addwallet", method=RequestMethod.POST)
	public String addWallet(@RequestBody UserWalletDTO userwalletDto) {
		return walletService.addWallet(userwalletDto);
	}
	
	@RequestMapping("/getallwallet")
	public Object getAllWallet(){		
		List<Wallet> list = walletService.getAllWallet();
		if(list.isEmpty())
			return "no wallet available";
		return list;
	}			
	
	@RequestMapping(value = "/wallethistory" , method = RequestMethod.POST)
	public Object getWalletHistory(@RequestBody UserWalletDTO userWallet) {
		List<OrderDetails> list =  walletService.getWalletHistory(userWallet);
		if(list.isEmpty())
			return "no wallet history available";
		return list;
	}
	
	
}
