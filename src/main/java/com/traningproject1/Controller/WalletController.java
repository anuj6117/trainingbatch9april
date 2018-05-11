package com.traningproject1.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.traningproject1.demo.dto.AssignWalletDTO;
import com.traningproject1.demo.dto.WalletApprovalDTO;
import com.traningproject1.domain.User;
import com.traningproject1.domain.UserOrder;
import com.traningproject1.enumsclass.CoinType;
import com.traningproject1.enumsclass.UserStatus;
import com.traningproject1.repository.UserOrderRepository;
import com.traningproject1.repository.UserRepository;
import com.traningproject1.service.ServiceClass;
import com.traningproject1.service.WalletService;

@RestController
public class WalletController {
	@Autowired
	ServiceClass serviceClass;
	@Autowired
	WalletService walletService;
	@Autowired
	UserOrderRepository userOrderRepository;
	@Autowired
	UserRepository userRepository;
	@RequestMapping(value="/walletapproval",method=RequestMethod.POST)
	public String walletApproval(@RequestBody  WalletApprovalDTO walletapprovaldto)
	{
		UserOrder userorder=userOrderRepository.findByuserorderId(walletapprovaldto.getUserorderId());
		if(userorder==null)
		{
			return "Invalid UserOrder Id";
		}
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
		User user=userRepository.findByUserId(assignwalletdto.getUserId());
		if(user==null)
		{
			return "Invalid UserId";
		}
		if(!(assignwalletdto.getCoinType().equals(CoinType.FIAT)))
		{
		 return  walletService.assignWallet(assignwalletdto);
		}
		return "Wallet Not Assign";
	}

}
