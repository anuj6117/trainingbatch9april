package io.oodles.springboot1.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.oodles.springboot1.enums.Status;
import io.oodles.springboot1.enums.WalletType;
import io.oodles.springboot1.model.AssignRole;
import io.oodles.springboot1.model.Role;
import io.oodles.springboot1.model.StoreOTP;
import io.oodles.springboot1.model.Users;
import io.oodles.springboot1.model.Wallet;
import io.oodles.springboot1.repository.RoleRepository;
import io.oodles.springboot1.repository.StoreOTPRepository;
import io.oodles.springboot1.repository.UsersRepository;
import io.oodles.springboot1.repository.WalletRepository;

@Service
public class Signupservice {

	@Autowired
	public UsersRepository usersRepository;
	
	@Autowired
	public RoleRepository rolerepository;

	@Autowired
	public Mail mail;

	@Autowired
	public OTP otpgenerate;

	@Autowired
	OTPService otpService;
	
	WalletType wallettype;
	
	@Autowired
	StoreOTPRepository storeOTPRepository;
	@Autowired
	WalletRepository walletRepository;
	
	Users users;
	Role role=new Role();
	Wallet wallet=new Wallet();
	StoreOTP storeOTP;
	Date date = new Date();
	
	/*public Map<String,Object> addUser(Users user){
		Map<String,Object> result=new HashMap<String,Object>();
		if((usersRepository.findByEmail(user.getEmail()) == null)) {
		if(user.getEmail().equals("")) {
			result.put("isSuccess", false);
			result.put("message", "email id not empty.");
			return result;
		}
		}
		else {
			result.put("isSuccess", false);
			result.put("message", "Email id already exists.");
			return result;
		}
		return result;
	}*/  //Correct
	/*public Map<String,Object> addUser(Users user){
		Map<String,Object> result=new HashMap<String,Object>();
		Users user1=usersRepository.findByEmail(user.getEmail());
		if(user1!=null) {
			result.put("isSuccess", false);
			result.put("message", "email id already exists.");
			return result;
		}
		return result;
	}*/
	

	public String addUser(Users users) {
		//System.out.println("???????????");
		Users user=usersRepository.findByEmail(users.getEmail());
		//System.out.println("???????????");
		Users phone=usersRepository.findByPhoneNumber(users.getPhoneNumber());
		//System.out.println("???????????");
		Users name=usersRepository.findByUserName(users.getUserName());
		//System.out.println("???????????");
		String s=users.getUserName();
		//System.out.println("???????????");
		String mob=users.getPhoneNumber();
		//System.out.println("???????????");
		Users mobpresent=usersRepository.findByPhoneNumber(users.getPhoneNumber());
		//System.out.println("???????????");
		
          
			
		
		//System.out.println(";;;;;;;;;;;");
		Role role1=rolerepository.findByRoleType("USER");
		if(role1==null) {
		if(!role.getRoleType().equalsIgnoreCase("user")) {
			//System.out.println("???????????");
		role.setRoleType("USER");
		//System.out.println("???????????");
		rolerepository.save(role);}}
		//System.out.println("???????????");
			
		if(user!=null) {
			//System.out.println("???????????");
			return "Oops,this Email id is already registered";
			
		}
		else if(phone!=null) {
			//System.out.println("???????????");
			return "phone number already exists";
		}
		else if(name!=null) {
			//System.out.println("???????????");
			return "Username already exists";
		}
		else if(s.length()>25) {
			return "Maximum Characters allowed for this field is 25";
		}
		else if(mob.length()>10){
			return "Please,enter a valid mobile number.";
		}
		else if(mobpresent!=null) {
			return "Oops,this number is already registered";
		}
		else if(!Pattern.matches("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,32}", users.getPassword())) {
			return "Please enter password with minimum 8 characters.You password should have atleast 1 Upper Case, 1 Lower Case, 1 Digit & 1 Special Character.";
		}
		else if(!Pattern.matches("\\d{10}", users.getPhoneNumber())) {
			return "Phone Number incorrect";
		}
		
		else {
		//System.out.println("////////////");

		Random rnd = new Random();
		
		int otp1 = rnd.nextInt(10000);
		
		
        users.setDate(date);
        users.getDate();
        users.setStatus(Status.INACTIVE);
        //System.out.println(":::::::::::::::");
        Users user1=usersRepository.save(users);
        //System.out.println("------------------");
        Set<Wallet> wallet=new HashSet<Wallet>();
        //System.out.println("------------------2");
        Wallet wallet1=new Wallet();
        //System.out.println("------------------3");
        wallet1.setCoinName("INR");
        //System.out.println("------------------4");
        wallet1.setCoinType(wallettype.FIAT);
        //System.out.println("------------------5");
        wallet1.setUsers(user1);
        //System.out.println("------------------6");
        wallet.add(wallet1);
        //System.out.println("------------------7");
        walletRepository.save(wallet1);
        //System.out.println("------------------8");
        users.setWallet(wallet);
        //System.out.println("------------------9");
        List<Role> list=new ArrayList<Role>();
        //System.out.println("------------------10");
        
		list.add(rolerepository.findByRoleType("USER"));
		System.out.println("++++++++++++++++");
	    users.setRoles(list);
	    System.out.println("------------------");
        usersRepository.save(user1);
        
        System.out.println("????????????");
		
		
			otpService.ValueMethod(users, otp1);
			String email=users.getEmail();
			
		    System.out.println("}}}}}}}}}}}}}");
		    
		   
		    
		   
		    
			mail.sendMail(otp1,email);
			otpgenerate.sendSMS(otp1);
			usersRepository.save(users);
			System.out.println("<<<<<<<<<<<<<<<<");
			return "Your account has been successfully created. Please, verify it by using OTP.";}
		}
	
	
	

	public List<Users> getallusers() {
		
		return usersRepository.findAll();
	}

	public String auth(StoreOTP storeotp1) {
		storeOTP=storeOTPRepository.findByTokenOTP(storeotp1.getTokenOTP());
		users=usersRepository.findByEmail(storeotp1.getEmail());
		if(storeOTP!=null) {
			if(storeOTP.getEmail().equals(storeotp1.getEmail())){
				
				storeOTPRepository.delete(storeOTP);
				users.setStatus(Status.ACTIVE);
				usersRepository.save(users);
				return "Success";
			}
			else
				return "Failure";
		}
		else
			return "Try Again";
	
		
		
		
		}

	

	public Optional<Users> searchbyid(int id) {
		// TODO Auto-generated method stub
		return usersRepository.findById(id);
	}

	public String update(Users user) {
		// TODO Auto-generated method stub
		Users user1=usersRepository.findByUserId(user.getUserId());
		System.out.println(":::::::::::::");
		user.setStatus(user1.getStatus());
		System.out.println("11111111111111");
		Date date=new Date();
		System.out.println("222222222222");
		user.setDate(date);
		System.out.println("33333333333333");
		usersRepository.save(user);
		System.out.println("444444444444444444");
		return "User Updated";
	}

	public String delete(int id) {
		// TODO Auto-generated method stub
		 usersRepository.deleteById(id);
		 return "User Deleted";
	}

	public String assign(AssignRole assignrole) {
		// TODO Auto-generated method stub
		users=usersRepository.findByUserId(assignrole.getUserid());
		role=rolerepository.findByRoleType(assignrole.getRoletype());
		users.getRoles().add(role);
		usersRepository.save(users);
		return "Role Assigned";
	}
	
}	
		
	
		
		// TODO Auto-generated method stub
		
	


