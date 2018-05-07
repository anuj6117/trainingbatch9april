package com.example.service;

import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dto.UserWalletDto;
import com.example.enums.OrderType;
import com.example.enums.StatusType;
import com.example.enums.UserStatus;
import com.example.enums.WalletType;
import com.example.model.Role;
import com.example.model.User;
import com.example.model.UserOrder;
import com.example.model.Wallet;
import com.example.repository.OrderRepository;
import com.example.repository.RoleRepository;
//import com.example.model.UserOtpTable;
import com.example.repository.UserRepository;
import com.example.repository.WalletRepository;

@Service
public class SignUpService 
{
	@Autowired
	private UserRepository userrepository;

	@Autowired
	private WalletRepository walletrepository;

	@Autowired
	private RoleRepository rolerepository;

	private Integer otpNum;
	
	@Autowired
	private OTPService otps;
	
    
	private User user;
	
	@Autowired
	private MailService mailController;
	@Autowired
	private OtpTableService otptableservice;
	@Autowired
	private OrderRepository orderRepository;
	
   
	
	
	public String addUser(User user)
	{ 
         //System.out.println("[[[[[[[[[[[[[[[[[[[[[[[[[[[[ "+userrepository.Eemail(10));
		 String date=new Date()+"";
		 Random random=new Random();
		 otpNum=random.nextInt(899000)+1000;
		 String otpval=""+otpNum;
		 //user.se(date);
		 //String newpassword=user.getPassword().trim();
		 String newpassword=user.getPassword().replaceAll("\\s+",""); 
		 user.setPassword(newpassword);
		 String name=user.getUserName();
		 user.setUserName(name.trim());
	
		 user.setCreatedOn(date);
		 user.setStatus((UserStatus.INACTIVE));
		 userrepository.save(user);
		 Set<Wallet> walletset=new HashSet<Wallet>();
		 Wallet wallet = new Wallet();
	     wallet.setWalletType(WalletType.FIAT);
	     wallet.setWalletName("INR");
	     wallet.setBalance(0);
	     wallet.setShadowbalance(0);
		 wallet.setUser(user);
		 walletset.add(wallet);
		 walletrepository.save(wallet);
		 Set<Role> rolelist=new HashSet<Role>();
		 Role role1=rolerepository.findByType("user");
		 Role role2=new Role();
		 if(role1!=null)
		 {
			 rolelist.add(role1);
		 }
		 else
		 {
			 role2.setType("user");
			 rolerepository.save(role2);
		 rolelist.add(role2);
		 }
		 
		 
		 user.setRoles(rolelist);
		 
		
		
		 if( !(userrepository.save(user) == null))
		{
			
			otps.sendSms(otpNum,user.getPhoneNumber());
			mailController.getMailOtp(otpval);
			otptableservice.valuemethod(user,otpval);
			mailController.home(user.getEmail());
			 
			return "Sent Successfully ";
		}
		else
		{
			return "Failure";
		}
	   
	}
	public String updateuser(User user)
	 {  
		 String date=new Date()+"";
		// user.getCreatedOn(date);
		 user.setCreatedOn(date);
		 user.setStatus(UserStatus.ACTIVE);
		
		if(!(userrepository.save(user)==null))
		{
			return "updates successfully";
		}
		else
			return "Not updated, null value";
		
	 }
	 
	public String deleteUser(User user)
	{
		userrepository.delete(user);
		System.out.println("repository se delete ho gya");
		return "deleted";
	}
	
	
	public String depositamount(UserWalletDto userwalletdto)
	 {
		UserOrder userorder=new UserOrder();
		 
		 if(userwalletdto==null)
	   {return "userwalletdto is null";
	   }else
	   {
		  String date=new Date()+"";
		  
		 userorder.setStatusType(StatusType.PENDING);
		 userorder.setCoinType(WalletType.FIAT);
		 userorder.setCoinName(userwalletdto.getWalletName());
		 userorder.setNetAmount(userwalletdto.getAmount());
		 userorder.setGrossAmount(userwalletdto.getAmount());
		 userorder.setOrderType(OrderType.DEPOSIT);
		 userorder.setOrderCreatedOn(date);
		 user=userrepository.findByUserId(userwalletdto.getUserId());
		 userorder.setUser(user);
		 //userorder.setId(1);
		 orderRepository.save(userorder);
		 
		 return "All value inserted";
	   }
		 /*
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
		 
	*/	
		 
	 }
	 

	
}

