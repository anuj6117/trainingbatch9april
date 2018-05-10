package com.training.demo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.training.demo.dto.DtoUser;
import com.training.demo.enums.RoleType;
import com.training.demo.enums.UserStatus;
import com.training.demo.enums.WalletType;
import com.training.demo.model.OtpVerification;
import com.training.demo.model.Role;
import com.training.demo.model.User;
import com.training.demo.model.Wallet;
import com.training.demo.repository.OtpRepository;
import com.training.demo.repository.RoleRepository;
import com.training.demo.repository.UserRepository;
import com.training.demo.repository.WalletRepository;


@Service
public class SignUpService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private OTPService otpService;

	@Autowired
	private EmailService emailService;

	@Autowired
	private OtpRepository otpRepository;
	@Autowired
	private WalletRepository walletRepository;

	@Autowired
	private RoleRepository roleRepository;

	private OtpVerification otpVerification;
	
	private static Pattern pswNamePtrn = 
			Pattern.compile("((?=.*\\d)(?=.$*[a-z])(?=.*[A-Z])(?=.*[@#%==>>>///]).{8,32})");
	

	public String addUser(User user) {
		Role roles = roleRepository.findByRoleId(1);
		HashSet<Role> hashSet = new HashSet();
		boolean b = hashSet.add(roles);
		
		boolean flag = userRepository.existsByEmail(user.getEmail());
		boolean phone=userRepository.existsByPhoneNumber(user.getPhoneNumber());
		// )&&(userRepository.existByPhoneNo(user.getPhoneNo())));
		
		 String pass=user.getPassword();
	       java.util.regex.Matcher mtch = pswNamePtrn.matcher(pass);
	       if(!mtch.matches())
	       {
	    	   return "Password Must Contains one Uppercase Alphabet,one lower case ,on dgigt, one special symbol";
	       }
		if (flag == false) {
			if(phone==false)
			{
				
				
			}
			else
			{
				return " oops this number is already exist";
			}
			String email = user.getEmail();
			
			if(user.getEmail() != null) {
		    	//if(!(Pattern.compile("^[_-]{0,1}+[a-z0-9]+[_-]{0,1}+(\\.[_a-z0-9-]+)*@[a-z0-9]+(\\.[a-z0-9]+)*(\\.[a-z]{2,})$").matcher(user.getEmail()).matches()))
				//if(!(Pattern.compile("^[a-z0-9][a-z0-9(\\-[a-z0-9]+)(\\_[a-z0-9]+)]*[a-zA-Z]+(\\.[a-z0-9]+)*[a-zA-Z]+(\\-[a-z0-9]+)*[a-zA-Z]+(\\_[a-z0-9]+)*[a-zA-Z]*@[a-z0-9]+(\\.[a-z0-9]+)*(\\.[a-z]{2,})$").matcher(user.getEmail()).matches()))
				//if(!(Pattern.compile("^[a-z0-9][a-z0-9(\\-[a-z0-9]+)(\\_[a-z0-9]+)]*[a-zA-Z0-9]+(\\.[a-z0-9]+)*[a-zA-Z0-9]+(\\-[a-z0-9]+)*[a-zA-Z0-9]+(\\_[a-z0-9]+)*[a-zA-Z0-9]*@[a-z0-9]+(\\.[a-z0-9]+)*(\\.[a-z]{2})$").matcher(user.getEmail()).matches())) 
				
				if(!(Pattern.compile("^[a-z0-9][a-z0-9(\\-[a-z0-9]+)(\\_[a-z0-9]+)]*[a-zA-Z0-9]+(\\.[a-z0-9]+)*[a-zA-Z0-9]+(\\-[a-z0-9]+)*[a-zA-Z0-9]+(\\_[a-z0-9]+)*[a-zA-Z0-9]*@[a-z0-9]+(\\.[a-z0-9]+)*(\\.[a-z]{2,})$").matcher(user.getEmail()).matches())) 			
				{
					
				
		    		
		    		return"enter valid email";
		    	}
			}
		    	else
		    	{
		    		return"email can not be null";
		    	}
			String phoneNo = user.getPhoneNumber();
			System.out.println(email);

			System.out.println("service hit");
			Random random = new Random();
			int otp = random.nextInt(899000)+1000;
			Date date;
			System.out.println("service hit before if");
			user.setUserStatus(UserStatus.INACTIVE);
			user.setRoles((hashSet));
			date = new Date();
			user.setDate(date);
			System.out.println("fdtrdtrdtrdrdrt");
			User existingUser = userRepository.save(user);
			Set<Wallet> wallet = new HashSet<Wallet>();
			Wallet userwallet = new Wallet();
			userwallet.setCoinType(WalletType.FIAT);
			userwallet.setCoinName("inr");
			userwallet.setUser(existingUser);
			wallet.add(userwallet);
			walletRepository.save(userwallet);
			user.setWallet(wallet);
			Role role=new Role();
			role.setRoleType(RoleType.USER);
			user.getRoles().add(role);
			userRepository.save(existingUser);

			// user.getWallet().add(wallet);
			if (existingUser != null) {

				System.out.println("service hit inside if.");
				otpService.sendSms(otp, phoneNo);
				emailService.home(email, otp);

				otpVerification = new OtpVerification();
				otpVerification.setUserId(user.getUserId());
				otpVerification.setTokenOTP(otp);
				otpVerification.setEmail(user.getEmail());
				date = new Date();
				otpVerification.setDate(date);
				otpRepository.save(otpVerification);
				return "your account is succesfully created plese verify it by using OTP.";
			} else {
				System.out.println("Invalid Username.");
				return "Registration Failure.";
			}
		} else {
			
			return "oops this email id is already registerd ";
		}
	}

	public String verifyUserWithOtp(String email, Integer otp) {
		try {
			OtpVerification tempOtpVerification = otpRepository.findByEmail(email);
			String v_email = tempOtpVerification.getEmail();
			int v_otp = tempOtpVerification.getTokenOTP();
			User t_user = userRepository.findByEmail(email);
			System.out.println(":::::::::::::::::::"+v_email);
			
			System.out.println(email+":::::::::::::::::::::");
			
			if (email.equals(v_email)) {
				System.out.println("email is successfully verified" + email);
				if (otp.equals(v_otp)) {
					System.out.println(otp + " otp is successfully verified" + otp);

				}
				otpRepository.delete(tempOtpVerification);
				t_user.setUserStatus(UserStatus.ACTIVE);
				userRepository.save(t_user);
				System.out.println("otpVerification table deleted.");
				return "your account is verified succesfully";
			} else {
				return "otp is not valid";
			}
		} catch (Exception e) {
			return " email does not match" + e.toString();
		}
	}

	public List<User> getAllUsers() {
		List<User> l = new ArrayList<User>();
		l = userRepository.findAll();
		return l;
	}

	public Object getUserById(Integer userId) {
		User user = userRepository.findByUserId(userId);
		if (user != null) {
			return user;
		} else {
			return "user does not exist.";
		}
	}

	public User assignRoleToUser(DtoUser userRoleDto) {
		User user = userRepository.findByUserId(userRoleDto.getUserId());
		Role role = roleRepository.findByRoleType(userRoleDto.getRoleType());
		 

		if ((user != null)&&(user.getUserStatus()==UserStatus.ACTIVE))
		{
			
			
			if (role != null) {
				
				user.getRoles().add(role);
				User tempUser = userRepository.save(user);
				return tempUser;
			} else {
				throw new NullPointerException("User role doesn't exist");
			}
		} else {
			throw new NullPointerException("User is INACTIVE.");
		}
	}

	public String updateUser(@RequestBody User user) {
		 String pass=user.getPassword();
		 java.util.regex.Matcher mtch = pswNamePtrn.matcher(pass);
		 int phoneLength = user.getPhoneNumber().length();
			String phn = user.getPhoneNumber().replaceAll("\\s+", "");
			int l = phn.length();
		 User u=userRepository.findByUserName(user.getuserName());
		 User c=userRepository.findByCountry(user.getCountry());
		 User e=userRepository.findByEmail(user.getEmail());
		 User p=userRepository.findByPhoneNumber(user.getPhoneNumber());
		  if (!(user.getPhoneNumber().length() == 10) || !(user.getPhoneNumber().matches("[0-9]+") || !(phoneLength == l)))
		  {
			  return"please enter valid phone number";
		  }
		
			
			User tempUser = userRepository.findByUserId(user.getUserId());
			if((!(Pattern.compile("^[A-Za-z0-9_-]{1,25}$").matcher(user.getuserName()).matches())))
			{
				return "please enter valid user name";
				
			}
			if(!(userRepository.findByUserName(user.getuserName())==null))
			{
			if(u.getUserId()!=user.getUserId())
			{
				return"user name already exist";
			}
			if((!(Pattern.compile("^[A-Za-z]{2,55}$").matcher(user.getCountry()).matches())))
			{
				return "please enter valid country name";
			}
			}
			
		
			//if(Pattern.compile("^[a-z0-9][a-z0-9(\\-[a-z0-9]+)(\\_[a-z0-9]+)]*[a-zA-Z]+(\\.[a-z0-9]+)*[a-zA-Z]+(\\-[a-z0-9]+)*[a-zA-Z]+(\\_[a-z0-9]+)*[a-zA-Z]*@[a-z0-9]+(\\.[a-z0-9]+)*(\\.[a-z]{2,})$").matcher(user.getEmail()).matches())
			if(!(Pattern.compile("^[a-z0-9][a-z0-9(\\-[a-z0-9]+)(\\_[a-z0-9]+)]*[a-zA-Z0-9]+(\\.[a-z0-9]+)*[a-zA-Z0-9]+(\\-[a-z0-9]+)*[a-zA-Z0-9]+(\\_[a-z0-9]+)*[a-zA-Z0-9]*@[a-z0-9]+(\\.[a-z0-9]+)*(\\.[a-z]{2,})$").matcher(user.getEmail()).matches())) 
			{
	    		
	    		return" please enter valid email";
	    	}
			if(!(userRepository.findByEmail(user.getEmail())==null))
			{
			if(e.getUserId()!=user.getUserId())
			{
				return"email already exist";
			}
			}
			else
			{
				return "please enter email";
			}
			
			  if(!mtch.matches())
		       {
		    	   return "Password Must Contains one Uppercase Alphabet,one lower case ,on dgigt, one special symbol";
		       }
			  if(!(userRepository.findByPhoneNumber(user.getPhoneNumber())==null))
			  if(p.getUserId()!=user.getUserId())
			  {
				  return "phone Number already in use";
			  }
			  
			
			if(tempUser!=null) {
			tempUser.setEmail(user.getEmail());
			tempUser.setuserName(user.getuserName());
			tempUser.setPhoneNumber(user.getPhoneNumber());
			tempUser.setCountry(user.getCountry());
			tempUser.setPassword(user.getPassword());
			userRepository.save(tempUser);
		
		return "User succesfully updated!";
			}
			else
			{
				return "user id is not valid";
			}
		
		
		
		
		
	}
}