package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.User;
import com.example.demo.domain.UserOrder;
import com.example.demo.dto.AssignWalletDTO;
import com.example.demo.dto.WalletApprovalDTO;
import com.example.demo.enumeration.CoinType;
import com.example.demo.enumeration.UserStatus;
import com.example.demo.repository.UserOrderRepository;
import com.example.demo.service.ServiceClass;
import com.example.demo.service.WalletService;

@RestController
public class WalletController {
	@Autowired
	ServiceClass serviceClass;
	@Autowired
	WalletService walletService;
	@Autowired
	UserOrderRepository userOrderRepository;
	
	@RequestMapping(value="/walletapproval",method=RequestMethod.POST)
	public String walletApproval(@RequestBody  WalletApprovalDTO walletapprovaldto)
	{
		UserOrder userorder=userOrderRepository.findByuserorderId(walletapprovaldto.getUserorderId());
		  User user=userorder.getUser();
		 if(user.getStatus().equals(UserStatus.INACTIVE))
		   {
			  return "User is Not Authorized or Inactive";
		   }
//		 if(!(walletapprovaldto.getTransactionStatus().equals(TransactionStatus.APPROVED))||!(walletapprovaldto.getTransactionStatus().equals(TransactionStatus.PENDING))||
//				   (!walletapprovaldto.getTransactionStatus().equals(TransactionStatus.FAILED)))
//		   {
//			   return "Invalid transaction status";
//		   }
		walletService.walletApproval(walletapprovaldto);
		return "Your Transaction has been Approved";
	}
	
	
	@RequestMapping(value="/addwallet",method=RequestMethod.POST)
	public String assignWallet(@RequestBody AssignWalletDTO assignwalletdto)
	{
		if(!(assignwalletdto.getCoinType().equals(CoinType.FIATE)))
		{
		 return  walletService.assignWallet(assignwalletdto);
		}
		return "Wallet Not Assign";
	}

}
