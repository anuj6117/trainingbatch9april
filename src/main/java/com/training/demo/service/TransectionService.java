package com.training.demo.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.training.demo.enums.OrderType;
import com.training.demo.enums.UserOrderStatus;
import com.training.demo.enums.WalletType;
import com.training.demo.model.CoinManagement;
import com.training.demo.model.Transection;
import com.training.demo.model.User;
import com.training.demo.model.UserOrder;
import com.training.demo.model.Wallet;
import com.training.demo.repository.CoinManagementRepository;
import com.training.demo.repository.TransectionRepository;
import com.training.demo.repository.UserOrderRepository;
import com.training.demo.repository.WalletRepository;
//import com.trainingproject.enums.WalletType;

@Service
public class TransectionService implements Comparator<UserOrder> {

	@Autowired
	UserOrderRepository orderRepository;

	@Autowired
	CoinManagementRepository currencyRepository;
	@Autowired
	WalletRepository walletRepository;
	@Autowired
	TransectionRepository transactionRepository;

	// add constraint if buyer is buying at rate less than admin than transaction
	// will not occur
	public String approveTransaction() {

		List<UserOrder> sellers = orderRepository.findByorderTypeAndStatus(OrderType.SELLER, UserOrderStatus.PENDING);
		List<UserOrder> buyers = orderRepository.findByorderTypeAndStatus(OrderType.BUYER, UserOrderStatus.PENDING);

		Collections.sort(sellers, this);
		Collections.sort(buyers, Collections.reverseOrder(this));

		if (sellers.size() == 0) {

			// check if admin has the currencies for buyers
			for (int i = 0; i < buyers.size(); i++) {

				// check if admin has that currency for sale
				String coinName = buyers.get(i).getCoinName();
				CoinManagement admin = currencyRepository.findByCoinName(coinName);
				if (admin == null) {
					// admin dont have that currency
					break;
				}

				if (admin.getInitialSupply() == 0) {
					// admin dont have the currency to sell
					buyers.get(i).setStatus(UserOrderStatus.PENDING);
					return "no match";
				}

				double coinBuyed = buyers.get(i).getCoinQuantity() - admin.getInitialSupply();

				if (coinBuyed <= 0) {
					// all coins will be buyed
					coinBuyed = buyers.get(i).getCoinQuantity();
					if ((buyers.get(i).getPrice() >= admin.getPrice()))
						admin.setInitialSupply(admin.getInitialSupply() - coinBuyed);
					buyers.get(i).setStatus(UserOrderStatus.APPROVE);
				} else {
					coinBuyed = admin.getInitialSupply();
					admin.setInitialSupply(0.0);
					buyers.get(i).setStatus(UserOrderStatus.PENDING);
				}

				// check if buyer has that kind of money or not for purchasing
				double pr = (buyers.get(i).getPrice());
				double fees = currencyRepository.findByCoinName(coinName).getFee();
				double totprice = (buyers.get(i).getPrice() * coinBuyed);
				double fee = ((fees * totprice) / 100);

				double grossAmount = totprice + fee;

				Wallet buyerFiatwallet = walletRepository.findBycoinTypeAndUser(WalletType.FIAT,
						buyers.get(i).getUser());

				Transection trans = new Transection();
				if (buyerFiatwallet.getShadowBalance() < grossAmount) {
					// transaction will be failed

					trans.setBuyer((buyers.get(i).getUser().getUserId()));
					trans.setSeller((admin.getCoinId()));
					trans.setFee(fees);
					trans.setCreatedOn(new Date());
					trans.setCoinName(coinName);
					trans.setWalletType(buyers.get(i).getCoinType());
					trans.setTransactionStatus(UserOrderStatus.FAILED);
					trans.setNetAmount(totprice);
					trans.setGrossAmount(grossAmount);
					trans.setMessage("insufficient funds");
					transactionRepository.save(trans);

					buyers.get(i).setStatus(UserOrderStatus.FAILED);
					orderRepository.save(buyers.get(i));

					break;

				} else {

					// check if admins selling price is lower than buying price of buyer
					if (buyers.get(i).getPrice() < admin.getPrice()) {
						// order will be pending

						trans.setBuyer((buyers.get(i).getUser().getUserId()));
						trans.setSeller((admin.getCoinId()));
						trans.setFee(fees);
						trans.setCreatedOn(new Date());
						trans.setCoinName(coinName);
						trans.setWalletType(buyers.get(i).getCoinType());
						trans.setTransactionStatus(UserOrderStatus.FAILED);
						trans.setNetAmount(totprice);
						trans.setGrossAmount(grossAmount);
						// 888888888888888888
						trans.setExchangeRate(pr);
						trans.setMessage("no match");
						transactionRepository.save(trans);

						buyers.get(i).setStatus(UserOrderStatus.PENDING);
						orderRepository.save(buyers.get(i));
						break;

					}
					// approve transaction

					// update admin currency
					double adminprice = (admin.getPrice() * coinBuyed);
					double coinInINR = (int) (totprice - adminprice);
					admin.setProfit(fee);
					admin.setINRconversion(coinInINR);
					currencyRepository.save(admin);
					trans.setBuyer((buyers.get(i).getUser().getUserId()));
					trans.setSeller((admin.getCoinId()));
					trans.setFee(fees);
					trans.setCreatedOn(new Date());
					trans.setCoinName(coinName);
					trans.setWalletType(buyers.get(i).getCoinType());
					trans.setTransactionStatus(UserOrderStatus.COMPLETED);
					trans.setNetAmount(totprice);
					trans.setGrossAmount(grossAmount);
					// 666666666666
					trans.setExchangeRate(pr);
					trans.setMessage("done");
					transactionRepository.save(trans);

					Wallet buyerwallet = walletRepository.findBycoinNameAndUser(coinName, buyers.get(i).getUser());
					buyerFiatwallet.setBalance(buyerFiatwallet.getBalance() - grossAmount);
					buyerFiatwallet.setShadowBalance(buyerFiatwallet.getShadowBalance() - grossAmount);
					if (buyerwallet == null) {
						buyerwallet = new Wallet();
						// buyerwallet.setCoinQuantity(coinBuyed);
						buyerwallet.setCoinName(coinName);
						buyerwallet.setCoinType(buyers.get(i).getCoinType());
						buyerwallet.setBalance(coinBuyed);
						buyerwallet.setShadowBalance(coinBuyed);
						buyerwallet.setUser(buyers.get(i).getUser());
					} else {
						buyerwallet.setBalance(buyerwallet.getBalance() + coinBuyed);
						// buyerwallet.setCoinName(coinName);
						// buyerwallet.setCoinType( buyers.get(i).getCoinType());
						// buyerwallet.setBalance(buyerwallet.getBalance() + coinBuyed);
						buyerwallet.setShadowBalance(buyerwallet.getShadowBalance() + coinBuyed);
					}
					walletRepository.save(buyerwallet);

					orderRepository.save(buyers.get(i));

				}
			}
			return "  sucess ladmin is there as a seller";
		}

		else if (buyers.size() == 0) {
			//

			return "buyres not available";
		}

		// both buyers and sellers are present
		for (int i = 0; i < sellers.size(); i++) {

			UserOrder seller = sellers.get(i);

			if (sellers.get(i).getStatus().equals(UserOrderStatus.PENDING)) {

				for (int j = 0; j < buyers.size(); j++) {

					UserOrder buyer = buyers.get(i);

					String coinName = buyers.get(i).getCoinName();

					if (buyer.getStatus().equals(UserOrderStatus.PENDING) && seller.getCoinName().equals(coinName)) {

						CoinManagement admin = currencyRepository.findByCoinName(coinName);

						if (admin.getPrice() < seller.getPrice()) {
							// transaction will occur with admin
							buyFromAdmin(admin, buyer);
							continue;
						}
						if (seller.getPrice() > buyer.getPrice()) {
							continue;
						}
						Wallet sellerFiatWallet = walletRepository.findBycoinTypeAndUser(WalletType.FIAT,
								seller.getUser());
						Wallet sellerWallet = walletRepository.findBycoinNameAndUser(coinName, seller.getUser());

						double scq = seller.getCoinQuantity();
						double bcq = buyer.getCoinQuantity();
						double coinBuyed = bcq - scq;

						if (coinBuyed < 0) {
							coinBuyed = bcq;
							scq = scq - coinBuyed;
							buyer.setStatus(UserOrderStatus.APPROVE);
							seller.setStatus(UserOrderStatus.PENDING);

						} else {
							coinBuyed = scq;
							// scq=0;

							if (bcq - scq == 0) {
								buyer.setStatus(UserOrderStatus.APPROVE);
							} else {
								buyer.setStatus(UserOrderStatus.PENDING);
							}
							scq = sellerWallet.getBalance() - scq;
							seller.setStatus(UserOrderStatus.APPROVE);

						}
						double fees = currencyRepository.findByCoinName(coinName).getFee();
						double totprice = (buyers.get(i).getPrice() * coinBuyed);
						double pr = (buyers.get(i).getPrice());
						double fee = ((fees * totprice) / 100);
						double grossAmount = totprice + fee;

						Wallet buyerFiatwallet = walletRepository.findBycoinTypeAndUser(WalletType.FIAT,
								buyer.getUser());
						Wallet buyerwallet = walletRepository.findBycoinNameAndUser(coinName, buyer.getUser());

						Transection trans = new Transection();

						if (buyerFiatwallet.getShadowBalance() < grossAmount) {

							trans.setBuyer((buyer.getUser().getUserId()));
							trans.setSeller(seller.getUser().getUserId());
							trans.setFee(fees);
							trans.setCreatedOn(new Date());
							trans.setCoinName(coinName);
							trans.setWalletType(buyers.get(i).getCoinType());
							trans.setTransactionStatus(UserOrderStatus.FAILED);
							trans.setNetAmount(totprice);
							trans.setExchangeRate(pr);
							trans.setGrossAmount(grossAmount);
							trans.setMessage("insufficient funds");
							transactionRepository.save(trans);

							buyers.get(i).setStatus(UserOrderStatus.FAILED);
							orderRepository.save(buyers.get(i));

							continue;

						}

						if (buyerwallet == null) {
							buyerwallet = new Wallet();
							buyerwallet.setBalance(coinBuyed);
							buyerwallet.setCoinName(coinName);
							buyerwallet.setCoinType(buyers.get(i).getCoinType());
							buyerwallet.setBalance(coinBuyed);
							buyerwallet.setShadowBalance(coinBuyed);
							buyerwallet.setUser(buyers.get(i).getUser());
						} else {
							buyerwallet.setBalance(buyerwallet.getBalance() + coinBuyed);
							// buyerwallet.setCoinName(coinName);
							// buyerwallet.setCoinType( buyers.get(i).getCoinType());
							// buyerwallet.setBalance(buyerwallet.getBalance() + coinBuyed);
							buyerwallet.setShadowBalance(buyerwallet.getShadowBalance() + coinBuyed);
						}

						sellerFiatWallet.setBalance(coinBuyed * seller.getPrice() + sellerFiatWallet.getBalance());
						sellerFiatWallet
								.setShadowBalance(sellerFiatWallet.getShadowBalance() + coinBuyed * seller.getPrice());
						// .setShadowBalance(sellerFiatWallet.getShadowBalance() + coinBuyed *
						// seller.getPrice());
						sellerWallet.setBalance(scq);
						sellerWallet.setShadowBalance(scq);
						// sellerWallet.setCoinQuantity(scq);

						seller.setCoinQuantity(scq);
						buyerFiatwallet.setBalance(buyerFiatwallet.getBalance() - grossAmount);
						buyerFiatwallet.setShadowBalance(buyerFiatwallet.getShadowBalance() - grossAmount);

						walletRepository.save(buyerwallet);
						walletRepository.save(buyerFiatwallet);
						walletRepository.save(sellerFiatWallet);

						// approve transaction

						double sellerprice = (seller.getPrice() * coinBuyed);
						Integer coinInINR = (int) (totprice - sellerprice);
						admin.setProfit(admin.getProfit() + fee);
						admin.setINRconversion(admin.getINRconversion() + coinInINR);

						trans.setBuyer(buyer.getUser().getUserId());
						trans.setSeller(seller.getUser().getUserId());
						trans.setFee(fees);
						trans.setCreatedOn(new Date());
						trans.setCoinName(coinName);
						trans.setWalletType(buyer.getCoinType());
						trans.setTransactionStatus(UserOrderStatus.COMPLETED);
						trans.setNetAmount(totprice);
						trans.setExchangeRate(pr);
						trans.setGrossAmount(grossAmount);
						trans.setMessage("done");
						transactionRepository.save(trans);

						buyer.setCoinQuantity(buyer.getCoinQuantity() - coinBuyed);
						seller.setCoinQuantity(scq);
						orderRepository.save(buyer);
						orderRepository.save(seller);

					}
				}

			}
		}

		return " transection successfull  buyer and seller are present";
	}

	private String buyFromAdmin(CoinManagement admin, UserOrder buyer) {

		String coinName = buyer.getCoinName();
		double coinBuyed = buyer.getCoinQuantity() - admin.getInitialSupply();

		if (admin.getInitialSupply() == 0) {
			// admin dont have the currency to sell
			buyer.setStatus(UserOrderStatus.PENDING);
			return "no match";
		}

		if (coinBuyed <= 0) {
			// all coins will be buyed
			coinBuyed = buyer.getCoinQuantity();
			admin.setInitialSupply(admin.getInitialSupply() - coinBuyed);
			buyer.setStatus(UserOrderStatus.APPROVE);
		} else {
			coinBuyed = admin.getInitialSupply();
			admin.setInitialSupply(0.0);
			buyer.setStatus(UserOrderStatus.PENDING);
		}

		// check if buyer has that kind of money or not for purchasing
		double fees = currencyRepository.findBycoinName(coinName).getFee();
		double totprice = (buyer.getPrice() * coinBuyed);
		double pr = (buyer.getPrice());
		double fee = (int) ((fees * totprice) / 100);
		double grossAmount = totprice + fee;

		Wallet buyerFiatwallet = walletRepository.findBycoinTypeAndUser(WalletType.FIAT, buyer.getUser());

		Transection trans = new Transection();
		if (buyerFiatwallet.getShadowBalance() < grossAmount) {
			// transaction will be failed

			trans.setBuyer(buyer.getUser().getUserId());
			trans.setSeller(admin.getCoinId());
			trans.setFee(fees);
			trans.setCreatedOn(new Date());
			trans.setCoinName(coinName);
			trans.setWalletType(buyer.getCoinType());
			trans.setTransactionStatus(UserOrderStatus.FAILED);
			trans.setNetAmount(totprice);
			trans.setGrossAmount(grossAmount);
			// trans.setExchangeRate(totprice);
			trans.setExchangeRate(pr);
			trans.setMessage("insufficient funds");
			transactionRepository.save(trans);

			buyer.setStatus(UserOrderStatus.FAILED);
			orderRepository.save(buyer);
			return "insufficient balence";

		} else {

			if (buyer.getPrice() < admin.getPrice()) {
				// order will be pending

				trans.setBuyer(buyer.getUser().getUserId());
				trans.setSeller(admin.getCoinId());
				trans.setFee(fees);
				trans.setCreatedOn(new Date());
				trans.setCoinName(coinName);
				trans.setWalletType(buyer.getCoinType());
				trans.setTransactionStatus(UserOrderStatus.FAILED);
				trans.setNetAmount(totprice);
				trans.setGrossAmount(grossAmount);
				// 66666666666666
				trans.setExchangeRate(pr);
				trans.setMessage("no match");
				transactionRepository.save(trans);
				buyer.setGrossAmmount(grossAmount);
				buyer.setStatus(UserOrderStatus.PENDING);
				orderRepository.save(buyer);

				return "no match found";
			}

			// approve transaction

			// update admin currency
			double adminprice = (admin.getPrice() * coinBuyed);
			Integer coinInINR = (int) (totprice - adminprice);
			admin.setProfit(admin.getProfit() + fee);
			admin.setINRconversion(admin.getINRconversion() + coinInINR);
			currencyRepository.save(admin);
			trans.setBuyer(buyer.getUser().getUserId());
			trans.setSeller(admin.getCoinId());
			trans.setFee(fees);
			trans.setCreatedOn(new Date());
			trans.setCoinName(coinName);
			trans.setWalletType(buyer.getCoinType());
			trans.setTransactionStatus(UserOrderStatus.COMPLETED);
			trans.setNetAmount(totprice);
			// 66666666666666666666666
			trans.setExchangeRate(pr);
			trans.setGrossAmount(grossAmount);
			trans.setMessage("done");
			transactionRepository.save(trans);

			Wallet buyerwallet = walletRepository.findBycoinNameAndUser(coinName, buyer.getUser());
			buyerFiatwallet.setBalance(buyerFiatwallet.getBalance() - grossAmount);
			buyerFiatwallet.setShadowBalance(buyerFiatwallet.getShadowBalance() - grossAmount);
			if (buyerwallet == null) {
				buyerwallet = new Wallet();
				buyerwallet.setBalance(coinBuyed);
				buyerwallet.setCoinName(coinName);
				buyerwallet.setCoinType(buyer.getCoinType());
				// buyerwallet.setBalance(coinBuyed);
				buyerwallet.setShadowBalance(coinBuyed);
				buyerwallet.setUser(buyer.getUser());
			} else {
				buyerwallet.setBalance(buyerwallet.getBalance() + coinBuyed);
				// buyerwallet.setCoinName(coinName);
				// buyerwallet.setCoinType( buyers.get(i).getCoinType());
				buyerwallet.setBalance(buyerwallet.getBalance() + coinBuyed);
				buyerwallet.setShadowBalance(buyerwallet.getShadowBalance() + coinBuyed);
			}
			walletRepository.save(buyerwallet);

			buyer.setGrossAmmount(grossAmount);
			orderRepository.save(buyer);

		}
		return "transection successfull with admin";
	}

	@Override
	public int compare(UserOrder o1, UserOrder o2) {

		// if(o1.getPrice()!=o2.getPrice())
		return (int) (o1.getPrice() - o2.getPrice());

		// else return o1.getDate().compareTo(o2.getDate());

	}

	public List<Transection> getalltransaction() {
		List<Transection> l = new ArrayList<Transection>();
		l = transactionRepository.findAll();
		return l;

	}

}
