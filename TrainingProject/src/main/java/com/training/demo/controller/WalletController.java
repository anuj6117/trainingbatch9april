package com.training.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.training.demo.dto.UserDepositDto;
import com.training.demo.dto.UserWalletDto;
import com.training.demo.model.Wallet;
import com.training.demo.repository.WalletRepository;
import com.training.demo.service.WalletService;

@RestController
public class WalletController {

	@Autowired
	private WalletService walletService;

	@Autowired
	private WalletRepository walletRepository;

	private Wallet wallet;

	@RequestMapping(value = "addWallet", method = RequestMethod.POST)
	public String addWallet(@RequestBody UserWalletDto userwalletdto) {
		// userwalletdto.setUserId(1234);
		// return
		// userwalletdto.getUserId()+"++++++++++++++++++++"+userwalletdto.getWalletType()+"++++++++++++++";
		// if (wallet != null) {
		return walletService.addWallet(userwalletdto);
		// return "Wallet Successfully Added.";
		// } else {
		// throw new NullPointerException("Wallet type may not be null ");
	}

	@RequestMapping(value = "depositAmount", method = RequestMethod.POST)

	public String deposit(@RequestBody UserDepositDto userdepositdto) {
		return walletService.depositAmount(userdepositdto);
	}

	@RequestMapping(value = "withdrawamount", method = RequestMethod.POST)
	public String withDraw(@RequestBody UserDepositDto userdepositdto) {
		return walletService.withDrawAmount(userdepositdto);
	}
}
