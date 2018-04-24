package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
	 private UserOtpTable userotptable;
	
	 private User user;
	 
	 
	@RequestMapping(value="/signup", method=RequestMethod.POST)
	public String insertUser(@RequestBody User user)
	{
		if(user.getPassword()!=null)
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
		{
			return "enter valid password";
		}
	}
	
	@RequestMapping(value="/verifyuser")
	public void validateUser(@RequestParam("Mail") String email,@RequestParam("otp") String otp)
	{
		
		try {
			userotptable=otpjparepository.findOneByOtp(otp);
			System.out.println("object may be null "+userotptable);
		
		if(userotptable.getEmailId().equals(email)   )
		{
			user = userrepository.findOneByemail(email);
		
			user.setStatus((UserStatus.ACTIVE));
		
			userrepository.save(user);
			otpjparepository.delete(userotptable);
		}
		else
		{
			
		}
       }
       catch(NullPointerException e)
       {
    	   System.out.println("otp not available");
       }
	
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
	
	
	}

  
 


