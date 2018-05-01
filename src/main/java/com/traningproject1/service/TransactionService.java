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
	 Transaction transaction=new Transaction();
	
	List<UserOrder>listbuyer=userOrderRepository.getBuyers("BUYER");
	
	//List<UserOrder>listseller=userOrderRepository.getSellers("SELLER");
	
	List<CurrencyClass>tempcurrency=currencyRepoistory.findAll();
	
	  //Transaction When there is no seller in Order Table/////
	 //////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////
	 
	//if(listseller==null)
//   	{ 	
   	  	 Iterator<UserOrder>listbuy=listbuyer.iterator();
   	  	while(listbuy.hasNext())
   	  	{
   	  		UserOrder buyer=listbuy.next();
   	  		User user=buyer.getUser();
   	  	    Wallet wallet=walletRepository.findByUserAndCoinType(user,CoinType.FIATE);
   	    
   	  	    Wallet wallet1=walletRepository.findByUserAndCoinTypeAndCoinName(user,CoinType.CRYPTO,buyer.getCoinName());
   	  	    String coin=buyer.getCoinName();
   	  	  
   	  	 //Coding Start from here At 11.02am 1 may//		
          
   	  	  Iterator<CurrencyClass>supply=tempcurrency.iterator();
   	  		while(supply.hasNext())
   	  		{
   	  			CurrencyClass currencycoin=supply.next();
   	  			
   	  		if(currencycoin.getCoinName().equals(coin))
   	  			{
//   	  			  if(list.getPrice()>currencycoin.getPrice())
//   	  			  {
//   	  				  double fee=currencycoin.getFees();
//   	  				  double gross= list.getGrossAmount();
//   	  			  }
   	  			  if(buyer.getPrice()==currencycoin.getPrice())
   	  			  {
   	  				 
   	  					 
     	      		 currencycoin.setCoinInINR(currencycoin.getCoinInINR());
   	  			     currencycoin.setProfit(buyer.getFees());
   	  				 currencycoin.setInitialSupply(0);
   	  			      
   	  				 	  				 
     				 wallet.setBalance((wallet.getBalance()-buyer.getGrossAmount()));
   	  				 wallet1.setBalance((buyer.getCoinQuantity()+wallet1.getBalance()));
   	  				 wallet1.setShadowBalance((buyer.getCoinQuantity()+wallet1.getShadowBalance()));
   	  			     
   	  				 
   	  			     
   	  				  }
   	  			  else if(buyer.getPrice()>currencycoin.getPrice())
   	  			   {
   	  				 //tr 
   	  			   }
   	  			
	  				 
   	  			
	  				 transaction.setBuyerId(user.getUserId());
	  				 transaction.setCoinType(CoinType.CRYPTO);
	  				 transaction.setCoinName(buyer.getCoinName());
	  				 transaction.setSellerId(null);
	  				 transaction.setExchangeRate(buyer.getPrice());
	  				 transaction.setFees(buyer.getFees());
	  				 transaction.setGrossAmount(buyer.getGrossAmount());
	  				 transaction.setNetAmount(buyer.getCoinQuantity()*buyer.getPrice());
	  				 transaction.setDateCreated(new Date());
	  				 transaction.setMessage("Transaction Done");
	  				 transaction.setStatus(TransactionStatus.APPROVED);
	  				 transaction.setUserOrderType(buyer.getOrderType());
	  	             
	  				  transactionRepository.save(transaction);
	  				  walletRepository.save(wallet);
	  				  walletRepository.save(wallet1);
	  				  currencyRepoistory.save(currencycoin);
   	  			  }
   	  			}
   	  		}

   	  	
   	//}
   	return "Success";	 
 }

 
private void transactionDetail(Wallet wallet, Wallet wallet1, User user, UserOrder buyer) {
	         
	       
	
}
private void wallet(Wallet wallet,Wallet wallet1,UserOrder buyer)
 {

		
 }
private void coinManagement(CurrencyClass currencycoin,UserOrder order)
{
	 
}
 private void userOrderTable(UserOrder order)
 {
	 
 }
}

