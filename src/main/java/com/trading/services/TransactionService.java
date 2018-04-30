package com.trading.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import com.trading.repository.CurrencyRepository;
import com.trading.repository.OrderRepository;
import com.trading.repository.TransactionRepository;
import com.trading.repository.UserRepository;
import com.trading.repository.WalletRepository;
import com.trading.utilities.PriceComparator;
import com.trading.utilities.UserOrderComparator;

@Service
public class TransactionService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private CurrencyRepository currencyRepository;
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private WalletRepository walletRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	
	public Map<String, Object> transaction()
	
	{		Map<String, Object> result = new HashMap<String, Object>();
	
	Transaction transaction = new Transaction();
 
		Currency currency = new Currency();
		List<UserOrder> buyer = orderRepository.findByOrderTypeAndStatusAndCoinType(OrderType.BUYER,
				TransactionOrderStatus.PENDING, WalletType.CRYPTO);
		UserOrder sell = null;
		if (buyer.isEmpty()) {
			result.put("isSuccess", false);
			result.put("message", "Buyer Not Found");
			return result;
		}

		for (UserOrder buy : buyer) {
			
			long buyPrice = buy.getPrice();
			currency = currencyRepository.findByCoinName(buy.getCoinName());
			long coinPrice = currency.getPrice();
			List<UserOrder> seller = orderRepository.findByOrderTypeAndStatusAndCoinName(OrderType.SELLER,
					TransactionOrderStatus.PENDING, buy.getCoinName());
			Collections.sort(seller, new UserOrderComparator(new PriceComparator() ));
			List<UserOrder> sellerData = new ArrayList<UserOrder>();

			
			for (UserOrder sellerInfo : seller) {
				if (sellerInfo.getUser() != buy.getUser()) {
					sellerData.add(sellerInfo);
					sell = sellerInfo;
					
				}
			}
			User user = userRepository.findOneByUserId(buy.getUser().getUserId());
			Wallet wallet = walletRepository.findByCoinNameAndUser(buy.getCoinName(), user);
			Wallet walletFiat = walletRepository.findByCoinTypeAndUser(WalletType.FIAT, user);
			if ((sellerData.isEmpty())) {
				if (buyPrice > coinPrice) {
					if (buy.getCoinQuantity() < currency.getInitialSupply()) {
						long fee = currency.getFee();
						long coinQuantity = buy.getCoinQuantity();
						long netAmount = (buyPrice * coinQuantity);
						long grossAmount = (buyPrice * coinQuantity) + (coinQuantity * fee);
						transaction.setCoinType(WalletType.CRYPTO);
						transaction.setCoinName(buy.getCoinName());
						transaction.setStatus(TransactionOrderStatus.APPROVED);
						transaction.setTransactionFee(currency.getFee());
						transaction.setNetAmount(netAmount);
						transaction.setGrossAmount(grossAmount);
						transaction.setExchangeRate(buyPrice);
						transaction.setBuyerId(buy.getOrderId());
						transaction.setTransactionCreatedOn(new Date().toString());
						transactionRepository.save(transaction);
						buy.setStatus(TransactionOrderStatus.APPROVED);
						walletFiat.setBalance(wallet.getShadowBalance());
						wallet.setBalance(wallet.getBalance() + buy.getCoinQuantity());
						wallet.setShadowBalance(wallet.getBalance() + buy.getCoinQuantity());
						currency.setProfit(fee * coinQuantity);
						currency.setCoinINR(buyPrice - currency.getPrice());
						currency.setInitialSupply(currency.getInitialSupply() - buy.getCoinQuantity());
						orderRepository.save(buy);
						walletRepository.save(wallet);
						walletRepository.save(walletFiat);
						currencyRepository.save(currency);
						result.put("isSuccess", true);
						result.put("message", "Transaction created succesfully");
						return result;
				
					} else if (buy.getCoinQuantity() > currency.getInitialSupply()) {
						long fee = currency.getFee();
						long coinQuantity = buy.getCoinQuantity();
						long netAmount = (buyPrice * coinQuantity);
						long grossAmount = (buyPrice * coinQuantity) + (coinQuantity * fee);
						transaction.setCoinType(WalletType.CRYPTO);
						transaction.setCoinName(buy.getCoinName());
						transaction.setStatus(TransactionOrderStatus.APPROVED);
						transaction.setTransactionFee(currency.getFee());
						transaction.setNetAmount(netAmount);
						transaction.setGrossAmount(grossAmount);
						transaction.setExchangeRate(buyPrice);
						transaction.setBuyerId(buy.getOrderId());
						transaction.setTransactionCreatedOn(new Date().toString());
						transactionRepository.save(transaction);
						long balance = currency.getInitialSupply() * buy.getPrice();
						walletFiat.setBalance(wallet.getBalance() - balance);
						wallet.setBalance(wallet.getBalance() + currency.getInitialSupply());
						wallet.setShadowBalance(wallet.getBalance() + currency.getInitialSupply());
						buy.setCoinQuantity(buy.getCoinQuantity()-currency.getInitialSupply());
						currency.setProfit(fee * currency.getInitialSupply());
						currency.setCoinINR(buyPrice - currency.getPrice());
						currency.setInitialSupply( buy.getCoinQuantity() - currency.getInitialSupply() );
						orderRepository.save(buy);
						walletRepository.save(wallet);
						walletRepository.save(walletFiat);
						currencyRepository.save(currency);

						result.put("isSuccess", true);
						result.put("message", "Transaction created succesfully");
						return result;
					} else {
						long fee = currency.getFee();
						long coinQuantity = buy.getCoinQuantity();

						long netAmount = (buyPrice * coinQuantity);
						long grossAmount = (buyPrice * coinQuantity) + (coinQuantity * fee);
						transaction.setCoinType(WalletType.CRYPTO);
						transaction.setCoinName(buy.getCoinName());
						transaction.setStatus(TransactionOrderStatus.APPROVED);
						transaction.setTransactionFee(currency.getFee());
						transaction.setNetAmount(netAmount);
						transaction.setGrossAmount(grossAmount);
						transaction.setExchangeRate(buyPrice);
						transaction.setBuyerId(buy.getOrderId());
						transaction.setTransactionCreatedOn(new Date().toString());
						transactionRepository.save(transaction);
						buy.setStatus(TransactionOrderStatus.APPROVED);
						walletFiat.setBalance(wallet.getShadowBalance());
						wallet.setBalance(wallet.getBalance() + buy.getCoinQuantity());
						wallet.setShadowBalance(wallet.getBalance() + buy.getCoinQuantity());
						currency.setProfit(fee * coinQuantity);
						currency.setCoinINR(buyPrice - currency.getPrice());
						currency.setInitialSupply(currency.getInitialSupply() - buy.getCoinQuantity());
						orderRepository.save(buy);
						walletRepository.save(wallet);
						walletRepository.save(walletFiat);

						result.put("isSuccess", true);
						result.put("message", "Transaction created succesfully");
						return result;
					}
				}
			} else {
			//	for (UserOrder sell : sellerData) {
					User sellerUser = userRepository.findByUserId(sell.getUser().getUserId()); 
				Wallet	sellerWallet = walletRepository.findByCoinNameAndUser(sell.getCoinName(), user);
				Wallet sellerWalletFiat = walletRepository.findByCoinTypeAndUser(WalletType.FIAT, sellerUser);
				System.out.println(sell.getPrice()+ "hhhhhhhhhhhhhhheeeeeee");	
				System.out.println(sell.getCoinName()+ "hhhhhhhhhhhhhhheeeeeee");	
				System.out.println(sell.getCoinName()+ "hhhhhhhhhhhhhheeeeeee");	

				if(sell.getCoinName().equals( buy.getCoinName())) {
							
								System.out.println(sell+ " heeeeeeeeeeeeeeellllllllllo");
								
							
					if (sell.getPrice() >= currency.getPrice()) {
						System.out.println("hiiiiiiiiiiiiiiiiiiii" + sell.getPrice());
						if (buy.getPrice() > currency.getPrice()) {
							if (buy.getCoinQuantity() < currency.getInitialSupply()) {
								long fee = currency.getFee();
								long coinQuantity = buy.getCoinQuantity();

								long netAmount = (buyPrice * coinQuantity);
								long grossAmount = (buyPrice * coinQuantity) + (coinQuantity * fee);
								transaction.setCoinType(WalletType.CRYPTO);
								transaction.setCoinName(buy.getCoinName());
								transaction.setStatus(TransactionOrderStatus.APPROVED);
								transaction.setTransactionFee(currency.getFee());
								transaction.setNetAmount(netAmount);
								transaction.setGrossAmount(grossAmount);
								transaction.setExchangeRate(buyPrice);
								transaction.setBuyerId(buy.getOrderId());
								transaction.setTransactionCreatedOn(new Date().toString());
								transactionRepository.save(transaction);
								buy.setStatus(TransactionOrderStatus.APPROVED);
								walletFiat.setBalance(wallet.getShadowBalance());
								wallet.setBalance(wallet.getBalance() + buy.getCoinQuantity());
								wallet.setShadowBalance(wallet.getBalance() + buy.getCoinQuantity());
								currency.setProfit(fee * coinQuantity);
								currency.setCoinINR(buyPrice - currency.getPrice());
								currency.setInitialSupply(currency.getInitialSupply() - buy.getCoinQuantity());
								orderRepository.save(buy);
								walletRepository.save(wallet);
								walletRepository.save(walletFiat);
								currencyRepository.save(currency);
								orderRepository.save(sell);
								result.put("isSuccess", true);
								result.put("message", "Transaction created succesfully");
								return result;
							} else if (buy.getCoinQuantity() > currency.getInitialSupply()) {
								long fee = currency.getFee();
								long coinQuantity = buy.getCoinQuantity();

								long netAmount = (buyPrice * coinQuantity);
								long grossAmount = (buyPrice * coinQuantity) + (coinQuantity * fee);
								transaction.setCoinType(WalletType.CRYPTO);
								transaction.setCoinName(buy.getCoinName());
								transaction.setStatus(TransactionOrderStatus.APPROVED);
								transaction.setTransactionFee(currency.getFee());
								transaction.setNetAmount(netAmount);
								transaction.setGrossAmount(grossAmount);
								transaction.setExchangeRate(buyPrice);
								transaction.setBuyerId(buy.getOrderId());
								transaction.setTransactionCreatedOn(new Date().toString());
								transactionRepository.save(transaction);
								long balance = currency.getInitialSupply() * buy.getPrice();
								walletFiat.setBalance(wallet.getBalance() - balance);
								wallet.setBalance(wallet.getBalance() + currency.getInitialSupply());
								wallet.setShadowBalance(wallet.getBalance() + currency.getInitialSupply());
								currency.setProfit(fee * currency.getInitialSupply());
								currency.setCoinINR(buyPrice - currency.getPrice());
								currency.setInitialSupply( buy.getCoinQuantity() - currency.getInitialSupply() );
								orderRepository.save(buy);
								walletRepository.save(wallet);
								walletRepository.save(walletFiat);
								currencyRepository.save(currency);

								result.put("isSuccess", true);
								result.put("message", "Transaction created succesfully");
								return result;
							} else {
								long fee = currency.getFee();
								long coinQuantity = buy.getCoinQuantity();

								long netAmount = (buyPrice * coinQuantity);
								long grossAmount = (buyPrice * coinQuantity) + (coinQuantity * fee);
								transaction.setCoinType(WalletType.CRYPTO);
								transaction.setCoinName(buy.getCoinName());
								transaction.setStatus(TransactionOrderStatus.APPROVED);
								transaction.setTransactionFee(currency.getFee());
								transaction.setNetAmount(netAmount);
								transaction.setGrossAmount(grossAmount);
								transaction.setExchangeRate(buyPrice);
								transaction.setBuyerId(buy.getOrderId());
								transaction.setTransactionCreatedOn(new Date().toString());
								result.put("isSuccess", true);
								transactionRepository.save(transaction);
								walletFiat.setBalance(walletFiat.getShadowBalance());
								wallet.setBalance(wallet.getBalance() + buy.getCoinQuantity());
								wallet.setShadowBalance(wallet.getBalance() + buy.getCoinQuantity());
								currency.setProfit(fee * coinQuantity);
								currency.setCoinINR(buyPrice - currency.getPrice());
								currency.setInitialSupply(currency.getInitialSupply() - buy.getCoinQuantity());
								orderRepository.save(buy);
								walletRepository.save(wallet);
								walletRepository.save(walletFiat);
								
								result.put("message", "Transaction created succesfully");
								return result;
							}
						}
					} else {
						if (buy.getPrice() > sell.getPrice()) {
							if (buy.getCoinQuantity() < sell.getCoinQuantity()) {
								System.out.println("vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv");

								long fee = currency.getFee();
								long coinQuantity = buy.getCoinQuantity();

								long netAmount = (buyPrice * coinQuantity);
								long grossAmount = (buyPrice * coinQuantity) + (coinQuantity * fee);
								transaction.setCoinType(WalletType.CRYPTO);
								transaction.setCoinName(buy.getCoinName());
								transaction.setStatus(TransactionOrderStatus.APPROVED);
								transaction.setTransactionFee(currency.getFee());
								transaction.setNetAmount(netAmount);
								transaction.setGrossAmount(grossAmount);
								transaction.setExchangeRate(buyPrice);
								transaction.setBuyerId(buy.getOrderId());
								transaction.setTransactionCreatedOn(new Date().toString());
								transaction.setSellerId(sell.getOrderId());
								transactionRepository.save(transaction);
								walletFiat.setBalance(wallet.getShadowBalance());
								wallet.setBalance(wallet.getBalance() + buy.getCoinQuantity());
								wallet.setShadowBalance(wallet.getBalance() + buy.getCoinQuantity());
								currency.setProfit(fee * coinQuantity);
								currency.setCoinINR(buyPrice - sell.getPrice());
								sell.setCoinQuantity(sell.getCoinQuantity() - buy.getCoinQuantity());
								sell.setStatus(TransactionOrderStatus.APPROVED);
								buy.setStatus(TransactionOrderStatus.APPROVED);
								sellerWallet.setBalance(sellerWallet.getShadowBalance());
								sellerWalletFiat.setBalance(sellerWalletFiat.getBalance() + (buy.getPrice() * buy.getCoinQuantity()));
								sellerWalletFiat.setShadowBalance(sellerWalletFiat.getBalance() + (buy.getPrice() * buy.getCoinQuantity()));
								currencyRepository.save(currency);
								walletRepository.save(sellerWallet);
								walletRepository.save(sellerWalletFiat);
								orderRepository.save(buy);
								orderRepository.save(sell);
								walletRepository.save(wallet);
								walletRepository.save(walletFiat);
								

								result.put("isSuccess", true);
								result.put("message", "Transaction created succesfully");
								return result;
							} else if (buy.getCoinQuantity() > sell.getCoinQuantity()) {
								long fee = currency.getFee();
								long coinQuantity = buy.getCoinQuantity();

								long netAmount = (buyPrice * coinQuantity);
								long grossAmount = (buyPrice * coinQuantity) + (coinQuantity * fee);
								transaction.setCoinType(WalletType.CRYPTO);
								transaction.setCoinName(buy.getCoinName());
								transaction.setStatus(TransactionOrderStatus.APPROVED);
								transaction.setTransactionFee(currency.getFee());
								transaction.setNetAmount(netAmount);
								transaction.setGrossAmount(grossAmount);
								transaction.setExchangeRate(buyPrice);
								transaction.setBuyerId(buy.getOrderId());
								transaction.setSellerId(sell.getOrderId());
								transaction.setTransactionCreatedOn(new Date().toString());
								transactionRepository.save(transaction);
								buy.setCoinQuantity(sell.getCoinQuantity() - buy.getCoinQuantity());
							
								walletFiat.setBalance(wallet.getShadowBalance());
								wallet.setBalance(wallet.getBalance() + buy.getCoinQuantity());
								wallet.setShadowBalance(wallet.getBalance() + buy.getCoinQuantity());
								currency.setProfit(fee * coinQuantity);
								currency.setCoinINR(buyPrice - sell.getPrice());
								sell.setStatus(TransactionOrderStatus.APPROVED);
								sellerWallet.setBalance(sellerWallet.getBalance() + buy.getCoinQuantity());
								sellerWalletFiat.setBalance(sellerWalletFiat.getBalance() + (buy.getPrice() * sell.getCoinQuantity()));
								sellerWalletFiat.setShadowBalance(sellerWalletFiat.getBalance() + (buy.getPrice() * sell.getCoinQuantity()));
								currencyRepository.save(currency);
								walletRepository.save(sellerWallet);
								walletRepository.save(sellerWalletFiat);
								orderRepository.save(buy);
								orderRepository.save(sell);
								walletRepository.save(wallet);
								walletRepository.save(walletFiat);
								

								result.put("isSuccess", true);
								result.put("message", "Transaction created succesfully");
								return result;
							} else {
								long fee = currency.getFee();
								long coinQuantity = buy.getCoinQuantity();
								System.out.println("vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv");
								long netAmount = (buyPrice * coinQuantity);
								long grossAmount = (buyPrice * coinQuantity) + (coinQuantity * fee);
								transaction.setCoinType(WalletType.CRYPTO);
								transaction.setCoinName(buy.getCoinName());
								transaction.setStatus(TransactionOrderStatus.APPROVED);
								transaction.setTransactionFee(currency.getFee());
								transaction.setNetAmount(netAmount);
								transaction.setGrossAmount(grossAmount);
								transaction.setExchangeRate(buyPrice);
								transaction.setBuyerId(buy.getOrderId());
								transaction.setTransactionCreatedOn(new Date().toString());
								transaction.setSellerId(sell.getOrderId());
								transactionRepository.save(transaction);
								sell.setStatus(TransactionOrderStatus.APPROVED);
								buy.setStatus(TransactionOrderStatus.APPROVED);
								walletFiat.setBalance(wallet.getShadowBalance());
								wallet.setBalance(wallet.getBalance() + buy.getCoinQuantity());
								wallet.setShadowBalance(wallet.getBalance() + buy.getCoinQuantity());
								currency.setProfit(fee * coinQuantity);
								currency.setCoinINR(buyPrice - sell.getPrice());
								sell.setCoinQuantity(sell.getCoinQuantity() - buy.getCoinQuantity());
								sellerWallet.setBalance(sellerWallet.getShadowBalance());
								sellerWalletFiat.setBalance(sellerWalletFiat.getBalance() + (buy.getPrice() * buy.getCoinQuantity()));
								sellerWalletFiat.setShadowBalance(sellerWalletFiat.getBalance() + (buy.getPrice() * buy.getCoinQuantity()));
								currencyRepository.save(currency);
								walletRepository.save(sellerWallet);
								walletRepository.save(sellerWalletFiat);
								orderRepository.save(buy);
								orderRepository.save(sell);
								walletRepository.save(wallet);
								walletRepository.save(walletFiat);
								

								result.put("isSuccess", true);
								result.put("message", "Transaction created succesfully");
								return result;
							}}}}}}

		result.put("isSuccess", true);
		result.put("message", "Success");
		return result;
							
						
		}
	
	public Iterable <Transaction> showAllTransaction()
	{
	  return transactionRepository.findAll();	}
}
