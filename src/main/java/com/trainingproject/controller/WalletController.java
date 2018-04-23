package com.trainingproject.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.trainingproject.domain.Wallet;
import com.trainingproject.service.WalletService;
@RestController
public class WalletController {
	@Autowired
	private WalletService walletService;
	/*@RequestMapping(value = "createcwallet",method = RequestMethod.POST)
	   public String func(@RequestBody Wallet wallet) {
		 Wallet walletCreated = walletService.addWallet(wallet);
		if(walletCreated != null) {
			return "success";
		}
			else
				return "Failure";

		}*/
	
	/*@RequestMapping(value = "createwallet",method = RequestMethod.POST)
	public void addWallet(@RequestBody Wallet wallet) {
		
		walletService.addWallet(wallet);
	}*/
	
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
	

}
