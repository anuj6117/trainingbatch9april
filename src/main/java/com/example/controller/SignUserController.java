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
import com.example.repository.jpaRepository;
import com.example.service.SignUpService;

@RestController
public class SignUserController 
{
	@Autowired
	private SignUpService signupservice;
	@Autowired
	private jpaRepository jRepository;
	
	@Autowired
	private OTPjpaRepository otpjparepository;
	//@Autowired
	 private UserOtpTable userotptable;
	
	 private User user;
	 
	 
	@RequestMapping(value="/signup", method=RequestMethod.POST)
	public String insertUser(@RequestBody User user)
	{
		String u=signupservice.addUser(user);
		if(u != null)
		{
			return "success";
		}
		else
		{
			return "failure";
		}
	}
	
	@RequestMapping(value="/validate")
	public void validateUser(@RequestParam("Mail") String email,@RequestParam("otp") String otp)
	{
		
		try {
			userotptable=otpjparepository.findOneByOtp(otp);
			System.out.println("object may be null "+userotptable);
		
		if(userotptable.getEmailId().equals(email)   )
		{
			user = jRepository.findOneByemail(email);
		
			user.setStatus((UserStatus.ACTIVE));
		
			jRepository.save(user);
			otpjparepository.delete(userotptable);
		}
		else
		{}
       }
       catch(NullPointerException e)
       {
    	   System.out.println("otp not available");
       }
	
	}
	@GetMapping("/getallusers")
	public List<User> getAllNotes() 
	{
	    return jRepository.findAll();
	}
	
	@GetMapping("/getbyuserid/{id}")
	public User getbyuserid(@PathVariable("id") Integer id) 
	{
	    return jRepository.findByUserId(id);
	}
	
	@PostMapping("/updateuser/{id}")
	public String updateuser(@PathVariable("id") Integer id) 
	{
	    String s=signupservice.updateuser(id);
	    if(s!=null)
	    {
	    	return "updated";
	    }
	    else
	    	return "null s object";
	}
	
	
	}

  
 


