package com.trainingproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trainingproject.domain.User;
import com.trainingproject.domain.UserOrder;
import com.trainingproject.domain.Wallet;
import com.trainingproject.dto.ApproveWalletBean;
import com.trainingproject.enums.OrderType;
import com.trainingproject.enums.UserOrderStatus;
import com.trainingproject.enums.WalletType;
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
		userorder.setOrderStatus(UserOrderStatus.APPROVED);
		userorderRepository.save(userorder);
		Integer userId=userorder.getUserId();
		User user =userService.getUserById(userId).get();
	     List<Wallet>walletlist=user.getUserWallet();
	     Wallet fiatWallet=null;
	     for(int i=0;i<walletlist.size();i++) {
	    	 if(walletlist.get(i).getWalletType()==WalletType.FIAT) {
	    		 fiatWallet=walletlist.get(i);
	    		 break;
	    	 }
	     }
	     OrderType orderType=userorder.getOrderType();
	     if(orderType==OrderType.DEPOSIT) {
	    	 fiatWallet.setBalance(fiatWallet.getBalance()+userorder.getPrice());
	    	 fiatWallet.setShadowBal(fiatWallet.getShadowBal()+userorder.getPrice());
	    	 walletRepository.save(fiatWallet);
	    	 return "success";
	     }
	     else if(orderType==OrderType.WITHDRAW) {
	    	 fiatWallet.setBalance(fiatWallet.getBalance()-userorder.getPrice());
	    	 fiatWallet.setShadowBal(fiatWallet.getShadowBal()-userorder.getPrice());
	    	 walletRepository.save(fiatWallet);
	    	 return "success";
	     }
	     return "failure";
		
	}
}
