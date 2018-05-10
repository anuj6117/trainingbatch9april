package com.trainingproject.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trainingproject.domain.Currency;
import com.trainingproject.domain.Transaction;
import com.trainingproject.domain.User;
import com.trainingproject.domain.UserOrder;
import com.trainingproject.domain.Wallet;
import com.trainingproject.enums.CoinType;
import com.trainingproject.enums.OrderType;
import com.trainingproject.enums.Status;
import com.trainingproject.repository.CurrencyRepository;
import com.trainingproject.repository.TransactionRepository;
import com.trainingproject.repository.UserOrderRepository;
import com.trainingproject.repository.WalletRepository;
import com.trainingproject.utils.UserOrderMaxBuyerPrice;
import com.trainingproject.utils.UserOrderMinSellerPrice;
@Service
public class TransactionService {
		@Autowired
		private TransactionRepository transactionRepository;
		@Autowired
		private UserOrderRepository userOrderRepository;
		@Autowired
		private CurrencyRepository currencyRepository;
		@Autowired
		private WalletRepository walletRepository;
		
		Double amount=0d, shadowAmount=0d;
		Currency currency;
		User user;
		Wallet wallet;
		Set<Wallet> walletSet = new HashSet<Wallet>();
		public String startTransaction() {
			
			System.out.println("rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrTransaction functionrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");
			ArrayList<UserOrder> buyers =  userOrderRepository.findAllByOrderTypeAndStatus(OrderType.Buyer,Status.PENDING);
			if(buyers.isEmpty()) {
				throw new RuntimeException("Buyer are not available");
			}
			ArrayList<UserOrder> sellers =  userOrderRepository.findAllByOrderTypeAndStatus(OrderType.Seller,Status.PENDING);
			
			Collections.sort(buyers, new UserOrderMaxBuyerPrice());
			Collections.reverse(buyers);
			Collections.sort(sellers, new UserOrderMinSellerPrice());
			
				if(sellers.isEmpty()) {
					for(UserOrder allBuyers:buyers) {
						currency = currencyRepository.findByCoinName(allBuyers.getCoinName());
					if(currency != null) {
					  if(currency.getPrice() <= allBuyers.getPrice() && currency.getInitialSupply() >= allBuyers.getCoinQuantity() && allBuyers.getCoinQuantity() > 0d) {
						System.out.println("sssssssssssssssssssssssssssss (currency >=) ssssssssssssssssssssssssssssss");
						Transaction transaction = new Transaction();
						transaction.setCoinType(allBuyers.getCoinType());
						transaction.setCoinName(allBuyers.getCoinName());
						transaction.setNetAmount(allBuyers.getCoinQuantity() * allBuyers.getPrice());
						transaction.setGrossAmount((allBuyers.getCoinQuantity() * allBuyers.getPrice()) + 
								((allBuyers.getCoinQuantity() * allBuyers.getPrice() * currency.getFees()) / 100));	
						transaction.setFees(currency.getFees());
						transaction.setBuyerId(allBuyers.getOrderId());
						transaction.setSellerId(currency.getCoinId());
						transaction.setCreatedOn(new Date());
						transaction.setStatus(Status.APPROVED);
						transaction.setMessage("Transaction is done");
						transaction.setExchangeRate(allBuyers.getPrice());
						transactionRepository.save(transaction);
						
						// Buyer FIAT wallet
						user = allBuyers.getUser();
						wallet = walletRepository.findByCoinTypeAndCoinNameAndUser(CoinType.FIAT, "INR", user); 
						wallet.setShadowBalance(wallet.getBalance() - transaction.getGrossAmount());
						wallet.setBalance(wallet.getBalance() - transaction.getGrossAmount());
						walletSet.add(wallet);
						walletRepository.save(wallet);
						
						// Buyer CRYPTO wallet
						wallet = walletRepository.findByCoinTypeAndCoinNameAndUser(allBuyers.getCoinType(), allBuyers.getCoinName(), user);
						wallet.setShadowBalance(wallet.getBalance() + allBuyers.getCoinQuantity());
						wallet.setBalance(wallet.getBalance() + allBuyers.getCoinQuantity()); 
						walletSet.add(wallet);
						walletRepository.save(wallet);
						
						// Currency update
						currency.setProfit(currency.getProfit() + ((allBuyers.getCoinQuantity() * allBuyers.getPrice() * currency.getFees()) / 100));
						currency.setCoinInINR(currency.getCoinInINR() + ((allBuyers.getCoinQuantity() * allBuyers.getPrice()) - 
								(allBuyers.getCoinQuantity() * currency.getPrice())));
						currency.setInitialSupply(currency.getInitialSupply() - allBuyers.getCoinQuantity());
						currencyRepository.save(currency);
						
						allBuyers.setCoinQuantity(0d);
						allBuyers.setStatus(Status.APPROVED);
						userOrderRepository.save(allBuyers);	
					}
					else if(currency.getPrice() <= allBuyers.getPrice() && currency.getInitialSupply() < allBuyers.getCoinQuantity() && currency.getInitialSupply() > 0d) {
						System.out.println("tttttttttttttttttttttttttttttttt (currency <) ttttttttttttttttttttttttttttttttttt");
						Transaction transaction = new Transaction();
						transaction.setCoinType(allBuyers.getCoinType());
						transaction.setCoinName(allBuyers.getCoinName());
						transaction.setNetAmount(currency.getInitialSupply() * allBuyers.getPrice());
						transaction.setGrossAmount((currency.getInitialSupply() * allBuyers.getPrice()) + 
								((currency.getInitialSupply() * allBuyers.getPrice() * currency.getFees()) / 100));	
						transaction.setFees(currency.getFees());
						transaction.setBuyerId(allBuyers.getOrderId());
						transaction.setSellerId(currency.getCoinId());
						transaction.setCreatedOn(new Date());
						transaction.setStatus(Status.APPROVED);
						transaction.setMessage("Transaction is done");
						transaction.setExchangeRate(allBuyers.getPrice());
						transactionRepository.save(transaction);
						
						// Buyer FIAT wallet
						user = allBuyers.getUser();
						wallet = walletRepository.findByCoinTypeAndCoinNameAndUser(CoinType.FIAT, "INR", user); 
						wallet.setShadowBalance(wallet.getBalance() - transaction.getGrossAmount());
						wallet.setBalance(wallet.getBalance() - transaction.getGrossAmount());
						walletSet.add(wallet);
						walletRepository.save(wallet);
						
						// Buyer CRYPTO wallet
						wallet = walletRepository.findByCoinTypeAndCoinNameAndUser(allBuyers.getCoinType(), allBuyers.getCoinName(), user);
						wallet.setShadowBalance(wallet.getBalance() + currency.getInitialSupply());
						wallet.setBalance(wallet.getBalance() + currency.getInitialSupply()); 
						walletSet.add(wallet);
						walletRepository.save(wallet);
						
						// Currency update
						currency.setProfit(currency.getProfit() + ((currency.getInitialSupply() * allBuyers.getPrice() * currency.getFees()) / 100));
						currency.setCoinInINR(currency.getCoinInINR() + ((currency.getInitialSupply() * allBuyers.getPrice()) - 
								(currency.getInitialSupply() * currency.getPrice())));
						/*currency.setInitialSupply(0d);
						currencyRepository.save(currency);*/
						
						allBuyers.setCoinQuantity(allBuyers.getCoinQuantity() - currency.getInitialSupply());
						currency.setInitialSupply(0d);
						currencyRepository.save(currency);
						allBuyers.setStatus(Status.PENDING);
						userOrderRepository.save(allBuyers);
						
					}
				  }
					else 
						return "Currency can not be null";
			   } 
			}
		
			
			
			for (UserOrder allBuyers : buyers) {
				System.out.println("ssssssssssssssss"+allBuyers.getPrice());
			}
			//Transaction transaction = new Transaction();
			System.out.println("+++++++++++++++++++++++++++++++++++++");
			// ************************************************************************

		System.err.println("+++++++++++++++++++Condition for buyer and seller list+++++++++");
		
			
			
			if(sellers != null) {
			for (UserOrder allBuyers : buyers) {
				for (UserOrder allSellers : sellers) {
					currency = currencyRepository.findByCoinName(allBuyers.getCoinName());
					System.out.println("currency price<<<<<<<<<<<<<<<<<<<<<<<<<<<<<"+currency.getPrice());
					//String message = "Transaction approved and done";
					//Transaction transaction = new Transaction();
				if(currency != null) {
				  if(allBuyers.getPrice() >= allSellers.getPrice() && allSellers.getPrice() <= currency.getPrice()
					&& allBuyers.getUser().getUserId() != allSellers.getUser().getUserId() && allBuyers.getCoinName().equals(allSellers.getCoinName())) {

					 if(allBuyers.getCoinQuantity() >= allSellers.getCoinQuantity() && allSellers.getCoinQuantity() > 0d) {
						System.out.println("uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu (buyerQuantity >= sellerQuantity) uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu");
						Transaction transaction = new Transaction();
						transaction.setCoinType(allBuyers.getCoinType());
						transaction.setCoinName(allBuyers.getCoinName());
						transaction.setNetAmount(allSellers.getCoinQuantity() * allBuyers.getPrice());
						transaction.setGrossAmount((allSellers.getCoinQuantity() * allBuyers.getPrice()) + 
								((allSellers.getCoinQuantity() * allBuyers.getPrice() * currency.getFees()) / 100));	
						transaction.setFees(currency.getFees());
						transaction.setBuyerId(allBuyers.getOrderId());
						transaction.setSellerId(allSellers.getOrderId());
						transaction.setCreatedOn(new Date());
						transaction.setStatus(Status.APPROVED);
						transaction.setMessage("Transaction is done buyer seller");
						transaction.setExchangeRate(allBuyers.getPrice());
						transactionRepository.save(transaction);
						
						// Buyer FIAT wallet
						user = allBuyers.getUser();
						wallet = walletRepository.findByCoinTypeAndCoinNameAndUser(CoinType.FIAT, "INR", user); 
						//((allSellers.getCoinQuantity() * allBuyers.getPrice()) + ((allSellers.getCoinQuantity() * allBuyers.getPrice() * currency.getFees()) / 100))
						wallet.setShadowBalance(wallet.getBalance() - transaction.getGrossAmount());
						wallet.setBalance(wallet.getBalance() - transaction.getGrossAmount());
						walletSet.add(wallet);
						walletRepository.save(wallet);
						
						// Buyer CRYPTO wallet
						wallet = walletRepository.findByCoinTypeAndCoinNameAndUser(allBuyers.getCoinType(), allBuyers.getCoinName(), user);
						wallet.setShadowBalance(wallet.getBalance() + allSellers.getCoinQuantity());
						wallet.setBalance(wallet.getBalance() + allSellers.getCoinQuantity()); 
						walletSet.add(wallet);
						walletRepository.save(wallet);
						
						// Seller FIAT wallet
						user = allSellers.getUser();
						wallet = walletRepository.findByCoinTypeAndCoinNameAndUser(CoinType.FIAT, "INR", user);
						wallet.setShadowBalance(wallet.getBalance() + (allSellers.getCoinQuantity() * allSellers.getPrice()));
						wallet.setBalance(wallet.getBalance() + (allSellers.getCoinQuantity() * allSellers.getPrice()));
						walletSet.add(wallet);
						walletRepository.save(wallet);
						
						// Seller CRYPTO wallet
						wallet = walletRepository.findByCoinTypeAndCoinNameAndUser(allSellers.getCoinType(), allSellers.getCoinName(), user);
						wallet.setShadowBalance(wallet.getBalance() - allSellers.getCoinQuantity());
						wallet.setBalance(wallet.getBalance() - allSellers.getCoinQuantity()); 
						walletSet.add(wallet);
						walletRepository.save(wallet);
						
						// Currency update
						currency.setProfit(currency.getProfit() + ((allSellers.getCoinQuantity() * allBuyers.getPrice() * currency.getFees()) / 100));
						currency.setCoinInINR(currency.getCoinInINR() + ((allSellers.getCoinQuantity() * allBuyers.getPrice()) - 
								(allSellers.getCoinQuantity() * allSellers.getPrice())));
						currencyRepository.save(currency);
						
						if(allBuyers.getCoinQuantity() == allSellers.getCoinQuantity()) {
							allBuyers.setCoinQuantity(0d);
							allBuyers.setStatus(Status.APPROVED);
						}
						  else {
							  allBuyers.setCoinQuantity(allBuyers.getCoinQuantity() - allSellers.getCoinQuantity());
							  allBuyers.setStatus(Status.PENDING);
						}
						allSellers.setCoinQuantity(0d);
						allSellers.setStatus(Status.APPROVED);
						userOrderRepository.save(allBuyers);
						userOrderRepository.save(allSellers);
					}
					else if(allBuyers.getCoinQuantity() < allSellers.getCoinQuantity() && allBuyers.getCoinQuantity() > 0d) {
						System.out.println("vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv (buyerQuantity < sellerQuantity) vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv");
						Transaction transaction = new Transaction();
						transaction.setCoinType(allBuyers.getCoinType());
						transaction.setCoinName(allBuyers.getCoinName());
						transaction.setNetAmount(allBuyers.getCoinQuantity() * allBuyers.getPrice());
						transaction.setGrossAmount((allBuyers.getCoinQuantity() * allBuyers.getPrice()) + 
								((allBuyers.getCoinQuantity() * allBuyers.getPrice() * currency.getFees()) / 100));	
						transaction.setFees(currency.getFees());
						transaction.setBuyerId(allBuyers.getOrderId());
						transaction.setSellerId(allSellers.getOrderId());
						transaction.setCreatedOn(new Date());
						transaction.setStatus(Status.APPROVED);
						transaction.setMessage("Transaction is done buyer seller");
						transaction.setExchangeRate(allBuyers.getPrice());
						transactionRepository.save(transaction);
						
						// Buyer FIAT wallet
						user = allBuyers.getUser();
						wallet = walletRepository.findByCoinTypeAndCoinNameAndUser(CoinType.FIAT, "INR", user); 
                        //((allBuyers.getCoinQuantity() * allBuyers.getPrice()) + ((allBuyers.getCoinQuantity() * allBuyers.getPrice() * currency.getFees()) / 100))
						wallet.setShadowBalance(wallet.getBalance() - transaction.getGrossAmount());
						wallet.setBalance(wallet.getBalance() - transaction.getGrossAmount());
						walletSet.add(wallet);
						walletRepository.save(wallet);
						
						// Buyer CRYPTO wallet
						wallet = walletRepository.findByCoinTypeAndCoinNameAndUser(allBuyers.getCoinType(), allBuyers.getCoinName(), user);
						wallet.setShadowBalance(wallet.getBalance() + allBuyers.getCoinQuantity());
						wallet.setBalance(wallet.getBalance() + allBuyers.getCoinQuantity()); 
						walletSet.add(wallet);
						walletRepository.save(wallet);
						
						// Seller FIAT wallet
						user = allSellers.getUser();
						wallet = walletRepository.findByCoinTypeAndCoinNameAndUser(CoinType.FIAT, "INR", user);
						wallet.setShadowBalance(wallet.getBalance() + (allBuyers.getCoinQuantity() * allSellers.getPrice()));
						wallet.setBalance(wallet.getBalance() + (allBuyers.getCoinQuantity() * allSellers.getPrice()));
						walletSet.add(wallet);
						walletRepository.save(wallet);
						
						// Seller CRYPTO wallet
						wallet = walletRepository.findByCoinTypeAndCoinNameAndUser(allSellers.getCoinType(), allSellers.getCoinName(), user);
						wallet.setShadowBalance(wallet.getBalance() - allBuyers.getCoinQuantity());
						wallet.setBalance(wallet.getBalance() - allBuyers.getCoinQuantity()); 
						walletSet.add(wallet);
						walletRepository.save(wallet);
						
						// Currency update
						currency.setProfit(currency.getProfit() + ((allBuyers.getCoinQuantity() * allBuyers.getPrice() * currency.getFees()) / 100));
						currency.setCoinInINR(currency.getCoinInINR() + ((allBuyers.getCoinQuantity() * allBuyers.getPrice()) - 
								(allBuyers.getCoinQuantity() * allSellers.getPrice())));
						currencyRepository.save(currency);
						
						allSellers.setCoinQuantity(allSellers.getCoinQuantity() - allBuyers.getCoinQuantity());
						allBuyers.setCoinQuantity(0d);
						allBuyers.setStatus(Status.APPROVED);
						allSellers.setStatus(Status.PENDING);
						userOrderRepository.save(allBuyers);
						userOrderRepository.save(allSellers);
					}
				}	  
				  else	if(currency.getPrice() < allSellers.getPrice() && currency.getPrice() <= allBuyers.getPrice()) {
						if(currency.getInitialSupply() >= allBuyers.getCoinQuantity() && allBuyers.getCoinQuantity() > 0d) {
							System.out.println("wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww( currency >= )wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww");
							Transaction transaction = new Transaction();
							transaction.setCoinType(allBuyers.getCoinType());
							transaction.setCoinName(allBuyers.getCoinName());
							transaction.setNetAmount(allBuyers.getCoinQuantity() * allBuyers.getPrice());
							transaction.setGrossAmount((allBuyers.getCoinQuantity() * allBuyers.getPrice()) + 
									((allBuyers.getCoinQuantity() * allBuyers.getPrice() * currency.getFees()) / 100));	
							transaction.setFees(currency.getFees());
							transaction.setBuyerId(allBuyers.getOrderId());
							transaction.setSellerId(currency.getCoinId());
							transaction.setCreatedOn(new Date());
							transaction.setStatus(Status.APPROVED);
							transaction.setMessage("Transaction approved and done");
							transaction.setExchangeRate(allBuyers.getPrice());
							transactionRepository.save(transaction);
							
							// Buyer FIAT wallet
							user = allBuyers.getUser();
							wallet = walletRepository.findByCoinTypeAndCoinNameAndUser(CoinType.FIAT, "INR", user); 
							wallet.setShadowBalance(wallet.getBalance() - transaction.getGrossAmount());
							wallet.setBalance(wallet.getBalance() - transaction.getGrossAmount());
							walletSet.add(wallet);
							walletRepository.save(wallet);
							
							// Buyer CRYPTO wallet
							wallet = walletRepository.findByCoinTypeAndCoinNameAndUser(allBuyers.getCoinType(), allBuyers.getCoinName(), user);
							wallet.setShadowBalance(wallet.getBalance() + allBuyers.getCoinQuantity());
							wallet.setBalance(wallet.getBalance() + allBuyers.getCoinQuantity()); 
							walletSet.add(wallet);
							walletRepository.save(wallet);
							
							// Currency update
							currency.setProfit(currency.getProfit() + ((allBuyers.getCoinQuantity() * allBuyers.getPrice() * currency.getFees()) / 100));
							currency.setCoinInINR(currency.getCoinInINR() + ((allBuyers.getCoinQuantity() * allBuyers.getPrice()) - 
									(allBuyers.getCoinQuantity() * currency.getPrice())));
							currency.setInitialSupply(currency.getInitialSupply() - allBuyers.getCoinQuantity());
							currencyRepository.save(currency);
							
							allBuyers.setCoinQuantity(0d);
							allBuyers.setStatus(Status.APPROVED);
							userOrderRepository.save(allBuyers);
							
						}
						else if(currency.getInitialSupply() < allBuyers.getCoinQuantity() && currency.getInitialSupply() > 0d) {
							System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx( currency < )xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
							Transaction transaction = new Transaction();
							transaction.setCoinType(allBuyers.getCoinType());
							transaction.setCoinName(allBuyers.getCoinName());
							transaction.setNetAmount(currency.getInitialSupply() * allBuyers.getPrice());
							transaction.setGrossAmount((currency.getInitialSupply() * allBuyers.getPrice()) + 
									((currency.getInitialSupply() * allBuyers.getPrice() * currency.getFees()) / 100));	
							transaction.setFees(currency.getFees());
							transaction.setBuyerId(allBuyers.getOrderId());
							transaction.setSellerId(currency.getCoinId());
							transaction.setCreatedOn(new Date());
							transaction.setStatus(Status.APPROVED);
							transaction.setMessage("Transaction approved and done");
							transaction.setExchangeRate(allBuyers.getPrice());
							transactionRepository.save(transaction);
							
							// Buyer FIAT wallet
							user = allBuyers.getUser();
							wallet = walletRepository.findByCoinTypeAndCoinNameAndUser(CoinType.FIAT, "INR", user); 
							wallet.setShadowBalance(wallet.getBalance() - transaction.getGrossAmount());
							wallet.setBalance(wallet.getBalance() - transaction.getGrossAmount());
							walletSet.add(wallet);
							walletRepository.save(wallet);
							
							// Buyer CRYPTO wallet
							wallet = walletRepository.findByCoinTypeAndCoinNameAndUser(allBuyers.getCoinType(), allBuyers.getCoinName(), user);
							wallet.setShadowBalance(wallet.getBalance() + currency.getInitialSupply());
							wallet.setBalance(wallet.getBalance() + currency.getInitialSupply()); 
							walletSet.add(wallet);
							walletRepository.save(wallet);
							
							// Currency update
							currency.setProfit(currency.getProfit() + ((currency.getInitialSupply() * allBuyers.getPrice() * currency.getFees()) / 100));
							currency.setCoinInINR(currency.getCoinInINR() + ((currency.getInitialSupply() * allBuyers.getPrice()) - 
									(currency.getInitialSupply() * currency.getPrice())));
							/*currency.setInitialSupply(0d);
							currencyRepository.save(currency);
							*/
							allBuyers.setCoinQuantity(allBuyers.getCoinQuantity() - currency.getInitialSupply());
							currency.setInitialSupply(0d);
							currencyRepository.save(currency);
							allBuyers.setStatus(Status.PENDING);
							userOrderRepository.save(allBuyers);
						}
				   }   

				}
				else 
					return "Currency can not be null";
			}	           
		}           	
	}					
			return "Transaction Success";
		}
		
		public List<Transaction> getAllTransaction() {
			return transactionRepository.findAll();
		}
}
