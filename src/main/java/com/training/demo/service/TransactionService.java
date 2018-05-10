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
	
	public String gettransaction() 
	{
		CopyOnWriteArrayList<OrderTable> buyers = transactionRepository.getBuyer("BUYER");
		CopyOnWriteArrayList<OrderTable> sellers = transactionRepository.getSeller("SELLER");	
		Iterator<OrderTable> buyerIterator = buyers.iterator();
		Iterator<OrderTable> sellerIterator = sellers.iterator();
		System.out.println(sellers);
		
		if(!sellers.isEmpty()) {
			System.out.println("sellers are available");
		}
		
	/*	while(sellerIterator.hasNext()) {
			OrderTable sellertemp = (OrderTable)sellerIterator.next();
			System.out.println(sellertemp.getUser().getUserId()+"/t"+sellertemp.getCoinName());
		}*/
		System.out.println("///////////////////////////////////////////////////////////");
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
			Set<Wallet> buyerWallets = buyerOrder.getUser().getWallets();
			Iterator<Wallet> buyerWalletIterator = buyerWallets.iterator();
			
			while(buyerWalletIterator.hasNext())
			{
				Wallet wall = buyerWalletIterator.next();
				if(wall.getCoinType().equals(WalletType.FIAT.toString())) {
					buyerFiatWallet = wall;
				}
				System.out.println(";;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;");
				if(wall.getCoinType().equals(WalletType.CRYPTO.toString()) && wall.getCoinName().equals(buyerOrder.getCoinName()))
				{
					System.out.println(";;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;111111111111111");
					buyerCryptoWallet = wall;
					System.out.println("222222222222222222222222222;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;");
				}
				System.out.println(";33333333333333333333333333333333333;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;");
			}
			
			if(!sellers.isEmpty())
			{
				System.out.println("SEllerrrrrrrrrrrrrrrrr    11111111111111111111111111111111111111111");
				
				while(sellerIterator.hasNext())
				{ 
					System.out.println("SELLERRRRRRRRRRRRRRRRRRRRRRRr     22222222222222222222222222222222222222222");
					sellerOrder = sellerIterator.next();
					System.out.println("Seller    33333333333333333333333333333333333333333333333");
					coinManagement = coinRepository.findOneByCoinName(buyerOrder.getCoinName());
					System.out.println("Seller 44444444444444444444444444444444444444444");
					Set<Wallet> sellerWallets = sellerOrder.getUser().getWallets();
					System.out.println("Seller            5555555555555555555555555555555555555");
					Iterator<Wallet> sellerWalletIterator = sellerWallets.iterator();
					System.out.println("Seller       66666666666666666666666666666666666666666666");
					
					while(sellerWalletIterator.hasNext())
					{
						System.out.println("Sellet 777777777777777777777777777777777");
						Wallet wall = sellerWalletIterator.next();
						System.out.println("Seller 888888888888888888888888888888888888888888");
						if(wall.getCoinType().equals(WalletType.FIAT.toString()))
						{
							System.out.println("Seller 999999999999999999999999999999999999999999999");
							sellerFiatWallet = wall;
							System.out.println("Seller 10101010101010101010101010101010101010");
						}
						System.out.println("Seller Seller 1111111111111111111111111111111111111111111");
						if(wall.getCoinType().equals(WalletType.CRYPTO.toString()) && wall.getCoinName().equals(buyerOrder.getCoinName()))
						{
							System.out.println("Seller Seller 22222222222222222222222222222222222222222222");
							sellerCryptoWallet = wall;
							System.out.println("Seller Seller 33333333333333333333333333333333333");
						}
						System.out.println("Seller Seller 44444444444444444444444444444");
					}
					System.out.println("Seller Seller 5555555555555555555555555555555555555555555555555");
				
/////////////////////////////////////////////
					
					
					
					System.out.println("Seller Seller 666666666666666666666666666666666666666666");
					
					System.out.println("Seller Seller 777777777777777777777777777777777777777777777777777777");
					
					if((sellerOrder.getPrice() <= coinManagement.getPrice() || coinManagement.getInitialSupply()==0)
							&& sellerOrder.getPrice() <= buyerOrder.getPrice() 
							&& sellerOrder.getCoinName().equals(buyerOrder.getCoinName())
							&& buyerOrder.getUser().getUserId() != sellerOrder.getUser().getUserId() 
							&& buyerOrder.getOrderStatus().equals(OrderStatus.PENDING)
							)
					{
						System.out.println("Seller Seller 88888888888888888888888888888888888888888888888888888");
						if(sellerOrder.getCoinQuantity() == buyerOrder.getCoinQuantity())
						{
							
							System.out.println("Seller Seller 99999999999999999999999999999999999999999999999999999999");
							seller = sellerOrder.getUser();
							buyer = buyerOrder.getUser();
							System.out.println("Seller Seller 1010101010100110101010101010101010");
							
							Double sellerFiatMainBal = sellerFiatWallet.getBalance();
							System.out.println("Seller Seller Seller 11111111111111111111111111111111111111111111");
							Double sellerFiatShadowBal = sellerFiatWallet.getShadowBalance();
							System.out.println("Seller Seller Seller 222222222222222222222222222222222222222222222");
							Double sellerCryptoMainBal = sellerCryptoWallet.getBalance();
							System.out.println("Seller Seller Seller 3333333333333333333333333333333333333333333333333");

							
							
							Double buyerFiatMainBal = buyerFiatWallet.getBalance();
							System.out.println("Seller Seller Seller 4444444444444444444444444444444444444444444444");
							Double buyerCryptoMainBal = buyerCryptoWallet.getBalance();
							System.out.println("Seller Seller Seller 55555555555555555555555555555555555555555555");
							Double buyerCryptoShadowBal = buyerCryptoWallet.getShadowBalance();
							System.out.println("Seller Seller Seller 666666666666666666666666666666666666666666666");
							
							sellerFiatWallet.setBalance(sellerFiatMainBal+sellerOrder.getGrossAmount());
							System.out.println("Seller Seller Seller 7777777777777777777777777777777777777777777777");
							sellerFiatWallet.setShadowBalance(sellerFiatShadowBal+sellerOrder.getGrossAmount());
							System.out.println("Seller Seller Seller 88888888888888888888888888888888888888888888888");
							sellerCryptoWallet.setBalance(sellerCryptoMainBal-sellerOrder.getCoinQuantity());
							System.out.println("Seller Seller Seller 9999999999999999999999999999999999999999999999");
							
							buyerFiatWallet.setBalance(buyerFiatMainBal - buyerOrder.getGrossAmount());
							System.out.println("11111111111111111111111111111111111111111 Seller Seller Seller ");
							buyerCryptoWallet.setBalance(buyerCryptoMainBal+buyerOrder.getCoinQuantity());
							System.out.println("2222222222222222222222222222222222222222222222222222 Seller Seller Seller ");
							buyerCryptoWallet.setShadowBalance(buyerCryptoShadowBal+buyerOrder.getCoinQuantity());
							System.out.println("3333333333333333333333333333333333333333333333 Seller Seller Seller ");
							
							Double fees = buyerOrder.getFees();
							System.out.println("444444444444444444444444444444444444444444444 Seller Seller Seller ");							
							coinManagement.setCoinInINR(coinManagement.getCoinInINR()+(buyerOrder.getNetAmount() - sellerOrder.getNetAmount()));
							System.out.println("555555555555555555555555555555555555555555555 Seller Seller Seller ");
							coinManagement.setProfit(coinManagement.getProfit()+fees);
							System.out.println("66666666666666666666666666666666666666666666 Seller Seller Seller ");
							
							System.out.println("777777777777777777777777777777777 Seller Seller Seller ");
							buyerOrder.setOrderStatus(OrderStatus.APPROVED);
							System.out.println("888888888888888888888888888888888888888 Seller Seller Seller ");
							sellerOrder.setOrderStatus(OrderStatus.APPROVED);
							System.out.println("999999999999999999999999999999999999999999999999999 Seller Seller Seller ");
							
							this.sellerBuyerUpdate(sellerFiatWallet, sellerCryptoWallet, buyerFiatWallet, buyerCryptoWallet, coinManagement, buyerOrder, sellerOrder);
							System.out.println("10101010101010100 Seller Seller Seller ");
							this.createTransaction(buyerOrder.getUser().getUserId(), buyerOrder.getCoinName(), buyerOrder.getCoinQuantity(),
									WalletType.CRYPTO, "Buyer transaction is completed from seller side.", buyerOrder.getPrice(),
										buyerOrder.getFees(), buyerOrder.getNetAmount(), buyerOrder.getGrossAmount(), sellerOrder.getUser().getUserId(), OrderStatus.COMPLETED);	
							System.out.println("Both buyer and seller Transaction is completed.");
							
							//return "Both buyer and seller Transaction is completed.";
		
							break;
						}
						if(sellerOrder.getCoinQuantity() < buyerOrder.getCoinQuantity()) 
						{
							System.out.println("Seller 1111111111111111111111111111111111111 Seller Seller ");
							seller = sellerOrder.getUser();
							System.out.println("Seller 2222222222222222222222222222222222222 Seller Seller ");
							buyer = buyerOrder.getUser();
							System.out.println("Seller 333333333333333333333333333333333333 Seller Seller ");
							Double sellerFiatMainBal = sellerFiatWallet.getBalance();
							System.out.println("Seller 44444444444444444444444444444444444 Seller Seller ");
							Double sellerFiatShadowBal = sellerFiatWallet.getShadowBalance();
							System.out.println("Seller 5555555555555555555555555555555555 Seller Seller ");
							Double sellerCryptoMainBal = sellerCryptoWallet.getBalance();
							System.out.println("Seller 666666666666666666666666666666666 Seller Seller ");
							
							Double buyerFiatMainBal = buyerFiatWallet.getBalance();
							System.out.println("Seller 777777777777777777777777777777777777777 Seller Seller ");
							Double buyerCryptoMainBal = buyerCryptoWallet.getBalance();
							System.out.println("Seller 8888888888888888888888888888888888888 Seller Seller ");
							Double buyerCryptoShadowBal = buyerCryptoWallet.getShadowBalance();
							System.out.println("Seller 9999999999999999999999999999999999999999999999 Seller Seller ");
							
							sellerFiatWallet.setBalance(sellerFiatMainBal+sellerOrder.getGrossAmount());
							System.out.println("Seller 10101010100010101010 Seller Seller ");
							sellerFiatWallet.setShadowBalance(sellerFiatShadowBal+sellerOrder.getGrossAmount());
							System.out.println("Seller Seller 1111111111111111111111111111111111111111 Seller ");
							sellerCryptoWallet.setBalance(sellerCryptoMainBal-sellerOrder.getCoinQuantity());
							
							System.out.println("Seller Seller 2222222222222222222222222222222222222222 Seller ");
							Double netAmount = sellerOrder.getNetAmount();
							System.out.println("Seller Seller 33333333333333333333333333333333333333333 Seller ");
							Double fees = (netAmount * (coinManagement.getFees()/100));
							System.out.println("Seller Seller 444444444444444444444444444444444444444444 Seller ");
							Double AmountAndFee = netAmount+fees;
							System.out.println("Seller Seller 55555555555555555555555555555555555555555 Seller ");
							
							buyerFiatWallet.setBalance(buyerFiatMainBal - AmountAndFee);
							System.out.println("Seller Seller 666666666666666666666666666666666666666 Seller ");
							buyerCryptoWallet.setBalance(buyerCryptoMainBal+sellerOrder.getCoinQuantity());
							System.out.println("Seller Seller 77777777777777777777777777777777777777 Seller ");
							buyerCryptoWallet.setShadowBalance(buyerCryptoShadowBal+sellerOrder.getCoinQuantity());
							System.out.println("Seller Seller 888888888888888888888888888888888888888 Seller ");
							coinManagement.setCoinInINR(buyerOrder.getPrice()*sellerOrder.getCoinQuantity() - sellerOrder.getPrice()*sellerOrder.getCoinQuantity());
							//coinManagement.setExchangeRate(buyerOrder.getPrice());
							System.out.println("Seller Seller 9999999999999999999999999999999999999999 Seller ");
							coinManagement.setProfit(fees);
							System.out.println("Seller Seller 101010101010101010101010 Seller ");
							buyerOrder.setCoinQuantity(buyerOrder.getCoinQuantity()-sellerOrder.getCoinQuantity());
							System.out.println("Seller 111111111111 Seller 1111111111111111 Seller ");
							buyerOrder.setFees(buyerOrder.getFees()-fees);
							System.out.println("Seller 222222222222 Seller 222222222222222222 Seller ");
							buyerOrder.setGrossAmount(buyerOrder.getGrossAmount()-AmountAndFee);
							System.out.println("Seller 33333333333333333333 Seller 3333333333333333333 Seller ");
							buyerOrder.setNetAmount(buyerOrder.getNetAmount()-sellerOrder.getGrossAmount());
							System.out.println("Seller 4444444444444444444 Seller 444444444444444444 Seller ");
							buyerOrder.setOrderStatus(OrderStatus.PENDING);
							System.out.println("Seller 555555555555555555555 Seller 555555555555555555 Seller ");
							
							sellerOrder.setOrderStatus(OrderStatus.APPROVED);
							System.out.println("Seller 666666666666666666 Seller 6666666666666666666 Seller ");
							
							this.sellerBuyerUpdate(sellerFiatWallet, sellerCryptoWallet, buyerFiatWallet, buyerCryptoWallet, coinManagement, buyerOrder, sellerOrder);
//							this.createSellerTransaction(buyerOrder, sellerOrder, buyerCryptoWallet.getCoinType());
							System.out.println("Seller 777777777777777777777 Seller 777777777777777777777 Seller ");
							this.createTransaction(buyerOrder.getUser().getUserId(), buyerOrder.getCoinName(), sellerOrder.getCoinQuantity(),
									WalletType.CRYPTO, "Buyer transaction is completed from seller side.", buyerOrder.getPrice(),
										fees, netAmount, AmountAndFee, sellerOrder.getUser().getUserId(), OrderStatus.APPROVED);	

							System.out.println("seller completed and buyer Transaction is pending.");
							//return "seller Transaction is completed and buyer pending.";
				
						}
			
						if(sellerOrder.getCoinQuantity() > buyerOrder.getCoinQuantity())
						{System.out.println("Seller 888888888 Seller 88888888888888 Seller ");
							seller = sellerOrder.getUser();
							System.out.println("Seller 99999999999999 Seller 99999999999999999 Seller ");
							buyer = buyerOrder.getUser();
							System.out.println("Seller 10101010 Seller 10101010 Seller ");
							Double sellerFiatMainBal = sellerFiatWallet.getBalance();
							System.out.println("Seller aaaaaaaaaa Seller Seller ");
							Double sellerFiatShadowBal = sellerFiatWallet.getShadowBalance();
							System.out.println("Seller bbbbbbbbbbbbbbbbb Seller Seller ");
							Double sellerCryptoMainBal = sellerCryptoWallet.getBalance();
							System.out.println("Seller cccccccccccccccccccc Seller Seller ");
							
							Double buyerFiatMainBal = buyerFiatWallet.getBalance();
							System.out.println("Seller dddddddddddddddddddddd Seller Seller ");
							
							/////////////////////////Final countdown
							
							Double buyerCryptoMainBal = buyerCryptoWallet.getBalance();
							System.out.println("Seller eeeeeeeeeeeeeeeeeee Seller Seller ");
							Double buyerCryptoShadowBal = buyerCryptoWallet.getShadowBalance();
							System.out.println("Seller ffffffffffffffff Seller Seller ");
							
							sellerFiatWallet.setBalance(sellerFiatMainBal+buyerOrder.getNetAmount());
							System.out.println("Seller gggggggggggggggggg Seller Seller ");
							sellerFiatWallet.setShadowBalance(sellerFiatShadowBal+buyerOrder.getNetAmount());
							System.out.println("Seller hhhhhhhhhhhhhhhhhhhhhhh Seller Seller ");
							sellerCryptoWallet.setBalance(sellerCryptoMainBal-buyerOrder.getCoinQuantity());
							System.out.println("Seller iiiiiiiiiiiiiiiiiiiii Seller Seller ");
							
							Double fees = (buyerOrder.getNetAmount() * (coinManagement.getFees()/100));
							System.out.println("Seller jjjjjjjjjjjjjjjjjjjjjj Seller Seller ");
							Double AmountAndFee = sellerOrder.getGrossAmount()+fees;
							System.out.println("Seller kkkkkkkkkkkkkkkkkkkkk Seller Seller ");
							buyerFiatWallet.setBalance(buyerFiatMainBal - AmountAndFee);
							System.out.println("Seller lllllllllllllllllllll Seller Seller ");
							buyerCryptoWallet.setBalance(buyerCryptoMainBal+buyerOrder.getCoinQuantity());
							System.out.println("Seller mmmmmmmmmmmmmmmmmmmmmm Seller Seller ");
							buyerCryptoWallet.setShadowBalance(buyerCryptoShadowBal+buyerOrder.getCoinQuantity());
							System.out.println("Seller nnnnnnnnnnnnnnnnnnnnnnnn Seller Seller ");
							coinManagement.setCoinInINR(buyerOrder.getPrice()*buyerOrder.getCoinQuantity() - sellerOrder.getPrice()*buyerOrder.getCoinQuantity());
							System.out.println("Seller ooooooooooooooooooooooooooooo Seller Seller ");
							coinManagement.setProfit(fees);
							System.out.println("Seller pppppppppppppppppppppppppppppppppppp Seller Seller ");
							
							sellerOrder.setCoinQuantity(sellerOrder.getCoinQuantity()-buyerOrder.getCoinQuantity());
							System.out.println("Seller qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq Seller Seller ");
							sellerOrder.setNetAmount(sellerOrder.getNetAmount()-buyerOrder.getNetAmount());
							System.out.println("Seller rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr Seller Seller ");
							sellerOrder.setGrossAmount(sellerOrder.getGrossAmount()-buyerOrder.getNetAmount());
							System.out.println("Seller ssssssssssssssssssssssssssssssssssssssss Seller Seller ");
							buyerOrder.setOrderStatus(OrderStatus.APPROVED);
							System.out.println("Seller ttttttttttttttttttttttttttttttttttttttttttttt Seller Seller ");
							sellerOrder.setOrderStatus(OrderStatus.PENDING);
							System.out.println("Seller uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu Seller Seller ");
							
							this.sellerBuyerUpdate(sellerFiatWallet, sellerCryptoWallet, buyerFiatWallet, buyerCryptoWallet, coinManagement, buyerOrder, sellerOrder);
							System.out.println("Seller vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv Seller Seller ");
							this.createTransaction(buyerOrder.getUser().getUserId(), buyerOrder.getCoinName(), buyerOrder.getCoinQuantity(),
									WalletType.CRYPTO, "Buyer transaction is completed from seller side.", buyerOrder.getPrice(),
										buyerOrder.getFees(), buyerOrder.getNetAmount(), buyerOrder.getGrossAmount(), sellerOrder.getUser().getUserId(), OrderStatus.COMPLETED);	
							System.out.println("Seller wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww Seller Seller ");
							System.out.println("buyer Transaction is completed but seller is pending.");
							//return "buyer Transaction is completed but seller is pending.";
						}
					}
				}
			}
			if(buyerOrder.getOrderStatus().equals(OrderStatus.PENDING) && coinManagement.getInitialSupply() > 0) 
			{
				Integer adminId = 0;
				if(coinManagement.getInitialSupply() == buyerOrder.getCoinQuantity() && buyerOrder.getPrice() >= coinManagement.getPrice())
				{									
					Double initSupply = coinManagement.getInitialSupply();
						initSupply = coinManagement.getInitialSupply()-buyerOrder.getCoinQuantity();
					
					Double buyerFiatMainBal = buyerFiatWallet.getBalance();
					Double buyerCryptoMainBal = buyerCryptoWallet.getBalance();
					Double buyerCryptoShadowBal = buyerCryptoWallet.getShadowBalance();
					Double AmountAndFee = buyerOrder.getGrossAmount();
					
					//WALLET UPGRADATIONS
					buyerFiatWallet.setBalance(buyerFiatMainBal - AmountAndFee);
					buyerCryptoWallet.setBalance(buyerCryptoMainBal+buyerOrder.getCoinQuantity());
					buyerCryptoWallet.setShadowBalance(buyerCryptoShadowBal+buyerOrder.getCoinQuantity());
					
					//COIN MANAGEMENT UPGRADATIONS
					coinManagement.setInitialSupply(initSupply);
					coinManagement.setCoinInINR(coinManagement.getCoinInINR()+(buyerOrder.getPrice()*buyerOrder.getCoinQuantity() - coinManagement.getPrice()*buyerOrder.getCoinQuantity()));
					coinManagement.setProfit(coinManagement.getFees()+buyerOrder.getFees());
					
					// ORDER UPGRADATIONS
					buyerOrder.setOrderStatus(OrderStatus.APPROVED);
					
					this.adminBuyerUpdate(buyerFiatWallet, buyerCryptoWallet, coinManagement, buyerOrder);
					
					this.createTransaction(buyerOrder.getUser().getUserId(), buyerOrder.getCoinName(), buyerOrder.getCoinQuantity(),
							WalletType.CRYPTO, "Buyer transaction is completed from coinManagement", buyerOrder.getPrice(),
								buyerOrder.getFees(), buyerOrder.getNetAmount(), buyerOrder.getGrossAmount(), adminId, OrderStatus.APPROVED);
					
					System.out.println("Buyer transaction is completed from coinManagement");
					//return "Buyer transaction is completed from coinManagement";

				}
				
				//////////////////////////////////////////////////
		////// CORRECTED
				////////////////////////////////////////////////
				
				if(coinManagement.getInitialSupply() < buyerOrder.getCoinQuantity() && buyerOrder.getPrice() >= coinManagement.getPrice())
				{
					Double initSupply = coinManagement.getInitialSupply();
					Double buyerFiatMainBal = buyerFiatWallet.getBalance();
					Double buyerCryptoMainBal = buyerCryptoWallet.getBalance();
					Double buyerCryptoShadowBal = buyerCryptoWallet.getShadowBalance();
					Double netAmount = (initSupply*buyerOrder.getPrice());
					Double fees = (netAmount*(coinManagement.getFees()/100));
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
					buyerOrder.setOrderStatus(OrderStatus.PENDING);
										
					//COIN TABLE UPGRADATION
					coinManagement.setCoinInINR(coinManagement.getCoinInINR()+(buyerOrder.getPrice()*initSupply - coinManagement.getPrice()*initSupply));
					coinManagement.setInitialSupply(0d);
					coinManagement.setProfit(coinManagement.getProfit()+fees);
					
					this.adminBuyerUpdate(buyerFiatWallet, buyerCryptoWallet, coinManagement, buyerOrder);
					
					this.createTransaction(buyerOrder.getUser().getUserId(), buyerOrder.getCoinName(), initSupply,
							WalletType.CRYPTO, "Buyer transaction is pending because of insufficient coin in currency.", buyerOrder.getPrice(),
								fees, netAmount, grossAmount, adminId, OrderStatus.APPROVED);		

					System.out.println("Buyer transaction is pending due to insufficient coins in transaction");
					//return "Buyer transaction is pending due to insufficient coins in transaction";
				}

				//////////////////////////////////////////////////
		////// CORRECTED
				////////////////////////////////////////////////
							
				if(coinManagement.getInitialSupply() >= buyerOrder.getCoinQuantity() && buyerOrder.getPrice() >= coinManagement.getPrice())
				{
					Double initSupply = coinManagement.getInitialSupply();	
					Double buyerFiatMainBal = buyerFiatWallet.getBalance();
					Double buyerCryptoMainBal = buyerCryptoWallet.getBalance();
					
					//WALLET UPGRADATION
					buyerFiatWallet.setBalance(buyerFiatMainBal - buyerOrder.getGrossAmount());
					Double buyerCryptoShadowBal = buyerCryptoWallet.getShadowBalance();
					buyerCryptoWallet.setBalance(buyerCryptoMainBal+buyerOrder.getCoinQuantity());
					buyerCryptoWallet.setShadowBalance(buyerCryptoShadowBal+buyerOrder.getCoinQuantity());
					
					//ORDER UPGRADATION
					buyerOrder.setOrderStatus(OrderStatus.APPROVED);					
					
					//COIN MANAGEMENT UPGRADATION
					coinManagement.setCoinInINR(coinManagement.getCoinInINR()+(buyerOrder.getNetAmount() - coinManagement.getPrice()*buyerOrder.getCoinQuantity()));
					initSupply = initSupply - buyerOrder.getCoinQuantity();
					coinManagement.setInitialSupply(initSupply);
					coinManagement.setProfit(coinManagement.getProfit()+buyerOrder.getFees());

					this.adminBuyerUpdate(buyerFiatWallet, buyerCryptoWallet, coinManagement, buyerOrder);
					this.createTransaction(buyerOrder.getUser().getUserId(), buyerOrder.getCoinName(), buyerOrder.getCoinQuantity(),
							WalletType.CRYPTO, "Buyer transaction is completed from coinManagement", buyerOrder.getPrice(),
								buyerOrder.getFees(), buyerOrder.getNetAmount(), buyerOrder.getGrossAmount(), adminId, OrderStatus.APPROVED);	
					
					System.out.println("Buyer transaction is completed from coinManagement because it has enough coins.");
					//return "Buyer transaction is completed from coinManagement because it has enough coins.";
				}
				
			}
		}
		
		return "TRANSACTIONS COMPLETED.";
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
	
		public void createTransaction(Integer buyerId, String coinName, Double coinQuantity, WalletType coinType, String description, 
				Double exchangeRate, Double fees, Double netAmount, Double grossAmount, Integer sellerId, OrderStatus orderStatus) 
		{
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
		
		public List<Transaction> getAllTransactions(){
			List<Transaction> transactions = transactionRepository.findAll();
			return transactions;			
		}
		
}			