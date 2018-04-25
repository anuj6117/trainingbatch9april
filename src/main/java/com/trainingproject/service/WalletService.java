package com.trainingproject.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trainingproject.domain.Transaction;
import com.trainingproject.domain.User;
import com.trainingproject.domain.UserOrder;
import com.trainingproject.domain.Wallet;
import com.trainingproject.dto.UserWalletDto;
import com.trainingproject.dto.WalletApprovalDto;
import com.trainingproject.enums.OrderType;
import com.trainingproject.enums.Status;
import com.trainingproject.repository.UserOrderRepository;
import com.trainingproject.repository.UserRepository;
import com.trainingproject.repository.WalletRepository;
@Service
public class WalletService {
	@Autowired
	private WalletRepository walletRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserOrderRepository userOrderRepository; 
	UserOrder  userOrder = new UserOrder();
	
	User user;
	Wallet wallet;
	static Long longBalance;

	public void addWallet(Wallet wallet) {
		// TODO Auto-generated method stub
		walletRepository.save(wallet);
	}

	public List<Wallet> getAllWallet() {
		// TODO Auto-generated method stub
		List<Wallet> list = new ArrayList<Wallet>();
		walletRepository.findAll()
		.forEach(list::add);
		return list;	
	}

	public Optional<Wallet> getById(Integer walletId) {
		// TODO Auto-generated method stub
		return walletRepository.findById(walletId);
	}

	public void updateWallet(Wallet wallet) {
		// TODO Auto-generated method stub
		walletRepository.save(wallet);	
	}

	public void deleteWallet(Integer walletId) {
		// TODO Auto-generated method stub
		walletRepository.deleteById(walletId);
	}

	
	public String depositAmount(UserWalletDto userWalletDto) {
		/*User user = userRepository.findByUserId(userWalletDto.getUserId());
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
		}*/
		user = userRepository.findByUserId(userWalletDto.getUserId());
		wallet = walletRepository.findByWalletType(userWalletDto.getWalletType());
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
			 
			 longBalance = wallet.getBalance();
				longBalance = longBalance + userOrder.getNetAmount();
				Set<Wallet> walletSet = new HashSet<Wallet>();
				wallet.setBalance(longBalance);
				wallet.setShadowBalance(longBalance);
				walletSet.add(wallet);
				walletRepository.save(wallet);
				user.setUserWallet(walletSet);
				userRepository.save(user);
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
			 
		return "success";
	}
}
