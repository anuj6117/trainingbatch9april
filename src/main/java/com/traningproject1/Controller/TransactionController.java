package com.traningproject1.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.traningproject1.service.TransactionCalculation;
import com.traningproject1.service.TransactionService;
import com.traningproject1.service.WalletService;

@RestController
public class TransactionController {
	@Autowired
	TransactionService transactionService;
	@Autowired
	WalletService walletService;
	@Autowired
	TransactionCalculation transactionCalculation;
@RequestMapping(value="/showalltransaction",method=RequestMethod.GET)
public List getAllTransaction()
{
	return transactionCalculation.getAllTransaction();
}
//@RequestMapping(value="/walletapproval",method=RequestMethod.POST)
//public void walletApproval(@RequestBody  WalletApprovalDTO walletapprovaldto)
//{
//	transactionService.walletApproval(walletapprovaldto);
//}
@RequestMapping(value="/transaction",method=RequestMethod.GET)
public String transaction()
{
	transactionService.gettransaction();
 	return "Transaction Action has been Approved";
}
}
