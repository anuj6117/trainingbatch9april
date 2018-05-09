package com.example.demo.service;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.CurrencyClass;
import com.example.demo.domain.Transaction;
import com.example.demo.domain.User;
import com.example.demo.domain.UserOrder;
import com.example.demo.domain.Wallet;
import com.example.demo.enumeration.CoinType;
import com.example.demo.enumeration.TransactionStatus;
import com.example.demo.enumeration.UserOrderStatus;
import com.example.demo.repository.CurrencyRepository;
import com.example.demo.repository.TransactionRepository;
import com.example.demo.repository.UserOrderRepository;
import com.example.demo.repository.WalletRepository;

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
	public String transaction()
	{
		
		List<UserOrder> listbuyer=userOrderRepository.getBuyers("BUYER");
		
		List<UserOrder> listseller=userOrderRepository.getSellers("SELLER");
		if(listbuyer.isEmpty())
		{
			return "Buyer Not Exist";
		}

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
						
						if(buyer.getPrice()>=tempcoin.getPrice())
						{
							if(buyer.getCoinQuantity()>tempcoin.getInitialSupply())
							{
							 Transaction transactionobject=new Transaction();
							 transactionobject.setNetAmount(tempcoin.getInitialSupply()*buyer.getPrice());
		                     transactionobject.setGrossAmount((((tempcoin.getInitialSupply()*buyer.getPrice())+((tempcoin.getInitialSupply()*buyer.getPrice())*tempcoin.getFees())/100)));
		                     transactionobject.setBuyerId(buyer.getUser().getUserId());
		                     transactionobject.setFees(tempcoin.getFees());
		                     transactionobject.setCoinType(CoinType.CRYPTO);
			  				 transactionobject.setCoinName(buyer.getCoinName());
			  				 transactionobject.setSellerId(0);
			  				 transactionobject.setExchangeRate(buyer.getPrice());
			  				 transactionobject.setDateCreated(new Date());
			  				 transactionobject.setMessage("Transaction Done");
			  				 transactionobject.setStatus(TransactionStatus.APPROVED);
			  				 transactionobject.setUserOrderType(buyer.getOrderType());
			  	             transactionRepository.save(transactionobject);
			  	             
			                       wallet.setBalance(((wallet.getBalance()-(buyer.getGrossAmount())-((tempcoin.getInitialSupply()*buyer.getPrice()+(tempcoin.getInitialSupply()*buyer.getPrice())*tempcoin.getFees())/100))));
		   	  				       wallet1.setBalance((tempcoin.getInitialSupply()+wallet1.getBalance()));
			                       wallet1.setShadowBalance((tempcoin.getInitialSupply()+wallet1.getShadowBalance()));
				  	               walletRepository.save(wallet);
				  				   walletRepository.save(wallet1);			                  
			                     
			                       buyer.setCoinQuantity(buyer.getCoinQuantity()-tempcoin.getInitialSupply());
				                   buyer.setStatus(UserOrderStatus.PENDING);
				                   buyer.setGrossAmount((buyer.getGrossAmount())-((tempcoin.getInitialSupply()*buyer.getPrice()+(tempcoin.getInitialSupply()*buyer.getPrice())*tempcoin.getFees())/100));
				                   buyer.setUser(buyer.getUser());
				                   userOrderRepository.save(buyer);
				              
				                  
				  				  double amount=(tempcoin.getInitialSupply()*buyer.getPrice())-(tempcoin.getInitialSupply()*tempcoin.getPrice());
				                  tempcoin.setCoinInINR(tempcoin.getCoinInINR()+amount);
				                  tempcoin.setProfit((((tempcoin.getInitialSupply()*buyer.getPrice())*tempcoin.getFees())/100)+tempcoin.getProfit());
				  	              tempcoin.setInitialSupply(0);
				  	              currencyRepository.save(tempcoin); 	           
							}
							else if(buyer.getCoinQuantity()==tempcoin.getInitialSupply())
							{
								Transaction transactionobject=new Transaction();
								transactionobject.setFees(tempcoin.getFees());
			   	  	             transactionobject.setNetAmount(buyer.getCoinQuantity()*buyer.getPrice());
			   	  				 transactionobject.setGrossAmount(buyer.getGrossAmount());
			   	  				 transactionobject.setBuyerId(buyer.getUser().getUserId());
								 transactionobject.setCoinType(CoinType.CRYPTO);
				  				 transactionobject.setCoinName(buyer.getCoinName());
				  				 transactionobject.setSellerId(0);
				  				 transactionobject.setExchangeRate(buyer.getPrice());
				  				 transactionobject.setDateCreated(new Date());
				  				 transactionobject.setMessage("Transaction Done");
				  				 transactionobject.setStatus(TransactionStatus.APPROVED);
				  				 transactionobject.setUserOrderType(buyer.getOrderType());
				  	             transactionRepository.save(transactionobject);
                                 
				  	             wallet.setBalance((wallet.getBalance()-buyer.getGrossAmount()));
		   	  				     wallet1.setBalance((buyer.getCoinQuantity()+wallet1.getBalance()));
		   	  				     wallet1.setShadowBalance((buyer.getCoinQuantity()+wallet1.getShadowBalance()));
		   	  				     walletRepository.save(wallet);
			  				     walletRepository.save(wallet1);
			  				     
	                             buyer.setStatus(UserOrderStatus.APPROVED);
		   	  				 	 buyer.setUser(buyer.getUser());
		   	  				     userOrderRepository.save(buyer);
		   	  				     
		   	  				tempcoin.setCoinInINR(((tempcoin.getInitialSupply()*buyer.getPrice())-(tempcoin.getInitialSupply()*tempcoin.getPrice()))+tempcoin.getCoinInINR());
	   	  			        tempcoin.setProfit(buyer.getFees()+(tempcoin.getProfit())); 	
					        tempcoin.setInitialSupply(0);
					        currencyRepository.save(tempcoin);
		   	  				 }
							else if(buyer.getCoinQuantity()<tempcoin.getInitialSupply())
							{
								Transaction transactionobject=new Transaction();
						     transactionobject.setFees(buyer.getFees());
		   	  				 transactionobject.setNetAmount(buyer.getCoinQuantity()*buyer.getPrice());
		   	  				 transactionobject.setGrossAmount(buyer.getGrossAmount());
		   	  				 transactionobject.setBuyerId(buyer.getUser().getUserId());
							 transactionobject.setCoinType(CoinType.CRYPTO);
			  				 transactionobject.setCoinName(buyer.getCoinName());
			  				 transactionobject.setSellerId(0);
			  				 transactionobject.setExchangeRate(buyer.getPrice());
			  				 transactionobject.setDateCreated(new Date());
			  				 transactionobject.setMessage("Transaction Done");
			  				 transactionobject.setStatus(TransactionStatus.APPROVED);
			  				 transactionobject.setUserOrderType(buyer.getOrderType());
			  	             transactionRepository.save(transactionobject);
			  	             
			  	             wallet.setBalance((wallet.getBalance()-buyer.getGrossAmount()));
	   	  				     wallet1.setBalance((buyer.getCoinQuantity()+wallet1.getBalance()));
	   	  				     wallet1.setShadowBalance((buyer.getCoinQuantity()+wallet1.getShadowBalance()));
	   	  				     walletRepository.save(wallet);
		  				     walletRepository.save(wallet1);
	   	  				     
		  				     buyer.setStatus(UserOrderStatus.APPROVED);
	   	  				 	 buyer.setUser(buyer.getUser());								 
	   	  				     userOrderRepository.save(buyer);   
								    
		   	  				 tempcoin.setCoinInINR((buyer.getCoinQuantity()*buyer.getPrice()-(tempcoin.getInitialSupply()*tempcoin.getPrice()))+tempcoin.getCoinInINR());
	   	  			         tempcoin.setProfit(buyer.getFees()+(tempcoin.getProfit()));
	   	  				     tempcoin.setInitialSupply(tempcoin.getInitialSupply()-buyer.getCoinQuantity());
                             currencyRepository.save(tempcoin);
							}
						}
						else if(buyer.getPrice()==tempcoin.getPrice())
						{
							if(buyer.getCoinQuantity()>tempcoin.getInitialSupply())
							{
								Transaction transactionobject=new Transaction();
								 transactionobject.setFees(tempcoin.getFees());
			                     transactionobject.setNetAmount(tempcoin.getInitialSupply()*buyer.getPrice());
			                     transactionobject.setGrossAmount(((tempcoin.getInitialSupply()*buyer.getPrice()+(tempcoin.getInitialSupply()*buyer.getPrice())*tempcoin.getFees())/100));
			                     transactionobject.setBuyerId(buyer.getUser().getUserId());				                  
			                     transactionobject.setCoinType(CoinType.CRYPTO);
				  				 transactionobject.setCoinName(buyer.getCoinName());
				  				 transactionobject.setSellerId(0);
				  				 transactionobject.setExchangeRate(buyer.getPrice());
				  				 transactionobject.setDateCreated(new Date());
				  				 transactionobject.setMessage("Transaction Done");
				  				 transactionobject.setStatus(TransactionStatus.APPROVED);
				  				 transactionobject.setUserOrderType(buyer.getOrderType());
				  	             transactionRepository.save(transactionobject);
								  
				  	              wallet.setBalance((wallet.getBalance()-(buyer.getGrossAmount()-((tempcoin.getInitialSupply()*buyer.getPrice()+(tempcoin.getInitialSupply()*buyer.getPrice())*tempcoin.getFees())/100))));
			   	  				  wallet1.setBalance((tempcoin.getInitialSupply()+wallet1.getBalance()));
				                  wallet1.setShadowBalance((tempcoin.getInitialSupply()+wallet1.getShadowBalance()));
				                  walletRepository.save(wallet);
					  		      walletRepository.save(wallet1);
				                  
				                  
				                  buyer.setCoinQuantity(buyer.getCoinQuantity()-tempcoin.getInitialSupply());
				                  buyer.setStatus(UserOrderStatus.PENDING);
				                  buyer.setGrossAmount(buyer.getGrossAmount()-((tempcoin.getInitialSupply()*buyer.getPrice()+(tempcoin.getInitialSupply()*buyer.getPrice())*tempcoin.getFees())/100));
				                  buyer.setFees(((buyer.getCoinQuantity()-tempcoin.getInitialSupply())*buyer.getPrice()*tempcoin.getFees())/100);
				                  userOrderRepository.save(buyer);
				                  
				                  buyer.setUser(buyer.getUser());
				                  double amount=tempcoin.getInitialSupply()*buyer.getPrice()-tempcoin.getInitialSupply()*tempcoin.getPrice();
					              tempcoin.setCoinInINR(amount+tempcoin.getCoinInINR());
					              tempcoin.setProfit((((tempcoin.getInitialSupply()*buyer.getPrice())*tempcoin.getFees())/100)+tempcoin.getProfit());
				                  currencyRepository.save(tempcoin);
					  			  
							}
							else if(buyer.getCoinQuantity()==tempcoin.getInitialSupply())
							{  
								Transaction transactionobject=new Transaction();
							      
		   	  				      transactionobject.setFees(buyer.getFees());
		   	  				      transactionobject.setNetAmount(buyer.getCoinQuantity()*buyer.getPrice());
		   	  				      transactionobject.setGrossAmount(buyer.getGrossAmount());
		   	  				 transactionobject.setBuyerId(buyer.getUser().getUserId());
							 transactionobject.setCoinType(CoinType.CRYPTO);
			  				 transactionobject.setCoinName(buyer.getCoinName());
			  				 transactionobject.setSellerId(0);
			  				 transactionobject.setExchangeRate(buyer.getPrice());
			  				 transactionobject.setDateCreated(new Date());
			  				 transactionobject.setMessage("Transaction Done");
			  				 transactionobject.setStatus(TransactionStatus.APPROVED);
			  				 transactionobject.setUserOrderType(buyer.getOrderType());
			  	             transactionRepository.save(transactionobject);
			  	              
			  	              wallet.setBalance((wallet.getBalance()-buyer.getGrossAmount()));
	   	  				      wallet1.setBalance((buyer.getCoinQuantity()+wallet1.getBalance()));
	   	  				      wallet1.setShadowBalance((buyer.getCoinQuantity()+wallet1.getShadowBalance()));
	   	  				      walletRepository.save(wallet);
		  				      walletRepository.save(wallet1);
							      
						          buyer.setStatus(UserOrderStatus.APPROVED);
	   	  				 	      buyer.setUser(buyer.getUser());
	   	  				 	      userOrderRepository.save(buyer);
	   	  				 	      
	   	  				 	  tempcoin.setCoinInINR(tempcoin.getCoinInINR());
						      tempcoin.setProfit(buyer.getFees());
		   	                  tempcoin.setInitialSupply(0);
		   	                   currencyRepository.save(tempcoin);
							}
							else if(buyer.getCoinQuantity()<tempcoin.getInitialSupply())
							{
								Transaction transactionobject=new Transaction();
								 transactionobject.setFees(buyer.getFees());
		   	  				     transactionobject.setNetAmount(buyer.getCoinQuantity()*buyer.getPrice());
		   	  				     transactionobject.setGrossAmount(buyer.getGrossAmount());
		   	  				transactionobject.setBuyerId(buyer.getUser().getUserId());
							 transactionobject.setCoinType(CoinType.CRYPTO);
			  				 transactionobject.setCoinName(buyer.getCoinName());
			  				 transactionobject.setSellerId(0);
			  				 transactionobject.setExchangeRate(buyer.getPrice());
			  				 transactionobject.setDateCreated(new Date());
			  				 transactionobject.setMessage("Transaction Done");
			  				 transactionobject.setStatus(TransactionStatus.APPROVED);
			  				 transactionobject.setUserOrderType(buyer.getOrderType());
			  	             transactionRepository.save(transactionobject);
			  	             
			  	             wallet.setBalance((wallet.getBalance()-buyer.getGrossAmount()));
	   	  				     wallet1.setBalance((buyer.getCoinQuantity()+wallet1.getBalance()));
	   	  				     wallet1.setShadowBalance((buyer.getCoinQuantity()+wallet1.getShadowBalance()));
	   	  				     walletRepository.save(wallet);
		  				     walletRepository.save(wallet1);
		  				     
								 tempcoin.setCoinInINR(tempcoin.getCoinInINR()+(buyer.getCoinQuantity()*buyer.getPrice()-(buyer.getCoinQuantity()*tempcoin.getPrice())));
		   	  			         tempcoin.setProfit(buyer.getFees()+(tempcoin.getProfit()));
		   	  				     tempcoin.setInitialSupply(tempcoin.getInitialSupply()-buyer.getCoinQuantity());
		   	  				     currencyRepository.save(tempcoin);
								 
		   	  				     buyer.setStatus(UserOrderStatus.APPROVED);
		   	  				 	 buyer.setUser(buyer.getUser());
		   	  				 	 userOrderRepository.save(buyer);
							}
						} 	
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
		   	  
			          if(seller.getPrice()<=buyer.getPrice()&&(!(seller.getUser().getUserId()==buyer.getUser().getUserId())))
			          {
			        	  if(seller.getPrice()<=currencycoin.getPrice())
			        	  {
			        		  
			        		  ////Seller sell////
			        		  if(buyer.getCoinQuantity()>seller.getCoinQuantity()&&(walletsellercrypto.getShadowBalance()>=0&&walletsellercrypto.getBalance()>=0))
								{
			        			  Transaction transactionsellerobject=new Transaction();
			        			  transactionsellerobject.setSellerId(seller.getUser().getUserId());
				                   transactionsellerobject.setFees(currencycoin.getInitialSupply()*buyer.getPrice()*currencycoin.getFees()/100);
				                   transactionsellerobject.setNetAmount(currencycoin.getInitialSupply()*buyer.getPrice());
				                   transactionsellerobject.setGrossAmount(((currencycoin.getInitialSupply()*buyer.getPrice()+(currencycoin.getInitialSupply()*buyer.getPrice())*currencycoin.getFees())/100));
				                   transactionsellerobject.setBuyerId(user.getUserId());
				                   
				                     
									 transactionsellerobject.setCoinType(CoinType.CRYPTO);
									 transactionsellerobject.setCoinName(buyer.getCoinName());
									 transactionsellerobject.setExchangeRate(buyer.getPrice());
									 transactionsellerobject.setDateCreated(new Date());
									 transactionsellerobject.setMessage("Transaction Done");
									 transactionsellerobject.setStatus(TransactionStatus.APPROVED);
									 transactionsellerobject.setUserOrderType(seller.getOrderType());
									 transactionRepository.save(transactionsellerobject);

				                  
				         
				                   wallet.setBalance((wallet.getBalance()-(buyer.getGrossAmount()-((seller.getCoinQuantity()*buyer.getPrice()+(seller.getCoinQuantity()*buyer.getPrice())*currencycoin.getFees())/100))));
			   	  				   
				                   wallet1.setBalance((seller.getCoinQuantity()+wallet1.getBalance()));
				                   
				                   wallet1.setShadowBalance((seller.getCoinQuantity()+wallet1.getShadowBalance()));
				                   
				                   walletseller.setBalance(walletseller.getBalance()+seller.getCoinQuantity()*seller.getPrice());
			   	  				   walletsellercrypto.setBalance(walletsellercrypto.getBalance()-seller.getCoinQuantity());
			   	  				     
			   	  				   walletRepository.save(walletseller);
				                   walletRepository.save(walletsellercrypto);
				                  
				                   walletRepository.save(wallet1);
			   	  				   walletRepository.save(wallet);
			   	  				
			   	  				     double amount=seller.getCoinQuantity()*buyer.getPrice()-seller.getCoinQuantity()*seller.getPrice();
				                  
				                  currencycoin.setCoinInINR(amount+currencycoin.getCoinInINR());
				                  currencycoin.setProfit((((seller.getCoinQuantity()*buyer.getPrice())*currencycoin.getFees())/100)+currencycoin.getProfit());
			   	  				     
			   	  				   buyer.setCoinQuantity(buyer.getCoinQuantity()-seller.getCoinQuantity());
				                   buyer.setStatus(UserOrderStatus.PENDING);
				                   buyer.setGrossAmount(buyer.getGrossAmount()-((seller.getCoinQuantity()*buyer.getPrice()+(seller.getCoinQuantity()*buyer.getPrice())*currencycoin.getFees())/100));
				                   buyer.setFees(((buyer.getCoinQuantity()-currencycoin.getInitialSupply())*buyer.getPrice()*currencycoin.getFees())/100);
				                    seller.setStatus(UserOrderStatus.APPROVED);
				                   //user id //
				                   buyer.setUser(buyer.getUser());
				                   seller.setUser(seller.getUser());
				                   
				                   userOrderRepository.save(buyer);
			   	  				   userOrderRepository.save(seller);

								}
								else if(buyer.getCoinQuantity()==seller.getCoinQuantity())
								{
									Transaction transactionsellerobject=new Transaction();
									  
				                     transactionsellerobject.setSellerId(seller.getUser().getUserId());
			   	  				     transactionsellerobject.setFees(buyer.getFees());
			   	  				     transactionsellerobject.setNetAmount(buyer.getCoinQuantity()*buyer.getPrice());
			   	  				     transactionsellerobject.setGrossAmount(buyer.getGrossAmount());	
			   	  				 transactionsellerobject.setBuyerId(user.getUserId());
								 transactionsellerobject.setCoinType(CoinType.CRYPTO);
								 transactionsellerobject.setCoinName(buyer.getCoinName());
								 transactionsellerobject.setExchangeRate(buyer.getPrice());
								 transactionsellerobject.setDateCreated(new Date());
								 transactionsellerobject.setMessage("Transaction Done");
								 transactionsellerobject.setStatus(TransactionStatus.APPROVED);
								 transactionsellerobject.setUserOrderType(buyer.getOrderType());
								 transactionRepository.save(transactionsellerobject);
								 
								 
								 wallet.setBalance((wallet.getBalance()-buyer.getGrossAmount()));
		   	  				     wallet1.setBalance((buyer.getCoinQuantity()+wallet1.getBalance()));
		   	  				     wallet1.setShadowBalance((buyer.getCoinQuantity()+wallet1.getShadowBalance()));
		   	  				     
		   	  				
		   	  				     walletseller.setBalance(walletseller.getBalance()+seller.getCoinQuantity()*seller.getPrice());
		   	  				     walletsellercrypto.setBalance(walletsellercrypto.getBalance()-seller.getCoinQuantity());
		   	  				     
		   	  				      walletRepository.save(walletseller);
			                      walletRepository.save(walletsellercrypto);
			                   
							      walletRepository.save(wallet1);
	   	  				          walletRepository.save(wallet);
									
	   	  				          currencycoin.setCoinInINR((buyer.getCoinQuantity()*buyer.getPrice()-(seller.getCoinQuantity()*seller.getPrice()))+currencycoin.getCoinInINR());
							      currencycoin.setProfit(buyer.getFees()+(currencycoin.getProfit()));
			   	  			      
							      
							      
			   	  				 	 buyer.setStatus(UserOrderStatus.APPROVED);
			   	  				 	 seller.setStatus(UserOrderStatus.APPROVED);
			   	  				 	 buyer.setUser(buyer.getUser());
			   	  				     seller.setUser(seller.getUser()); 
			   	  				     userOrderRepository.save(buyer);
			   	  				     userOrderRepository.save(seller);
			     				    
				  				 
								}
								else if(buyer.getCoinQuantity()<seller.getCoinQuantity())
								{
									Transaction transactionsellerobject=new Transaction();
									   
			   	  				     transactionsellerobject.setSellerId(seller.getUser().getUserId());
			   	  				     transactionsellerobject.setFees(currencycoin.getFees());
			   	  				     transactionsellerobject.setNetAmount(buyer.getCoinQuantity()*buyer.getPrice());
			   	  				     transactionsellerobject.setGrossAmount(buyer.getGrossAmount());
			   	  				     
			   	  				 transactionsellerobject.setBuyerId(user.getUserId());
								 transactionsellerobject.setCoinType(CoinType.CRYPTO);
								 transactionsellerobject.setCoinName(buyer.getCoinName());
								 transactionsellerobject.setExchangeRate(buyer.getPrice());
								 transactionsellerobject.setDateCreated(new Date());
								 transactionsellerobject.setMessage("Transaction Done");
								 transactionsellerobject.setStatus(TransactionStatus.APPROVED);
								 transactionsellerobject.setUserOrderType(buyer.getOrderType());
								 transactionRepository.save(transactionsellerobject);
								 
								 wallet.setBalance((wallet.getBalance()-buyer.getGrossAmount()));
		   	  				     wallet1.setBalance((buyer.getCoinQuantity()+wallet1.getBalance()));
		   	  				     wallet1.setShadowBalance((buyer.getCoinQuantity()+wallet1.getShadowBalance()));
		   	  				     
		   	  				     walletseller.setBalance(walletseller.getBalance()+buyer.getCoinQuantity()*seller.getPrice());
	   	  				         walletsellercrypto.setBalance(walletsellercrypto.getBalance()-buyer.getCoinQuantity());
	   	  				     
	   	  				         walletRepository.save(walletseller);
		                         walletRepository.save(walletsellercrypto);
		   	  				  
							     walletRepository.save(wallet1);
	   	  				         walletRepository.save(wallet);
	   	  				         
	   	  				        buyer.setStatus(UserOrderStatus.APPROVED);
		   	  				    buyer.setUser(buyer.getUser());
		   	  				    seller.setStatus(UserOrderStatus.PENDING);
		   	  				     
		   	  				     seller.setUser(seller.getUser());
								 
		   	  				     userOrderRepository.save(buyer);
	   	  				         userOrderRepository.save(seller);
							     currencycoin.setCoinInINR(buyer.getCoinQuantity()+(buyer.getCoinQuantity()*buyer.getPrice()-(buyer.getCoinQuantity()*seller.getPrice()))+currencycoin.getCoinInINR());
			   	  			     currencycoin.setProfit(buyer.getFees()+(currencycoin.getProfit()));
				  				 
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
										Transaction transactionsellerobject=new Transaction();
										  

						                   transactionsellerobject.setFees(currencycoin.getInitialSupply()*buyer.getPrice()*currencycoin.getFees()/100);
						                   transactionsellerobject.setNetAmount(currencycoin.getInitialSupply()*buyer.getPrice());
						                   transactionsellerobject.setGrossAmount(((currencycoin.getInitialSupply()*buyer.getPrice()+(currencycoin.getInitialSupply()*buyer.getPrice())*currencycoin.getFees())/100));
						                   transactionsellerobject.setBuyerId(user.getUserId());
											 transactionsellerobject.setCoinType(CoinType.CRYPTO);
											 transactionsellerobject.setCoinName(buyer.getCoinName());
											 transactionsellerobject.setExchangeRate(buyer.getPrice());
											 transactionsellerobject.setDateCreated(new Date());
											 transactionsellerobject.setMessage("Transaction Done");
											 transactionsellerobject.setStatus(TransactionStatus.APPROVED);
											 transactionsellerobject.setUserOrderType(buyer.getOrderType());
											 transactionRepository.save(transactionsellerobject);
											
											 wallet.setBalance((wallet.getBalance()-(buyer.getGrossAmount()-((currencycoin.getInitialSupply()*buyer.getPrice()+(currencycoin.getInitialSupply()*buyer.getPrice())*currencycoin.getFees())/100))));
						   	  				 wallet1.setBalance((currencycoin.getInitialSupply()+wallet1.getBalance()));
							                 wallet1.setShadowBalance((currencycoin.getInitialSupply()+wallet1.getShadowBalance()));
							                  
							                 walletRepository.save(wallet1);
						   	  				 walletRepository.save(wallet);
												 
						   	  			   buyer.setCoinQuantity(buyer.getCoinQuantity()-currencycoin.getInitialSupply());
						                   buyer.setStatus(UserOrderStatus.PENDING);
						                   buyer.setGrossAmount(buyer.getGrossAmount()-((currencycoin.getInitialSupply()*buyer.getPrice()+(currencycoin.getInitialSupply()*buyer.getPrice())*currencycoin.getFees())/100));
						                   buyer.setFees(((buyer.getCoinQuantity()-currencycoin.getInitialSupply())*buyer.getPrice()*currencycoin.getFees())/100);	 
						                   buyer.setUser(buyer.getUser());
						                   
						                  double amount=currencycoin.getInitialSupply()*buyer.getPrice()-currencycoin.getInitialSupply()*currencycoin.getPrice();
						                  currencycoin.setCoinInINR(amount+currencycoin.getCoinInINR());
						                  currencycoin.setProfit((((currencycoin.getInitialSupply()*buyer.getPrice())*currencycoin.getFees())/100)+currencycoin.getProfit());
						                  currencycoin.setInitialSupply(0);
						                  currencyRepository.save(currencycoin); 
						                  
						                   //user id //
						                   
						     
						                   
							  				 
									}
									else if(buyer.getCoinQuantity()==currencycoin.getInitialSupply())
									{
										Transaction transactionsellerobject=new Transaction();
									 transactionsellerobject.setFees(buyer.getFees());
				   	  				 transactionsellerobject.setNetAmount(buyer.getCoinQuantity()*buyer.getPrice());
				   	  				 transactionsellerobject.setGrossAmount(buyer.getGrossAmount());
				   	  				 transactionsellerobject.setBuyerId(user.getUserId());
									 transactionsellerobject.setCoinType(CoinType.CRYPTO);
									 transactionsellerobject.setCoinName(buyer.getCoinName());
									 transactionsellerobject.setExchangeRate(buyer.getPrice());
									 transactionsellerobject.setDateCreated(new Date());
									 transactionsellerobject.setMessage("Transaction Done");
									 transactionsellerobject.setStatus(TransactionStatus.APPROVED);
									 transactionsellerobject.setUserOrderType(buyer.getOrderType());
									 transactionRepository.save(transactionsellerobject);
									 

			     				     wallet.setBalance((wallet.getBalance()-buyer.getGrossAmount()));
			   	  				     wallet1.setBalance((buyer.getCoinQuantity()+wallet1.getBalance()));
			   	  				     wallet1.setShadowBalance((buyer.getCoinQuantity()+wallet1.getShadowBalance()));
			   	  				     walletRepository.save(wallet1);
		   	  				         walletRepository.save(wallet);


			   	  				 	 buyer.setStatus(UserOrderStatus.APPROVED);
			   	  				 	 buyer.setUser(buyer.getUser());
			   	  				 	 
								     currencycoin.setCoinInINR((currencycoin.getInitialSupply()*buyer.getPrice()-(currencycoin.getInitialSupply()*currencycoin.getPrice()))+currencycoin.getCoinInINR());
								     currencycoin.setProfit(buyer.getFees()+(currencycoin.getProfit()));
							         currencycoin.setInitialSupply(0);
							         currencyRepository.save(currencycoin);
									 					  				 
									}
									else if(buyer.getCoinQuantity()<currencycoin.getInitialSupply())
									{
										Transaction transactionsellerobject=new Transaction();
										 
										 transactionsellerobject.setFees(buyer.getFees());
				   	  				     transactionsellerobject.setNetAmount(buyer.getCoinQuantity()*buyer.getPrice());
				   	  				     transactionsellerobject.setGrossAmount(buyer.getGrossAmount());
				   	  				     transactionsellerobject.setBuyerId(user.getUserId());
									     transactionsellerobject.setCoinType(CoinType.CRYPTO);
									     transactionsellerobject.setCoinName(buyer.getCoinName());
									     transactionsellerobject.setExchangeRate(buyer.getPrice());
									     transactionsellerobject.setDateCreated(new Date());
									     transactionsellerobject.setMessage("Transaction Done");
									     transactionsellerobject.setStatus(TransactionStatus.APPROVED);
									     transactionsellerobject.setUserOrderType(buyer.getOrderType());
									     transactionRepository.save(transactionsellerobject);
									     

										 wallet.setBalance((wallet.getBalance()-buyer.getGrossAmount()));
				   	  				     wallet1.setBalance((buyer.getCoinQuantity()+wallet1.getBalance()));
				   	  				     wallet1.setShadowBalance((buyer.getCoinQuantity()+wallet1.getShadowBalance()));
				   	  			     
										 walletRepository.save(wallet1);
				   	  				     walletRepository.save(wallet);
				   	  				     
				   	  				    buyer.setStatus(UserOrderStatus.APPROVED);
			   	  				 	    buyer.setUser(buyer.getUser());
			   	  				 	    userOrderRepository.save(buyer);   
			   	  				 	    
										 currencycoin.setCoinInINR((buyer.getCoinQuantity()*buyer.getPrice()-(currencycoin.getInitialSupply()*currencycoin.getPrice()))+currencycoin.getCoinInINR());
				   	  			         currencycoin.setProfit(buyer.getFees()+(currencycoin.getProfit()));
				   	  			         currencycoin.setInitialSupply(currencycoin.getInitialSupply()-buyer.getCoinQuantity());
				   	  				     currencyRepository.save(currencycoin);				  				 
									}
								}
								else if(buyer.getPrice()==currencycoin.getPrice())
								{
									if(buyer.getCoinQuantity()>currencycoin.getInitialSupply())
									{
										Transaction transactionsellerobject=new Transaction();

						                  transactionsellerobject.setSellerId(0);
						                  transactionsellerobject.setFees(currencycoin.getInitialSupply()*buyer.getPrice()*currencycoin.getFees()/100);
						                  transactionsellerobject.setNetAmount(currencycoin.getInitialSupply()*buyer.getPrice());
						                  transactionsellerobject.setGrossAmount(((currencycoin.getInitialSupply()*buyer.getPrice()+(currencycoin.getInitialSupply()*buyer.getPrice())*currencycoin.getFees())/100));
						                  transactionsellerobject.setBuyerId(user.getUserId());
											 transactionsellerobject.setCoinType(CoinType.CRYPTO);
											 transactionsellerobject.setCoinName(buyer.getCoinName());
											 transactionsellerobject.setExchangeRate(buyer.getPrice());
											 transactionsellerobject.setDateCreated(new Date());
											 transactionsellerobject.setMessage("Transaction Done");
											 transactionsellerobject.setStatus(TransactionStatus.APPROVED);
											 transactionsellerobject.setUserOrderType(buyer.getOrderType());
											 transactionRepository.save(transactionsellerobject);
										
											  wallet.setBalance((wallet.getBalance()-(buyer.getGrossAmount()-((currencycoin.getInitialSupply()*buyer.getPrice()+(currencycoin.getInitialSupply()*buyer.getPrice())*currencycoin.getFees())/100))));
						   	  				  wallet1.setBalance((currencycoin.getInitialSupply()+wallet1.getBalance()));
							                  wallet1.setShadowBalance((currencycoin.getInitialSupply()+wallet1.getShadowBalance()));
							                  walletRepository.save(wallet1);
						   	  				  walletRepository.save(wallet);
								  			
											  buyer.setCoinQuantity(buyer.getCoinQuantity()-currencycoin.getInitialSupply());
							                  buyer.setStatus(UserOrderStatus.PENDING);
							                  buyer.setGrossAmount(buyer.getGrossAmount()-((currencycoin.getInitialSupply()*buyer.getPrice()+(currencycoin.getInitialSupply()*buyer.getPrice())*currencycoin.getFees())/100));
							                  buyer.setFees(((buyer.getCoinQuantity()-currencycoin.getInitialSupply())*buyer.getPrice()*currencycoin.getFees())/100);
							                  
							                  //user id //
							                  buyer.setUser(buyer.getUser());
							                  userOrderRepository.save(buyer);
											 
						                  double amount=currencycoin.getInitialSupply()*buyer.getPrice()-currencycoin.getInitialSupply()*currencycoin.getPrice();
						                  currencycoin.setCoinInINR(amount+currencycoin.getCoinInINR());
						                  currencycoin.setProfit((((currencycoin.getInitialSupply()*buyer.getPrice())*currencycoin.getFees())/100)+currencycoin.getProfit());
						                  currencycoin.setInitialSupply(0);
						                  currencyRepository.save(currencycoin);
						                  
						                  
						                	 
									}
									else if(buyer.getCoinQuantity()==currencycoin.getInitialSupply())
									{ 
										Transaction transactionsellerobject=new Transaction();
			   	  				      transactionsellerobject.setSellerId(0);
			   	  				      transactionsellerobject.setFees(buyer.getFees());
			   	  				      transactionsellerobject.setNetAmount(buyer.getCoinQuantity()*buyer.getPrice());
			   	  				      transactionsellerobject.setGrossAmount(buyer.getGrossAmount());
			   	  				 transactionsellerobject.setBuyerId(user.getUserId());
								 transactionsellerobject.setCoinType(CoinType.CRYPTO);
								 transactionsellerobject.setCoinName(buyer.getCoinName());
								 transactionsellerobject.setExchangeRate(buyer.getPrice());
								 transactionsellerobject.setDateCreated(new Date());
								 transactionsellerobject.setMessage("Transaction Done");
								 transactionsellerobject.setStatus(TransactionStatus.APPROVED);
								 transactionsellerobject.setUserOrderType(buyer.getOrderType());
								        transactionRepository.save(transactionsellerobject);
								 
								         wallet.setBalance((wallet.getBalance()-buyer.getGrossAmount()));
		   	  				             wallet1.setBalance((buyer.getCoinQuantity()+wallet1.getBalance()));
		   	  				             wallet1.setShadowBalance((buyer.getCoinQuantity()+wallet1.getShadowBalance()));
								         walletRepository.save(wallet1);
		   	  				             walletRepository.save(wallet);
		   	  				        

				   	  				     buyer.setStatus(UserOrderStatus.APPROVED);
			   	  				 	     buyer.setUser(buyer.getUser());
			   	  				 	     userOrderRepository.save(buyer);
			   	  				 	     
		   	  				           currencycoin.setCoinInINR(currencycoin.getCoinInINR());
							           currencycoin.setProfit(buyer.getFees());
		   	  				           currencycoin.setInitialSupply(0);
		   	  				           currencyRepository.save(currencycoin);
									}
									else if(buyer.getCoinQuantity()<currencycoin.getInitialSupply())
									{
										Transaction transactionsellerobject=new Transaction();
			
				   	  				     transactionsellerobject.setSellerId(0);
				   	  				     transactionsellerobject.setFees(buyer.getFees());
				   	  				     transactionsellerobject.setNetAmount(buyer.getCoinQuantity()*buyer.getPrice());
				   	  				     transactionsellerobject.setGrossAmount(buyer.getGrossAmount());
				   	  				 transactionsellerobject.setBuyerId(user.getUserId());
									 transactionsellerobject.setCoinType(CoinType.CRYPTO);
									 transactionsellerobject.setCoinName(buyer.getCoinName());
									 transactionsellerobject.setExchangeRate(buyer.getPrice());
									 transactionsellerobject.setDateCreated(new Date());
									 transactionsellerobject.setMessage("Transaction Done");
									 transactionsellerobject.setStatus(TransactionStatus.APPROVED);
									 transactionsellerobject.setUserOrderType(buyer.getOrderType());
									 transactionRepository.save(transactionsellerobject);
									 

									 wallet.setBalance((wallet.getBalance()-buyer.getGrossAmount()));
			   	  				     wallet1.setBalance((buyer.getCoinQuantity()+wallet1.getBalance()));
			   	  				     wallet1.setShadowBalance((buyer.getCoinQuantity()+wallet1.getShadowBalance()));
									 walletRepository.save(wallet1);
			   	  				     walletRepository.save(wallet);
			   	  				     
			   	  				    buyer.setStatus(UserOrderStatus.APPROVED);
		   	  				 	    buyer.setUser(buyer.getUser());
		   	  				 	    userOrderRepository.save(buyer);
		   	  				 	  
		   	  				 	    currencycoin.setCoinInINR((buyer.getCoinQuantity()*buyer.getPrice()-(currencycoin.getInitialSupply()*currencycoin.getPrice()))+currencycoin.getCoinInINR());
		   	  			            currencycoin.setProfit(buyer.getFees()+(currencycoin.getProfit()));
		   	  				        currencycoin.setInitialSupply(currencycoin.getInitialSupply()-buyer.getCoinQuantity());
		   	  				        currencyRepository.save(currencycoin);  
								 }
							}
					    }  
			       }
			    }
		     }
	      }
	   }
		return "Transaction Action has been Approved";
	}
 }
