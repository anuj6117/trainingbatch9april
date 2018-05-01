package com.example.service;

import java.util.Date;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.enums.StatusType;
import com.example.enums.WalletType;
import com.example.model.Transaction;
import com.example.model.User;
import com.example.model.UserOrder;
import com.example.model.Wallet;
import com.example.repository.OrderRepository;

@Service
public class TransactionService
{   @Autowired
	private OrderRepository orderRepository;
 public String transactionMethod()
 {
  Set<UserOrder> sellList=orderRepository.useList("SELL");
  for(UserOrder sell:sellList)
  {
	  UserOrder userOrderSell=sell; UserOrder userOrderBuy=null;
	  System.out.println("rrrrrrrrrrrrrrrrr"+userOrderSell);
	  int sPrice=sell.getPrice();
	 Set<UserOrder> buyList=orderRepository.useList("BUY");
	 int bPrice=0;
	  for(UserOrder buy:buyList)
	  {
		 
		 if(buy.getPrice()>=sPrice)
		 {
			 bPrice=sell.getPrice();
			 userOrderBuy=buy;
			 System.out.println("rrrrrrrrrrrrrrrrr"+userOrderBuy);
			 
		 }
		  
	  }
	  
     if(userOrderBuy.getCoinQuantity()==userOrderSell.getCoinQuantity())
      {
    	 String date =new Date()+" ";
    	 userOrderBuy.setStatusType(StatusType.APPROVED);
    	 userOrderSell.setStatusType(StatusType.APPROVED);
         Transaction transaction=new Transaction();
         transaction.setBuyerId(userOrderBuy.getUser().getUserId());
         transaction.setSellerId(userOrderSell.getUser().getUserId());
         transaction.setCoinName(userOrderBuy.getCoinName());
         transaction.setCoinType(userOrderBuy.getCoinType());
         transaction.setExchangeRate(userOrderBuy.getPrice());
         transaction.setExchangeRate(userOrderBuy.getPrice());
         transaction.setNetAmount(userOrderBuy.getNetAmount());
         transaction.setGrossAmount(userOrderBuy.getGrossAmount());
         transaction.setCoinQuantity(userOrderBuy.getCoinQuantity());
         transaction.setDescription("purchase done");
         transaction.setStatus(StatusType.COMPLETED);
         transaction.setTransactionCreatedOn(date);
         transaction.setTransactionFee(userOrderBuy.getFees());
         User user1=userOrderBuy.getUser();
         Set<Wallet> sett=user1.getWallet();
         for(Wallet w:sett)
          {
        	 if(w.getWalletType()==userOrderBuy.getCoinType())
        	  {
        		 w.setBalance(userOrderBuy.getCoinQuantity());
        		
        	  }
        	 else if(w.getWalletType()==WalletType.FIAT)
        	  {
        		 int balance=userOrderBuy.getGrossAmount();
        		 int buyerBalance=w.getBalance();
        		 buyerBalance=buyerBalance-balance;
        		 w.setBalance(buyerBalance);
        		 w.setShadowbalance(buyerBalance);
        	  }
          }
         
         User user2=userOrderSell.getUser();
         Set<Wallet> sset=user1.getWallet();
         for(Wallet w:sset)
          {
        	 if(w.getWalletType()==userOrderSell.getCoinType())
        	  {  int balance=w.getBalance();
        	      balance=balance-userOrderSell.getCoinQuantity();
        		 w.setBalance(balance);
        		
        	  }
        	 else if(w.getWalletType()==WalletType.FIAT)
        	  {
        		 int balance=userOrderSell.getGrossAmount();
        		 int sellerBalance=w.getBalance();
        		 sellerBalance=sellerBalance+balance;
        		 w.setBalance(sellerBalance);
        		 w.setShadowbalance(sellerBalance);
        	  }
          }
         
         }

	  
  }
   return "";
	 
 }
}
