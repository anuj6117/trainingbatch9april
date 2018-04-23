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
private	WalletService walletService;
	@RequestMapping(value = "/addwallet", method = RequestMethod.POST)
	public String addWallet(@RequestBody UserWalletDto userwalletdto)
	{
		return walletService.insertWallet(userwalletdto);
	}

	@RequestMapping(value = "/deposit", method = RequestMethod.POST)
	public String deposit(@RequestBody UserWalletDto userwalletdto)
	{
		return walletService.depositAmount(userwalletdto);
	}

	@RequestMapping(value = "/withdraw", method = RequestMethod.POST)
	public String withdraw(@RequestBody UserWalletDto userwalletdto)
	{
		return walletService.withdrawAmount(userwalletdto);
	}
}

