package com.example.service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dto.UserWalletDto;
import com.example.dto.WalletApprovalDto;
import com.example.enums.StatusType;
import com.example.enums.UserStatus;
import com.example.enums.WalletType;
import com.example.model.Transaction;
import com.example.model.User;
import com.example.model.UserOrder;
import com.example.model.Wallet;
import com.example.repository.OrderRepository;
import com.example.repository.TransactionRepository;
import com.example.repository.UserRepository;
import com.example.repository.WalletRepository;

@Service
public class WalletService
{   @Autowired
	private UserRepository userrepository;
    @Autowired
    private OrderRepository orderrepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private WalletRepository walletRepository;
	private User user;
	private UserOrder userorder;
	private Transaction transaction=new Transaction();
	@Autowired
	private WalletRepository walletrepository;
	//Wallet wallet;
	static int value=0;
	//private UserWalletDto userwalletdto;
	
 public String addWallet(UserWalletDto userwalletdto)
 {
	 user=userrepository.findByUserId(userwalletdto.getUserId());
	 if(user.getStatus()==UserStatus.ACTIVE)
	 {
	 Wallet wallet=new Wallet();
	 wallet.setCoinType(userwalletdto.getCoinType());
	 wallet.setCoinName(userwalletdto.getCoinName());
	 wallet.setBalance(0.0);
	 wallet.setShadowbalance(0.0);
	 wallet.setUser(user);
	 walletrepository.save(wallet);
	 return "wallet added in service";
	 }
	 else
		 return "invalid user";
 }
 
 public String walletApprovalStatus(WalletApprovalDto walletApprovalDto)
 {   User user1=userrepository.findByUserId(walletApprovalDto.getUserId());
    
     Wallet wallet=null;
	 String date=new Date()+"";

	 userorder=orderrepository.findByOrderId(walletApprovalDto.getOrderId());
	 user=userorder.getUser();
     
	 if(user.getStatus().equals(UserStatus.ACTIVE))
	 {
		
		 if(walletApprovalDto.getStatusType()==StatusType.COMPLETED)
		 {
			 Set<Wallet> walletlist=user1.getWallet();
		     for(Wallet s:walletlist)
		     {
		    	 
		   
		    	 if(s.getCoinType()==WalletType.FIAT)
		    	 {
		    		 wallet=s;
		    		 s.setBalance(userorder.getGrossAmount());
		    		 s.setShadowbalance(userorder.getGrossAmount()); 
		    	 }
		     }
		     
			 walletRepository.save(wallet);
			 transaction.setCoinType(WalletType.FIAT);
			 transaction.setCoinName(userorder.getCoinName());
			 transaction.setTransactionCreatedOn(date);
			 transaction.setStatus(StatusType.COMPLETED);
			 transaction.setDescription(walletApprovalDto.getDescription());
			 transaction.setGrossAmount(userorder.getGrossAmount());
			 transaction.setNetAmount(userorder.getNetAmount());
			 transactionRepository.save(transaction);
			 userorder.setStatusType(StatusType.APPROVED);
			 orderrepository.save(userorder);
			 return "Deposit is approved";
		 }
		 else if(walletApprovalDto.getStatusType()==StatusType.REJECTED)
		  {
			 transaction.setCoinType(WalletType.FIAT);
			 transaction.setCoinName(userorder.getCoinName());
			 transaction.setTransactionCreatedOn(date);
			 transaction.setStatus(StatusType.REJECTED);
			 transaction.setDescription(walletApprovalDto.getDescription());
			 transaction.setGrossAmount(userorder.getGrossAmount());
			 transaction.setNetAmount(userorder.getNetAmount());
			 transactionRepository.save(transaction);
			 userorder.setStatusType(StatusType.REJECTED);
			 orderrepository.save(userorder);
			 return "Diposit is rejected";
		  }
		 
	 }
	 else
	    return "user is not active"; 
    return "";
 }
 
 public  void addBalance()
 {
	 
 }

 public String withdrawamount(UserWalletDto userwalletdto)
 {
	 
	User user=userrepository.findByUserId(userwalletdto.getUserId());
	
	 if(user!=null )
  {
		 if(userwalletdto.getCoinType()==WalletType.FIAT) 
		 {
	        Set<Wallet> wallett=user.getWallet();
	        for(Wallet fiatWallet:wallett)
	        {
	        	if(fiatWallet.getCoinType()==WalletType.FIAT)
	        	{
	        		Double walletBalance=fiatWallet.getBalance();
	        		Double amount=userwalletdto.getAmount();
	   		     if(walletBalance>=amount)
	   		     {
	   		      fiatWallet.setBalance(walletBalance-amount);
	   		      fiatWallet.setShadowbalance(walletBalance-amount);
	   		      walletrepository.save(fiatWallet);
	   		      return "Amount withdrawl";
	   		     }
	   		     else
	   			   return "balance too low";
	   	       
	   		     
	        	}
	        	
	        }
		   // 
		    //Wallet wallet=walletrepository.findByWalletType(userwalletdto.getWalletType());
		    
		 }
		 else
			 return "Wrong wallet type for withdrawl";
  }
  else
	 return "user doesnot exist";
	return "";
	 
	
	 
 }
}


