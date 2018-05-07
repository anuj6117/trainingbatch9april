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
			OrderDetails sellerOrder = new OrderDetails(currency,buyerOrder.getOrderId());
			if(seller.isEmpty()) {
				String flag="Admin";
				makeTransaction(buyerOrder,sellerOrder, flag);
			}
			else {				
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
					boolean cryptoFlag=false;
					boolean inrFlag=false;
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
							cryptoFlag = true;
						}
						if(buyerWallet.getCoinName().equalsIgnoreCase("INR")) {
							buyerWallet.setBalance(buyerWallet.getShadowBalance());
							inrFlag = true;
						}
						if(inrFlag&&cryptoFlag)
							break;
					}
					if(flag.equals("seller")){
						for(Wallet sellerWallet : sellUser.getWallet()){			
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
						//transactionRepository.save(new Transaction(sellerOrder));
						transactionRepository.save(new Transaction(buyerOrder,sellerOrder.getUser().getUserId(),"buyer"));
						orderRepository.save(sellerOrder);
					}
					else if(flag.equals("Admin")) {
						currency.setInitialSupply(currency.getInitialSupply()-buyerOrder.getCoinQuantity());
						transactionRepository.save(new Transaction(buyerOrder,0,"buyer"));
					}
					buyerOrder.setOrderStatus(OrderStatus.APPROVED);					
					currencyRepository.save(currency);
					orderRepository.save(buyerOrder);
					
				}	
				if((buyerOrder.getCoinQuantity())<(sellerOrder.getCoinQuantity())) {
					boolean cryptoFlag=false;
					boolean inrFlag=false;
					User buyUser = buyerOrder.getUser();
					Double priceDiff = 0.0;
					Double profit = 0.0;
					if(flag.equals("seller"))
						sellUser = sellerOrder.getUser();
					for(Wallet buyerWallet : buyUser.getWallet()) {
						if(buyerWallet.getCoinName().equalsIgnoreCase(sellerOrder.getCoinName())) {
							buyerWallet.setShadowBalance(buyerWallet.getBalance()+buyerOrder.getCoinQuantity());
							buyerWallet.setBalance(buyerWallet.getShadowBalance()+buyerOrder.getCoinQuantity());
							profit = (currency.getFee()*buyerOrder.getPrice()*buyerOrder.getCoinQuantity())/100.0;
							if(flag.equals("seller"))
									priceDiff = (buyerOrder.getPrice()*buyerOrder.getCoinQuantity())-(sellerOrder.getPrice()*buyerOrder.getCoinQuantity());
							else {
								priceDiff = buyerOrder.getPrice()*buyerOrder.getCoinQuantity();
								if(currency.getProfit()!=null)
									currency.setProfit(currency.getProfit()+profit);
								else
									currency.setProfit(currency.getProfit()+profit);
								if(currency.getCoinInINR()!=null)
									currency.setCoinInINR(currency.getCoinInINR()+priceDiff);
								else
									currency.setCoinInINR(priceDiff);
								}
							cryptoFlag = true;
						}
						if(buyerWallet.getCoinName().equals("INR")) {
							buyerWallet.setBalance(buyerWallet.getShadowBalance());
							inrFlag = true;
						}
						if(cryptoFlag&&inrFlag)
							break;
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
						//sellerOrder.setOrderStatus(OrderStatus.PENDING);
						buyerOrder.setOrderStatus(OrderStatus.APPROVED);
						transactionRepository.save(new Transaction(buyerOrder,sellerOrder.getUser().getUserId(),"buyer"));
						orderRepository.save(sellerOrder);
					}
					else if(flag.equals("Admin")) {
						currency.setInitialSupply(currency.getInitialSupply()-buyerOrder.getCoinQuantity());
						currency.setProfit(currency.getProfit()+profit);
						buyerOrder.setOrderStatus(OrderStatus.APPROVED);
						transactionRepository.save(new Transaction(buyerOrder,0,"buyer"));						
						currencyRepository.save(currency);
					}					
					orderRepository.save(buyerOrder);					
				}
				if((buyerOrder.getCoinQuantity())>(sellerOrder.getCoinQuantity())) {
					boolean cryptoFlag=false;
					boolean inrFlag=false;
					User buyUser = buyerOrder.getUser();
					if(flag.equals("seller"))
						sellUser = sellerOrder.getUser();
					for(Wallet buyerWallet : buyUser.getWallet()) {
						if(buyerWallet.getCoinName().equalsIgnoreCase(sellerOrder.getCoinName())) {
							buyerWallet.setShadowBalance(buyerWallet.getBalance()+sellerOrder.getCoinQuantity());
							buyerWallet.setBalance(buyerWallet.getShadowBalance());
							Double profit = (currency.getFee()*buyerOrder.getPrice()*buyerOrder.getCoinQuantity())/100.0;
							Double priceDiff = (sellerOrder.getPrice()*sellerOrder.getCoinQuantity())-(buyerOrder.getPrice()*buyerOrder.getCoinQuantity());
							if(flag.equals("Admin")) {
								currency.setProfit(currency.getProfit()+profit);
								currency.setCoinInINR(priceDiff);
								sellerOrder.setProfit(profit);
								sellerOrder.setAmount((buyerOrder.getPrice()*sellerOrder.getCoinQuantity())+((buyerOrder.getPrice()*sellerOrder.getCoinQuantity()*currency.getFee())/100));
							}
							
							cryptoFlag = true;
						}
						if(buyerWallet.getCoinName().equals("INR")) {
							buyerWallet.setBalance(buyerWallet.getShadowBalance());	
							inrFlag = true;
						}
						if(inrFlag&&cryptoFlag)
							break;
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
						transactionRepository.save(new Transaction(sellerOrder,buyerOrder.getUser().getUserId(),"seller"));
						orderRepository.save(sellerOrder);
					}
					else if(flag.equals("Admin")) {
						currency.setInitialSupply(currency.getInitialSupply()-sellerOrder.getCoinQuantity());						
						transactionRepository.save(new Transaction(sellerOrder,buyerOrder.getUser().getUserId(),"seller"));
						currencyRepository.save(currency);
					}
					buyerOrder.setOrderStatus(OrderStatus.PENDING);	
					buyerOrder.setCoinQuantity(buyerOrder.getCoinQuantity()-sellerOrder.getCoinQuantity());					
					orderRepository.save(buyerOrder);						
				}
			}
		}
	}
}


















