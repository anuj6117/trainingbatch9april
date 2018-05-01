package com.trainingproject.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
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
	     
	     p = Pattern.compile("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");
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
		
		user.setCreatedOn(new Date());
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
		wallet.setUser(user);
		walletSet.add(wallet);
		walletRepository.save(wallet);
		user.setUserWallet(walletSet);
		
		List<Role> roleList=new ArrayList<Role>();
		Role role=new Role();
		role.setRoleType("User");
		roleList.add(role);      //todo
		if(roleRepository.findByroleType("User")==null)
		roleRepository.save(role);
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
	
	public Optional<User> getUserById(Integer userId) {

		return userRepository.findById(userId);
		
	}


	public String update(User user) {
	
		if(!userRepository.existsById(user.getUserId()))
			return "this user do not exist";
		
		if(user.getStatus()==null)
			return "user is inactive";
		
		if(user.getStatus().equals(UserStatus.INACTIVE))
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
		user.setCreatedOn(new Date());
		 userRepository.save(user);
		 return "success";
	}

	public void deleteData(Integer userId)
	{
		userRepository.deleteById(userId);
	}


	public String assignRole(AssignRoleBean arb) {
		
		User user=getUserById(arb.getUserId()).get();
		
		 if(user.getStatus()==null)
				return "user is inactive";
		 
	      if(user.getStatus().equals(UserStatus.INACTIVE))
		return "user is inactive";
	      
		Role roleobj=roleRepository.findByroleType(arb.getRoleType());
		List<Role> role=new ArrayList<Role>();
		role.add(roleobj);
		user.setRoleType(role);
		userRepository.save(user);
		return "success";
		//System.out.println(rolelist.get(0).getRoleType()+"kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk");
	
		
	}


	public String addWallet(AssignWalletBean awb) {

		
		User user=getUserById(awb.getUserId()).get();
		
		if(user.getStatus().equals(UserStatus.INACTIVE))
			return "user is inactive";
		
		
		Wallet cwallet=new Wallet();
		cwallet.setCoinType(awb.getWalletType());
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
		userorder.setCoinQuantity((int)wdb.getAmount());
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
		User user = userRepository.findByEmail(userOtp.getEmail());
		if(userOTP != null) {
			
			if(userOtp.getEmail().equals(userOTP.getEmail())) {
				signupOTPRepository.delete(userOTP);
				user.setStatus(UserStatus.ACTIVE);
				userRepository.save(user);
				return "success";
			}
				else
					return "failure";
		  }
		else
			return "not found";
		
	
	}
	

	
}
