package com.example.demo.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.UserTransactionDTO;
import com.example.demo.enums.TransactionStatus;
import com.example.demo.model.Transaction;
import com.example.demo.model.User;
import com.example.demo.model.Wallet;
import com.example.demo.repository.TransactionRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.WalletRepository;

@Service
public class TransactionService {
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private WalletRepository walletRepository;
	
	private Set<Wallet> walletSet = new HashSet<>(); 
	
	public List<Transaction> showAllTransaction(){
		return transactionRepository.findAll();
	}

	public String approvedDepositTransaction(UserTransactionDTO userTransactionDto) {
		Transaction transaction = transactionRepository.findById(userTransactionDto.getTransactionId()).get();
		
		User user = userRepository.findOneById(userTransactionDto.getUserId());
		if(user!=null&&transaction!=null) {
			for(Wallet wallet : user.getWallet()) {
				if(wallet.getWalletType().equals(transaction.getWalletType())) {
					wallet.setBalance(wallet.getBalance()+transaction.getDepositAmount());
					user.getWallet().add(wallet);
					transaction.setTransactionStatus(TransactionStatus.APPROVED);
					try {
						transactionRepository.save(transaction);
						userRepository.save(user);
						
					} catch (Exception e1) {
						e1.printStackTrace();
						return "transaction failed";
					}					
				}			
			}
			return "transaction successfull";
		}
		else
			return "user or transaction not exist";		
	}
}
