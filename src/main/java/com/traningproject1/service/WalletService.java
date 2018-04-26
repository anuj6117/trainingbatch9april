package com.traningproject1.service;

import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.traningproject1.demo.dto.WalletApprovalDTO;
import com.traningproject1.domain.Transaction;
import com.traningproject1.domain.User;
import com.traningproject1.domain.UserOrder;
import com.traningproject1.domain.Wallet;
import com.traningproject1.enumsclass.CoinType;
import com.traningproject1.enumsclass.UserOrderStatus;
import com.traningproject1.repository.TransactionRepository;
import com.traningproject1.repository.UserOrderRepository;
import com.traningproject1.repository.UserRepository;
import com.traningproject1.repository.WalletRepository;

@Service
public class WalletService {

//	public void addwallet(Wallet wallet) {
		@Autowired
	    UserOrderRepository userOrderRepository;
		@Autowired 
		UserRepository userRepository;
		@Autowired 
		WalletRepository walletRepository;
		@Autowired
		TransactionRepository transactionRepository;
	public String walletApproval(WalletApprovalDTO walletapprovaldto)
	{
	  UserOrder userorder=userOrderRepository.findByuserorderId(walletapprovaldto.getUserorderId());
    // if(UserOrderStatus.PENDING)
	  User user=userorder.getUser();
	 if(userorder.getStatus().equals(UserOrderStatus.PENDING))
	 {
	  userorder.setStatus(UserOrderStatus.APPROVED);
      
      Transaction transaction=new Transaction();
      transaction.setCoinType(CoinType.FIATE);
      transaction.setStatus(walletapprovaldto.getTransactionStatus());
      transaction.setMessage(walletapprovaldto.getMessage());
      transaction.setUserOrderType(userorder.getOrderType());
      transaction.setNetAmount(userorder.getCoinQuantity());
      transaction.setDateCreated(new Date());
      //User user=userRepository.findByuserId(userorder.getUser().getUserId());
      
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
    		   //System.out.println("+++++++++++++++++"+userorder.getAmount());    		   wallet.setShadowBalance(userorder.getAmount());
    		   
    		   //System.out.println("+++++++++++++++++");
    		   walletRepository.save(wallet); 
    	   }
//       }
      
	 }
       transactionRepository.save(transaction);
	}
	  userOrderRepository.save(userorder);
	  userRepository.save(user);
	  
	  return "Success";	
	}
}