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

	{
		Map<String, Object> result = new HashMap<String, Object>();

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

			double buyPrice = buy.getPrice();
			currency = currencyRepository.findByCoinName(buy.getCoinName());
			if (currency == null) {
				result.put("isSuccess", false);
				result.put("message", "Currency does not exist");
				return result;
			}

			double coinPrice = currency.getPrice();
			List<UserOrder> seller = orderRepository.findByOrderTypeAndStatusAndCoinName(OrderType.SELLER,
					TransactionOrderStatus.PENDING, buy.getCoinName());
			Collections.sort(seller, new UserOrderComparator(new PriceComparator()));
			List<UserOrder> sellerData = new ArrayList<UserOrder>();
			boolean flag = false;
			boolean selluser = false;
			boolean admin = false;

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

				if (currency.getInitialSupply() == 0) {
					result.put("isSuccess", false);
					result.put("message", "No supply");
					return result;
				}

				if (buyPrice > coinPrice) {

					flag = true;
					admin = true;
				}
			} else {

				if (sell.getCoinName().equals(buy.getCoinName())) {

					if (sell.getPrice() >= currency.getPrice()) {
						if (buy.getPrice() > currency.getPrice()) {
							flag = true;
							admin = true;
						}
					} else {
						if (buy.getPrice() > sell.getPrice()) {
							flag = true;
							selluser = true;

						}
					}
				}
			}
			double fee = currency.getFee();
			double coinQuantity = buy.getCoinQuantity();
			double netAmount = (buyPrice * coinQuantity);
			double grossAmount = (buyPrice * coinQuantity) + (coinQuantity * fee);
			if (flag = true) {
				if (admin = true) {

					if (buy.getCoinQuantity() < currency.getInitialSupply()) {

						transaction.setCoinType(WalletType.CRYPTO);
						transaction.setCoinName(buy.getCoinName());
						transaction.setStatus(TransactionOrderStatus.APPROVED);
						transaction.setTransactionFee(currency.getFee());
						transaction.setNetAmount(netAmount);
						transaction.setGrossAmount(grossAmount);
						transaction.setExchangeRate(buyPrice);
						transaction.setBuyerId(buy.getOrderId());
						transaction.setTransactionCreatedOn(new Date().toString());
						transaction.setCoinQuantiy(buy.getCoinQuantity());
						buy.setStatus(TransactionOrderStatus.APPROVED);
						buy.setCoinQuantity(currency.getInitialSupply() - buy.getCoinQuantity());
						walletFiat.setBalance(wallet.getShadowBalance());
						walletFiat.setShadowBalance(wallet.getShadowBalance());
						wallet.setBalance(wallet.getBalance() + buy.getCoinQuantity());
						wallet.setShadowBalance(wallet.getBalance() + buy.getCoinQuantity());
						currency.setProfit(fee * coinQuantity);
						currency.setCoinINR(buyPrice - currency.getPrice());
						currency.setInitialSupply(currency.getInitialSupply() - buy.getCoinQuantity());
						transactionRepository.save(transaction);
						orderRepository.save(buy);
						walletRepository.save(wallet);
						walletRepository.save(walletFiat);
						currencyRepository.save(currency);
						System.out.println(currency.getInitialSupply() + " vanshikamadan");
						result.put("isSuccess", true);
						result.put("message", "Transaction created succesfully");
						return result;

					} else if (buy.getCoinQuantity() > currency.getInitialSupply()) {

						transaction.setCoinType(WalletType.CRYPTO);
						transaction.setCoinName(buy.getCoinName());
						transaction.setStatus(TransactionOrderStatus.APPROVED);
						transaction.setTransactionFee(currency.getFee());
						transaction.setNetAmount(netAmount);
						transaction.setGrossAmount(grossAmount);
						transaction.setExchangeRate(buyPrice);
						transaction.setBuyerId(buy.getOrderId());
						transaction.setTransactionCreatedOn(new Date().toString());
						transaction.setCoinQuantiy(currency.getInitialSupply());
						transactionRepository.save(transaction);
						double balance = currency.getInitialSupply() * buy.getPrice();
						walletFiat.setBalance(wallet.getBalance() - balance);
						wallet.setBalance(wallet.getBalance() + currency.getInitialSupply());
						wallet.setShadowBalance(wallet.getBalance() + currency.getInitialSupply());
						buy.setCoinQuantity(buy.getCoinQuantity() - currency.getInitialSupply());
						currency.setProfit(fee * currency.getInitialSupply());
						currency.setCoinINR(buyPrice - currency.getPrice());
						currency.setInitialSupply(buy.getCoinQuantity() - currency.getInitialSupply());
						orderRepository.save(buy);
						walletRepository.save(wallet);
						walletRepository.save(walletFiat);
						currencyRepository.save(currency);

						result.put("isSuccess", true);
						result.put("message", "Transaction created succesfully");
						return result;
					} else {

						transaction.setCoinType(WalletType.CRYPTO);
						transaction.setCoinName(buy.getCoinName());
						transaction.setStatus(TransactionOrderStatus.APPROVED);
						transaction.setTransactionFee(currency.getFee());
						transaction.setNetAmount(netAmount);
						transaction.setGrossAmount(grossAmount);
						transaction.setExchangeRate(buyPrice);
						transaction.setBuyerId(buy.getOrderId());
						transaction.setTransactionCreatedOn(new Date().toString());
						transaction.setCoinQuantiy(buy.getCoinQuantity());
						transactionRepository.save(transaction);
						buy.setCoinQuantity(0);
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
			}
			if (flag = true) {
				if (selluser = true) {
					User sellerUser = userRepository.findByUserId(sell.getUser().getUserId());
					Wallet sellerWallet = walletRepository.findByCoinNameAndUser(sell.getCoinName(), user);
					Wallet sellerWalletFiat = walletRepository.findByCoinTypeAndUser(WalletType.FIAT, sellerUser);
					if (buy.getCoinQuantity() < sell.getCoinQuantity()) {
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
						transaction.setCoinQuantiy(buy.getCoinQuantity());

						transactionRepository.save(transaction);
						walletFiat.setBalance(wallet.getShadowBalance());
						wallet.setBalance(wallet.getBalance() + buy.getCoinQuantity());
						wallet.setShadowBalance(wallet.getBalance() + buy.getCoinQuantity());
						currency.setProfit(fee * coinQuantity);
						currency.setCoinINR(buyPrice - sell.getPrice());
						sell.setCoinQuantity(sell.getCoinQuantity() - buy.getCoinQuantity());
						sell.setStatus(TransactionOrderStatus.APPROVED);
						buy.setStatus(TransactionOrderStatus.APPROVED);
						buy.setCoinQuantity(0);
						sell.setCoinQuantity(sell.getCoinQuantity() - buy.getCoinQuantity());
						sellerWallet.setBalance(sellerWallet.getShadowBalance());
						sellerWalletFiat
								.setBalance(sellerWalletFiat.getBalance() + (buy.getPrice() * buy.getCoinQuantity()));
						sellerWalletFiat.setShadowBalance(
								sellerWalletFiat.getBalance() + (buy.getPrice() * buy.getCoinQuantity()));
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
						transaction.setCoinQuantiy(sell.getCoinQuantity());

						transactionRepository.save(transaction);
						buy.setCoinQuantity(sell.getCoinQuantity() - buy.getCoinQuantity());
						sell.setCoinQuantity(buy.getCoinQuantity() - sell.getCoinQuantity());
						walletFiat.setBalance(wallet.getShadowBalance());
						wallet.setBalance(wallet.getBalance() + buy.getCoinQuantity());
						wallet.setShadowBalance(wallet.getBalance() + buy.getCoinQuantity());
						currency.setProfit(fee * coinQuantity);
						currency.setCoinINR(buyPrice - sell.getPrice());
						sell.setStatus(TransactionOrderStatus.APPROVED);
						sellerWallet.setBalance(sellerWallet.getBalance() + buy.getCoinQuantity());
						sellerWalletFiat
								.setBalance(sellerWalletFiat.getBalance() + (buy.getPrice() * sell.getCoinQuantity()));
						sellerWalletFiat.setShadowBalance(
								sellerWalletFiat.getBalance() + (buy.getPrice() * sell.getCoinQuantity()));
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
						transaction.setCoinQuantiy(buy.getCoinQuantity());

						transactionRepository.save(transaction);
						sell.setStatus(TransactionOrderStatus.APPROVED);
						sell.setCoinQuantity(0);
						buy.setCoinQuantity(0);
						buy.setStatus(TransactionOrderStatus.APPROVED);
						walletFiat.setBalance(wallet.getShadowBalance());
						wallet.setBalance(wallet.getBalance() + buy.getCoinQuantity());
						wallet.setShadowBalance(wallet.getBalance() + buy.getCoinQuantity());
						currency.setProfit(fee * coinQuantity);
						currency.setCoinINR(buyPrice - sell.getPrice());
						sell.setCoinQuantity(sell.getCoinQuantity() - buy.getCoinQuantity());
						sellerWallet.setBalance(sellerWallet.getShadowBalance());
						sellerWalletFiat
								.setBalance(sellerWalletFiat.getBalance() + (buy.getPrice() * buy.getCoinQuantity()));
						sellerWalletFiat.setShadowBalance(
								sellerWalletFiat.getBalance() + (buy.getPrice() * buy.getCoinQuantity()));
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
					}
				}
			}
		}
		result.put("isSuccess", false);
		result.put("message", "No transactions done");
		return result;

	}

	public Iterable<Transaction> showAllTransaction() {
		return transactionRepository.findAll();
	}
}
