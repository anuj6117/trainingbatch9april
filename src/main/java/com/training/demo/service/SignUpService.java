package com.training.demo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.training.demo.dto.UserRoleDto;
import com.training.demo.enums.UserStatus;
import com.training.demo.enums.WalletType;
import com.training.demo.model.OtpVerification;
import com.training.demo.model.Role;
import com.training.demo.model.User;
import com.training.demo.model.Wallet;
import com.training.demo.repository.OtpRepository;
import com.training.demo.repository.RoleRepository;
import com.training.demo.repository.UserRepository;
import com.training.demo.utility.EmailValidation;
import com.training.demo.utility.PhoneValidation;

@Service
public class SignUpService 
{
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OTPService otpService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private OtpRepository otpRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	private OtpVerification otpVerification;
	
	EmailValidation emailValidation = new EmailValidation();		

	public String addUser(User user)
	{
		String userName=user.getUserName();
		System.out.println(userName+"======================================");
		String password = user.getPassword();
		
		String tempEmail=user.getEmail();		
		boolean validateEmail=emailValidation.validateEmail(tempEmail);
		
			if(!validateEmail)
			{
				return  "Please enter a valid email address.";
			}

		
		if(!(password.matches("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}")))
		{
			return "Please enter password with minimum 8 characters. Your password should have atleast 1 Uppercase, 1 Lowercase, 1 Digit & 1 Special character. Space is not allowed";
		}
			
		if(userName.equals("") || userName.isEmpty() || userName == null)
		{
			return  "User Name can't be null.";
		}
		
		if(userName.length() >= 26 || userName.length() <= 6)
		{
			return  "Maximum characters allowed for this field is 6 to 25.";			
		}
		
		if(userName.trim().length() == 0)
		{
			return	"User Name must contain characters.";
		}
				
		String tempPhoneNo=user.getPhoneNumber();
		if(!PhoneValidation.isValid(tempPhoneNo))
		{
			return  "Please enter a valid mobile number.";
		}

		
			if ((userRepository.findByEmail(user.getEmail()) == null)) 
			{
				//boolean emailFlag = userRepository.existsByEmail(user.getEmail());
				boolean phoneNoFlag = userRepository.existsByPhoneNumber(user.getPhoneNumber());
					if(phoneNoFlag == false)
					{
						System.out.println("service hit");
						Random random=new Random();
						int tokenOTP=random.nextInt(99777)+1432;
						Date date;
						System.out.println("service hit before if");
						user.setUserStatus(UserStatus.INACTIVE);
						user.setDate(new Date());
						
						System.out.println("Default roleType assigned to user = ");
						//Default Role Creation
						Role roles = roleRepository.findByRoleId(1);
						HashSet<Role> roleHashSet = new HashSet<Role>();
						roleHashSet.add(roles);
						user.setRoles(roleHashSet);
			
						//Default Wallet Creation 
						Wallet wallet  = new Wallet();
						wallet.setWalletType(WalletType.FIAT);
						wallet.setUser(user);
						wallet.setBalance(0.0);
						wallet.setShadowBalance(0.0);
						Set<Wallet> walletHashSet = new HashSet<Wallet>();
						walletHashSet.add(wallet);
						user.setWallets(walletHashSet);
			
						User existingUser =userRepository.save(user);
						if( existingUser != null)
						{
							String email = user.getEmail();
							String phoneNo = user.getPhoneNumber();
							System.out.println("service hit inside if.");
							//otpService.sendSms(tokenOTP, phoneNo);
							try 
							{
								//emailService.sendEmail(tokenOTP, email);
							}
							catch (Exception e) 
							{
								System.out.println("email could not be verified as : "+e.getMessage());
							}
							otpVerification = new OtpVerification();
							otpVerification.setUserId(user.getUserId());
							otpVerification.setTokenOTP(tokenOTP);
							otpVerification.setEmail(user.getEmail());
							date = new Date();
							otpVerification.setDate(date);
							otpRepository.save(otpVerification);
							return "Your account has been successfully created. Please, verify it by using OTP.";
						}
					}	
					else
					{
							System.out.println("Invalid Username.");
							return "already existing phone number.";
					}
				}
			
					System.out.println("Already existing Email or Username.");
					return "Already existing Email.";			
	}
	public String verifyUserWithOtp(String email,Integer tokenOTP)
	{
		OtpVerification tempOtpVerification;
		User t_user;
		try 
		{			
			tempOtpVerification = otpRepository.findByEmail(email);
			t_user = userRepository.findByEmail(email);		
			String v_email = tempOtpVerification.getEmail();
			int v_otp = tempOtpVerification.getTokenOTP();
		
			if(email.equals(v_email))
			{
				System.out.println("email is successfully verified : "+email);
				System.out.println("..........---------Token otp >>>>>> "+tempOtpVerification.getTokenOTP());
					if(tokenOTP.equals(v_otp))
					{
						System.out.println(tokenOTP+"< token otp is successfully verified with table otp > "+v_otp);
					}
					else
					{
						return "invalid otp";
					}
					otpRepository.delete(tempOtpVerification);	
					t_user.setUserStatus(UserStatus.ACTIVE);
					userRepository.save(t_user);
					System.out.println("otpVerification table deleted.");
					return "Your account is verified successfully.";
			}
			else
			{
					System.out.println("Sorry, invalid username or otp");
					return "Invalid email";
			}
		}
		catch(Exception e)
		{
			return "invalid email.";
		}
		
	}
	
	public List<User> getAllUsers()
	{
		List<User> l = new ArrayList<User>();
		l = userRepository.findAll();
		return l;
	}
		public Optional<User> getUserById(Integer userId)
		{
		Optional<User> usrid = userRepository.findById(userId);
		if (usrid != null) {
			return usrid;
		} else {
			throw new NullPointerException("Id does not exist.");
		}
	}
		public String updateUser(@RequestBody User user) {
			String userName = user.getUserName();
			String password = user.getPassword();
			
			if(!(password.matches("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}")))
			{
				return "Please enter password with minimum 8 characters. Your password should have atleast 1 Uppercase, 1 Lowercase, 1 Digit & 1 Special character. Space is not allowed.";
				
			}
			
				if(userName.equals("") || userName.isEmpty() || userName == null)
				{
					return  "User Name can't be null.";
				}
				
				if(userName.length() >= 26 || userName.length() <= 6)
				{
					return  "Maximum characters allowed for this User Name field is 6 to 25.";			
				}
				
				if(userName.trim().length() == 0)
				{
					return	"User Name must contain characters.";
				}
			
			String tempEmail=user.getEmail();
			
			boolean validateEmail=emailValidation.validateEmail(tempEmail);
			
				if(!validateEmail)
				{
					return  "Please enter a valid email address.";
				}
			
			String tempPhoneNo=user.getPhoneNumber();
			
				if(!PhoneValidation.isValid(tempPhoneNo))
				{
					return  "Please enter a valid mobile number.";
				}

			try {
		     User tempUser = userRepository.findByUserId(user.getUserId());
			 tempUser.setEmail(user.getEmail());
			 tempUser.setUserName(user.getUserName());
			 tempUser.setPhoneNumber(user.getPhoneNumber());
			 tempUser.setCountry(user.getCountry());
			 tempUser.setPassword(user.getPassword());	      
			 userRepository.save(tempUser); 
			}
		catch (Exception ex)
		{	 
			return "user does not exist";
		}
			
		return "User succesfully updated!";
	 }

	public String assignRoleToUser(UserRoleDto userRoleDto)
	{
		User user;
		Role role;
		try 
		{
			
			user = userRepository.findByUserId(userRoleDto.getUserId());
			System.out.println("1111111111111111111111111111111");
			}	
		catch(Exception e)
		{
			System.out.println("userrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");
				return "invalid userid";
			}
		try 
		{
			role = roleRepository.findByRoleType(userRoleDto.getRoleType());
			System.out.println("22222222222222222222222222222222222222222");
			}	
		catch(Exception e)
		{
			System.out.println("roleeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
				return "invalid roleType";
			}
			if(user != null)
				{
					if(role != null)
					{
						user.getRoles().add(role);
						userRepository.save(user);

						return "Role is successfully assigned to the given user.";
					}	
					else
					{
						return "invalid roleType";
					}
					
				}
			else
			{
				return "invalid userId.";
			}
			
		}
}