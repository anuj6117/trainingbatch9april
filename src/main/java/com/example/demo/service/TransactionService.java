package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.enums.OrderStatus;
import com.example.demo.enums.OrderType;
import com.example.demo.model.CurrencyModel;
import com.example.demo.model.OrderDetails;
import com.example.demo.model.Transaction;
import com.example.demo.model.User;
import com.example.demo.model.Wallet;
import com.example.demo.repository.CurrencyRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.TransactionRepository;

@Service
public class TransactionService {
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private OrderRepository orderRepository;	
	
	@Autowired
	private CurrencyRepository currencyRepository;
	
	private CurrencyModel currency;	
	
	public List<Transaction> showAllTransaction(){
		List<Transaction> list =  transactionRepository.findAll();
		return list;
	}

	public void approvedDepositTransaction() {
		List<OrderDetails> buyer = orderRepository.getBuyer(OrderType.BUYER.toString(),OrderStatus.PENDING.toString());
		List<OrderDetails> seller = orderRepository.getSeller(OrderType.SELLER.toString(),OrderStatus.PENDING.toString());
		if(!buyer.isEmpty())
		for(OrderDetails buyerOrder : buyer) {
			currency = currencyRepository.findOneByCoinName(buyerOrder.getCoinName().toLowerCase());
			OrderDetails sellerOrder = new OrderDetails(currency);
			if(seller.isEmpty()) {
				String flag="Admin";
				makeTransaction(buyerOrder,sellerOrder, flag);
			}else {				
				for(OrderDetails sellOrder : seller) {
					String flag="seller";
					if(sellOrder.getPrice() > currency.getPrice()){
						flag = "Admin";
						makeTransaction(buyerOrder,sellerOrder, flag);
					}
					else {
					sellerOrder = sellOrder;
					makeTransaction(buyerOrder,sellerOrder, flag);
					}
				}
			}
		}
	}
			
	
	
	public void makeTransaction(OrderDetails buyerOrder, OrderDetails sellerOrder, String flag) {
		User sellUser = null;		
		if(buyerOrder.getCoinName().equalsIgnoreCase(sellerOrder.getCoinName())) {
			if(buyerOrder.getPrice()>sellerOrder.getPrice()){						
				if((buyerOrder.getCoinQuantity())==(sellerOrder.getCoinQuantity())) {
					User buyUser = buyerOrder.getUser();
					if(flag.equals("seller"))
						sellUser = sellerOrder.getUser();	
					for(Wallet buyerWallet : buyUser.getWallet()) {
						if(buyerWallet.getCoinName().equals(buyerOrder.getCoinName())) {
							buyerWallet.setShadowBalance(buyerWallet.getBalance()+buyerOrder.getCoinQuantity());
							buyerWallet.setBalance(buyerWallet.getShadowBalance());									
							Double profit = (currency.getFee()*buyerOrder.getPrice()*buyerOrder.getCoinQuantity())/100.0;
							Double priceDiff = (sellerOrder.getPrice()*sellerOrder.getCoinQuantity())-(buyerOrder.getPrice()*buyerOrder.getCoinQuantity());
							currency.setProfit(currency.getProfit()+profit);
							currency.setCoinInINR(priceDiff);
						}
						if(buyerWallet.getCoinName().equalsIgnoreCase("INR"))
							buyerWallet.setBalance(buyerWallet.getShadowBalance());
					}
					if(flag.equals("seller")){
						for(Wallet sellerWallet : sellUser.getWallet()) {			
							if(sellerWallet.getCoinName().equals(sellerOrder.getCoinName())) {
								sellerWallet.setShadowBalance(sellerWallet.getBalance()-sellerOrder.getCoinQuantity());
								sellerWallet.setBalance(sellerWallet.getShadowBalance());
							}
							if(sellerWallet.getCoinName().equalsIgnoreCase("INR")) {
								sellerWallet.setShadowBalance(sellerWallet.getBalance()+(sellerOrder.getCoinQuantity()*sellerOrder.getPrice()));
								sellerWallet.setBalance(sellerWallet.getShadowBalance());
							}								
						}
						sellerOrder.setOrderStatus(OrderStatus.APPROVED);
						transactionRepository.save(new Transaction(sellerOrder));
						transactionRepository.save(new Transaction(buyerOrder));
						orderRepository.save(sellerOrder);
					}
					else if(flag.equals("Admin")) {
						currency.setInitialSupply(currency.getInitialSupply()-buyerOrder.getCoinQuantity());
					}
					buyerOrder.setOrderStatus(OrderStatus.APPROVED);					
					currencyRepository.save(currency);
					orderRepository.save(buyerOrder);
					
				}	
				if((buyerOrder.getCoinQuantity())<(sellerOrder.getCoinQuantity())) {
					User buyUser = buyerOrder.getUser();
					if(flag.equals("seller"))
						sellUser = sellerOrder.getUser();
					for(Wallet buyerWallet : buyUser.getWallet()) {
						if(buyerWallet.getCoinName().equalsIgnoreCase(sellerOrder.getCoinName())) {
							buyerWallet.setShadowBalance(buyerWallet.getBalance()+buyerOrder.getCoinQuantity());
							buyerWallet.setBalance(buyerWallet.getShadowBalance());
							Double profit = (currency.getFee()*buyerOrder.getPrice()*buyerOrder.getCoinQuantity())/100.0;
							Double priceDiff = (buyerOrder.getPrice()*buyerOrder.getCoinQuantity())-(sellerOrder.getPrice()*buyerOrder.getCoinQuantity());
							if(currency.getProfit()!=null)
								currency.setProfit(currency.getProfit()+profit);
							else
								currency.setProfit(profit);
							if(currency.getCoinInINR()!=null)
								currency.setCoinInINR(currency.getCoinInINR()+priceDiff);
							else
								currency.setCoinInINR(priceDiff);
						}
						if(buyerWallet.getCoinName().equals("INR"))
							buyerWallet.setBalance(buyerWallet.getShadowBalance());							
					}
					if(flag.equals("seller")) {
						for(Wallet sellerWallet : sellUser.getWallet()) {
							if(sellerWallet.getCoinName().equals(sellerOrder.getCoinName())) {
								sellerWallet.setShadowBalance(sellerWallet.getBalance()-buyerOrder.getCoinQuantity());
								sellerWallet.setBalance(sellerWallet.getShadowBalance());								
							}
							if(sellerWallet.getCoinName().equals("INR")) {
								sellerWallet.setShadowBalance(sellerWallet.getBalance()+(buyerOrder.getCoinQuantity()*sellerOrder.getPrice()));
								sellerWallet.setBalance(sellerWallet.getShadowBalance());
							}							
						}
						sellerOrder.setCoinQuantity(sellerOrder.getCoinQuantity()-buyerOrder.getCoinQuantity());
						sellerOrder.setOrderStatus(OrderStatus.PENDING);
						orderRepository.save(sellerOrder);
					}
					else if(flag.equals("Admin")) {
						currency.setInitialSupply(currency.getInitialSupply()-buyerOrder.getCoinQuantity());
						
					}
					buyerOrder.setOrderStatus(OrderStatus.APPROVED);
					transactionRepository.save(new Transaction(buyerOrder));
					orderRepository.save(buyerOrder);
					currencyRepository.save(currency);
					
				}
				if((buyerOrder.getCoinQuantity())>(sellerOrder.getCoinQuantity())) {
					User buyUser = buyerOrder.getUser();
					if(flag.equals("seller"))
						sellUser = sellerOrder.getUser();
					for(Wallet buyerWallet : buyUser.getWallet()) {
						if(buyerWallet.getCoinName().equalsIgnoreCase(sellerOrder.getCoinName())) {
							buyerWallet.setShadowBalance(buyerWallet.getBalance()+sellerOrder.getCoinQuantity());
							buyerWallet.setBalance(buyerWallet.getShadowBalance());
							Double profit = (currency.getFee()*buyerOrder.getPrice()*buyerOrder.getCoinQuantity())/100.0;
							Double priceDiff = (sellerOrder.getPrice()*sellerOrder.getCoinQuantity())-(buyerOrder.getPrice()*buyerOrder.getCoinQuantity());
							currency.setProfit(currency.getProfit()+profit);
							currency.setCoinInINR(priceDiff);
						}
						if(buyerWallet.getCoinName().equals("INR"))
							buyerWallet.setBalance(buyerWallet.getShadowBalance());							
					}
					if(flag.equals("seller")) {
						for(Wallet sellerWallet : sellUser.getWallet()) {
							if(sellerWallet.getCoinName().equalsIgnoreCase(sellerOrder.getCoinName())) {
								sellerWallet.setShadowBalance(sellerWallet.getBalance()-sellerOrder.getCoinQuantity());
								sellerWallet.setBalance(sellerWallet.getShadowBalance());
							}
							if(sellerWallet.getCoinName().equals("INR")) {
								sellerWallet.setShadowBalance(sellerWallet.getBalance()+(sellerOrder.getCoinQuantity()*sellerOrder.getPrice()));
								sellerWallet.setBalance(sellerWallet.getShadowBalance());
							}							
						}
						sellerOrder.setOrderStatus(OrderStatus.APPROVED);
						orderRepository.save(sellerOrder);
					}
					else if(flag.equals("Admin")) {
						currency.setInitialSupply(currency.getInitialSupply()-sellerOrder.getCoinQuantity());						
					}
					buyerOrder.setOrderStatus(OrderStatus.PENDING);	
					buyerOrder.setCoinQuantity(buyerOrder.getCoinQuantity()-sellerOrder.getCoinQuantity());
					transactionRepository.save(new Transaction(sellerOrder));
					orderRepository.save(buyerOrder);	
					currencyRepository.save(currency);
				}
			}
		}
	}
}


















