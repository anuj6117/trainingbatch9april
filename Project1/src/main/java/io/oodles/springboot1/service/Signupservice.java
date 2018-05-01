package io.oodles.springboot1.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

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

	public String addUser(Users users) {
		

		Random rnd = new Random();
		
		int otp1 = rnd.nextInt(10000);
		
		
        users.setDate(date);
        users.getDate();
        users.setStatus(Status.INACTIVE);
        Users user1=usersRepository.save(users);
        Set<Wallet> wallet=new HashSet<Wallet>();
        Wallet wallet1=new Wallet();
        wallet1.setCoinName("INR");
        wallet1.setCoinType(wallettype.FIAT);
        wallet1.setUsers(user1);
        wallet.add(wallet1);
        walletRepository.save(wallet1);
        users.setWallet(wallet);
        List<Role> list=new ArrayList<Role>();
		list.add(rolerepository.findByRoleType("USER"));
	    users.setRoles(list);
        usersRepository.save(user1);
		
		
			otpService.ValueMethod(users, otp1);
			String email=users.getEmail();
			
		    
		    
		   
		    
		   
		    
			mail.sendMail(otp1,email);
			otpgenerate.sendSMS(otp1);
			usersRepository.save(users);
			
			return "Your account has been successfully created. Please, verify it by using OTP.";
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

	public String update(Users users) {
		// TODO Auto-generated method stub
		Users user=usersRepository.findByUserId(users.getUserId());
		users.setDate(date);
		if(user.getStatus()==Status.INACTIVE) {
		
		users.setStatus(Status.INACTIVE);}
		else
		{
			users.setStatus(Status.ACTIVE);
		}
		usersRepository.save(users);
		return "User Updated";
	}

	public void delete(int id) {
		// TODO Auto-generated method stub
		 usersRepository.deleteById(id);;
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
		
	


