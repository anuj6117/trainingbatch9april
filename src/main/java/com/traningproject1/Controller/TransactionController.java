package com.traningproject1.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.traningproject1.domain.Transaction;
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
//	@MockBean
//    private TransactionService transactionService;
@RequestMapping(value="/showalltransaction",method=RequestMethod.GET)
public List<Transaction>getAllTransaction()
{
	return transactionService.getAllTransaction();
}
//@RequestMapping(value="/walletapproval",method=RequestMethod.POST)
//public void walletApproval(@RequestBody  WalletApprovalDTO walletapprovaldto)
//{
//	transactionService.walletApproval(walletapprovaldto);
//}
@RequestMapping(value="/transaction",method=RequestMethod.POST)
public String transaction()
{
	transactionCalculation.transaction();
 	return "Transaction Action has been Approved";
}
}
