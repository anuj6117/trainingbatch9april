package com.trading.services;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trading.Enum.OrderType;
import com.trading.Enum.TransactionOrderStatus;
import com.trading.Enum.WalletType;
import com.trading.domain.User;
import com.trading.domain.UserOrder;
import com.trading.domain.Wallet;
import com.trading.dto.UserWalletDto;
import com.trading.repository.OrderRepository;
import com.trading.repository.UserRepository;
import com.trading.repository.WalletRepository;

@Service
public class WalletService {

	@Autowired
	private WalletRepository walletRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private OrderRepository orderRepository;

	public Map<String, Object> insertWallet(UserWalletDto userwalletdto) {
		Map<String, Object> result = new HashMap<String, Object>();

		User user = userRepository.findOneByUserId(userwalletdto.getuserId());
		Wallet walletdb = walletRepository.findByWalletTypeAndUser(userwalletdto.getwalletType(), user);
		if(user ==null)
		{
			result.put("isSuccess", true);
			result.put("message", "User does not exist");
			return result;
		}
		if (walletdb == null) {
			Wallet wallet = new Wallet();
			wallet.setwalletType(userwalletdto.getwalletType());
			wallet.setuser(user);
			walletRepository.save(wallet);
			result.put("isSuccess", true);
			result.put("message", "New wallet has been added and assigned");
			return result;
		}

		else {
			result.put("isSuccess", false);
			result.put("message", "WalletType already exist");
			return result;
		}
	}

	public Map<String, Object> depositAmount(UserWalletDto userwalletdto) {
		Map<String, Object> result = new HashMap<String, Object>();

		User user = userRepository.findOneByUserId(userwalletdto.getuserId());
		UserOrder userOrder = new UserOrder();

		userOrder.setOrderType(OrderType.DEPOSIT);
		userOrder.setCoinType(WalletType.FIAT);
		userOrder.setPrice(userwalletdto.getamount());
		userOrder.setOrderCreatedOn(new Date());
		userOrder.setStatus(TransactionOrderStatus.PENDING);
		userOrder.setUser(user);
		orderRepository.save(userOrder);
		;
		if (userOrder.getStatus() == TransactionOrderStatus.APPROVED) {

			result.put("isSuccess", true);
			result.put("message", "Amount added to wallet balance");
			return result;
		} else {
			result.put("isSuccess", false);
			result.put("message", "Order status pending");
			return result;
		}
	}

	public Map<String, Object> withdrawAmount(UserWalletDto userwalletdto) {
		Map<String, Object> result = new HashMap<String, Object>();
		Wallet wallet = new Wallet();
		User user = userRepository.findOneByUserId(userwalletdto.getuserId());
		wallet = walletRepository.findByWalletTypeAndUser(userwalletdto.getwalletType(), user);
		if (wallet.getBalance() == 0) {
			result.put("isSuccess", false);
			result.put("message", "No balance in wallet");
			return result;
		}
		if (userwalletdto.getamount() >= wallet.getBalance()) {
			wallet.setBalance(userwalletdto.getamount() - wallet.getBalance());
			walletRepository.save(wallet);
		} else {
			wallet.setBalance(wallet.getBalance() - userwalletdto.getamount());
			walletRepository.save(wallet);
		}
		result.put("isSuccess", true);
		result.put("message", "Success");
		return result;
	}
}