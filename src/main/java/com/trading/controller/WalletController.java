package com.trading.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.trading.domain.Wallet;
import com.trading.services.WalletService;

@RestController
public class WalletController {

	
	@Autowired
private	WalletService walletservice;
	@RequestMapping(value = "/addwallet", method = RequestMethod.POST)
	public String addWallet(@RequestBody Wallet wallet)
	{
		return walletservice.insertWallet(wallet);
	}
}