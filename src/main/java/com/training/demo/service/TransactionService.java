package com.training.demo.service;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.training.demo.enums.OrderStatus;
import com.training.demo.enums.WalletType;
import com.training.demo.model.CoinManagement;
import com.training.demo.model.OrderTable;
import com.training.demo.model.Transaction;
import com.training.demo.model.User;
import com.training.demo.model.Wallet;
import com.training.demo.repository.CoinManagementRepository;
import com.training.demo.repository.OrderRepository;
import com.training.demo.repository.TransactionRepository;
import com.training.demo.repository.UserRepository;
import com.training.demo.repository.WalletRepository;

@Service
public class TransactionService {
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private WalletRepository walletRepository;
	@Autowired
	private CoinManagementRepository coinRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	private TransactionRepository transactionRepository;

	public String gettransaction() {
		CopyOnWriteArrayList<OrderTable> buyers = transactionRepository.getBuyer("BUYER");
		CopyOnWriteArrayList<OrderTable> sellers = transactionRepository.getSeller("SELLER");
		Iterator<OrderTable> buyerIterator = buyers.iterator();
		
		System.out.println(sellers);

		if (sellers.isEmpty()) {
			System.out.println("sellers aren't available.");
		}

		User seller = null;
		User buyer = null;
		OrderTable buyerOrder = null;
		OrderTable sellerOrder = null;
		CoinManagement coinManagement = null;
		Wallet buyerFiatWallet = null;
		Wallet buyerCryptoWallet = null;
		Wallet sellerFiatWallet = null;
		Wallet sellerCryptoWallet = null;

		while (buyerIterator.hasNext()) {
			buyerOrder = buyerIterator.next();
			coinManagement = coinRepository.findOneByCoinName(buyerOrder.getCoinName());
			Set<Wallet> buyerWallets = buyerOrder.getUser().getWallets();
			Iterator<Wallet> buyerWalletIterator = buyerWallets.iterator();
			OrderStatus buyerStatusUpdatedTo = buyerOrder.getOrderStatus();
			
			
			while (buyerWalletIterator.hasNext()) {
				Wallet wall = buyerWalletIterator.next();
				if (wall.getCoinType().equals(WalletType.FIAT.toString())) {
					buyerFiatWallet = wall;
				}
				if (wall.getCoinType().equals(WalletType.CRYPTO.toString())
						&& wall.getCoinName().equals(buyerOrder.getCoinName())) {
					buyerCryptoWallet = wall;
					}
			}
			Iterator<OrderTable> sellerIterator = sellers.iterator();
			if (!sellers.isEmpty()) {

				while (sellerIterator.hasNext()) {
					sellerOrder = sellerIterator.next();

					coinManagement = coinRepository.findOneByCoinName(buyerOrder.getCoinName());
					Set<Wallet> sellerWallets = sellerOrder.getUser().getWallets();
					Iterator<Wallet> sellerWalletIterator = sellerWallets.iterator();

					while (sellerWalletIterator.hasNext()) {

						Wallet wall = sellerWalletIterator.next();

						if (wall.getCoinType().equals(WalletType.FIAT.toString())) {

							sellerFiatWallet = wall;

						}

						if (wall.getCoinType().equals(WalletType.CRYPTO.toString())
								&& wall.getCoinName().equals(buyerOrder.getCoinName())) {

							sellerCryptoWallet = wall;

						}

					}

					
					if ((sellerOrder.getPrice() <= coinManagement.getPrice() || coinManagement.getInitialSupply() == 0)
							&& sellerOrder.getPrice() <= buyerOrder.getPrice()
							&& sellerOrder.getCoinName().equals(buyerOrder.getCoinName())
							&& buyerOrder.getUser().getUserId() != sellerOrder.getUser().getUserId()
							&& buyerStatusUpdatedTo.equals(OrderStatus.PENDING)) {
					
						if (sellerOrder.getCoinQuantity() == buyerOrder.getCoinQuantity()) {

							seller = sellerOrder.getUser();
							buyer = buyerOrder.getUser();

							Double sellerFiatMainBal = sellerFiatWallet.getBalance();
							Double sellerFiatShadowBal = sellerFiatWallet.getShadowBalance();
							Double sellerCryptoMainBal = sellerCryptoWallet.getBalance();							

							Double buyerFiatMainBal = buyerFiatWallet.getBalance();

							Double buyerCryptoMainBal = buyerCryptoWallet.getBalance();

							Double buyerCryptoShadowBal = buyerCryptoWallet.getShadowBalance();

							sellerFiatWallet.setBalance(sellerFiatMainBal + sellerOrder.getGrossAmount());

							sellerFiatWallet.setShadowBalance(sellerFiatShadowBal + sellerOrder.getGrossAmount());

							sellerCryptoWallet.setBalance(sellerCryptoMainBal - sellerOrder.getCoinQuantity());

							buyerFiatWallet.setBalance(buyerFiatMainBal - buyerOrder.getGrossAmount());

							buyerCryptoWallet.setBalance(buyerCryptoMainBal + buyerOrder.getCoinQuantity());

							buyerCryptoWallet.setShadowBalance(buyerCryptoShadowBal + buyerOrder.getCoinQuantity());

							Double fees = buyerOrder.getFees();

							coinManagement.setCoinInINR(coinManagement.getCoinInINR() + (buyerOrder.getNetAmount() - sellerOrder.getNetAmount()));

							coinManagement.setProfit(coinManagement.getProfit() + fees);

							buyerStatusUpdatedTo = OrderStatus.APPROVED;
							buyerOrder.setOrderStatus(OrderStatus.APPROVED);
							sellerOrder.setOrderStatus(OrderStatus.APPROVED);

							this.sellerBuyerUpdate(sellerFiatWallet, sellerCryptoWallet, buyerFiatWallet,
									buyerCryptoWallet, coinManagement, buyerOrder, sellerOrder);

							this.createTransaction(buyerOrder.getUser().getUserId(), buyerOrder.getCoinName(),
									buyerOrder.getCoinQuantity(), WalletType.CRYPTO,
									"Buyer transaction is completed from seller side.", buyerOrder.getPrice(),
									buyerOrder.getFees(), buyerOrder.getNetAmount(), buyerOrder.getGrossAmount(),
									sellerOrder.getUser().getUserId(), OrderStatus.COMPLETED);
							System.out.println("Both buyer and seller Transaction is completed.");

							// return "Both buyer and seller Transaction is completed.";

							break;
						}
						if (sellerOrder.getCoinQuantity() < buyerOrder.getCoinQuantity()) {

							seller = sellerOrder.getUser();

							buyer = buyerOrder.getUser();

							Double sellerFiatMainBal = sellerFiatWallet.getBalance();

							Double sellerFiatShadowBal = sellerFiatWallet.getShadowBalance();

							Double sellerCryptoMainBal = sellerCryptoWallet.getBalance();

							Double buyerFiatMainBal = buyerFiatWallet.getBalance();
							
							Double buyerCryptoMainBal = buyerCryptoWallet.getBalance();
							
							Double buyerCryptoShadowBal = buyerCryptoWallet.getShadowBalance();
							

							sellerFiatWallet.setBalance(sellerFiatMainBal + sellerOrder.getGrossAmount());
							
							sellerFiatWallet.setShadowBalance(sellerFiatShadowBal + sellerOrder.getGrossAmount());
							
							sellerCryptoWallet.setBalance(sellerCryptoMainBal - sellerOrder.getCoinQuantity());

							Double netAmount = sellerOrder.getNetAmount();
							
							Double fees = (netAmount * (coinManagement.getFees() / 100));
							
							Double AmountAndFee = netAmount + fees;
							

							buyerFiatWallet.setBalance(buyerFiatMainBal - AmountAndFee);
							
							buyerCryptoWallet.setBalance(buyerCryptoMainBal + sellerOrder.getCoinQuantity());
							
							buyerCryptoWallet.setShadowBalance(buyerCryptoShadowBal + sellerOrder.getCoinQuantity());
							
							coinManagement.setCoinInINR(coinManagement.getCoinInINR()
									+ (buyerOrder.getPrice() * sellerOrder.getCoinQuantity()
											- sellerOrder.getPrice() * sellerOrder.getCoinQuantity()));
							
							coinManagement.setProfit(coinManagement.getProfit() + fees);
							
							buyerOrder.setCoinQuantity(buyerOrder.getCoinQuantity() - sellerOrder.getCoinQuantity());
							
							buyerOrder.setFees(fees);
							
							buyerOrder.setGrossAmount(buyerOrder.getGrossAmount() - AmountAndFee);
							
							buyerOrder.setNetAmount(buyerOrder.getNetAmount() - sellerOrder.getGrossAmount());
							
							buyerStatusUpdatedTo = OrderStatus.PENDING;
							buyerOrder.setOrderStatus(OrderStatus.PENDING);

							sellerOrder.setOrderStatus(OrderStatus.APPROVED);

							this.sellerBuyerUpdate(sellerFiatWallet, sellerCryptoWallet, buyerFiatWallet,
									buyerCryptoWallet, coinManagement, buyerOrder, sellerOrder);

							this.createTransaction(buyerOrder.getUser().getUserId(), buyerOrder.getCoinName(),
									sellerOrder.getCoinQuantity(), WalletType.CRYPTO,
									"Buyer transaction is completed from seller side.", buyerOrder.getPrice(), fees,
									netAmount, AmountAndFee, sellerOrder.getUser().getUserId(), OrderStatus.APPROVED);

							System.out.println("seller completed and buyer Transaction is pending.");
							// return "seller Transaction is completed and buyer pending.";

						}

						if (sellerOrder.getCoinQuantity() > buyerOrder.getCoinQuantity()) {
							
							seller = sellerOrder.getUser();
							
							buyer = buyerOrder.getUser();
							
							Double sellerFiatMainBal = sellerFiatWallet.getBalance();
							
							Double sellerFiatShadowBal = sellerFiatWallet.getShadowBalance();
							
							Double sellerCryptoMainBal = sellerCryptoWallet.getBalance();

							Double buyerFiatMainBal = buyerFiatWallet.getBalance();
							
							Double buyerCryptoMainBal = buyerCryptoWallet.getBalance();
							
							Double buyerCryptoShadowBal = buyerCryptoWallet.getShadowBalance();
							
							sellerFiatWallet.setBalance(sellerFiatMainBal + buyerOrder.getNetAmount());
							
							sellerFiatWallet.setShadowBalance(sellerFiatShadowBal + buyerOrder.getNetAmount());
							
							sellerCryptoWallet.setBalance(sellerCryptoMainBal - buyerOrder.getCoinQuantity());
							
							buyerFiatWallet.setBalance(buyerFiatMainBal - buyerOrder.getGrossAmount());
							
							buyerCryptoWallet.setBalance(buyerCryptoMainBal + buyerOrder.getCoinQuantity());
							
							buyerCryptoWallet.setShadowBalance(buyerCryptoShadowBal + buyerOrder.getCoinQuantity());
							
							coinManagement.setCoinInINR(buyerOrder.getNetAmount()
									- (sellerOrder.getPrice() * buyerOrder.getCoinQuantity()));
							
							coinManagement.setProfit(buyerOrder.getFees());

							sellerOrder.setCoinQuantity(sellerOrder.getCoinQuantity() - buyerOrder.getCoinQuantity());
							
							sellerOrder.setNetAmount(sellerOrder.getNetAmount() - buyerOrder.getNetAmount());
							
							sellerOrder.setGrossAmount(sellerOrder.getGrossAmount() - buyerOrder.getNetAmount());
							
							buyerStatusUpdatedTo = OrderStatus.APPROVED;
							buyerOrder.setOrderStatus(OrderStatus.APPROVED);
							
							sellerOrder.setOrderStatus(OrderStatus.PENDING);

							this.sellerBuyerUpdate(sellerFiatWallet, sellerCryptoWallet, buyerFiatWallet,
									buyerCryptoWallet, coinManagement, buyerOrder, sellerOrder);
							
							this.createTransaction(buyerOrder.getUser().getUserId(), buyerOrder.getCoinName(),
									buyerOrder.getCoinQuantity(), WalletType.CRYPTO,
									"Buyer transaction is completed from seller side.", buyerOrder.getPrice(),
									buyerOrder.getFees(), buyerOrder.getNetAmount(), buyerOrder.getGrossAmount(),
							sellerOrder.getUser().getUserId(), OrderStatus.COMPLETED);
							
							System.out.println("buyer Transaction is completed but seller is pending.");
							// return "buyer Transaction is completed but seller is pending.";
						}
					}
				}
			}
			if (buyerStatusUpdatedTo.equals(OrderStatus.PENDING) && coinManagement.getInitialSupply() > 0) {
				Integer adminId = 0;
				if (coinManagement.getInitialSupply() == buyerOrder.getCoinQuantity()
						&& buyerOrder.getPrice() >= coinManagement.getPrice()) {
					Double initSupply = coinManagement.getInitialSupply();
					initSupply = coinManagement.getInitialSupply() - buyerOrder.getCoinQuantity();

					Double buyerFiatMainBal = buyerFiatWallet.getBalance();
					Double buyerCryptoMainBal = buyerCryptoWallet.getBalance();
					Double buyerCryptoShadowBal = buyerCryptoWallet.getShadowBalance();
					Double AmountAndFee = buyerOrder.getGrossAmount();

					// WALLET UPGRADATIONS
					buyerFiatWallet.setBalance(buyerFiatMainBal - AmountAndFee);
					buyerCryptoWallet.setBalance(buyerCryptoMainBal + buyerOrder.getCoinQuantity());
					buyerCryptoWallet.setShadowBalance(buyerCryptoShadowBal + buyerOrder.getCoinQuantity());

					// COIN MANAGEMENT UPGRADATIONS
					coinManagement.setInitialSupply(initSupply);
					coinManagement.setCoinInINR(
							coinManagement.getCoinInINR() + (buyerOrder.getPrice() * buyerOrder.getCoinQuantity()
									- coinManagement.getPrice() * buyerOrder.getCoinQuantity()));
					coinManagement.setProfit(coinManagement.getFees() + buyerOrder.getFees());

					// ORDER UPGRADATIONS
					buyerStatusUpdatedTo = OrderStatus.APPROVED;
					buyerOrder.setOrderStatus(OrderStatus.APPROVED);

					this.adminBuyerUpdate(buyerFiatWallet, buyerCryptoWallet, coinManagement, buyerOrder);

					this.createTransaction(buyerOrder.getUser().getUserId(), buyerOrder.getCoinName(),
							buyerOrder.getCoinQuantity(), WalletType.CRYPTO,
							"Buyer transaction is completed from coinManagement", buyerOrder.getPrice(),
							buyerOrder.getFees(), buyerOrder.getNetAmount(), buyerOrder.getGrossAmount(), adminId,
							OrderStatus.APPROVED);

					System.out.println("Buyer transaction is completed from coinManagement");
					// return "Buyer transaction is completed from coinManagement";

				}

				if (coinManagement.getInitialSupply() < buyerOrder.getCoinQuantity()
						&& buyerOrder.getPrice() >= coinManagement.getPrice()) {
					Double initSupply = coinManagement.getInitialSupply();
					Double buyerFiatMainBal = buyerFiatWallet.getBalance();
					Double buyerCryptoMainBal = buyerCryptoWallet.getBalance();
					Double buyerCryptoShadowBal = buyerCryptoWallet.getShadowBalance();
					Double netAmount = (initSupply * buyerOrder.getPrice());
					Double fees = (netAmount * (coinManagement.getFees() / 100));
					Double grossAmount = (initSupply * buyerOrder.getPrice()) + fees;

					// WALLET UPGRADATION
					buyerFiatWallet.setBalance(buyerFiatMainBal - grossAmount);
					buyerCryptoWallet.setBalance(buyerCryptoMainBal + initSupply);
					buyerCryptoWallet.setShadowBalance(buyerCryptoShadowBal + initSupply);

					// ORDER TABLE UPGRADATION
					buyerOrder.setCoinQuantity(buyerOrder.getCoinQuantity() - initSupply);
					buyerOrder.setNetAmount(buyerOrder.getNetAmount() - netAmount);
					buyerOrder.setFees(buyerOrder.getFees() - fees);
					buyerOrder.setGrossAmount(buyerOrder.getGrossAmount() - grossAmount);
					buyerStatusUpdatedTo = OrderStatus.PENDING;
					buyerOrder.setOrderStatus(OrderStatus.PENDING);

					// COIN TABLE UPGRADATION
					coinManagement.setCoinInINR(coinManagement.getCoinInINR()
							+ (buyerOrder.getPrice() * initSupply - coinManagement.getPrice() * initSupply));
					coinManagement.setInitialSupply(0d);
					coinManagement.setProfit(coinManagement.getProfit() + fees);

					this.adminBuyerUpdate(buyerFiatWallet, buyerCryptoWallet, coinManagement, buyerOrder);

					this.createTransaction(buyerOrder.getUser().getUserId(), buyerOrder.getCoinName(), initSupply,
							WalletType.CRYPTO, "Buyer transaction is pending because of insufficient coin in currency.",
							buyerOrder.getPrice(), fees, netAmount, grossAmount, adminId, OrderStatus.APPROVED);

					System.out.println("Buyer transaction is pending due to insufficient coins in transaction");
					// return "Buyer transaction is pending due to insufficient coins in
					// transaction";
				}

				if (coinManagement.getInitialSupply() >= buyerOrder.getCoinQuantity()
						&& buyerOrder.getPrice() >= coinManagement.getPrice()) {
					Double initSupply = coinManagement.getInitialSupply();
					Double buyerFiatMainBal = buyerFiatWallet.getBalance();
					Double buyerCryptoMainBal = buyerCryptoWallet.getBalance();

					// WALLET UPGRADATION
					buyerFiatWallet.setBalance(buyerFiatMainBal - buyerOrder.getGrossAmount());
					Double buyerCryptoShadowBal = buyerCryptoWallet.getShadowBalance();
					buyerCryptoWallet.setBalance(buyerCryptoMainBal + buyerOrder.getCoinQuantity());
					buyerCryptoWallet.setShadowBalance(buyerCryptoShadowBal + buyerOrder.getCoinQuantity());

					// ORDER UPGRADATION
					buyerStatusUpdatedTo = OrderStatus.PENDING;
					buyerOrder.setOrderStatus(OrderStatus.APPROVED);

					// COIN MANAGEMENT UPGRADATION
					coinManagement.setCoinInINR(coinManagement.getCoinInINR()
							+ (buyerOrder.getNetAmount() - coinManagement.getPrice() * buyerOrder.getCoinQuantity()));
					initSupply = initSupply - buyerOrder.getCoinQuantity();
					coinManagement.setInitialSupply(initSupply);
					coinManagement.setProfit(coinManagement.getProfit() + buyerOrder.getFees());

					this.adminBuyerUpdate(buyerFiatWallet, buyerCryptoWallet, coinManagement, buyerOrder);
					this.createTransaction(buyerOrder.getUser().getUserId(), buyerOrder.getCoinName(),
							buyerOrder.getCoinQuantity(), WalletType.CRYPTO,
							"Buyer transaction is completed from coinManagement", buyerOrder.getPrice(),
							buyerOrder.getFees(), buyerOrder.getNetAmount(), buyerOrder.getGrossAmount(), adminId,
							OrderStatus.APPROVED);

					System.out
							.println("Buyer transaction is completed from coinManagement because it has enough coins.");
					// return "Buyer transaction is completed from coinManagement because it has
					// enough coins.";
				}

			}
		}

		return "TRANSACTIONS COMPLETED.";
	}

	public void adminBuyerUpdate(Wallet buyerFiatWallet, Wallet buyerCryptoWallet, CoinManagement coinManagement,
			OrderTable buyerOrder) {
		
		orderRepository.save(buyerOrder);
		walletRepository.save(buyerFiatWallet);
		walletRepository.save(buyerCryptoWallet);
		coinRepository.save(coinManagement);

	}

	public void sellerBuyerUpdate(Wallet sellerFiatWallet, Wallet sellerCryptoWallet, Wallet buyerFiatWallet,
			Wallet buyerCryptoWallet, CoinManagement coinManagement, OrderTable buyerOrder, OrderTable sellerOrder) {
	
		orderRepository.save(buyerOrder);
		orderRepository.save(sellerOrder);
		walletRepository.save(sellerFiatWallet);
		walletRepository.save(sellerCryptoWallet);
		walletRepository.save(buyerFiatWallet);
		walletRepository.save(buyerCryptoWallet);
		coinRepository.save(coinManagement);

	}

	public void createTransaction(Integer buyerId, String coinName, Double coinQuantity, WalletType coinType,
			String description, Double exchangeRate, Double fees, Double netAmount, Double grossAmount,
			Integer sellerId, OrderStatus orderStatus) {
		
		Transaction transaction = new Transaction();
		transaction.setBuyerId(buyerId);
		transaction.setCoinName(coinName);
		transaction.setCoinQuantity(coinQuantity);
		transaction.setCoinType(coinType);
		transaction.setDescription(description);
		transaction.setExchangeRate(exchangeRate);
		transaction.setFees(fees);
		transaction.setNetAmount(netAmount);
		transaction.setGrossAmount(grossAmount);
		transaction.setSellerId(sellerId);
		transaction.setTransactionCreatedOn(new Date());
		transaction.setTransactionStatus(orderStatus);
		transactionRepository.save(transaction);
	
	}

	public List<Transaction> getAllTransactions() {
		List<Transaction> transactions = transactionRepository.findAll();
		return transactions;
	}

}