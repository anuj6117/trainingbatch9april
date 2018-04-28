package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.DepositAmountDTO;
import com.example.demo.dto.WalletDTO;
import com.example.demo.enums.UserStatus;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.WalletService;

@RestController
public class WalletController {

	@Autowired
	private WalletService walletService;
	
	@Autowired
	private UserRepository userRepository;

	@RequestMapping(value = "/addwallet", method = RequestMethod.POST)
	public String addWallet(@RequestBody WalletDTO walletDTO)
	{
		if (walletDTO != null) 
		{
			walletService.addWalletToUser(walletDTO);
			return "Successfully Added";
		}
		else
		{
			return "Wallet not added";
		}
	}
	
	@RequestMapping(value="/deposit", method=RequestMethod.POST)
	public String deposit(@RequestBody DepositAmountDTO depositAmountDTO)
	{
		User user=userRepository.findByUserId(depositAmountDTO.getUserId());
		if(user.getStatus().equals(UserStatus.ACTIVE))
		{
			return walletService.deposit(depositAmountDTO);
		}
		else
		{
			return "User Inactive deposit won't be complete.";
		}
	}
	
	/*@RequestMapping(value="/withdraw", method=RequestMethod.POST)
	public String withdraw(@RequestBody DepositAmountDTO transactionDto)
	{
		return walletService.withdraw(transactionDto);
		
	}*/

}
