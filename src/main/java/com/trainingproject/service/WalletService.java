package com.trainingproject.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trainingproject.domain.Transaction;
import com.trainingproject.domain.User;
import com.trainingproject.domain.UserOrder;
import com.trainingproject.domain.Wallet;
import com.trainingproject.dto.ApproveWalletBean;
import com.trainingproject.enums.CoinType;
import com.trainingproject.enums.OrderType;
import com.trainingproject.enums.UserOrderStatus;

import com.trainingproject.repository.UserOrderRepository;
import com.trainingproject.repository.WalletRepository;

@Service
public class WalletService {

	@Autowired
	WalletRepository walletRepository;
	@Autowired
	UserOrderService userorderService;
	@Autowired
	UserOrderRepository userorderRepository;
	@Autowired
	UserService userService;
	
	
	public Wallet createWallet(Wallet wallet) {
		Wallet createdWallet=walletRepository.save(wallet);
		return createdWallet;
	}


	public String approveWallet(ApproveWalletBean awb) {
		
		
		UserOrder userorder=userorderService.getUserOrderById(awb.getOrderId());
		if(userorder.getOrderStatus()==UserOrderStatus.APPROVED)
			return "already approved";
		
		userorder.setOrderStatus(awb.getOrderStatus());
		userorderRepository.save(userorder);
		Integer userId=userorder.getUserId();
		User user =userService.getUserById(userId).get();
	     List<Wallet>walletlist=user.getUserWallet();
	     Wallet fiatWallet=walletRepository.findBycoinTypeAndUser(CoinType.FIAT, user);
	     OrderType orderType=userorder.getOrderType();
	     if(orderType==OrderType.DEPOSIT) {
	    	 
	    	  Transaction transaction=new Transaction();
	 	     transaction.setBuyer(null);
	 	     transaction.setAmount(userorder.getPrice());
	 	     transaction.setFee(0);
//	 	     transaction.setWalletType(CoinType.FIAT);
//	 	    transaction.setDate(userorder.getDate());
//	 	   transaction.setExchangeRate(0);
//	 	   transaction.set
	 	     
	    	 if(awb.getOrderStatus()==UserOrderStatus.APPROVED)
	    	 fiatWallet.setBalance(fiatWallet.getBalance()+userorder.getPrice());
	    	 if(awb.getOrderStatus()!=UserOrderStatus.FAILED)
	    	 fiatWallet.setShadowBal(fiatWallet.getShadowBal()+userorder.getPrice());
	    	 walletRepository.save(fiatWallet);
	    	 
	    	 if(awb.getOrderStatus()==UserOrderStatus.FAILED)
	    		 return "request declined";
	    	 else 
	    	 return "success";
	     }
	     else if(orderType==OrderType.WITHDRAW) {
	    	 if(awb.getOrderStatus()==UserOrderStatus.APPROVED)
	    	 fiatWallet.setBalance(fiatWallet.getBalance()-userorder.getPrice());
	    	 if(awb.getOrderStatus()!=UserOrderStatus.FAILED)
	    	 fiatWallet.setShadowBal(fiatWallet.getShadowBal()-userorder.getPrice());
	    	 walletRepository.save(fiatWallet);
	    	 return "success";
	     }
	     return "";
	   
		
	}
}
