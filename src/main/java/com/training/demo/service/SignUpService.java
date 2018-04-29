package com.training.demo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

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
	
//	private OtpVerification otpVerification=new OtpVerification();
	
	
	public String addUser(User user)
	{	
		boolean emailFlag = userRepository.existsByEmail(user.getEmail());
		boolean phoneNoFlag = userRepository.existsByPhoneNo(user.getPhoneNo());
		if(emailFlag == false && phoneNoFlag == false)
		{
			System.out.println("service hit");
			Random random=new Random();
			int tokenOTP=random.nextInt(999999)+1432;
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
			
			// Default Wallet Creation 
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
						String phoneNo = user.getPhoneNo();
							System.out.println("service hit inside if.");
							//otpService.sendSms(tokenOTP, phoneNo);
							try {
								emailService.sendEmail(tokenOTP, email);
							} catch (Exception e) {
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
				else
				{
					System.out.println("Invalid Username.");
					return "Registration Failure.";
				}
		}
		else
		{
			System.out.println("Already existing Email or Username.");
			return "Already existing Email or Username.";
		}
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
		try {
		     User tempUser = userRepository.findByUserId(user.getUserId());
			 tempUser.setEmail(user.getEmail());
			 tempUser.setFullName(user.getFullName());
			 tempUser.setPhoneNo(user.getPhoneNo());
			 tempUser.setCountry(user.getCountry());
			 tempUser.setPassword(user.getPassword());	      
			 userRepository.save(tempUser);
			 }
		catch (Exception ex)
		{	 
			return "Error while updating the user: " + ex.toString();
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
			role = roleRepository.findByRoleType(userRoleDto.getRoleType());
		}	
		catch(Exception e)
		{
				return "invalid userid";
			}
		try 
		{
			role = roleRepository.findByRoleType(userRoleDto.getRoleType());
		}	
		catch(Exception e)
		{
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
					else {
						throw new NullPointerException("Given role doesn't exist");
					}
				}
			else
			{
				throw new NullPointerException("User id does not exist.");
			}
	}	
}