package com.training.demo.service;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.training.demo.dto.OrderDto;
import com.training.demo.dto.WalletDto;
import com.training.demo.enums.OrderStatus;
import com.training.demo.enums.OrderType;
import com.training.demo.enums.UserStatus;
import com.training.demo.enums.WalletType;
import com.training.demo.model.CoinManagement;
import com.training.demo.model.OrderTable;
import com.training.demo.model.Transaction;
import com.training.demo.model.User;
import com.training.demo.model.Wallet;
import com.training.demo.repository.CoinManagementRepository;
import com.training.demo.repository.OrderRepository;
import com.training.demo.repository.UserRepository;
import com.training.demo.repository.WalletRepository;

@Service
public class WalletService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private WalletRepository walletRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private CoinManagementRepository coinRepository;
	
	public String addWallet(WalletDto walletDto) {
		boolean flag = false;
		User user;
		
		if((user = userRepository.findByUserId(walletDto.getUserId())) == null)
		{
			return "Invalid User Id";
		}
		
		if(!(walletDto.getCoinType().equalsIgnoreCase(WalletType.CRYPTO.toString())))
		{
			return "Invalid Coin Type.";
		}	
			
		String tempCoinName = walletDto.getCoinName().toUpperCase();
		System.out.println("coinnme : "+tempCoinName);
		walletDto.setCoinName(tempCoinName);
		System.out.println("dto.coinname : "+walletDto.getCoinName());
		if(tempCoinName == null || tempCoinName.equals(""))
		{
			return "Please Enter Coin Name.";
		}
		
			if(!coinRepository.existsByCoinName(tempCoinName))
			{
				return "Invalid Coin Name";
			}
			
		if(user.getUserStatus().equals(UserStatus.ACTIVE))
		{
		Set<Wallet> walletSet = user.getWallets();
		Iterator<Wallet> walletIterator = walletSet.iterator();
		while(walletIterator.hasNext())
		{
			Wallet tempwallet = walletIterator.next();
				
			if(tempwallet.getCoinName() != null)	
			{
				if(tempwallet.getCoinType().equals(walletDto.getCoinType()) && (tempwallet.getCoinName().equals(walletDto.getCoinName())))
				{
					System.out.println(tempwallet);
					flag = true;
				}
			}
		}
		if(!flag)
		{	
			Wallet wallet = new Wallet();
			wallet.setCoinType(WalletType.valueOf(walletDto.getCoinType()));
			wallet.setUser(user);
			wallet.setBalance(0.0);
			wallet.setShadowBalance(0.0);
			wallet.setCoinName(walletDto.getCoinName());
			walletRepository.save(wallet);
			return "Wallet is successfully created.";
		}
		else {
			return "Existing wallet type for the given coin name.";
		}
		}
		else
		{
			return "Please activate your account first and than only you can create wallet.";
		}
	}
	
	//DepositConfigurationssssss
	
	public String depositAmount(OrderDto orderDto) {
		User user ;
		
		
		if((user = userRepository.findByUserId(orderDto.getUserId())) == null)
		{
			return "invalid user id.";
		}
			if(!orderDto.getCoinType().equals(WalletType.FIAT.toString())) {
				return "invalid coin type.";
			}
		
		if(orderDto.getAmount() <=0) {
			return "Amount can not be less than or equal to 0";
		}	
		
		if(user.getUserStatus().equals(UserStatus.ACTIVE))
		{
			OrderTable tempOrderTable = new OrderTable();
			tempOrderTable.setOrderType(OrderType.DEPOSIT);
			tempOrderTable.setCoinName(orderDto.getCoinName());
			tempOrderTable.setNetAmount(orderDto.getAmount());
			tempOrderTable.setCoinQuantity(orderDto.getAmount());
			tempOrderTable.setFees(0d);
			tempOrderTable.setPrice(0d);
			tempOrderTable.setGrossAmount(orderDto.getAmount());
			tempOrderTable.setOrderCreatedOn(new Date());
			tempOrderTable.setOrderStatus(OrderStatus.PENDING);
			tempOrderTable.setUser(user);
			orderRepository.save(tempOrderTable); 
			return "Your deposit request is generated. Please wait for approval";
		}
		else
		{
			return "User can not deposit until his/her status is not active.";
		}
	}
			
/*	public String toWithdrawn(OrderDto orderDto ) {
		 Integer userId = orderDto.getUserId();
		 Double amount = orderDto.getAmount();
		 User user ;
		 if((user = userRepository.findByUserId(userId))==null) {
			 return "Invalid user id.";
		 }
		 if(orderDto.getAmount()<=0) {
			 return "Amount can not be zero or less.";
		 }
		 
		 if(!orderDto.getCoinType().equalsIgnoreCase("fiat")) {
			 return "you can only withdrawn FIAT currency.";
		 }
		 
		 Set<Wallet> userWallet = user.getWallets();
		 Iterator<Wallet> userWalletIterator = userWallet.iterator();
		 Wallet tempUserWallet;
		 while(userWalletIterator.hasNext()) {
			 tempUserWallet = userWalletIterator.next();
			 if(tempUserWallet.getCoinName().equalsIgnoreCase(orderDto.getCoinName())) {
				 {
					 break;
					 }
			 }
			 
				 Double availBalance = tempUserWallet.getBalance();
				 if(amount <= availBalance)
				 {
					 Double totalAmount = tempUserWallet.getBalance() - amount;
					 tempUserWallet.setBalance(totalAmount);
					 Double shadowBalance = tempUserWallet.getShadowBalance() - amount;
					 tempUserWallet.setShadowBalance(shadowBalance);
					 walletRepository.save(tempUserWallet);
					 userRepository.save(user);					
					 OrderTable withdrawTransaction = new OrderTable();
					 withdrawTransaction.setUser(user);
					 withdrawTransaction.setCoinName(orderDto.getCoinName().toUpperCase());
					 withdrawTransaction.setOrderType(OrderType.WITHDRAWN);
					 withdrawTransaction.setFees(0.0);
					 withdrawTransaction.setGrossAmount(amount);
					 withdrawTransaction.setNetAmount(amount);
					 withdrawTransaction.setOrderCreatedOn(new Date());
					 withdrawTransaction.setOrderStatus(OrderStatus.COMPLETED);
					 withdrawTransaction.setPrice(0.0);
					 orderRepository.save(withdrawTransaction);
					 
				 }
				 else
				 {
					 return "Sorry insufficient balance.";
				 }
		 }
		 return "Successfully withdrawn.";
	 }

*/	public Object showWalletHistoryByUserIdAndCoinName(Integer userId, String coinName){
		
		User user;
		CoinManagement coinManagement;
		
		if((user =  userRepository.findByUserId(userId)) == null)
		{
			return "Invalid User Id";
		}
		
		if((coinManagement= coinRepository.findAllByCoinName(coinName)) ==null) {
			
			return "Invalid Coin Name";
		}
				
		List<OrderTable> userWalletHistory = orderRepository.getWalletHistory(userId,coinName);
		if(userWalletHistory.isEmpty()) {
		
			return "There is no any wallet history for the given user and coin name.";
		}	
		
		return userWalletHistory;
	}
}