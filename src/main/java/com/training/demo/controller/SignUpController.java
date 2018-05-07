package com.training.demo.controller;

import java.util.List;
import java.util.regex.*;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.training.demo.dto.DtoUser;
import com.training.demo.model.OtpVerification;
import com.training.demo.model.User;
import com.training.demo.repository.UserRepository;
import com.training.demo.service.SignUpService;

@RestController
public class SignUpController {
	@Autowired
	private SignUpService signUpService;

	@Autowired
	private UserRepository userRepository;

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String insertUser(@RequestBody User user) {
		String password = user.getPassword().trim();
		String username = user.getuserName();
		String username1 = user.getuserName().trim();
		String countryname=user.getCountry();
		String countryname1=user.getCountry().trim();
		int countrylength=countryname.length();
		int countrylength1=countryname1.length();

		int length = password.length();
		int unamelength = username.length();
		int uname1length = username1.length();
		int phoneLength = user.getPhoneNo().length();
		String phn = user.getPhoneNo().replaceAll("\\s+", "");
		int l = phn.length();
		if (user.getPhoneNo().length() == 10 && (user.getPhoneNo().matches("[0-9]+") && (phoneLength == l))) {
			if ((unamelength != 0) && (unamelength == uname1length) && (user.getuserName() != null)) {
				if (username.length() <= 25) {

					if ((user.getCountry()!= null)&&(user.getCountry().length()!=0)&&(countrylength==countrylength1)) {

						if (length != 0) {
							String newUser = signUpService.addUser(user);
							if (newUser != null) {
								return newUser;
							} else
								return "please enter valid entries";
						} else
							return "password can not be null";

					} else
						return "country can't be null";
				} else
					return "maximum charector for user name is 25";
			} else
				return "user name can't be null or cannot contain inappropriate spaces";
		} else
			return "phone number should be length of 10 and  should contain numeric only";
	}

	@RequestMapping(value = "/verify", method = RequestMethod.POST)
	public String userVerification(@RequestBody OtpVerification otpVerification) {
		if ((otpVerification.getEmail() == null) || (otpVerification.getTokenOTP() == null)) {
			return "otp not found";
		} else {
			return signUpService.verifyUserWithOtp(otpVerification.getEmail(), otpVerification.getTokenOTP());

		}
	}

	@RequestMapping(value = "/getallusers", method = RequestMethod.GET)
	public List<User> getAll() {
		return signUpService.getAllUsers();
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String updateUser(@RequestBody User user) {
		return signUpService.updateUser(user);
	}

	@RequestMapping(value = "/assignrole", method = RequestMethod.POST)
	public String assignRole(@RequestBody DtoUser userdto) {
		User user = null;
		try {
			user = signUpService.assignRoleToUser(userdto);
		} catch (Exception e) {
			return "Role Cannot be assigned as : " + e.getMessage();
		}
		return "Role Is Successfully Assigned.";
	}

	@RequestMapping(value = "/getbyuserid", method = RequestMethod.GET)
	public Optional<User> getUserById(@RequestParam("userId") Integer userId) {
		Optional<User> obj = null;
		try {
			obj = signUpService.getUserById(userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

	@RequestMapping(value = "/deleteuser", method = RequestMethod.GET)
	public String deleteUser(@RequestParam("userId") Integer id) {
		try {
			User t_user = userRepository.findByUserId(id);
			userRepository.delete(t_user);
			return "user deleted sucessfully";
		} catch (Exception e) {
			return "fail";
		}
	}
}