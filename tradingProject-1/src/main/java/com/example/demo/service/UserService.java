package com.example.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

import com.example.demo.enums.UserStatus;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.utilities.EmailValidator;
import com.example.demo.utilities.NameValidator;
import com.example.demo.utilities.EmailValidator;
//import com.example.demo.utilities.PasswordValidator;
//import com.example.demo.utilities.PhoneValidator;
//import com.example.demo.utilities.NameValidator;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
//	@Autowired
//	private EmailValidator emailValidator;
	
//	@Autowired
//	private PhoneValidator phoneValidator;
	
//	@Autowired
//	private PasswordValidator passwordValidator;
	
	
	public String  insertUser(User user) {
		
	/*	String userName=user.getUserName();
		
		if(userName.length()==0) {
			return "user name can not be null";
		}
		
		if(userName.startsWith(" ")){
			return "user name should not have leading space";
		}
		if(userName.endsWith(" ")) {
			return "user name should not have trailing space";
		} 
		if(!new NameValidator().checkNameValidation(userName)) {
			return "maximum characters allowed for this field is 25";
		}
		
		if(userRepository.findByEmail(user.getEmail())==null) {
			if (!emailValidator.checkEmail(user.getEmail())) {
				return "Please enetr a valid email address";
			}
		}else {
			return "Oops! this Emailid is already registered";
		}

		String phoneNumber=user.getPhoneNumber();
		if(userRepository.findByPhoneNumber(phoneNumber)==null) {
			if(!phoneValidator.checkPhoneNumber(phoneNumber)) {
				return "Invalid phone number";
			}
		}else {
			return "Oops! this number is already registered";
		}

		String country=user.getCountry();
		if(country.trim().length()<=2) {
			return "Country name must have atleast 2 characters";
		}
		
		String password=user.getPassword();
		if(!passwordValidator.isValid(password)) {
			return "Please provide valid password";
		}

*/		user.setStatus(UserStatus.INACTIVE);
		user.setDate(new Date().toString());
//		Wallet wallet=new Wallet(CoinType.FIAT);
//		wallet.setCoinName("INR");
//		wallet.setUser(user);
//		wallet.setBalance(0.0);
//		wallet.setShadowBalance(0.0);
		
		
//		Role role =null;
//		if((role = roleRepository.findOneByRoleType("User"))==null) {
//			role = new Role();
//			role.setRoleType("User");
//		}
		
	userRepository.save(user);	
	return "successfully inserted";
 }
	
	
	public List<User> getallUsers() {
	List<User> users=	userRepository.findAll();
	return users;
	}
	
	


	public User getSingleUser(Integer id) {
		return userRepository.findOneById(id);
	}
	
	
	//updating a user
	public String updateUser(User updateduser) {
//apply basic validation that have applied during insertion 		
		if((userRepository.findOneById(updateduser.getId()))!=null){
			User user=userRepository.findOneById(updateduser.getId());
			
			String userName=updateduser.getUserName();
			System.out.print("jsjfksj ----------------------------------------jdsfjskjfkldjflldsj=---------sdfsdfsd");
			System.out.println(updateduser.getEmail());
	       if(userName.length()==0) {
	    	   return "name can not be blank";
	       }
	       if(userName.startsWith(" ")) {
	    	   return "name can not have leading space";
	       }
	       if(userName.endsWith(" ")) {
	    	   return "name can not have trailing space";
	       }
	       if(!new NameValidator().checkNameValidation(userName)) {
				return "maximum characters allowed for this field is 25";
			}
			if((userRepository.findByEmail(updateduser.getEmail())) != null) {
				return "Oops! this Emailid is already registered";
			}
			if(!new EmailValidator().checkEmail(updateduser.getEmail())) {
				return "Enter valid email";
			}
			updateduser.setStatus(user.getStatus());
			updateduser.setDate(user.getDate());
			userRepository.save(updateduser);
		}else {
			
		}
		return "succesfully updated";
		
	}
	
	
	public String deleteUser(Integer id) {
		if(userRepository.findOneById(id)!=null) {
			userRepository.deleteById(id);
			return "deleted succesfully";
		}else {
			return "Record does not exist";
		}
	}
}
