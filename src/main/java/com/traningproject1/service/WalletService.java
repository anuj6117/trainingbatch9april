package com.traningproject1.service;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.traningproject1.demo.dto.AssignWalletDTO;
import com.traningproject1.demo.dto.WalletApprovalDTO;
import com.traningproject1.domain.Transaction;
import com.traningproject1.domain.User;
import com.traningproject1.domain.UserOrder;
import com.traningproject1.domain.Wallet;
import com.traningproject1.enumsclass.CoinType;
import com.traningproject1.enumsclass.TransactionStatus;
import com.traningproject1.enumsclass.UserOrderStatus;
import com.traningproject1.enumsclass.UserStatus;
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
		Wallet wallet1;
		
		public String assignWallet(AssignWalletDTO assignwalletDTO)
		{
			User user=userRepository.findByuserId(assignwalletDTO.getUserId());
			
		     if(user==null)
		     {
		    	 return "User Doesn't exist";
		     }
			//Wallet wallet=walletRepository.findByCoinType(assignwalletDTO.getUserId());
			
			if(user.getWallet().equals(CoinType.FIATE))
			{
				return "Wallet Type Already exist";
			}
			if(user.getStatus().equals(UserStatus.INACTIVE))
			{
				return "User is Not Authorized";
			}
			Set<Wallet>walletuser=user.getWallet();
			Iterator<Wallet>itr=walletuser.iterator();
			while(itr.hasNext())
			{
				Wallet wallet2=itr.next();
				if(wallet2.getCoinName().equals(assignwalletDTO.getCoinName()))
				{
					return "Walet for Coin Name Already Exist";
				}
			}
		      
		    wallet1=new Wallet();
			
			wallet1.setUser(user);
			wallet1.setCoinName(assignwalletDTO.getCoinName());
			wallet1.setCoinType(CoinType.CRYPTO);
			
					  
			
				
			
			
			HashSet<Wallet>walletset=new HashSet<>();
		    
			//System.out.println("walletset++++++++++++++++++++++++++++"+walletset);
			
		    walletset.add(wallet1);
		   
		    //System.out.println("id++++++++++++++++++++++++++++"+user.getUserId());
		    
		    
		    
		    user.setWallet(walletset);
		    walletRepository.save(wallet1);
		    userRepository.save(user);
		    
			return "success";
		}
	public String walletApproval(WalletApprovalDTO walletapprovaldto)
	{
	  UserOrder userorder=userOrderRepository.findByuserorderId(walletapprovaldto.getUserorderId());
	  User user=userorder.getUser();
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
      transaction.setUserOrderType(userorder.getOrderType());
      transaction.setNetAmount(userorder.getCoinQuantity());
      transaction.setDateCreated(new Date());

       transactionRepository.save(transaction);

	  userOrderRepository.save(userorder);
	  userRepository.save(user);
	  
	  return "Success";	
	}
}