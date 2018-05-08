package com.trainingproject.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trainingproject.domain.UserOrder;
import com.trainingproject.domain.Wallet;
import com.trainingproject.dto.UserWalletDto;
import com.trainingproject.dto.WalletApprovalDto;
import com.trainingproject.service.WalletService;
@RestController
public class WalletController {
	@Autowired
	private WalletService walletService;
	
	
	@RequestMapping(value = "/getallwallet")
	public List<Wallet> getAllWallet(){
		return walletService.getAllWallet();
	}
	
	@RequestMapping(value = "/getbywalletid",method = RequestMethod.GET)
	public Optional<Wallet> getById(Integer walletId){
		return walletService.getById(walletId);
	}
	@RequestMapping(value = "/updatewallet",method = RequestMethod.POST)
	public void updateWallet(@RequestBody Wallet wallet){
		walletService.updateWallet(wallet);
	}

	@RequestMapping(value = "/deletewallet",method = RequestMethod.GET)
	public void deleteWallet(Integer walletId){
		walletService.deleteWallet(walletId);
	}
	@RequestMapping(value = "/depositamount", method = RequestMethod.POST)
	public String depositAmount(@RequestBody   UserWalletDto userWalletDto) {
		return walletService.depositAmount(userWalletDto);
		 
	}

	@RequestMapping(value = "/walletapproved", method = RequestMethod.POST)
	public String walletApproved(@RequestBody  WalletApprovalDto walletApprovalDto) throws Exception {
		return walletService.walletApproved(walletApprovalDto);
	}
	
	@RequestMapping(value = "/wallethistory", method = RequestMethod.GET)
	public UserOrder walletHistory(@RequestParam("userId") Integer userId, @RequestParam("coinName") String coinName ) {
		return walletService.walletHistory(userId, coinName);
	}
}
