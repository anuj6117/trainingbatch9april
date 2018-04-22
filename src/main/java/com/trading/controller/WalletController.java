package com.trading.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.trading.dto.UserWalletDto;
import com.trading.services.WalletService;

@RestController
public class WalletController {

	
	@Autowired
private	WalletService walletservice;
	@RequestMapping(value = "/addwallet", method = RequestMethod.POST)
	public String addWallet(@RequestBody UserWalletDto userwalletdto)
	{
		return walletservice.insertWallet(userwalletdto);
	}

	@RequestMapping(value = "/deposit", method = RequestMethod.POST)
	public String deposit(@RequestBody UserWalletDto userwalletdto)
	{
		return walletservice.depositAmount(userwalletdto);
	}


	@RequestMapping(value = "/withdraw", method = RequestMethod.POST)
	public String withdraw(@RequestBody UserWalletDto userwalletdto)
	{
		return walletservice.withdrawAmount(userwalletdto);
	}
}

