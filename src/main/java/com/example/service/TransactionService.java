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
    
    
   public void transactionMethod()
   {
	/*  // validation for coin name remaining
    Set<UserOrder> sellList=orderRepository.sellList("SELL","PENDING");
    System.out.println("selllist ki value.................."+sellList);
    for(UserOrder sell:sellList)
    
    {   String coinName=sell.getCoinName();
	    int sPrice=sell.getPrice();   //seller ka price
	    Set<UserOrder> buyList=orderRepository.buyList("BUY","PENDING",coinName);
	    System.out.println("buyList ki value...................."+buyList);
	    int sellerQuantity=sell.getCoinQuantity();
	    Currency currency=currencyRepository.findByCoinName(sell.getCoinName());
	    int adminPrice=currency.getPrice();
	    int adminQuantity=currency.getInitialSupply();
	    for(UserOrder buy:buyList)
	    {
	       int bPrice=buy.getPrice();
	       
	       if(sPrice<adminPrice)
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
	        else if(adminPrice<=sPrice)// seller purchasing from admin
	        {
	        	if(adminQuantity<sellerQuantity)
	        	{
	        	  
	        	}
	        	else
	        	{ int quantityDone=sell.getCoinQuantity();
	        	  int quantityRemaining=adminQuantity-quantityDone;
	        		sell.setStatusType(StatusType.APPROVED);
	        		currency.setInitialSupply(quantityRemaining);
	        		orderRepository.save(sell);
	        		currencyRepository.save(currency);
	        		//setTransaction(userOrderBuy, userOrderSell, quantity);
	        		
	        	}
	        }
	        else
	        {
	        	return "no buyer for this seller";
	        }
	     } 
	  //here we have userOrderSell as seller object and userOrderBuy as buyer object
	  
	 
	  
  }
   return "";
*/    
	   Set<UserOrder> buyList=orderRepository.buyList("BUY","PENDING");
	   System.out.println("value of buyList..................................."+buyList);
	   if(buyList.isEmpty())
	   {
		   throw new RuntimeException("Buyer are not available");
		  
	   }
	   for(UserOrder buy:buyList)
	   {
		   int bPrice=buy.getPrice();
		   Currency currency=currencyRepository.findByCoinName(buy.getCoinName());
		   int adminPrice=currency.getPrice();
		   int adminQuantity=currency.getInitialSupply();
		    
		   Set<UserOrder> sellList=orderRepository.sellList("SELL","PENDING",buy.getCoinName());
		   System.out.println("value of buyList..................................."+sellList);
		   if(sellList.isEmpty())
		   {
			   throw new RuntimeException("Seller are not available");
			  
		   }
		   for(UserOrder sell:sellList)
		   {
			   int sPrice=sell.getPrice();
			   if(bPrice<sPrice && bPrice<adminPrice)
			   {
				  // return "buyer order will be in pending stage";
			   }
			   else if(adminPrice<=sPrice)
			   {
				   // one case left here is that when admin quantity is insufficient
				  
		            	int quantityDone=buy.getCoinQuantity();
		        	    int quantityRemaining=adminQuantity-quantityDone;
		        		buy.setStatusType(StatusType.APPROVED);
		        		currency.setInitialSupply(quantityRemaining);
		        		int fee=currency.getFees();
		        		int netamount=quantityDone*buy.getPrice();
		        		int feeAmount=((fee*netamount)/100);
		        		int currencyNetAmount=quantityDone*currency.getPrice();
		        		int NetAmount=netamount-currencyNetAmount;
		        		currency.setCoinInINR(NetAmount);
		        		currency.setProfit(feeAmount);
		        		orderRepository.save(buy);
		        		currencyRepository.save(currency);
		        		String date =new Date()+" ";
		        		Transaction transaction=new Transaction();
		        		transaction.setBuyerId(buy.getUser().getUserId());
		        		//transaction.setSellerId(userOrderSell.getUser().getUserId()); // admin ki seller id
		        		transaction.setCoinName(buy.getCoinName());
		        		transaction.setCoinType(buy.getCoinType());
		        		transaction.setExchangeRate(buy.getPrice());
		        		transaction.setNetAmount(buy.getNetAmount());
		        		transaction.setGrossAmount(buy.getGrossAmount());
		        		transaction.setCoinQuantity(quantityDone);
		        		transaction.setTransactionFee(feeAmount);
		        		
		        		transaction.setDescription("purchase done");
		        		transaction.setStatus(StatusType.COMPLETED);
		        		transaction.setTransactionCreatedOn(date);
		        		transactionRepository.save(transaction);		        		
		        		//setTransaction(userOrderBuy, userOrderSell, quantity);
		        		 User user1=buy.getUser();
		        	      Set<Wallet> sett=user1.getWallet();
		        	      for(Wallet w:sett)
		        	       {
		        	     	 if(w.getWalletType()==buy.getCoinType())
		        	     	  {
		        	     		 int balance=w.getBalance();
		        	     		 balance+=quantityDone;
		        	     		 w.setBalance(balance);
		        	     	  }
		        	     	 if(w.getWalletType()==WalletType.FIAT)
		        	     	  {
		        	     		 int balance=buy.getGrossAmount();
		        	     		 int buyerBalance=w.getBalance();
		        	     		 buyerBalance=buyerBalance-balance;
		        	     		 w.setBalance(buyerBalance);
		        	     		 w.setShadowbalance(buyerBalance);
		        	     	  }
		        	     	 walletRepository.save(w);
		        	     	 
		        	       }
		        	     // return "admin is seller here";
		        	       
		        }
			   else if(adminPrice>sPrice && sPrice<bPrice)
			    {
				   int buyerQuantity=buy.getCoinQuantity();
				   int sellerQuantity=sell.getCoinQuantity();
				   //buyer purchasing from seller
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
				      
                 //  return  "";
				   
			    }
			   
			   
		   }
		   
	   }
	   
	   
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
     int grossAmount=userOrderBuy.getGrossAmount();
     int netAmount=userOrderBuy.getNetAmount();
     transaction.setTransactionFee(grossAmount-netAmount);
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
