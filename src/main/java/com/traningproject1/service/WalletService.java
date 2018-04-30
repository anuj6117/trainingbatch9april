package com.traningproject1.service;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.traningproject1.demo.dto.AssignWalletDTO;
import com.traningproject1.demo.dto.WalletApprovalDTO;
import com.traningproject1.domain.CurrencyClass;
import com.traningproject1.domain.Transaction;
import com.traningproject1.domain.User;
import com.traningproject1.domain.UserOrder;
import com.traningproject1.domain.Wallet;
import com.traningproject1.enumsclass.CoinType;
import com.traningproject1.enumsclass.TransactionStatus;
import com.traningproject1.enumsclass.UserOrderStatus;
import com.traningproject1.enumsclass.UserOrderType;
import com.traningproject1.enumsclass.UserStatus;
import com.traningproject1.repository.CurrencyRepository;
import com.traningproject1.repository.TransactionRepository;
import com.traningproject1.repository.UserOrderRepository;
import com.traningproject1.repository.UserRepository;
import com.traningproject1.repository.WalletRepository;

@Service
public class WalletService 
   { 
		@Autowired
	    UserOrderRepository userOrderRepository;
		@Autowired 
		UserRepository userRepository;
		@Autowired 
		WalletRepository walletRepository;
		@Autowired
		TransactionRepository transactionRepository;
		@Autowired
		CurrencyRepository currencyRepository;
		Wallet wallet1;
		public String assignWallet(AssignWalletDTO assignwalletDTO)
		{
			User user=null;
			boolean flag=false;
			boolean flag1=false;
			try {
			     user=userRepository.findByuserId(assignwalletDTO.getUserId());
			}
			catch(Exception e)
			{
				return "User doesn't exist ";
			}
		    System.out.println("++++++++++++++++++++++++++++"+user.getWallet().toString());
			if(user.getStatus().equals(UserStatus.INACTIVE))
			{
				return "User is Not Authorized";
			}
			List<CurrencyClass>coinNameValue=currencyRepository.findAll();
	    	Iterator<CurrencyClass>itr1=coinNameValue.iterator();
	    	while(itr1.hasNext())
	    	{
	    		CurrencyClass cc=itr1.next();
	    		if(cc.getCoinName().equals(assignwalletDTO.getCoinName()))
	    		{
	    			flag1=true;
	    		}
	    	}
			Set<Wallet>walletuser=user.getWallet();
			Iterator<Wallet>itr=walletuser.iterator();
			
			 while(itr.hasNext())
			  {
				Wallet wallet2=itr.next();
			    if(wallet2.getCoinType().equals(CoinType.CRYPTO)&&wallet2.getCoinName().equals(assignwalletDTO.getCoinName()))
				 {
					 flag=true;
				 }
			  }
			if((!flag)&&flag1)
			{
		     wallet1=new Wallet();
			 wallet1.setUser(user);
			 wallet1.setCoinName(assignwalletDTO.getCoinName());
			 wallet1.setCoinType(CoinType.CRYPTO);
			 HashSet<Wallet>walletset=new HashSet<>();
		     walletset.add(wallet1);
		     user.setWallet(walletset);
		     walletRepository.save(wallet1);
		     userRepository.save(user);
		     return "Wallet Added Successfully"; 
			}
			else
			{
				return "Coin is not present in the Coin or currency table /Wallet For this Coin Name Already Exist";
			}
			
		}
		
		
  

  public String walletApproval(WalletApprovalDTO walletapprovaldto)
  {
	  boolean flag=false;
	  UserOrder userorder=userOrderRepository.findByuserorderId(walletapprovaldto.getUserorderId());
	  User user=userorder.getUser();
	
	  if(userorder.getOrderType().equals(UserOrderType.DEPOSIT))
	  {
		  flag=true;
	  }
	  System.out.println("1111111111111111111111111"+userorder.getOrderType());
	   if(user.getStatus().equals(UserStatus.INACTIVE))
	   {
		  return "User is Not Authorized or Inactive";
	   }
	   if(flag)
	   {
	    Transaction transaction=new Transaction();
	   if(walletapprovaldto.getTransactionStatus().equals(TransactionStatus.APPROVED))
	   {
	      userorder.setStatus(UserOrderStatus.APPROVED);
      
           transaction.setCoinType(CoinType.FIATE);
      
         Set<Wallet> walletlist=user.getWallet();
         Iterator <Wallet> itr=walletlist.iterator();
         while(itr.hasNext())
          {
    	   Wallet wallet=itr.next();
    	   if(wallet.getCoinType().equals(CoinType.FIATE))
    	   {
    		   wallet.setBalance(userorder.getCoinQuantity());
    		   wallet.setCoinName(userorder.getCoinName());
    		   
    		   wallet.setShadowBalance(userorder.getCoinQuantity());
    		   walletRepository.save(wallet); 
    	   }    		   
        }
      }
	  else
	  {
		  userorder.setStatus(UserOrderStatus.FAILED);
	  }
       
       
       
	  transaction.setStatus(walletapprovaldto.getTransactionStatus());
	  transaction.setMessage(walletapprovaldto.getMessage());
	  transaction.setCoinType(CoinType.FIATE);
      transaction.setUserOrderType(userorder.getOrderType());
      transaction.setGrossAmount(userorder.getGrossAmount());
      transaction.setNetAmount(userorder.getCoinQuantity());
      transaction.setDateCreated(new Date());

       transactionRepository.save(transaction);

	  userOrderRepository.save(userorder);
	  userRepository.save(user);
	  }
	  return "Success";	
   }
}