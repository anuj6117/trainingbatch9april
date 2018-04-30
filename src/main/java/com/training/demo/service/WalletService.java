package com.training.demo.service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.training.demo.dto.UserDepositDto;
import com.training.demo.dto.UserWalletDto;
import com.training.demo.dto.WalletApprovalDto;
import com.training.demo.enums.OrderType;
import com.training.demo.enums.UserOrderStatus;
import com.training.demo.enums.UserStatus;
import com.training.demo.enums.WalletType;
import com.training.demo.model.Transection;
import com.training.demo.model.User;
import com.training.demo.model.UserOrder;
import com.training.demo.model.Wallet;
import com.training.demo.repository.OrderRepository;
import com.training.demo.repository.TransectionRepository;
import com.training.demo.repository.UserOrderRepository;
import com.training.demo.repository.UserRepository;
import com.training.demo.repository.WalletRepository;

@Service
public class WalletService {

	@Autowired
	private WalletRepository walletRepository;

	@Autowired
	private UserRepository userrepository;
	@Autowired
	private OrderRepository orderrepository;
	@Autowired
	private TransectionRepository transectionrepo;
	
	@Autowired
	private UserOrderRepository userorderrepository;
	
	WalletType walletType;
	
	Wallet wallet;
	String coinName;

	public String addWallet(UserWalletDto userWalletdto) {
		System.out.println(userWalletdto.getUserId());
		User user = userrepository.findByUserId(userWalletdto.getUserId());
		//walletType =walletRepository.existsByWalletType(userWalletdto.getCoinType());
		if ((user != null)&&(walletType==null))
		{
			Set<Wallet>walletset=new HashSet<Wallet>();
			Wallet wallet = new Wallet();
			wallet.setCoinType(userWalletdto.getCoinType());
			wallet.setCoinName(userWalletdto.getCoinName());
			wallet.setBalance(0);
			wallet.setShadowBalance(0);
			wallet.setUser(user);
			walletset.add(wallet);
			user.getWallet().add(wallet);
			walletRepository.save(wallet);
			
			return "wallet add sucees";

		} else {
			return "failed to add new wallet";
		}

	}

	
	public String withDrawAmount(UserDepositDto userdepositdto) {
		Wallet wallet = new Wallet();
		User user = userrepository.findByUserId(userdepositdto.getUserId());
		wallet = walletRepository.findByCoinType(userdepositdto.getWalletType());
		//walletType=userdepositdto.getWalletType();
		if ((wallet != null) && (userdepositdto.getAmount() < wallet.getBalance())) {
			long moneyBalance = wallet.getBalance() - userdepositdto.getAmount();
			wallet.setBalance(moneyBalance);
			wallet.setShadowBalance(moneyBalance);
			walletRepository.save(wallet);
			return "sucess";
		} else {
			return "failue";
		}

	}

	public String depositAmount(UserWalletDto userwalletdto) {
		// Wallet wallet = new Wallet();
		// UserOrder userorder=new UserOrder();
		User user = userrepository.findByUserId(userwalletdto.getUserId());
		walletType = userwalletdto.getCoinType();
		coinName=userwalletdto.getCoinName();
		UserOrder userorder = new UserOrder();
		userorder.setOrderType(OrderType.DEPOSIT);
		userorder.setCoinName(userwalletdto.getCoinName());
		userorder.setCoinType(walletType);
		userorder.setPrice(userwalletdto.getAmount());
		userorder.setGrossAmmount(userwalletdto.getAmount());
		userorder.setNetAmmount(userwalletdto.getAmount());
		userorder.setOrderCreatedOn(new Date());
		userorder.setStatus(UserOrderStatus.PENDING);
		userorder.setUser(user);
		orderrepository.save(userorder);

		if (userorder.getStatus() == UserOrderStatus.APPROVE) {

			return "Amount added";
		} else {
			return "Status Pending";
		}
	}
	// if (wallet != null) {
	// long moneyBalance = userdepositdto.getAmount() + wallet.getBalance();
	// wallet.setBalance(moneyBalance);
	// wallet.setShadowBalance(moneyBalance);
	// walletRepository.save(wallet);
	// return "sucess";
	// } else {
	// return "failue";
	// }

	
	public String deposit(WalletApprovalDto walletapprovaldto) {
		UserOrder userorder = userorderrepository.findByUserOrderId(walletapprovaldto.getOrderId());
		User user = userrepository.findByUserId(walletapprovaldto.getUserId());
		 wallet = walletRepository.findByCoinTypeAndCoinNameAndUser(walletType,coinName,user);
		if ((userorder.getStatus() == UserOrderStatus.PENDING) && (user.getUserStatus() == UserStatus.ACTIVE))

		{
			userorder.setStatus(walletapprovaldto.getOrderstatus());

		}
		if (userorder.getStatus() == UserOrderStatus.APPROVE) {
			  String message = "deposit approved";
			  
			Transection transection = new Transection();
			transection.setNetAmount(userorder.getNetAmmount());
			transection.setWalletType(userorder.getCoinType());
			transection.setGrossAmount(userorder.getGrossAmmount());
			transection.setCreatedOn(new Date());
			transection.setTransactionStatus(walletapprovaldto.getOrderstatus());
			transection.setMessage(message);
			transectionrepo.save(transection);

			long longBalance = wallet.getBalance();
					
			longBalance = longBalance + userorder.getNetAmmount();
			Set<Wallet> walletSet = new HashSet<Wallet>();
			wallet.setBalance(longBalance);
			//wallet.setUser(user);
			//wallet.setCoinType(userorder.getCoinType());
			wallet.setCoinName(userorder.getCoinName());
			wallet.setShadowBalance(longBalance);
			walletSet.add(wallet);
			walletRepository.save(wallet);
			user.setWallet(walletSet);
			//userrepository.save(user);
		}
		else if(userorder.getStatus()==UserOrderStatus.FAILED)
		{
			  String message = "deposit failed";
				Transection transection = new Transection();
				transection.setNetAmount(userorder.getNetAmmount());
				transection.setWalletType(userorder.getCoinType());
				transection.setGrossAmount(userorder.getGrossAmmount());
				transection.setCreatedOn(new Date());
				transection.setTransactionStatus(walletapprovaldto.getOrderstatus());
				transection.setMessage(message);
				transectionrepo.save(transection);

			
		}
		else if(userorder.getStatus()==UserOrderStatus.REJECTED)
		{
			  String message = "deposit rejected";
				Transection transection = new Transection();
				transection.setNetAmount(userorder.getNetAmmount());
				transection.setWalletType(userorder.getCoinType());
				transection.setGrossAmount(userorder.getGrossAmmount());
				transection.setCreatedOn(new Date());
				transection.setTransactionStatus(walletapprovaldto.getOrderstatus());
				transection.setMessage(message);
				transectionrepo.save(transection);

			
		}
		else
		{
			return "user is inactive";
		}

		return "sucess";
	}

}
  







