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
import com.traningproject1.enumsclass.UserOrderStatus;
import com.traningproject1.repository.CurrencyRepository;
import com.traningproject1.repository.TransactionRepository;
import com.traningproject1.repository.UserOrderRepository;
import com.traningproject1.repository.WalletRepository;

@Service
public class TransactionCalculation {
	@Autowired
	UserOrderRepository userOrderRepository;
	@Autowired
	CurrencyRepository currencyRepository;
	@Autowired
	WalletRepository walletRepository;
	@Autowired
	TransactionRepository transactionRepository;
	public void transaction()
	{
		
		List<UserOrder> listbuyer=userOrderRepository.getBuyers("BUYER");
		
		List<UserOrder> listseller=userOrderRepository.getSellers("SELLER");
		
		//List<Wallet>wallet=walletRepository.

	    	Transaction transactionobject= new Transaction();

		if(listseller.isEmpty())
		{
			Iterator<UserOrder>itrbuy=listbuyer.iterator();
			while(itrbuy.hasNext())
			{
				UserOrder buyer=itrbuy.next();
				User user=buyer.getUser();
				Wallet wallet=walletRepository.findByUserAndCoinType(user,CoinType.FIATE);
	   	    	Wallet wallet1=walletRepository.findByUserAndCoinTypeAndCoinName(user,CoinType.CRYPTO,buyer.getCoinName());
	   	    	
	   	    	///Currency Class ////
				
					CurrencyClass tempcoin=currencyRepository.findByCoinName(buyer.getCoinName());
					 if(tempcoin.getInitialSupply()!=0)
					 {
						
						if(buyer.getPrice()>tempcoin.getPrice())
						{
							if(buyer.getCoinQuantity()>tempcoin.getInitialSupply())
							{
							  //transactionobject=new Transaction();
			                  tempcoin.setInitialSupply(0);
			                  double amount=tempcoin.getInitialSupply()*buyer.getPrice()-tempcoin.getInitialSupply()*tempcoin.getPrice();
			                  tempcoin.setCoinInINR(amount+tempcoin.getCoinInINR());
			                  tempcoin.setProfit((((tempcoin.getInitialSupply()*buyer.getPrice())*tempcoin.getFees())/100)+tempcoin.getProfit());
			                  
			                   buyer.setCoinQuantity(buyer.getCoinQuantity()-tempcoin.getInitialSupply());
			                   buyer.setStatus(UserOrderStatus.PENDING);
			                   buyer.setGrossAmount(buyer.getGrossAmount()-((tempcoin.getInitialSupply()*buyer.getPrice()+(tempcoin.getInitialSupply()*buyer.getPrice())*tempcoin.getFees())/100));
			                   buyer.setFees(((buyer.getCoinQuantity()-tempcoin.getInitialSupply())*buyer.getPrice()*tempcoin.getFees())/100);
			                  
			                   //user id //
			                   buyer.setUser(buyer.getUser());
			                  
			                   wallet.setBalance((wallet.getBalance()-(buyer.getGrossAmount()-((tempcoin.getInitialSupply()*buyer.getPrice()+(tempcoin.getInitialSupply()*buyer.getPrice())*tempcoin.getFees())/100))));
		   	  				   wallet1.setBalance((tempcoin.getInitialSupply()+wallet1.getBalance()));
			                   wallet1.setShadowBalance((tempcoin.getInitialSupply()+wallet1.getShadowBalance()));
			                  
			                   transactionobject.setFees(tempcoin.getInitialSupply()*buyer.getPrice()*tempcoin.getFees()/100);
			                   transactionobject.setNetAmount(tempcoin.getInitialSupply()*buyer.getPrice());
			                   transactionobject.setGrossAmount(((tempcoin.getInitialSupply()*buyer.getPrice()+(tempcoin.getInitialSupply()*buyer.getPrice())*tempcoin.getFees())/100));
							}
							else if(buyer.getCoinQuantity()==tempcoin.getInitialSupply())
							{
								//transactionobject=new Transaction();
								tempcoin.setCoinInINR(tempcoin.getCoinInINR()+(tempcoin.getInitialSupply()*buyer.getPrice()-(tempcoin.getInitialSupply()*tempcoin.getPrice())));
		   	  			        tempcoin.setProfit(buyer.getFees()+(tempcoin.getProfit()));
		   	  				    tempcoin.setInitialSupply(0);
		   	  			      
		   	  				 	 buyer.setStatus(UserOrderStatus.APPROVED);
		   	  				 	 buyer.setUser(buyer.getUser());
		   	  				 	 
		     				     wallet.setBalance((wallet.getBalance()-buyer.getGrossAmount()));
		   	  				     wallet1.setBalance((buyer.getCoinQuantity()+wallet1.getBalance()));
		   	  				     wallet1.setShadowBalance((buyer.getCoinQuantity()+wallet1.getShadowBalance()));	
							   
		   	  				     transactionobject.setFees(buyer.getFees());
		   	  				     transactionobject.setNetAmount(buyer.getCoinQuantity()*buyer.getPrice());
		   	  				     transactionobject.setGrossAmount(buyer.getGrossAmount());
		   	  				 }
							else if(buyer.getCoinQuantity()<tempcoin.getInitialSupply())
							{
								 //transactionobject=new Transaction();
								 tempcoin.setCoinInINR(tempcoin.getCoinInINR()+(buyer.getCoinQuantity()*buyer.getPrice()-(tempcoin.getInitialSupply()*tempcoin.getPrice())));
		   	  			         tempcoin.setProfit(buyer.getFees()+(tempcoin.getProfit()));
		   	  				     tempcoin.setInitialSupply(tempcoin.getInitialSupply()-buyer.getCoinQuantity());
		   	  				    
								 buyer.setStatus(UserOrderStatus.APPROVED);
		   	  				 	 buyer.setUser(buyer.getUser());
		   	  				 	 
								 wallet.setBalance((wallet.getBalance()-buyer.getGrossAmount()));
		   	  				     wallet1.setBalance((buyer.getCoinQuantity()+wallet1.getBalance()));
		   	  				     wallet1.setShadowBalance((buyer.getCoinQuantity()+wallet1.getShadowBalance()));
		   	  				     
		   	  				     transactionobject.setFees(buyer.getFees());
		   	  				     transactionobject.setNetAmount(buyer.getCoinQuantity()*buyer.getPrice());
		   	  				     transactionobject.setGrossAmount(buyer.getGrossAmount());

							}
						}
						else if(buyer.getPrice()==tempcoin.getPrice())
						{
							if(buyer.getCoinQuantity()>tempcoin.getInitialSupply())
							{
								  //transactionobject=new Transaction();
								  tempcoin.setInitialSupply(0);
								  
				                  double amount=tempcoin.getInitialSupply()*buyer.getPrice()-tempcoin.getInitialSupply()*tempcoin.getPrice();
				                  tempcoin.setCoinInINR(amount+tempcoin.getCoinInINR());
				                  tempcoin.setProfit((((tempcoin.getInitialSupply()*buyer.getPrice())*tempcoin.getFees())/100)+tempcoin.getProfit());
				                  
				                  buyer.setCoinQuantity(buyer.getCoinQuantity()-tempcoin.getInitialSupply());
				                  buyer.setStatus(UserOrderStatus.PENDING);
				                  buyer.setGrossAmount(buyer.getGrossAmount()-((tempcoin.getInitialSupply()*buyer.getPrice()+(tempcoin.getInitialSupply()*buyer.getPrice())*tempcoin.getFees())/100));
				                  buyer.setFees(((buyer.getCoinQuantity()-tempcoin.getInitialSupply())*buyer.getPrice()*tempcoin.getFees())/100);
				                  
				                  //user id //
				                  buyer.setUser(buyer.getUser());
				                  
				                  wallet.setBalance((wallet.getBalance()-(buyer.getGrossAmount()-((tempcoin.getInitialSupply()*buyer.getPrice()+(tempcoin.getInitialSupply()*buyer.getPrice())*tempcoin.getFees())/100))));
			   	  				  wallet1.setBalance((tempcoin.getInitialSupply()+wallet1.getBalance()));
				                  wallet1.setShadowBalance((tempcoin.getInitialSupply()+wallet1.getShadowBalance()));
				                  
				                  transactionobject.setFees(tempcoin.getInitialSupply()*buyer.getPrice()*tempcoin.getFees()/100);
				                  transactionobject.setNetAmount(tempcoin.getInitialSupply()*buyer.getPrice());
				                  transactionobject.setGrossAmount(((tempcoin.getInitialSupply()*buyer.getPrice()+(tempcoin.getInitialSupply()*buyer.getPrice())*tempcoin.getFees())/100));
							}
							else if(buyer.getCoinQuantity()==tempcoin.getInitialSupply())
							{  
								  //transactionobject=new Transaction();
							      tempcoin.setCoinInINR(tempcoin.getCoinInINR());
							      tempcoin.setProfit(buyer.getFees());
							      tempcoin.setInitialSupply(0);
		   	  			        
		   	  				      buyer.setStatus(UserOrderStatus.APPROVED);
	   	  				 	      buyer.setUser(buyer.getUser());
		   	  				 	
		     				      wallet.setBalance((wallet.getBalance()-buyer.getGrossAmount()));
		   	  				      wallet1.setBalance((buyer.getCoinQuantity()+wallet1.getBalance()));
		   	  				      wallet1.setShadowBalance((buyer.getCoinQuantity()+wallet1.getShadowBalance()));
		   	  				     
		   	  				      transactionobject.setFees(buyer.getFees());
		   	  				      transactionobject.setNetAmount(buyer.getCoinQuantity()*buyer.getPrice());
		   	  				      transactionobject.setGrossAmount(buyer.getGrossAmount());
							}
							else if(buyer.getCoinQuantity()<tempcoin.getInitialSupply())
							{
								 //transactionobject=new Transaction();
								 tempcoin.setCoinInINR(tempcoin.getCoinInINR()+(buyer.getCoinQuantity()*buyer.getPrice()-(tempcoin.getInitialSupply()*tempcoin.getPrice())));
		   	  			         tempcoin.setProfit(buyer.getFees()+(tempcoin.getProfit()));
		   	  				     tempcoin.setInitialSupply(tempcoin.getInitialSupply()-buyer.getCoinQuantity());
		   	  				    
								 buyer.setStatus(UserOrderStatus.APPROVED);
		   	  				 	 buyer.setUser(buyer.getUser());
		   	  				 	 
								 wallet.setBalance((wallet.getBalance()-buyer.getGrossAmount()));
		   	  				     wallet1.setBalance((buyer.getCoinQuantity()+wallet1.getBalance()));
		   	  				     wallet1.setShadowBalance((buyer.getCoinQuantity()+wallet1.getShadowBalance()));
		   	  				     
		   	  				     transactionobject.setFees(buyer.getFees());
		   	  				     transactionobject.setNetAmount(buyer.getCoinQuantity()*buyer.getPrice());
		   	  				     transactionobject.setGrossAmount(buyer.getGrossAmount());
							}
						}
						System.out.println(buyer.getUser().getUserId());
						transactionobject.setBuyerId(buyer.getUser().getUserId());
						
						 System.out.println(buyer.getUser().getUserId());

						 transactionobject.setCoinType(CoinType.CRYPTO);
		  				 transactionobject.setCoinName(buyer.getCoinName());
		  				 transactionobject.setSellerId(0);
		  				 transactionobject.setExchangeRate(buyer.getPrice());
		  				 transactionobject.setDateCreated(new Date());
		  				 transactionobject.setMessage("Transaction Done");
		  				 transactionobject.setStatus(TransactionStatus.APPROVED);
		  				 transactionobject.setUserOrderType(buyer.getOrderType());
		  	             
		  				 transactionRepository.save(transactionobject);
		  				 walletRepository.save(wallet);
		  				 walletRepository.save(wallet1);
		  				 currencyRepository.save(tempcoin);
		  				 userOrderRepository.save(buyer);
				  }
			 }
		}
		
		else
		{
			Iterator<UserOrder>buyitr=listbuyer.iterator();
			while(buyitr.hasNext())
			{
				UserOrder buyer=buyitr.next();
				User user=buyer.getUser();
				
				Wallet wallet=walletRepository.findByUserAndCoinType(user,CoinType.FIATE);
	   	    	Wallet wallet1=walletRepository.findByUserAndCoinTypeAndCoinName(user,CoinType.CRYPTO,buyer.getCoinName());
				
	   	    	CurrencyClass currencycoin=currencyRepository.findByCoinName(buyer.getCoinName());
				
	   	    	Transaction transactionsellerobject;
	   	    			////Seller////
		         
				 Iterator<UserOrder>sellitr=listseller.iterator();
			     while(sellitr.hasNext())
			     {
			    	UserOrder seller=sellitr.next();
			    	User userseller=seller.getUser();
			    	
			    	Wallet walletseller=walletRepository.findByUserAndCoinType(userseller,CoinType.FIATE);
		   	    	Wallet walletsellercrypto=walletRepository.findByUserAndCoinTypeAndCoinName(userseller,CoinType.CRYPTO,buyer.getCoinName());
			        
		   	    	if(seller.getCoinName().equals(buyer.getCoinName()))
			        {
		   	    	transactionsellerobject=new Transaction();
			          if(seller.getPrice()<=buyer.getPrice())
			          {
			        	  if(seller.getPrice()<=currencycoin.getPrice())
			        	  {
			        		  
			        		  ////Seller sell////
			        		  if(buyer.getCoinQuantity()>seller.getCoinQuantity())
								{
			        			  transactionsellerobject=new Transaction();
				                  double amount=seller.getCoinQuantity()*buyer.getPrice()-seller.getCoinQuantity()*seller.getPrice();
				                  
				                  currencycoin.setCoinInINR(amount+currencycoin.getCoinInINR());
				                  currencycoin.setProfit((((seller.getCoinQuantity()*buyer.getPrice())*currencycoin.getFees())/100)+currencycoin.getProfit());
				                  
				                   buyer.setCoinQuantity(buyer.getCoinQuantity()-seller.getCoinQuantity());
				                   buyer.setStatus(UserOrderStatus.PENDING);
				                   buyer.setGrossAmount(buyer.getGrossAmount()-((seller.getCoinQuantity()*buyer.getPrice()+(seller.getCoinQuantity()*buyer.getPrice())*currencycoin.getFees())/100));
				                   buyer.setFees(((buyer.getCoinQuantity()-currencycoin.getInitialSupply())*buyer.getPrice()*currencycoin.getFees())/100);
				                    
				                   //user id //
				                   buyer.setUser(buyer.getUser());
				                  
				                   wallet.setBalance((wallet.getBalance()-(buyer.getGrossAmount()-((seller.getCoinQuantity()*buyer.getPrice()+(seller.getCoinQuantity()*buyer.getPrice())*currencycoin.getFees())/100))));
			   	  				   wallet1.setBalance((seller.getCoinQuantity()+wallet1.getBalance()));
				                   wallet1.setShadowBalance((seller.getCoinQuantity()+wallet1.getShadowBalance()));
				                   
				                   walletseller.setBalance(walletseller.getBalance()+seller.getCoinQuantity()*seller.getPrice());
			   	  				   walletsellercrypto.setBalance(walletsellercrypto.getBalance()-seller.getCoinQuantity());
			   	  				     
			   	  				   walletRepository.save(walletseller);
				                   walletRepository.save(walletsellercrypto);
				                  
				                   transactionsellerobject.setSellerId(seller.getUser().getUserId());
				                   transactionsellerobject.setFees(currencycoin.getInitialSupply()*buyer.getPrice()*currencycoin.getFees()/100);
				                   transactionsellerobject.setNetAmount(currencycoin.getInitialSupply()*buyer.getPrice());
				                   transactionsellerobject.setGrossAmount(((currencycoin.getInitialSupply()*buyer.getPrice()+(currencycoin.getInitialSupply()*buyer.getPrice())*currencycoin.getFees())/100));
								}
								else if(buyer.getCoinQuantity()==seller.getCoinQuantity())
								{
									transactionsellerobject=new Transaction();
									 currencycoin.setCoinInINR(currencycoin.getCoinInINR()+(buyer.getCoinQuantity()*buyer.getPrice()-(seller.getCoinQuantity()*seller.getPrice())));
									 currencycoin.setProfit(buyer.getFees()+(currencycoin.getProfit()));
			   	  			      
			   	  				 	 buyer.setStatus(UserOrderStatus.APPROVED);
			   	  				 	 buyer.setUser(buyer.getUser());
			   	  				 	 
			     				     wallet.setBalance((wallet.getBalance()-buyer.getGrossAmount()));
			   	  				     wallet1.setBalance((buyer.getCoinQuantity()+wallet1.getBalance()));
			   	  				     wallet1.setShadowBalance((buyer.getCoinQuantity()+wallet1.getShadowBalance()));
			   	  				     
			   	  				
			   	  				     walletseller.setBalance(walletseller.getBalance()+seller.getCoinQuantity()*seller.getPrice());
			   	  				     walletsellercrypto.setBalance(walletsellercrypto.getBalance()-seller.getCoinQuantity());
			   	  				     
			   	  				     walletRepository.save(walletseller);
				                     walletRepository.save(walletsellercrypto);
				                     
				                     transactionsellerobject.setSellerId(seller.getUser().getUserId());
			   	  				     transactionsellerobject.setFees(buyer.getFees());
			   	  				     transactionsellerobject.setNetAmount(buyer.getCoinQuantity()*buyer.getPrice());
			   	  				     transactionsellerobject.setGrossAmount(buyer.getGrossAmount());	
								}
								else if(buyer.getCoinQuantity()<seller.getCoinQuantity())
								{
									transactionsellerobject=new Transaction();
									 currencycoin.setCoinInINR(buyer.getCoinQuantity()+(buyer.getCoinQuantity()*buyer.getPrice()-(buyer.getCoinQuantity()*seller.getPrice())));
			   	  			         currencycoin.setProfit(buyer.getFees()+(currencycoin.getProfit()));
			   	  
			   	  				 
									 buyer.setStatus(UserOrderStatus.APPROVED);
			   	  				 	 buyer.setUser(buyer.getUser());
			   	  				 	  
									 wallet.setBalance((wallet.getBalance()-buyer.getGrossAmount()));
			   	  				     wallet1.setBalance((buyer.getCoinQuantity()+wallet1.getBalance()));
			   	  				     wallet1.setShadowBalance((buyer.getCoinQuantity()+wallet1.getShadowBalance()));
			   	  				     
			   	  				     walletseller.setBalance(walletseller.getBalance()+buyer.getCoinQuantity()*seller.getPrice());
		   	  				         walletsellercrypto.setBalance(walletsellercrypto.getBalance()-buyer.getCoinQuantity());
		   	  				     
		   	  				         walletRepository.save(walletseller);
			                         walletRepository.save(walletsellercrypto);
			   	  				     
			   	  				     transactionsellerobject.setSellerId(seller.getUser().getUserId());
			   	  				     transactionsellerobject.setFees(buyer.getFees());
			   	  				     transactionsellerobject.setNetAmount(buyer.getCoinQuantity()*buyer.getPrice());
			   	  				     transactionsellerobject.setGrossAmount(buyer.getGrossAmount());
								}
			        	  }
			        	  
			          }
			          else if(seller.getPrice()>buyer.getPrice())
			          {
			        	  
			        	  if(currencycoin.getInitialSupply()!=0)
							 {
			        		    
								if(buyer.getPrice()>currencycoin.getPrice())
								{
									if(buyer.getCoinQuantity()>currencycoin.getInitialSupply())
									{
										  transactionsellerobject=new Transaction();
										  currencycoin.setInitialSupply(0);
						                  double amount=currencycoin.getInitialSupply()*buyer.getPrice()-currencycoin.getInitialSupply()*currencycoin.getPrice();
						                  currencycoin.setCoinInINR(amount+currencycoin.getCoinInINR());
						                  currencycoin.setProfit((((currencycoin.getInitialSupply()*buyer.getPrice())*currencycoin.getFees())/100)+currencycoin.getProfit());
						                  
						                   buyer.setCoinQuantity(buyer.getCoinQuantity()-currencycoin.getInitialSupply());
						                   buyer.setStatus(UserOrderStatus.PENDING);
						                   buyer.setGrossAmount(buyer.getGrossAmount()-((currencycoin.getInitialSupply()*buyer.getPrice()+(currencycoin.getInitialSupply()*buyer.getPrice())*currencycoin.getFees())/100));
						                   buyer.setFees(((buyer.getCoinQuantity()-currencycoin.getInitialSupply())*buyer.getPrice()*currencycoin.getFees())/100);
						                  
						                   //user id //
						                   buyer.setUser(buyer.getUser());
						                  
						                   wallet.setBalance((wallet.getBalance()-(buyer.getGrossAmount()-((currencycoin.getInitialSupply()*buyer.getPrice()+(currencycoin.getInitialSupply()*buyer.getPrice())*currencycoin.getFees())/100))));
					   	  				   wallet1.setBalance((currencycoin.getInitialSupply()+wallet1.getBalance()));
						                   wallet1.setShadowBalance((currencycoin.getInitialSupply()+wallet1.getShadowBalance()));
						                  
						                   transactionsellerobject.setFees(currencycoin.getInitialSupply()*buyer.getPrice()*currencycoin.getFees()/100);
						                   transactionsellerobject.setNetAmount(currencycoin.getInitialSupply()*buyer.getPrice());
						                   transactionsellerobject.setGrossAmount(((currencycoin.getInitialSupply()*buyer.getPrice()+(currencycoin.getInitialSupply()*buyer.getPrice())*currencycoin.getFees())/100));
									}
									else if(buyer.getCoinQuantity()==currencycoin.getInitialSupply())
									{
										transactionsellerobject=new Transaction();
										currencycoin.setCoinInINR(currencycoin.getCoinInINR()+(currencycoin.getInitialSupply()*buyer.getPrice()-(currencycoin.getInitialSupply()*currencycoin.getPrice())));
										currencycoin.setProfit(buyer.getFees()+(currencycoin.getProfit()));
										currencycoin.setInitialSupply(0);
				   	  			      
				   	  				 	 buyer.setStatus(UserOrderStatus.APPROVED);
				   	  				 	 buyer.setUser(buyer.getUser());
				   	  				 	 
				     				     wallet.setBalance((wallet.getBalance()-buyer.getGrossAmount()));
				   	  				     wallet1.setBalance((buyer.getCoinQuantity()+wallet1.getBalance()));
				   	  				     wallet1.setShadowBalance((buyer.getCoinQuantity()+wallet1.getShadowBalance()));	
									   
				   	  				     transactionsellerobject.setFees(buyer.getFees());
				   	  				     transactionsellerobject.setNetAmount(buyer.getCoinQuantity()*buyer.getPrice());
				   	  				     transactionsellerobject.setGrossAmount(buyer.getGrossAmount());	
									}
									else if(buyer.getCoinQuantity()<currencycoin.getInitialSupply())
									{
										 transactionsellerobject=new Transaction();
										 currencycoin.setCoinInINR(currencycoin.getCoinInINR()+(buyer.getCoinQuantity()*buyer.getPrice()-(currencycoin.getInitialSupply()*currencycoin.getPrice())));
				   	  			         currencycoin.setProfit(buyer.getFees()+(currencycoin.getProfit()));
				   	  			         currencycoin.setInitialSupply(currencycoin.getInitialSupply()-buyer.getCoinQuantity());
				   	  				    
										 buyer.setStatus(UserOrderStatus.APPROVED);
				   	  				 	 buyer.setUser(buyer.getUser());
				   	  				 	 
										 wallet.setBalance((wallet.getBalance()-buyer.getGrossAmount()));
				   	  				     wallet1.setBalance((buyer.getCoinQuantity()+wallet1.getBalance()));
				   	  				     wallet1.setShadowBalance((buyer.getCoinQuantity()+wallet1.getShadowBalance()));
				   	  				     
				   	  				     transactionsellerobject.setFees(buyer.getFees());
				   	  				     transactionsellerobject.setNetAmount(buyer.getCoinQuantity()*buyer.getPrice());
				   	  				     transactionsellerobject.setGrossAmount(buyer.getGrossAmount());
									}
								}
								else if(buyer.getPrice()==currencycoin.getPrice())
								{
									if(buyer.getCoinQuantity()>currencycoin.getInitialSupply())
									{
										transactionsellerobject=new Transaction();
										  currencycoin.setInitialSupply(0);
										
						                  double amount=currencycoin.getInitialSupply()*buyer.getPrice()-currencycoin.getInitialSupply()*currencycoin.getPrice();
						                  currencycoin.setCoinInINR(amount+currencycoin.getCoinInINR());
						                  currencycoin.setProfit((((currencycoin.getInitialSupply()*buyer.getPrice())*currencycoin.getFees())/100)+currencycoin.getProfit());
						                  
						                  buyer.setCoinQuantity(buyer.getCoinQuantity()-currencycoin.getInitialSupply());
						                  buyer.setStatus(UserOrderStatus.PENDING);
						                  buyer.setGrossAmount(buyer.getGrossAmount()-((currencycoin.getInitialSupply()*buyer.getPrice()+(currencycoin.getInitialSupply()*buyer.getPrice())*currencycoin.getFees())/100));
						                  buyer.setFees(((buyer.getCoinQuantity()-currencycoin.getInitialSupply())*buyer.getPrice()*currencycoin.getFees())/100);
						                  
						                  //user id //
						                  buyer.setUser(buyer.getUser());
						                  
						                  wallet.setBalance((wallet.getBalance()-(buyer.getGrossAmount()-((currencycoin.getInitialSupply()*buyer.getPrice()+(currencycoin.getInitialSupply()*buyer.getPrice())*currencycoin.getFees())/100))));
					   	  				  wallet1.setBalance((currencycoin.getInitialSupply()+wallet1.getBalance()));
						                  wallet1.setShadowBalance((currencycoin.getInitialSupply()+wallet1.getShadowBalance()));
						                  
						                  transactionsellerobject.setSellerId(0);
						                  transactionsellerobject.setFees(currencycoin.getInitialSupply()*buyer.getPrice()*currencycoin.getFees()/100);
						                  transactionsellerobject.setNetAmount(currencycoin.getInitialSupply()*buyer.getPrice());
						                  transactionsellerobject.setGrossAmount(((currencycoin.getInitialSupply()*buyer.getPrice()+(currencycoin.getInitialSupply()*buyer.getPrice())*currencycoin.getFees())/100));
									}
									else if(buyer.getCoinQuantity()==currencycoin.getInitialSupply())
									{ 
										transactionsellerobject=new Transaction();
									  currencycoin.setCoinInINR(currencycoin.getCoinInINR());
								      currencycoin.setProfit(buyer.getFees());
								      currencycoin.setInitialSupply(0);
			   	  			        
			   	  				      buyer.setStatus(UserOrderStatus.APPROVED);
		   	  				 	      buyer.setUser(buyer.getUser());
			   	  				 	
			     				      wallet.setBalance((wallet.getBalance()-buyer.getGrossAmount()));
			   	  				      wallet1.setBalance((buyer.getCoinQuantity()+wallet1.getBalance()));
			   	  				      wallet1.setShadowBalance((buyer.getCoinQuantity()+wallet1.getShadowBalance()));
			   	  				     
			   	  				      transactionsellerobject.setSellerId(0);
			   	  				      transactionsellerobject.setFees(buyer.getFees());
			   	  				      transactionsellerobject.setNetAmount(buyer.getCoinQuantity()*buyer.getPrice());
			   	  				      transactionsellerobject.setGrossAmount(buyer.getGrossAmount());
									}
									else if(buyer.getCoinQuantity()<currencycoin.getInitialSupply())
									{
										transactionsellerobject=new Transaction();
										 currencycoin.setCoinInINR(currencycoin.getCoinInINR()+(buyer.getCoinQuantity()*buyer.getPrice()-(currencycoin.getInitialSupply()*currencycoin.getPrice())));
				   	  			         currencycoin.setProfit(buyer.getFees()+(currencycoin.getProfit()));
				   	  				     currencycoin.setInitialSupply(currencycoin.getInitialSupply()-buyer.getCoinQuantity());
				   	  
				   	  				 
										 buyer.setStatus(UserOrderStatus.APPROVED);
				   	  				 	 buyer.setUser(buyer.getUser());
				   	  				 	 
										 wallet.setBalance((wallet.getBalance()-buyer.getGrossAmount()));
				   	  				     wallet1.setBalance((buyer.getCoinQuantity()+wallet1.getBalance()));
				   	  				     wallet1.setShadowBalance((buyer.getCoinQuantity()+wallet1.getShadowBalance()));
				   	  				     
				   	  				     transactionsellerobject.setSellerId(0);
				   	  				     
				   	  				     transactionsellerobject.setFees(buyer.getFees());
				   	  				     transactionsellerobject.setNetAmount(buyer.getCoinQuantity()*buyer.getPrice());
				   	  				     transactionsellerobject.setGrossAmount(buyer.getGrossAmount());
									}
								}
								 transactionsellerobject.setBuyerId(user.getUserId());
								 transactionsellerobject.setCoinType(CoinType.CRYPTO);
								 transactionsellerobject.setCoinName(buyer.getCoinName());
								 transactionsellerobject.setExchangeRate(buyer.getPrice());
								 transactionsellerobject.setDateCreated(new Date());
								 transactionsellerobject.setMessage("Transaction Done");
								 transactionsellerobject.setStatus(TransactionStatus.APPROVED);
								 transactionsellerobject.setUserOrderType(buyer.getOrderType());
								 
								 walletRepository.save(wallet1);
		   	  				     walletRepository.save(wallet);
				  				 
		   	  				     transactionRepository.save(transactionsellerobject);
		 
				  	    }  
			          }
			        }
			      }
			    }
			    
			     
			}
		}

     }
