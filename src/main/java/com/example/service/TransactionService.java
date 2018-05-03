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
    private  TransactionRepository transactionRepository;
    @Autowired
    private   WalletRepository walletRepository;
    @Autowired
    private CurrencyRepository currencyRepository;
    
    
   public void transactionMethod()
   {
    
	   Set<UserOrder> buyList=orderRepository.buyList("BUY","PENDING");
	   System.out.println("value of buyList..................................."+buyList);
	   if(buyList.isEmpty())
	   {
		   throw new RuntimeException("Buyer are not available");
		  
	   }
	   for(UserOrder buy:buyList)
	   {
		   Integer bPrice=buy.getPrice();
		   Integer adminPrice=0; Integer adminQuantity = 0;
		   Currency currency=currencyRepository.findByCoinName(buy.getCoinName());
		   if(currency==null)
		   {
			   System.out.println("coin name is not available with admin");
		   }
		   else
		   {   
		    adminPrice=currency.getPrice();
		    adminQuantity=currency.getInitialSupply();
		   }
		   Set<UserOrder> sellList=orderRepository.sellList("SELL","PENDING",buy.getCoinName());
		   System.out.println("value of buyList..................................."+sellList);
		   if(sellList.isEmpty())
		   {
			   System.out.println("11.....................................");
			   if(bPrice>=adminPrice)
			   {
				   System.out.println("11.....................................");
			  // throw new RuntimeException("Seller are not available");
				   Integer quantityDone=buy.getCoinQuantity();
				   Integer quantityRemaining=adminQuantity-quantityDone;
       		    buy.setStatusType(StatusType.APPROVED);
       	     	currency.setInitialSupply(quantityRemaining);
       	        Integer fee=currency.getFees();
       	        Integer netamount=quantityDone*buy.getPrice();
       	        Integer feeAmount=((fee*netamount)/100);
       	        Integer currencyNetAmount=quantityDone*currency.getPrice();
       	        Integer NetAmount=netamount-currencyNetAmount;
        		currency.setCoinInINR(NetAmount);
        		currency.setProfit(feeAmount);
        		orderRepository.save(buy);
        		currencyRepository.save(currency);
        		String date =new Date()+" ";
        		Transaction transaction=new Transaction();
        		transaction.setBuyerId(buy.getUser().getUserId());
        		transaction.setSellerId(0); // admin ki seller id
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
       	       {System.out.println("11.....................................11");
       	     	 if(w.getWalletType()==buy.getCoinType())
       	     	  {System.out.println("11.....................................22");
       	     		Integer balance=w.getBalance();
       	     		 balance+=quantityDone;
       	     		 w.setBalance(balance);
       	     		 w.setShadowbalance(balance);
       	     	  }
       	     	 if(w.getWalletType()==WalletType.FIAT)
       	     	  {System.out.println("11.....................................33");
       	     		Integer balance=buy.getGrossAmount();
       	     	    Integer buyerBalance=w.getBalance();
       	     		 buyerBalance=buyerBalance-balance;
       	     		 w.setBalance(buyerBalance);
       	     		 w.setShadowbalance(buyerBalance);
       	     	  }
       	     	 walletRepository.save(w);
       	     	 
       	       }
			   }else
			    {
				  // return "buyer in pending stage";
			    }
			  
		   }
		  else
		  {	   
		   for(UserOrder sell:sellList)
		   {
			   Integer sPrice=sell.getPrice();
			   if(bPrice<sPrice && bPrice<adminPrice)
			   {
				  // return "buyer order will be in pending stage";
			   }
			   /*else if(adminPrice<=sPrice)*/
			   else if(sPrice>=adminPrice)
			   {   if((bPrice>=adminPrice))
			       {
				   // one case left here is that when admin quantity is insufficient
				  
		            	Integer quantityDone=buy.getCoinQuantity();
		        	    Integer quantityRemaining=adminQuantity-quantityDone;
		        		buy.setStatusType(StatusType.APPROVED);
		        		currency.setInitialSupply(quantityRemaining);
		        		Integer fee=currency.getFees();
		        		Integer netamount=quantityDone*buy.getPrice();
		        		Integer feeAmount=((fee*netamount)/100);
		        		Integer currencyNetAmount=quantityDone*currency.getPrice();
		        		Integer NetAmount=netamount-currencyNetAmount;
		        		Integer initialINR=currency.getCoinInINR();
		        		initialINR+=NetAmount;
		        		currency.setCoinInINR(initialINR);
		        		currency.setProfit(feeAmount);
		        		orderRepository.save(buy);
		        		currencyRepository.save(currency);
		        		String date =new Date()+" ";
		        		Transaction transaction=new Transaction();
		        		transaction.setBuyerId(buy.getUser().getUserId());
		        		transaction.setSellerId(currency.getCoinId()); // admin ki seller id
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
		        	     		 System.out.println("ssssssssssssssssssssssssss");
		        	     		 Integer balance=w.getBalance();
		        	     		 balance+=quantityDone;
		        	     		 w.setBalance(balance);
		        	     		 w.setShadowbalance(balance);
		        	     	  }
		        	     	 if(w.getWalletType()==WalletType.FIAT)
		        	     	  {
		        	     		 Integer balance=buy.getGrossAmount();
		        	     		 Integer buyerBalance=w.getBalance();
		        	     		 buyerBalance=buyerBalance-balance;
		        	     		 w.setBalance(buyerBalance);
		        	     		 w.setShadowbalance(buyerBalance);
		        	     	  }
		        	     	 walletRepository.save(w);
		        	     	 
		        	       }
		        	     // return "admin is seller here";
			       }
			       else
			        {
			    	   //return "buyer is pendingg";
			        }
		        	       
		        }
			   /*else if(adminPrice>sPrice && sPrice<bPrice)*/
			   else if(adminPrice>sPrice)
			    {
				   if(bPrice>sPrice)
				   {
					   System.out.println("22.....................................11");
				   Integer buyerQuantity=buy.getCoinQuantity();
				   Integer sellerQuantity=sell.getCoinQuantity();
				   //buyer purchasing from seller
				   if(buyerQuantity==sellerQuantity)
				      {
					   System.out.println("22.....................................2222");
					    buy.setStatusType(StatusType.APPROVED);
					    sell.setStatusType(StatusType.APPROVED);
					    orderRepository.save(buy);
					    orderRepository.save(sell);
					    System.out.println("22.....................................2222"); 
					    
					    Integer buyerAmount=buyerQuantity*buy.getPrice();
		        		Integer feeAmount=((currency.getFees()*buyerAmount)/100);
		        		Integer sellerAmount=sellerQuantity*sell.getPrice();
		        		Integer NetAmount=buyerAmount-sellerAmount;
		        		Integer initialINR=currency.getCoinInINR();
		        		System.out.println("intial value of coin INR............."+initialINR);
		        		initialINR+=NetAmount;
		        		currency.setCoinInINR(initialINR);
		        		System.out.println("updated vlue of coin inr.............."+currency.getCoinInINR());
		        		Integer InitialProfit=currency.getProfit();
		        		System.out.println("intial value of coin INR............."+InitialProfit);
		        		feeAmount+=InitialProfit;
		        		currency.setProfit(feeAmount);
		        		System.out.println("updated vlue of coin inr.............."+currency.getProfit());
		        		currencyRepository.save(currency);
					    
					    setTransaction(buy, sell,buyerQuantity);// transaction table entry
				        walletUpdation(buy, sell,buyerQuantity); // updation in wallet
				         
				       }
				       else if(buyerQuantity>sellerQuantity)
				       {
					      Integer quantityDone=sellerQuantity;
					      Integer quantityRemaining=buyerQuantity-sellerQuantity;
		                  buy.setStatusType(StatusType.PENDING);
		                  buy.setCoinQuantity(quantityRemaining);
		                  orderRepository.save(buy);
		                  sell.setStatusType(StatusType.APPROVED);
		                  sell.setCoinQuantity(quantityDone);
		                  orderRepository.save(sell);
		                    Integer buyerAmount=quantityDone*buy.getPrice();
			        		Integer feeAmount=((currency.getFees()*buyerAmount)/100);
			        		Integer sellerAmount=quantityDone*sell.getPrice();
			        		Integer NetAmount=buyerAmount-sellerAmount;
			        		Integer initialINR=currency.getCoinInINR();
			        		initialINR+=NetAmount;
			        		currency.setCoinInINR(initialINR);
			        		Integer InitialProfit=currency.getProfit();
			        		feeAmount+=InitialProfit;
			        		currency.setProfit(feeAmount);
			        		currencyRepository.save(currency);
						    
					      setTransaction(buy, sell, quantityDone);
					      walletUpdation(buy, sell, quantityDone);
				       }
				       else// seller quantity >buyer Quantity
				       {
				    	   Integer quantityDone=buyerQuantity;
				    	   Integer quantityRemaining=sellerQuantity-buyerQuantity;
				    	   buy.setStatusType(StatusType.APPROVED);
				    	   buy.setCoinQuantity(quantityDone);
				    	   sell.setStatusType(StatusType.PENDING);
				    	   sell.setCoinQuantity(quantityRemaining);
				    	   orderRepository.save(buy);
				    	   orderRepository.save(sell);
				    	   Integer buyerAmount=quantityDone*buy.getPrice();
			        		Integer feeAmount=((currency.getFees()*buyerAmount)/100);
			        		Integer sellerAmount=quantityDone*sell.getPrice();
			        		Integer NetAmount=buyerAmount-sellerAmount;
			        		Integer initialINR=currency.getCoinInINR();
			        		initialINR+=NetAmount;
			        		currency.setCoinInINR(initialINR);
			        		Integer InitialProfit=currency.getProfit();
			        		feeAmount+=InitialProfit;
			        		currency.setProfit(feeAmount);
			        		currencyRepository.save(currency);
						    
				    	   setTransaction(buy, sell, quantityDone);
						   walletUpdation(buy, sell, quantityDone);
				       }
				   }
				   else
				   {
					   // return "buyer is pending";
				   }
                 //  return  "";
				   
			    }
			   
			   
		   }
		  }
		   
	   }
	   
	   
 }
  
 public  void setTransaction(UserOrder userOrderBuy,UserOrder userOrderSell, Integer quantity)
 {
	 System.out.println("setTransaction.....................................");
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
     Integer grossAmount=userOrderBuy.getGrossAmount();
     Integer netAmount=userOrderBuy.getNetAmount();
     transaction.setTransactionFee(grossAmount-netAmount);
    
     transactionRepository.save(transaction);
     System.out.println("setTransaction.....................................end");
 }
 
 
  public  void walletUpdation(UserOrder userOrderBuy,UserOrder userOrderSell,Integer quantity)
   {
	  System.out.println("wallet.....................................");
	  User user1=userOrderBuy.getUser();
      Set<Wallet> sett=user1.getWallet();
      for(Wallet w:sett)
       {
     	 if(w.getWalletType()==userOrderBuy.getCoinType())
     	  {
     		 System.out.println("userid for wallet.(buy)..................."+w.getUser().getUserId());
     		Integer balance=w.getBalance();
     		 balance+=quantity;
     		 w.setBalance(balance);
     		 w.setShadowbalance(balance);
     		 System.out.println("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ balance"+w.getBalance());
     		 System.out.println("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ shadowBalance"+w.getShadowbalance());
     		 
    		 
     		
     	  }
     	 else if(w.getWalletType()==WalletType.FIAT)
     	  {
     		 System.out.println("userid for wallet.(buy)..................."+w.getUser().getUserId());
     		Integer balance=userOrderBuy.getGrossAmount();
     		Integer buyerBalance=w.getBalance();
     		 buyerBalance=buyerBalance-balance;
     		 w.setBalance(buyerBalance);
     		 w.setShadowbalance(buyerBalance);
     		 System.out.println("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ balance"+w.getBalance());
     		 System.out.println("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ shadowBalance"+w.getShadowbalance());
     	  }
     	 walletRepository.save(w);
     	 
       }
      
      User user2=userOrderSell.getUser();
      Set<Wallet> sset=user2.getWallet();
      for(Wallet w:sset)
       {
     	 if(w.getWalletType()==userOrderSell.getCoinType())
     	  {  System.out.println("userid for wallet.(sell)..................."+w.getUser().getUserId());
     		 Integer balance=w.getBalance();
     	      balance=balance-quantity;
     		 w.setBalance(balance);
     		 w.setShadowbalance(balance);
     		System.out.println("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ balance"+w.getBalance());
    		 System.out.println("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ shadowBalance"+w.getShadowbalance());
     		
     	  }
     	 else if(w.getWalletType()==WalletType.FIAT)
     	  {
     		System.out.println("userid for wallet.(sell)..................."+w.getUser().getUserId());
     		Integer balance=userOrderSell.getGrossAmount();
     		Integer sellerBalance=w.getBalance();
     		 sellerBalance=sellerBalance+balance;
     		 w.setBalance(sellerBalance);
     		 w.setShadowbalance(sellerBalance);
     		System.out.println("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ balance"+w.getBalance());
    		 System.out.println("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ shadowBalance"+w.getShadowbalance());
     	  }
     	 walletRepository.save(w);
       }
      System.out.println("setTransaction.....................................end");
    
   }
 
 
}
