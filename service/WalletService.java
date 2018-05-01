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
import com.trainingproject.enums.CoinType;
import com.trainingproject.repository.TransactionRepository;
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
	@Autowired
	private TransactionRepository transactionRepository;
	UserOrder  userOrder = new UserOrder();
	CoinType coinType;
	
	User user;
	Wallet wallet;
	static Long longBalance;
	String coinName;

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
		user = userRepository.findByUserId(userWalletDto.getUserId());
		coinType = userWalletDto.getCoinType();
		coinName = userWalletDto.getCoinName();
		if(user != null && user.getStatus() == Status.ACTIVE ) {
				userOrder.setUser(user);
				userOrder.setCoinType(userWalletDto.getCoinType());
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
		    return "User id does not exist or user is not ACTIVE";
	}


	public String message;
	public String walletApproved(WalletApprovalDto walletApprovalDto) {
		// TODO Auto-generated method stub
		 UserOrder  userOrder = userOrderRepository.findByOrderId(walletApprovalDto.getOrderId());
		 wallet = walletRepository.findByCoinTypeAndCoinNameAndUser(coinType, coinName, user);
		 if(userOrder.getStatus() == Status.PENDING) {
			userOrder.setStatus(walletApprovalDto.getStatus()); 
			}
		 
			 Transaction transaction = new  Transaction();
			 transaction.setNetAmount(userOrder.getNetAmount());
			 transaction.setCoinType(userOrder.getCoinType());
			 transaction.setCoinName(userOrder.getCoinName());
			 transaction.setGrossAmount(userOrder.getGrossAmount());
			 transaction.setCreatedOn(new Date());
			 
			 if(userOrder.getStatus() == Status.APPROVED) {
				 message = "deposit approved";
			 transaction.setStatus(walletApprovalDto.getStatus());
			 transaction.setMessage(message);
			// transactionRepository.save(transaction);
			 
			 longBalance = wallet.getBalance();
				longBalance = longBalance + userOrder.getNetAmount();
				Set<Wallet> walletSet = new HashSet<Wallet>();
				wallet.setBalance(longBalance);
				wallet.setShadowBalance(longBalance);
				walletSet.add(wallet);
				walletRepository.save(wallet);
		 } 
		 else if(userOrder.getStatus() == Status.FAILED) {
			 message = "deposit failed";
			 transaction.setStatus(walletApprovalDto.getStatus());
			 transaction.setMessage(message);
			 //transactionRepository.save(transaction);
		 } 
		 else if(userOrder.getStatus() == Status.REJECTED) {
			 message = "deposit rejected";
			 transaction.setStatus(walletApprovalDto.getStatus());
			 transaction.setMessage(message);
			 //transactionRepository.save(transaction);
		 }
			 transactionRepository.save(transaction);
		return "success";
	}
}
