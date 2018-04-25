package com.training.demo.service;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.training.demo.enums.WalletType;
import com.training.demo.model.CoinManagement;
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
	WalletRepository walletRepo;
	
	public String approveOrder(OrderApprovalDto orderApprovalDto)
	{       User user = userRepository.findByUserId(orderApprovalDto.getUserId());
			UserOrder userOrder =  orderRepository.findByOrderId(orderApprovalDto.getOrderId());
	        userOrder.setStatus(orderApprovalDto.getStatus());
	        orderRepository.save(userOrder);
	        Wallet wallet = walletRepository.findByWalletTypeAndUser(WalletType.FIAT, user);
			if (wallet != null && userOrder.getStatus() == OrderStatus.COMPLETE) {
				wallet.setBalance(userOrder.getPrice() + wallet.getBalance());
				walletRepository.save(wallet);
			}
	        
	        return "Status Updated Succesfully";
	        
			}

	}
	public List<OrderTable> showalldata(Integer userId) {
		User user = userData.findByUserId(userId);
		List<OrderTable> result = user.getOrderTable();
		return result;
	}

}
