package com.trainingproject.service;

//import java.text.SimpleDateFormat;

//import java.util.Date;
import java.util.List;
//import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trainingproject.domain.Transaction;
import com.trainingproject.domain.User;
import com.trainingproject.domain.UserOrder;
import com.trainingproject.domain.Wallet;
//import com.trainingproject.domain.WalletHistory;
import com.trainingproject.dto.ApproveWalletBean;
import com.trainingproject.enums.CoinType;
import com.trainingproject.enums.OrderType;
import com.trainingproject.enums.TransactionStatus;
import com.trainingproject.enums.UserOrderStatus;
import com.trainingproject.repository.TransactionRepository;
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
	@Autowired
	TransactionRepository transactionRepository;
	
	
	public String createWallet(Wallet wallet) {
		
		Wallet ewall=walletRepository.findBycoinNameAndCoinType(wallet.getCoinName(),wallet.getCoinType());
		if(ewall!=null)
			return "wallet already exists";
		walletRepository.save(wallet);
		return "success";
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
	     Wallet fiatWallet=null;
	    // fiatWallet=walletRepository.findBycoinTypeAndUser(CoinType.FIAT, user);
	     for(int i=0;i<walletlist.size();i++) {
	    	 if(walletlist.get(i).getCoinType()==CoinType.FIAT) {
	    		 
	    		 if(walletlist.get(i).getCoinName().equals(userorder.getCoinName())) {
	    			 fiatWallet=walletlist.get(i);
	    			 break;
	    		 }
	    	 }
	     }
	    
	     OrderType orderType=userorder.getOrderType();
	     
	     
	     Transaction transaction=new Transaction();
	     
	     transaction.setBuyer(null);
 	     transaction.setAmount(userorder.getPrice());
 	     transaction.setFees(0);
 	     transaction.setCoinType(CoinType.FIAT);
 	   
 	 
 	     transaction.setDate(userorder.getDate());
 	     transaction.setExchangeRate(0);
 	    transaction.setCoinName(userorder.getCoinName());
 	   transaction.setCoinType(userorder.getCoinType());
 	   
 	     if(awb.getOrderStatus().equals(UserOrderStatus.APPROVED))
 	     transaction.setStatus(TransactionStatus.APPROVED);
 	     else  if(awb.getOrderStatus().equals(UserOrderStatus.PENDING))
 	    	 transaction.setStatus(TransactionStatus.PENDING);
 	    else  if(awb.getOrderStatus().equals(UserOrderStatus.FAILED))
 	    	 transaction.setStatus(TransactionStatus.FAILED);
         transactionRepository.save(transaction);
 	   
         
         
	     if(orderType==OrderType.DEPOSIT) {
	    	
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
	
	public List<UserOrder> walletHistory(Integer userId,CoinType coinType) {
		
		return userorderRepository.findBycoinTypeAndUser(coinType, userService.getUserById(userId).get());
		
	}
}
