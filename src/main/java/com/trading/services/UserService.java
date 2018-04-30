package com.trading.services;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trading.Enum.RoleType;
import com.trading.Enum.UserStatus;
import com.trading.Enum.WalletType;
import com.trading.domain.Role;
import com.trading.domain.User;
import com.trading.domain.UserOtp;
import com.trading.domain.Wallet;
import com.trading.dto.UserRoleDto;
import com.trading.repository.RoleRepository;
import com.trading.repository.UserOtpRepository;
import com.trading.repository.UserRepository;
import com.trading.utilities.EmailValidator;
import com.trading.utilities.NameValidator;
import com.trading.utilities.PasswordValidator;
import com.trading.utilities.PhoneValidator;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private OtpService otpservice;

	@Autowired
	private EmailService emailservice;

	@Autowired
	private UserOtpRepository userotpRepository;

	Random rnd = new Random();
	int otp = rnd.nextInt(10000);

	public int getOtp() {
		return otp;
	}

	private UserOtp userotp = new UserOtp();

	public Map<String, Object> signup(User user) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		if (user.getPassword() == "" || user.getPassword() == null) {
			result.put("isSuccess", false);
			result.put("message", "Password cannot be null");
			return result;
		}
		if (user.getUserName() == null || user.getUserName() == "") {
			result.put("isSuccess", false);
			result.put("message", "Username can not be null");
			return result;
		}
		if (userRepository.findByEmail(user.getEmail()) != null) {
			result.put("isSuccess", false);
			result.put("message", "Oopss, this email is already registered");
			return result;
		}
		if (user.getEmail() == null || user.getEmail() == "") {
			result.put("isSuccess", false);
			result.put("message", "Email cannot be null");
			return result;
		}

		if (userRepository.findByphoneNumber(user.getPhoneNumber()) != null) {
			result.put("isSuccess", false);
			result.put("message", "Oopss, this phoneNumber is already registered");
			return result;
		}
		String phoneNumber = user.getPhoneNumber()+"";
		if (phoneNumber.trim().length()!= 10 ) {
			result.put("isSuccess", false);
			result.put("message", "Enter valid phone Number");
			return result;
		}
		
				
	if(NameValidator.isValid(user.getUserName())  && user.getUserName().trim().length() <= 25) 
		{
		if (EmailValidator.isValidEmailAddress(user.getEmail())) {
			if (PasswordValidator.isValid(user.getPassword())) {
				if (userRepository.save(user) != null) {
					user.setDate(new Date().toString());
					user.setStatus(UserStatus.INACTIVE);
					String email = user.getEmail();
					otpservice.sendSMS(otp);
					emailservice.sendEmail(otp);
					userotp.settokenOTP(otp);
					userotp.setEmail(email);
					userotpRepository.save(userotp);
					Wallet wallet = new Wallet();
					wallet.setCoinType(WalletType.FIAT);
					wallet.setCoinName("INR");
					wallet.setuser(user);
					user.getWallet().add(wallet);
					Role role = new Role();
					role.setRoleType(RoleType.USER);
					user.getRole().add(role);
					userRepository.save(user);
					result.put("isSuccess", true);
					result.put("message", "Your account has been created, please verify your account");
					return result;
				} else {
					result.put("isSuccess", false);
					result.put("message", "Failed to create new account");
					return result;
				}}
			else {
				result.put("isSuccess", false);
				result.put("message", "Please enter valid password");
				return result;
			}}
		else {
			
			result.put("isSuccess", false);
			result.put("message", "Please enter valid email address");
			return result;
		}
		}
		
		else
		{
			result.put("isSuccess", false);
			result.put("message", "Please enter valid user name");
			return result;
		}
		}
		

	

	public Iterable<User> getDetails() {
		return userRepository.findAll();
	}

	public Optional<User> getById(long userId) {
		if (userRepository.findOneByUserId(userId) != null) {
			return userRepository.findById(userId);
		} else {
			return null;
		}
	}

	public Map<String, Object> updateDetails(User user) {
		Map<String, Object> result = new HashMap<String, Object>();

		User userdb = null;
		userdb = userRepository.findOneByUserId(user.getUserId());
		if (user.getPassword() == "" || user.getPassword() == null) {
			result.put("isSuccess", false);
			result.put("message", "Password cannot be null");
			return result;
		}
		if (user.getUserName() == null || user.getUserName() == "") {
			result.put("isSuccess", false);
			result.put("message", "Username can not be null");
			return result;
		}
		
		if (user.getEmail() == null || user.getEmail() == "") {
			result.put("isSuccess", false);
			result.put("message", "Email cannot be null");
			return result;
		}

		
		String phoneNumber = user.getPhoneNumber()+"";
		if (phoneNumber.trim().length()!= 10  && PhoneValidator.isValid(phoneNumber)) {
			result.put("isSuccess", false);
			result.put("message", "Enter valid phone Number");
			return result;
		}
		
	if(NameValidator.isValid(user.getUserName())  && user.getUserName().trim().length() <= 25) 
		{
		if (EmailValidator.isValidEmailAddress(user.getEmail())) {
			if (PasswordValidator.isValid(user.getPassword())) {
		if (userdb != null) {
			userdb.setUserName(user.getUserName());
			userdb.setCountry(user.getCountry());
			userdb.setEmail(user.getEmail());
			userdb.setPassword(user.getPassword());
			userdb.setPhoneNumber(user.getPhoneNumber());
			userRepository.save(userdb);
			result.put("isSuccess", true);
			result.put("message", "Succesfully updated details");
			return result;
		} else {

			result.put("isSuccess", false);
			result.put("message", "User Id does not exist");
			return result;
		}}
			else {
				result.put("isSuccess", false);
				result.put("message", "Please enter valid password");
				return result;
			}}
		else {
			
			result.put("isSuccess", false);
			result.put("message", "Please enter valid email address");
			return result;
		}
		}
		
		else
		{
			result.put("isSuccess", false);
			result.put("message", "Please enter valid user name");
			return result;
		}
	}

	public Map<String, Object> deleteById(long userId) {
		Map<String, Object> result = new HashMap<String, Object>();

		User user = userRepository.findOneByUserId(userId);
		if (user != null) {

			userRepository.deleteById(userId);
			result.put("isSuccess", true);
			result.put("message", "User Deleted");
			return result;
		} else {
			result.put("isSuccess", false);
			result.put("message", "User Id does not exist");
			return result;
		}
	}

	public Map<String, Object> assignNewRole(UserRoleDto userroledto) {
		Map<String, Object> result = new HashMap<String, Object>();

		User userdb = null;
		userdb = userRepository.findOneByUserId(userroledto.getuserId());
		Role roledb = roleRepository.findByRoleType(userroledto.getRoleType());
		if (userdb == null) {
			result.put("isSuccess", true);
			result.put("message", "User does not exist");
			return result;
		}
		if (roledb == null) {
			Role role = new Role();
			role.setRoleType(userroledto.getRoleType());
			userdb.getRole().add(role);
			userRepository.save(userdb);
			result.put("isSuccess", true);
			result.put("message", "New role has been added and assigned");
			return result;
		} else {
			userdb.getRole().add(roledb);
			userRepository.save(userdb);
			result.put("isSuccess", false);
			result.put("message", "Existing role has been assigned");
			return result;
		}
	}
}
