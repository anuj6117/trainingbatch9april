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
	
	
	public Map<String,Object> update(Users users) {
		Map<String,Object> result=new HashMap<String,Object>();
		
		Users user=usersRepository.findByEmail(users.getEmail());
		Users phone=usersRepository.findByPhoneNumber(users.getPhoneNumber());
		Users name=usersRepository.findByUserName(users.getUserName());
		if(user!=null )  {
			result.put("isSuccess", false);
			result.put("message","Email Id already present.");
			result.put("timestamp",date );
			return result;
		}
		 if(phone!=null) {
				result.put("isSuccess", false);
				result.put("message","phone number already exists");
				result.put("timestamp",date );
				return result;
				
			}
			 if(name!=null) {
				result.put("isSuccess", false);
				result.put("message","Username already exists");
				result.put("timestamp",date );
				return result;
				
			}
		
		String s=users.getUserName();
		String mob=users.getPhoneNumber();
		
		// TODO Auto-generated method stub
		System.out.println("?????????????");
		
		System.out.println("?????????????");
		if(users.getCountry().length()==0||users.getEmail().length()==0||users.getPassword().length()==0||users.getPhoneNumber().length()==0||
				users.getUserId().toString().length()==0||users.getUserName().length()==0) {
			System.out.println("?????????????1111111111");
			result.put("isSuccess", false);
			result.put("message","Field cannot be blank");
			result.put("timestamp",date );
			return result;
		}
		
		if(!Pattern.matches("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%!&*<?>/]).{8,32}", users.getPassword())) {
			result.put("isSuccess", false);
			result.put("message","Please enter password with minimum 8 characters.You password should have atleast 1 Upper Case, 1 Lower Case, 1 Digit & 1 Special Character.");
			result.put("timestamp",date );
			return result;

			
		}
		if(!Pattern.matches("\\d{10}", users.getPhoneNumber())) {
			result.put("isSuccess", false);
			result.put("message","Phone Number incorrect");
			result.put("timestamp",date );
			return result;

			
		}
		if(!Pattern.matches("^[a-zA-Z0-9_-]{1,25}$", users.getUserName())) {
			result.put("isSuccess", false);
			result.put("message","Username format wrong.");
			result.put("timestamp",date );
			return result;
		}
		if(!Pattern.matches("^[a-z0-9][a-z0-9(\\-[a-z0-9]+)(\\_[a-z0-9]+)]*[a-zA-Z0-9]+(\\.[a-z0-9]+)*[a-zA-Z0-9]+(\\-[a-z0-9]+)*[a-zA-Z0-9]+(\\_[a-z0-9]+)*[a-zA-Z0-9]*@[a-z0-9]+(\\.[a-z0-9]+)*(\\.[a-z]{2,})$", users.getEmail())) {
			result.put("isSuccess", false);
			result.put("message","Email format wrong.");
			result.put("timestamp",date );
			return result;
		}
		if(s.length()>25) {
			result.put("isSuccess", false);
			result.put("message","Maximum Characters allowed for this field is 25");
			result.put("timestamp",date );
			return result;

			
		}
		 if(mob.length()>10){
			result.put("isSuccess", false);
			result.put("message","Please,enter a valid mobile number.");
			result.put("timestamp",date );
			return result;

			
		}
		 if(users.getCountry().length()<2){
				result.put("isSuccess", false);
				result.put("message","Minimum 2 characters required");
				result.put("timestamp",date );
				return result;
				
			}
		
	   /* Users useremail=usersRepository.findByUserIdAndEmail(user.getUserId(),user.getEmail());
	    System.out.println("?????????????");
	    Users usersusername=usersRepository.findByUserIdAndUserName(user.getUserId(),user.getUserName());
	    System.out.println("?????????????");
	    Users usersphone=usersRepository.findByUserIdAndPhoneNumber(user.getUserId(),user.getPhoneNumber());
	    System.out.println("?????????????");
	    if(user.getEmail().equalsIgnoreCase(useremail.getEmail())){||user.getUserName().equalsIgnoreCase(usersusername.getUserName())||user.getPhoneNumber().equalsIgnoreCase(usersphone.getUserName())) {*/
	    	System.out.println("?????????????");
	    	Users user1=usersRepository.findByUserId(user.getUserId());
	    	System.out.println("?????????????");
		users.setStatus(user1.getStatus());
		System.out.println("?????????????");
		Date date=new Date();
		System.out.println("?????????????");
	    users.setCreatedOn(date);
	    System.out.println("?????????????");
		usersRepository.save(users);
		System.out.println("?????????????");
		result.put("isSuccess", true);
		result.put("message","Success");
		result.put("timestamp",date );
		return result;}/*else {
	    result.put("isSuccess", false);
		result.put("message","Cannot update");
		result.put("timestamp",date );
		return result;}
	    */
		
	

	public Map<String,Object> delete(int id) {
		// TODO Auto-generated method stub
		Map<String,Object> result=new HashMap<String,Object>();
		Users deleted=usersRepository.findByUserId(id);
		if(deleted!=null) {
		 usersRepository.deleteById(id);
		 result.put("isSuccess", true);
			result.put("message","Success");
			result.put("timestamp",date );
			return result;}
		result.put("isSuccess", false);
		result.put("message","Id does not exist");
		result.put("timestamp",date );
		return result;
	}
	

	public Map<String,Object> addUser(Users users) {
		Map<String,Object> result=new HashMap<String,Object>();
		Date date=new Date();
		Long count=usersRepository.count();
		Users user=usersRepository.findByEmail(users.getEmail());
		Users phone=usersRepository.findByPhoneNumber(users.getPhoneNumber());
		Users name=usersRepository.findByUserName(users.getUserName());
		String s=users.getUserName();
		String mob=users.getPhoneNumber();
		Users mobpresent=usersRepository.findByPhoneNumber(users.getPhoneNumber());
		if(count==0) {
			role.setRoleType("USER");
			rolerepository.save(role);
		}
		
		
		if(user!=null) {
			result.put("isSuccess", false);
			result.put("message","Oops,this Email id is already registered");
			result.put("timestamp",date );
			return result;
			
			}
	 if(phone!=null) {
			result.put("isSuccess", false);
			result.put("message","phone number already exists");
			result.put("timestamp",date );
			return result;
			
		}
		 if(name!=null) {
			result.put("isSuccess", false);
			result.put("message","Username already exists");
			result.put("timestamp",date );
			return result;
			
		}
	 if(s.length()>25) {
			result.put("isSuccess", false);
			result.put("message","Maximum Characters allowed for this field is 25");
			result.put("timestamp",date );
			return result;

			
		}
		 if(mob.length()>10){
			result.put("isSuccess", false);
			result.put("message","Please,enter a valid mobile number.");
			result.put("timestamp",date );
			return result;

			
		}
		 if(mobpresent!=null) {
			result.put("isSuccess", false);
			result.put("message","Oops,this number is already registered");
			result.put("timestamp",date );
			return result;

			
		}
		 if(!Pattern.matches("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%!&*<?>/]).{8,32}", users.getPassword())) {
			result.put("isSuccess", false);
			result.put("message","Please enter password with minimum 8 characters.You password should have atleast 1 Upper Case, 1 Lower Case, 1 Digit & 1 Special Character.");
			result.put("timestamp",date );
			return result;

			
		}
		 if(!Pattern.matches("\\d{10}", users.getPhoneNumber())) {
			result.put("isSuccess", false);
			result.put("message","Phone Number incorrect");
			result.put("timestamp",date );
			return result;

			
		} if(users.getCountry().length()<2){
			result.put("isSuccess", false);
			result.put("message","Minimum 2 characters required");
			result.put("timestamp",date );
			return result;
			
		}
		if(!Pattern.matches("^[a-zA-Z0-9_-]{1,25}$", users.getUserName())) {
			result.put("isSuccess", false);
			result.put("message","Username format wrong.");
			result.put("timestamp",date );
			return result;
		}
		if(!Pattern.matches("^[a-z0-9][a-z0-9(\\-[a-z0-9]+)(\\_[a-z0-9]+)]*[a-zA-Z0-9]+(\\.[a-z0-9]+)*[a-zA-Z0-9]+(\\-[a-z0-9]+)*[a-zA-Z0-9]+(\\_[a-z0-9]+)*[a-zA-Z0-9]*@[a-z0-9]+(\\.[a-z0-9]+)*(\\.[a-z]{2,})$", users.getEmail())) {
			result.put("isSuccess", false);
			result.put("message","Email format wrong.");
			result.put("timestamp",date );
			return result;
		}
		else {
			
			
		Random rnd = new Random();
		int otp1 = rnd.nextInt(10000)+1000;
	    users.setCreatedOn((date));
        users.getCreatedOn();
        users.setStatus(Status.INACTIVE);
        Users user1=usersRepository.save(users);
        Set<Wallet> wallet=new HashSet<Wallet>();
        Wallet wallet1=new Wallet();
        wallet1.setCoinName("INR");
        wallet1.setCoinType(wallettype.FIAT);
        wallet1.setUsers(user1);
        wallet.add(wallet1);
        walletRepository.save(wallet1);
        users.setUserWallet(wallet);
        List<Role> list=new ArrayList<Role>();
        list.add(rolerepository.findByRoleType("USER"));
		users.setRoleType(list);
	   usersRepository.save(user1);
        
            otpService.ValueMethod(users, otp1);
			String email=users.getEmail();
			
		    
		    mail.sendMail(otp1,email);
			otpgenerate.sendSMS(otp1);
			usersRepository.save(users);
			result.put("isSuccess", true);
			result.put("message","Your account has been successfully created. Please, verify it by using OTP.");
			result.put("timestamp",date );
			return result;
			}
		
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

	

	public Users searchbyid(int id) {
		// TODO Auto-generated method stub
		Users user=usersRepository.findByUserId(id);
		if(user!=null) {
			return user;
		}
		else {throw new NullPointerException("User id does not active");}
	}

	


	public String assign(AssignRole assignrole) {
		
		// TODO Auto-generated method stub
		users=usersRepository.findByUserId(assignrole.getUserId());
		if(users==null) {
			return "UserId invalid.";		}
		role=rolerepository.findByRoleType(assignrole.getRoleType());
		List<Role> list1=users.getRoleType();
		for(Role r:list1) {
			if(r.getRoleType().equalsIgnoreCase(assignrole.getRoleType())) {
				return "Role already Assigned";
			}
		}
		if(role!=null) {
			
		users.getRoleType().add(role);
		usersRepository.save(users);
		return "Role Assigned";}
		else return "Role not present";
	}
	
	
}	
		
	
		
		// TODO Auto-generated method stub
		
	


