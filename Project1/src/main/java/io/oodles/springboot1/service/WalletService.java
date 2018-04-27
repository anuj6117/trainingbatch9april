package io.oodles.springboot1.service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.oodles.springboot1.DTO.AddWallet;
import io.oodles.springboot1.enums.OrderStatus;
import io.oodles.springboot1.enums.OrderType;
import io.oodles.springboot1.enums.WalletType;
import io.oodles.springboot1.model.ApprovalDTO;
import io.oodles.springboot1.model.Deposit;
import io.oodles.springboot1.model.UserOrder;
import io.oodles.springboot1.model.UserTransaction;
import io.oodles.springboot1.model.Users;
import io.oodles.springboot1.model.Wallet;
import io.oodles.springboot1.repository.OrderRepository;
import io.oodles.springboot1.repository.TransactionRepository;
import io.oodles.springboot1.repository.UsersRepository;
import io.oodles.springboot1.repository.WalletRepository;

@Service
public class WalletService {
	@Autowired
	WalletRepository walletRepository;
	//Wallet wallet=new Wallet();
	Users user;
	WalletType walletType;
	Wallet wallet;
	
	//OrderStatus orderStatus;
	
	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	UsersRepository usersRepository;
	
	@Autowired
	TransactionRepository transactionRepository;
	
	UserOrder userorder;

	public List<Wallet> getallwallet() {
		// TODO Auto-generated method stub
		return walletRepository.findAll();
	} 
	
	public Optional<Wallet> searchbyid(int id) {
		// TODO Auto-generated method stub
		return walletRepository.findById(id);

	}

	public Wallet update(Wallet wallet) {
		// TODO Auto-generated method stub
		return walletRepository.save(wallet);
	}

	public void delete(int id) {
		// TODO Auto-generated method stub
		
		walletRepository.deleteById(id);
	}

	public Users add(AddWallet addwallet) {
		// TODO Auto-generated method stub
		user=usersRepository.findByUserid(addwallet.getUserid());
		Set<Wallet> walletsets=new HashSet<Wallet>();
		Wallet wallet=new Wallet();
		wallet.setUsers(user);
		
		wallet.setWallet(addwallet.getWalletType());
		
		walletsets.add(wallet);
		
		walletRepository.save(wallet);
		user.setWallet(walletsets);
		System.out.println("/////////////////"+wallet.getWallet());
		if(user!=null && wallet!=null) {
		user.getWallet().add(wallet);
	
		usersRepository.save(user);}
		return user;
		
	}

	public Wallet newwallet1(Wallet wallet) {
		// TODO Auto-generated method stub
		return walletRepository.save(wallet);
		
	}

	public String deposit(Deposit deposit) {
		// TODO Auto-generated method stub
		user=usersRepository.findByUserid(deposit.getUserid());
		wallet=walletRepository.findByWallet((deposit.getCoinType()));
		Date date=new Date();
		
		UserOrder userOrder=new UserOrder();
	    userOrder.setUsersorder(user);
	    userOrder.setCoinType(deposit.getCoinType());
	    
	    userOrder.setCoinname(deposit.getCoinName());
	    userOrder.setCoinQuantity(deposit.getAmount());
	    userOrder.setNetAmount(deposit.getAmount());
	    userOrder.setGrossAmount(deposit.getAmount());
	    
	    
	    userOrder.setCoinname(deposit.getCoinName());
	    
	    userOrder.setOrderStatus(OrderStatus.PENDING);
	    
	    userOrder.setOrderCreatedOn(date);
	    
	    userOrder.setOrdertype(OrderType.DEPOSIT);
	    
	     orderRepository.save(userOrder);
	    
	    return "Success";
		
       
	
	}

	public String approve(ApprovalDTO approvalDTO) {
		// TODO Auto-generated method stub
		/*userorder=orderRepository.findById(order.getId()).get();
		if(userorder.getOrderStatus()==OrderStatus.PENDING)
		{
			userorder.setOrderStatus(OrderStatus.APPROVE);
			orderRepository.save(userorder);
		}
		return "Success";
	}*/
		user=usersRepository.findByUserid(approvalDTO.getUserid());
		
		Date date=new Date();
		UserTransaction userTransaction=new UserTransaction();
		userorder=orderRepository.findById(approvalDTO.getOrderid()).get();
		System.out.println(userorder);
		System.out.println(userorder.getOrderStatus());
		if(userorder.getOrderStatus()==OrderStatus.PENDING) {
			userorder.setOrderStatus(approvalDTO.getOrderStatus());}
			if(userorder.getOrderStatus()==OrderStatus.APPROVE) {
				Set<Wallet> walletset=new HashSet<Wallet>();
				
				userTransaction.setTransactionstatus(userorder.getOrderStatus());
				userTransaction.setGrossAmount(userorder.getGrossAmount());
				userTransaction.setNetBalance(userorder.getNetAmount());
				userTransaction.setWalletType(userorder.getCoinType());
				userTransaction.setDateCreated(date);
				userTransaction.setDescription("Approved");
				
				transactionRepository.save(userTransaction);
				System.out.println("???????????????");
				wallet.setBalance(userorder.getNetAmount());
				System.out.println("???????????????");
				wallet.setShadowbalance(userorder.getNetAmount());
				System.out.println("???????????????");
				wallet.setWallet(userorder.getCoinType());
				System.out.println("???????????????");
				wallet.setUsers(user);
				System.out.println("???????????????");
				walletRepository.save(wallet);
				System.out.println("???????????????");
				walletset.add(wallet);
				user.setWallet(walletset);
				usersRepository.save(user);
				return "Success";
			}
			else if(userorder.getOrderStatus()==OrderStatus.FAILED) {
				userTransaction.setTransactionstatus(userorder.getOrderStatus());
				userTransaction.setGrossAmount(userorder.getGrossAmount());
				userTransaction.setNetBalance(userorder.getNetAmount());
				userTransaction.setWalletType(userorder.getCoinType());
				userTransaction.setDateCreated(date);
				userTransaction.setDescription("Failed");
				transactionRepository.save(userTransaction);
				return "Failed";
			}
			else if(userorder.getOrderStatus()==OrderStatus.REJECTED) {
				userTransaction.setTransactionstatus(userorder.getOrderStatus());
				userTransaction.setGrossAmount(userorder.getGrossAmount());
				userTransaction.setNetBalance(userorder.getNetAmount());
				userTransaction.setWalletType(userorder.getCoinType());
				userTransaction.setDateCreated(date);
				userTransaction.setDescription("Rejected");
				transactionRepository.save(userTransaction);
				return "Rejected";
			}
			
				
		return "No Approval";
		}
	}


