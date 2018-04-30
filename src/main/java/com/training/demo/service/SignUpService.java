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

import com.training.demo.dto.DtoUser;
import com.training.demo.enums.RoleType;
import com.training.demo.enums.UserStatus;
import com.training.demo.enums.WalletType;
import com.training.demo.model.OtpVerification;
import com.training.demo.model.Role;
import com.training.demo.model.User;
import com.training.demo.model.Wallet;
import com.training.demo.repository.UserRepository;
import com.training.demo.repository.WalletRepository;
import com.training.demo.repository.OtpRepository;
import com.training.demo.repository.RoleRepository;

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

	public String addUser(User user) {
		Role roles = roleRepository.findByRoleId(1);
		HashSet<Role> hashSet = new HashSet();
		boolean b = hashSet.add(roles);
		boolean flag = userRepository.existsByEmail(user.getEmail());
		boolean phone=userRepository.existsByPhoneNo(user.getPhoneNo());
		// )&&(userRepository.existByPhoneNo(user.getPhoneNo())));
		if (flag == false) {
			if(phone==false)
			{
				
				
			}
			else
			{
				return " oops this number is already exist";
			}
			String email = user.getEmail();
			String phoneNo = user.getPhoneNo();
			System.out.println(email);

			System.out.println("service hit");
			Random random = new Random();
			int otp = random.nextInt(5255);
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
			//userwallet.setCoinName("inr");
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
				otpVerification.setOtp(otp);
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
		String t_email = otpVerification.getEmail();
		Integer t_otp = otpVerification.getOtp();
		try {

			OtpVerification tempOtpVerification = otpRepository.findByEmail(email);
			String v_email = tempOtpVerification.getEmail();
			int v_otp = tempOtpVerification.getOtp();
			User t_user = userRepository.findByEmail(email);

			if (t_email.equals(v_email)) {
				System.out.println("email is successfully verified" + t_email);
				if (t_otp.equals(v_otp)) {
					System.out.println(t_otp + " otp is successfully verified" + t_otp);

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

	public Optional<User> getUserById(Integer userId) {
		Optional<User> usrid = userRepository.findById(userId);
		if (usrid != null) {
			return usrid;
		} else {
			throw new NullPointerException("Id does not exist.");
		}
	}

	public User assignRoleToUser(DtoUser userRoleDto) {
		User user = userRepository.findByUserId(userRoleDto.getUserId());
		Role role = roleRepository.findByRoleType(userRoleDto.getRoleType());

		if (user != null) {
			if (role != null) {
				
				user.getRoles().add(role);
				User tempUser = userRepository.save(user);
				return tempUser;
			} else {
				throw new NullPointerException("User role doesn't exist");
			}
		} else {
			throw new NullPointerException("User id does not exist.");
		}
	}

	public String updateUser(@RequestBody User user) {
		try {
			User tempUser = userRepository.findByUserId(user.getUserId());
			tempUser.setEmail(user.getEmail());
			tempUser.setuserName(user.getuserName());
			tempUser.setPhoneNo(user.getPhoneNo());
			tempUser.setCountry(user.getCountry());
			tempUser.setPassword(user.getPassword());
			userRepository.save(tempUser);
		} catch (Exception ex) {
			return "Error while updating the user: " + ex.toString();
		}
		return "User succesfully updated!";
	}
}