

package com.training.demo.service;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		if(buyers.equals("") || buyers == null)
		{
				return "buyers are not available";
		}
		
		List<OrderTable> sellers = transactionRepository.getSeller("SELLER");
		
				
		
		

		

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
			
			Set<Wallet> buyerWallets = buyerOrder.getUser().getWallets();
			Iterator<Wallet> walletIterator = buyerWallets.iterator();
			
			while(walletIterator.hasNext())
			{
				Wallet wall = walletIterator.next();
				if(wall.getWalletType().equals(WalletType.FIAT)) {
					buyerFiatWallet = wall;
				}
				if(wall.getWalletType().equals(WalletType.CRYPTO) && wall.getCoinName().equals(buyerOrder.getCoinName()))
				{
					buyerCryptoWallet = wall;
				}
				
			}
			
			if(sellers!=null)
			{

				Set<Wallet> sellerWallets = sellerOrder.getUser().getWallets();
				Iterator<Wallet> sellerWalletIterator = sellerWallets.iterator();
				while(sellerWalletIterator.hasNext())
				{
					Wallet wall = walletIterator.next();

					if(wall.getWalletType().equals(WalletType.FIAT)) {
						sellerFiatWallet = wall;
					}
					if(wall.getWalletType().equals(WalletType.CRYPTO) && wall.getCoinName().equals(buyerOrder.getCoinName()))
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
							
							this.sellerBuyerUpdate(sellerFiatWallet,sellerCryptoWallet,buyerFiatWallet,buyerCryptoWallet);
							this.createSellerTransaction(buyerOrder, sellerOrder, buyerCryptoWallet.getWalletType());
					}
					
				}
			}
			
			
			
			
			
		}
		return null;
	}
	public void sellerBuyerUpdate(Wallet sellerFiatWallet,Wallet sellerCryptoWallet, Wallet buyerFiatWallet, Wallet buyerCryptoWallet)
	{
		walletRepository.save(sellerFiatWallet);
		walletRepository.save(sellerCryptoWallet);
		walletRepository.save(buyerFiatWallet);
		walletRepository.save(buyerCryptoWallet);
	}
	
		public void createSellerTransaction(OrderTable buyerOrder, OrderTable sellerOrder,String buyerWalletType) 
		{
			Transaction transaction = new Transaction();
			transaction.setBuyerId(buyerOrder.getUser().getUserId());
			transaction.setCoinName(sellerOrder.getCoinName());
			transaction.setCoinQuantity(sellerOrder.getCoinQuantity());
			transaction.setCoinType(WalletType.valueOf(buyerCryptoWallet.getWalletType()));
			transaction.setDescription("xxxxxxxxxxxxxxxxxx");
			transaction.setExchangeRate(buyerOrder.getPrice());
			transaction.setFees(buyerOrder.getFees());
			transaction.setGrossAmount(buyerOrder.getGrossAmount());
			transaction.setNetAmount(buyerOrder.getNetAmount());
			transaction.setSellerId(sellerOrder.getUser().getUserId());
			transaction.setTransactionCreatedOn(new Date());
			transaction.setTransactionStatus(buyerOrder.getOrderStatus());
			transactionRepository.save(transaction);		
		}
		public void createAdminTransaction(OrderTable buyerOrder,WalletType buyerWalletType) {

				Transaction transaction = new Transaction();
				transaction.setBuyerId(buyerOrder.getUser().getUserId());
				transaction.setCoinName(buyerOrder.getCoinName());
				transaction.setCoinQuantity(buyerOrder.getCoinQuantity());
				transaction.setCoinType(WalletType.valueOf(buyerCryptoWallet.getWalletType()));
				transaction.setDescription("");
				transaction.setExchangeRate(buyerOrder.getPrice());
				transaction.setFees(buyerOrder.getFees());
				transaction.setGrossAmount(buyerOrder.getGrossAmount());
				transaction.setNetAmount(buyerOrder.getNetAmount());
				//	transaction.setSellerId();
				transaction.setTransactionCreatedOn(new Date());
				transaction.setTransactionStatus(buyerOrder.getOrderStatus());
				transactionRepository.save(transaction);	
		}
	
	
	
}			
		
		
		
/*
				for(OrderTable buyer : buyers) 
		{	
			System.out.println("88888888888888888888888888888888888888888888888888888     loop      888888888888888888");
			coinManagement = coinRepository.findOneByCoinName(buyer.getCoinName());
			
			
			if (sellers == null && coinManagement == null ) 
			{
				System.out.println("777777777777777777777777777777777777777777777777777");
				return "sellers are not available ";
			}
			
			else if(sellers != null || coinManagement != null) 
			{
				System.out.println("9999999999999999999999999999999999999999999999999999999999999999999999999999999999");
			
				
				
				
				
				if(sellers != null) 
				{
					for(OrderTable seller : sellers)
					{
					System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
					if((seller.getPrice() <= coinManagement.getPrice()) && seller.getPrice() <= buyer.getPrice())
					{
						System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
						Double quantity = seller.getCoinQuantity();
						Double price = seller.getPrice();
						User buyerUser = null;
						User sellerUser = seller.getUser();
						
					// Wallet retreival coding .............................
						System.out.println("ccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccc");
						Set<Wallet> sellerWallet = sellerUser.getWallets();
						System.out.println("ddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd");
						for(Wallet sellWallet : sellerWallet) 
						{				System.out.println("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
							if(sellerFiatWallet == null)
							{
								System.out.println("fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff");
									if(sellWallet.getWalletType().equals(WalletType.FIAT.toString()))
									{				System.out.println("gggggggggggggggggggggggggggggggggggggggggggggggggggggggggggg");
										sellerFiatWallet = sellWallet; 
										System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
									}
									
							}
							
							
							if(sellWallet.getWalletType().equals(WalletType.CRYPTO.toString()) && sellWallet.getCoinName().equals(seller.getCoinName()))
							{
								buyerUser = buyer.getUser();
								Set<Wallet> buyerWallet = buyerUser.getWallets();
								for(Wallet buyWallet:buyerWallet)
								{
									if(buyWallet.getWalletType().equals(WalletType.CRYPTO.toString())&& buyWallet.getCoinName().equals(seller.getCoinName()))
									{
										buyerCryptoWallet = buyWallet;
										sellerCryptoWallet = sellWallet;
									}

									if(buyerFiatWallet == null)
									{
											if(buyWallet.getWalletType().equals(WalletType.FIAT.toString()))
											{
												sellerFiatWallet = buyWallet; 
											}
									}
								}
							}
							
						}
									if(seller.getCoinQuantity() == buyer.getCoinQuantity())
									{
											
											Double sellerMainBal = sellerCryptoWallet.getBalance();
												sellerMainBal = sellerMainBal-seller.getCoinQuantity();

											Double buyerMainBal = buyerCryptoWallet.getBalance();
												buyerMainBal = buyerMainBal+buyer.getCoinQuantity();
											
												sellerCryptoWallet.setBalance(sellerMainBal);
												buyerCryptoWallet.setBalance(buyerMainBal);
											
											Double amountToBeAddedInFiat = seller.getCoinQuantity() * seller.getPrice();
											sellerFiatWallet.setBalance(sellerFiatWallet.getBalance()+amountToBeAddedInFiat);
											sellerFiatWallet.setShadowBalance(sellerFiatWallet.getShadowBalance()+amountToBeAddedInFiat);
											
											Double balanceAfterDeduction = buyerFiatWallet.getBalance()-buyer.getGrossAmount();
											Double shadowBalAfterDeduction = buyerFiatWallet.getShadowBalance()-buyer.getGrossAmount(); 
											buyerFiatWallet.setBalance(balanceAfterDeduction);
											buyerFiatWallet.setShadowBalance(shadowBalAfterDeduction);

											Double profitOnCoin = coinManagement.getProfit();
											profitOnCoin = (buyer.getGrossAmount()-seller.getNetAmount());											
											coinManagement.setProfit(profitOnCoin);
											coinRepository.save(coinManagement);
											walletRepository.save(sellerCryptoWallet);
											walletRepository.save(buyerCryptoWallet);
											walletRepository.save(sellerFiatWallet);
											walletRepository.save(buyerFiatWallet);
											
											userRepository.save(sellerUser);
											userRepository.save(buyerUser);
											
											seller.setOrderStatus(OrderStatus.COMPLETED);
											buyer.setOrderStatus(OrderStatus.COMPLETED);
											
											orderRepository.save(seller);
											orderRepository.save(buyer);
											
											Transaction transaction = new Transaction();
											transaction.setBuyerId(buyer.getUser().getUserId());
											transaction.setCoinName(seller.getCoinName());
											transaction.setCoinQuantity(seller.getCoinQuantity());
											transaction.setCoinType(WalletType.valueOf(buyerCryptoWallet.getWalletType()));
											transaction.setDescription("xxxxxxxxxxxxxxxxxx");
											transaction.setExchangeRate(buyer.getPrice());
											transaction.setFees(buyer.getFees());
											transaction.setGrossAmount(buyer.getGrossAmount());
											transaction.setNetAmount(buyer.getNetAmount());
											transaction.setSellerId(seller.getUser().getUserId());
											transaction.setTransactionCreatedOn(new Date());
											transaction.setTransactionStatus(buyer.getOrderStatus());
											transactionRepository.save(transaction);											
											return "order success.";
										}
									
										if(seller.getCoinQuantity() < buyer.getCoinQuantity())
										{
											Double sellerMainBal = sellerCryptoWallet.getBalance();
											sellerMainBal = sellerMainBal-seller.getCoinQuantity();
											
											Double availableCoins = seller.getCoinQuantity();
											Double pendingCoins = buyer.getCoinQuantity()-seller.getCoinQuantity();
											
											Double buyerMainBal = buyerCryptoWallet.getBalance();
											buyerMainBal = buyerMainBal+availableCoins;
											
											sellerCryptoWallet.setBalance(sellerMainBal);
											buyerCryptoWallet.setBalance(buyerMainBal);
											
/////////////////////						coinManagement.setCoinInINR(coinManagement.getInitialSupply()*coinManagement.getPrice());

											
											
											Double amountToBeAddedInFiat = (seller.getCoinQuantity() * seller.getPrice());
											
											sellerFiatWallet.setBalance(sellerFiatWallet.getBalance()+amountToBeAddedInFiat);
											sellerFiatWallet.setShadowBalance(sellerFiatWallet.getShadowBalance()+amountToBeAddedInFiat);
												
											Double profitOnCoin = (amountToBeAddedInFiat *(coinManagement.getProfit()/100));											
											Double amountToBeDeduct = (amountToBeAddedInFiat + profitOnCoin);
											Double balanceAfterDeduction = (buyerFiatWallet.getBalance()-amountToBeDeduct);
											Double shadowBalAfterDeduction = (buyerFiatWallet.getShadowBalance()-amountToBeDeduct);
											
											buyerFiatWallet.setBalance(balanceAfterDeduction);
											buyerFiatWallet.setShadowBalance(shadowBalAfterDeduction);
											
											coinManagement.setProfit(profitOnCoin);

											coinRepository.save(coinManagement);
											walletRepository.save(sellerCryptoWallet);
											walletRepository.save(buyerCryptoWallet);
											walletRepository.save(sellerFiatWallet);
											walletRepository.save(buyerFiatWallet);

											
											userRepository.save(sellerUser);
											userRepository.save(buyerUser);

											seller.setOrderStatus(OrderStatus.COMPLETED);											
											buyer.setCoinQuantity(pendingCoins);
											buyer.setOrderStatus(OrderStatus.PENDING);
											
											orderRepository.save(seller);
											orderRepository.save(buyer);
											
											Transaction transaction = new Transaction();
											transaction.setBuyerId(buyer.getUser().getUserId());
											transaction.setCoinName(seller.getCoinName());
											transaction.setCoinQuantity(seller.getCoinQuantity());
											transaction.setCoinType(WalletType.valueOf(buyerCryptoWallet.getWalletType()));
											transaction.setDescription("xxxxxxxxxxxxxxxxxx");
											transaction.setExchangeRate(buyer.getPrice());
											transaction.setFees(buyer.getFees());
											transaction.setGrossAmount(buyer.getGrossAmount());
											transaction.setNetAmount(buyer.getNetAmount());
											transaction.setSellerId(seller.getUser().getUserId());
											transaction.setTransactionCreatedOn(new Date());
											transaction.setTransactionStatus(seller.getOrderStatus());
											transactionRepository.save(transaction);											
											return availableCoins+"coin transferred. and "+pendingCoins+" are in pending.";

										}

										if(seller.getCoinQuantity() > buyer.getCoinQuantity())
										{
											Double sellerMainBal = sellerCryptoWallet.getBalance();
												sellerMainBal = sellerMainBal-buyer.getCoinQuantity();
											
											Double sellerFiatBal = sellerFiatWallet.getBalance();
												sellerFiatBal = sellerFiatBal + buyer.getNetAmount();
											
											Double sellerFiatShadowBal = sellerFiatWallet.getShadowBalance();											
												sellerFiatShadowBal = sellerFiatShadowBal + buyer.getNetAmount(); 
																						
											Double buyerMainBal = buyerCryptoWallet.getBalance();
												buyerMainBal = buyerMainBal + buyer.getCoinQuantity();
													
											Double buyerFiatBal = buyerFiatWallet.getBalance();	
												buyerFiatBal = buyerFiatBal - buyer.getGrossAmount();
												
											Double pendingCoins = seller.getCoinQuantity()-buyer.getCoinQuantity();
									
											Double profitOnCoins = buyer.getGrossAmount()-buyer.getNetAmount();
											sellerFiatWallet.setBalance(sellerFiatBal);
											sellerFiatWallet.setShadowBalance(sellerFiatShadowBal);
												
											buyerFiatWallet.setBalance(buyerFiatBal);
											
											coinManagement.setProfit(profitOnCoins);
											
											buyerCryptoWallet.setBalance(buyerMainBal);
											sellerCryptoWallet.setBalance(sellerMainBal);
											
											coinRepository.save(coinManagement);
											walletRepository.save(sellerCryptoWallet);
											walletRepository.save(buyerCryptoWallet);
											walletRepository.save(sellerFiatWallet);
											walletRepository.save(buyerFiatWallet);

											userRepository.save(sellerUser);
											userRepository.save(buyerUser);
																						
											buyer.setOrderStatus(OrderStatus.COMPLETED);
											
											seller.setCoinQuantity(pendingCoins);
											seller.setOrderStatus(OrderStatus.PENDING);
											
											orderRepository.save(seller);
											orderRepository.save(buyer);
											
											Transaction transaction = new Transaction();
											transaction.setBuyerId(buyer.getUser().getUserId());
											transaction.setCoinName(seller.getCoinName());
											transaction.setCoinQuantity(seller.getCoinQuantity());
											transaction.setCoinType(WalletType.valueOf(buyerCryptoWallet.getWalletType()));
											transaction.setDescription("xxxxxxxxxxxxxxxxxx");
											transaction.setExchangeRate(buyer.getPrice());
											transaction.setFees(buyer.getFees());
											transaction.setGrossAmount(buyer.getGrossAmount());
											transaction.setNetAmount(buyer.getNetAmount());
											transaction.setSellerId(seller.getUser().getUserId());
											transaction.setTransactionCreatedOn(new Date());
											transaction.setTransactionStatus(buyer.getOrderStatus());
											transactionRepository.save(transaction);											
											return "Buyer Order Completed and Seller Order In Pending";

										}
							}
					}
	//////////////////////////////////				
///////////////COIN MANAGEMENT TRANSACTION
	/////////////////////////////////				
					
					else if((seller.getPrice() > coinManagement.getPrice()) && seller.getPrice() > buyer.getPrice() )
					{
						Double quantity = coinManagement.getInitialSupply();
						Double price = coinManagement.getPrice();
						User buyerUser;
						
								buyerUser = buyer.getUser();
								Set<Wallet> buyerWallet = buyerUser.getWallets();
								for(Wallet buyWallet:buyerWallet)
								{
									if(buyWallet.getWalletType().equals(WalletType.CRYPTO.toString())&& buyWallet.getCoinName().equals(coinManagement.getCoinName()))
									{
										buyerCryptoWallet = buyWallet;
									}
								}
							
							if(coinManagement.getInitialSupply() == buyer.getCoinQuantity())
							{									
								Double coinManagementInitBal = coinManagement.getInitialSupply();
									coinManagementInitBal = coinManagementInitBal - buyer.getCoinQuantity();
									
								Double buyerMainBal = buyerCryptoWallet.getBalance();
									buyerMainBal = buyerMainBal+buyer.getCoinQuantity();
								buyerCryptoWallet.setBalance(buyerMainBal);
								coinManagement.setCoinInINR(buyer.getPrice()-coinManagement.getPrice());
									
									userRepository.save(buyerUser);
									buyer.setOrderStatus(OrderStatus.COMPLETED);
									
									
									
									Transaction transaction = new Transaction();
									transaction.setBuyerId(buyer.getUser().getUserId());
									transaction.setCoinName(buyer.getCoinName());
									transaction.setCoinQuantity(buyer.getCoinQuantity());
									transaction.setCoinType(WalletType.valueOf(buyerCryptoWallet.getWalletType()));
									transaction.setDescription("UserOrder == CurrencyStock");
									transaction.setExchangeRate(buyer.getPrice());
									transaction.setFees(buyer.getFees());
									transaction.setGrossAmount(buyer.getGrossAmount());
									transaction.setNetAmount(buyer.getNetAmount());
									//transaction.setSellerId();
									transaction.setTransactionCreatedOn(new Date());
									transaction.setTransactionStatus(buyer.getOrderStatus());
									transactionRepository.save(transaction);											
									
									coinRepository.save(coinManagement);
									orderRepository.save(buyer);
									
									return "Transaction success currency == buyer";
							}
						
							if(coinManagement.getInitialSupply() < buyer.getCoinQuantity())
							{
								Double coinManagementInitBal = coinManagement.getInitialSupply();
								Double availableCoins = coinManagementInitBal;
								Double pendingCoins = buyer.getCoinQuantity()-availableCoins;

									
								Double buyerMainBal = buyerCryptoWallet.getBalance();
								buyerMainBal = buyerMainBal+availableCoins;
								
																
								coinManagementInitBal = 0.0;
								buyerCryptoWallet.setBalance(buyerMainBal);
								userRepository.save(buyerUser);
								coinManagement.setInitialSupply(coinManagementInitBal);
								coinManagement.setCoinInINR(buyer.getPrice()-coinManagement.getPrice());
								coinRepository.save(coinManagement);											
								buyer.setCoinQuantity(pendingCoins);
								buyer.setOrderStatus(OrderStatus.PENDING);
								
								
								Transaction transaction = new Transaction();
								transaction.setBuyerId(buyer.getUser().getUserId());
								transaction.setCoinName(buyer.getCoinName());
								transaction.setCoinQuantity(buyer.getCoinQuantity());
								transaction.setCoinType(WalletType.valueOf(buyerCryptoWallet.getWalletType()));
								transaction.setDescription("Currency <<<<<<<< buyerOrder");
								transaction.setExchangeRate(buyer.getPrice());
								transaction.setFees(buyer.getFees());
								transaction.setGrossAmount(buyer.getGrossAmount());
								transaction.setNetAmount(buyer.getNetAmount());
								//transaction.setSellerId();
								transaction.setTransactionCreatedOn(new Date());
								transaction.setTransactionStatus(buyer.getOrderStatus());
								transactionRepository.save(transaction);											
								

								orderRepository.save(buyer);
								return availableCoins+"coin transferred. and "+pendingCoins+" are in pending.";

							}

							if(coinManagement.getInitialSupply() > buyer.getCoinQuantity())
							{
								Double coinManagementInitBal = coinManagement.getInitialSupply();
								
								coinManagementInitBal = coinManagementInitBal - buyer.getCoinQuantity();
								
								Double fiatBal = buyerFiatWallet.getBalance();
								fiatBal = fiatBal - buyer.getGrossAmount();
								buyerFiatWallet.setBalance(fiatBal);
								walletRepository.save(buyerFiatWallet);
								
								Double buyerMainBal = buyerCryptoWallet.getBalance();
								buyerMainBal = buyerMainBal+buyer.getCoinQuantity();
									
								buyerCryptoWallet.setBalance(buyerMainBal);	
								Double buyerShadowBal = buyer.getNetAmount();
								buyerCryptoWallet.setShadowBalance(buyerShadowBal);
								
								buyerCryptoWallet.setBalance(buyerMainBal);
								
								userRepository.save(buyerUser);
								
								coinManagement.setInitialSupply(coinManagementInitBal);
								coinManagement.setExchangeRate(buyer.getPrice());
								coinManagement.setCoinInINR(buyer.getPrice()-coinManagement.getPrice());
								coinRepository.save(coinManagement);
								
								
								Transaction transaction = new Transaction();
								transaction.setBuyerId(buyer.getUser().getUserId());
								transaction.setCoinName(buyer.getCoinName());
								transaction.setCoinQuantity(buyer.getCoinQuantity());
								transaction.setCoinType(WalletType.valueOf(buyerCryptoWallet.getWalletType()));
								transaction.setDescription("");
								transaction.setExchangeRate(buyer.getPrice());
								transaction.setFees(buyer.getFees());
								transaction.setGrossAmount(buyer.getGrossAmount());
								transaction.setNetAmount(buyer.getNetAmount());
								//transaction.setSellerId();
								transaction.setTransactionCreatedOn(new Date());
								transaction.setTransactionStatus(buyer.getOrderStatus());
								transactionRepository.save(transaction);											
								
								
								buyer.setOrderStatus(OrderStatus.COMPLETED);
								orderRepository.save(buyer);
								
								return "Buyerr order successs, coin remain in coin table "+coinManagementInitBal;

							}
							return "elseeeeeeeeeeeeeeeeeeeeeeee iffffffffffffffffffffffff";
					}
				}
			}
		}
		
	return "hhhhhhhhhhhhhhhhhhhhhhhhsssssssssssssssssssssiiiiiiiiiiiiiiiiiiiiiiiiiiii";

		}
	
}
	*/				