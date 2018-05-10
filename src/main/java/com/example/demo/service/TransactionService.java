package com.example.demo.service;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.enums.OrderStatus;
import com.example.demo.enums.OrderType;
import com.example.demo.enums.WalletType;
import com.example.demo.model.CoinManagement;
import com.example.demo.model.Order;
import com.example.demo.model.Transaction;
import com.example.demo.model.User;
import com.example.demo.model.Wallet;
import com.example.demo.repository.CoinManagementRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.TransactionRepository;
import com.example.demo.repository.WalletRepository;

@Service
public class TransactionService
{	
	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private CoinManagementRepository coinManagementRepository;
	
	@Autowired
	private WalletRepository walletRepository;
	
	public String transactionApproval() 
	{
		User asSeller = null;
		User asBuyer = null;

		Order buyerOrder = null;
		Order sellerOrder = null;

		Wallet buyerFiatWallet = null;
		Wallet buyerCryptoWallet = null;
		
		Wallet sellerFiatWallet = null;
		Wallet sellerCryptoWallet = null;
		
		CoinManagement coinManagement = null;
		
		List<Order> buyers = orderRepository.getBuyers("BUYER");
		
		if(buyers.isEmpty())
		{
				return "No Buyer is available at this time.";
		}
		
		List<Order> sellers = orderRepository.getSellers("SELLER");
				
		Iterator<Order> buyerIterator = buyers.iterator();
		Iterator<Order> sellerIterator = sellers.iterator();

		while(buyerIterator.hasNext()) 
		{
			buyerOrder = buyerIterator.next();
			coinManagement = coinManagementRepository.findOneByCoinName(buyerOrder.getCoinName());
			Set<Wallet> buyerWallets = buyerOrder.getUser().getWallets();
			Iterator<Wallet> walletIterator = buyerWallets.iterator();
			
			while(walletIterator.hasNext())
			{
				Wallet wallet = walletIterator.next();

				if(wallet.getWalletType().equals(WalletType.FIAT))
				{
					buyerFiatWallet = wallet;
				}
				if(wallet.getWalletType().equals(WalletType.CRYPTO) && wallet.getCoinName().equals(buyerOrder.getCoinName()))
				{
					buyerCryptoWallet = wallet;
				}	
			}
			
			if(!sellers.isEmpty())
			{
				Set<Wallet> sellerWallets = sellerOrder.getUser().getWallets();
				
				Iterator<Wallet> sellerWalletIterator = sellerWallets.iterator();
				while(sellerWalletIterator.hasNext())
				{
					Wallet wallet = walletIterator.next();

					if(wallet.getWalletType().equals(WalletType.FIAT)) 
					{
						sellerFiatWallet = wallet;
					}
					if(wallet.getWalletType().equals(WalletType.CRYPTO) && wallet.getCoinName().equals(buyerOrder.getCoinName()))
					{
						sellerCryptoWallet = wallet;
					}	
				}
				
				while(sellerIterator.hasNext())
				{
					sellerOrder = sellerIterator.next();
					
					if((sellerOrder.getPrice() <= coinManagement.getPrice()) && sellerOrder.getPrice() <= buyerOrder.getPrice() && sellerOrder.getUser().getUserId() != buyerOrder.getUser().getUserId())
					{
						if(sellerOrder.getCoinQuantity() == buyerOrder.getCoinQuantity())
						{
							asSeller = sellerOrder.getUser();
							asBuyer = buyerOrder.getUser();
						
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
							
							/*Double fees = ((buyerOrder.getNetAmount() * coinManagement.getFee())/100);*/
							Double fees = ((buyerOrder.getNetAmount()+(buyerOrder.getGrossAmount()-buyerOrder.getNetAmount())));
							
							coinManagement.setCoinInInr(buyerOrder.getPrice()*buyerOrder.getCoinQuantity() - sellerOrder.getPrice()*sellerOrder.getCoinQuantity());
							coinManagement.setExchangeRate(buyerOrder.getPrice());
							coinManagement.setProfit(coinManagement.getProfit() + fees);
							
							buyerOrder.setOrderStatus(OrderStatus.COMPLETED);
							sellerOrder.setOrderStatus(OrderStatus.COMPLETED);
							
							this.sellerBuyerUpdate(sellerFiatWallet, sellerCryptoWallet, buyerFiatWallet, buyerCryptoWallet, coinManagement, buyerOrder, sellerOrder);
							this.createSellerTransaction(buyerOrder, sellerOrder, buyerCryptoWallet.getWalletType());
					
							return "Transaction has completed for buyer and seller";
						}
						if(sellerOrder.getCoinQuantity() < buyerOrder.getCoinQuantity()) 
						{
							asSeller = sellerOrder.getUser();
							asBuyer = buyerOrder.getUser();
						
							Double sellerFiatMainBal = sellerFiatWallet.getBalance();
							Double sellerFiatShadowBal = sellerFiatWallet.getShadowBalance();
							Double sellerCryptoMainBal = sellerCryptoWallet.getBalance();
							
							Double buyerFiatMainBal = buyerFiatWallet.getBalance();
							Double buyerCryptoMainBal = buyerCryptoWallet.getBalance();
							Double buyerCryptoShadowBal = buyerCryptoWallet.getShadowBalance();
							
							sellerFiatWallet.setBalance(sellerFiatMainBal+sellerOrder.getGrossAmount());
							sellerFiatWallet.setShadowBalance(sellerFiatShadowBal+sellerOrder.getGrossAmount());
							sellerCryptoWallet.setBalance(sellerCryptoMainBal-sellerOrder.getCoinQuantity());
							
							Double fees = (sellerOrder.getGrossAmount() * (coinManagement.getFee()/100));
							Double AmountAndFee = sellerOrder.getGrossAmount()+fees;
							
							buyerFiatWallet.setBalance(buyerFiatMainBal - AmountAndFee);
							buyerCryptoWallet.setBalance(buyerCryptoMainBal+sellerOrder.getCoinQuantity());
							buyerCryptoWallet.setShadowBalance(buyerCryptoShadowBal+sellerOrder.getCoinQuantity());
							
							coinManagement.setCoinInInr(buyerOrder.getPrice()*sellerOrder.getCoinQuantity() - sellerOrder.getPrice()*sellerOrder.getCoinQuantity());
							coinManagement.setExchangeRate(buyerOrder.getPrice());
							coinManagement.setProfit(fees);
							
							buyerOrder.setCoinQuantity(buyerOrder.getCoinQuantity()-sellerOrder.getCoinQuantity());
							buyerOrder.setFee(buyerOrder.getFee()-fees);
							buyerOrder.setGrossAmount(buyerOrder.getGrossAmount()-AmountAndFee);
							buyerOrder.setNetAmount(buyerOrder.getNetAmount()-sellerOrder.getGrossAmount());
							buyerOrder.setOrderStatus(OrderStatus.PENDING);
							
							sellerOrder.setOrderStatus(OrderStatus.COMPLETED);
							
							this.sellerBuyerUpdate(sellerFiatWallet, sellerCryptoWallet, buyerFiatWallet, buyerCryptoWallet, coinManagement, buyerOrder, sellerOrder);
							this.createSellerTransaction(buyerOrder, sellerOrder, buyerCryptoWallet.getWalletType());
							
							return "Transaction is in pending for seller and has completed for buyer";
						}
			
						if(sellerOrder.getCoinQuantity() > buyerOrder.getCoinQuantity())
						{
							asSeller = sellerOrder.getUser();
							asBuyer = buyerOrder.getUser();
						
							Double sellerFiatMainBal = sellerFiatWallet.getBalance();
							Double sellerFiatShadowBal = sellerFiatWallet.getShadowBalance();
							Double sellerCryptoMainBal = sellerCryptoWallet.getBalance();
							
							Double buyerFiatMainBal = buyerFiatWallet.getBalance();
							Double buyerCryptoMainBal = buyerCryptoWallet.getBalance();
							Double buyerCryptoShadowBal = buyerCryptoWallet.getShadowBalance();
							
							sellerFiatWallet.setBalance(sellerFiatMainBal+buyerOrder.getNetAmount());
							sellerFiatWallet.setShadowBalance(sellerFiatShadowBal+buyerOrder.getNetAmount());
							sellerCryptoWallet.setBalance(sellerCryptoMainBal-buyerOrder.getCoinQuantity());
							
							Double fees =((buyerOrder.getNetAmount() * coinManagement.getFee())/100);
							Double AmountAndFee = sellerOrder.getGrossAmount()+fees;
							
							buyerFiatWallet.setBalance(buyerFiatMainBal - AmountAndFee);
							buyerCryptoWallet.setBalance(buyerCryptoMainBal+buyerOrder.getCoinQuantity());
							buyerCryptoWallet.setShadowBalance(buyerCryptoShadowBal+buyerOrder.getCoinQuantity());
							
							coinManagement.setCoinInInr(buyerOrder.getPrice()*buyerOrder.getCoinQuantity() - sellerOrder.getPrice()*buyerOrder.getCoinQuantity());
							coinManagement.setExchangeRate(buyerOrder.getPrice());
							coinManagement.setProfit(coinManagement.getProfit() + fees );
							
							sellerOrder.setCoinQuantity(sellerOrder.getCoinQuantity()-buyerOrder.getCoinQuantity());
							sellerOrder.setNetAmount(sellerOrder.getNetAmount()-buyerOrder.getNetAmount());
							sellerOrder.setGrossAmount(sellerOrder.getGrossAmount()-buyerOrder.getNetAmount());
							buyerOrder.setOrderStatus(OrderStatus.COMPLETED);
							sellerOrder.setOrderStatus(OrderStatus.PENDING);
							
							this.sellerBuyerUpdate(sellerFiatWallet, sellerCryptoWallet, buyerFiatWallet, buyerCryptoWallet, coinManagement, buyerOrder, sellerOrder);
							this.createSellerTransaction(buyerOrder, sellerOrder, buyerCryptoWallet.getWalletType());	
						
							return "Transaction is in pending for buyer and has completed for seller";
						}
					}
				}
			}

				if(coinManagement.getInitialSupply() == buyerOrder.getCoinQuantity() && buyerOrder.getPrice() >= coinManagement.getPrice())
				{									
					Double initialSupply = coinManagement.getInitialSupply();
					initialSupply = coinManagement.getInitialSupply()-buyerOrder.getCoinQuantity();
					
					Double buyerFiatMainBal = buyerFiatWallet.getBalance();
					Double buyerCryptoMainBal = buyerCryptoWallet.getBalance();
					Double buyerCryptoShadowBal = buyerCryptoWallet.getShadowBalance();
					
					Double AmountAndFee = buyerOrder.getGrossAmount();
					
					buyerFiatWallet.setBalance(buyerFiatMainBal - AmountAndFee);
					buyerCryptoWallet.setBalance(buyerCryptoMainBal + buyerOrder.getCoinQuantity());
					buyerCryptoWallet.setShadowBalance(buyerCryptoShadowBal+buyerOrder.getCoinQuantity());
					
					coinManagement.setInitialSupply(initialSupply);
					coinManagement.setCoinInInr(buyerOrder.getPrice()*buyerOrder.getCoinQuantity() - coinManagement.getPrice()*buyerOrder.getCoinQuantity());
					coinManagement.setExchangeRate(buyerOrder.getPrice());
					coinManagement.setProfit(coinManagement.getProfit() + ((buyerOrder.getNetAmount() * coinManagement.getFee()) / 100));
					
					buyerOrder.setOrderStatus(OrderStatus.COMPLETED);
					
					this.adminBuyerUpdate(buyerFiatWallet, buyerCryptoWallet, coinManagement, buyerOrder);
					this.createAdminTransaction(buyerOrder, buyerCryptoWallet.getWalletType());
					
					return "Buyer transaction has completed through Admin";

				}
				if(coinManagement.getInitialSupply() < buyerOrder.getCoinQuantity() && buyerOrder.getPrice() >= coinManagement.getPrice())
				{
					Double initSupply = coinManagement.getInitialSupply();	
				
					Double buyerFiatMainBal = buyerFiatWallet.getBalance();
					Double buyerCryptoMainBal = buyerCryptoWallet.getBalance();
					Double buyerCryptoShadowBal = buyerCryptoWallet.getShadowBalance();
					
					Double fees = ((initSupply*buyerOrder.getPrice())*(coinManagement.getFee()/100));
					Double AmountAndFee = initSupply * buyerOrder.getPrice()+fees;
										
					buyerFiatWallet.setBalance(buyerFiatMainBal - AmountAndFee);
					buyerCryptoWallet.setBalance(buyerCryptoMainBal+initSupply);
					buyerCryptoWallet.setShadowBalance(buyerCryptoShadowBal+initSupply);
					
					buyerOrder.setCoinQuantity(buyerOrder.getCoinQuantity()-initSupply);
					buyerOrder.setNetAmount(buyerOrder.getNetAmount()-(initSupply * buyerOrder.getPrice()));
					buyerOrder.setGrossAmount(buyerOrder.getGrossAmount()-AmountAndFee);
					buyerOrder.setOrderStatus(OrderStatus.PENDING);
					
					coinManagement.setCoinInInr(buyerOrder.getPrice() * initSupply - coinManagement.getPrice() * initSupply);
					initSupply = 0d;
					coinManagement.setInitialSupply(initSupply);
					coinManagement.setExchangeRate(buyerOrder.getPrice());
					coinManagement.setProfit(buyerOrder.getFee());
				
					this.adminBuyerUpdate(buyerFiatWallet, buyerCryptoWallet, coinManagement, buyerOrder);
					this.createAdminTransaction(buyerOrder, buyerCryptoWallet.getWalletType());	

					return "Buyer transaction is in pending.";
				}

				if(coinManagement.getInitialSupply() > buyerOrder.getCoinQuantity() && buyerOrder.getPrice() >= coinManagement.getPrice())
				{
					Double initSupply = coinManagement.getInitialSupply();
						
					Double buyerFiatMainBal = buyerFiatWallet.getBalance();
					
					Double buyerCryptoMainBal = buyerCryptoWallet.getBalance();
					
					Double buyerCryptoShadowBal = buyerCryptoWallet.getShadowBalance();
										
					buyerFiatWallet.setBalance(buyerFiatMainBal - buyerOrder.getGrossAmount());
					buyerCryptoWallet.setBalance(buyerCryptoMainBal+buyerOrder.getCoinQuantity());
					buyerCryptoWallet.setShadowBalance(buyerCryptoShadowBal+buyerOrder.getCoinQuantity());
					
					buyerOrder.setOrderStatus(OrderStatus.COMPLETED);
					
					coinManagement.setCoinInInr(((buyerOrder.getPrice()*buyerOrder.getCoinQuantity())- (coinManagement.getPrice()*buyerOrder.getCoinQuantity()))+coinManagement.getCoinInInr());
					initSupply = initSupply - buyerOrder.getCoinQuantity();
					coinManagement.setInitialSupply(initSupply);
					coinManagement.setExchangeRate(buyerOrder.getPrice());
					coinManagement.setProfit(coinManagement.getProfit() + ((buyerOrder.getNetAmount() * coinManagement.getFee()) / 100));
				
					this.adminBuyerUpdate(buyerFiatWallet, buyerCryptoWallet, coinManagement, buyerOrder);
					this.createAdminTransaction(buyerOrder, buyerCryptoWallet.getWalletType());	
					
					return "Buyer transaction is completed from Admin";
				}
			
			}
		return "No matching transaction is there.";
	}
	
	public void adminBuyerUpdate(Wallet buyerFiatWallet, Wallet buyerCryptoWallet, CoinManagement coinManagement,Order buyerOrder)
	{
		orderRepository.save(buyerOrder);
		walletRepository.save(buyerFiatWallet);
		walletRepository.save(buyerCryptoWallet);
		coinManagementRepository.save(coinManagement);
	}
	
	public void sellerBuyerUpdate(Wallet sellerFiatWallet,Wallet sellerCryptoWallet, Wallet buyerFiatWallet, Wallet buyerCryptoWallet, CoinManagement coinManagement,Order buyerOrder, Order sellerOrder)
	{
		orderRepository.save(buyerOrder);
		orderRepository.save(sellerOrder);
		walletRepository.save(sellerFiatWallet);
		walletRepository.save(sellerCryptoWallet);
		walletRepository.save(buyerFiatWallet);
		walletRepository.save(buyerCryptoWallet);
		coinManagementRepository.save(coinManagement);
	}

	public void createAdminTransaction(Order buyerOrder, WalletType buyerWalletType) 
	{
			Transaction transaction = new Transaction();
			transaction.setBuyerId(buyerOrder.getUser().getUserId());
			transaction.setCoinName(buyerOrder.getCoinName());
			transaction.setCoinQuantity(buyerOrder.getCoinQuantity());
			transaction.setCoinType(buyerWalletType);
			transaction.setDescription("Admin has sold his coins.");
			//transaction.setOrderType(OrderType.);
			transaction.setExchangeRate(buyerOrder.getPrice());
			transaction.setGrossAmount(buyerOrder.getGrossAmount());
			transaction.setNetAmount(buyerOrder.getNetAmount());
			transaction.setTransactionCreatedOn(new Date());
			transaction.setOrderStatus(buyerOrder.getOrderStatus());
			transactionRepository.save(transaction);	
	}

	public void createSellerTransaction(Order buyerOrder, Order sellerOrder, WalletType buyerWalletType) 
	{
		Transaction transaction = new Transaction();
		transaction.setBuyerId(buyerOrder.getUser().getUserId());
		transaction.setCoinName(sellerOrder.getCoinName());
		transaction.setCoinQuantity(sellerOrder.getCoinQuantity());
		transaction.setCoinType(buyerWalletType);
		transaction.setDescription("Seller has sold his coins.");
		transaction.setExchangeRate(buyerOrder.getPrice());
		transaction.setGrossAmount(buyerOrder.getGrossAmount());
		transaction.setNetAmount(buyerOrder.getNetAmount());
		transaction.setSellerId(sellerOrder.getUser().getUserId());
		transaction.setTransactionCreatedOn(new Date());
		transaction.setOrderStatus(buyerOrder.getOrderStatus());
		transactionRepository.save(transaction);		
	}

	public List<Transaction> getAllTransaction() 
	{
		return transactionRepository.findAll();
	}
}