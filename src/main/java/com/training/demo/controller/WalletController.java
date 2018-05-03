package com.training.demo.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.training.demo.dto.UserDepositDto;
import com.training.demo.dto.UserWalletDto;
import com.training.demo.dto.WalletApprovalDto;
import com.training.demo.model.UserOrder;
import com.training.demo.model.Wallet;
import com.training.demo.repository.UserOrderRepository;
import com.training.demo.repository.WalletRepository;
import com.training.demo.service.WalletService;

@RestController
public class WalletController {

	@Autowired
	private WalletService walletService;

	@Autowired
	private WalletRepository walletRepository;
	@Autowired
	private UserOrderRepository userorderrepository;

	private Wallet wallet;

	@RequestMapping(value = "addwallet", method = RequestMethod.POST)
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

	@RequestMapping(value = "depositamount", method = RequestMethod.POST)

	public String deposit(@RequestBody UserWalletDto userwalletdto) {
		return walletService.depositAmount(userwalletdto);
	}

	@RequestMapping(value = "withdrawamount", method = RequestMethod.POST)
	public String withDraw(@RequestBody UserDepositDto userdepositdto) {
		return walletService.withDrawAmount(userdepositdto);
	}
	@RequestMapping(value="walletapprove",method=RequestMethod.POST)
	public String approve(@RequestBody WalletApprovalDto walletapprovaldto) throws Exception{
	return walletService.deposit(walletapprovaldto);
	
}
	@RequestMapping(value="/walletHistory", method=RequestMethod.GET)
	   public Set<UserOrder> walletHistory(@RequestParam("userId") Integer userId,@RequestParam("coinName") String coinName)
	    {
		   
		 return userorderrepository.history(userId, coinName);
	    }


	
}


