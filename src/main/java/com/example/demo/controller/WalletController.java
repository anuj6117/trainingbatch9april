package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.WalletDTO;
import com.example.demo.service.WalletService;

@RestController
public class WalletController {

	@Autowired
	private WalletService walletService;

	@RequestMapping(value = "/addwallet", method = RequestMethod.POST)
	private String addWallet(@RequestBody WalletDTO walletDTO) {
		System.out.println("from wallet controller");
		if (walletDTO != null) {
			System.out.println(walletDTO.getUserId());
			walletService.addWalletToUser(walletDTO);
			return "Successfully Added";
		} else {
			return "Wallet not added";
		}
	}

}
