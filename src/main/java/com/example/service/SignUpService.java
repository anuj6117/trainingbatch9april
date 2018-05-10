package com.example.service;

import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.regex.Pattern;

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
	     wallet.setCoinType(WalletType.FIAT);
	     wallet.setCoinName("INR");
	     wallet.setBalance(0.0);
	     wallet.setShadowbalance(0.0);
		 wallet.setUser(user);
		 walletset.add(wallet);
		 walletrepository.save(wallet);
		 Set<Role> rolelist=new HashSet<Role>();
		 Role role1=rolerepository.findByRoleType("user");
		 Role role2=new Role();
		 if(role1!=null)
		 {
			 rolelist.add(role1);
		 }
		 else
		 {
			 role2.setRoleType("user");
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
		
		
		 User user1=userrepository.findByEmail(user.getEmail());
		   User user2=userrepository.findByPhoneNumber(user.getPhoneNumber());
		   User user3=userrepository.findByUserId(user.getUserId());
		   User userIdExistOrNot=userrepository.findByUserId(user.getUserId());
		  		  // System.out.println("user:::::::::::::::::::1"+user+" "+user.getUserId());
		  // System.out.println("user:::::::::::::::::::2"+user1);
		  // System.out.println("user:::::::::::::::::::3"+user2);
		  // System.out.println("user:::::::::::::::::::4"+user1.getUserId()+" "+user1.getEmail());
		  // System.out.println("user:::::::::::::::::::5"+user2.getUserId()+" "+user2.getEmail());
		  
		  // System.out.println("user:::::::::::::::::6"+user3.getStatus());
		   if(userIdExistOrNot==null)
		   {
			   return "Invalid user";
		   }
		   else if(user3.getStatus()!=UserStatus.ACTIVE)
			{
			 
			   return "User not active";
			}
		   else if(user1!=null && (user1.getUserId()!=user.getUserId()) )
			{
				return "email already exist";
			}
			else if(user2!=null && (user2.getPhoneNumber()!=user.getPhoneNumber()) )
			{
				return "phone number already exist";
			}
			else 
			{
				//Validation for username
			String username1=user.getUserName();
			String username=user.getUserName().trim();
			//String username3=user.getUserName().replaceAll("\\s+","");
			int usernameLength=username.length();
			int usernameLength2=username1.length();
			
			   // validation for password
			String passwordvalue=user.getPassword();
			int passwordLength1=passwordvalue.length();
		    passwordvalue=passwordvalue.replaceAll("\\s+","");
		    int passwordLength2=passwordvalue.length();
		  
		    String pattern="(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,32}";
		   
		   
		    String emailPattern="^[a-z0-9][a-z0-9(\\-[a-z0-9]+)(\\_[a-z0-9]+)]*[a-zA-Z0-9]+(\\.[a-z0-9]+)*[a-zA-Z0-9]+(\\-[a-z0-9]+)*[a-zA-Z0-9]+(\\_[a-z0-9]+)*[a-zA-Z0-9]*@[a-z0-9]+(\\.[a-z0-9]+)*(\\.[a-z]{2,})$";
		    if((passwordLength2!=0) && (passwordLength1==passwordLength2) && (passwordvalue.matches(pattern)))
			{
		    	
		    	 if(user.getPhoneNumber().length()==10 && (user.getPhoneNumber().matches("[0-9]+")))
		    	 {
		    		 if((usernameLength!=0 )&& (usernameLength==usernameLength2) && (user.getUserName()!=null) )
		    	   {
		    		 if( (username1.length()<=25))
		    		 {
		    		  if(Pattern.compile(emailPattern).matcher(user.getEmail()).matches())
		    		  {
		    			if(user.getCountry().trim().length()!=0)
		    			{      user.setCreatedOn(user3.getCreatedOn());

		    				  user.setStatus(UserStatus.ACTIVE);
		    				  userrepository.save(user);
		                      return "Your account has been successfully updated." ;
		    				//	return "User not active";
				        }
		    			else
		    			  return "Country can't be null";	
		    		   }
		    		  else
		    			return "invalid email";
		    		 }
		    		 else
		    		 return "Maximum character for username is 25";
		    	   }
		    	   else
		    		return "Username can't be null or cannot contain inappropriate spaces";
			     }
		    	   else
		    		return "Phone number should be of length 10 and should contain numeric only ";
		   }
			else
			{
				return "enter valid password";
			}
		   } 

		
		
		
		
		
		
		
		
		
		
	/*	String username1=user.getUserName();
		String username=user.getUserName().trim();
		//String username3=user.getUserName().replaceAll("\\s+","");
		int usernameLength=username.length();
		int usernameLength2=username1.length();
		
		
			String passwordvalue=user.getPassword();
		int passwordLength1=passwordvalue.length();
	    passwordvalue=passwordvalue.replaceAll("\\s+","");
	    int passwordLength2=passwordvalue.length();
	   // String patternPhone="(?=.*[0-9]).{10,10}";
	    String pattern="(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,32}";
	  
		User user1=userrepository.findByUserId(user.getUserId());
		User userEmail=userrepository.findByEmail(user.getEmail());
		User userPhone=userrepository.findByPhoneNumber(user.getPhoneNumber());
		if((user1==null)&&(user1.getStatus()==UserStatus.INACTIVE))
		{
           return "Invalid user";			
		}else if((userEmail!=null && (userEmail.getUserId()!=user.getUserId()) ) )
		{
			return "Email already exist";
		}
		else if((passwordLength2==0) || !(passwordLength1==passwordLength2) ||
				(passwordvalue.matches(pattern)))
		{
			
		}
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
		*/
	 }
	 
	public String deleteUser(User user)
	{
        
        	userrepository.delete(user);
    	return "user deleted ";	
		
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
		 userorder.setCoinName(userwalletdto.getCoinName());
		 userorder.setNetAmount(userwalletdto.getAmount());
		 userorder.setGrossAmount(userwalletdto.getAmount());
		 userorder.setOrderType(OrderType.DEPOSIT);
		 userorder.setOrderCreatedOn(date);
		 user=userrepository.findByUserId(userwalletdto.getUserId());
		 userorder.setUser(user);
		 //userorder.setId(1);
		 orderRepository.save(userorder);
		 
		 return "Deposit has done. Waiting for approval";
	   }
	
		 
	 }
	 

	
}

