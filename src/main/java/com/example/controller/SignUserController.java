package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.UserWalletDto;
import com.example.enums.UserStatus;
import com.example.model.User;
import com.example.model.UserOtpTable;
import com.example.repository.OTPjpaRepository;
import com.example.repository.UserRepository;
import com.example.service.SignUpService;

@RestController
public class SignUserController 
{
	@Autowired
	private SignUpService signupservice;
	@Autowired
	private UserRepository userrepository;
	
	@Autowired
	private OTPjpaRepository otpjparepository;
	//@Autowired
	 private UserOtpTable userotptable1;
	
	 private User user;
	 
	 
	@RequestMapping(value="/signup", method=RequestMethod.POST)
	public String insertUser(@RequestBody User user)
	{  User user1=userrepository.findByEmail(user.getEmail());
	   User user2=userrepository.findByPhoneNumber(user.getPhoneNumber());
		if(user1!=null)
		{
			return "email already exist";
		}
		else if(user2!=null)
		{
			return "phone number already exist";
		}
		else 
		{
		String username1=user.getUserName();
		String username=user.getUserName().trim();
		int usernameLength=username.length();
		int usernameLength2=username1.length();
		
		String passwordvalue=user.getPassword();
		int passwordLength1=passwordvalue.length();
	    passwordvalue=passwordvalue.replaceAll("\\s+","");
	    int passwordLength2=passwordvalue.length();
	    
	    String pattern="(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,32}";
	    if((passwordLength2!=0) && (passwordLength1==passwordLength2) && (passwordvalue.matches(pattern)))
		{
	    	 System.out.println("......................111111111122222");
	    	 if(user.getPhoneNumber().length()==10)
	    	 {
	    		 if((usernameLength!=0 )&& (usernameLength==usernameLength2) && (user.getUserName()!=null) )
	    	   {
	    		 if( (username1.length()<=25))
	    		 {
	    			if(user.getCountry()!=null)
	    			{
			         String u=signupservice.addUser(user);
			         if(u != null)
			         {
			    	 return "Your account has been successfully created. Please, verify it by using OTP ";
		             }
			         else
			         return "use different email";
			        }
	    			else
	    			 return "Country can't be null";	
	    		 }
	    		 else
	    		 return "Maximum character for username is 25";
	    	   }
	    	   else
	    		return "Username can't be null or cannot contain inappropriate spaces";
		     }
	    	   else
	    		return "Phone number should be of length 10";
	   }
		else
		{
			return "enter valid password";
		}
	   } 
	}
	
	@RequestMapping(value="/verifyuser",method=RequestMethod.POST)
	public String validateUser(@RequestBody UserOtpTable userotptable)
	{
		
		
		user=userrepository.findByEmail(userotptable.getEmail());
		
		if(user!=null)
		{
			userotptable1=otpjparepository.findByEmail(user.getEmail());
			if(userotptable1.getOtp().equals(userotptable.getOtp()))
			{
				user.setStatus((UserStatus.ACTIVE));
				
				userrepository.save(user);
				otpjparepository.delete(userotptable);
				return "Your account is verified successfully";
			}
			else
				return "Invalid OTP";
		}
		else
			return "email not matched";
		
	
	}
	@GetMapping("/getallusers")
	public List<User> getAllNotes() 
	{
	    return userrepository.findAll();
	}
	
	@GetMapping("/getbyuserid")
	public User getbyuserid(@RequestParam("userId") Integer id) 
	{
	    return userrepository.findByUserId(id);
	}
	
	@RequestMapping(value="/updateuser",method=RequestMethod.POST)
	public String updateuser(@RequestBody User user) 
	{
	    String s=signupservice.updateuser(user);
	    if(s!=null)
	    {
	    	return "updated";
	    }
	    else
	    	return "null";
	}
	
	@GetMapping("/deleteuser")
	public String deleteUser(@RequestParam("userId") Integer id) 
	{
		user=userrepository.findByUserId(id);
		System.out.println("here......user........has......"+user);
		signupservice.deleteUser(user);
	    return "account deleted";
	}
	
	@RequestMapping(value="/depositamount",method=RequestMethod.POST)
	public String depositamount(@RequestBody UserWalletDto userwalletdto )
	{
		
		return signupservice.depositamount(userwalletdto);
	}
	
	
	}

  
 


