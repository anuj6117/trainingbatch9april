package com.example.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dto.UserWalletDto;
import com.example.enums.OrderType;
import com.example.enums.StatusType;
import com.example.enums.WalletType;
import com.example.model.User;
import com.example.model.Wallet;
import com.example.repository.OrderRepository;
import com.example.repository.UserRepository;
import com.example.repository.WalletRepository;

@Service
public class WalletService
{   @Autowired
	private UserRepository userrepository;
    @Autowired
    private OrderRepository orderrepository;
	private User user;
	
	@Autowired
	private WalletRepository walletrepository;
	static int value=0;
	//private UserWalletDto userwalletdto;
	
 public String addWallet(UserWalletDto userwalletdto)
 {
	 user=userrepository.findByUserId(userwalletdto.getUserId());
	 Wallet wallet=new Wallet();
	 wallet.setWalletType(userwalletdto.getWalletType());
	 wallet.setUser(user);
	 walletrepository.save(wallet);
	 return "wallet added in service";
 }
 
/* public String depositamount(UserWalletDto userwalletdto)
 {
	 
	 if(userwalletdto==null)
   {return "userwalletdto is null::::::::::::::::::::::::::::::::::::::::::::";
   }else
   { System.out.println("::::::::::::::::::::::::::::::::date");
	  String date=new Date()+"";
	  System.out.println("::::::::::::::::::::::::::::::::pendingSt");
	  
	 order.setStatusType(StatusType.PENDING);
	 System.out.println("::::::::::::::::::::::::::::::::enummmmmmmmmmmm");
	 order.setCoinName(WalletType.FIAT.toString());
	 System.out.println("::::::::::::::::::::::::::::::::minummmmmmmmmmmmmmm");
	 value+=userwalletdto.getAmount();
	 order.setPrice(value)
	 order.setOrderType(OrderType.DEPOSIT);
	 order.setOrderCcreatedOn(date);
	 user=userrepository.findByUserId(userwalletdto.getUserId());
	 order.setUser(user);
	// order.setId(1);
	 orderrepository.save(order);
	 System.out.println(">>>>>>>>>>>>>>>>"+order.getStatusType());
	 
	 return "All value inserted";
   }
	 
	 user=userrepository.findByUserId(userwalletdto.getUserId());
	 
	 if(user!=null)
{
	 if(walletrepository.findByWalletType(userwalletdto.getWalletType())!=null)
	 {
		 int val=userwalletdto.getAmount();
		 Wallet wallet=walletrepository.findByWalletType(userwalletdto.getWalletType());
		 wallet.setBalance(val);
		 walletrepository.save(wallet);
		 return "amount deposited";
		 
	 }
	 else
		 return "wallettype doesnot available";
	 
	 
}
else
	return "user doesnot exist";
	 
	
	 
 }
 */
 public String withdrawamount(UserWalletDto userwalletdto)
 {
	 
	 user=userrepository.findByUserId(userwalletdto.getUserId());
	
	 if(user!=null )
  {
	 if(walletrepository.findByWalletType(userwalletdto.getWalletType())!=null) 
	 {
		 int amount=userwalletdto.getAmount();
		 Wallet wallet=walletrepository.findByWalletType(userwalletdto.getWalletType());
		 int walletBalance=wallet.getBalance();
		 if(walletBalance>=amount)
		 {
		 wallet.setBalance(walletBalance-amount);
		 walletrepository.save(wallet);
		 return "amount withdrawl";
		 }
		 else
			 return "balance too low";
	 }
	 else
		 return "wallettype doesnot available";
	 
	 
  }
  else
	 return "user doesnot exist";
	 
	
	 
 }
}


