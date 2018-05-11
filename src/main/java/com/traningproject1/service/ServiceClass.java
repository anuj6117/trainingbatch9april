package com.traningproject1.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.traningproject1.demo.dto.ClassDTO;
import com.traningproject1.demo.dto.DepositAmountDTO;
import com.traningproject1.demo.dto.VerifyUserDTO;
import com.traningproject1.demo.dto.WithdrawAmountDTO;
import com.traningproject1.domain.Role;
import com.traningproject1.domain.User;
import com.traningproject1.domain.UserOTP;
import com.traningproject1.domain.UserOrder;
import com.traningproject1.domain.Wallet;
import com.traningproject1.enumsclass.CoinType;
import com.traningproject1.enumsclass.UserOrderStatus;
import com.traningproject1.enumsclass.UserOrderType;
import com.traningproject1.enumsclass.UserStatus;
import com.traningproject1.repository.RoleRepository;
import com.traningproject1.repository.UserOTPRepository;
import com.traningproject1.repository.UserOrderRepository;
import com.traningproject1.repository.UserRepository;
import com.traningproject1.repository.WalletRepository;
import com.traningproject1.utils.MailService;
import com.traningproject1.utils.SmsService;
@Service
public class ServiceClass {


@Autowired
 private UserRepository userRepository;

@Autowired
private RoleRepository roleRepository;

@Autowired
private WalletRepository walletRepository;

@Autowired
private UserOrderRepository userOrderRepository;
Integer otp;

@Autowired
SmsService smsService;

@Autowired
MailService mailService;

@Autowired
UserOTPRepository userOTPRepository;

UserOTP userOTP;
   public User addUser(User user)
	   {
	
	      user.setCreatedOn(new Date());
          List<Role>roleType=new ArrayList<>();
          Role role=new Role();
          Role roleObject1=new Role();
          
          role=roleRepository.findByRoleType("user");
	      if(role!=null)
	      {
	    	  roleType.add(role);
	    	  
	      }
	      else
	      {
	    	  roleObject1.setRoleType("user");
	    	  roleType.add(roleObject1);
	    	  roleRepository.save(roleObject1);
	      } 
	   
	      user.setRole(roleType);
	   Random random=new Random();
	   otp=random.nextInt(988888);
	   otp += 1000;

		userRepository.save(user);
		
		
		Set<Wallet>walletset=new HashSet<>();
		Wallet wallet=new Wallet();
		
		
		wallet.setCoinType(CoinType.FIAT);
		
		wallet.setUser(user);
		
		walletset.add(wallet);
		
		walletRepository.save(wallet);
	    user.setWallet(walletset);
	    user.setStatus(UserStatus.INACTIVE);
		userRepository.save(user);
		
		 //smsService.sendSms(otp);
		
	
		 //mailService.sendMail(otp,user.getEmail());
		 userOTP=new UserOTP(); 
		 userOTP.setUserOTPId(user.getUserId());
		 userOTP.setTokenOTP(otp);
		 userOTP.setEmailId(user.getEmail());
		 userOTP.setDate(new Date());

		userOTPRepository.save(userOTP);
		return user;

	}
public ArrayList<User> getAllUser()
{
	ArrayList<User> list=new ArrayList<>();
	userRepository.findAll()
	.forEach(list::add);
	return list;
}
public Optional<User> getByUserId(Integer userId)
{
  Optional<User> userGet=userRepository.findById(userId);
  return userGet;
}
public void deleteUser(Integer userId)
{
	userRepository.deleteById(userId);
}
public String updateUserData(User user) {
	User tempuser=userRepository.findByuserId(user.getUserId());
	if(tempuser!=null)
	{
	 tempuser.setEmail(user.getEmail());
	 tempuser.setUserName(user.getUserName());
	 tempuser.setPassword(user.getPassword());
	 tempuser.setPhoneNumber(user.getPhoneNumber());
	 tempuser.setCountry(user.getCountry());
	 userRepository.save(tempuser);
	 return "User Has been updated Successfully";
	}
	else
		return "Invalid UserId";
}
/*public void assignRole(Integer userId,Role roleType)
{
   User user=userRepository.findById(userId).get();
  // System.out.println("id"+" ###############################"+id);
   //System.out.println("id"+" ##################################"+id);
   List<Role>list=new ArrayList();
   
  list.add(roleType);
  //roleService.addRole(list);
   user.setRole(list);
   userRepository.save(user);
}*/
public User assignRoleToUser(ClassDTO classDTO)
{
	User user=userRepository.findByuserId(classDTO.getUserId());
	
	Role role=roleRepository.findByroleType(classDTO.getRoleType());
	if(user!=null)
	{
		if(role!=null)
		{
			user.getRole().add(role);
			User tempuser=userRepository.save(user);
			return tempuser;
		}
		else
		{
			throw new NullPointerException("User Role doesn't not exist");
		}
	}
	else
	{
		throw new NullPointerException(" "+"User id doesn't not exist");
		}
}
  public String withdrawAmount(WithdrawAmountDTO withdrawamountdto)
  {
	  User user=userRepository.findByuserId(withdrawamountdto.getUserId());
	  Wallet wallet=walletRepository.findByCoinType(withdrawamountdto.getCoinType());
	  
	  double amount=withdrawamountdto.getAmount();
	  
	  wallet.setBalance(wallet.getBalance()-amount);
	  wallet.setShadowBalance(wallet.getBalance());
	  walletRepository.save(wallet);
	  userRepository.save(user);
	  
	  return "success";
  }
  public String depositAmount(DepositAmountDTO depositamountdto)
  {
	  User user=userRepository.findByuserId(depositamountdto.getUserId());
	 // Wallet wallet=walletRepository.findByCoinType(depositamountdto.getCoinType());
	  UserOrder userorder=new UserOrder();
	  //userorder.setUserId(user.getUserId());
	  
	  userorder.setUser(user);
	  userorder.setCoinQuantity(depositamountdto.getAmount());
	  userorder.setGrossAmount(depositamountdto.getAmount());
	  userorder.setStatus(UserOrderStatus.PENDING);
	  userorder.setOrderType(UserOrderType.DEPOSIT);
	  userorder.setCoinName(depositamountdto.getCoinName());
	  userorder.setDateCreated(new Date());
	  
	  userOrderRepository.save(userorder);  
    // long balance=depositamountdto.getAmount();
	 
    // wallet.setBalance(balance+wallet.getBalance());
    // wallet.setShadowBalance(wallet.getBalance());
	  
    // walletRepository.save(wallet);
	  
	// userRepository.save(user);
	 
	  return "Your Order Has Been  placed successfully Wait for Approval";
  }
  public String verifyOTP(VerifyUserDTO verifyuserdto )
  {
	  
	  String otp=verifyuserdto.getTokenOTP().toString();
	 
	  
	  if(verifyuserdto.getTokenOTP()==null||otp.length()<4)
	  {
		  return "Invalid OTP";
		  
	  }
	  try
	  {
		  UserOTP userotp=userOTPRepository.findByEmailId(verifyuserdto.getEmailId());
		  
		  UserOTP user1=userOTPRepository.findByTokenOTP(verifyuserdto.getTokenOTP());
		  
		  User user=userRepository.findByEmail(verifyuserdto.getEmailId());
	  
		  if(verifyuserdto.getEmailId().equals(userotp.getEmailId()))
	  {
		  if(verifyuserdto.getTokenOTP().equals(userotp.getTokenOTP()))
	     {  
		    user.setStatus(UserStatus.ACTIVE);
		   UserOTP userdelete=userOTPRepository.findBytokenOTP(userotp.getTokenOTP());
		   userOTPRepository.delete(userdelete);
		   userRepository.save(user);
		    return "Your account verified Successfully";
	     }
		  else
		  {
		   return "invalid OTP";
	      }
	  }
	  else
	  {	
		  return "Invalid  Email";
	  }
	  }
	  catch(Exception e)
	  {
		  return "Invalid Email";
	  }
   }
}