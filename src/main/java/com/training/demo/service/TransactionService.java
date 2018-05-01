


package com.training.demo.service;
import java.util.ArrayList;
import java.util.Date;
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
	
	TransactionService transactionService = new TransactionService();

	public String gettransaction() {
		
		Wallet buyerFiatWallet = null;
		Wallet buyerCryptoWallet = null;
		Wallet sellerFiatWallet = null;
		Wallet sellerCryptoWallet =null;
		
		System.err.println("Transaction function");
		ArrayList<OrderTable> buyers = (ArrayList<OrderTable>) transactionRepository.getBuyer("BUYER");
		if (buyers.isEmpty() || buyers == null)
		{
			throw new RuntimeException("Buyer are not available");
		}
		ArrayList<OrderTable> sellers = (ArrayList<OrderTable>) transactionRepository.getSeller("SELLER");

		if (sellers.isEmpty() || sellers == null) 
		{
			throw new RuntimeException("seller are not available ");
		}
			
		for(OrderTable buyer : buyers) 
		{
			CoinManagement coinManagement = coinRepository.findOneByCoinName(buyer.getCoinName());
				for(OrderTable seller : sellers)
				{

					if((seller.getPrice() <= coinManagement.getPrice()) && seller.getPrice() <= buyer.getPrice())
					{
						Double quantity = seller.getCoinQuantity();
						Double price = seller.getPrice();
						User buyerUser = null;
						User sellerUser = seller.getUser();
						
					// Wallet retreival coding .............................
						
						Set<Wallet> sellerWallet = sellerUser.getWallets();
						for(Wallet sellWallet : sellerWallet) 
						{
							if(sellerFiatWallet == null)
							{
									if(sellWallet.getWalletType().equals(WalletType.FIAT.toString()))
									{
										sellerFiatWallet = sellWallet; 
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
									coinManagement.setExchangeRate(buyer.getPrice());
	
									Double buyerMainBal = buyerCryptoWallet.getBalance();
										buyerMainBal = buyerMainBal+buyer.getCoinQuantity();								
										buyerCryptoWallet.setBalance(buyerMainBal);
										buyer.setOrderStatus(OrderStatus.COMPLETED);								
										buyerFiatWallet.setBalance(buyerFiatWallet.getBalance()-buyer.getGrossAmount());
										
/*									Double balanceAfterDeduction = buyerFiatWallet.getBalance()-buyer.getGrossAmount();
  									userRepository.save(buyerUser);
									coinRepository.save(coinManagement);
									orderRepository.save(buyer);
				*/
					
									String description = "hellooooooooooooooooooooooooooo";
									transactionService.saveTransactionUpdatedModels(buyerUser, coinManagement, buyer, buyerFiatWallet, buyerCryptoWallet);
									transactionService.coinManagementTransaction(buyer, WalletType.valueOf(buyerCryptoWallet.getWalletType()),description) ;
									return "Transaction success currency == buyer";
							}
						
							if(coinManagement.getInitialSupply() < buyer.getCoinQuantity())
							{
								Double coinManagementInitBal = coinManagement.getInitialSupply();
								Double availableCoins = coinManagementInitBal;
								Double pendingCoins = buyer.getCoinQuantity()-availableCoins;
									
								Double buyerMainBal = buyerCryptoWallet.getBalance();
								buyerMainBal = buyerMainBal+availableCoins;								
								buyerCryptoWallet.setBalance(buyerMainBal);
								buyer.setOrderStatus(OrderStatus.COMPLETED);								
								buyerFiatWallet.setBalance(buyerFiatWallet.getBalance()-buyer.getGrossAmount());								
								
								coinManagementInitBal = 0.0;
								buyerCryptoWallet.setBalance(buyerMainBal);								
								coinManagement.setInitialSupply(coinManagementInitBal);								
								buyer.setCoinQuantity(pendingCoins);
								buyer.setOrderStatus(OrderStatus.PENDING);
								
/*								userRepository.save(buyerUser);
								coinRepository.save(coinManagement);											
								orderRepository.save(buyer); 
*/								
								
								String description = "Hiiiiiiiiiiiiiiiiiiiiiiiiiiiiii";
								
								transactionService.saveTransactionUpdatedModels(buyerUser, coinManagement, buyer, buyerFiatWallet, buyerCryptoWallet);								
								transactionService.coinManagementTransaction(buyer, WalletType.valueOf(buyerCryptoWallet.getWalletType()),description);								transactionService.coinManagementTransaction(buyer,WalletType.valueOf(buyerCryptoWallet.getWalletType()),description); 
								return availableCoins+"coin transferred. and "+pendingCoins+" are in pending.";

							}

							if(seller.getCoinQuantity() > buyer.getCoinQuantity())
							{
								Double coinManagementInitBal = coinManagement.getInitialSupply();
								Double availableCoins = coinManagementInitBal;
								Double remainingCoins = availableCoins - buyer.getCoinQuantity();									
								Double buyerMainBal = buyerCryptoWallet.getBalance();
									buyerMainBal = buyerMainBal+buyer.getCoinQuantity();
									buyerCryptoWallet.setBalance(buyerMainBal);
									coinManagement.setInitialSupply(remainingCoins);
									buyer.setOrderStatus(OrderStatus.COMPLETED);
								
								String description = "Hurayyyyyyyyyyyyyyyyyyyyyyyyyyy!!!!!!!!!!!!!!!!!!!!!!";
								
								transactionService.saveTransactionUpdatedModels(buyerUser, coinManagement, buyer, buyerFiatWallet, buyerCryptoWallet);								
								transactionService.coinManagementTransaction(buyer, WalletType.valueOf(buyerCryptoWallet.getWalletType()),description); 
								
								return "Buyerr order successs, coin remain in coin table "+remainingCoins;

							}
						}
					}
				}
		return "";
		}
	
	public void saveTransactionUpdatedModels(User buyerUser, CoinManagement coinManagement, OrderTable order, Wallet buyerFiatWallet, Wallet buyerCryptoWallet)
	{	
		userRepository.save(buyerUser);
		coinRepository.save(coinManagement);
		orderRepository.save(order);
		walletRepository.save(buyerFiatWallet);
		walletRepository.save(buyerCryptoWallet);
						
	}
	
	public void coinManagementTransaction(OrderTable buyer, WalletType coinType,String description) {
		
		Transaction transaction = new Transaction();
		transaction.setBuyerId(buyer.getUser().getUserId());
		transaction.setCoinName(buyer.getCoinName());
		transaction.setCoinQuantity(buyer.getCoinQuantity());
		transaction.setCoinType(coinType);
		transaction.setDescription(description);
		transaction.setExchangeRate(buyer.getPrice());
		transaction.setFees(buyer.getFees());
		transaction.setGrossAmount(buyer.getGrossAmount());
		transaction.setNetAmount(buyer.getNetAmount());
		transaction.setTransactionCreatedOn(new Date());
		transaction.setTransactionStatus(buyer.getOrderStatus());
		transactionRepository.save(transaction);											
	}
}