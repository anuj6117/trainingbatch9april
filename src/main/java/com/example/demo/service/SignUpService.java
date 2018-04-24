package com.example.demo.service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.enums.Status;
import com.example.demo.enums.WalletEnum;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.model.VerifyOtp;
import com.example.demo.model.Wallet;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.VerifyOtpRepository;
import com.example.demo.repository.WalletRepository;

@Service
public class SignUpService {
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

	private VerifyOtp verifyOtp = new VerifyOtp();

	private Integer otp;

	public String addUser(User user) {
		if ((userRepository.findByEmail(user.getEmail()) == null)) {
			Random randomNumber = new Random();
			otp = randomNumber.nextInt(10000);
			

			Role role = null;
			if((role = roleRepository.findByRoleType("user")) == null) {
				role = new Role();
				role.setRoleType("user");
			}
			
			User newUser = new User(user);
			newUser.getRoles().add(role);
			newUser.setStatus(Status.INACTIVE);
			System.out.println(newUser.getRoles());
			Wallet wallet=new Wallet();
			wallet.setWalletType(WalletEnum.FIAT);
			wallet.setUser(user);
			wallet.setBalance(0.0);
			wallet.setShadowBalance(0.0);
			wallet.setUser(newUser);
			newUser.getWallets().add(wallet);

			if ((userRepository.save(newUser) != null)) {
				// otpService.sendSms(otp);
				// mailService.sendMail(otp, user.getEmail());

				verifyOtp.setId(user.getUserId());
				verifyOtp.setTokenOtp(otp);
				verifyOtp.setEmailId(user.getEmail());
				verifyOtp.getEmailId();
				verifyOtp.setDate(new Date());

				verifyOtpRepository.save(verifyOtp);

				return "Successfully sent";
			} else {
				return "Failure";
			}
		} else {
			return "Already existing user";
		}
	}

	public String verifyUserWithOtp(String emailId, Integer otp) {
		
		VerifyOtp vOtp = verifyOtpRepository.findByEmailId(emailId);
		if (vOtp == null) {
			return "email not exist";
		}
		String v_email = vOtp.getEmailId();
		System.out.println("v_email " + v_email);
		Integer v_otp = vOtp.getTokenOtp();
		System.out.println("v_otp " + v_otp);

		if (otp.equals(v_otp)) {
			System.out.println(otp + " otp is successfully verified" + otp);

			User user = userRepository.findByEmail(emailId);
			// System.out.println(userRepository.findByEmail(emailId));
			user.setStatus(Status.ACTIVE);
			// verifyOtpRepository.delete(verifyOtp);
			verifyOtpRepository.deleteById(vOtp.getId());
			System.out.println("otpVerification table deleted.");
		} else {
			System.out.println("Sorry, invalid username or otp");
		}
		return "success";
	}

	public Optional<User> getuserById(Integer id) {

		Optional<User> usrid = userRepository.findById(id);
		if (usrid != null) {
			return usrid;
		} else {
			throw new NullPointerException("Id doen't be exist");
		}
	}

	public List<User> getAllUsers() {
		return userRepository.findAll();

	}

	public User update(User user) {

		User userdb = null;
		userdb = userRepository.findOneByUserId(user.getUserId());
		userdb.setUserName(user.getUserName());
		// userdb.setEmail(user.getEmail());
		// userdb.setPhoneNumber(user.getPhoneNumber());
		userdb.setCountry(user.getCountry());
		// userdb.setPassword(user.getPassword());

		return userRepository.save(userdb);
	}

	public void delete(Integer id) {

		userRepository.deleteById(id);
	}
}
