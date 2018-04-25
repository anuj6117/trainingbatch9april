package com.trainingproject.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trainingproject.domain.Role;
import com.trainingproject.domain.User;
import com.trainingproject.domain.UserOrder;
import com.trainingproject.domain.Wallet;
import com.trainingproject.dto.AssignRoleBean;
import com.trainingproject.dto.AssignWalletBean;
import com.trainingproject.dto.BuySellBean;
import com.trainingproject.dto.WithdrawDepositBean;
import com.trainingproject.enums.OrderType;
import com.trainingproject.enums.UserOrderStatus;
import com.trainingproject.enums.CoinType;
import com.trainingproject.repository.RoleRepository;
import com.trainingproject.repository.UserOrderRepository;
import com.trainingproject.repository.UserRepository;
import com.trainingproject.repository.WalletRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	WalletRepository walletRepository;
	
	@Autowired
	UserOrderRepository userorderRepository;
	
	@Autowired
	RoleRepository roleService;
	
	public String createUser(User user) {
		
		Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(user.getUserName());
	     if(m.find())
	    	 return "your name cannot have a special character";
		if(user.getUserName().length()==0)
			return "name cannot be empty";
		else if(user.getUserName().length()>25)
			return "Maximum characters allowed for this field is 25";
		
		if(userRepository.findByEmail(user.getEmail())!=null)
			return "oops this email id is already registered";
		
		if(userRepository.findByphoneNumber(user.getPhoneNumber())!=null)
			return "oops this phone number is already registered";
		
		if(user.getPhoneNumber().toString().length()!=10)
			return "please enter a valid phone number";
		
		if(user.getPassword().length()<8)
			return "please enter password with minimum 8 characters";
		
		if(user.getPassword().length()>32)
			return "Maximum characters allowed for this field is 32";
		
		user.setCreatedOn(new Date());
		
		User createduser=userRepository.save(user);
		List<Wallet> walletSet=new ArrayList<Wallet>();
		Wallet wallet=new Wallet();
		wallet.setCoinType(CoinType.FIAT);
		wallet.setUser(user);
		walletSet.add(wallet);
		walletRepository.save(wallet);
		user.setUserWallet(walletSet);
		
		List<Role> roleList=new ArrayList<Role>();
		Role role=new Role();
		role.setRoleType("User");
		roleList.add(role);
		roleRepository.save(role);
		user.setRoleType(roleList);
		
	     userRepository.save(user);
		   return "success";
	}
	
	
	
	public List<User> getAllUsers() {
		
     return userRepository.findAll();
	
	}
	
	public Optional<User> getUserById(Integer id) {
	//	List<User> list=new ArrayList<User>();
		//return new List.add(rep.findById(id);
		return userRepository.findById(id);
		
	}


	public String update(User user) {
	
		if(!userRepository.existsById(user.getUserId()))
			return "this user do not exist";
		
		Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(user.getUserName());
	     if(m.find())
	    	 return "your name cannot have a special character";
		if(user.getUserName().length()==0)
			return "name cannot be empty";
		else if(user.getUserName().length()>25)
			return "Maximum characters allowed for this field is 25";
		
		if(userRepository.findByEmail(user.getEmail())!=null)
			return "oops this email id is already registered";
		
		if(userRepository.findByphoneNumber(user.getPhoneNumber())!=null)
			return "oops this phone number is already registered";
		
		if(user.getPhoneNumber().toString().length()!=10)
			return "please enter a valid phone number";
		
		if(user.getPassword().length()<8)
			return "please enter password with minimum 8 characters";
		
		if(user.getPassword().length()>32)
			return "Maximum characters allowed for this field is 32";
		user.setCreatedOn(new Date());
		 userRepository.save(user);
		 return "success";
	}

	public void deleteData(Integer id)
	{
		userRepository.deleteById(id);
	}


	public String assignRole(AssignRoleBean arb) {
		
		User user=getUserById(arb.getUserId()).get();
	      
		
		Role roleobj=roleRepository.findByroleType(arb.getRoleType());
		List<Role> role=new ArrayList<Role>();
		role.add(roleobj);
		user.setRoleType(role);
		userRepository.save(user);
		return "success";
		//System.out.println(rolelist.get(0).getRoleType()+"kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk");
	
		
	}


	public String addWallet(AssignWalletBean awb) {

		
		
//	  User user=getUserById(awb.getUserId()).get();
//	  Wallet walletobj=walletRepository.findBywalletType(awb.getWalletType());
//	  walletobj.setUser(user);
//	  List<Wallet> walletSet=new ArrayList<Wallet>();
//		walletSet.add(walletobj);
//		user.setWalletType(walletSet);
////		System.out.println(user.getWalletType().get(0).getWalletType()+"wallettypppppppppppppeeee");
//		userRepository.save(user);
//		return "success";
		
		
		User user=getUserById(awb.getUserId()).get();
		Wallet cwallet=new Wallet();
		cwallet.setCoinType(awb.getWalletType());
		cwallet.setUser(user);
		walletRepository.save(cwallet);
		
		 List<Wallet> walletSet=new ArrayList<Wallet>();
		 walletSet.add(cwallet);
		user.setUserWallet(walletSet);
		
		userRepository.save(user);
		
		
		return "success";
		
		
		
	}



	public String withdrawAmount(WithdrawDepositBean wb) {
     
		User user=getUserById(wb.getUserId()).get();
		
        UserOrder userorder=new UserOrder();
		
		userorder.setDate(new Date());
		userorder.setOrderType(OrderType.WITHDRAW);
		userorder.setOrderStatus(UserOrderStatus.PENDING);
		userorder.setUser(user);
		userorder.setUserId(user.getUserId());
		userorder.setPrice(wb.getAmount());
		userorder.setGrossAmount(wb.getAmount());
		userorderRepository.save(userorder);
		
		return "successoo";
//		List <Wallet> walletsList=user.getUserWallet();
//		
//		Wallet wallet=null;
//		boolean b=false;
//		for(int i=0;i<walletsList.size();i++) {
//		
//			if(walletsList.get(i).getWalletType().equals(wb.getWalletType())) {
//				
//				b=true;
//				wallet=walletsList.get(i);
//				break;
//			}
//		}
//		
//		if(b) {
//		wallet.setBalance(wallet.getBalance()-wb.getAmount());
//		walletRepository.save(wallet);
//		return "success";
//		}
//		else {
//			return "failure";
//		}
		
		
	}



	public String depositAmount(WithdrawDepositBean wdb) {
	   
//		User user=getUserById(wdb.getUserId()).get();
//		List <Wallet> walletsList=user.getUserWallet();
//		Wallet wallet=null;
//		boolean b=false;
//		
//		wallet=walletRepository.findBywalletType(WalletType.FIAT);
//		if(wallet!=null)
//			b=true;
//		
//		if(b)
//		{
//		wallet.setBalance(wallet.getBalance()+wdb.getAmount());
//		walletRepository.save(wallet);
//		return "success";
//		}
//		else return "you dont have this account";
		
		User user =getUserById(wdb.getUserId()).get();
		
		UserOrder userorder=new UserOrder();
		
		userorder.setDate(new Date());
		userorder.setOrderType(OrderType.DEPOSIT);
		userorder.setOrderStatus(UserOrderStatus.PENDING);
		userorder.setUser(user);
		userorder.setUserId(user.getUserId());
		userorder.setPrice(wdb.getAmount());
		userorder.setGrossAmount(wdb.getAmount());
		userorderRepository.save(userorder);
		
		return "success";
		
	}



	public String createBuyOrder(BuySellBean bsb) {
		User user =getUserById(bsb.getUserId()).get();
		
		UserOrder userorder=new UserOrder();
		userorder.setCoinName(bsb.getCoinName());
		userorder.setCoinQuantity(bsb.getCoinQuantity());
		userorder.setDate(new Date());
		userorder.setOrderType(OrderType.BUY);
		userorder.setUser(user);
		userorder.setPrice(bsb.getPrice());
		userorder.setUserId(user.getUserId());
		userorderRepository.save(userorder);
		
		return "success";
	}



	public String createSellOrder(BuySellBean bsb) {
	
     User user =getUserById(bsb.getUserId()).get();
		
		UserOrder userorder=new UserOrder();
		userorder.setCoinName(bsb.getCoinName());
		userorder.setCoinQuantity(bsb.getCoinQuantity());
		userorder.setDate(new Date());
		userorder.setOrderType(OrderType.SELL);
		userorder.setUser(user);
		userorder.setPrice(bsb.getPrice());
		userorder.setUserId(user.getUserId());
		userorderRepository.save(userorder);
		
		return "success";
	}
	
	public List<UserOrder> getAllOrdersByUserId(Integer userId) {
		
		User user=getUserById(userId).get();
		
		return user.getUserOrder();
		
	}
	
	
	
}
