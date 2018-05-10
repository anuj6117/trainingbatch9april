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
		   Double bPrice=buy.getPrice();
		   Double adminPrice=0.0; Double adminQuantity = 0.0;
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
		   System.out.println("value of sellList..................................."+sellList);
		   if(sellList.isEmpty())
		   {
			   System.out.println("seller list empty.....................................");
			   if((bPrice>=adminPrice)&&((buy.getCoinQuantity()>0)&&(currency.getInitialSupply()>0)))
			   {
				   System.out.println("admin is seller.....................................");
			  // throw new RuntimeException("Seller are not available");
				   Double quantityDone=buy.getCoinQuantity();
				   System.out.println("buy quantity:::::::::::::::::"+buy.getCoinQuantity());
				   Double quantityRemaining=adminQuantity-quantityDone;
				   System.out.println("buy remaining:::::::::::::::::"+quantityRemaining);
				   
       		    buy.setStatusType(StatusType.APPROVED);
       		    System.out.println("buy status:::::"+buy.getStatusType());
       	     	currency.setInitialSupply(quantityRemaining);
       	        Double fee=currency.getFees();
       	         System.out.println("fee of currency:::::::::::::"+fee);
       	        Double netamount=quantityDone*buy.getPrice();
       	          System.out.println("netamount:::::::::::::::::"+netamount);
       	        Double feeAmount=((fee*netamount)/100);
       	               System.out.println("feeAmount:::::::::::::"+feeAmount);
       	        Double currencyNetAmount=quantityDone*currency.getPrice();
       	          System.out.println("currencyNetAmount:::::::::::::::"+currencyNetAmount);
       	        Double NetAmount=netamount-currencyNetAmount;
       	           System.out.println("NetAmount:::::::::::::::"+NetAmount);
        		currency.setCoinInINR(NetAmount);
        		    System.out.println("currency.setCoinININr:::::::::::"+currency.getCoinInINR());
        		currency.setProfit(feeAmount);
        		System.out.println("currncy.getProfit::::::::::::::"+currency.getProfit());
        		orderRepository.save(buy);
        		currencyRepository.save(currency);
        		String date =new Date()+" ";
        		System.out.println("TRANSACTION TABLE start///////////////////");
        		Transaction transaction=new Transaction();
        		transaction.setBuyerId(buy.getUser().getUserId());
        		  System.out.println("buyer id:::::::::::::::::::::::"+transaction.getBuyerId());
        		transaction.setSellerId(0); // admin ki seller id
        		System.out.println("seller id:::::::::::::::::::::::"+transaction.getSellerId());
        		transaction.setCoinName(buy.getCoinName());
        		System.out.println("coin name :::::::::::::::::::::::"+transaction.getCoinName());
       	    	transaction.setCoinType(buy.getCoinType());
       	    	System.out.println("coin type:::::::::::::::::::::::"+transaction.getCoinType());
       	     	transaction.setExchangeRate(buy.getPrice());
       	        System.out.println("exchange rate:::::::::::::::::::::::"+transaction.getExchangeRate());
       	     	transaction.setNetAmount(buy.getNetAmount());
        	     System.out.println("net amount:::::::::::::::::::::::"+transaction.getNetAmount());
       		    transaction.setGrossAmount(buy.getGrossAmount());
        		 System.out.println("gross amount:::::::::::::::::::::::"+transaction.getGrossAmount());
       	     	transaction.setCoinQuantity(quantityDone);
        	     System.out.println("coin quantity:::::::::::::::::::::::"+transaction.getCoinQuantity());
       		    transaction.setTransactionFee(feeAmount);
        		 System.out.println("transaction fee:::::::::::::::::::::::"+transaction.getTransactionFee());
       	    	transaction.setDescription("purchase done");
       		    transaction.setStatus(StatusType.COMPLETED);
        		 System.out.println("status type:::::::::::::::::::::::"+transaction.getStatus());
       		    transaction.setTransactionCreatedOn(date);
       		    transactionRepository.save(transaction);		        		
       		   //setTransaction(userOrderBuy, userOrderSell, quantity);
       		   User user1=buy.getUser();
       		   
       	       Set<Wallet> sett=user1.getWallet();
       	       System.out.println("user id of buyer::::::::::::::::"+user1.getUserId());
       	       for(Wallet w:sett)
       	       {System.out.println("seller is empyt// admin seller// updating wallet//buyer wallet update in crypto.....................................");
       	     	 if(w.getCoinType()==buy.getCoinType())
       	     	  {System.out.println("11.....................................22");
       	     	     Double balance=w.getBalance();
       	     	     System.out.println("initial balance:::::::::::::::::::::::::"+balance);
       	     		 balance+=quantityDone;
       	     		 System.out.println("updated balance::::::::::::::::::::::::"+balance);
       	     		 w.setBalance(balance);
       	     		// System.out.println("w.get balance::::::::::::::::::::::::::::"+w.getBalance());
       	     		 System.out.println("balance(crypto)111........."+w.getBalance());
       	     		 w.setShadowbalance(balance);
       	     		System.out.println("balance(crypto)222........."+w.getShadowbalance());
       	     	  }
       	     	 if(w.getCoinType()==WalletType.FIAT)
       	     	  {System.out.println("seller is empyt// admin seller// updating wallet//buyer wallet update in crypto.....................................33");
       	     	     Double balance=buy.getGrossAmount();
       	     	     System.out.println("balnce:::::::::::::::::::"+balance);
       	             Double buyerBalance=w.getBalance();
       	             System.out.println("buyerbalance::::::::::::::::::::::::::"+buyerBalance);
       	     		 buyerBalance=buyerBalance-balance;
       	     		System.out.println(" updated buyerbalance::::::::::::::::::::::::::"+buyerBalance);
       	     		 w.setBalance(buyerBalance);
       	     	
       	     		System.out.println("balance(crypto)333........."+w.getBalance());
       	     		 //w.setShadowbalance(buyerBalance);
       	     		System.out.println("balance(crypto)444........."+w.getShadowbalance());
       	     	  }
       	     	 walletRepository.save(w);
       	     	 
       	       }
			   }else
			    {
				  // return "";
				   System.out.println("outer cconditions are not satisfied");
			    }
			  
		   }
		  else
		  {	   
		   for(UserOrder sell:sellList)
		   {
			   Double sPrice=sell.getPrice();
			   if(bPrice<sPrice && bPrice<adminPrice)
			   {
				  // return "buyer order will be in pending stage";
			   }
			   /*else if(adminPrice<=sPrice)*/
			   else if(sPrice>=adminPrice)
			   {   if((bPrice>=adminPrice))
			       {
				   System.out.println("Seller is available//admin is seller.........................................");
				   // one case left here is that when admin quantity is insufficient
				      
				        Double quantityDone=buy.getCoinQuantity();
				        Double quantityRemaining=adminQuantity-quantityDone;
		        		buy.setStatusType(StatusType.APPROVED);
		        		currency.setInitialSupply(quantityRemaining);
		        		Double fee=currency.getFees();
		        		Double netamount=quantityDone*buy.getPrice();
		        		Double feeAmount=((fee*netamount)/100);
		        		Double currencyNetAmount=quantityDone*currency.getPrice();
		        		Double NetAmount=netamount-currencyNetAmount;
		        		Double initialINR=currency.getCoinInINR();
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
		        	     	 if(w.getCoinType()==buy.getCoinType())
		        	     	  {
		        	     		Double balance=w.getBalance();
		        	     		 balance+=quantityDone;
		        	     		 w.setBalance(balance);
		        	     		 w.setShadowbalance(balance);
		        	     	  }
		        	     	 if(w.getCoinType()==WalletType.FIAT)
		        	     	  {
		        	     		Double balance=buy.getGrossAmount();
		        	     		Double buyerBalance=w.getBalance();
		        	     		 buyerBalance=buyerBalance-balance;
		        	     		 w.setBalance(buyerBalance);
		        	     		// w.setShadowbalance(buyerBalance);
		        	     	  }
		        	     	 walletRepository.save(w);
		        	     	 
		        	       }
		        	     // return "admin is seller here";
			       }
			       else
			        {
			    	   //return "buyer is pending";
			        }
		        	       
		        }
			   else if(adminPrice>sPrice)
			    {
				   aa:
				   if(bPrice>sPrice)
				   {System.out.println("Seller is a user here not admin//..........................");
					   System.out.println("22.....................................11");
					   
				   if(buy.getCoinQuantity()==sell.getCoinQuantity())
				      {
					   System.out.println("Seller is a user here not admin//buyerQUANt=sellerQUANT..........................");
					   System.out.println("22.....................................2222");
					    buy.setStatusType(StatusType.APPROVED);
					    sell.setStatusType(StatusType.APPROVED);
					    orderRepository.save(buy);
					    orderRepository.save(sell);
					    System.out.println("22.....................................2222"); 
					    
					    Double buyerAmount=buy.getCoinQuantity()*buy.getPrice();
					    Double feeAmount=((currency.getFees()*buyerAmount)/100);
					    Double sellerAmount=sell.getCoinQuantity()*sell.getPrice();
					    Double NetAmount=buyerAmount-sellerAmount;
					    Double initialINR=currency.getCoinInINR();
		        		System.out.println("intial value of coin INR............."+initialINR);
		        		initialINR+=NetAmount;
		        		currency.setCoinInINR(initialINR);
		        		System.out.println("updated vlue of coin inr.............."+currency.getCoinInINR());
		        		Double InitialProfit=currency.getProfit();
		        		System.out.println("intial value of profit............."+InitialProfit);
		        		feeAmount+=InitialProfit;
		        		currency.setProfit(feeAmount);
		        		System.out.println("updated vlue of profit.............."+currency.getProfit());
		        		currencyRepository.save(currency);
					    
					    setTransaction(buy, sell,buy.getCoinQuantity(),currency);// transaction table entry
				        walletUpdation(buy, sell,buy.getCoinQuantity(),currency); // updation in wallet
				        orderRepository.save(buy);
				        orderRepository.save(sell);
				         
				       }
				       else if(buy.getCoinQuantity()>sell.getCoinQuantity())
				       {  System.out.println("Seller is a user here not admin//buyerQUANt > sellerQUANT.........................");
				          System.out.println("BUYER'S QUANT HERE:::::::::::::::::::::::::"+buy.getCoinQuantity());
				          System.out.println("SELLER'S QUANT HERE:::::::::::::::::::::::::"+sell.getCoinQuantity());
				          Double quantityDone=sell.getCoinQuantity();
				          Double quantityRemaining=buy.getCoinQuantity()-sell.getCoinQuantity();
		                  buy.setStatusType(StatusType.PENDING);
		                 // buy.setCoinQuantity(quantityRemaining);          number 007
		                  orderRepository.save(buy);
		                  sell.setStatusType(StatusType.APPROVED);
		                  sell.setCoinQuantity(quantityDone);
		                  orderRepository.save(sell);
		                  //currency additon in profit and coin inr.........
		                  Double buyerAmount=quantityDone*buy.getPrice();
		                  Double feeAmount=((currency.getFees()*buyerAmount)/100);
		                  Double sellerAmount=quantityDone*sell.getPrice();
		                  Double NetAmount=buyerAmount-sellerAmount;
		                  Double initialINR=currency.getCoinInINR();
			        		initialINR+=NetAmount;
			        		currency.setCoinInINR(initialINR);
			        		Double InitialProfit=currency.getProfit();
			        		feeAmount+=InitialProfit;
			        		currency.setProfit(feeAmount);
			        		currencyRepository.save(currency);
						    
					      setTransaction(buy, sell, quantityDone,currency);
					      System.out.println("wallet.....................................");
						  User user1=buy.getUser();
					      Set<Wallet> sett=user1.getWallet();
					      for(Wallet w:sett)
					       {
					     	 if(w.getCoinType()==buy.getCoinType())// crypto add hoga
					     	  {
					     		 System.out.println("userid for wallet.(buy)..................."+w.getUser().getUserId());
					     		Double balance=w.getBalance();
					     		 balance+=quantityDone;
					     		 w.setBalance(balance);
					     		Double BuyQuantity=buy.getCoinQuantity();
					     		 buy.setCoinQuantity(BuyQuantity-balance);
					     		 w.setShadowbalance(balance);
					     		 System.out.println("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ balance"+w.getBalance());
					     		 System.out.println("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ shadowBalance"+w.getShadowbalance());
					     	  }
					     	 else if(w.getCoinType()==WalletType.FIAT) // money deduct hogi
					     	  {
					     		 System.out.println("userid for wallet.(buy)..................."+w.getUser().getUserId());
					     		Double fee=currency.getFees();
					     		Double moneyToBeDeducted=quantityDone*buy.getPrice();
					     		moneyToBeDeducted=moneyToBeDeducted+((moneyToBeDeducted*fee)/100);
					     		Double balance=moneyToBeDeducted;
					     		Double buyerBalance=w.getBalance();
					     		 buyerBalance=buyerBalance-balance;
					     		 if(buyerBalance<0)
					     		 {
					     			 break aa;
					     		 }
					     		 w.setBalance(buyerBalance);
					     		 System.out.println("||||||||||||||||||| shadow balance is........"+w.getShadowbalance());
					     		 System.out.println("||||||||||||||||||| buy.getGrossAmount........"+buy.getGrossAmount());
					     		 System.out.println("||||||||||||||||||| moneyToBeDeducted.........."+moneyToBeDeducted);
					     		 /*Integer addShadowBalance=buy.getGrossAmount()-moneyToBeDeducted;
					     		 Integer shadowBalance=w.getShadowbalance();
					     		shadowBalance+=addShadowBalance;
					     		 w.setShadowbalance(shadowBalance);*/
					     		 System.out.println("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ balance"+w.getBalance());
					     		 System.out.println("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ shadowBalance"+w.getShadowbalance());
					     	  }
					     	 walletRepository.save(w);
					     	 
					       }
					      
					      User user2=sell.getUser();
					      Set<Wallet> sset=user2.getWallet();
					      for(Wallet w:sset)
					       {
					     	 if(w.getCoinType()==sell.getCoinType())
					     	  {  System.out.println("userid for wallet.(sell)..................."+w.getUser().getUserId());
					     	 Double balance=w.getBalance();
					     		 
					     	      balance=balance-quantityDone;
					     	      if(balance<0)
					     	      {System.out.println("balance is negative....................97");
					     	    	break aa;  
					     	      }
					     		 w.setBalance(balance);
					     		 sell.setCoinQuantity(balance);
					     		// w.setShadowbalance(balance);
					     		System.out.println("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ balance"+w.getBalance());
					    		 System.out.println("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ shadowBalance"+w.getShadowbalance());
					     	  }
					     	 else if(w.getCoinType()==WalletType.FIAT)
					     	  {
					     		System.out.println("userid for wallet.(sell)..................."+w.getUser().getUserId());
					     		Double balance=sell.getGrossAmount();
					     		Double sellerBalance=w.getBalance();
					     		 sellerBalance=sellerBalance+balance;
					     		 w.setBalance(sellerBalance);
					     		 w.setShadowbalance(sellerBalance);
					     		System.out.println("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ balance"+w.getBalance());
					    		 System.out.println("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ shadowBalance"+w.getShadowbalance());
					     	  }
					     	 walletRepository.save(w);
					     	 
					       }
					      orderRepository.save(buy);
					      orderRepository.save(sell);
					      System.out.println("setTransaction.....................................end");
					     // walletUpdation(buy, sell, quantityDone);
				       }
				       else// seller quantity >buyer Quantity
				       {   
				    	   System.out.println("Seller is a user here not admin//buyerQUANt < sellerQUANT..........................");
				    	   System.out.println("Buyer quantity:::::::::::::::::::::"+buy.getCoinQuantity());
				    	   System.out.println("Seller quantity:::::::::::::::::::::"+sell.getCoinQuantity());
				    	   Double quantityDone=buy.getCoinQuantity();
				    	   Double quantityRemaining=sell.getCoinQuantity()-buy.getCoinQuantity();
				    	   System.out.println("quantityRemaining:::::::::::::::::::"+quantityRemaining);
				    	   buy.setStatusType(StatusType.APPROVED);
				    	   buy.setCoinQuantity(quantityDone);
				    	   sell.setStatusType(StatusType.PENDING);
				    	   sell.setCoinQuantity(quantityRemaining);
				    	   orderRepository.save(buy);
				    	   orderRepository.save(sell);
				    	   Double buyerAmount=quantityDone*buy.getPrice();
				    	   Double feeAmount=((currency.getFees()*buyerAmount)/100);
				    	   Double sellerAmount=quantityDone*sell.getPrice();
				    	   Double NetAmount=buyerAmount-sellerAmount;
				    	   Double initialINR=currency.getCoinInINR();
			        		initialINR+=NetAmount;
			        		currency.setCoinInINR(initialINR);
			        		Double InitialProfit=currency.getProfit();
			        		feeAmount+=InitialProfit;
			        		currency.setProfit(feeAmount);
			        		currencyRepository.save(currency);
						    
				    	   setTransaction(buy, sell, quantityDone,currency);
				    	   System.out.println("wallet.....................................");
				    		  User user1=buy.getUser();
				    	      Set<Wallet> sett=user1.getWallet();
				    	      for(Wallet w:sett)
				    	       {
				    	     	 if(w.getCoinType()==buy.getCoinType())// crypto add hoga
				    	     	  {
				    	     		 System.out.println("userid for wallet.(buy)..................."+w.getUser().getUserId());
				    	     		Double balance=w.getBalance();
				    	     		 balance+=quantityDone;
				    	     		 w.setBalance(balance);
				    	     		Double BuyQuantity=buy.getCoinQuantity();
						     		 
						     		 if((BuyQuantity-balance)<0)
						     		 {
						     			 buy.setCoinQuantity(0.0);
						     		 }else
						     			buy.setCoinQuantity(BuyQuantity-balance);
				    	     		 //buy.setCoinQuantity(balance);
				    	     		 w.setShadowbalance(balance);
				    	     		 System.out.println("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ balance"+w.getBalance());
				    	     		 System.out.println("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ shadowBalance"+w.getShadowbalance());
				    	     	  }
				    	     	 else if(w.getCoinType()==WalletType.FIAT) // money deduct hogi
				    	     	  {
				    	     		 System.out.println("userid for wallet.(buy)..................."+w.getUser().getUserId());
				    	     		Double balance=quantityDone*buy.getPrice();
				    	     		Double fee=currency.getFees();
				    	     		balance=balance+((fee*balance)/100);
				    	     		Double buyerBalance=w.getBalance();
				    	     		 buyerBalance=buyerBalance-balance;
				    	     		 if(buyerBalance<0)
				    	     		 {
				    	     			System.out.println("balance negative....................98");
				    	     			 break aa;
				    	     		 }
				    	     		 w.setBalance(buyerBalance);
				    	    
				    	     		// w.setShadowbalance(buyerBalance);
				    	     		 System.out.println("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ balance"+w.getBalance());
				    	     		 System.out.println("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ shadowBalance"+w.getShadowbalance());
				    	     	  }
				    	     	 walletRepository.save(w);
				    	       }
				    	      
				    	      User user2=sell.getUser();
				    	      Set<Wallet> sset=user2.getWallet();
				    	      for(Wallet w:sset)
				    	       {
				    	     	 if(w.getCoinType()==sell.getCoinType())
				    	     	  {  System.out.println("userid for wallet.(sell)..................."+w.getUser().getUserId());
				    	     	 Double balance=w.getBalance();
				    	     	      balance=balance-quantityDone;
				    	     		 w.setBalance(balance);
				    	     		 sell.setCoinQuantity(balance);
				    	     		/* Integer shadowBalance=w.getShadowbalance();
				    	     		   shadowBalance+=quantityRemaining;
				    	     		 w.setShadowbalance(shadowBalance);*/
				    	     		System.out.println("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ balance"+w.getBalance());
				    	    		 System.out.println("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ shadowBalance"+w.getShadowbalance());
				    	     	  }
				    	     	 else if(w.getCoinType()==WalletType.FIAT)
				    	     	  {
				    	     		System.out.println("userid for wallet.(sell)..................."+w.getUser().getUserId());
				    	     		/*Integer balance=sell.getGrossAmount();
				    	     		Integer sellerBalance=w.getBalance();
				    	     		 sellerBalance=sellerBalance+balance;*/
				    	     		Double sellerBalance=quantityDone*sell.getPrice();
				    	     		Double initialBalnce=w.getBalance();
				    	     		initialBalnce+=sellerBalance;
				    	     		 w.setBalance(initialBalnce);
				    	     		Double initialShadowbalance=w.getShadowbalance();
				    	     		initialShadowbalance+=sellerBalance;
				    	     		 w.setShadowbalance(initialShadowbalance);
				    	     		System.out.println("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ balance"+w.getBalance());
				    	    		 System.out.println("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ shadowBalance"+w.getShadowbalance());
				    	     	  }
				    	     	 walletRepository.save(w);
				    	       }
				    	      System.out.println("setTransaction.....................................end");
						  // walletUpdation(buy, sell, quantityDone);
				    	      orderRepository.save(buy);
				    	      orderRepository.save(sell);
				       }
				   }
				   else
				   {
					   // return "buyer is pending";
				   }
                   
			    }			   
		   }
		  }
	   }   
 }
  
 public  void setTransaction(UserOrder userOrderBuy,UserOrder userOrderSell, Double quantity,Currency currency)
 {
	 System.out.println("setTransaction.....................................");
	 String date =new Date()+" ";
    Transaction transaction=new Transaction();
     transaction.setBuyerId(userOrderBuy.getUser().getUserId());
     transaction.setSellerId(userOrderSell.getUser().getUserId());
     transaction.setCoinName(userOrderBuy.getCoinName());
     transaction.setCoinType(userOrderBuy.getCoinType());
     transaction.setExchangeRate(userOrderBuy.getPrice());
     Double netamount=quantity*userOrderBuy.getPrice();
     transaction.setNetAmount(/*userOrderBuy.getNetAmount()*/netamount);
     Double fee=currency.getFees();
     Double grossamount=((fee*netamount)/100)+netamount;
     transaction.setGrossAmount(grossamount);
     transaction.setCoinQuantity(quantity);
     transaction.setDescription("purchase done");
     transaction.setStatus(StatusType.COMPLETED);
     transaction.setTransactionCreatedOn(date);
     //Integer grossAmount=userOrderBuy.getGrossAmount();
     //Integer netAmount=userOrderBuy.getNetAmount();
     transaction.setTransactionFee(grossamount-netamount);
    
     transactionRepository.save(transaction);
     System.out.println("setTransaction.....................................end");
 }
 
 
  public  void walletUpdation(UserOrder userOrderBuy,UserOrder userOrderSell,Double quantity,Currency currency)
   {
	  System.out.println("walletUpdation METHOD.....................................");
	  User user1=userOrderBuy.getUser();
      Set<Wallet> sett=user1.getWallet();
      bb:
      for(Wallet w:sett)
       {
    	  System.out.println("w.getWalletType()..................."+w.getCoinType());
    	  System.out.println("userOrderBuy.getCoinType()............."+userOrderBuy.getCoinType());
     	 if(w.getCoinType()==userOrderBuy.getCoinType())// crypto add hoga
     	  {System.out.println("entering into crypto................");
     		 System.out.println("userid for wallet.(buy)(CRYPTO)..................."+w.getUser().getUserId());
     		Double balance=w.getBalance();
     		 balance+=quantity;
     		 w.setBalance(balance);
     		 userOrderBuy.setCoinQuantity(balance);
     		 w.setShadowbalance(balance);
     		 System.out.println("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ balance"+w.getBalance());
     		 System.out.println("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ shadowBalance"+w.getShadowbalance());
     	  }
     	 else if(w.getCoinType()==WalletType.FIAT) // money deduct hogi
     	  {
     		 System.out.println("userid for wallet.(buy)(FIAT)..................."+w.getUser().getUserId());
     		 System.out.println("Quantity:::::::::::::::::::::::::::::"+quantity);
     		 
     		Double updatedAmount=quantity*userOrderBuy.getPrice();
     		System.out.println("updateAmount:::::::::::::::::::::::::::::"+updatedAmount);
     		updatedAmount=(((currency.getFees()*updatedAmount)/100)+updatedAmount);
     		System.out.println("balance is::::::::::::::::::::"+updatedAmount);
     		Double buyerBalance=w.getBalance();
     		System.out.println("buyer balance is::::::::::::::::::::"+buyerBalance);
     		 buyerBalance=buyerBalance-updatedAmount;
     		System.out.println("buyer balance is::::::::::::::::::::"+buyerBalance);
     		 if(buyerBalance<0)
     		 {
     			System.out.println("balance negative....................99");
     			 break bb;
     		 }
     		 w.setBalance(buyerBalance);
     		//w.setShadowbalance(buyerBalance);
     		 System.out.println("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ balance"+w.getBalance());
     		 System.out.println("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ shadowBalance"+w.getShadowbalance());
     	  }
     	 walletRepository.save(w);
     	 orderRepository.save(userOrderBuy);
       }
      
      User user2=userOrderSell.getUser();
      Set<Wallet> sset=user2.getWallet();
      cc:
      for(Wallet w:sset)
       {
     	 if(w.getCoinType()==userOrderSell.getCoinType())
     	  {  System.out.println("userid for wallet.(sell)(CRYPTO)..................."+w.getUser().getUserId());
     	 Double balance=w.getBalance();
     	      balance=balance-quantity;
     	      if(balance<0)
     	      {
     	    	  
     	    	  System.out.println("balance negative....................100");
     	    	  break cc;
     	      }
     		 w.setBalance(balance);
     		 userOrderSell.setCoinQuantity(balance);
     		// w.setShadowbalance(balance);
     		System.out.println("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ balance"+w.getBalance());
    		 System.out.println("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ shadowBalance"+w.getShadowbalance());
     	  }
     	 else if(w.getCoinType()==WalletType.FIAT)
     	  {
     		System.out.println("userid for wallet.(sell)(FIAT)..................."+w.getUser().getUserId());
     		Double balance=userOrderSell.getGrossAmount();
     		Double sellerBalance=w.getBalance();
     		 sellerBalance=sellerBalance+balance;
     		 w.setBalance(sellerBalance);
     		 w.setShadowbalance(sellerBalance);
     		System.out.println("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ balance"+w.getBalance());
    		 System.out.println("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ shadowBalance"+w.getShadowbalance());
     	  }
     	 walletRepository.save(w);
     	 orderRepository.save(userOrderSell);
       }
      System.out.println("setTransaction.....................................end");
    
   }
 
 
}
