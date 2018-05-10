package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.DepositAmountDTO;
import com.example.demo.dto.UserWalletDTO;
import com.example.demo.dto.WalletDTO;
import com.example.demo.enums.UserStatus;
import com.example.demo.model.Order;
import com.example.demo.model.User;
import com.example.demo.model.Wallet;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.WalletService;
import com.example.demo.utility.ResponseHandler;

@RestController
public class WalletController {

	@Autowired
	private WalletService walletService;
	
	@Autowired
	private UserRepository userRepository;
	
	@RequestMapping(value = "/addwallet", method = RequestMethod.POST)
	public ResponseEntity<Object> addWallet(@RequestBody WalletDTO walletDTO)
	{
		Map<String, Object> result = null;
		try 
		{
			System.out.println("111111111111111111111111");
			result = walletService.addWallet(walletDTO);

			if (result.get("isSuccess").equals(true))
			{
				return ResponseHandler.generateResponse(HttpStatus.OK, true, result.get("message").toString(), result);
			} else {
				return ResponseHandler.generateResponse(HttpStatus.OK, false, result.get("message").toString(), result);
			}

		} catch (Exception e) {
			return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, false, e.getMessage(), result);
		}
	}
	
	@RequestMapping(value="/depositamount", method=RequestMethod.POST)
	public String deposit(@RequestBody DepositAmountDTO depositAmountDTO)
	{
		try
		{
			User user=userRepository.findByUserId(depositAmountDTO.getUserId());
			if(user != null)
			{
				if(user.getStatus().equals(UserStatus.ACTIVE))
				{
					return walletService.deposit(depositAmountDTO);
				}
				else
				{
					return "User Inactive deposit won't be complete.";
				}	
			}
		}
		catch(Exception e)
		{
			return "User does not exist.";
		}
		return "User does not exist.";
	}
	
	@RequestMapping(value = "/getallwallets", method = RequestMethod.GET)
	public List<Wallet> getAllWallets()
	{
		return walletService.getAllWallets();
	}
	
	@RequestMapping(value = "/wallethistory", method = RequestMethod.POST)
	public Object getWalletHistory(@RequestBody UserWalletDTO userWalletDTO )
	{
		List<Order> orderHistory = walletService.getWalletHistory(userWalletDTO);
		if(orderHistory.isEmpty())
		{
			return "No order available for this user.";
		}
		else
		{
			return orderHistory;
		}
	}
}
