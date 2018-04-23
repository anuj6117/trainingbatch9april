package com.training.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.training.demo.dto.UserTransactionDto;
import com.training.demo.dto.WalletDto;
import com.training.demo.service.WalletService;

@RestController
public class WalletController {

	@Autowired
	private WalletService walletService;

	@RequestMapping(value = "/addwallet", method = RequestMethod.POST)
	public String addWallet(@RequestBody WalletDto walletDto) {
		System.out.println("from wallet controller");
		if (walletDto != null) {
			System.out.println(walletDto.getUserId());
			System.out.println(walletDto.getWalletType());
			walletService.addWalletToUser(walletDto);
			return "Successfully Added";
		} else {
			return "Wallet not added";
		}
	}
	
	@RequestMapping(value="/depositamount", method = RequestMethod.POST)
	public String toDepositAmount(@RequestBody UserTransactionDto utd){
		if(utd != null) {
			walletService.toDepositAmount(utd);
			return "success";
		}
		else {
			return "failure";
		}
	}		
}