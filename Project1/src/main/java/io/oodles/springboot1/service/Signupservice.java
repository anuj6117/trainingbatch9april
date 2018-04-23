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
	StoreOTP storeOTP=new StoreOTP();
	Users users=new Users();
	Role role=new Role();
	Wallet wallet=new Wallet();

	public void addUser(Users users) {

		Random rnd = new Random();
		Date date = new Date();
		int otp1 = rnd.nextInt(10000);
		
		
        users.setDate(date);
        users.getDate();
        users.setStatus(Status.INACTIVE);
        Users user1=usersRepository.save(users);
        Set<Wallet> wallet=new HashSet<Wallet>();
        Wallet wallet1=new Wallet();
        wallet1.setWallet(wallettype.FIATE);
        wallet1.setUsers(user1);
        wallet.add(wallet1);
        walletRepository.save(wallet1);
        users.setWallet(wallet);
        List<Role> list=new ArrayList<Role>();
		list.add(rolerepository.findByRoletype("USER"));
	    users.setRoles(list);
        usersRepository.save(user1);
		
		
			otpService.ValueMethod(users, otp1);
			String email=users.getEmailid();
			
			/*List<Role> list=new ArrayList<Role>();
			list.add(rolerepository.findByRoletype("USER"));
		    users.setRoles(list);*/
		    
		    
		   
		    usersRepository.save(users);
		   
		    
			mail.sendMail(otp1,email);
			otpgenerate.sendSMS(otp1);
			
			
		

	}

	public List<Users> getallusers() {
		
		return usersRepository.findAll();
	}

	public String auth(StoreOTP storeotp1) {
		/*storeOTP=storeOTPRepository.findOnebyOtp(otp);
		if(storeOTP.getUser_Email().equals(email)) {
			System.out.println("success");
		}
		else
			System.out.println("failure");
		*/
		//storeOTP=storeOTPRepository.findByOtp(storeotp1.getUser_otp());
		//users=usersRepository.findByEmailid(storeotp1.getUser_Email());
		if(storeOTP!=null) {
			if(storeotp1.getUserEmail().equals(storeotp1.getUserEmail())) {
				storeOTPRepository.deleteAll();
				users.setStatus(Status.ACTIVE);
				usersRepository.save(users);
				return "Success";
			}
			else
				return "Failure";
		}
		else return "Not found";
		
		
		
		}

	

	public Optional<Users> searchbyid(int id) {
		// TODO Auto-generated method stub
		return usersRepository.findById(id);
	}

	public Users update(Users users, int id) {
		// TODO Auto-generated method stub
		return usersRepository.save(users);
	}

	public void delete(int id) {
		// TODO Auto-generated method stub
		 usersRepository.deleteById(id);;
	}

	public Users assign(AssignRole assignrole) {
		// TODO Auto-generated method stub
		users=usersRepository.findByUserid(assignrole.getUserid());
		role=rolerepository.findByRoletype(assignrole.getRoletype());
		users.getRoles().add(role);
		return usersRepository.save(users);
	}
	
}	
		
	
		
		// TODO Auto-generated method stub
		
	


