package com.trading.services;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trading.Enum.OrderType;
import com.trading.Enum.TransactionOrderStatus;
import com.trading.Enum.WalletType;
import com.trading.domain.Currency;
import com.trading.domain.Transaction;
import com.trading.domain.User;
import com.trading.domain.UserOrder;
import com.trading.domain.Wallet;
import com.trading.dto.OrderApprovalDto;
import com.trading.dto.UserOrderDto;
import com.trading.repository.CurrencyRepository;
import com.trading.repository.OrderRepository;
import com.trading.repository.TransactionRepository;
import com.trading.repository.UserRepository;
import com.trading.repository.WalletRepository;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private WalletRepository walletRepository;

	@Autowired
	private CurrencyRepository currencyRepository;

	@Autowired
	private TransactionRepository transactionRepository;

	public Map<String, Object> createBuyOrder(UserOrderDto userOrderDto) {
		Map<String, Object> result = new HashMap<String, Object>();

		User user = userRepository.findOneByUserId(userOrderDto.getUserId());

		Currency currency = new Currency();
		currency = currencyRepository.findByCoinName(userOrderDto.getCoinName());
		currency.setFee(userOrderDto.getFee());

		if (user != null && userOrderDto != null) {
			UserOrder userOrder = new UserOrder();
			userOrder.setOrderCreatedOn(new Date());
			userOrder.setStatus(TransactionOrderStatus.PENDING);
			userOrder.setOrderType(OrderType.BUYER);
			userOrder.setUser(user);
			userOrder.setCoinQuantity(userOrderDto.getCoinQuantity());
			userOrder.setCoinType(WalletType.CRYPTO);
			userOrder.setCoinName(userOrderDto.getCoinName());
			userOrder.setPrice(userOrderDto.getPrice());
			orderRepository.save(userOrder);
			result.put("isSuccess", true);
			result.put("message", "Order added succesfully");
			return result;
		} else {
			result.put("isSuccess", false);
			result.put("message", "Failed to add buy order");
			return result;
		}
	}

	public Map<String, Object> createSellOrder(UserOrderDto userOrderDto) {
		Map<String, Object> result = new HashMap<String, Object>();

		User user = userRepository.findOneByUserId(userOrderDto.getUserId());
		if (user != null && userOrderDto != null) {
			UserOrder userOrder = new UserOrder();
			userOrder.setOrderCreatedOn(new Date());
			userOrder.setStatus(TransactionOrderStatus.PENDING);
			userOrder.setOrderType(OrderType.SELLER);
			userOrder.setUser(user);
			userOrder.setCoinQuantity(userOrderDto.getCoinQuantity());
			userOrder.setCoinName(userOrderDto.getCoinName());
			userOrder.setPrice(userOrderDto.getPrice());
			orderRepository.save(userOrder);
			result.put("isSuccess", true);
			result.put("message", "Success");
			return result;
		} else {
			result.put("isSuccess", false);
			result.put("message", "Failed");
			return result;
		}
	}

	public UserOrder getOrderByUserId(long userId) {
		User user = userRepository.findOneByUserId(userId);
		if (user != null) {
			return orderRepository.findOneByUser(user);
		} else {
			return null;
		}
	}

	public Map<String, Object> approveOrder(OrderApprovalDto orderApprovalDto) {
		Map<String, Object> result = new HashMap<String, Object>();

		User user = userRepository.findByUserId(orderApprovalDto.getUserId());
		UserOrder userOrder = orderRepository.findByOrderId(orderApprovalDto.getOrderId());
		if (userOrder == null) {
			result.put("isSuccess", false);
			result.put("message", "Order Id does not exist");
			return result;
		}
		Wallet wallet = walletRepository.findByCoinNameAndUser(userOrder.getCoinName(), user);
		if (orderApprovalDto.getStatus() == TransactionOrderStatus.APPROVED) {
			if (wallet != null && userOrder.getStatus() == TransactionOrderStatus.PENDING) {
				wallet.setBalance(userOrder.getPrice() + wallet.getBalance());
				walletRepository.save(wallet);
				Transaction transaction = new Transaction();
				transaction.setCoinType(WalletType.FIAT);
				transaction.setCoinName(userOrder.getCoinName());
				transaction.setStatus(TransactionOrderStatus.APPROVED);
				transaction.setNetAmount(userOrder.getPrice());
				transaction.setBuyerId(userOrder.getOrderId());
				transaction.setTransactionCreatedOn(new Date());
				transactionRepository.save(transaction);
				userOrder.setStatus(orderApprovalDto.getStatus());
				orderRepository.save(userOrder);
				result.put("isSuccess", true);
				result.put("message", "Order Status updated and Balance added");
				return result;

			}
			else
			{
			result.put("isSuccess", false);
			result.put("message", "CoinName doesnot exist in wallet and order status is not pending");
			return result;	
		}
		}
		else {
			result.put("isSuccess", false);
			result.put("message", "Status is not approved by admin");
			return result;	
		}
			
	}
}
