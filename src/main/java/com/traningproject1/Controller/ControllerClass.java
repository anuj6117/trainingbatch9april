package com.traningproject1.Controller;

import java.util.Iterator;
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
import com.traningproject1.domain.Role;
import com.traningproject1.domain.User;
import com.traningproject1.enumsclass.UserStatus;
import com.traningproject1.repository.RoleRepository;
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
	@Autowired
	RoleRepository roleRepository;
	
   Optional<User>user=null;
	private static Pattern pswNamePtrn = 
	        Pattern.compile("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-<>~`+-]).{8,32})");
	private static final String EMAIL_PATTERN = "^[A-Za-z0-9.\\+]+(\\.[-A-Za-z0-9_.]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	     
@RequestMapping(value="/signup",method=RequestMethod.POST)
public String addUser(@RequestBody User user)
{   
	if(!(user.getUserName().matches("^[A-Za-z0-9_-]{1,25}$"))){
		return "User Name not valid";
	}
	

       String pass=user.getPassword();
       java.util.regex.Matcher mtch = pswNamePtrn.matcher(pass);
       if(!mtch.matches())
       {
    	   return "Password Must Contains one Uppercase Alphabet,one lower case ,on dgigt, one special symbol";
       }
       if(!(user.getPassword().matches("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-<>~`)(_+=}[{]':;/]).{8,}$")))
		{
			return "Please enter password with minimum 8 characters. Your password should have atleast 1 Uppercase, 1 Lowercase, 1 Digit & 1 Special character. Space is not allowed";
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
       if(country1.length()<2)
       {
    	   return "Invalid country Name";
       }
       
       if(user.getPassword().length()<8||user.getPassword().length()>32)
       {
    	   return "Password Cannot be less Than 8 or Greater than 32";
       }
       
    
          String phone1=user.getPhoneNumber();
          Pattern pattern=Pattern.compile("(0/91)?[0-9][0-9]{9}");
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
public Object getUserById( Integer userId)
{
	try
	{
		User user=userRepository.findByUserId(userId);
		if(user==null)
		{
	    	throw new Exception("Invalid User");
		}
	}
	catch(Exception e)
	{
		return "Invalid User";
	}	
	
 return  serviceClass.getByUserId(userId);
}


@RequestMapping(value="/deleteuser",method=RequestMethod.GET)
public String deleteUser(Integer userId)
{
	List<User> userlist=userRepository.findAll();
	Iterator<User>itr=userlist.iterator();
   while(itr.hasNext())
   {
	   if(itr.next().getUserId()==userId)
	   {
		   serviceClass.deleteUser(userId);
		   return "User is Successfully Deleted";
		   
	   }
   }
   return "Invalid User id";
}


@RequestMapping(value="/updateuser",method=RequestMethod.POST)
public  String updateUserData(@RequestBody User user)
{
	 User cc;
	if((cc = userRepository.findByEmail(user.getEmail()))!=null)
	{
		if(cc.getUserId() != user.getUserId())
		return "already existing Email Id";
	}
	
	if((cc = userRepository.findByPhoneNumber(user.getPhoneNumber()))!=null)
	if(cc.getUserId() != user.getUserId())
	{
		return "already existing phone Number";
	}
	if(!(user.getUserName().matches("^([a-zA-Z0-9]{2,}\\s[a-zA-z0-9]{1,}'?-?[a-zA-Z0-9]{2,}\\s?([a-zA-Z0-9]{1,})?)"))){
		return "User Name not valid";
	}
	
       String pass=user.getPassword();
       java.util.regex.Matcher mtch = pswNamePtrn.matcher(pass);
       if(!mtch.matches())
       {
    	   return "Password Must Contains one Uppercase Alphabet,one lower case ,on dgigt, one special symbol";
       }
       if(!(user.getPassword().matches("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-<>~`)(_+=}[{]':;/]).{8,}$")))
		{
			return "Please enter password with minimum 8 characters. Your password should have atleast 1 Uppercase, 1 Lowercase, 1 Digit & 1 Special character. Space is not allowed";
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
       if(country1.length()<2)
       {
    	   return "Invalid country Name";
       }
       
       if(user.getPassword().length()<8||user.getPassword().length()>32)
       {
    	   return "Password Cannot be less Than 8 or Greater than 32";
       }
       
    
          String phone1=user.getPhoneNumber();
          Pattern pattern=Pattern.compile("(0/91)?[0-9][0-9]{9}");
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
	serviceClass.updateUserData(user);
 return  "User has been updated successfully";
		 	
}

@RequestMapping(value="/assignrole",method=RequestMethod.POST)
public String assignRoleToUser(@RequestBody ClassDTO classDTO)
{
	User user=userRepository.findByuserId(classDTO.getUserId());
	
	if(user==null)
	{
		return "Invalid User id";
	}
	if(user.getStatus().equals(UserStatus.INACTIVE))
	{
	 return "User is Not verified";
	}
	try {
	List<Role>roles=user.getRole();
	Iterator<Role>itrrole=roles.iterator();
	while(itrrole.hasNext())
	{
		String roless=itrrole.next().getRoleType();
		System.out.println(roless+"1111111111111111111111111111111111111111111111");
		if(roless.equalsIgnoreCase(classDTO.getRoleType()))
		{
			return "Role Already assigned";
		}
	}
	  serviceClass.assignRoleToUser(classDTO);
	  //roleRepository.findByUserAndRole(classDTO.getRoleType());
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
	User user=userRepository.findByUserId(depositamountdto.getUserId());
	if(user==null)
	{
		return "Invalid User Id";
	}
 if(depositamountdto.getAmount()==0)
 {
	 return "Please Enter Amount";
 }
 if(depositamountdto.getAmount()<0)
 {
	 return "Amount can't be less than zero";
 }
  serviceClass.depositAmount(depositamountdto);
  return "Your Order Has Been  placed successfully Wait for Approval";	
}
@RequestMapping(value="/verifyuser",method=RequestMethod.POST)
public String verifyOTP(@RequestBody VerifyUserDTO verifyuserdto)
{
	System.out.println(verifyuserdto.getTokenOTP()+"\t"+verifyuserdto.getEmailId());
	return serviceClass.verifyOTP(verifyuserdto);

}
}
