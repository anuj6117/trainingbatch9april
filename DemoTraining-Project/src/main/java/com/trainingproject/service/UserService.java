package com.trainingproject.service;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trainingproject.domain.Role;
import com.trainingproject.domain.User;
import com.trainingproject.domain.UserOTP;
import com.trainingproject.domain.UserOrder;
import com.trainingproject.domain.Wallet;
import com.trainingproject.dto.UserRoleDto;
import com.trainingproject.dto.UserWalletDto;
import com.trainingproject.enums.CoinType;
import com.trainingproject.enums.Status;
import com.trainingproject.repository.RoleRepository;
import com.trainingproject.repository.UserOTPRepository;
import com.trainingproject.repository.UserRepository;
import com.trainingproject.repository.WalletRepository;
import com.trainingproject.utils.MD5Generator;
@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserOTPRepository userOTPRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private UserOTPService userOTPService; 
	@Autowired
	private WalletRepository walletRepository;
	
	private UserOTP otp = new UserOTP();
	//private Role role = new Role();
	static Integer otp1;
	User user;
	static Integer integerBalance;
	UserOrder  userOrder = new UserOrder();
	CoinType coinType;
	String coinName;
	
	public Map<String,Object> addUsers(User user) {
		Map<String,Object> result = new HashMap<>();
		if ((userRepository.findByEmail(user.getEmail()) == null)) {
			String userName=user.getUserName();
		if(user.getUserName() != null) {
			if(userName.equals("")) {
				result.put("isSuccess", false);
				result.put("message", "User Name can't be blank.");
				return result;
			}
			if(userName.startsWith(" ") || userName.endsWith(" ")){
				result.put("isSuccess", false);
				result.put("message", "Space is not allowed in trailing and leading postion.");
				return result;
			}
			if(!(Pattern.compile("^[A-Za-z0-9_-]{1,25}$").matcher(userName).matches())) {
				result.put("isSuccess", false);
				result.put("message", "Space is not allowed between userName or Maximum character allowed for this field is 25.");
				return result;
			}
		}
			else {
	    	    result.put("isSuccess", false);
				result.put("message", "User Name can not be null");
				return result;
	       }
			if(user.getEmail() != null) {
		    	if(!(Pattern.compile("^[a-z0-9][a-z0-9(\\-[a-z0-9]+)(\\_[a-z0-9]+)]*[a-zA-Z0-9]+(\\.[a-z0-9]+)*[a-zA-Z0-9]+(\\-[a-z0-9]+)*[a-zA-Z0-9]+(\\_[a-z0-9]+)*[a-zA-Z0-9]*@[a-z0-9]+(\\.[a-z0-9]+)*(\\.[a-z]{2,})$").matcher(user.getEmail()).matches())) {
		    		result.put("isSuccess", false);
					result.put("message", "Please, enter a valid email address");
					return result;
		    	   }
		    	}
		          else {
		    	    result.put("isSuccess", false);
					result.put("message", "Email can not be null");
					return result;
		    }
			if(user.getPassword() != null) {
				if(!(Pattern.compile("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[+[@=#$]%{^}&*()_-|:?/>,.<]).{8,32}").matcher(user.getPassword()).matches())) {
					result.put("isSuccess", false);
					result.put("message", "Please, enter password with minimum 8 characters. your password should have atleast 1 Upper Case, 1 Lower Case, 1 Digit & 1 Special Character");
					return result;
		    	}
		    	
		    }
		    else {
		    	result.put("isSuccess", false);
				result.put("message", "Password can not be null");
				return result;
		    }
			if(user.getCountry() != null) {
				if(!(Pattern.compile("^[A-Za-z]{1,25}$").matcher(user.getCountry()).matches())) {
					result.put("isSuccess", false);
					result.put("message", "Country name is not valid");
					return result;
		    	}
		    }
		    else {
		    	result.put("isSuccess", false);
				result.put("message", "Country name can not be null");
				return result;
		    }	
			if(user.getPhoneNumber() != null ) {
				if(!(Pattern.compile("(^[0-9]{10}$)").matcher(user.getPhoneNumber()).matches())) {
					result.put("isSuccess", false);
					result.put("message", "Please, enter a valid mobile number");
					return result;
		    	}
		    }
		    else {
		    	result.put("isSuccess", false);
				result.put("message", "PhoneNumber can not be null");
				return result;
		    }	
			
			
			user.setCreatedOn(new Date());
			user.setStatus(Status.INACTIVE);
			List<Role> list = new ArrayList<Role>();
			Role role = new Role();
			Role role1 = new Role();
			role = roleRepository.findByRoleType("user");
			if(role != null) {
				list.add(role);
			}
			else {
			role1.setRoleType("user");
			list.add(role1);
			}
			user.setRoleType(list);
			user.setPassword(MD5Generator.encrypt(user.getPassword()));
			if(userRepository.findByPhoneNumber(user.getPhoneNumber()) == null)
			{	
				user.setPhoneNumber(user.getPhoneNumber());
			}
			else
			{
				result.put("isSuccess", false);
				result.put("message", "Mobile number is already exist.");
				return result;
			}
			User userCreated = userRepository.save(user);
			Set<Wallet> walletSet = new HashSet<Wallet>();
			Wallet wallet = new Wallet();
			wallet.setCoinType(CoinType.FIAT);
			wallet.setCoinName("INR");
			wallet.setUser(userCreated);
			walletSet.add(wallet);
			walletRepository.save(wallet);
			user.setUserWallet(walletSet);
			userRepository.save(userCreated);
			
			String email = user.getEmail();
			
			if(!(userRepository.save(user) == null)) {
				otp1 = userOTPService.sendSMS();
				userOTPService.sendMail(email);
				otp.setDate(new Date());
				otp.setEmail(email);
				otp.setTokenOTP(otp1);
				userOTPRepository.save(otp);
				
				result.put("isSuccess", true);
				result.put("message", "Your account has been successfully created. Please, verify it by using OTP.");
				return result;
			}
		}
		else {
			result.put("isSuccess", false);
			result.put("message", "Oopss, this email id is already registered");
			return result;
		}
		
		return result;
	}
	
	
		public List<User> getAllUsers() {
		List<User> list = new ArrayList<User>();
		userRepository.findAll()
		.forEach(list::add);
		return list;			
	}

	
	public User getById(Integer userId) {
		// TODO Auto-generated method stub
		User userGet = userRepository.findByUserId(userId);
		if(userGet != null) {
			return userGet;
		}
		else {
			throw new NullPointerException("User id does not exist");
		}
	}
	
	
	public Map<String,Object> updateUser(User user) {
		Map<String,Object> result = new HashMap<>();
		if ((userRepository.findByEmail(user.getEmail()) == null)) {
			String userName=user.getUserName();
		if(user.getUserName() != null) {
			if(userName.equals("")) {
				result.put("isSuccess", false);
				result.put("message", "User Name can't be blank.");
				return result;
			}
			if(userName.startsWith(" ") || userName.endsWith(" ")){
				result.put("isSuccess", false);
				result.put("message", "Space is not allowed in trailing and leading postion.");
				return result;
			}
			if(!(Pattern.compile("^[A-Za-z0-9_-]{1,25}$").matcher(userName).matches())) {
				result.put("isSuccess", false);
				result.put("message", "Space is not allowed between userName or Maximum character allowed for this field is 25.");
				return result;
			}
		}
			else {
	    	    result.put("isSuccess", false);
				result.put("message", "User Name can not be null");
				return result;
	       }
			if(user.getEmail() != null) {
		    	if(!(Pattern.compile("^[a-z0-9][a-z0-9(\\-[a-z0-9]+)(\\_[a-z0-9]+)]*[a-zA-Z0-9]+(\\.[a-z0-9]+)*[a-zA-Z0-9]+(\\-[a-z0-9]+)*[a-zA-Z0-9]+(\\_[a-z0-9]+)*[a-zA-Z0-9]*@[a-z0-9]+(\\.[a-z0-9]+)*(\\.[a-z]{2,})$").matcher(user.getEmail()).matches())) {
		    		result.put("isSuccess", false);
					result.put("message", "Please, enter a valid email address");
					return result;
		    	   }
		    	}
		          else {
		    	    result.put("isSuccess", false);
					result.put("message", "Email can not be null");
					return result;
		    }
			if(user.getPassword() != null) {
				if(!(Pattern.compile("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[+[@=#$]%{^}&*()_-|:?/>,.<]).{8,32}").matcher(user.getPassword()).matches())) {
					result.put("isSuccess", false);
					result.put("message", "Please, enter password with minimum 8 characters. your password should have atleast 1 Upper Case, 1 Lower Case, 1 Digit & 1 Special Character");
					return result;
		    	}
		    	
		    }
		    else {
		    	result.put("isSuccess", false);
				result.put("message", "Password can not be null");
				return result;
		    }
			if(user.getCountry() != null) {
				if(!(Pattern.compile("^[A-Za-z]{1,25}$").matcher(user.getCountry()).matches())) {
					result.put("isSuccess", false);
					result.put("message", "Country name is not valid");
					return result;
		    	}
		    }
		    else {
		    	result.put("isSuccess", false);
				result.put("message", "Country name can not be null");
				return result;
		    }	
			if(user.getPhoneNumber() != null ) {
				if(!(Pattern.compile("(^[0-9]{10}$)").matcher(user.getPhoneNumber()).matches())) {
					result.put("isSuccess", false);
					result.put("message", "Please, enter a valid mobile number");
					return result;
		    	}
		    }
		    else {
		    	result.put("isSuccess", false);
				result.put("message", "PhoneNumber can not be null");
				return result;
		    }
			user.setPassword(MD5Generator.encrypt(user.getPassword()));
		if(userRepository.findByPhoneNumber(user.getPhoneNumber()) == null)
		{	
			user.setPhoneNumber(user.getPhoneNumber());
		}
		else
		{
			result.put("isSuccess", false);
			result.put("message", "Mobile number is already exist.");
			return result;
		}
		userRepository.save(user);
		if(!(userRepository.save(user) == null)) {
			result.put("isSuccess", true);
			result.put("message", "Your account has been successfully Updated");
			return result;
		}
	}
		else {
			result.put("isSuccess", false);
			result.put("message", "Oopss, this email id is already registered");
			return result;
		}
		
		return result;
	}
	
	
	public String deleteUser(Integer userId) {
		user = userRepository.findByUserId(userId);
		if(user != null) {
		userRepository.deleteById(userId);
		return "Delete user success";
		}
		else {
			throw new NullPointerException("User id does not exist");
		}
		
	}

	
	public String assignRole(UserRoleDto userRoleDto) {
		User user = userRepository.findByUserId(userRoleDto.getUserId());
		if(user == null) {
			throw new NullPointerException("User id does not exist");
		}
		Role role = roleRepository.findByRoleType(userRoleDto.getRoleType());
		List<Role> roleType = user.getRoleType(); 
		for(Role fetchRole : roleType) {
			if(fetchRole.getRoleType().equalsIgnoreCase(userRoleDto.getRoleType())) {
				throw new NullPointerException("Role already assigned");
			}
		  }
		
		if(user.getStatus() == Status.ACTIVE) {
			if(role != null) {
				user.getRoleType().add(role);
				userRepository.save(user);
				return "Role has Successfully Assigned";
				} 	else {
					  throw new NullPointerException("User role doesn't exist");
					}
			} 	else {
			      throw new NullPointerException("User not ACTIVE.");
		        }
	}

	
	
	
	UserOTP userOTP;
	public String userVerification (UserOTP userOtp) throws Exception {
		userOTP = userOTPRepository.findByTokenOTP(userOtp.getTokenOTP());
		user = userRepository.findByEmail(userOtp.getEmail());
		if(userOTP != null) {
			if(userOTP.getEmail().equals(userOtp.getEmail())) {
				userOTPRepository.delete(userOTP);//deleteAll();
				user.setStatus(Status.ACTIVE);
				userRepository.save(user);
				return "Your account is verified successfully";
			}
				else
					return "Invalid email";
		  }
		else
			return "Invalid OTP";
		}

	
	
	
	
	public String addWallet(UserWalletDto userWalletDto) {
		// TODO Auto-generated method stub
		User user = userRepository.findByUserId(userWalletDto.getUserId());
		Set<Wallet> walletSet = new HashSet<Wallet>();
		Wallet wallet = new Wallet();
		coinType = userWalletDto.getCoinType();
		coinName = userWalletDto.getCoinName();
		if(walletRepository.findByCoinTypeAndCoinNameAndUser(coinType, coinName, user) == null) {
		if(user != null && user.getStatus() == Status.ACTIVE ) {
			wallet.setUser(user);
			wallet.setCoinType(userWalletDto.getCoinType());
			wallet.setCoinName(userWalletDto.getCoinName());
			walletSet.add(wallet);
			walletRepository.save(wallet);
			return "Wallet is successfully added";
		}
		else
			return "userId does not exist or user INACTIVE";
		}
		else 
			return "User have already these type of wallet";
	}
}
