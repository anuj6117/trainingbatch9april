package com.example.demo.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.DepositAmountDTO;
import com.example.demo.dto.WalletDTO;
import com.example.demo.enums.OrderStatus;
import com.example.demo.enums.OrderType;
import com.example.demo.enums.WalletType;
import com.example.demo.model.User;
import com.example.demo.model.Order;
import com.example.demo.model.Wallet;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.WalletRepository;

@Service
public class WalletService 
{
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private WalletRepository walletRepository;
	
	@Autowired
	private OrderRepository orderRepository;

	public String addWalletToUser(WalletDTO walletDTO) {

		User user = userRepository.findByUserId(walletDTO.getUserId());
		
		Wallet wallet = new Wallet();
		wallet.setWalletType(WalletType.FIAT);
		wallet.setUser(user);
		wallet.setBalance(0.0);
		wallet.setShadowBalance(0.0);
		try {
			walletRepository.save(wallet);
		} catch (Exception e) {
			System.out.println("wallet not added to User." + e);
		}
		return "Wallet Added to User.";

	}

	public String deposit(DepositAmountDTO depositAmountDTO)
	{
		User user=userRepository.findByUserId(depositAmountDTO.getUserId());
		
		Order order=new Order();
		order.setUser(user);
		order.setCoinQuantity(depositAmountDTO.getAmount());
		order.setCoinName(depositAmountDTO.getCoinName());
		order.setCoinType(WalletType.FIAT);
		order.setNetAmount(depositAmountDTO.getAmount());
		order.setGrossAmount(depositAmountDTO.getAmount());
		order.setOrderStatus(OrderStatus.PENDING);
		order.setOrderType(OrderType.DEPOSIT);
		order.setDateCreated(new Date());
		
		orderRepository.save(order);
		
		return "successfully order saved.";
		
	}
		
		/*//User user=userRepository.findByUserId(userOrder.getUserId());
		Order newUserOrder=new Order();
		newUserOrder.setOrderType(OrderType.DEPOSIT);
		newUserOrder.setCoinQuantity(userOrder.getCoinQuantity());
		newUserOrder.setPrice(userOrder.getPrice());
		newUserOrder.setNetAmount(userOrder.getNetAmount());
		newUserOrder.setFee(userOrder.getFee());
		newUserOrder.setGrossAmount(userOrder.getGrossAmount());
		newUserOrder.setOrderStatus(OrderStatus.PENDING);
		newUserOrder.setDateCreated(new Date());
		//newUserOrder.setUser(user);
		orderRepository.save(newUserOrder);
		
		return "successfully data saved into userorder table ";
		
*/		
		
		//Integer userId=userOrder.getUserId();
		//Double amount=userOrder.getAmount();
		//String walletType=transactionDto.getWalletType();
		//OrderType orderType=transactionDto.getOrderType();
		
		/*User user=userRepository.findByUserId(userId);
		
		UserOrder userOrder=new UserOrder();
		userOrder.setOrderType(OrderType.DEPOSIT);
		userOrder.setCoinName(WalletType.valueOf("FIAT").toString());
		userOrder.setPrice(amount);
		userOrder.setDateCreated(new Date());
		userOrder.setOrderStatus(OrderStatus.PENDING);
		userOrder.setUser(user);
		orderRepository.save(userOrder);*/
		

		/*Integer userId=transactionDto.getUserId();
		Double amount=transactionDto.getAmount();
		String walletType=transactionDto.getWalletType();
		System.out.println(walletType);
		
		User user=userRepository.findByUserId(userId);
		List<Wallet> wallets=user.getWallets();
		Iterator itr=wallets.iterator();
		while(itr.hasNext())
		{
			Wallet wallet=(Wallet)itr.next();
			System.out.println(wallet+"=================================================");
			if( wallet == walletRepository.findByWalletType(WalletType.valueOf(walletType))) {
				Double walletBalance=wallet.getBalance();
				wallet.setBalance(transactionDto.getAmount()+walletBalance);
				userRepository.save(user);
			}
		}*/
		
		//return "success";	
// }

	/*public String withdraw(DepositAmountDTO transactionDto) {
		String walletType=transactionDto.getWalletType();
		User user=userRepository.findByUserId(transactionDto.getUserId());
		List<Wallet> wallets=user.getWallets();
		Iterator itr=wallets.iterator();
		while(itr.hasNext())
		{
			Wallet wallet=(Wallet)itr.next();
			if(wallet == walletRepository.findByWalletType(WalletType.valueOf(walletType)))
			{
				Double walletBalance=wallet.getBalance();
				
				if(walletBalance >= transactionDto.getAmount())
				{
					walletBalance = walletBalance-transactionDto.getAmount();
					wallet.setBalance(walletBalance);
					
					userRepository.save(user);
				}
				else
				{
					return "insufficient balance to withdraw";
				}	
			}	
		}
		return "success";	
	}*/
}
