package com.example.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.enums.UserStatus;
import com.example.enums.WalletType;
import com.example.model.Role;
import com.example.model.User;
import com.example.model.Wallet;
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
	
	@Autowired
	private MailService mailController;
	@Autowired
	private OtpTableService otptableservice;
	

	
	
	public String addUser(User user)
	{ 
		if( !(userrepository.findByEmail(user.getEmail())==null) )
		{
			return null;
		}
		else
		{	
		 String date=new Date()+"";
		 Random random=new Random();
		 otpNum=random.nextInt(8999)+1000;
		 String otpval=""+otpNum;
		 user.setDate(date);
		 user.setStatus((UserStatus.INACTIVE));
		 userrepository.save(user);
		 Set<Wallet> walletset=new HashSet<Wallet>();
		 Wallet wallet = new Wallet();
	     wallet.setWalletType(WalletType.FIAT);
		 wallet.setUser(user);
		 walletset.add(wallet);
		 walletrepository.save(wallet);
		 Set<Role> rolelist=new HashSet<Role>();
		 rolelist.add(rolerepository.findByType("User"));
		 user.setRoles(rolelist);
		
		
		 if( !(userrepository.save(user) == null))
		{
			
			//otps.sendSms(otpNum);
			mailController.getMailOtp(otpval);
			otptableservice.valuemethod(user,otpval);
			mailController.home();
			 
			return "Sent Successfully ";
		}
		else
		{
			return "Failure";
		}
	   }
	}
	public String updateuser(User user)
	 {  
		 String date=new Date()+"";
		 user.setDate(date);
		 
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
	
}

