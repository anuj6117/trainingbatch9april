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
import com.training.demo.repository.UserOrderRepository;
import com.training.demo.repository.UserRepository;
import com.training.demo.repository.WalletRepository;

@Service
public class WalletService {

	@Autowired
	private WalletRepository walletRepository;

	@Autowired
	private WalletService walletService;
	@Autowired
	private UserRepository userrepository;
	@Autowired
	private OrderRepository orderrepository;
	@Autowired
	private UserOrderRepository userorderrepository;
	 Wallet wallet = new Wallet();

	public String addWallet(UserWalletDto userWalletdto) {
		System.out.println(userWalletdto.getUserId());
		User user = userrepository.findByUserId(userWalletdto.getUserId());
		if (user != null) {
			Wallet wallet = new Wallet();
			wallet.setCoinType(userWalletdto.getWalletType());
			wallet.setUser(user);
			walletRepository.save(wallet);
			return "wallet add sucees";

		} else {
			return "failed to add new wallet";
		}

	}

	public String depositAmount(UserWalletDto userwalletdto) {
		// Wallet wallet = new Wallet();
		// UserOrder userorder=new UserOrder();
		User user = userrepository.findByUserId(userwalletdto.getUserId());
		// wallet = walletRepository.findByWalletType(userdepositdto.getWalletType());
		UserOrder userorder = new UserOrder();
		userorder.setOrderType(OrderType.DEPOSIT);
		userorder.setCoinName(userwalletdto.getCoinName());
		userorder.setCoinType(userwalletdto.getWalletType());
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

	public String withDrawAmount(UserDepositDto userdepositdto) {
		Wallet wallet = new Wallet();
		User user = userrepository.findByUserId(userdepositdto.getUserId());
		wallet = walletRepository.findByCoinType(userdepositdto.getWalletType());
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

	public String deposit(WalletApprovalDto walletapprovaldto) {
		UserOrder userorder = userorderrepository.findByUserOrderId(walletapprovaldto.getOrderId());
		User user = userrepository.findByUserId(walletapprovaldto.getUserId());
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

			long longBalance = wallet.getBalance();
					
			longBalance = longBalance + userorder.getNetAmmount();
			Set<Wallet> walletSet = new HashSet<Wallet>();
			wallet.setBalance(longBalance);
			wallet.setShadowBalance(longBalance);
			walletSet.add(wallet);
			walletRepository.save(wallet);
			user.setWallet(walletSet);
			userrepository.save(user);
		}

		return "null";
	}

}
