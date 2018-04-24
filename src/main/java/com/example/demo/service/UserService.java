package com.example.demo.service;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.UserDepositWithdrawDTO;
import com.example.demo.dto.UserRoleDTO;
import com.example.demo.enums.TransactionStatus;
import com.example.demo.enums.UserStatus;
import com.example.demo.enums.WalletEnum;
import com.example.demo.model.Role;
import com.example.demo.model.Transaction;
import com.example.demo.model.User;
import com.example.demo.model.UserOtp;
import com.example.demo.model.Wallet;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.TransactionRepository;
import com.example.demo.repository.UserOtpRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.mailservice.SendMail;
import com.example.demo.service.mailservice.SendOtpMobile;
import com.example.demo.utility.OtpGenerator;



@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private SendOtpMobile sendOtpMobile;
	
	@Autowired
	private SendMail send;	
	
	@Autowired
	private UserOtpRepository userOtpRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	private User user;	
		
	public String insertData(User user) {
		if(user.getPhoneNumber().length()!=10)
			return "mobile number is not valid";
		
		else if((userRepository.findByEmail(user.getEmail()))!=null)
			return "user with same email already exist";
		
		else if ((userRepository.findByPhoneNumber(user.getPhoneNumber()))!=null)
			return "user with same mobile number already exist";
		
		else {	
			user.setStatus(UserStatus.INACTIVE);
			user.setDate(new Date().toString());
			Wallet wallet = new Wallet(WalletEnum.FIATE);
			wallet.setUser(user);
			Role role = null;
			if((role = roleRepository.findOneByRoleType("User"))==null) {
				role = new Role();
				role.setRoleType("User");
			}
			
			try {
				user.getRole().add(role);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		
			try {
				user.getWallet().add(wallet);
			} catch (Exception e1) {
				System.out.println("didn't add the wallet");
				e1.printStackTrace();
			}		
	
			String otp = new String(OtpGenerator.generateOtp(4));
					
			try {
					send.sendEmail(otp,user.getEmail());
				}			
			catch(Exception e) {
				System.out.println("mail not sent\t"+ e);
				}
			
			try {
				sendOtpMobile.sendSMS(otp,user.getPhoneNumber());
			}
			catch(Exception e) {
				System.out.println("sms not sent\t"+ e);
			} 
			
			if((userRepository.save(user))!=null){
				if(userOtpRepository.save(new UserOtp(user.getEmail(),otp,user.getStatus().toString()))!=null)
						return "Data inserted";
				else
					return "data inserted into user but not in otptable";
			}					
			return "data not inserted into user";		
		}
	}		
	
	
	public boolean checkUser(UserOtp userOtp) {
		UserOtp userotp = userOtpRepository.findByOtp(userOtp.getOtp());
		if(userOtp!=null&&(userotp.getOtp()).equals(userOtp.getOtp())) {				
			userOtpRepository.deleteById(userotp.getUserid());
			user=userRepository.findByEmail(userotp.getEmail());
			user.setStatus(UserStatus.ACTIVE);
			userRepository.save(user);				
			return true;			
		}		
		else
			return false;		
	}
	
	
	public List<User> getAllUser() {
		return userRepository.findAll();
	}	
	
	
	public String deleteUser(String email) {
			if((user = userRepository.findByEmail(email))!=null){
				userRepository.delete(user);
				return "user deleted sucesfully";
			}			
			else 
				return ("user not exist");		
	}	
	

	public User getByUserId(Integer id) {		
		if((user=userRepository.findOneById(id))!=null)
			return user;		
		else
			return null ;		
	}
	

	public String updateUser(User updateUser) {
		user = userRepository.findOneById(updateUser.getId());
		if(user!=null) {
			user.setCountry(updateUser.getCountry());
			user.setDate(new Date().toString());
			user.setEmail(updateUser.getEmail());
			user.setPassword(updateUser.getPassword());
			user.setPhoneNumber(updateUser.getPhoneNumber());
			user.setUserName(updateUser.getUserName());
			userRepository.save(user);
			return "User Updated Successfully";
		}		
		else
			return "User Not Exist";		
	}
	

	public String assignRole(UserRoleDTO userRoleDto) {
		user = userRepository.findById(userRoleDto.getId()).get();
		Role role = null;
		if((role=roleRepository.findOneByRoleType(userRoleDto.getRoleType()))==null)
			role = roleService.createRole(userRoleDto.getRoleType());		
		user.getRole().add(role);
		return (userRepository.save(user)!=null)?"success":"failure";
	}
	

	public String deposit(UserDepositWithdrawDTO userDeposit) {
		
		user = userRepository.findOneById(userDeposit.getUserId());
		for(Wallet wallet : user.getWallet()) {
			if((wallet.getWalletType().compareTo(userDeposit.getWalletType()))==0) {
				wallet.setBalance(wallet.getBalance()+userDeposit.getAmount());
				user.getWallet().add(wallet);
				Transaction transaction = new Transaction(user,wallet,userDeposit.getAmount(),0,TransactionStatus.APPROVED);
				try{
					transactionRepository.save(transaction);
				}catch(Exception e)
				{
					System.out.println("transaction not persist++++++"+e);
				}
				return userRepository.save(user)!=null?"success":"failed";
			}
		}
		return "wallet not found";		
	}


	public String withdraw(UserDepositWithdrawDTO userWithdraw) {
		user = userRepository.findOneById(userWithdraw.getUserId());
		for(Wallet wallet : user.getWallet()) {
			if(wallet.getWalletType().equals(userWithdraw.getWalletType())) {
				if(wallet.getBalance()>=userWithdraw.getAmount()) {
					wallet.setBalance(wallet.getBalance()-userWithdraw.getAmount());
					user.getWallet().add(wallet);
					Transaction transaction = new Transaction(user,wallet,0,userWithdraw.getAmount(),TransactionStatus.APPROVED);
					try{
						transactionRepository.save(transaction);
					}catch(Exception e)
					{
						System.out.println("transaction not persist++++++"+e);
					}
					return userRepository.save(user)!=null?"success":"failure";
				}
				else
					return "insufficient balance";
			}
		}
		return "wallet not found";			
	}	
}
