package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.UserWalletDTO;
import com.example.demo.model.Wallet;
import com.example.demo.service.WalletService;


@RestController
//@RequestMapping("wallet")
public class WalletController {
	
	@Autowired
	private WalletService walletService;
	
	@RequestMapping(value="/addwallet", method=RequestMethod.POST)
	public String addWallet(@RequestBody UserWalletDTO userwalletDto) {
		return walletService.addWallet(userwalletDto);
	}
	
	@RequestMapping("/getallwallet")
	public List<Wallet> getAllWallet(){		
		return walletService.getAllWallet();		
	}

}
