package com.training.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.training.demo.dto.OrderDto;
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
			System.out.println(walletDto.getCoinName()+"/t"+walletDto.getCoinType()+"/t"+walletDto.getUserId());
			System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");;
			return walletService.addWallet(walletDto);
		} else {
			return "Insufficient information to create wallet.";
		}
	}
	
	@RequestMapping(value="/depositamount", method = RequestMethod.POST)
	public String toDepositAmount(@RequestBody OrderDto orderDto){
		if(orderDto != null) {
			return walletService.depositAmount(orderDto);
		}
		else {
			throw new NullPointerException("Insufficient information....");
		}
	}		
	
	@RequestMapping(value="/withdrawamount", method = RequestMethod.POST)
	public String toWithdrawAmount(@RequestBody OrderDto orderDto) 
	{
		System.out.println(orderDto.getUserId()+"/t"+orderDto.getWalletType()+"/t"+orderDto.getAmount());
				return walletService.toWithdrawn(orderDto);

	}
	
	@RequestMapping(value="/walletHistory", method = RequestMethod.GET)
	public Object showWalletHistoryByUserIdAndCoinName(Integer userId, String coinName){
		return walletService.showWalletHistoryByUserIdAndCoinName(userId, coinName);
		
	}
}
	
