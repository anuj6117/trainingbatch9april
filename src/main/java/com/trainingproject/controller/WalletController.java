package com.trainingproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trainingproject.domain.Wallet;
import com.trainingproject.domain.WalletHistory;
import com.trainingproject.dto.ApproveWalletBean;
import com.trainingproject.enums.CoinType;
import com.trainingproject.service.WalletHistoryService;
import com.trainingproject.service.WalletService;

@RestController
public class WalletController {

	@Autowired
	WalletService walletService;
	

	
	@RequestMapping(value="/createwallet",method=RequestMethod.POST)
	public String createWallet(@RequestBody Wallet wallet) {
		Wallet cwallet=walletService.createWallet(wallet);
		if(cwallet==null)
			return "failure";
		else return "success";
	}
	
	@RequestMapping(value="approvewallet",method=RequestMethod.POST)
	public String approveWallet(@RequestBody ApproveWalletBean awb) {
		return walletService.approveWallet(awb);
	}
	
	@RequestMapping(value="walletHistory",method=RequestMethod.GET)
	public Wallet walletHistory(@RequestParam Integer userId,CoinType coinType) {
		
		 return  walletService.walletHistory(userId,coinType);
	}
}
