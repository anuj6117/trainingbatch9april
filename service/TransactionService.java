package com.trainingproject.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trainingproject.domain.Currency;
import com.trainingproject.domain.Transaction;
import com.trainingproject.domain.UserOrder;
import com.trainingproject.enums.Status;
import com.trainingproject.repository.CurrencyRepository;
import com.trainingproject.repository.UserOrderRepository;
import com.trainingproject.utils.UserOrderMaxBuyerPrice;
import com.trainingproject.utils.UserOrderMinSellerPrice;
@Service
public class TransactionService {
		//@Autowired
		//private TransactionRepository transactionRepository;
		@Autowired
		private UserOrderRepository userOrderRepository;
		//@Autowired
		private CurrencyRepository currencyRepository;
		//@Autowired
		//private WalletRepository walletRepository;
		UserOrder buyerMaxPrice;
		UserOrder sellerMinPrice;
		Long netAmount;
		Currency currency;
		
		public Transaction startTransaction() {
			
			System.err.println("+++++++++++++++++++++Transaction function+++++++++++++++++++");
			ArrayList<UserOrder> buyers =  (ArrayList<UserOrder>)userOrderRepository
					.findAllByOrderTypeAndStatus("Buyer","PENDING");
			if(buyers.isEmpty()) {
				throw new RuntimeException("Buyer are not available");
			}
			ArrayList<UserOrder> sellers =  (ArrayList<UserOrder>)userOrderRepository
					.findAllByOrderTypeAndStatus("Seller","PENDING");
			if(buyers.isEmpty()) {
				throw new RuntimeException("Buyer are not available");
			}
			Transaction trans = null;
			UserOrder buyer = null;
			UserOrder seller = null;
			Boolean flag = true;
			// ************************************************************************

		System.err.println("+++++++++++++++++++Condition for buyer and seller list+++++++++");
		
			Collections.sort(buyers, new UserOrderMaxBuyerPrice());
			Collections.reverse(buyers);
			Collections.sort(sellers, new UserOrderMinSellerPrice());
			
			System.out.println("sssssssssssss"+buyerMaxPrice+"ccccccccssss"+sellerMinPrice);
			for (UserOrder allBuyers : buyers) {
				for (UserOrder allSellers : sellers) {
					if(allBuyers.getCoinName().equals(allSellers.getCoinName())) {
						if(allBuyers.getCoinQuantity() == allSellers.getCoinQuantity()) {
							currency = currencyRepository.findByCoinName(allBuyers.getCoinName());
							String message = "Transaction approved";
							Transaction transaction = new Transaction();
							transaction.setCoinType(allBuyers.getCoinType());
							transaction.setCoinName(allBuyers.getCoinName());
							transaction.setNetAmount(allBuyers.getNetAmount());
							transaction.setGrossAmount(allBuyers.getGrossAmount());	
							transaction.setFees(allBuyers.getFee());
							transaction.setBuyerId(allBuyers.getOrderId());
							transaction.setSellerId(allSellers.getOrderId());
							transaction.setCreatedOn(new Date());
							transaction.setStatus(Status.APPROVED);
							transaction.setMessage(message);
							//transaction.setExchangeRate(exchangeRate);
							
							allBuyers.setCoinQuantity(0l);
							
						}
						else if(allBuyers.getCoinQuantity() > allSellers.getCoinQuantity()) {
							
						}
						else if(allBuyers.getCoinQuantity() < allSellers.getCoinQuantity()) {
							
						}
					}
				}
			}	
			/*for (UserOrder allBuyers : buyers) {
				System.out.println("::::::::::::::::buyers loop started::::::::::::::::::::::");
				
				for (UserOrder allSellers : sellers) {
					System.out.println("::::::::::::::::sellers loop started::::::::::::::::::");
					
					if (allBuyers.getPrice() >= allSellers.getPrice()
							&& allBuyers.getCoinName().equals(allSellers.getCoinName())
							&& allBuyers.getCoinQuantity() <= allSellers.getCoinQuantity()
							&& allSellers.getStatus().equals("PENDING")) {
						System.out.println("::::::::::::::::::order match:::::::::::::::::::");

						flag = false;
						buyer = allBuyers;
						seller = allSellers;

					}
					System.out.println(":::::::::::::::sellers loop end::::::::::::::::::::::::");
				}
				
				System.err.println("++++++++++++----------------------------");
				if (flag) {
					throw new NullPointerException("No any seller are available ");
				}
				// *****************************************************************************
				System.err.println("+++++++++++++++++Condition for order match+++++++++++++");

				if (buyer.getPrice() == seller.getPrice()) {
					buyer.setStatus(Status.APPROVED);
					userOrderRepository.save(buyer);
					seller.setStatus(Status.APPROVED);
					userOrderRepository.save(seller);
				}
				
				if (buyer.getPrice() <= seller.getPrice()) {
					buyer.setStatus(Status.PENDING);
					seller.setCoinQuantity(seller.getCoinQuantity() - buyer.getCoinQuantity());
					seller.setStatus(Status.APPROVED);
				}
				seller.setCoinQuantity(seller.getCoinQuantity() - buyer.getCoinQuantity());
				seller.setStatus(Status.PENDING);
				userOrderRepository.save(seller);
				buyer.setStatus(Status.APPROVED);
				userOrderRepository.save(buyer);
				// ******************************************************************************
				System.err.println("++++++++++++++walletList through user+++++++++++++");
			*///}   
			/*
				 
			User buyeruserData = buyer.getUserModelInOrderModel();
			// User selleruserData = seller.getUserModelInOrderModel();
			Wallet buyerDefaultWallet = null;
			Wallet buyerCurrentWallet = null;

			if (buyer.getStatus().equals("completed")) {
				List<Wallet> buyerWallets = buyeruserData.getWallet();
				for (Wallet buyerWall : buyerWallets) {
					buyerDefaultWallet = buyerWallets.get(0);
					if (buyerWall.getWalletType().equals(buyer.getCoinName())) {
						buyerCurrentWallet = buyerWall;
					}
				}
			}
			buyerCurrentWallet.setBalance(buyerCurrentWallet.getShadowBalance());
			buyerDefaultWallet.setBalance(buyerDefaultWallet.getShadowBalance());
			walletRepository.save(buyerDefaultWallet);
			walletRepository.save(buyerCurrentWallet);

			//
			// ****************************************************************************************
			System.err.println(
					"+++++++++++++++++++++++++++++++++++++++++++Condition for coinManagement++++++++++++++++++++++++++");

			CoinManagement coin = coinManagementRepository.findByCoinName(seller.getCoinName());
			if (coin.getInitialSupply() >= buyer.getAmount()) {
				Double newIntialSupply = coin.getInitialSupply() - buyer.getAmount();
				coin.setInitialSupply(newIntialSupply);

				Double profit = (buyer.getAmount() * 100) * 2 / 100;
				// Double profit = (buyer.getAmount() * 100) * buyer.getFee() / 100;
				coin.setProfit(profit);
				System.out.println(":::::::::::::::" + profit);

				Double newCoinInInR = coin.getPrice() * buyer.getAmount();
				coin.setCoinInr(newCoinInInR);
				System.out.println(":::::::::::::::" + newCoinInInR);
			} else {
				coin.setInitialSupply((double) 0);
				Double newCoinInInR = coin.getInitialSupply() * coin.getPrice();
				coin.setCoinInr(newCoinInInR);

			}

			coinManagementRepository.save(coin);

			// ***************************************************************************************************
			System.err.println(
					"++++++++++++++++++++++++Create Transaction+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

			Transaction transactionModel = new Transaction();
			transactionModel.setCointype(buyer.getCoinName());
			transactionModel.setInQuantity(buyer.getAmount());
			transactionModel.setStatus("complete");
			transactionModel.setTransactionCreatedOn(new Date());
			double netAmount = buyer.getAmount() * coin.getPrice();
			transactionModel.setNetAmount(netAmount);
			double transFee = (netAmount * 2) / 100;
			transactionModel.setTransationFee((int) transFee);
			transactionModel.setExchangeRate(coin.getPrice());

			Double grossAmount = ((netAmount * 2) / 100) + netAmount;

			// Double grossAmount = (buyer.getGrossAmount() + seller.getGrossAmount()) / 2;
			transactionModel.setGrossAmount(grossAmount);
			transactionModel.setBuyerId(buyeruserData.getUserId());
			transactionModel.setSellerId(0);
			transactionModel.setDescription("None");

			trans = transactionRepository.save(transactionModel);

			System.out.println(":::::::::::::::buyers loop end::::::::::::::::::::::::::::::::::::::::::::");
		}

		return trans;
	}

	public List<Transaction> getAllTransaction() {
		List<Transaction> data = transactionRepository.findAll();
		if (data.isEmpty()) {
			throw new RuntimeException("transaction is empty");
		}

				 */
				

			return trans;
		}
}
