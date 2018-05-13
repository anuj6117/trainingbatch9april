package com.example.demo.service;


import com.example.demo.combinedfields.UserRole;
import com.example.demo.enums.CoinType;
import com.example.demo.model.Wallet;
import com.example.demo.repository.RoleRepository;
import com.example.demo.utilities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.regex.Pattern;

import com.example.demo.enums.UserStatus;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.utilities.EmailValidator;
//import com.example.demo.utilities.PasswordValidator;
//import com.example.demo.utilities.PhoneValidator;
//import com.example.demo.utilities.NameValidator;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	RoleService roleService;
//	@Autowired
//	private EmailValidator emailValidator;
	
//	@Autowired
//	private PhoneValidator phoneValidator;
	
//	@Autowired
//	private PasswordValidator passwordValidator;
	
	
	public String  insertUser(User user) {
		
		String userName=user.getUserName();
		
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

		if(user.getEmail().length()==0){
			return "password field can not be empty";
		}
		if(userRepository.findOneByEmail(user.getEmail())==null) {
			if (!new EmailValidator().checkEmail(user.getEmail())) {
				return "Please enetr a valid email address";
			}
		}else {
			return "Oops! this Emailid is already registered";
		}

		String phoneNumber=user.getPhoneNumber();
		if(phoneNumber.length()==0){
			return "please enter any valid phonenumer";
		}
		System.out.println("here i am ======================================================");

		if(userRepository.findByPhoneNumber(phoneNumber)==null) {
			if(!new PhoneValidator().checkPhoneNumber(phoneNumber)) {
				return "Invalid phone number";
			}
		}else {
			return "Oops! this number is already registered";
		}

		System.out.println("after phone number==============================================");

		String country=user.getCountry();
		if(country.length()==0){
			return "country can not be blank";
		}

		if(!(Pattern.compile("^[A-Za-z]{2,25}$").matcher(user.getCountry()).matches()))
		{
		return "country name is not valid";
		}


		String password=user.getPassword();
		if(password.length()==0){
			return "password field can not be empty";
		}
		if(! new PasswordValidator().isValid(password)) {
			return "Please provide valid password";
		}

		user.setStatus(UserStatus.INACTIVE);
		user.setDate(new Date().toString());

		//adding roles to user
		Role role=roleRepository.findOneByRoleType("User");
		if(role==null){
			{
				role=new Role();
				role.setRoleType("User");
			}
		}else{
			user.getRole().add(role);
		}


		//adding wallet to user

		Wallet wallet =new Wallet(CoinType.FIAT);
		wallet.setCoinName("INR");
		wallet.setUser(user);
		user.getWallets().add(wallet);



		System.out.println("before saving========================================================");
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
			if((userRepository.findOneByEmail(updateduser.getEmail())) != null) {
				return "Oops! this Emailid is already registered";
			}
			if(!new EmailValidator().checkEmail(updateduser.getEmail())) {
				return "Enter valid email";
			}
			updateduser.setStatus(user.getStatus());
			updateduser.setDate(user.getDate());
			userRepository.save(updateduser);
		}else {
			return "id does not exist to update";
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

	public String assignRole(UserRole userRole){
		User user=userRepository.findOneById(userRole.getUserId());
		if(user!=null){
			/*Set<Role> roles=user.getRole();
			for(Role role: roles){
				if(!role.getRoleType().equals(userRole.getRoleType())){
					Role roleFromRoleTable=roleRepository.findOneByRoleType(userRole.getRoleType());
					if(roleFromRoleTable==null){
					Role rolerepnse=roleService.createRole(userRole.getRoleType());

					}
					else{
					roles.add(roleFromRoleTable);
					return "role assigned successfully";
				}else{
					return "role already exists";
				}*/
			Role role=roleRepository.findOneByRoleType(userRole.getRoleType());
			if(role==null){
				role=roleService.createRole(userRole.getRoleType());
			}
Set<Role> roleassigned=user.getRole();
			for(Role roles:roleassigned){
				if(!roles.getRoleType().equals(role.getRoleType())){
				}else{
					return "role already assigned";
				}
			}
			roleassigned.add(role);
			userRepository.save(user);
			return "role assigned successfully";
		}
			return "user does not exist for  userid";
	}
}
