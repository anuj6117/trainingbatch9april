package com.trainingproject.service;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trainingproject.domain.Currency;
import com.trainingproject.domain.Transaction;
import com.trainingproject.domain.UserOrder;
import com.trainingproject.domain.Wallet;
import com.trainingproject.enums.CoinType;
import com.trainingproject.enums.OrderType;
import com.trainingproject.enums.TransactionStatus;
import com.trainingproject.enums.UserOrderStatus;
import com.trainingproject.repository.CurrencyRepository;
import com.trainingproject.repository.TransactionRepository;
import com.trainingproject.repository.UserOrderRepository;
import com.trainingproject.repository.WalletRepository;

@Service
public class TransactionService2  {

	@Autowired
	UserOrderRepository orderRepository;
	
	@Autowired
	CurrencyRepository  currencyRepository;
	@Autowired
	WalletRepository walletRepository;
	@Autowired
	TransactionRepository transactionRepository;
	
	
	public String approveTransaction() {
		
		List<UserOrder> sellers=null;
		List<UserOrder> buyers=null;
		
       
		 sellers=orderRepository.findByorderTypeAndOrderStatus(OrderType.SELL,UserOrderStatus.PENDING);
		 buyers=orderRepository.findByorderTypeAndOrderStatus(OrderType.BUY,UserOrderStatus.PENDING);
      
		TransactionComparater comparator=new TransactionComparater();
		if(sellers!=null)
		Collections.sort(sellers, comparator);
		if(buyers!=null)
		Collections.sort(buyers,Collections.reverseOrder(comparator));
		
		Currency admin;
		Transaction transaction=new Transaction();
		
		if(sellers==null||sellers.size()==0) {
			
			if(buyers==null||buyers.size()==0) {
				
				//no orders present
				return "no orders present";
			}
			
			else
			{
				//only buyers are present  but no seller
				//looping through all buyers if they can buy from admin
				for (UserOrder buyer : buyers) {
					
					String coin_name=buyer.getCoinName();
					CoinType coin_type=buyer.getCoinType();
					
					admin=currencyRepository.findBycoinNameAndCoinType(coin_name, coin_type);
					
					if(admin==null) {
						//this buyer cannot buy from admin as admin dont have this currency
						continue;
					}
					else {
						
				         double buyer_price=buyer.getPrice();
				         double admin_price=admin.getPrice();
				         double amount=buyer.getCoinQuantity()*buyer_price;
				         
						if(buyer_price<admin_price) {
							//buyer cannot buy 
							//transaction wont happen
							//setTransactionStatus(buyer,null,"price<admin price",TransactionStatus.FAILED,admin);
							continue;
						}
						
						//buyer will buy from admin
						else {
							
							double buyer_cq=buyer.getCoinQuantity();
							double admin_cq=admin.getInitialSupply();
							
							double coin_bought=0;
							double diff=buyer_cq-admin_cq;
							
							if(diff<0) {
								coin_bought=buyer_cq;
			setTransactionStatus(buyer,null,"price<admin price",TransactionStatus.APPROVED,admin,coin_bought);
							}
							else {
								coin_bought=admin_cq;
			setTransactionStatus(buyer,null,"all admins coins",TransactionStatus.APPROVED,admin,coin_bought);
							}
							
						}
					}
					
				}
				
			}
		}
		//sellers present
		else {
			
			//sellers present but no buyers
			if(buyers==null||buyers.size()==0) {
				return "no buyers present";
			}
			
			//buyers and sellers both present
			else {
				
				for (UserOrder buyer : buyers) {
					
					for (UserOrder seller : sellers) {
						
						String coin_name=buyer.getCoinName();
						
						if(coin_name.equals(seller.getCoinName())) {
							
							admin=currencyRepository.findBycoinName(coin_name);
							
					         if(admin==null)
					        	 return "admin dont have this currency";
					         
					      
					         else {
					        	 
					        	 double buyer_price=buyer.getPrice();
						         double admin_price=admin.getPrice();
						         double seller_price=seller.getPrice();
						          double fee=admin.getFees();
						          double init_supply=admin.getInitialSupply();
						          
						          double buyer_cq=buyer.getCoinQuantity();
									double admin_cq=admin.getInitialSupply();
									double seller_cq=seller.getCoinQuantity();
									
									
						         //buyer will buy from admin
									if(buyer_price>admin_price)
						         if(admin_price<seller_price) {
						        	
										double coin_bought=0;
										double diff=buyer_cq-admin_cq;
										
										if(diff<0) {
											coin_bought=buyer_cq;
						setTransactionStatus(buyer,null,"all coins bought",TransactionStatus.APPROVED,admin,coin_bought);
										}
										else {
											coin_bought=admin_cq;
						setTransactionStatus(buyer,null,"all admins coins",TransactionStatus.APPROVED,admin,coin_bought);
										}
										
						         }
						         
						         //buyer will buy from seller
						         if(buyer_price>seller_price&&seller_cq>0) 
						         {
						         

										double coin_bought=0;
										double diff=buyer_cq-seller_cq;
										
										if(diff<0) {
											coin_bought=buyer_cq;
						setTransactionStatus(buyer,seller,"all coins bought",TransactionStatus.APPROVED,admin,coin_bought);
										}
										else {
											coin_bought=seller_cq;
						setTransactionStatus(buyer,seller,"not all bought",TransactionStatus.APPROVED,admin,coin_bought);
										}
								
						         }
					        	   
					        	
									
					         }
						}
					}
					//if no seller is found than check if buyer can buy from admin
					
					
					buyFromAdmin(buyer);
				}
				
			}
			
		}
		
		return "success";
	}

	private void buyFromAdmin(UserOrder buyer) {
		
		Currency admin=currencyRepository.findBycoinName(buyer.getCoinName());
		if(admin==null)
			return ;
		Transaction trans=new Transaction();
		
		double buyer_price=buyer.getPrice();
        double admin_price=admin.getPrice();
       
         double buyer_cq=buyer.getCoinQuantity();
			double admin_cq=admin.getInitialSupply();
			double coin_bought=0;
			
		 if(buyer_price>admin_price) {
	        	
				
				double diff=buyer_cq-admin_cq;
				
				if(diff<0) {
					coin_bought=buyer_cq;
setTransactionStatus(buyer,null,"all coins bought",TransactionStatus.APPROVED,admin,coin_bought);
				}
				else {
					coin_bought=admin_cq;
setTransactionStatus(buyer,null,"all admins coins",TransactionStatus.APPROVED,admin,coin_bought);

				}
				
        }
		 admin.setInitialSupply(admin.getInitialSupply()-coin_bought);
		 currencyRepository.save(admin);
		 transactionRepository.save(trans);
		
	}

	private void setTransactionStatus(UserOrder buyer,UserOrder seller, String remarks,TransactionStatus status, Currency admin,double coin_bought) {
		
		//add date
		Transaction trans=new Transaction();
		double net_amount=0;
		double gross_amount=0,fee=0,inr_profit=0,fee_profit=0;
		
		if(admin!=null) {
			fee=admin.getFees();
			trans.setFee(fee);
		}
		
		if(buyer!=null) {
			
			Wallet buyer_fwallet=walletRepository.findBycoinNameAndUser("INR",buyer.getUser());
			Wallet buyer_cwallet=walletRepository.findBycoinNameAndUser(buyer.getCoinName(),buyer.getUser());
			
			trans.setBuyer(buyer.getUserId());
			trans.setExchangeRate(buyer.getPrice());
			
			trans.setCoinName(buyer.getCoinName());
			trans.setCoinType(buyer.getCoinType());
			
			if(coin_bought!=0) {
				
				net_amount=coin_bought*buyer.getPrice();
				gross_amount=net_amount+net_amount*fee/100;
			}
			fee_profit=net_amount*fee/100;
			admin.setProfit(fee_profit);
			
			trans.setAmount(net_amount);
			trans.setGrossAmount(gross_amount);
			
			buyer.setFee(fee);
			buyer.setGrossAmount(gross_amount);
			
			if(buyer.getCoinQuantity()==coin_bought) {
				buyer.setOrderStatus(UserOrderStatus.APPROVED);
				buyer.setCoinQuantity(0);
			}
			else {
				buyer.setCoinQuantity(buyer.getCoinQuantity()-coin_bought);
				buyer.setOrderStatus(UserOrderStatus.PENDING);
			}
			
			
			buyer_fwallet.setBalance(buyer_fwallet.getBalance()-gross_amount);
			buyer_fwallet.setShadowBal(buyer_fwallet.getShadowBal()-gross_amount);
			buyer_fwallet.setCoinQuantity(buyer.getCoinQuantity()-gross_amount);
			
			buyer_cwallet.setBalance(buyer_cwallet.getBalance()+coin_bought);
			buyer_cwallet.setShadowBal(buyer_cwallet.getShadowBal()+coin_bought);
			buyer_cwallet.setCoinQuantity(buyer.getCoinQuantity()+coin_bought);
			
			walletRepository.save(buyer_cwallet);
			walletRepository.save(buyer_fwallet);
			
			orderRepository.save(buyer);
		}
		if(seller!=null) {
			
			Wallet seller_fwallet=walletRepository.findBycoinNameAndUser("INR",seller.getUser());
			Wallet seller_cwallet=walletRepository.findBycoinNameAndUser(seller.getCoinName(),seller.getUser());
			
			trans.setSeller(seller.getUserId());
			double diff=seller.getCoinQuantity()-coin_bought;
			
			seller.setCoinQuantity(diff);
			seller.setFee(0);
			if(diff==0)
			seller.setOrderStatus(UserOrderStatus.APPROVED);
			else seller.setOrderStatus(UserOrderStatus.PENDING);
			seller.setGrossAmount(diff*seller.getPrice());
			
			inr_profit=net_amount-diff*seller.getPrice();
			admin.setCoinInINR(inr_profit);
			
			
			seller_fwallet.setBalance(seller_fwallet.getBalance()+diff*seller.getPrice());
			seller_fwallet.setShadowBal(seller_fwallet.getShadowBal()+diff*seller.getPrice());
			seller_fwallet.setCoinQuantity(seller.getCoinQuantity()-coin_bought);
			
			seller_cwallet.setBalance(seller_cwallet.getBalance()-coin_bought);
			seller_cwallet.setShadowBal(seller_cwallet.getShadowBal()-coin_bought);
			seller_cwallet.setCoinQuantity(seller.getCoinQuantity()-coin_bought);
			
			walletRepository.save(seller_cwallet);
			walletRepository.save(seller_fwallet);
			
			orderRepository.save(seller);
		}
		else {
			if(admin!=null) {
			trans.setSeller(admin.getCoinId());
			
			if(seller==null) {
				inr_profit=net_amount-coin_bought*admin.getPrice();
				admin.setCoinInINR(inr_profit);
				admin.setInitialSupply(admin.getInitialSupply()-coin_bought);
			}
			}
		}
		  trans.setRemarks(remarks);
		  trans.setStatus(status);
		  
		  transactionRepository.save(trans);
		  currencyRepository.save(admin);
		
	}

	
	
	public  List<Transaction> getAllTransaction() {
		return transactionRepository.findAll();
		
	}

}
