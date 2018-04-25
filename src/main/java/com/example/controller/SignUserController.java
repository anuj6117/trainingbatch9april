package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	{  
		String passwordvalue=user.getPassword().trim();
		String username=user.getUserName().trim();
	    int passwordLength=passwordvalue.length();
	    int usernameLength=username.length();
	    if(passwordLength!=0)
		{
	    	if(usernameLength!=0)
	    	{
			 String u=signupservice.addUser(user);
			   if(u != null)
			   {
				return "success";
		       }
			   else
			   {
				return "use different email";
			   }
	    	}
	    	else
	    		return "username can't be null";
		}
		else
		{
			return "enter valid password";
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
				return "otp and email matched";
			}
			else
				return "otp not matched";
		}
		else
			return "email not matched";
		
	
	}
	@GetMapping("/getallusers")
	public List<User> getAllNotes() 
	{
	    return userrepository.findAll();
	}
	
	@GetMapping("/getbyuserid/{id}")
	public User getbyuserid(@PathVariable("id") Integer id) 
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
	
	@GetMapping("/deleteuser/{id}")
	public String deleteUser(@PathVariable("id") Integer id) 
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

  
 


