package com.trainingproject.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trainingproject.domain.Role;
import com.trainingproject.domain.SignUpOTP;
import com.trainingproject.domain.User;
import com.trainingproject.domain.UserOrder;
import com.trainingproject.domain.Wallet;
import com.trainingproject.dto.AssignRoleBean;
import com.trainingproject.dto.AssignWalletBean;
import com.trainingproject.dto.WithdrawDepositBean;
import com.trainingproject.enums.CoinType;
import com.trainingproject.enums.OrderType;
import com.trainingproject.enums.UserOrderStatus;
import com.trainingproject.enums.UserStatus;
import com.trainingproject.repository.RoleRepository;
import com.trainingproject.repository.SignUpOTPRepository;
import com.trainingproject.repository.UserOrderRepository;
import com.trainingproject.repository.UserRepository;
import com.trainingproject.repository.WalletRepository;
import com.trainingproject.util.SmsOTP;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	WalletRepository walletRepository;
	
	@Autowired
	UserOrderRepository userorderRepository;
	@Autowired
	SignUpOTPRepository signupOTPRepository;
	
	@Autowired
	SmsOTP smsOTP;
	@Autowired
	RoleRepository roleService;
	Integer otp;
	
	public String createUser(User user) {
		
		Pattern p = Pattern.compile("^[a-zA-Z0-9._-]{3,}$", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(user.getUserName());
		
		 if(!m.find())
			 return "invalid username";
		if(user.getUserName().charAt(0)==' ')
			return "your name canot have spaces";
		
		if(user.getUserName().charAt(0)==' ')
			return "your name canot have spaces";
		
	     if(user.getUserName().charAt(user.getUserName().length()-1)==' ')
	    	 return "your name cannot have space";
	     
	     p = Pattern.compile("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#/|()~`!$%^&+=])(?=\\S+$).{8,}$");
		 m = p.matcher(user.getPassword());
    
		 if(!m.find())
	    	 return "your password should contain special characters and no spaces with one upper case character";
		 
         
		if(user.getUserName().length()==0)
			return "name cannot be empty";
		else if(user.getUserName().length()>25)
			return "Maximum characters allowed for user name field is 25";
		
		if(userRepository.findByEmail(user.getEmail())!=null)
			return "oops this email id is already registered";
		
		if(userRepository.findByphoneNumber(user.getPhoneNumber())!=null)
			return "oops this phone number is already registered";
		
		if(user.getPhoneNumber().toString().length()!=10)
			return "please enter a valid phone number";
		
		if(user.getPassword().length()<8)
			return "please enter password with minimum 8 characters";
		
		if(user.getPassword().length()>32)
			return "Maximum characters allowed for password field is 32";
		
		if(user.getCountry()==null||user.getCountry().length()==0)
			return "county cannot be null";
		
		p = Pattern.compile("^[a-zA-Z]{2,}$", Pattern.CASE_INSENSITIVE);
		m = p.matcher(user.getCountry());
		  if(!m.matches())
		    return "Invalid country name";
		
		  SimpleDateFormat sdf = new SimpleDateFormat("M/d/yy h:mm a Z");
		  TimeZone istTimeZone = TimeZone.getTimeZone("Asia/Kolkata");
		  Date d = new Date();
		  sdf.setTimeZone(istTimeZone);
		  String strtime = sdf.format(d);
		
		
		 user.setCreatedOn(strtime);
		user.setStatus(UserStatus.INACTIVE);
		
		String regex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";

	      Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
	      Matcher matcher = pattern.matcher(user.getEmail());
	      
	      if(matcher.matches())
	      {
	      
	    	  userRepository.save(user);
		
		List<Wallet> walletSet=new ArrayList<Wallet>();
		Wallet wallet=new Wallet();
		wallet.setCoinType(CoinType.FIAT);
		wallet.setCoinName("INR");
		wallet.setUser(user);
		walletSet.add(wallet);
		walletRepository.save(wallet);
		user.setUserWallet(walletSet);
		
		List<Role> roleList=new ArrayList<Role>();
		Role role=roleRepository.findByRoleType("user");
		if(role==null) {
			role=new Role();
			role.setRoleType("USER");
			roleRepository.save(role);
		}
		roleList.add(role);    
		
		user.setRoleType(roleList);
		
		otp= smsOTP.sendSMS();
		smsOTP.sendMail(user.getEmail());
	  SignUpOTP otpobj=new SignUpOTP();
	  otpobj.setDate(new Date());
	  otpobj.setEmail(user.getEmail());
	  otpobj.setTokenOTP(otp);
	 
	  signupOTPRepository.save(otpobj);
	 
	  userRepository.save(user);
			
	 return "success";
	 
	}
	
	
	else {
		return "Invalid email";
	   }
	      
	}
	
	public List<User> getAllUsers() {
		
     return userRepository.findAll();
	
	}
	
//	public GetUserById getUserById(Integer userId) {
//
//		Optional<User> opt= userRepository.findById(userId);
//		GetUserById bean=new GetUserById();
//		if(opt.isPresent()) {
//			bean.setOpt(opt);		
//		}
//		else bean.setMessage("User do not exist");
//		return bean;
//		
//	}
//	
	public Optional<User> getUserById(Integer userId) {

		return userRepository.findById(userId);	
		
	}


	public String update(User user) {
	
		if(user.getUserId()==null)
			return "user id cannot be null";
		if(user.getUserName()==null)
			return "username cannot be null";
		if(user.getEmail()==null)
			return "email cannot be null";
		if(user.getPassword()==null)
			return "password cannot be null";
		if(user.getPhoneNumber()==null)
			return "phone number cannot be null";
		
		if(!userRepository.existsById(user.getUserId()))
			return "this user do not exist";
		
		User cuser=userRepository.findById(user.getUserId()).get();
		
		if(cuser.getStatus()==null)
			return "user is inactive";
		
		if(cuser.getStatus().equals(UserStatus.INACTIVE))
			return "user is inactive";
		
		Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(user.getUserName());
	     if(m.find())
	    	 return "your name cannot have a special character";
	     
	     m = p.matcher(user.getPassword());
	     if(!m.find())
	    	 return "your password should have a special character";
	     
	 	if(user.getCountry()==null||user.getCountry().length()==0)
			return "county cannot be null";
	 	
		if(user.getUserName().length()==0)
			return "name cannot be empty";
		else if(user.getUserName().length()>25)
			return "Maximum characters allowed for this field is 25";
		
		if(userRepository.findByEmail(user.getEmail())!=null)
			return "oops this email id is already registered";
		
		if(userRepository.findByphoneNumber(user.getPhoneNumber())!=null)
			return "oops this phone number is already registered";
		
		if(user.getPhoneNumber().toString().length()!=10)
			return "please enter a valid phone number";
		
		if(user.getPassword().length()<8)
			return "please enter password with minimum 8 characters";
		
		if(user.getPassword().length()>32)
			return "Maximum characters allowed for this field is 32";
		//user.setCreatedOn(new Date());
		  cuser.setUserName(user.getUserName());
		  cuser.setEmail(user.getEmail());
		  cuser.setPhoneNumber(user.getPhoneNumber());
		  cuser.setPassword(user.getPassword());
		 userRepository.save(cuser);
		 return "success";
	}

	public String deleteUser(Integer userId)
	{
		try {
		if(getUserById(userId).get()==null)
			return "user does not exist ";
		}
		catch(Exception e) {
			return "user does not exist ";
		}
		userRepository.deleteById(userId);
		return "user deleted";
	}


	public String assignRole(AssignRoleBean arb) {
		
		User user=getUserById(arb.getUserId()).get();
		
		 if(user.getStatus()==null)
				return "user is inactive";
		 
	      if(user.getStatus().equals(UserStatus.INACTIVE))
		return "user is inactive";
	      
	   List<Role> roles=user.getRoleType();
	   
	   for(int i=0;i<roles.size();i++) {
		   if(roles.get(i).getRoleType().equalsIgnoreCase(arb.getRoleType()))
                     return "role type already assigned";
	   }
	     
	      if(arb.getRoleType().equalsIgnoreCase("ADMIN")||arb.getRoleType().equalsIgnoreCase("MANAGER"))
	      {
	    	 

		Role roleobj=roleRepository.findByRoleType(arb.getRoleType());
		if(roleobj==null)
			return "role cannot be assigned";
		List<Role> role=new ArrayList<Role>();
		
		role=user.getRoleType();
		role.add(roleobj);
		
		user.setRoleType(role);
		userRepository.save(user);
		return "success";
	      }
	      else return "role cannot be assigned";
		//System.out.println(rolelist.get(0).getRoleType()+"kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk");
	
		
	}


	public String addWallet(AssignWalletBean awb) {

		
		User user=getUserById(awb.getUserId()).get();
		
		if(user==null)
			return "no user present with thid id";
		if(user.getStatus().equals(UserStatus.INACTIVE))
			return "user is inactive";
		if(awb.getCoinName()==null)
			return "coin name cannot be null";
		
		if(awb.getCoinType()==null)
			return "coin type cannot be null";
		
		if(awb.getCoinName().length()==0)
			return "coin name cannot be empty";
		
		
		Wallet cwallet=new Wallet();
		cwallet.setCoinType(awb.getCoinType());
		cwallet.setCoinName(awb.getCoinName());
		cwallet.setUser(user);
		walletRepository.save(cwallet);
		
		 List<Wallet> walletSet=new ArrayList<Wallet>();
		 walletSet.add(cwallet);
		user.setUserWallet(walletSet);
		
		userRepository.save(user);
		
		
		return "success";
		
		
	}


	public String withdrawAmount(WithdrawDepositBean wb) {
     
		User user=getUserById(wb.getUserId()).get();
		
		if(user.getStatus().equals(UserStatus.INACTIVE))
			return "user is inactive";
		
        UserOrder userorder=new UserOrder();
		
		userorder.setDate(new Date());
		userorder.setOrderType(OrderType.WITHDRAW);
		userorder.setOrderStatus(UserOrderStatus.PENDING);
		userorder.setUser(user);
		userorder.setUserId(user.getUserId());
		userorder.setPrice(wb.getAmount());
		userorder.setGrossAmount(wb.getAmount());
		userorderRepository.save(userorder);
		
		return "success";
		
	}



	public String depositAmount(WithdrawDepositBean wdb) {
	   
		
		Pattern p = Pattern.compile("^[a-zA-Z]{1,}$", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(wdb.getCoinName());
		
		 if(!m.find())
			 return "invalid coin name";
		 
	    if(wdb.getAmount()==0||wdb.getAmount()<0)
	    	return "please enter a valid amount to deposit";
	    
	    
		User user =getUserById(wdb.getUserId()).get();
		
		
		if(user.getStatus().equals(UserStatus.INACTIVE))
			return "user is inactive";
		
		UserOrder userorder=new UserOrder();
		
		userorder.setDate(new Date());
		userorder.setOrderType(OrderType.DEPOSIT);
		userorder.setOrderStatus(UserOrderStatus.PENDING);
		userorder.setUser(user);
		userorder.setUserId(user.getUserId());
		userorder.setPrice(wdb.getAmount());
		userorder.setGrossAmount(wdb.getAmount());
		userorder.setCoinQuantity(wdb.getAmount());
		userorder.setCoinType(wdb.getCoinType());
		userorder.setCoinName(wdb.getCoinName());
		userorderRepository.save(userorder);
		
		return "your order has been placed successfully.wait for approval";
		
	}



//	public String createBuyOrder(BuySellBean bsb) {
//		User user =getUserById(bsb.getUserId()).get();
//		
//		if(user==null)
//			return "user is null";
//		
//		if(user.getStatus()==null)
//			return "user is inactive";
//		if(user.getStatus().equals(UserStatus.INACTIVE))
//			return "user is inactive";
//		if(bsb.getUserId()==null)
//      return "userId cannot be null";
//		
//		if(bsb.getCoinName()==null||bsb.getCoinName().length()==0)
//		      return "coin name cannot be null";
//		
//		if(bsb.getCoinQuantity()==null||bsb.getCoinQuantity()==0)
//		      return "coin quantity cannot be null";
//		
//		if(bsb.getPrice()==null||bsb.getPrice()==0)
//		      return "price cannot be null";
//		
//		UserOrder userorder=new UserOrder();
//		userorder.setCoinName(bsb.getCoinName());
//		userorder.setCoinQuantity(bsb.getCoinQuantity());
//		userorder.setDate(new Date());
//		userorder.setOrderType(OrderType.BUY);
//		userorder.setUser(user);
//		userorder.setPrice(bsb.getPrice());
//		userorder.setUserId(user.getUserId());
//		userorderRepository.save(userorder);
//		
//		return "your order has been placed successfully! wait for approval";
//	}
//
//
//
//	public String createSellOrder(BuySellBean bsb) {
//
//	
//     User user =getUserById(bsb.getUserId()).get();
// 	
//     if(user==null)
//			return "user is null";
//     
//     if(user.getStatus()==null)
//			return "user is inactive";
//		if(user.getStatus().equals(UserStatus.INACTIVE))
//			return "user is inactive";
//		if(bsb.getUserId()==null)
//         return "userId cannot be null";
//		
//		if(bsb.getCoinName()==null||bsb.getCoinName().length()==0)
//		      return "coin name cannot be null";
//		
//		if(bsb.getCoinQuantity()==null||bsb.getCoinQuantity()==0)
//		      return "coin quantity cannot be null";
//		
//		if(bsb.getPrice()==null||bsb.getPrice()==0)
//		      return "price cannot be null";
//		
//     
//		UserOrder userorder=new UserOrder();
//		userorder.setCoinName(bsb.getCoinName());
//		userorder.setCoinQuantity(bsb.getCoinQuantity());
//		userorder.setDate(new Date());
//		userorder.setOrderType(OrderType.SELL);
//		userorder.setUser(user);
//		userorder.setPrice(bsb.getPrice());
//		userorder.setUserId(user.getUserId());
//		userorderRepository.save(userorder);
//		
//		return "your order has been placed successfully! wait for approval";
//	}
//	
	
	
	public List<UserOrder> getAllOrdersByUserId(Integer userId) {
		
		User user=getUserById(userId).get();
		
		return user.getUserOrder();
		
	}



	public String verifyUser(SignUpOTP userOtp) {
		
		SignUpOTP userOTP = signupOTPRepository.findBytokenOTP(userOtp.getTokenOTP());
		User user = userRepository.findById(userOtp.getUserId()).get();
		
		if(user==null) {
			return "Invalid OTP";
		}
		else {
			if(userOTP.getEmail().equals(user.getEmail())) {
				
				signupOTPRepository.delete(userOTP);
				user.setStatus(UserStatus.ACTIVE);
				userRepository.save(user);
				return "your account is verified successfully";
			}
			else return "invalid OTP";
		}
		
	
	}

	
//	public String verifyUser(SignUpOTP userOtp) {
//		
//		SignUpOTP userOTP = signupOTPRepository.findBytokenOTP(userOtp.getTokenOTP());
//		User user=null;
//		if(userOtp.getEmail()!=null)
//		 user = userRepository.findByEmail(userOtp.getEmail());
//		else if(userOtp.getPhoneNumber()!=0)
//			 user = userRepository.findByphoneNumber(userOtp.getPhoneNumber());
//		
//		if(userOTP != null) {
//			
//			if(userOtp.getEmail()!=null) {
//			if(userOtp.getEmail().equals(userOTP.getEmail())) {
//				signupOTPRepository.delete(userOTP);
//				user.setStatus(UserStatus.ACTIVE);
//				userRepository.save(user);
//				return "success";
//			}
//				else
//					return "failure";
//		}
//			else  if(userOtp.getPhoneNumber()!=0) {
//				
//				if(userOtp.getPhoneNumber()==userOTP.getPhoneNumber()) {
//					signupOTPRepository.delete(userOTP);
//					user.setStatus(UserStatus.ACTIVE);
//					userRepository.save(user);
//					return "success";
//				}
//					else
//						return "failure";
//				
//			}
//			return "not found..";
//		  }
//		
//		else return "not found";
//		
//	
//	}
//	

	
}
