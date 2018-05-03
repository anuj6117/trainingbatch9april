package com.training.demo.service;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
	
	public String gettransaction() 
	{
		
		List<OrderTable> buyers = transactionRepository.getBuyer("BUYER");
		if(buyers == null)
		{
				return "buyers are not available";
		}
		System.out.println("11111111111111111111");
		List<OrderTable> sellers = transactionRepository.getSeller("SELLER");
		System.out.println("22222222222222222222222222222222");
				
		Iterator<OrderTable> se = sellers.iterator();
		while(se.hasNext()) {
			System.out.println(se.next().getCoinName()+"/t"+se.next().getOrderId());
		}
		Iterator<OrderTable> buyerIterator = buyers.iterator();
		Iterator<OrderTable> sellerIterator = sellers.iterator();

		User seller;
		User buyer;

		OrderTable buyerOrder = null ;
		OrderTable sellerOrder = null ;
		
		CoinManagement coinManagement;

		Wallet buyerFiatWallet = null;
		Wallet buyerCryptoWallet = null;
		Wallet sellerFiatWallet = null;
		Wallet sellerCryptoWallet = null;
		
		while(buyerIterator.hasNext()) 
		{
			buyerOrder = buyerIterator.next();
			coinManagement = coinRepository.findOneByCoinName(buyerOrder.getCoinName());
			System.out.println("ssssssssssssssssssssssssssssssssssssssssssss");
			Set<Wallet> buyerWallets = buyerOrder.getUser().getWallets();
			System.out.println("yyyyyyyyyyyyyyyyyyyyyyyyyyyyy");
			Iterator<Wallet> walletIterator = buyerWallets.iterator();
			
			while(walletIterator.hasNext())
			{
				Wallet wall = walletIterator.next();
				System.out.println("rrrrrrrrrrrrrrrrrrrr" + wall);
				if(wall.getWalletType().equals(WalletType.FIAT.toString())) {
					buyerFiatWallet = wall;
				}
				if(wall.getWalletType().equals(WalletType.CRYPTO.toString()) && wall.getCoinName().equals(buyerOrder.getCoinName()))
				{
					buyerCryptoWallet = wall;
				}
				
			}
			System.out.println("bbbbbbbbbbbbbbbbbbbbbbbb" +buyerFiatWallet);
			System.out.println("SELLLER "+sellers);
			if(!sellers.isEmpty())
			{
				System.out.println("sellerrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");
				Set<Wallet> sellerWallets = sellerOrder.getUser().getWallets();
				System.out.println("selllllllllllllllllllleeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeerrrrrrrrrrrrrrrrrrrrrrrrr");
				Iterator<Wallet> sellerWalletIterator = sellerWallets.iterator();
				while(sellerWalletIterator.hasNext())
				{
					Wallet wall = walletIterator.next();

					if(wall.getWalletType().equals(WalletType.FIAT.toString())) {
						sellerFiatWallet = wall;
					}
					if(wall.getWalletType().equals(WalletType.CRYPTO.toString()) && wall.getCoinName().equals(buyerOrder.getCoinName()))
					{
						sellerCryptoWallet = wall;
					}

					
				}
				
				
				
				while(sellerIterator.hasNext())
				{
					sellerOrder = sellerIterator.next();
					
					if((sellerOrder.getPrice() <= coinManagement.getPrice()) && sellerOrder.getPrice() <= buyerOrder.getPrice())
					{
						if(sellerOrder.getCoinQuantity() == buyerOrder.getCoinQuantity())
						{

							seller = sellerOrder.getUser();
							buyer = buyerOrder.getUser();
						
							Double sellerFiatMainBal = sellerFiatWallet.getBalance();
							Double sellerFiatShadowBal = sellerFiatWallet.getShadowBalance();
							Double sellerCryptoMainBal = sellerCryptoWallet.getBalance();
							
							Double buyerFiatMainBal = buyerFiatWallet.getBalance();
							Double buyerCryptoMainBal = buyerCryptoWallet.getBalance();
							Double buyerCryptoShadowBal = buyerCryptoWallet.getShadowBalance();
							
							sellerFiatWallet.setBalance(sellerFiatMainBal+sellerOrder.getGrossAmount());
							sellerFiatWallet.setShadowBalance(sellerFiatShadowBal+sellerOrder.getGrossAmount());
							sellerCryptoWallet.setBalance(sellerCryptoMainBal-sellerOrder.getCoinQuantity());
							
							buyerFiatWallet.setBalance(buyerFiatMainBal - buyerOrder.getGrossAmount());
							buyerCryptoWallet.setBalance(buyerCryptoMainBal+buyerOrder.getCoinQuantity());
							buyerCryptoWallet.setShadowBalance(buyerCryptoShadowBal+buyerOrder.getCoinQuantity());
							
							Double fees = (buyerOrder.getGrossAmount() * (coinManagement.getFees()/100));
							
							coinManagement.setCoinInINR(buyerOrder.getPrice()*buyerOrder.getCoinQuantity() - sellerOrder.getPrice()*sellerOrder.getCoinQuantity());
							coinManagement.setExchangeRate(buyerOrder.getPrice());
							coinManagement.setProfit(fees);
							
							buyerOrder.setCoinQuantity(buyerOrder.getCoinQuantity()-sellerOrder.getCoinQuantity());
							buyerOrder.setOrderStatus(OrderStatus.COMPLETED);
							sellerOrder.setOrderStatus(OrderStatus.COMPLETED);
							
							this.sellerBuyerUpdate(sellerFiatWallet, sellerCryptoWallet, buyerFiatWallet, buyerCryptoWallet, coinManagement, buyerOrder, sellerOrder);
							this.createSellerTransaction(buyerOrder, sellerOrder, buyerCryptoWallet.getWalletType());
					
							return "Both buyer and seller Transaction is completed.";
						}
						if(sellerOrder.getCoinQuantity() < buyerOrder.getCoinQuantity()) 
						{
							seller = sellerOrder.getUser();
							buyer = buyerOrder.getUser();
						
							Double sellerFiatMainBal = sellerFiatWallet.getBalance();
							Double sellerFiatShadowBal = sellerFiatWallet.getShadowBalance();
							Double sellerCryptoMainBal = sellerCryptoWallet.getBalance();
							
							Double buyerFiatMainBal = buyerFiatWallet.getBalance();
							Double buyerCryptoMainBal = buyerCryptoWallet.getBalance();
							Double buyerCryptoShadowBal = buyerCryptoWallet.getShadowBalance();
							
							sellerFiatWallet.setBalance(sellerFiatMainBal+sellerOrder.getGrossAmount());
							sellerFiatWallet.setShadowBalance(sellerFiatShadowBal+sellerOrder.getGrossAmount());
							sellerCryptoWallet.setBalance(sellerCryptoMainBal-sellerOrder.getCoinQuantity());
							
							Double fees = (sellerOrder.getGrossAmount() * (coinManagement.getFees()/100));
							Double AmountAndFee = sellerOrder.getGrossAmount()+fees;
							
							buyerFiatWallet.setBalance(buyerFiatMainBal - AmountAndFee);
							buyerCryptoWallet.setBalance(buyerCryptoMainBal+sellerOrder.getCoinQuantity());
							buyerCryptoWallet.setShadowBalance(buyerCryptoShadowBal+sellerOrder.getCoinQuantity());
							
							coinManagement.setCoinInINR(buyerOrder.getPrice()*sellerOrder.getCoinQuantity() - sellerOrder.getPrice()*sellerOrder.getCoinQuantity());
							coinManagement.setExchangeRate(buyerOrder.getPrice());
							coinManagement.setProfit(fees);
							
							buyerOrder.setCoinQuantity(buyerOrder.getCoinQuantity()-sellerOrder.getCoinQuantity());
							buyerOrder.setFees(buyerOrder.getFees()-fees);
							buyerOrder.setGrossAmount(buyerOrder.getGrossAmount()-AmountAndFee);
							buyerOrder.setNetAmount(buyerOrder.getNetAmount()-sellerOrder.getGrossAmount());
							buyerOrder.setOrderStatus(OrderStatus.PENDING);
							
							sellerOrder.setOrderStatus(OrderStatus.COMPLETED);
							
							this.sellerBuyerUpdate(sellerFiatWallet, sellerCryptoWallet, buyerFiatWallet, buyerCryptoWallet, coinManagement, buyerOrder, sellerOrder);
							this.createSellerTransaction(buyerOrder, sellerOrder, buyerCryptoWallet.getWalletType());
							
							return "seller Transaction is pending and buyer completed.";
						}
			
						if(sellerOrder.getCoinQuantity() > buyerOrder.getCoinQuantity())
						{
							seller = sellerOrder.getUser();
							buyer = buyerOrder.getUser();
						
							Double sellerFiatMainBal = sellerFiatWallet.getBalance();
							Double sellerFiatShadowBal = sellerFiatWallet.getShadowBalance();
							Double sellerCryptoMainBal = sellerCryptoWallet.getBalance();
							
							Double buyerFiatMainBal = buyerFiatWallet.getBalance();
							Double buyerCryptoMainBal = buyerCryptoWallet.getBalance();
							Double buyerCryptoShadowBal = buyerCryptoWallet.getShadowBalance();
							
							sellerFiatWallet.setBalance(sellerFiatMainBal+buyerOrder.getNetAmount());
							sellerFiatWallet.setShadowBalance(sellerFiatShadowBal+buyerOrder.getNetAmount());
							sellerCryptoWallet.setBalance(sellerCryptoMainBal-buyerOrder.getCoinQuantity());
							
							Double fees = (buyerOrder.getNetAmount() * (coinManagement.getFees()/100));
							Double AmountAndFee = sellerOrder.getGrossAmount()+fees;
							
							buyerFiatWallet.setBalance(buyerFiatMainBal - AmountAndFee);
							buyerCryptoWallet.setBalance(buyerCryptoMainBal+buyerOrder.getCoinQuantity());
							buyerCryptoWallet.setShadowBalance(buyerCryptoShadowBal+buyerOrder.getCoinQuantity());
							
							coinManagement.setCoinInINR(buyerOrder.getPrice()*buyerOrder.getCoinQuantity() - sellerOrder.getPrice()*buyerOrder.getCoinQuantity());
							coinManagement.setExchangeRate(buyerOrder.getPrice());
							coinManagement.setProfit(fees);
							
							sellerOrder.setCoinQuantity(sellerOrder.getCoinQuantity()-buyerOrder.getCoinQuantity());
							sellerOrder.setNetAmount(sellerOrder.getNetAmount()-buyerOrder.getNetAmount());
							sellerOrder.setGrossAmount(sellerOrder.getGrossAmount()-buyerOrder.getNetAmount());
							buyerOrder.setOrderStatus(OrderStatus.COMPLETED);
							sellerOrder.setOrderStatus(OrderStatus.PENDING);
							
							this.sellerBuyerUpdate(sellerFiatWallet, sellerCryptoWallet, buyerFiatWallet, buyerCryptoWallet, coinManagement, buyerOrder, sellerOrder);
							this.createSellerTransaction(buyerOrder, sellerOrder, buyerCryptoWallet.getWalletType());	
						
							return "buyer Transaction is completed but seller is pending.";
						}
					}
				}
			}

				if(coinManagement.getInitialSupply() == buyerOrder.getCoinQuantity() && buyerOrder.getPrice() >= coinManagement.getPrice())
				{									
					Double initSupply = coinManagement.getInitialSupply();
						initSupply = coinManagement.getInitialSupply()-buyerOrder.getCoinQuantity();
					
					Double buyerFiatMainBal = buyerFiatWallet.getBalance();
					Double buyerCryptoMainBal = buyerCryptoWallet.getBalance();
					Double buyerCryptoShadowBal = buyerCryptoWallet.getShadowBalance();
					
					Double AmountAndFee = buyerOrder.getGrossAmount();
					
					buyerFiatWallet.setBalance(buyerFiatMainBal - AmountAndFee);
					buyerCryptoWallet.setBalance(buyerCryptoMainBal+buyerOrder.getCoinQuantity());
					buyerCryptoWallet.setShadowBalance(buyerCryptoShadowBal+buyerOrder.getCoinQuantity());
					
					coinManagement.setInitialSupply(initSupply);
					coinManagement.setCoinInINR(buyerOrder.getPrice()*buyerOrder.getCoinQuantity() - coinManagement.getPrice()*buyerOrder.getCoinQuantity());
					coinManagement.setExchangeRate(buyerOrder.getPrice());
					coinManagement.setProfit(buyerOrder.getFees());
					
					buyerOrder.setOrderStatus(OrderStatus.COMPLETED);
					
					this.adminBuyerUpdate(buyerFiatWallet, buyerCryptoWallet, coinManagement, buyerOrder);
					this.createAdminTransaction(buyerOrder, buyerCryptoWallet.getWalletType());
					
					return "Buyer transaction is completed from coinManagement";

				}
				if(coinManagement.getInitialSupply() < buyerOrder.getCoinQuantity() && buyerOrder.getPrice() >= coinManagement.getPrice())
				{
					Double initSupply = coinManagement.getInitialSupply();
						
				
					Double buyerFiatMainBal = buyerFiatWallet.getBalance();
					Double buyerCryptoMainBal = buyerCryptoWallet.getBalance();
					Double buyerCryptoShadowBal = buyerCryptoWallet.getShadowBalance();
					
					Double fees = ((initSupply*buyerOrder.getPrice())*(coinManagement.getFees()/100));
					Double AmountAndFee = initSupply * buyerOrder.getPrice()+fees;
										
					buyerFiatWallet.setBalance(buyerFiatMainBal - AmountAndFee);
					buyerCryptoWallet.setBalance(buyerCryptoMainBal+initSupply);
					buyerCryptoWallet.setShadowBalance(buyerCryptoShadowBal+initSupply);
					
					buyerOrder.setCoinQuantity(buyerOrder.getCoinQuantity()-initSupply);
					buyerOrder.setNetAmount(buyerOrder.getNetAmount()-(initSupply * buyerOrder.getPrice()));
					buyerOrder.setGrossAmount(buyerOrder.getGrossAmount()-AmountAndFee);
					buyerOrder.setOrderStatus(OrderStatus.PENDING);
					
					coinManagement.setCoinInINR(buyerOrder.getPrice()*initSupply - coinManagement.getPrice()*initSupply);
							initSupply = 0d;
					coinManagement.setInitialSupply(initSupply);
					coinManagement.setExchangeRate(buyerOrder.getPrice());
					coinManagement.setProfit(buyerOrder.getFees());
				
					this.adminBuyerUpdate(buyerFiatWallet, buyerCryptoWallet, coinManagement, buyerOrder);
					this.createAdminTransaction(buyerOrder, buyerCryptoWallet.getWalletType());	

					return "Buyer transaction is pending due to insufficient coins in transaction";
				}

				if(coinManagement.getInitialSupply() > buyerOrder.getCoinQuantity() && buyerOrder.getPrice() >= coinManagement.getPrice())
				{
					Double initSupply = coinManagement.getInitialSupply();
					System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhhh"+buyerFiatWallet);
					System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhhiiiiiiiiiiiiiiiiiiih"+buyerFiatWallet.getBalance());

					System.out.println("alalslslslsllslslssssssssssssssssssssssssslslslslsllllllllllllllllllll");	
					Double buyerFiatMainBal = buyerFiatWallet.getBalance();
					
					System.out.println(buyerFiatWallet.getUser().getUserId()+":::::::::::::::::::"+buyerFiatMainBal);
					Double buyerCryptoMainBal = buyerCryptoWallet.getBalance();
					
					System.out.println(":::::::::::::::buyer crypto wallet balance "+buyerCryptoMainBal);
					Double buyerCryptoShadowBal = buyerCryptoWallet.getShadowBalance();

					//Double fees = ((initSupply*buyerOrder.getPrice())*(coinManagement.getFees()/100));
					//Double AmountAndFee = initSupply * buyerOrder.getPrice()+fees;
										
					buyerFiatWallet.setBalance(buyerFiatMainBal - buyerOrder.getGrossAmount());
					buyerCryptoWallet.setBalance(buyerCryptoMainBal+buyerOrder.getCoinQuantity());
					buyerCryptoWallet.setShadowBalance(buyerCryptoShadowBal+buyerOrder.getCoinQuantity());
					
					buyerOrder.setOrderStatus(OrderStatus.COMPLETED);
					
					coinManagement.setCoinInINR(buyerOrder.getPrice()*buyerOrder.getCoinQuantity() - coinManagement.getPrice()*buyerOrder.getCoinQuantity());
					initSupply = initSupply - buyerOrder.getCoinQuantity();
					coinManagement.setInitialSupply(initSupply);
					coinManagement.setExchangeRate(buyerOrder.getPrice());
					coinManagement.setProfit(buyerOrder.getFees());
				
					this.adminBuyerUpdate(buyerFiatWallet, buyerCryptoWallet, coinManagement, buyerOrder);
					this.createAdminTransaction(buyerOrder, buyerCryptoWallet.getWalletType());	
					
					return "Buyer transaction is completed from coinManagement because it has enough coins.";
				}
				
			}
		return "Didn't find any matching transactions.";
	}
	
	public void adminBuyerUpdate(Wallet buyerFiatWallet, Wallet buyerCryptoWallet, CoinManagement coinManagement,OrderTable buyerOrder)
	{
		orderRepository.save(buyerOrder);
		walletRepository.save(buyerFiatWallet);
		walletRepository.save(buyerCryptoWallet);
		coinRepository.save(coinManagement);

	}
	
	public void sellerBuyerUpdate(Wallet sellerFiatWallet,Wallet sellerCryptoWallet, Wallet buyerFiatWallet, Wallet buyerCryptoWallet, CoinManagement coinManagement,OrderTable buyerOrder, OrderTable sellerOrder)
	{
		orderRepository.save(buyerOrder);
		orderRepository.save(sellerOrder);
		walletRepository.save(sellerFiatWallet);
		walletRepository.save(sellerCryptoWallet);
		walletRepository.save(buyerFiatWallet);
		walletRepository.save(buyerCryptoWallet);
		coinRepository.save(coinManagement);

	}

	public void createAdminTransaction(OrderTable buyerOrder,String buyerWalletType) 
	{
			Transaction transaction = new Transaction();
			transaction.setBuyerId(buyerOrder.getUser().getUserId());
			transaction.setCoinName(buyerOrder.getCoinName());
			transaction.setCoinQuantity(buyerOrder.getCoinQuantity());
			transaction.setCoinType(WalletType.valueOf(buyerWalletType));
			transaction.setDescription("Admin has sold his coins.");
			transaction.setExchangeRate(buyerOrder.getPrice());
			transaction.setFees(buyerOrder.getFees());
			transaction.setGrossAmount(buyerOrder.getGrossAmount());
			transaction.setNetAmount(buyerOrder.getNetAmount());
			transaction.setTransactionCreatedOn(new Date());
			transaction.setTransactionStatus(buyerOrder.getOrderStatus());
			transactionRepository.save(transaction);	
	}

	
		public void createSellerTransaction(OrderTable buyerOrder, OrderTable sellerOrder,String buyerWalletType) 
		{
			Transaction transaction = new Transaction();
			transaction.setBuyerId(buyerOrder.getUser().getUserId());
			transaction.setCoinName(sellerOrder.getCoinName());
			transaction.setCoinQuantity(sellerOrder.getCoinQuantity());
			transaction.setCoinType(WalletType.valueOf(buyerWalletType));
			transaction.setDescription("Seller has sold his coins.");
			transaction.setExchangeRate(buyerOrder.getPrice());
			transaction.setFees(buyerOrder.getFees());
			transaction.setGrossAmount(buyerOrder.getGrossAmount());
			transaction.setNetAmount(buyerOrder.getNetAmount());
			transaction.setSellerId(sellerOrder.getUser().getUserId());
			transaction.setTransactionCreatedOn(new Date());
			transaction.setTransactionStatus(buyerOrder.getOrderStatus());
			transactionRepository.save(transaction);		
		}	
		

		
		public List<Transaction> getAllTransactions(){
			List<Transaction> transactions = transactionRepository.findAll();
			return transactions;			
		}
		
	
}			