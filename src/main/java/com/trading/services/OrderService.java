package com.trading.services;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trading.Enum.OrderStatus;
import com.trading.Enum.OrderType;
import com.trading.Enum.WalletType;
import com.trading.domain.User;
import com.trading.domain.UserOrder;
import com.trading.domain.Wallet;
import com.trading.dto.OrderApprovalDto;
import com.trading.dto.UserOrderDto;
import com.trading.repository.OrderRepository;
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

	public Map<String, Object> createBuyOrder(UserOrderDto userOrderDto) {
		Map<String, Object> result = new HashMap<String, Object>();

		User user = userRepository.findOneByUserId(userOrderDto.getUserId());

		if (user != null && userOrderDto != null) {
			UserOrder userOrder = new UserOrder();
			userOrder.setOrderCreatedOn(new Date());
			userOrder.setStatus(OrderStatus.PENDING);
			userOrder.setOrderType(OrderType.BUYER);
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

	public Map<String, Object> createSellOrder(UserOrderDto userOrderDto) {
		Map<String, Object> result = new HashMap<String, Object>();

		User user = userRepository.findOneByUserId(userOrderDto.getUserId());
		if (user != null && userOrderDto != null) {
			UserOrder userOrder = new UserOrder();
			userOrder.setOrderCreatedOn(new Date());
			userOrder.setStatus(OrderStatus.PENDING);
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
		return orderRepository.findOneByUser(user);
	}

	public Map<String, Object> approveOrder(OrderApprovalDto orderApprovalDto) {
		Map<String, Object> result = new HashMap<String, Object>();

		{
			User user = userRepository.findByUserId(orderApprovalDto.getUserId());
			UserOrder userOrder = orderRepository.findByOrderId(orderApprovalDto.getOrderId());
			userOrder.setStatus(orderApprovalDto.getStatus());
			orderRepository.save(userOrder);
			Wallet wallet = walletRepository.findByWalletTypeAndUser(WalletType.FIAT, user);
			if (wallet != null && userOrder.getStatus() == OrderStatus.COMPLETE) {
				wallet.setBalance(userOrder.getPrice() + wallet.getBalance());
				walletRepository.save(wallet);
				result.put("isSuccess", true);
				result.put("message", "Balance added succesfully");
				return result;
			}

			result.put("isSuccess", true);
			result.put("message", "Status updated succesfully");
			return result;
		}
	}
}