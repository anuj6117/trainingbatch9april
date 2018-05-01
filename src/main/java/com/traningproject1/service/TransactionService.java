package com.traningproject1.service;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.traningproject1.domain.CurrencyClass;
import com.traningproject1.domain.Transaction;
import com.traningproject1.domain.User;
import com.traningproject1.domain.UserOrder;
import com.traningproject1.domain.Wallet;
import com.traningproject1.enumsclass.CoinType;
import com.traningproject1.enumsclass.TransactionStatus;
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
	 double total=0;
	
	List<UserOrder>listbuyer=userOrderRepository.getBuyers("BUYER");
	
	List<UserOrder>listseller=userOrderRepository.getSellers("SELLER");
	
	List<CurrencyClass>tempcurrency=currencyRepoistory.findAll();
	
	  //Transaction When there is no seller in Order Table/////
	 //////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////
	 
	//if(listseller==null)
//   	{ 	
   	  	 Iterator<UserOrder>listbuy=listbuyer.iterator();
   	  	while(listbuy.hasNext())
   	  	{
   	  		UserOrder list=listbuy.next();
   	  		User user=list.getUser();
   	  	    Wallet wallet=walletRepository.findByUserAndCoinType(user,CoinType.FIATE);
   	  		String coin=list.getCoinName();
   	  	  
   	  	 //Coding Start from here At 11.02am 1 may//		
          
   	  	  Iterator<CurrencyClass>supply=tempcurrency.iterator();
   	  		while(supply.hasNext())
   	  		{
   	  			CurrencyClass currencycoin=supply.next();
   	  			
   	  		if(currencycoin.getCoinName().equals(coin)&&currencycoin.getInitialSupply()!=0)
   	  			{
   	  			  if(list.getPrice()>currencycoin.getPrice())
   	  			  {
   	  				  double fee=currencycoin.getFees();
   	  				  double gross= list.getGrossAmount();
   	  			  }
   	  			  else if(list.getPrice()==currencycoin.getPrice()&&list.getCoinQuantity()==currencycoin.getInitialSupply())
   	  			  {
   	  					 Wallet wallet1=walletRepository.findByUserAndCoinTypeAndCoinName(user,CoinType.CRYPTO,list.getCoinName());
   	  					 
   	  					 ///Method of Transaction///
   	  				     transactionDetail(wallet,wallet1,user,list);
   	  	
   	  				     ////method of Wallet////
   	  				     ////////////////////////
   	  				     wallet(wallet,wallet1,list);
   	  				     
   	  				     coinManagement(currencycoin,list);
				
   	  				  }
   	  			  }
   	  			}
   	  		}

   	  	
   	//}
   	return "Success";	 
 }

 
private void transactionDetail(Wallet wallet, Wallet wallet1, User user, UserOrder buyer) {
	         
	         ////Saving Inside Transaction/////
			 /////////////////////////////////
			 ////////////////////////////////
			 
			 Transaction transaction=new Transaction();
			 
			 transaction.setBuyerId(user.getUserId());
			 transaction.setCoinType(CoinType.CRYPTO);
			 transaction.setCoinName(buyer.getCoinName());
			 transaction.setSellerId(null);
			 transaction.setExchangeRate(buyer.getPrice());
			 transaction.setFees(buyer.getFees());
			 transaction.setNetAmount(buyer.getGrossAmount()-buyer.getFees());
			 transaction.setDateCreated(new Date());
			 transaction.setMessage("Transaction Done");
			 transaction.setStatus(TransactionStatus.APPROVED);
			 transaction.setUserOrderType(buyer.getOrderType());
             transactionRepository.save(transaction);
	
}
private void wallet(Wallet wallet,Wallet wallet1,UserOrder buyer)
 {

		  ///update in FIATE and CYPTO Wallet///
		 //////////////////////////////////////
		 /////////////////////////////////////
		 
		 wallet.setBalance((wallet.getBalance()-buyer.getGrossAmount()));
		 wallet1.setBalance((buyer.getCoinQuantity()+wallet1.getBalance()));
		 wallet1.setShadowBalance((buyer.getCoinQuantity()+wallet1.getShadowBalance()));
		 
	     walletRepository.save(wallet);
		 walletRepository.save(wallet1);
 }
private void coinManagement(CurrencyClass currencycoin,UserOrder order)
{
	 ////update in Currency Class Table//// 
		 //////////////////////////////////////
		 //////////////////////////////////////
		 currencycoin.setCoinInINR(currencycoin.getCoinInINR());
	     currencycoin.setProfit(order.getFees());
		 currencycoin.setInitialSupply(0);
}
 
}

