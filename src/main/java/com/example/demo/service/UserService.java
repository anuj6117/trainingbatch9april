package com.example.demo.service;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.UserDepositWithdrawDTO;
import com.example.demo.dto.UserRoleDTO;
import com.example.demo.enums.CoinType;
import com.example.demo.enums.OrderType;
import com.example.demo.enums.UserStatus;
import com.example.demo.model.OrderDetails;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.model.UserOtp;
import com.example.demo.model.Wallet;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserOtpRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.mailservice.SendMail;
import com.example.demo.service.mailservice.SendOtpMobile;
import com.example.demo.utility.EmailValidator;
import com.example.demo.utility.OtpGenerator;
import com.example.demo.utility.PasswordValidator;
import com.example.demo.utility.UserNameValidator;
import com.example.demo.utility.Validator;



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
	private OrderRepository orderRepository;
	
	private User user;	
		
	public String insertData(User user) {
	
		if(user.getCountry().trim().length()>=2) {
			if(new Validator().validateEmail(user.getEmail())) {
				if(user.getUserName().trim().length()<25) {
					if(UserNameValidator.isValid(user.getUserName())) {
						if(user.getPhoneNumber().trim().length()==10) {
							if(PasswordValidator.isValid(user.getPassword())&&user.getPassword().trim().length()<32) {
								try {
									Long i = Long.valueOf(user.getPhoneNumber());
									i.doubleValue();
								}
								catch(Exception e) {return "invalid Phone Number";}
								if(user.getPhoneNumber().length()!=10) 			
									return "mobile number is not valid";									
								else if((userRepository.findByEmail(user.getEmail()))!=null)
									return "Oopss, this email is already registered";							
								else if ((userRepository.findByPhoneNumber(user.getPhoneNumber()))!=null)
									return "Oopss, this number is already register";							
								else {				
									user.setStatus(UserStatus.INACTIVE);
									user.setDate(new Date().toString());
									Wallet wallet = new Wallet(CoinType.FIAT);
									wallet.setCoinName("INR");
									wallet.setUser(user);
									wallet.setBalance(0.0);
									wallet.setShadowBalance(0.0);
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
							else
								return "Please enter password with minimum 8 characters. Your password should have atleast 1 Upper Case, 1 Lower Case, 1 Digit and 1 Special Character.";
						}
						else
							return "Please Enter valid phone Number";
					}
					else
						return "username can't be null";
				}
				else
					return "username must be less then 25 charactor";
			}
			else 
				return "Please enter valid email";
		}
		else 
			return "invalid country";
				
	}
	
	
	
	public String checkUser(UserOtp userOtp) {
		UserOtp userotp = null;
		try {
			int i = Integer.valueOf(userOtp.getOtp());
		}catch(Exception e ) {return "invalid userid";}
		try {
			int m = Integer.valueOf(userOtp.getUserId());
		}catch(Exception e) {return "invalid otp";}
		if(userOtp.getUserId()>0) {
			if((user = userRepository.findOneByUserId(userOtp.getUserId()))!=null){
				try {
					userotp = userOtpRepository.findByOtp(userOtp.getOtp());
				}catch(Exception e) {
				System.out.print("invalid otp "+e);
				return "invalid otp";
			}
				if(userotp!=null) {	
					if((userotp.getEmail()).equals(user.getEmail())) {
						userOtpRepository.deleteById(userotp.getId());
						user=userRepository.findByEmail(userotp.getEmail());
						user.setStatus(UserStatus.ACTIVE);
						userRepository.save(user);				
						return "user Verified successfully";
					}
					else
						return "invalid email";
				}	
				else 
					return "invalid otp";
			}
			return "invalid user";
		}		
		else
			return "invalid userId";				
	}
	
	
	public List<User> getAllUser() {
		return userRepository.findAll();
	}	
	
	
	public String deleteUser(Integer id) {
			if((user = userRepository.findOneByUserId(id))!=null){
				userRepository.delete(user);
				return "user deleted sucesfully";
			}			
			else 
				return ("user not exist");		
	}	
	

	public User getByUserId(Integer id) {		
		if((user=userRepository.findOneByUserId(id))!=null)
			return user;		
		else
			return null ;		
	}
	

	public String updateUser(User updateUser) {
		user = userRepository.findOneByUserId(updateUser.getUserId());
		if(user!=null) {
			if(user.getStatus().equals(UserStatus.ACTIVE)) {
				if(EmailValidator.isValidEmailAddress(updateUser.getEmail())) {
					if(updateUser.getPhoneNumber().trim().length()==10) {
						try {
							Long i = Long.valueOf(user.getPhoneNumber());
							i.doubleValue();
						}
						catch(Exception e) {return "invalid Phone Number";}
						if((userRepository.findByEmail(user.getEmail()))!=null)
							return "Oopss, this email is already registered";							
						else if ((userRepository.findByPhoneNumber(user.getPhoneNumber()))!=null)
							return "Oopss, this number is already register";	
						else {
							user.setCountry(updateUser.getCountry());
							user.setDate(new Date().toString());
							user.setEmail(updateUser.getEmail());
							user.setPassword(updateUser.getPassword());
							user.setPhoneNumber(updateUser.getPhoneNumber());
							user.setUserName(updateUser.getUserName());
							userRepository.save(user);
							return "User Updated Successfully";
						}
					}	
					else
						return "Phone number must be of 10 digit";
				}
				else 
					return "please enter valid email address";
			}
			else 
				return "please verify your account";					
		}
		else
			return "User Not Exist";
	}
	

	public String assignRole(UserRoleDTO userRoleDto) {
		try {
		user = userRepository.findByUserId(userRoleDto.getUserId()).get();
		}
		catch(Exception e) {
			return "invalid user";
		}
		if(user!=null) {
		if(user.getStatus().equals(UserStatus.ACTIVE)) {
			Role role = null;
			if((role=roleRepository.findOneByRoleType(userRoleDto.getRoleType()))==null)
				role = roleService.createRole(userRoleDto.getRoleType());		
			user.getRole().add(role);
			return (userRepository.save(user)!=null)?"success":"failure";
		}
		else
			return "please verify your account";
		}
		else 
			return "invalid user iD";
	}
	

	public String deposit(UserDepositWithdrawDTO userDeposit) {		
		user = userRepository.findOneByUserId(userDeposit.getUserId());
		try {
		if(user.getStatus().equals(UserStatus.ACTIVE)) {
			for(Wallet wallet : user.getWallet()) {
				if(wallet.getCoinName().equals(userDeposit.getCoinName())) {
					OrderDetails orderDetails = new OrderDetails(userDeposit, user);
					return orderRepository.save(orderDetails)!=null?"request submitted":"request failed to submit";	
				}				
			}
			return "wallet not exist";			
		}
		else
			return "please verify your account";
		}catch(Exception e) {return "invalid userId";}
	}


	public String withdraw(UserDepositWithdrawDTO userWithdraw) {
		user = userRepository.findOneByUserId(userWithdraw.getUserId());
		if(user!=null) {
			if(user.getStatus().equals(UserStatus.ACTIVE)) {
				userWithdraw.setOrderType(OrderType.WITHDRAW);
				OrderDetails orderDetails = new OrderDetails(userWithdraw, user);
				return orderRepository.save(orderDetails)!=null?"request submitted":"request failed to submit";	
			}	
			else
				return "please verify your account";
		}
		else 
			return "user not exist";
	}
}
