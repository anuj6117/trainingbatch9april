package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.UserOrderDTO;
import com.example.demo.enums.CoinType;
import com.example.demo.enums.OrderStatus;
import com.example.demo.enums.OrderType;
import com.example.demo.enums.UserStatus;
import com.example.demo.model.CurrencyModel;
import com.example.demo.model.OrderDetails;
import com.example.demo.model.Transaction;
import com.example.demo.model.User;
import com.example.demo.model.Wallet;
import com.example.demo.repository.CurrencyRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.TransactionRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.WalletRepository;
import com.example.demo.utility.ProfitCalculator;

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
	
	private User user;
	private OrderDetails orderDetails;

	public String createBuyOrder(UserOrderDTO userOrderDto) {
		String coinName = userOrderDto.getCoinName().toLowerCase();
		CurrencyModel currency = null;
		boolean walletFlag = false;
		userOrderDto.getUserId();
		if((user=userRepository.findOneByUserId(userOrderDto.getUserId()))==null)
			return "invalid user";
		else {			
			for(Wallet wallet : user.getWallet() ) {
				if(wallet.getCoinName().equalsIgnoreCase(userOrderDto.getCoinName())) {
					walletFlag = true;
					break;
				}
			}
			if(walletFlag) {
				if(user.getStatus().equals(UserStatus.ACTIVE)) {
					try {
						currency = currencyRepository.findByCoinName(coinName).get();
					}catch(Exception e) {return "no such currency";}
					if(currency==null)
						return "currency not exist";
					orderDetails = new OrderDetails(userOrderDto);
					orderDetails.setUser(user);
					orderDetails.setCoinType(CoinType.CRYPTO);
					orderDetails.setOrderType(OrderType.BUYER);
					orderDetails.setFee(currency.getFee());
					orderDetails.setOrderStatus(OrderStatus.PENDING);					
					orderDetails.setProfit(ProfitCalculator.calculateProfit(userOrderDto.getPrice(),userOrderDto.getCoinQuantity(), currency.getFee()));
					orderDetails.setAmount(orderDetails.getProfit()+(orderDetails.getCoinQuantity()*orderDetails.getPrice()));
					for(Wallet wallet : user.getWallet()) {
						if(wallet.getCoinName().equals("INR")) {
							if(wallet.getShadowBalance()<orderDetails.getAmount())
								return "insufficient balance to place this order";
							wallet.setShadowBalance(wallet.getShadowBalance()-orderDetails.getAmount());
						}
					}
					return orderRepository.save(orderDetails)!=null?"Order placed successfully":"failed";
				}
				else
					return "please verify your account";
			}
			return "user doesn't hava wallet";
		}
	}

	public String createSellOrder(UserOrderDTO userOrderDto) {
		userOrderDto.setCoinName(userOrderDto.getCoinName().toLowerCase());
		if((user = userRepository.findOneByUserId(userOrderDto.getUserId()))==null)
			return "invalid user";
		boolean walletFlag = false;
		if(user.getStatus().equals(UserStatus.ACTIVE)) {
			for(Wallet wallet : user.getWallet()) {
				if(wallet.getCoinName().equals(userOrderDto.getCoinName())) {
					if(wallet.getShadowBalance()>=userOrderDto.getCoinQuantity()) {
						walletFlag = true;
						break;
					}
					else
						return "insufficient balance";
				}				
			}
			if(walletFlag) {
				CurrencyModel currency = currencyRepository.findOneByCoinName(userOrderDto.getCoinName().toLowerCase());
				orderDetails = new OrderDetails(userOrderDto);
				orderDetails.setUser(user);
				orderDetails.setOrderType(OrderType.SELLER);
				orderDetails.setCoinType(CoinType.CRYPTO);
				orderDetails.setFee(currency.getFee());
				orderDetails.setOrderStatus(OrderStatus.PENDING);	
				orderDetails.setProfit(ProfitCalculator.calculateProfit(userOrderDto.getPrice(),userOrderDto.getCoinQuantity(), currency.getFee()));
				orderDetails.setAmount(orderDetails.getProfit()+(orderDetails.getCoinQuantity()*orderDetails.getPrice()));
				return orderRepository.save(orderDetails)!=null?"success":"failed";
			}
			else
				return "user wallet not exist";
		}
		else
			return "please verify your account";
	}

	public OrderDetails getOrderByUserId(Integer userId) {
		User user = userRepository.findOneByUserId(userId);
		return orderRepository.findAllByUser(user);
	}

	public List<OrderDetails> showAllOrder() {
		return orderRepository.findAll();
	}

	public String orderApproval(UserOrderDTO userOrderDto) {
		user = null;	
		Wallet wallet = null;
		if(userOrderDto.getOrderStatus().equals(OrderStatus.APPROVED)) {
			if((orderDetails = orderRepository.findByOrderId(userOrderDto.getOrderId()))!=null) {
				if(((user = userRepository.findById(userOrderDto.getUserId()).get())!=null)&&orderDetails.getOrderStatus().equals(OrderStatus.PENDING)) {   							
					try { 
						wallet = walletRepository.findByCoinNameAndUser(orderDetails.getCoinName(),user);
					}catch(Exception e) {return "wallet not exist";}
					if(userOrderDto.getOrderType().equals(OrderType.DEPOSIT)) {
						if(wallet.getShadowBalance()!=null&&wallet.getBalance()!=null)
							wallet.setShadowBalance(wallet.getBalance()+orderDetails.getPrice());
						else
							wallet.setShadowBalance(orderDetails.getPrice());
						wallet.setBalance(wallet.getShadowBalance());
					}
					else if(userOrderDto.getOrderType().equals(OrderType.WITHDRAW)&&(orderDetails.getPrice()<wallet.getBalance())) 
						wallet.setShadowBalance(wallet.getBalance()-orderDetails.getPrice());	
					else 
						return "insufficient balance";
					wallet.setBalance(wallet.getShadowBalance());
					user.getWallet().add(wallet);
					orderDetails.setOrderStatus(userOrderDto.getOrderStatus());
					orderDetails.setOrderType(userOrderDto.getOrderType());
					orderDetails.setFee(0);
					orderDetails.setCoinQuantity(0);
					orderDetails.setExchangeRate(0.0);
					transactionRepository.save(new Transaction(orderDetails));						
					orderRepository.save(orderDetails);
					return userRepository.save(user)!=null?"transaction successfull":"Waiting for approval";					
				}
				else
					return "invalid userId";
			}
			else
				return "invalid orderId";
		}
		else
			return "request not Approved";		
	}	
}
