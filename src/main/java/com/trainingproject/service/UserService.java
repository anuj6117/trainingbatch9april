package com.trainingproject.service;

import java.util.ArrayList;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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
import com.trainingproject.enums.WalletType;
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
	
	public User createUser(User user) {
		
		user.setCreatedOn(new Date());
		
		User createduser=userRepository.save(user);
		List<Wallet> walletSet=new ArrayList<Wallet>();
		Wallet wallet=new Wallet();
		wallet.setWalletType(WalletType.FIAT);
		wallet.setUser(user);
		walletSet.add(wallet);
		walletRepository.save(wallet);
		user.setWalletType(walletSet);
		
		List<Role> roleList=new ArrayList<Role>();
		Role role=new Role();
		role.setRoleType("User");
		roleList.add(role);
		roleRepository.save(role);
		user.setRoleType(roleList);
		
	     userRepository.save(user);
		return user;
	}
	
	
	
	public List<User> getAllUsers() {
		
     return userRepository.findAll();
	
	}
	
	public Optional<User> getUserById(Integer id) {
	//	List<User> list=new ArrayList<User>();
		//return new List.add(rep.findById(id);
		return userRepository.findById(id);
		
	}


	public User update(User user) {
	
		return userRepository.save(user);
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
		cwallet.setWalletType(awb.getWalletType());
		cwallet.setUser(user);
		walletRepository.save(cwallet);
		
		 List<Wallet> walletSet=new ArrayList<Wallet>();
		 walletSet.add(cwallet);
		user.setWalletType(walletSet);
		
		userRepository.save(user);
		
		
		return "success";
		
		
		
	}



	public String withdrawAmount(WithdrawDepositBean wb) {
     
		User user=getUserById(wb.getUserId()).get();
		List <Wallet> walletsList=user.getWalletType();
		
		Wallet wallet=null;
		boolean b=false;
		for(int i=0;i<walletsList.size();i++) {
		
			if(walletsList.get(i).getWalletType().equals(wb.getWalletType())) {
				
				b=true;
				wallet=walletsList.get(i);
				break;
			}
		}
		
		if(b) {
		wallet.setBalance(wallet.getBalance()-wb.getAmount());
		walletRepository.save(wallet);
		return "success";
		}
		else {
			return "failure";
		}
	}



	public String depositAmount(WithdrawDepositBean wdb) {
	   
		User user=getUserById(wdb.getUserId()).get();
		List <Wallet> walletsList=user.getWalletType();
		Wallet wallet=null;
		boolean b=false;
		for(int i=0;i<walletsList.size();i++) {
		
			if(walletsList.get(i).getWalletType().equals(wdb.getWalletType())) {
				
				b=true;
				wallet=walletsList.get(i);
				break;
			}
		}
		
		if(b)
		{
		wallet.setBalance(wallet.getBalance()+wdb.getAmount());
		walletRepository.save(wallet);
		return "success";
		}
		else return "you dont have this account";
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
		userorderRepository.save(userorder);
		
		return "success";
	}
	
	
	
	
	
}
