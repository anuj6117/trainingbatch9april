package com.example.demo.service;


import com.example.demo.combinedfields.UserRole;
import com.example.demo.dto.ApprovalRequest;
import com.example.demo.dto.DepositAmountDto;
import com.example.demo.dto.WithDrawAmount;
import com.example.demo.enums.CoinType;
import com.example.demo.enums.OrderStatus;
import com.example.demo.enums.OrderType;
import com.example.demo.model.*;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserOtpRepository;
import com.example.demo.utilities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.regex.Pattern;

import com.example.demo.enums.UserStatus;
import com.example.demo.repository.UserRepository;
import com.example.demo.utilities.EmailValidator;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	RoleService roleService;

	@Autowired
	SendMailNSms sendMailNSms;

	@Autowired
	UserOtpRepository userOtpRepository;

	@Autowired
	OrderRepository orderRepository;

	public String insertUser(User user) throws Exception {

		String userName = user.getUserName();

		if (userName.length() == 0) {
			return "user name can not be null";
		}

		if (userName.startsWith(" ")) {
			return "user name should not have leading space";
		}
		if (userName.endsWith(" ")) {
			return "user name should not have trailing space";
		}
		if(userName.length()>25){
			return "name should not exceeed 25 characters";
		}
		if (!new NameValidator().checkNameValidation(userName)) {
			return "Name must be valid";
		}

		if (user.getEmail().length() == 0) {
			return "password field can not be empty";
		}
		if (userRepository.findOneByEmail(user.getEmail()) == null) {
			if (!new EmailValidator().checkEmail(user.getEmail())) {
				return "Please enetr a valid email address";
			}
		} else {
			return "Oops! this Emailid is already registered";
		}

		String phoneNumber = user.getPhoneNumber();
		if (phoneNumber.length() == 0) {
			return "please enter any valid phonenumer";
		}
		System.out.println("here i am ======================================================");

		if (userRepository.findByPhoneNumber(phoneNumber) == null) {
			if (!new PhoneValidator().checkPhoneNumber(phoneNumber)) {
				return "Invalid phone number";
			}
		} else {
			return "Oops! this number is already registered";
		}

		System.out.println("after phone number==============================================");

		String country = user.getCountry();
		if (country.length() == 0) {
			return "country can not be blank";
		}

		if (!(Pattern.compile("^[A-Za-z]{2,25}$").matcher(user.getCountry()).matches())) {
			return "country name is not valid";
		}


		String password = user.getPassword();
		if (password.length() == 0) {
			return "password field can not be empty";
		}
		if (!new PasswordValidator().isValid(password)) {
			return "Please provide valid password";
		}

		user.setStatus(UserStatus.INACTIVE);
		user.setDate(new Date().toString());

		//adding roles to user
		Role role = roleRepository.findOneByRoleType("User");
		if (role == null) {
			{
				role = new Role();
				role.setRoleType("User");
				roleRepository.save(role);
				user.getRole().add(role);
			}
		} else {
			user.getRole().add(role);
		}


		//adding wallet to user

		Wallet wallet = new Wallet(CoinType.FIAT);
		wallet.setCoinName("INR");
		wallet.setUser(user);
		user.getWallets().add(wallet);


		System.out.println("before saving========================================================");

		if ((userRepository.save(user) != null)) {
			Integer otp = OtpGenearator.generateOtp();
			try {
				UserOtp userOtp = new UserOtp();
				userOtp.setTokenOTP(otp);
				userOtp.setEmail(user.getEmail());
				userOtp.setPhoneNumber(user.getPhoneNumber());
				userOtp.setUserId(user.getId());
				userOtpRepository.save(userOtp);
//				sendMailNSms.sendSms(user,otp);
//				sendMailNSms.sendMail(user,otp);
				return "user created suucessfully please verify it by using otp";
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		} else {
			return "user not created";
		}
		return "successfully inserted";
	}


	//get all users
	public List<User> getallUsers() throws Exception{
		return userRepository.findAll();
	}


//get single user

	public User getSingleUser(Integer id)  {
		if(userRepository.findOneById(id)!=null)
		return userRepository.findOneById(id);
	else
		return null;
	}


	//updating a user
	public String updateUser(User updateduser) throws Exception {
//apply basic validation that have applied during insertion 		
		if ((userRepository.findOneById(updateduser.getId())) != null) {
			User user = userRepository.findOneById(updateduser.getId());

			String userName = updateduser.getUserName();
			System.out.print("jsjfksj ----------------------------------------jdsfjskjfkldjflldsj=---------sdfsdfsd");
			System.out.println(updateduser.getEmail());
			if (userName.length() == 0) {
				return "name can not be blank";
			}
			if (userName.startsWith(" ")) {
				return "name can not have leading space";
			}
			if (userName.endsWith(" ")) {
				return "name can not have trailing space";
			}
			if (!new NameValidator().checkNameValidation(userName)) {
				return "maximum characters allowed for this field is 25";
			}
			if ((userRepository.findOneByEmail(updateduser.getEmail())) != null) {
				return "Oops! this Emailid is already registered";
			}
			if (!new EmailValidator().checkEmail(updateduser.getEmail())) {
				return "Enter valid email";
			}
			updateduser.setStatus(user.getStatus());
			updateduser.setDate(user.getDate());
			userRepository.save(updateduser);
		} else {
			return "id does not exist to update";
		}
		return "succesfully updated";

	}


	//deleting a user
	public String deleteUser(Integer id) throws  Exception{
		if (userRepository.findOneById(id) != null) {
			userRepository.deleteById(id);
			return "deleted succesfully";
		} else {
			return "Record does not exist";
		}
	}


	//assigning a role to user
	public String assignRole(UserRole userRole) throws Exception {
		User user = userRepository.findOneById(userRole.getUserId());
		if (user != null) {
			Role role = roleRepository.findOneByRoleType(userRole.getRoleType());
			if (role == null) {
				role = roleService.createRole(userRole.getRoleType());
			}
			Set<Role> roleassigned = user.getRole();
			for (Role roles : roleassigned) {
				if (!roles.getRoleType().equals(role.getRoleType())) {
				} else {
					return "role already assigned";
				}
			}
			roleassigned.add(role);
			userRepository.save(user);
			return "role assigned successfully";
		}
		return "user does not exist for  userid";
	}


	//verify the user
	public String verifyUser(UserOtp userotp) throws Exception{
		if (userRepository.findOneById(userotp.getUserId()) != null) {
			User initialuser = userRepository.findOneById(userotp.getUserId());
			UserOtp userOtp = userOtpRepository.findOneByTokenOTP(userotp.getTokenOTP());
			if (userOtp != null) {
				if (userOtp.getEmail().equals(initialuser.getEmail())) {
					initialuser.setStatus(UserStatus.ACTIVE);
					//once user is verified delelte that token from exisiting userotp table
					userRepository.save(initialuser);
					userOtpRepository.delete(userOtp);
					return "valid user";
				}else{
					return "invalid user";
				}
			} else {
				return "invalid token";
			}
		}else{
			return "invalid user id";
		}
	}


	//deposit amount to wallet having currency fiat
	public String depositAmount(DepositAmountDto depositAmountDto) throws Exception {
		User user=userRepository.findOneById(depositAmountDto.getUserId());
		int counter=0;
		if(user!=null) {
			if(user.getStatus().equals(UserStatus.ACTIVE)){
				if (depositAmountDto.getWalletType().equals(CoinType.FIAT) && depositAmountDto.getAmount()!=null) {
					Set<Wallet> wallets=user.getWallets();
					for(Wallet wallet:wallets){
						if(wallet.getCoinType().equals(CoinType.FIAT)){
							counter=1;
							OrderDetails orderDetails=new OrderDetails();
							orderDetails.setOrderType(OrderType.DEPOSIT);
							orderDetails.setPrice(depositAmountDto.getAmount());
							orderDetails.setOrderStatus(OrderStatus.PENDING);
							orderDetails.setCoinName("INR");
							orderDetails.setFee(0.0);
							orderDetails.setOrderCreatedOn(new Date());
							user.getOrderDetailsList().add(orderDetails);
							orderDetails.setUser(user);
							orderRepository.save(orderDetails);
						}
					}
					if(counter==0){
						return "user don't have any wallet of cointype fiat";
					}
				} else {
					return "withdraw and deposit functionality should be applied on fiat wallettype";
				}
			}else{
				return  "user is not verified";
			}
		}else {
			return "user id does not exist";
		}
		return "deposit amount request submitted";
	}



	//withdraw amount from user wallet having wallet type fiat
	public String withDrawAmount(WithDrawAmount withDrawAmount) throws Exception{
		User user=userRepository.findOneById(withDrawAmount.getUserId());
		int counter=0;
		if(user!=null) {
			if (user.getStatus().equals(UserStatus.ACTIVE)) {
				if (withDrawAmount.getWalletType().equals(CoinType.FIAT) && withDrawAmount.getAmount() != null) {
					Set<Wallet> existingWallet=	user.getWallets();
					for(Wallet wallet:existingWallet){
						if(wallet.getCoinType().equals(CoinType.FIAT)){
							counter=1;
							OrderDetails orderDetails=new OrderDetails();
							orderDetails.setOrderStatus(OrderStatus.PENDING);
							orderDetails.setPrice(withDrawAmount.getAmount());
							orderDetails.setFee(0.0);
							orderDetails.setOrderType(OrderType.WITHDRAW);
							orderDetails.setOrderCreatedOn(new Date());
							orderDetails.setCoinName("INR");
							user.getOrderDetailsList().add(orderDetails);
							orderDetails.setUser(user);
							orderRepository.save(orderDetails);
						}else{
							return "can not perform withdraw on cryptocurrency .Withdraw should be on Fiat";
						}
					}
				}
			} else {
				return "user does not exist with this id";
			}
		}else{
			return  "user is inactive";
		}
		return "withdraw request submitted";
	}



	public String approveRequest(ApprovalRequest approvalRequest) throws Exception{
		OrderDetails orderDetails=orderRepository.findOneByOrderId(approvalRequest.getOrderId());
		int counter=0;
		if(orderDetails!=null){
			if (orderDetails.getOrderStatus().equals(OrderStatus.PENDING)) {
				if (approvalRequest.getStatus().equals(OrderStatus.APPROVED)) {
					User user = orderDetails.getUser();
					Set<Wallet> exisitngWallets = user.getWallets();
					for (Wallet wallet : exisitngWallets) {
						if (wallet.getCoinType().equals(CoinType.FIAT) && wallet.getCoinName().equalsIgnoreCase("inr")) {
							counter = 1;
							if (orderDetails.getOrderType().equals(OrderType.DEPOSIT)) {
								wallet.setShadowBalance(wallet.getBalance() + orderDetails.getPrice());
								wallet.setBalance(wallet.getShadowBalance());
							}
							if (orderDetails.getOrderType().equals(OrderType.WITHDRAW)) {
								if (wallet.getBalance() < orderDetails.getPrice()) {
									return "transaction can not be done due to low amount in your wallet";
								}
								wallet.setShadowBalance(wallet.getBalance() - orderDetails.getPrice());
								wallet.setBalance(wallet.getShadowBalance());
							}
							orderDetails.setOrderStatus(OrderStatus.APPROVED);
							orderRepository.save(orderDetails);
							userRepository.save(user);
						}
					}
					if (counter == 0) {
						return "wallet dont have any coin type fiat and coin name inr";
					}
				} else {
					return "order can not APPROVED";
				}
			}else{
				return  "order status is not pending";
			}

		}else{
			return "order does not exist";
		}
		return "ordered successful";
	}
}