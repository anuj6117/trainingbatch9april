package com.training.demo.service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
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
import com.training.demo.utility.EmailValidation;
import com.training.demo.utility.PhoneValidation;

@Service
public class SignUpService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private OtpRepository otpRepository;

	@Autowired
	private RoleRepository roleRepository;

	private OtpVerification otpVerification;

	EmailValidation emailValidation = new EmailValidation();

	public String addUser(User user) {

		String userName = user.getUserName();
		System.out.println(userName + "======================================");
		String password = user.getPassword();

		String tempEmail = user.getEmail();
		String countryName = user.getCountry();

		boolean validateEmail = emailValidation.validateEmail(tempEmail);

		if (!validateEmail) {
			return "Please enter a valid email address.";
		}

		if (!(password.matches("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-<>~`)(_+=}[{]':;/]).{8,}$"))) {
			return "Please enter password with minimum 8 characters. Your password should have atleast 1 Uppercase, 1 Lowercase, 1 Digit & 1 Special character. Space is not allowed";
		}

		if (countryName.equals("") || countryName.isEmpty() || countryName == null
				|| countryName.trim().length() == 0) {
			return "Country Name can not be null or empty.";
		}

		if (!(countryName.matches("^[a-zA-Z]{2,}$"))) {
			return "country name can not be null or its length should be more than 2 characters.";
		}

		if (!(userName.matches("^([a-zA-Z0-9]{2,}\\s[a-zA-z0-9]{1,}'?-?[a-zA-Z0-9]{2,}\\s?([a-zA-Z0-9]{1,})?)"))) {
			return "Maximun charaters allowed for userName field is 6 to 25 and User name can not contain any special character.";
		}

		if (userName.equals("") || userName.isEmpty() || userName == null || userName.trim().length() == 0) {
			return "User Name can not be null or empty.";
		}

		// PHONE NUMBER VALIDATION
		String tempPhoneNo = user.getPhoneNumber();
		if (!PhoneValidation.isValid(tempPhoneNo)) {
			return "Please enter a valid mobile number.";
		}

		if ((userRepository.findByEmail(user.getEmail()) == null)) {
			// boolean emailFlag = userRepository.existsByEmail(user.getEmail());
			boolean phoneNoFlag = userRepository.existsByPhoneNumber(user.getPhoneNumber());
			if (phoneNoFlag == false) {
				System.out.println("service hit");
				Random random = new Random();
				int tokenOTP = random.nextInt(99777) + 1432;

				user.setDate(new Date());

				System.out.println("service hit before if");
				user.setUserStatus(UserStatus.INACTIVE);

				System.out.println("Default roleType assigned to user = ");
				// Default Role Creation

				Role role = null;
				if ((role = roleRepository.findByRoleType("user")) == null) {
					role = new Role();
					role.setRoleType("USER");
				}
				user.getRoles().add(role);

				/// Default Wallet Creation
				Wallet wallet = new Wallet();
				wallet.setCoinType(WalletType.FIAT);
				wallet.setUser(user);
				wallet.setBalance(0.0);
				wallet.setShadowBalance(0.0);
				wallet.setCoinName("");
				Set<Wallet> walletHashSet = new HashSet<Wallet>();
				walletHashSet.add(wallet);
				user.setWallets(walletHashSet);

				User existingUser = userRepository.save(user);
				if (existingUser != null) {
					String email = user.getEmail();
					String phoneNo = user.getPhoneNumber();
					System.out.println("service hit inside if.");
					// otpService.sendSms(tokenOTP, phoneNo);
					try {
						// emailService.sendEmail(tokenOTP, email);
					} catch (Exception e) {
						System.out.println("email could not be verified as : " + e.getMessage());
					}
					otpVerification = new OtpVerification();
					otpVerification.setUserId(user.getUserId());
					otpVerification.setTokenOTP(tokenOTP);
					otpVerification.setEmail(user.getEmail());
					otpVerification.setDate(new Date());
					otpRepository.save(otpVerification);
					return "Your account has been successfully created. Please, verify it by using OTP.";
				}
			} else {
				System.out.println("Invalid Username.");
				return "already existing phone number.";
			}
		}

		System.out.println("Already existing Email or Username.");
		return "Already existing Email.";
	}

	public String verifyUserWithOtp(String email, Integer tokenOTP) {
		OtpVerification tempOtpVerification;
		User t_user;

		String tempTokenOTP = tokenOTP.toString();
		if (tokenOTP == null || tempTokenOTP.length() < 4) {
			return "invalid otp";
		}
		try {
			tempOtpVerification = otpRepository.findByEmail(email);
			t_user = userRepository.findByEmail(email);
			String v_email = tempOtpVerification.getEmail();
			int v_otp = tempOtpVerification.getTokenOTP();

			if (email.equals(v_email)) {
				System.out.println("email is successfully verified : " + email);
				System.out.println("..........---------Token otp >>>>>> " + tempOtpVerification.getTokenOTP());
				if (tokenOTP.equals(v_otp)) {
					System.out.println(tokenOTP + "< token otp is successfully verified with table otp > " + v_otp);
				} else {
					return "invalid otp";
				}
				otpRepository.delete(tempOtpVerification);
				t_user.setUserStatus(UserStatus.ACTIVE);
				userRepository.save(t_user);
				System.out.println("otpVerification table deleted.");
				return "Your account is verified successfully.";
			} else {
				System.out.println("Sorry, invalid username or otp");
				return "Invalid email";
			}
		} catch (Exception e) {
			return "invalid email.";
		}

	}

	public String updateUser(@RequestBody User user) {
		String userName = user.getUserName();
		System.out.println(userName + "======================================");
		String password = user.getPassword();
		String tempEmail = user.getEmail();
		String countryName = user.getCountry();

		boolean validateEmail = emailValidation.validateEmail(tempEmail);

		if (!validateEmail) {
			return "Please enter a valid email address.";
		}

		if (!(password.matches("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-<>~`)(_+=}[{]':;/]).{8,}$"))) {
			return "Please enter password with minimum 8 characters. Your password should have atleast 1 Uppercase, 1 Lowercase, 1 Digit & 1 Special character. Space is not allowed";
		}

		if (countryName.equals("") || countryName.isEmpty() || countryName == null
				|| countryName.trim().length() == 0) {
			return "Country Name can not be null or empty.";
		}

		if (!(countryName.matches("^[a-zA-Z]{2,}$"))) {
			return "country name can not be null or its length should be more than 2 characters.";
		}

		if (!(userName.matches("^([a-zA-Z0-9]{2,}\\s[a-zA-z0-9]{1,}'?-?[a-zA-Z0-9]{2,}\\s?([a-zA-Z0-9]{1,})?)"))) {
			return "Maximun charaters allowed for userName field is 6 to 25 and User name can not contain any special character.";
		}

		if (userName.equals("") || userName.isEmpty() || userName == null || userName.trim().length() == 0) {
			return "User Name can not be null or empty.";
		}

		String tempPhoneNo = user.getPhoneNumber();
		if (!PhoneValidation.isValid(tempPhoneNo)) {
			return "Please enter a valid mobile number.";
		}

		try {
			User tempUser = userRepository.findByUserId(user.getUserId());
			if (tempUser.getUserStatus().equals(UserStatus.ACTIVE)) {
				tempUser.setEmail(user.getEmail());
				tempUser.setUserName(user.getUserName());
				tempUser.setPhoneNumber(user.getPhoneNumber());
				tempUser.setCountry(user.getCountry());
				tempUser.setPassword(user.getPassword());
				userRepository.save(tempUser);
			} else {
				return "please activate your account first.";
			}
		} catch (Exception ex) {
			return "user does not exist";
		}

		return "User succesfully updated!";
	}

	public Object getAllUsers() {
		List<User> userList = userRepository.findAll();
		if (userList.isEmpty()) {
			return "There are no existing users.";
		}
		return userList;
	}

	public Object getUserById(Integer userId) {
		User user = null;
		if (((user = userRepository.findByUserId(userId)) != null)) {
			return user;
		} else {
			return "invalid user id.";
		}
	}

	public String assignRoleToUser(UserRoleDto userRoleDto) {
		User user = null;
		Role role = null;

		if ((user = userRepository.findByUserId(userRoleDto.getUserId())) == null) {
			return "invalid user id.";
		}

		if ((role = roleRepository.findByRoleType(userRoleDto.getRoleType())) == null) {
			return "invalid role type.";
		}

		user.getRoles().add(role);
		userRepository.save(user);
		return "Role is successfully assigned to the given user.";

	}

}