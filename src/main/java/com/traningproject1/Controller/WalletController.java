package com.traningproject1.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.traningproject1.demo.dto.AssignWalletDTO;
import com.traningproject1.demo.dto.WalletApprovalDTO;
import com.traningproject1.enumsclass.CoinType;
import com.traningproject1.service.ServiceClass;
import com.traningproject1.service.WalletService;

@RestController
public class WalletController {
	@Autowired
	ServiceClass serviceClass;
	@Autowired
	WalletService walletService;
	
	
	@RequestMapping(value="/walletapproval",method=RequestMethod.POST)
	public void walletApproval(@RequestBody  WalletApprovalDTO walletapprovaldto)
	{
	
		walletService.walletApproval(walletapprovaldto);
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
