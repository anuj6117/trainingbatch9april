package com.traningproject1.Controller;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.traningproject1.demo.dto.ClassDTO;
import com.traningproject1.demo.dto.DepositAmountDTO;
import com.traningproject1.demo.dto.VerifyUserDTO;
import com.traningproject1.domain.User;
import com.traningproject1.repository.UserRepository;
import com.traningproject1.service.ServiceClass;
import com.traningproject1.service.UserOTPService;
import com.traningproject1.service.WalletService;

@RestController
public class ControllerClass {
	@Autowired
	ServiceClass serviceClass;
	
	@Autowired
	WalletService walletService;
	@Autowired
	UserOTPService userOTPService;
	@Autowired
	UserRepository userRepository;
	

	private static Pattern pswNamePtrn = 
	        Pattern.compile("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,32})");
	     
@RequestMapping(value="/signup",method=RequestMethod.POST)
public String addUser(@RequestBody User user)
{   
	String usertrim=user.getUserName().trim();
	user.setUserName(usertrim);
	
	
	
       if(user.getUserName().equalsIgnoreCase(" "))
       {
    	   return "Name cannot Be Null";
       }
       String pass=user.getPassword();
       java.util.regex.Matcher mtch = pswNamePtrn.matcher(pass);
       if(!mtch.matches())
       {
    	   return "Password Must Contains one Uppercase Alphabet,one lower case ,on dgigt, one special symbol";
       }
       if(user.getUserName().length()>25)
       {
    	   return "Name cannot be greater than 25";
       }
       if(user.getPassword().length()<8||user.getPassword().length()>32)
       {
    	   return "Password Cannot be less Than 8 or Greater than 32";
       }
       if(userRepository.findByphoneNumber(user.getPhoneNumber())!=null)
       {
    	   return "Oops Phone number already registration ";
       }
       if(userRepository.findByemail(user.getEmail())!=null)
       {
    	   return "This email id already registered ";
       }
     
       if(!(EmailValidator.getInstance().isValid(user.getEmail())))
       {
    	   return "Email Should be in Correct Format";
       }
	  User userCreated=serviceClass.addUser(user);
	  if(userCreated!=null)
		 return"success";
	
	 else
		return "fail";
}


@RequestMapping(value="/getallusers",method=RequestMethod.GET)
public List<User> getAllUser()
{
	return serviceClass.getAllUser();
	
}


@RequestMapping(value="/getbyuserid",method=RequestMethod.GET)
public Optional<User> getUserById( Integer userId)
{
 return serviceClass.getByUserId(userId);	
}


@RequestMapping(value="/deleteuser",method=RequestMethod.GET)
public String deleteUser(Integer userId)
{
  serviceClass.deleteUser(userId);
  return "Success";
}


@RequestMapping(value="/updateuser",method=RequestMethod.POST)
public  String updateUserData(@RequestBody User user)
{
	serviceClass.updateUserData(user);
 return  "Success";
		 	
}


@RequestMapping(value="/assignrole",method=RequestMethod.POST)
public String assignRoleToUser(@RequestBody ClassDTO classDTO)
{
  try
  {
	  serviceClass.assignRoleToUser(classDTO);
  }
  catch(Exception e)
  {
	  return "Role Cannot Be Assigned"+e.getMessage();
  }
  return "Role Assign Succesfully";
}

@RequestMapping(value="/depositamount",method=RequestMethod.POST)
public String depositAmount(@RequestBody DepositAmountDTO depositamountdto)
{
 if(depositamountdto.getAmount()==0)
 {
	 return "Please Enter Amount";
 }
 if(depositamountdto.getAmount()<0)
 {
	 return "Amount can't be Zero or less than zero";
 }
  serviceClass.depositAmount(depositamountdto);
  return "Success";	
}
@RequestMapping(value="/verifyuser",method=RequestMethod.POST)
public String verifyOTP(@RequestBody VerifyUserDTO verifyuserdto)
{
	return serviceClass.verifyOTP(verifyuserdto);
}
//@RequestMapping(value="/withdrawamount",method=RequestMethod.POST)
//public String withdrawAmount(@RequestBody WithdrawAmountDTO withdrawamountdto)
//{ 
//  serviceClass.withdrawAmount(withdrawamountdto);
//  return "Success";	
//}
}
