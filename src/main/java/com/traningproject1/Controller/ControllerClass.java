package com.traningproject1.Controller;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.traningproject1.demo.dto.ClassDTO;
import com.traningproject1.demo.dto.DepositAmountDTO;
import com.traningproject1.demo.dto.VerifyUserDTO;
import com.traningproject1.domain.User;
import com.traningproject1.enumsclass.UserStatus;
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
	private static final String EMAIL_PATTERN = 
			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	     
@RequestMapping(value="/signup",method=RequestMethod.POST)
public String addUser(@RequestBody User user)
{   
	String usertrim=user.getUserName().trim();
	if(usertrim.length()!=user.getUserName().length())
	{
		return"Please Enter user name without using Leading and Trailing Space";
	}
       if(user.getUserName().equals(" "))
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
       if(user.getCountry().length()==0)
       {
    	   return"Please enter the country Name";
       }
       String country1=user.getCountry().trim();
       
       if(country1.length()!=user.getCountry().length())
       {
    	   return "Invalid input of Country Name";
       }
       
       if(user.getPassword().length()<8||user.getPassword().length()>32)
       {
    	   return "Password Cannot be less Than 8 or Greater than 32";
       }
       
    
          String phone1=user.getPhoneNumber();
          Pattern pattern=Pattern.compile("(0/91)?[6-9][0-9]{9}");
          Matcher matcher = pattern.matcher(phone1);
          if(!(matcher.matches()))
          {
        	  return "Phone Number is not Valid";
          }
            
       if(userRepository.findByphoneNumber(user.getPhoneNumber())!=null)
       {
    	   return "Oops Phone number already registration ";
       }
       if(userRepository.findByEmail(user.getEmail())!=null)
       {
    	   return "This email id already registered ";
       }
       String email1=user.getEmail();
       Pattern pattern1 = Pattern.compile(EMAIL_PATTERN);
       Matcher matcher1 = pattern1.matcher(email1);
       if(!(matcher1.matches()))
       {
    	   return "Email Should be in Correct Format";
       }
	  User userCreated=serviceClass.addUser(user);
	  if(userCreated!=null)
		 return"Your Account has been successfull created.Please verify it by using OTP";
	
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
	User user=userRepository.findByuserId(classDTO.getUserId());
	if(user.getStatus().equals(UserStatus.INACTIVE))
	{
	 return "User is Not verified";
	}
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
	System.out.println(verifyuserdto.getTokenOTP()+"\t"+verifyuserdto.getEmailId());
	return serviceClass.verifyOTP(verifyuserdto.getTokenOTP(),verifyuserdto.getEmailId());

}
//@RequestMapping(value="/withdrawamount",method=RequestMethod.POST)
//public String withdrawAmount(@RequestBody WithdrawAmountDTO withdrawamountdto)
//{ 
//  serviceClass.withdrawAmount(withdrawamountdto);
//  return "Success";	
//}

}
