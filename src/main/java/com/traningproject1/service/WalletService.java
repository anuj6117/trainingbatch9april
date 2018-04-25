package com.traningproject1.service;

import java.util.Iterator;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.traningproject1.demo.dto.WalletApprovalDTO;
import com.traningproject1.domain.User;
import com.traningproject1.domain.UserOrder;
import com.traningproject1.domain.Wallet;
import com.traningproject1.enumsclass.CoinType;
import com.traningproject1.enumsclass.UserOrderStatus;
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
	public String walletApproval(WalletApprovalDTO walletapprovaldto)
	{
	  UserOrder userorder=userOrderRepository.findByuserorderId(walletapprovaldto.getUserorderId());
    // if(UserOrderStatus.PENDING)
	 // if()
	  userorder.setStatus(UserOrderStatus.APPROVED);
      User user=userorder.getUser();
      //User user=userRepository.findByuserId(userorder.getUser().getUserId());
      
      Set<Wallet> walletlist=user.getWallet();
       Iterator <Wallet> itr=walletlist.iterator();
//       while(itr.hasNext())
//       {
//    	   Wallet wallet=itr.next();
//    	   if(wallet.getCoinType().equals(CoinType.FIATE))
//    	   {
//    		   wallet.setBalance(userorder.get());
//    		   System.out.println("+++++++++++++++++"+userorder.getAmount());    		   wallet.setShadowBalance(userorder.getAmount());
//    		   
//    		   System.out.println("+++++++++++++++++");
//    		   walletRepository.save(wallet); 
//    	   }
//       }
      
	  userOrderRepository.save(userorder);
	  userRepository.save(user);
	  
	  return "Success";	
	}
}
