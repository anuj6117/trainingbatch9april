package com.training.demo.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.training.demo.dto.OrderApprovalDto;
import com.training.demo.enums.OrderStatus;
import com.training.demo.enums.WalletType;
import com.training.demo.model.OrderTable;
import com.training.demo.model.User;
import com.training.demo.model.Wallet;
import com.training.demo.repository.CoinManagementRepository;
import com.training.demo.repository.OrderRepository;
import com.training.demo.repository.UserRepository;
import com.training.demo.repository.WalletRepository;

@Service
public class OrderService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	CoinManagementRepository coinManagementRepository;
	
	@Autowired
	OrderRepository orderdata;
	
	@Autowired
	UserRepository userData;
	
	@Autowired
	CoinManagementRepository coindata;
	
	@Autowired
	WalletRepository walletRepository;
	
	@Autowired
	OrderRepository orderRepository;
		
	public String approveOrder(OrderApprovalDto orderApprovalDto)
	{       User user = userRepository.findByUserId(orderApprovalDto.getUserId());
			OrderTable orderTable =  orderRepository.findByOrderId(orderApprovalDto.getOrderId());
	        orderTable.setOrderStatus(orderApprovalDto.getStatus());
	        orderRepository.save(orderTable);
	        Wallet wallet = walletRepository.findByWalletTypeAndUser(WalletType.FIAT, user);
			if (wallet != null && orderTable.getOrderStatus() == OrderStatus.APPROVED) {
				wallet.setBalance(orderTable.getPrice() + wallet.getBalance());
				walletRepository.save(wallet);
			}
	     return "Status Updated Succesfully";
	}
	
	public String createBuyOrder(OrderTable orderTable)
	{ 
			User user = orderTable.getUser();
			OrderTable tempOrderTable = new OrderTable();
			tempOrderTable.setUser(user);
			tempOrderTable.setCoinName((orderTable.getCoinName()));
			tempOrderTable.setCoinQuantity(orderTable.getCoinQuantity());
			tempOrderTable.setOrderStatus(OrderStatus.PENDING);
			tempOrderTable.setFee(orderTable.getFee());
			orderRepository.save(tempOrderTable);

	     return "Create buy order request is processed successfully and pending for approval.";
	}
	
	public String createSellOrder(OrderTable orderTable)
	{ 
			User user = orderTable.getUser();
			OrderTable tempOrderTable = new OrderTable();
			tempOrderTable.setUser(user);
			tempOrderTable.setCoinName((orderTable.getCoinName()));
			tempOrderTable.setCoinQuantity(orderTable.getCoinQuantity());
			tempOrderTable.setOrderStatus(OrderStatus.PENDING);

	     return "Sell buy order request is processed successfully and pending for approval.";
	}
	
	public Set<OrderTable> showalldata(Integer userId) {
		User user = userData.findByUserId(userId);
		Set<OrderTable> allOrders = user.getOrderTable();
		return allOrders;
	}

}
