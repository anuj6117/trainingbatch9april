package com.example.demo.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.enums.UserStatus;
import com.example.demo.enums.WalletType;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.model.VerifyOtp;
import com.example.demo.model.Wallet;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.VerifyOtpRepository;
import com.example.demo.repository.WalletRepository;
import com.example.demo.utility.EmailValidator;
import com.example.demo.utility.MobileNumberValidator;

@Service
public class SignUpService
{
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private OTPService otpService;

	@Autowired
	private MailService mailService;

	@Autowired
	private VerifyOtpRepository verifyOtpRepository;

	@Autowired
	private WalletRepository walletRepository;
	
	@Autowired
	private EmailValidator emailValidator;
	
	@Autowired
	private MobileNumberValidator mobileNumberValidator;

	private VerifyOtp verifyOtp = new VerifyOtp();
	
	private Integer tokenOtp;

	public Map<String, Object> addUser(User user) 
	{
		Map<String, Object> result = new HashMap<String, Object>();
		
		if ((userRepository.findByEmail(user.getEmail()) == null)) 
		{
			
			String userName=user.getUserName();
			
			if(userName.length() == 0)
			{
				result.put("isSuccess", false);
				result.put("message", "User Name should not be empty.");
				return result;
			}
			if(userName.startsWith(" "))
			{
				result.put("isSuccess", false);
				result.put("message", "User Name should not have leading space.");
				return result;
			}
			if(userName.endsWith(" "))
			{
				result.put("isSuccess", false);
				result.put("message", "User Name should not have trailing space.");
				return result;
			}
			
			if(!(Pattern.compile("^[a-zA-Z0-9\\s\\\\_\\\\-]{3,25}$").matcher(userName).matches())) 
			{
				result.put("isSuccess", false);
				result.put("message", "Special character is not allowed in username or length should not exceed 25 character.");
				return result;
			}			
			
			String country = user.getCountry();
			if(country.length() == 0)
			{
				result.put("isSuccess", false);
				result.put("message", "Country should not be empty.");
				return result;
			}
			if(country.startsWith(" "))
			{
				result.put("isSuccess", false);
				result.put("message", "Country should not have leading space.");
				return result;
			}
			if(country.endsWith(" "))
			{
				result.put("isSuccess", false);
				result.put("message", "country should not have trailing space.");
				return result;
			}
			if(country.length() == 0)
			{
				result.put("isSuccess", false);
				result.put("message", "Country should not be empty.");
				return result;
			}
			if(country.length() < 2)
			{
				result.put("isSuccess", false);
				result.put("message", "Country should have atleast two characters.");
				return result;
			}
			
			String email=user.getEmail();
			
			boolean b=emailValidator.validateEmail(email);
			
			if(!b)
			{
				result.put("isSuccess", false);
				result.put("message", "Please enter a valid email address.");
				return result;
			}
			
			String phoneNumber=user.getPhoneNumber();
			
			if(!mobileNumberValidator.isValid(phoneNumber))
			{
				result.put("isSuccess", false);
				result.put("message", "Please enter a valid mobile number.");
				return result;
			}
			
			String password=user.getPassword();
			if(!(password.matches("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=>])(?=\\S+$).{8,32}")))
			{
				result.put("isSuccess", false);
				result.put("message", "Please enter password with minimum 8 characters. Your password should have atleast 1 Uppercase, 1 Lowercase, 1 Digit & 1 Special character. Space is not allowed.");
				return result;
			}
						
			Random randomNumber = new Random();
			tokenOtp = randomNumber.nextInt(10000);

			Role role = null;
			if ((role = roleRepository.findByRoleType("user")) == null) 
			{
				role = new Role();
				role.setRoleType("USER");
			}

			User newUser = new User(user);
			newUser.getRoles().add(role);
			newUser.setStatus(UserStatus.INACTIVE);

			Wallet wallet = new Wallet();
			wallet.setWalletType(WalletType.FIAT);
			wallet.setBalance(0.0);
			wallet.setShadowBalance(0.0);
			wallet.setCoinName("INR");
			wallet.setUser(newUser);
			newUser.getWallets().add(wallet);
			
			
			newUser.setUserName(userName.trim());
			newUser.setEmail(email);
			newUser.setPassword(password);
			if(userRepository.findByPhoneNumber(phoneNumber) == null)
			{	
				newUser.setPhoneNumber(phoneNumber);
			}
			else
			{
				result.put("isSuccess", false);
				result.put("message", "Mobile number is already exist.");
				return result;
			}

			if ((userRepository.save(newUser) != null))
			{
				walletRepository.save(wallet);
				//otpService.sendSms(tokenOtp);
				//mailService.sendMail(tokenOtp, user.getEmail());

				verifyOtp.setId(user.getUserId());
				verifyOtp.setTokenOTP(tokenOtp);
				verifyOtp.setEmail(user.getEmail());
				verifyOtp.getEmail();
				verifyOtp.setDate(new Date());

				verifyOtpRepository.save(verifyOtp);

				result.put("isSuccess", true);
				result.put("message", "Your account has been successfully created. Please, verify it by using OTP.");
				return result;
			} 
			else
			{
				result.put("isSuccess", false);
				result.put("message", "FAIL");
				return result;
			}
		} 
		else
		{
			result.put("isSuccess", false);
			result.put("message", "Oopss, this email id is already exist.");
			return result;
		}
	}

	public String verifyUserWithOtp(String emailId, Integer otp)
	{

		VerifyOtp verifyOtpObject = verifyOtpRepository.findByEmail(emailId);
		if (verifyOtpObject == null) 
		{
			return "email does not exist.";
		}
		
		if (otp.equals(verifyOtpObject.getTokenOTP())) 
		{
			User user = userRepository.findByEmail(emailId);
			user.setStatus(UserStatus.ACTIVE);
			verifyOtpRepository.deleteById(verifyOtpObject.getId());
			return "Otp verification successfull.";
		} 
		else
		{
			return "Sorry, invalid otp";
		}
	}

	public List<User> getAllUsers() 
	{
		return userRepository.findAll();

	}
	
	public String delete(Integer id)
	{
		try 
		{
			userRepository.deleteById(id);
			return "User deleted successfully.";
		}
		catch(Exception e)
		{
			return "User does not exist of given id.";
		}
		
	}

	public Object getByUserId(Integer userId)
	{
		User user = userRepository.findOneByUserId(userId);
		if(user != null)
		{
			return user;
		}
		else
		{
			return "User does not exist of given id.";
		}
	}

	public Object update(User user) 
	{
		User userdb = userRepository.findOneByUserId(user.getUserId());
		if(userdb != null)
		{
			String userName = user.getUserName();
			if(userName.length() == 0)
			{
				return "User Name should not be empty.";
			}
			if(userName.startsWith(" "))
			{
				return "User Name should not have leading space.";
			}
			if(userName.endsWith(" "))
			{
				return "User Name should not have trailing space.";
			}
			if(!(Pattern.compile("^[a-zA-Z0-9\\s\\\\._\\\\-]{3,25}$").matcher(userName).matches())) 
			{
				return "Special character is not allowed in username or length should not exceed 25 character.";
			}
			
			String email=user.getEmail();
			boolean b=emailValidator.validateEmail(email);
			if(!b)
			{
				return "Please enter a valid email address.";
			}
			
			String phoneNumber=user.getPhoneNumber();
			if(!mobileNumberValidator.isValid(phoneNumber))
			{
				return "Please enter a valid mobile number.";
			}
			
			String country = user.getCountry();
			if(country.length() == 0)
			{
				return "Country should not be empty.";
			}
			if(country.startsWith(" "))
			{
			return "Country should not have leading space.";
			}
			if(country.endsWith(" "))
			{
				return "country should not have trailing space.";
			}
			if(country.length() == 0)
			{
				return"Country should not be empty.";
			}
			if(country.length() < 2)
			{
				return "Country should have atleast two characters.";
			}
			
			String password=user.getPassword();
			if(!(password.matches("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=>])(?=\\S+$).{8,32}")))
			{
				return "Please enter password with minimum 8 characters. Your password should have atleast 1 Uppercase, 1 Lowercase, 1 Digit & 1 Special character. Space is not allowed.";
			}
			
			userdb.setUserName(userName);
			userdb.setEmail(email);
			userdb.setPhoneNumber(phoneNumber);
			userdb.setCountry(country);
			userdb.setPassword(password);
			userRepository.save(userdb);
			
			return "User updated successfully.";
			
		}
		else
		{
			return "User does not exist of given id.";
		}
		
		/*userdb.setUserName(user.getUserName());
		userdb.setEmail(user.getEmail());
		userdb.setPhoneNumber(user.getPhoneNumber());
		userdb.setCountry(user.getCountry());
		userdb.setPassword(user.getPassword());

		return userRepository.save(userdb);*/
	}

	
}
