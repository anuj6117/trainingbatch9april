package com.traningproject1.service;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.traningproject1.domain.CurrencyClass;
import com.traningproject1.domain.Transaction;
import com.traningproject1.domain.UserOrder;
import com.traningproject1.repository.CurrencyRepository;
import com.traningproject1.repository.TransactionRepository;
import com.traningproject1.repository.UserOrderRepository;
import com.traningproject1.repository.UserRepository;
import com.traningproject1.repository.WalletRepository;

@Service
public class TransactionService {

@Autowired
TransactionRepository transactionRepository;
@Autowired
UserOrderRepository userOrderRepository;
@Autowired
WalletRepository walletRepository; 
@Autowired
UserRepository userRepository;
@Autowired
CurrencyRepository currencyRepoistory;
public List<Transaction> getAllTransaction()
{
	
	return transactionRepository.findAll();
}
//public String walletApproval(WalletApprovalDTO walletapprovaldto)
//{
//  UserOrder userorder=userOrderRepository.findByuserorderId(walletapprovaldto.getUserorderId());
//  User user=userorder.getUser();
//  if(user.getStatus().equals(UserStatus.INACTIVE))
//  {
//	  return "User is Not Authorized or Inactive";
//  }
//  Transaction transaction=new Transaction();
//  if(walletapprovaldto.getTransactionStatus().equals(TransactionStatus.APPROVED))
//  {
//      userorder.setStatus(UserOrderStatus.APPROVED);
//  
//       transaction.setCoinType(CoinType.FIATE);
//  
//     Set<Wallet> walletlist=user.getWallet();
//     Iterator <Wallet> itr=walletlist.iterator();
//     while(itr.hasNext())
//      {
//	   Wallet wallet=itr.next();
//	   if(wallet.getCoinType().equals(CoinType.FIATE))
//	   {
//		   wallet.setBalance(userorder.getCoinQuantity());
//		   wallet.setCoinName(userorder.getCoinName());
//		   wallet.setShadowBalance(userorder.getCoinQuantity());
//		   walletRepository.save(wallet); 
//	   }
//    }
//  }
//  else
//  {
//	  userorder.setStatus(UserOrderStatus.FAILED);
//  }
//  
//    transaction.setStatus(walletapprovaldto.getTransactionStatus());
//    transaction.setMessage(walletapprovaldto.getMessage());
//    transaction.setUserOrderType(userorder.getOrderType());
//    transaction.setNetAmount(userorder.getCoinQuantity());
//    transaction.setDateCreated(new Date());
//  transactionRepository.save(transaction);
//  userOrderRepository.save(userorder);
//  userRepository.save(user);
//  return "Success";	
//}
 public String transactionApproval()
 {
	List<UserOrder>listbuyer=userOrderRepository.getBuyers("BUYER");
	//List<UserOrder>listseller=userOrderRepository.getAllStatus(UserOrderType.SELLER);
	List<UserOrder>listseller=userOrderRepository.getSellers("SELLER");
	List<CurrencyClass>tempcurrency=currencyRepoistory.findAll();
//   	if(listseller==null)
//   	{
   	System.out.println("111111111111111111111111111111111111111");  	
   	  	 Iterator<UserOrder>listbuy=listbuyer.iterator();
   	  	while(listbuy.hasNext())
   	  	{
   	  	System.out.println("22222222222222222222222222222222");
   	  		UserOrder list=listbuy.next();
   	  	System.out.println("23333333333333333333333333333333333");
   	  		String coin=list.getCoinName();
   	  	System.out.println("44444444444444444444444444444444444");
   	  		System.out.println(list.getCoinName()+" "+list.getFees()+"  "+list.getGrossAmount()+"  "+list.getDateCreated()+" "+list.getPrice());
//   	  		Iterator<CurrencyClass>supply=tempcurrency.iterator();
//   	  		while(supply.hasNext())
//   	  		{
//   	  			CurrencyClass currencycoin=supply.next();
//   	  			
//   	  			if(currencycoin.getCoinName().equals(coin)&&currencycoin.getInitialSupply()!=0)
//   	  			{
//   	  			  if(list.getPrice()>=currencycoin.getPrice())
//   	  			  {
//   	  				  
//   	  			  
//   	  			  }
//   	  			}
//   	  		}

   		System.out.println("nullllllllllllllllllllllllllllllllll");
   		}
   	  System.out.println("notttttttttttttttttttttttttttttttttt");	
   	//}
   	return "Success";	
}
}
