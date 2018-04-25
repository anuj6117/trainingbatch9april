package com.trainingproject.service;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trainingproject.domain.UserOTP;
import com.trainingproject.domain.UserOrder;
import com.trainingproject.dto.UserOrderBuySellDto;
import com.trainingproject.dto.UserRoleDto;
import com.trainingproject.dto.UserWalletDto;
import com.trainingproject.dto.WalletApprovalDto;
import com.trainingproject.domain.Role;
import com.trainingproject.domain.Transaction;
import com.trainingproject.domain.User;
import com.trainingproject.domain.Wallet;
import com.trainingproject.enums.OrderType;
import com.trainingproject.enums.Status;
import com.trainingproject.enums.WalletType;
import com.trainingproject.repository.UserOTPRepository;
import com.trainingproject.repository.UserOrderRepository;
import com.trainingproject.repository.UserRepository;
import com.trainingproject.repository.WalletRepository;
import com.trainingproject.repository.RoleRepository;
import com.trainingproject.domain.User;
@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserOTPRepository userOTPRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private UserOTPService userOTPService; 
	@Autowired
	private RoleService roleService;
	@Autowired
	private WalletRepository walletRepository;
	@Autowired
	private UserOrderRepository userOrderRepository;
	private UserOTP otp = new UserOTP();
	private Role role = new Role();
	static Integer otp1;
	User user;
	static Long longBalance;
	UserOrder  userOrder = new UserOrder();
	
	
	public User addUsers(User user) {
	    user.setCreatedOn(new Date());
		user.setStatus(Status.INACTIVE);
		List<Role> list = new ArrayList<Role>();
		list.add(roleRepository.findByRoleType("user"));
		User userCreated = userRepository.save(user);
		
		Set<Wallet> walletSet = new HashSet<Wallet>();
		Wallet wallet = new Wallet();
		wallet.setWalletType(WalletType.FIAT);
		wallet.setUser(userCreated);
		walletSet.add(wallet);
		walletRepository.save(wallet);
		user.setUserWallet(walletSet);
		user.setRoleType(list);
		userRepository.save(userCreated);
		
		String str = user.getEmail();
		
		if(!(userRepository.save(user) == null)) {
			otp1 = userOTPService.sendSMS();
			userOTPService.sendMail(str);
			otp.setDate(new Date());
			otp.setEmail(str);
			otp.setTokenOTP(otp1);
			userOTPRepository.save(otp);
		}
	return userCreated;
}

	
	
	

	public List<User> getAllUsers() {
		List<User> list = new ArrayList<User>();
		userRepository.findAll()
		.forEach(list::add);
		return list;			
	}

	
	public Optional<User> getById(Integer userId) {
		// TODO Auto-generated method stub
		return userRepository.findById(userId);
	}
	
	
	public void updateUser(User user) {
		userRepository.save(user);
	}
	
	
	public void deleteUser(Integer userId) {
		userRepository.deleteById(userId);
	}

	
	
	
	
	public User assignRole(UserRoleDto userRoleDto) {
		User user = userRepository.findByUserId(userRoleDto.getUserId());
		Role role = roleRepository.findByRoleType(userRoleDto.getRoleType());
	
		if(user != null) {
			if(role != null) {
				user.getRoleType().add(role);
				User tempUser = userRepository.save(user);
				return tempUser;
				} 	else {
					  throw new NullPointerException("User role doesn't exist");
					}
			} 	else {
			      throw new NullPointerException("User id does not exist.");
		        }
	}

	
	
	
	UserOTP userOTP;
	public String userVerification (UserOTP userOtp) throws Exception {
		userOTP = userOTPRepository.findByTokenOTP(userOtp.getTokenOTP());
		user = userRepository.findByEmail(userOtp.getEmail());
		if(userOTP != null) {
			if(userOTP.getEmail().equals(userOtp.getEmail())) {
				userOTPRepository.delete(userOTP);//deleteAll();
				user.setStatus(Status.ACTIVE);
				userRepository.save(user);
				return "success";
			}
				else
					return "failure";
		  }
		else
			return "not found";
		}

	
	
	
	
	public User addWallet(UserWalletDto userWalletDto) {
		// TODO Auto-generated method stub
		User user = userRepository.findByUserId(userWalletDto.getUserId());
		Set<Wallet> walletSet = new HashSet<Wallet>();
		Wallet wallet = new Wallet();
		wallet.setUser(user);
		wallet.setWalletType(userWalletDto.getWalletType());
		walletSet.add(wallet);
		walletRepository.save(wallet);
		user.setUserWallet(walletSet);
		
		if(user != null && wallet != null) {
			user.getUserWallet().add(wallet);
			userRepository.save(user);
			return user;
		}
		else
			throw new NullPointerException("User id does not exist.");
	}
	
	
	
		
	public String withdrawAmount(UserWalletDto userWalletDto) {
		// TODO Auto-generated method stub
		User user = userRepository.findByUserId(userWalletDto.getUserId());
		Wallet wallet = walletRepository.findByWalletType(userWalletDto.getWalletType());
		if(user != null) {
			if(wallet != null ) {
				longBalance = wallet.getBalance();
				if(longBalance >= userWalletDto.getAmount()) {
				longBalance = longBalance - userWalletDto.getAmount();
				Set<Wallet> walletSet = new HashSet<Wallet>();
				wallet.setBalance(longBalance);
				wallet.setShadowBalance(longBalance);
				walletSet.add(wallet);
				walletRepository.save(wallet);
				user.setUserWallet(walletSet);
				userRepository.save(user);
			  }
				else
					return "You can't withdraw amount, your account balance is "+longBalance;
			}		
		}
		return "Withdraw amount successfully";
	}
	
	/*public String depositAmount(UserWalletDto userWalletDto) {
		User user = userRepository.findByUserId(userWalletDto.getUserId());
		Wallet wallet = walletRepository.findByWalletType(userWalletDto.getWalletType());
		if(user != null) {
			if(wallet != null ) {
				longBalance = wallet.getBalance();
				longBalance = longBalance + userWalletDto.getAmount();
				Set<Wallet> walletSet = new HashSet<Wallet>();
				wallet.setBalance(longBalance);
				wallet.setShadowBalance(longBalance);
				walletSet.add(wallet);
				walletRepository.save(wallet);
				user.setUserWallet(walletSet);
				userRepository.save(user);
			}
		}
		User user = userRepository.findByUserId(userWalletDto.getUserId());
		Wallet wallet = walletRepository.findByWalletType(userWalletDto.getWalletType());
		if(user != null && wallet != null ) {
				//UserOrder  userOrder = new UserOrder();
				userOrder.setUser(user);
				userOrder.setCoinType(userWalletDto.getWalletType());
				userOrder.setCoinName(userWalletDto.getCoinName());
				userOrder.setOrderType(OrderType.dEPOSIT);
				userOrder.setCoinQuantity(userWalletDto.getAmount());
				userOrder.setNetAmount(userWalletDto.getAmount());
				userOrder.setGrossAmount(userWalletDto.getAmount());
				userOrder.setStatus(Status.PENDING);
				userOrder.setOrderCreatedOn(new Date());
				userOrderRepository.save(userOrder);
				return "success";
			}
		else 
		    return "User id does not exist.";
	}


	public String message;
	public String walletApproved(WalletApprovalDto walletApprovalDto) {
		// TODO Auto-generated method stub
		 UserOrder  userOrder = userOrderRepository.findByOrderId(walletApprovalDto.getOrderId());
		 if(userOrder.getStatus() == Status.PENDING) {
			userOrder.setStatus(walletApprovalDto.getStatus()); 
			}
		 if(userOrder.getStatus() == Status.APPROVED) {
			 message = "deposit approved";
			 Transaction transaction = new  Transaction();
			 transaction.setNetAmount(userOrder.getNetAmount());
			 transaction.setWalletType(userOrder.getCoinType());
			 transaction.setGrossAmount(userOrder.getGrossAmount());
			 transaction.setCreatedOn(new Date());
			 transaction.setStatus(walletApprovalDto.getStatus());
			 transaction.setMessage(message);
		 } 
		 else if(userOrder.getStatus() == Status.FAILED) {
			 message = "deposit failed";
			 Transaction transaction = new  Transaction();
			 transaction.setNetAmount(userOrder.getNetAmount());
			 transaction.setWalletType(userOrder.getCoinType());
			 transaction.setGrossAmount(userOrder.getGrossAmount());
			 transaction.setCreatedOn(new Date());
			 transaction.setStatus(walletApprovalDto.getStatus());
			 transaction.setMessage(message);
		 } 
		 else if(userOrder.getStatus() == Status.REJECTED) {
			 message = "deposit rejected";
			 Transaction transaction = new  Transaction();
			 transaction.setNetAmount(userOrder.getNetAmount());
			 transaction.setWalletType(userOrder.getCoinType());
			 transaction.setGrossAmount(userOrder.getGrossAmount());
			 transaction.setCreatedOn(new Date());
			 transaction.setStatus(walletApprovalDto.getStatus());
			 transaction.setMessage(message);
		 }
			 
		 
		return null;
	}*/
}
