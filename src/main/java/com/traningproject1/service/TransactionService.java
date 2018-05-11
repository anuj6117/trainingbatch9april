package com.traningproject1.service;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.traningproject1.domain.CurrencyClass;
import com.traningproject1.domain.Transaction;
import com.traningproject1.domain.User;
import com.traningproject1.domain.UserOrder;
import com.traningproject1.domain.Wallet;
import com.traningproject1.enumsclass.CoinType;
import com.traningproject1.enumsclass.TransactionStatus;
import com.traningproject1.enumsclass.UserOrderStatus;
import com.traningproject1.repository.CurrencyRepository;
import com.traningproject1.repository.TransactionRepository;
import com.traningproject1.repository.UserOrderRepository;
import com.traningproject1.repository.UserRepository;
import com.traningproject1.repository.WalletRepository;

@Service
public class TransactionService {
	@Autowired
	private UserOrderRepository userorderRepository;
	@Autowired
	private WalletRepository walletRepository;
	@Autowired
	private CurrencyRepository currencyRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	private TransactionRepository transactionRepository;
	
	public String gettransaction() 
	{
		CopyOnWriteArrayList<UserOrder> buyers = userorderRepository.getBuyers("BUYER");
		CopyOnWriteArrayList<UserOrder> sellers = userorderRepository.getSellers("SELLER");	
		Iterator<UserOrder> buyerIterator = buyers.iterator();
		
		System.out.println(sellers);
		
		if(!sellers.isEmpty()) {
			System.out.println("sellers are available");
		}
		User seller;
		User buyer;
		UserOrder buyerOrder = null ;
		UserOrder sellerOrder = null ;
		CurrencyClass currencyclass;
		Wallet buyerFiatWallet = null;
		Wallet buyerCryptoWallet = null;
		Wallet sellerFiatWallet = null;
		Wallet sellerCryptoWallet = null;
		
		while(buyerIterator.hasNext()) 
		{
			buyerOrder = buyerIterator.next();
			currencyclass = currencyRepository.findOneByCoinName(buyerOrder.getCoinName());
			Set<Wallet> buyerWallets = buyerOrder.getUser().getWallet();
			Iterator<Wallet> buyerWalletIterator = buyerWallets.iterator();
			UserOrderStatus buyerstatus=buyerOrder.getStatus();
			while(buyerWalletIterator.hasNext())
			{
				Wallet wall = buyerWalletIterator.next();
				if(wall.getCoinType().equals(CoinType.FIAT)) {
					buyerFiatWallet = wall;
				}
				if(wall.getCoinType().equals(CoinType.CRYPTO) && wall.getCoinName().equals(buyerOrder.getCoinName()))
				{
					buyerCryptoWallet = wall;
				}
			}
			////////SELLER EXIST AT THIS POINT///////////
			Iterator<UserOrder> sellerIterator = sellers.iterator();
			if(!sellers.isEmpty())
			{	
		    	while(sellerIterator.hasNext())
				{ 
					sellerOrder = sellerIterator.next();
					currencyclass = currencyRepository.findOneByCoinName(buyerOrder.getCoinName());
					Set<Wallet> sellerWallets = sellerOrder.getUser().getWallet();
					Iterator<Wallet> sellerWalletIterator = sellerWallets.iterator();
					while(sellerWalletIterator.hasNext())
					{
						Wallet wall = sellerWalletIterator.next();
						if(wall.getCoinType().equals(CoinType.FIAT))
						{
							sellerFiatWallet = wall;
						}
						if(wall.getCoinType().equals(CoinType.CRYPTO) && wall.getCoinName().equals(buyerOrder.getCoinName()))
						{
							sellerCryptoWallet = wall;
						}
					}
					
			/////Checking ALL CONDITIONS FOR TRANSACTION BETWEEN SELLER AND BUYER////////
					
					if((sellerOrder.getPrice() <= currencyclass.getPrice() || currencyclass.getInitialSupply()==0)
							&& (sellerOrder.getPrice() <= buyerOrder.getPrice()) 
							&& (sellerOrder.getCoinName().equals(buyerOrder.getCoinName()))
							&& (buyerOrder.getUser().getUserId() != sellerOrder.getUser().getUserId()) 
							&& (buyerstatus.equals(UserOrderStatus.PENDING))
							)
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
							
							
							Double fees = buyerOrder.getFees();							
							currencyclass.setCoinInINR(currencyclass.getCoinInINR()+(buyerOrder.getNetAmount() - sellerOrder.getNetAmount()));
							currencyclass.setProfit(currencyclass.getProfit()+fees);
							
							buyerstatus = UserOrderStatus.APPROVED;
							buyerOrder.setStatus(UserOrderStatus.APPROVED);
							sellerOrder.setStatus(UserOrderStatus.APPROVED);
							
							this.sellerBuyerUpdate(sellerFiatWallet, sellerCryptoWallet, buyerFiatWallet, buyerCryptoWallet, currencyclass, buyerOrder, sellerOrder);
							this.createTransaction(buyerOrder.getUser().getUserId(), buyerOrder.getCoinName(),
									CoinType.CRYPTO, "Buyer transaction is completed from seller side.", buyerOrder.getPrice(),
										buyerOrder.getFees(), buyerOrder.getNetAmount(), buyerOrder.getGrossAmount(), sellerOrder.getUser().getUserId(), TransactionStatus.APPROVED);	
							System.out.println("Both buyer and seller Transaction is completed.");
		
							break;
						}
						////Quantity Basis Transaction Between Seller and buyer////
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
							
							Double netAmount = sellerOrder.getNetAmount();
							Double fees = (netAmount * (currencyclass.getFees()/100));
							Double AmountAndFee = netAmount+fees;
							
							buyerFiatWallet.setBalance(buyerFiatMainBal - AmountAndFee);
							buyerCryptoWallet.setBalance(buyerCryptoMainBal+sellerOrder.getCoinQuantity());
							buyerCryptoWallet.setShadowBalance(buyerCryptoShadowBal+sellerOrder.getCoinQuantity());
							currencyclass.setCoinInINR(buyerOrder.getPrice()*sellerOrder.getCoinQuantity() - sellerOrder.getPrice()*sellerOrder.getCoinQuantity());
							//coinManagement.setExchangeRate(buyerOrder.getPrice());
							currencyclass.setProfit(fees);
							buyerOrder.setCoinQuantity(buyerOrder.getCoinQuantity()-sellerOrder.getCoinQuantity());
							buyerOrder.setFees(buyerOrder.getFees()-fees);
							buyerOrder.setGrossAmount(buyerOrder.getGrossAmount()-AmountAndFee);
							buyerOrder.setNetAmount(buyerOrder.getNetAmount()-sellerOrder.getGrossAmount());
							buyerstatus = UserOrderStatus.PENDING;
							buyerOrder.setStatus(UserOrderStatus.PENDING);
							
							sellerOrder.setStatus(UserOrderStatus.APPROVED);
							
							this.sellerBuyerUpdate(sellerFiatWallet, sellerCryptoWallet, buyerFiatWallet, buyerCryptoWallet, currencyclass, buyerOrder, sellerOrder);
			
							this.createTransaction(buyerOrder.getUser().getUserId(), buyerOrder.getCoinName(), 
									CoinType.CRYPTO, "Buyer transaction is completed from seller side.", buyerOrder.getPrice(),
										fees, netAmount, AmountAndFee, sellerOrder.getUser().getUserId(), TransactionStatus.APPROVED);	
				
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
							
							Double fees = (buyerOrder.getNetAmount() * (currencyclass.getFees()/100));
							Double AmountAndFee = sellerOrder.getGrossAmount()+fees;
							buyerFiatWallet.setBalance(buyerFiatMainBal - AmountAndFee);
							buyerCryptoWallet.setBalance(buyerCryptoMainBal+buyerOrder.getCoinQuantity());
							buyerCryptoWallet.setShadowBalance(buyerCryptoShadowBal+buyerOrder.getCoinQuantity());
							currencyclass.setCoinInINR(buyerOrder.getPrice()*buyerOrder.getCoinQuantity() - sellerOrder.getPrice()*buyerOrder.getCoinQuantity());
							currencyclass.setProfit(fees);
							
							sellerOrder.setCoinQuantity(sellerOrder.getCoinQuantity()-buyerOrder.getCoinQuantity());
							sellerOrder.setNetAmount(sellerOrder.getNetAmount()-buyerOrder.getNetAmount());
							sellerOrder.setGrossAmount(sellerOrder.getGrossAmount()-buyerOrder.getNetAmount());
							buyerstatus = UserOrderStatus.APPROVED;
							buyerOrder.setStatus(UserOrderStatus.APPROVED);
							sellerOrder.setStatus(UserOrderStatus.PENDING);
							
							this.sellerBuyerUpdate(sellerFiatWallet, sellerCryptoWallet, buyerFiatWallet, buyerCryptoWallet, currencyclass, buyerOrder, sellerOrder);
							this.createTransaction(buyerOrder.getUser().getUserId(), buyerOrder.getCoinName(), 
									CoinType.CRYPTO, "Buyer transaction is completed from seller side.", buyerOrder.getPrice(),
										buyerOrder.getFees(), buyerOrder.getNetAmount(), buyerOrder.getGrossAmount(), sellerOrder.getUser().getUserId(), TransactionStatus.APPROVED);
						}
					}
				}
			}
			if(buyerstatus.equals(UserOrderStatus.PENDING) && currencyclass.getInitialSupply() > 0) 
			{
				Integer adminId = 0;
				if(currencyclass.getInitialSupply() == buyerOrder.getCoinQuantity() && buyerOrder.getPrice() >= currencyclass.getPrice())
				{									
					Double initSupply = currencyclass.getInitialSupply();
						initSupply = currencyclass.getInitialSupply()-buyerOrder.getCoinQuantity();
					
					Double buyerFiatMainBal = buyerFiatWallet.getBalance();
					Double buyerCryptoMainBal = buyerCryptoWallet.getBalance();
					Double buyerCryptoShadowBal = buyerCryptoWallet.getShadowBalance();
					Double AmountAndFee = buyerOrder.getGrossAmount();
					
					//WALLET UPGRADATIONS
					buyerFiatWallet.setBalance(buyerFiatMainBal - AmountAndFee);
					buyerCryptoWallet.setBalance(buyerCryptoMainBal+buyerOrder.getCoinQuantity());
					buyerCryptoWallet.setShadowBalance(buyerCryptoShadowBal+buyerOrder.getCoinQuantity());
					
					//CURRENCYCLASS UPGRADATION
					currencyclass.setInitialSupply(initSupply);
					currencyclass.setCoinInINR(currencyclass.getCoinInINR()+(buyerOrder.getPrice()*buyerOrder.getCoinQuantity() - currencyclass.getPrice()*buyerOrder.getCoinQuantity()));
					currencyclass.setProfit(currencyclass.getFees()+buyerOrder.getFees());
					
					// ORDER UPGRADATIONS
					buyerstatus = UserOrderStatus.APPROVED;
					buyerOrder.setStatus(UserOrderStatus.APPROVED);
					
					this.adminBuyerUpdate(buyerFiatWallet, buyerCryptoWallet, currencyclass, buyerOrder);
					
					this.createTransaction(buyerOrder.getUser().getUserId(), buyerOrder.getCoinName(),
							CoinType.CRYPTO, "Buyer transaction is completed from coinManagement", buyerOrder.getPrice(),
								buyerOrder.getFees(), buyerOrder.getNetAmount(), buyerOrder.getGrossAmount(), adminId, TransactionStatus.APPROVED);
					
					System.out.println("Buyer transaction is completed from coinManagement");

				}
				
				//////////////////////////////////////////////////
		////// CORRECTED
				////////////////////////////////////////////////
				
				if(currencyclass.getInitialSupply() < buyerOrder.getCoinQuantity() && buyerOrder.getPrice() >= currencyclass.getPrice())
				{
					Double initSupply = currencyclass.getInitialSupply();
					Double buyerFiatMainBal = buyerFiatWallet.getBalance();
					Double buyerCryptoMainBal = buyerCryptoWallet.getBalance();
					Double buyerCryptoShadowBal = buyerCryptoWallet.getShadowBalance();
					Double netAmount = (initSupply*buyerOrder.getPrice());
					Double fees = (netAmount*(currencyclass.getFees()/100));
					Double grossAmount = (initSupply * buyerOrder.getPrice())+fees;
										
					//WALLET UPGRADATION
					buyerFiatWallet.setBalance(buyerFiatMainBal - grossAmount);
					buyerCryptoWallet.setBalance(buyerCryptoMainBal+initSupply);
					buyerCryptoWallet.setShadowBalance(buyerCryptoShadowBal+initSupply);
					
					//ORDER TABLE UPGRADATION
					buyerOrder.setCoinQuantity(buyerOrder.getCoinQuantity()-initSupply);			
					buyerOrder.setNetAmount(buyerOrder.getNetAmount()-netAmount);					
					buyerOrder.setFees(buyerOrder.getFees()-fees);										
					buyerOrder.setGrossAmount(buyerOrder.getGrossAmount()-grossAmount);
					buyerstatus = UserOrderStatus.PENDING;
					buyerOrder.setStatus(UserOrderStatus.PENDING);
										
					//COIN TABLE UPGRADATION
					currencyclass.setCoinInINR(currencyclass.getCoinInINR()+(buyerOrder.getPrice()*initSupply - currencyclass.getPrice()*initSupply));
					currencyclass.setInitialSupply(0d);
					currencyclass.setProfit(currencyclass.getProfit()+fees);
					
					this.adminBuyerUpdate(buyerFiatWallet, buyerCryptoWallet, currencyclass, buyerOrder);
					
					this.createTransaction(buyerOrder.getUser().getUserId(), buyerOrder.getCoinName(),
							CoinType.CRYPTO, "Buyer transaction is pending because of insufficient coin in currency.", buyerOrder.getPrice(),
								fees, netAmount, grossAmount, adminId, TransactionStatus.APPROVED);		

					System.out.println("Buyer transaction is pending due to insufficient coins in transaction");
					//return "Buyer transaction is pending due to insufficient coins in transaction";
				}

				//////////////////////////////////////////////////
		////// CORRECTED
				////////////////////////////////////////////////
							
				if(currencyclass.getInitialSupply() >= buyerOrder.getCoinQuantity() && buyerOrder.getPrice() >= currencyclass.getPrice())
				{
					Double initSupply = currencyclass.getInitialSupply();	
					Double buyerFiatMainBal = buyerFiatWallet.getBalance();
					Double buyerCryptoMainBal = buyerCryptoWallet.getBalance();
					
					//WALLET UPGRADATION
					buyerFiatWallet.setBalance(buyerFiatMainBal - buyerOrder.getGrossAmount());
					Double buyerCryptoShadowBal = buyerCryptoWallet.getShadowBalance();
					buyerCryptoWallet.setBalance(buyerCryptoMainBal+buyerOrder.getCoinQuantity());
					buyerCryptoWallet.setShadowBalance(buyerCryptoShadowBal+buyerOrder.getCoinQuantity());
					
					//ORDER UPGRADATION
					buyerstatus = UserOrderStatus.PENDING;
					buyerOrder.setStatus(UserOrderStatus.APPROVED);					
					
					//COIN MANAGEMENT UPGRADATION
					currencyclass.setCoinInINR(currencyclass.getCoinInINR()+(buyerOrder.getNetAmount() - currencyclass.getPrice()*buyerOrder.getCoinQuantity()));
					initSupply = initSupply - buyerOrder.getCoinQuantity();
					currencyclass.setInitialSupply(initSupply);
					currencyclass.setProfit(currencyclass.getProfit()+buyerOrder.getFees());

					this.adminBuyerUpdate(buyerFiatWallet, buyerCryptoWallet, currencyclass, buyerOrder);
					
					
					
					this.createTransaction(buyerOrder.getUser().getUserId(), buyerOrder.getCoinName(), CoinType.CRYPTO, "Buyer transaction is completed from coinManagement", buyerOrder.getPrice(),
								buyerOrder.getFees(), buyerOrder.getNetAmount(), buyerOrder.getGrossAmount(), adminId, TransactionStatus.APPROVED);	
					
					System.out.println("Buyer transaction is completed from coinManagement because it has enough coins.");
				}
				
			}
		}
		
		return "TRANSACTIONS COMPLETED.";
	}
	public void adminBuyerUpdate(Wallet buyerFiatWallet, Wallet buyerCryptoWallet, CurrencyClass currencyclass,UserOrder buyerOrder)
	{
		userorderRepository.save(buyerOrder);
		walletRepository.save(buyerFiatWallet);
		walletRepository.save(buyerCryptoWallet);
		currencyRepository.save(currencyclass);

	}
	
	public void sellerBuyerUpdate(Wallet sellerFiatWallet,Wallet sellerCryptoWallet, Wallet buyerFiatWallet, Wallet buyerCryptoWallet, CurrencyClass currencyclass,UserOrder buyerOrder, UserOrder sellerOrder)
	{
		userorderRepository.save(buyerOrder);
		userorderRepository.save(sellerOrder);
		walletRepository.save(sellerFiatWallet);
		walletRepository.save(sellerCryptoWallet);
		walletRepository.save(buyerFiatWallet);
		walletRepository.save(buyerCryptoWallet);
		currencyRepository.save(currencyclass);

	}
	
		public void createTransaction(Integer buyerId, String coinName, CoinType coinType, String description, 
				Double exchangeRate, Double fees, Double netAmount, Double grossAmount, Integer sellerId, TransactionStatus approved) 
		{
			Transaction transaction = new Transaction();
			transaction.setBuyerId(buyerId);
			transaction.setCoinName(coinName);
			transaction.setCoinType(coinType);
			transaction.setMessage(description);
			transaction.setExchangeRate(exchangeRate);
			transaction.setFees(fees);
			transaction.setNetAmount(netAmount);
			transaction.setGrossAmount(grossAmount);
			transaction.setSellerId(sellerId);
			transaction.setDateCreated(new Date());
			transaction.setStatus(approved);
			transactionRepository.save(transaction);		
		}
		/*public List<Transaction> getAllTransaction()
		{
			return transactionRepository.findAll();
		}*/
} 
