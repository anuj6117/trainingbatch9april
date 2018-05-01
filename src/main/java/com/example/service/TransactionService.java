package com.example.service;

import java.util.Date;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.enums.StatusType;
import com.example.enums.WalletType;
import com.example.model.Currency;
import com.example.model.Transaction;
import com.example.model.User;
import com.example.model.UserOrder;
import com.example.model.Wallet;
import com.example.repository.CurrencyRepository;
import com.example.repository.OrderRepository;
import com.example.repository.TransactionRepository;
import com.example.repository.WalletRepository;

@Service
public class TransactionService
{   @Autowired
	private OrderRepository orderRepository;
    @Autowired
    private static  TransactionRepository transactionRepository;
    @Autowired
    private static WalletRepository walletRepository;
    @Autowired
    private CurrencyRepository currencyRepository;
    
    
   public String transactionMethod()
   {
	  // validation for coin name remaining
    Set<UserOrder> sellList=orderRepository.sellList("SELL","PENDING");
    for(UserOrder sell:sellList)
    
    {  String coinName=sell.getCoinName();
	    int sPrice=sell.getPrice();
	    Set<UserOrder> buyList=orderRepository.buyList("BUY","PENDING",coinName);
	    int sellerQuantity=sell.getCoinQuantity();
	    Currency currency=currencyRepository.findByCoinName(sell.getCoinName());
	    int adminPrice=currency.getPrice();
	    for(UserOrder buy:buyList)
	    {
	       int bPrice=buy.getPrice();
	       
	       if(sPrice<=bPrice && (bPrice<adminPrice))
	        {
		      int buyerQuantity=buy.getCoinQuantity();
		      if(buyerQuantity==sellerQuantity)
		      {
			    buy.setStatusType(StatusType.APPROVED);
			    sell.setStatusType(StatusType.APPROVED);
			    orderRepository.save(buy);
			    orderRepository.save(sell);

			    setTransaction(buy, sell,buyerQuantity);// transaction table entry
		        walletUpdation(buy, sell,buyerQuantity); // updation in wallet
		         
		       }
		       else if(buyerQuantity>=sellerQuantity)
		       {
			      int quantityDone=sellerQuantity;
			      int quantityRemaining=buyerQuantity-sellerQuantity;
                  buy.setStatusType(StatusType.PENDING);
                  buy.setCoinQuantity(quantityRemaining);
                  orderRepository.save(buy);
                  sell.setStatusType(StatusType.APPROVED);
                  sell.setCoinQuantity(quantityDone);
                  orderRepository.save(sell);
			      setTransaction(buy, sell, quantityDone);
			      walletUpdation(buy, sell, quantityDone);
		       }
		       else// seller quantity >buyer Quantity
		       {
		    	   int quantityDone=buyerQuantity;
		    	   int quantityRemaining=sellerQuantity-buyerQuantity;
		    	   buy.setStatusType(StatusType.APPROVED);
		    	   buy.setCoinQuantity(quantityDone);
		    	   sell.setStatusType(StatusType.PENDING);
		    	   sell.setCoinQuantity(quantityRemaining);
		    	   orderRepository.save(buy);
		    	   orderRepository.save(sell);
		    	   setTransaction(buy, sell, quantityDone);
				   walletUpdation(buy, sell, quantityDone);
		       }
		      
		      
		      
	        } 
	        else
	        	return "no buyer for this seller";
	     } 
	  //here we have userOrderSell as seller object and userOrderBuy as buyer object
	  
	 
	  
  }
   return "";
	 
 }
  
 public static void setTransaction(UserOrder userOrderBuy,UserOrder userOrderSell, int quantity)
 {
	 String date =new Date()+" ";
    Transaction transaction=new Transaction();
     transaction.setBuyerId(userOrderBuy.getUser().getUserId());
     transaction.setSellerId(userOrderSell.getUser().getUserId());
     transaction.setCoinName(userOrderBuy.getCoinName());
     transaction.setCoinType(userOrderBuy.getCoinType());
     transaction.setExchangeRate(userOrderBuy.getPrice());
     transaction.setNetAmount(userOrderBuy.getNetAmount());
     transaction.setGrossAmount(userOrderBuy.getGrossAmount());
     transaction.setCoinQuantity(quantity);
     transaction.setDescription("purchase done");
     transaction.setStatus(StatusType.COMPLETED);
     transaction.setTransactionCreatedOn(date);
     transactionRepository.save(transaction);
 }
 
 
  public static void walletUpdation(UserOrder userOrderBuy,UserOrder userOrderSell,int quantity)
   {
	  
	  User user1=userOrderBuy.getUser();
      Set<Wallet> sett=user1.getWallet();
      for(Wallet w:sett)
       {
     	 if(w.getWalletType()==userOrderBuy.getCoinType())
     	  {
     		 int balance=w.getBalance();
     		 balance+=quantity;
     		 w.setBalance(balance);
     		
     	  }
     	 else if(w.getWalletType()==WalletType.FIAT)
     	  {
     		 int balance=userOrderBuy.getGrossAmount();
     		 int buyerBalance=w.getBalance();
     		 buyerBalance=buyerBalance-balance;
     		 w.setBalance(buyerBalance);
     		 w.setShadowbalance(buyerBalance);
     	  }
     	 walletRepository.save(w);
     	 
       }
      
      User user2=userOrderSell.getUser();
      Set<Wallet> sset=user1.getWallet();
      for(Wallet w:sset)
       {
     	 if(w.getWalletType()==userOrderSell.getCoinType())
     	  {  int balance=w.getBalance();
     	      balance=balance-quantity;
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
     	 walletRepository.save(w);
       }
    
   }
 
 
}
