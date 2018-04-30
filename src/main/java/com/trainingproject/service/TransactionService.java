package com.trainingproject.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trainingproject.domain.Currency;
import com.trainingproject.domain.Transaction;
import com.trainingproject.domain.UserOrder;
import com.trainingproject.domain.Wallet;
import com.trainingproject.enums.OrderType;
import com.trainingproject.enums.TransactionStatus;
import com.trainingproject.enums.UserOrderStatus;
import com.trainingproject.repository.CurrencyRepository;
import com.trainingproject.repository.TransactionRepository;
import com.trainingproject.repository.UserOrderRepository;
import com.trainingproject.repository.WalletRepository;

@Service
public class TransactionService implements Comparator<UserOrder> {

	@Autowired
	UserOrderRepository orderRepository;
	
	@Autowired
	CurrencyRepository  currencyRepository;
	@Autowired
	WalletRepository walletRepository;
	@Autowired
	TransactionRepository transactionRepository;
	
	public String approveTransaction() {
	
		List<UserOrder> sellers=orderRepository.findByorderType(OrderType.SELL);
		List<UserOrder> buyers=orderRepository.findByorderType(OrderType.BUY);
		
		Collections.sort(sellers, this);
		Collections.sort(buyers,Collections.reverseOrder());
		
	     if(sellers.size()==0) {
	    	 
	    	 //check if admin has the currencies for buyers
	    	 for(int i=0;i<buyers.size();i++) {
	    		 
	    		 //check if admin has that currency for sale
	    		 String coinName=buyers.get(i).getCoinName();
	    		 Currency admin=currencyRepository.findBycoinName(coinName);
	    		 if(admin==null)
	    			 break;
	    		 
	    		 
	    		 Integer coinBuyed=buyers.get(i).getCoinQuantity()-admin.getInitialSupply();
	            	
	            	if(coinBuyed<=0) {
	            		//all coins will be buyed
	            		coinBuyed=buyers.get(i).getCoinQuantity()*-1;
	            	}
	            	else {
	            		coinBuyed=admin.getInitialSupply();
	            	}
	            	
	            	
	    		 
	    		 //check if buyer has that kind of money or not for purchasing
	    		 Integer fees=currencyRepository.findBycoinName(coinName).getFees();
					long totprice= (buyers.get(i).getPrice()*coinBuyed);
					long fee=(fees*totprice)/100;
					long grossAmount=totprice+fee;
					
				    Wallet buyerwallet=walletRepository.findBycoinNameAndUser(coinName, buyers.get(i).getUser());
				    
					Transaction trans=new Transaction();
		            if(buyerwallet.getShadowBal()<grossAmount) {
		            	//transaction will be failed
		            	
		            	
		            	trans.setBuyer(buyers.get(i).getUser().getUserId());
						trans.setSeller(admin.getId());
						trans.setFee(fees);
						trans.setDate(new Date());
						trans.setCoinName(coinName);
						trans.setCoinType(buyers.get(i).getCoinType());
						trans.setStatus(TransactionStatus.FAILED);
						trans.setAmount(totprice);
						trans.setGrossAmount(grossAmount);
						trans.setRemarks("insufficient funds");
					    transactionRepository.save(trans);
						
					    buyers.get(i).setOrderStatus(UserOrderStatus.FAILED);
					    orderRepository.save(buyers.get(i));
					    
		            	break;
		       
		            }
		            else {
		            	//approve transaction
		            
		            
						trans.setBuyer(buyers.get(i).getUser().getUserId());
						trans.setSeller(admin.getId());
						trans.setFee(fees);
						trans.setDate(new Date());
						trans.setCoinName(coinName);
						trans.setCoinType(buyers.get(i).getCoinType());
						trans.setStatus(TransactionStatus.APPROVED);
						trans.setAmount(totprice);
						trans.setGrossAmount(grossAmount);
					    transactionRepository.save(trans);
						
					    buyers.get(i).setOrderStatus(UserOrderStatus.APPROVED);
					    orderRepository.save(buyers.get(i));
						
						
		            }
	    	 }
	    	 return "success :: no sellers";
	     }
	     
	     else if(buyers.size()==0) {
	    	 //
	    	 
	    	 return "success :: no buyers";
	     }
		
	
			
			for(int i=0;i<sellers.size();i++) {
				
				UserOrder seller=sellers.get(i);
			
			if(sellers.get(i).getOrderStatus().equals(UserOrderStatus.PENDING)) {
				
					
					for(int j=0;j<buyers.size();j++) {
						
						UserOrder buyer=buyers.get(i);
					
					String coinName=buyers.get(i).getCoinName();
					
					
					if(buyer.getOrderStatus().equals(UserOrderStatus.PENDING)&&seller.getCoinName().equals(coinName))
					{
						
						Integer scQ=seller.getCoinQuantity();
						Integer bcq=buyer.getCoinQuantity();
						
						Integer fees=currencyRepository.findBycoinName(coinName).getFees();
						long totprice= (buyers.get(i).getPrice()*buyers.get(i).getCoinQuantity());
						long fee=(fees*totprice)/100;
						long grossAmount=totprice+fee;
						
					    Wallet buyerwallet=walletRepository.findBycoinNameAndUser(coinName, buyer.getUser());
					    
					    Transaction trans=new Transaction();
					    
			            if(buyerwallet.getShadowBal()<grossAmount) {
			            	
			            	trans.setBuyer(buyer.getUser().getUserId());
							trans.setSeller(seller.getUserId());
							trans.setFee(fees);
							trans.setDate(new Date());
							trans.setCoinName(coinName);
							trans.setCoinType(buyers.get(i).getCoinType());
							trans.setStatus(TransactionStatus.FAILED);
							trans.setAmount(totprice);
							trans.setGrossAmount(grossAmount);
							trans.setRemarks("insufficient funds");
						    transactionRepository.save(trans);
							
						    buyers.get(i).setOrderStatus(UserOrderStatus.FAILED);
						    orderRepository.save(buyers.get(i));
						    
						    
			            	continue;
			       
			            }
						
			            //approve transaction
						
			            trans.setBuyer(buyer.getUser().getUserId());
						trans.setSeller(seller.getUserId());
						trans.setFee(fees);
						trans.setDate(new Date());
						trans.setCoinName(coinName);
						trans.setCoinType(buyer.getCoinType());
						trans.setStatus(TransactionStatus.APPROVED);
						trans.setAmount(totprice);
						trans.setGrossAmount(grossAmount);
					    transactionRepository.save(trans);
						
					    buyers.get(i).setOrderStatus(UserOrderStatus.APPROVED);
					    orderRepository.save(buyer);
					
						
					}
				}
				
			}
		}
		
		return "success :: both present";
	}

	@Override
	public int compare(UserOrder o1, UserOrder o2) {
		
		if(o1.getPrice()!=o2.getPrice())
		return (int) (o1.getPrice()-o2.getPrice());
		
		else return o1.getDate().compareTo(o2.getDate());
		
	}

}
