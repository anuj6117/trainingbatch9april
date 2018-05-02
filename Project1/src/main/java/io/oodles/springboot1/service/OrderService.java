package io.oodles.springboot1.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.oodles.springboot1.comparator.Sortbyprice;
import io.oodles.springboot1.enums.OrderStatus;
import io.oodles.springboot1.enums.OrderType;
import io.oodles.springboot1.enums.WalletType;
import io.oodles.springboot1.model.BuyOrder;
import io.oodles.springboot1.model.Currency;
import io.oodles.springboot1.model.UserOrder;
import io.oodles.springboot1.model.UserTransaction;
import io.oodles.springboot1.model.Users;
import io.oodles.springboot1.model.Wallet;
import io.oodles.springboot1.repository.CurrencyRepository;
import io.oodles.springboot1.repository.OrderRepository;
import io.oodles.springboot1.repository.TransactionRepository;
import io.oodles.springboot1.repository.UsersRepository;
import io.oodles.springboot1.repository.WalletRepository;
@Service
public class OrderService {
	Users user;
	Date date=new Date();
	Currency currency;
	List<UserOrder> listBuyOrder=new ArrayList<UserOrder>();
	List<UserOrder> listSellOrder=new ArrayList<UserOrder>();
	
	@Autowired
	CurrencyRepository currencyRepository;
	@Autowired
	UsersRepository usersRepository;
	
	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	TransactionRepository transactionRepository;
	
	@Autowired
	WalletRepository walletRepository;
	public String buy(BuyOrder buyOrder) {
	
		
		currency=currencyRepository.findByCoinName(buyOrder.getCoinname());
		
		
		
		
		
		Integer netAmount=buyOrder.getPrice()*buyOrder.getCoinQuantity();
		
	    Integer grossAmount=(netAmount+((netAmount*currency.getFees())/100));
	    
		// TODO Auto-generated method stub
		user=usersRepository.findByUserId(buyOrder.getUserid());
		
		UserOrder userOrder=new UserOrder();
		
		
		//user=usersRepository.findByUserid(buyOrder.getUserid());
		userOrder.setOrdertype(OrderType.BUY);

		userOrder.setCoinname(buyOrder.getCoinname());
		
		userOrder.setCoinQuantity(buyOrder.getCoinQuantity());
		
		userOrder.setCoinType(buyOrder.getCoinType());
		
	    userOrder.setFee(currency.getFees());
		
		userOrder.setGrossAmount(grossAmount);
		
		userOrder.setNetAmount(netAmount);
		
		userOrder.setOrderCreatedOn(date);
		
		userOrder.setOrderStatus(OrderStatus.PENDING);
		
		userOrder.setPrice(buyOrder.getPrice());
	
		userOrder.setUsersorder(user);
		
	    orderRepository.save(userOrder);
	    System.out.println("????????????????");
	    listBuyOrder.add(userOrder);
	    System.out.println("<<<<<<<<<<<<<<<"+listBuyOrder);
	    return "Success";
		
		
		
	}
	public String sell(BuyOrder buyOrder) {
		// TODO Auto-generated method stub
        currency=currencyRepository.findByCoinName(buyOrder.getCoinname());
		
		
		
		
		
		Integer netAmount=buyOrder.getPrice()*buyOrder.getCoinQuantity();
		
	    Integer grossAmount=(netAmount+((netAmount*currency.getFees())/100));
	    
		// TODO Auto-generated method stub
		user=usersRepository.findByUserId(buyOrder.getUserid());
		
		UserOrder userOrder=new UserOrder();
		
		
		//user=usersRepository.findByUserid(buyOrder.getUserid());
		userOrder.setOrdertype(OrderType.SELL);

		userOrder.setCoinname(buyOrder.getCoinname());
		
		userOrder.setCoinQuantity(buyOrder.getCoinQuantity());
		
		userOrder.setCoinType(buyOrder.getCoinType());
		
	    //userOrder.setFee(currency.getFee());
		
		userOrder.setGrossAmount(grossAmount);
		
		userOrder.setNetAmount(netAmount);
		
		userOrder.setOrderCreatedOn(date);
		
		userOrder.setOrderStatus(OrderStatus.PENDING);
		
		userOrder.setPrice(buyOrder.getPrice());
	
		userOrder.setUsersorder(user);
		
		
	    orderRepository.save(userOrder);
	    listSellOrder.add(userOrder);
	    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>");
	    System.out.println("<<<<<<<<<<<<<<<<"+listSellOrder);
	    return "Success";
		
	}
	public UserOrder get(int id) {
		// TODO Auto-generated method stub
		
		return orderRepository.findById(id).get();

	}
	
	public void transaction() {
	 List<UserOrder> buyerlist=orderRepository.findByOrdertype1();
	 //System.out.println("?????????????"+buyerlist.size());
	 Collections.sort(buyerlist,new Sortbyprice());
	 Collections.reverse(buyerlist);
	 
	 
	 List<UserOrder> sellerlist=orderRepository.findByOrdertype();
		
     Collections.sort(sellerlist,new Sortbyprice());
     if(sellerlist.size()!=0) {
    	 for(UserOrder b:buyerlist) {
    	for(UserOrder s:sellerlist) {	 
    	 
    	 
    	 currency=currencyRepository.findByCoinNameAndCoinType(b.getCoinname(),b.getCoinType());
    	 
    	 if(s.getPrice()>currency.getPrice()) {
    		 if(b.getPrice()>currency.getPrice()) {
    			 if(b.getCoinname().equals(currency.getCoinName())&& b.getCoinType().equals(currency.getCoinType())) {
        			 UserTransaction userTransaction=new UserTransaction();
         	    	userTransaction.setBuyer_id(b.getOrderid());
         	    	     		userTransaction.setCoinName(b.getCoinname());
         	    	     		userTransaction.setCoinType(b.getCoinType());
         	    	     		userTransaction.setDateCreated(date);
         	    	     		userTransaction.setDescription("Approveds");
         	    	     		userTransaction.setFees(b.getFee());
         	    	     		Integer netAmount1=b.getPrice()*b.getCoinQuantity();
         	    				
         	    			    Integer grossAmount1=(netAmount1+((netAmount1*b.getFee())/100));
         	    	     		userTransaction.setGrossAmount(grossAmount1);
         	    	     		userTransaction.setNetAmount(netAmount1);
         	    	     		userTransaction.setTransactionstatus(OrderStatus.APPROVE);
         	    	     		transactionRepository.save(userTransaction);
         	    	     		
         	    	     		
         	    	     		Integer currencycoin=currency.getInitialSupply()-b.getCoinQuantity();
         	    	     		System.out.println("//////////"+currencycoin);
         	    	     		Integer buyercoin=currency.getInitialSupply()-currencycoin;
         	    	     		System.out.println(">>>>>>>>>>"+ buyercoin+b.getCoinQuantity());
         	    	     		b.setCoinQuantity(b.getCoinQuantity()-buyercoin);
         	    				b.setOrderStatus(OrderStatus.APPROVE);
         	    				orderRepository.save(b);
         	    	     		
         	    	     		Integer inrconversion=(currency.getInitialSupply()*currency.getPrice())-(b.getCoinQuantity()*b.getPrice());
         	    				//System.out.println(inrconversion);
         	    	     		currency.setINRconversion(inrconversion);
         	    				Integer initial=currency.getInitialSupply()-b.getCoinQuantity();
         	    				//System.out.println(initial);
         	    				currency.setInitialSupply(initial);
         	    				Integer profit=b.getGrossAmount()-b.getNetAmount();
         	    				//System.out.println(profit);
         	    				currency.setProfit(profit);
         	    				currencyRepository.save(currency);
         	    				
         	    				
         	    				//For FIAT
         	    				Users user=b.getUsersorder();
         	    				System.out.println(">>>>>>>>>>>>>"+user.getUserId());
         	    				Wallet wallet=walletRepository.findByUsersAndCoinTypeAndCoinName(user,WalletType.FIAT,"INR");;
         	    				Integer bal=wallet.getBalance()-b.getGrossAmount();
         	    				System.out.println(bal);
         	    				wallet.setBalance(bal);
         	    				wallet.setShadowbalance(bal);
         	    				walletRepository.save(wallet);
         	    				
         	    				//For CRYPTO
         	    				Users user1=b.getUsersorder();
         	    				System.out.println(user1.getUserId());
         	    				String s1=b.getCoinname();
         	    				System.out.println(">>>>>>>>>>"+s1);
         Wallet wallet1=walletRepository.findByUsersAndCoinTypeAndCoinName(user1, WalletType.CRYPTO,b.getCoinname());
         	    				System.out.println(">>>>>>>>>>"+wallet1.getId()+wallet1.getCoinName());
         	    				wallet1.setBalance(b.getCoinQuantity());
         	    				wallet1.setShadowbalance(b.getCoinQuantity());
         	    				walletRepository.save(wallet1);

        		 }
    			 
    		 }
    		 
    		 
    	 }else {
         if(b.getCoinQuantity()>s.getCoinQuantity()) {
        	 if(b.getCoinname().equals(currency.getCoinName())&& b.getCoinType().equals(currency.getCoinType())) {
    			 UserTransaction userTransaction=new UserTransaction();
     	    	userTransaction.setBuyer_id(b.getOrderid());
     	    	     		userTransaction.setCoinName(b.getCoinname());
     	    	     		userTransaction.setCoinType(b.getCoinType());
     	    	     		userTransaction.setDateCreated(date);
     	    	     		userTransaction.setDescription("Approveds");
     	    	     		userTransaction.setFees(b.getFee());
     	    	     		Integer netAmount1=b.getPrice()*b.getCoinQuantity();
     	    				
     	    			    Integer grossAmount1=(netAmount1+((netAmount1*b.getFee())/100));
     	    	     		userTransaction.setGrossAmount(grossAmount1);
     	    	     		userTransaction.setNetAmount(netAmount1);
     	    	     		userTransaction.setTransactionstatus(OrderStatus.APPROVE);
     	    	     		transactionRepository.save(userTransaction);
     	    	     		
     	    	     		
     	    	     		Integer currencycoin=currency.getInitialSupply()-b.getCoinQuantity();
     	    	     		System.out.println("//////////"+currencycoin);
     	    	     		Integer buyercoin=currency.getInitialSupply()-currencycoin;
     	    	     		System.out.println(">>>>>>>>>>"+ buyercoin+b.getCoinQuantity());
     	    	     		b.setCoinQuantity(b.getCoinQuantity()-buyercoin);
     	    				b.setOrderStatus(OrderStatus.APPROVE);
     	    				orderRepository.save(b);
     	    	     		
     	    	     		Integer inrconversion=(currency.getInitialSupply()*currency.getPrice())-(b.getCoinQuantity()*b.getPrice());
     	    				//System.out.println(inrconversion);
     	    	     		currency.setINRconversion(inrconversion);
     	    				Integer initial=currency.getInitialSupply()-b.getCoinQuantity();
     	    				//System.out.println(initial);
     	    				currency.setInitialSupply(initial);
     	    				Integer profit=b.getGrossAmount()-b.getNetAmount();
     	    				//System.out.println(profit);
     	    				currency.setProfit(profit);
     	    				currencyRepository.save(currency);
     	    				
     	    				
     	    				//For FIAT
     	    				Users user=b.getUsersorder();
     	    				System.out.println(">>>>>>>>>>>>>"+user.getUserId());
     	    				Wallet wallet=walletRepository.findByUsersAndCoinTypeAndCoinName(user,WalletType.FIAT,"INR");;
     	    				Integer bal=wallet.getBalance()-b.getGrossAmount();
     	    				System.out.println(bal);
     	    				wallet.setBalance(bal);
     	    				wallet.setShadowbalance(bal);
     	    				walletRepository.save(wallet);
     	    				
     	    				//For CRYPTO
     	    				Users user1=b.getUsersorder();
     	    				System.out.println(user1.getUserId());
     	    				String s1=b.getCoinname();
     	    				System.out.println(">>>>>>>>>>"+s1);
     Wallet wallet1=walletRepository.findByUsersAndCoinTypeAndCoinName(user1, WalletType.CRYPTO,b.getCoinname());
     	    				System.out.println(">>>>>>>>>>"+wallet1.getId()+wallet1.getCoinName());
     	    				wallet1.setBalance(b.getCoinQuantity());
     	    				wallet1.setShadowbalance(b.getCoinQuantity());
     	    				walletRepository.save(wallet1);

    		 }
        	 
    			 
    		 }else if(b.getCoinQuantity() == s.getCoinQuantity()) {
    			 if(b.getCoinname().equals(currency.getCoinName())&& b.getCoinType().equals(currency.getCoinType())) {
        			 UserTransaction userTransaction=new UserTransaction();
         	    	userTransaction.setBuyer_id(b.getOrderid());
         	    	     		userTransaction.setCoinName(b.getCoinname());
         	    	     		userTransaction.setCoinType(b.getCoinType());
         	    	     		userTransaction.setDateCreated(date);
         	    	     		userTransaction.setDescription("Approveds");
         	    	     		userTransaction.setFees(b.getFee());
         	    	     		Integer netAmount1=b.getPrice()*b.getCoinQuantity();
         	    				
         	    			    Integer grossAmount1=(netAmount1+((netAmount1*b.getFee())/100));
         	    	     		userTransaction.setGrossAmount(grossAmount1);
         	    	     		userTransaction.setNetAmount(netAmount1);
         	    	     		userTransaction.setTransactionstatus(OrderStatus.APPROVE);
         	    	     		transactionRepository.save(userTransaction);
         	    	     		
         	    	     		
         	    	     		Integer currencycoin=currency.getInitialSupply()-b.getCoinQuantity();
         	    	     		System.out.println("//////////"+currencycoin);
         	    	     		Integer buyercoin=currency.getInitialSupply()-currencycoin;
         	    	     		System.out.println(">>>>>>>>>>"+ buyercoin+b.getCoinQuantity());
         	    	     		b.setCoinQuantity(b.getCoinQuantity()-buyercoin);
         	    				b.setOrderStatus(OrderStatus.APPROVE);
         	    				orderRepository.save(b);
         	    	     		
         	    	     		Integer inrconversion=(currency.getInitialSupply()*currency.getPrice())-(b.getCoinQuantity()*b.getPrice());
         	    				//System.out.println(inrconversion);
         	    	     		currency.setINRconversion(inrconversion);
         	    				Integer initial=currency.getInitialSupply()-b.getCoinQuantity();
         	    				//System.out.println(initial);
         	    				currency.setInitialSupply(initial);
         	    				Integer profit=b.getGrossAmount()-b.getNetAmount();
         	    				//System.out.println(profit);
         	    				currency.setProfit(profit);
         	    				currencyRepository.save(currency);
         	    				
         	    				
         	    				//For FIAT
         	    				Users user=b.getUsersorder();
         	    				System.out.println(">>>>>>>>>>>>>"+user.getUserId());
         	    				Wallet wallet=walletRepository.findByUsersAndCoinTypeAndCoinName(user,WalletType.FIAT,"INR");;
         	    				Integer bal=wallet.getBalance()-b.getGrossAmount();
         	    				System.out.println(bal);
         	    				wallet.setBalance(bal);
         	    				wallet.setShadowbalance(bal);
         	    				walletRepository.save(wallet);
         	    				
         	    				//For CRYPTO
         	    				Users user1=b.getUsersorder();
         	    				System.out.println(user1.getUserId());
         	    				String s1=b.getCoinname();
         	    				System.out.println(">>>>>>>>>>"+s1);
         Wallet wallet1=walletRepository.findByUsersAndCoinTypeAndCoinName(user1, WalletType.CRYPTO,b.getCoinname());
         	    				System.out.println(">>>>>>>>>>"+wallet1.getId()+wallet1.getCoinName());
         	    				wallet1.setBalance(b.getCoinQuantity());
         	    				wallet1.setShadowbalance(b.getCoinQuantity());
         	    				walletRepository.save(wallet1);

        		 }
    			 
    		 }else{
    			 if(b.getCoinname().equals(currency.getCoinName())&& b.getCoinType().equals(currency.getCoinType())) {
        			 UserTransaction userTransaction=new UserTransaction();
         	    	userTransaction.setBuyer_id(b.getOrderid());
         	    	     		userTransaction.setCoinName(b.getCoinname());
         	    	     		userTransaction.setCoinType(b.getCoinType());
         	    	     		userTransaction.setDateCreated(date);
         	    	     		userTransaction.setDescription("Approveds");
         	    	     		userTransaction.setFees(b.getFee());
         	    	     		Integer netAmount1=b.getPrice()*b.getCoinQuantity();
         	    				
         	    			    Integer grossAmount1=(netAmount1+((netAmount1*b.getFee())/100));
         	    	     		userTransaction.setGrossAmount(grossAmount1);
         	    	     		userTransaction.setNetAmount(netAmount1);
         	    	     		userTransaction.setTransactionstatus(OrderStatus.APPROVE);
         	    	     		transactionRepository.save(userTransaction);
         	    	     		
         	    	     		
         	    	     		Integer currencycoin=currency.getInitialSupply()-b.getCoinQuantity();
         	    	     		System.out.println("//////////"+currencycoin);
         	    	     		Integer buyercoin=currency.getInitialSupply()-currencycoin;
         	    	     		System.out.println(">>>>>>>>>>"+ buyercoin+b.getCoinQuantity());
         	    	     		b.setCoinQuantity(b.getCoinQuantity()-buyercoin);
         	    				b.setOrderStatus(OrderStatus.APPROVE);
         	    				orderRepository.save(b);
         	    	     		
         	    	     		Integer inrconversion=(currency.getInitialSupply()*currency.getPrice())-(b.getCoinQuantity()*b.getPrice());
         	    				//System.out.println(inrconversion);
         	    	     		currency.setINRconversion(inrconversion);
         	    				Integer initial=currency.getInitialSupply()-b.getCoinQuantity();
         	    				//System.out.println(initial);
         	    				currency.setInitialSupply(initial);
         	    				Integer profit=b.getGrossAmount()-b.getNetAmount();
         	    				//System.out.println(profit);
         	    				currency.setProfit(profit);
         	    				currencyRepository.save(currency);
         	    				
         	    				
         	    				//For FIAT
         	    				Users user=b.getUsersorder();
         	    				System.out.println(">>>>>>>>>>>>>"+user.getUserId());
         	    				Wallet wallet=walletRepository.findByUsersAndCoinTypeAndCoinName(user,WalletType.FIAT,"INR");;
         	    				Integer bal=wallet.getBalance()-b.getGrossAmount();
         	    				System.out.println(bal);
         	    				wallet.setBalance(bal);
         	    				wallet.setShadowbalance(bal);
         	    				walletRepository.save(wallet);
         	    				
         	    				//For CRYPTO
         	    				Users user1=b.getUsersorder();
         	    				System.out.println(user1.getUserId());
         	    				String s1=b.getCoinname();
         	    				System.out.println(">>>>>>>>>>"+s1);
         Wallet wallet1=walletRepository.findByUsersAndCoinTypeAndCoinName(user1, WalletType.CRYPTO,b.getCoinname());
         	    				System.out.println(">>>>>>>>>>"+wallet1.getId()+wallet1.getCoinName());
         	    				wallet1.setBalance(b.getCoinQuantity());
         	    				wallet1.setShadowbalance(b.getCoinQuantity());
         	    				walletRepository.save(wallet1);

        		 }
    			 
    		 } 
    	 }}}}
    	 
     else {
    	 //admin transaction
    	 
    	 
    	 for(UserOrder b:buyerlist) {
    		 
    		 currency=currencyRepository.findByCoinNameAndCoinType(b.getCoinname(),b.getCoinType());
    		 if(b.getCoinname().equals(currency.getCoinName())&& b.getCoinType().equals(currency.getCoinType())) {
    			 UserTransaction userTransaction=new UserTransaction();
     	    	userTransaction.setBuyer_id(b.getOrderid());
     	    	     		userTransaction.setCoinName(b.getCoinname());
     	    	     		userTransaction.setCoinType(b.getCoinType());
     	    	     		userTransaction.setDateCreated(date);
     	    	     		userTransaction.setDescription("Approveds");
     	    	     		userTransaction.setFees(b.getFee());
     	    	     		Integer netAmount1=b.getPrice()*b.getCoinQuantity();
     	    				
     	    			    Integer grossAmount1=(netAmount1+((netAmount1*b.getFee())/100));
     	    	     		userTransaction.setGrossAmount(grossAmount1);
     	    	     		userTransaction.setNetAmount(netAmount1);
     	    	     		userTransaction.setTransactionstatus(OrderStatus.APPROVE);
     	    	     		transactionRepository.save(userTransaction);
     	    	     		
     	    	     		
     	    	     		Integer currencycoin=currency.getInitialSupply()-b.getCoinQuantity();
     	    	     		System.out.println("//////////"+currencycoin);
     	    	     		Integer buyercoin=currency.getInitialSupply()-currencycoin;
     	    	     		System.out.println(">>>>>>>>>>"+ buyercoin+b.getCoinQuantity());
     	    	     		b.setCoinQuantity(b.getCoinQuantity()-buyercoin);
     	    				b.setOrderStatus(OrderStatus.APPROVE);
     	    				orderRepository.save(b);
     	    	     		
     	    	     		Integer inrconversion=(currency.getInitialSupply()*currency.getPrice())-(b.getCoinQuantity()*b.getPrice());
     	    				//System.out.println(inrconversion);
     	    	     		currency.setINRconversion(inrconversion);
     	    				Integer initial=currency.getInitialSupply()-b.getCoinQuantity();
     	    				//System.out.println(initial);
     	    				currency.setInitialSupply(initial);
     	    				Integer profit=b.getGrossAmount()-b.getNetAmount();
     	    				//System.out.println(profit);
     	    				currency.setProfit(profit);
     	    				currencyRepository.save(currency);
     	    				
     	    				
     	    				//For FIAT
     	    				Users user=b.getUsersorder();
     	    				System.out.println(">>>>>>>>>>>>>"+user.getUserId());
     	    				Wallet wallet=walletRepository.findByUsersAndCoinTypeAndCoinName(user,WalletType.FIAT,"INR");;
     	    				Integer bal=wallet.getBalance()-b.getGrossAmount();
     	    				System.out.println(bal);
     	    				wallet.setBalance(bal);
     	    				wallet.setShadowbalance(bal);
     	    				walletRepository.save(wallet);
     	    				
     	    				//For CRYPTO
     	    				Users user1=b.getUsersorder();
     	    				System.out.println(user1.getUserId());
     	    				String s1=b.getCoinname();
     	    				System.out.println(">>>>>>>>>>"+s1);
     Wallet wallet1=walletRepository.findByUsersAndCoinTypeAndCoinName(user1, WalletType.CRYPTO,b.getCoinname());
     	    				System.out.println(">>>>>>>>>>"+wallet1.getId()+wallet1.getCoinName());
     	    				wallet1.setBalance(b.getCoinQuantity());
     	    				wallet1.setShadowbalance(b.getCoinQuantity());
     	    				walletRepository.save(wallet1);

    		 }
    	 }
     }
     }
     
    
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/* if(sellerlist.size()!=0) 2{
    	 
     for(UserOrder b: buyerlist) 3{
    	 for(UserOrder s:sellerlist ) 4{
    		 
    		 if(seller grater than admin) 5{
    				 if(buyer greater than admin) 6{
    					 
    					 
    					 
    					 
    					 
    				 6}
    				 5}else 12{
    		
    			seller less than admin
    					 
    					 
    					 
    					 
    					 
    					 
    					 
    					 
    					 
    					 
    					 
    					 
    					 
    					 if(b.getCoinname().equals(s.getCoinname())) 7{
    				if(b.getCoinQuantity()==s.getCoinQuantity()) 8{
    					UserTransaction userTransaction=new UserTransaction();
    					userTransaction.setCoinName(b.getCoinname());
    					userTransaction.setCoinType(b.getCoinType());
    					userTransaction.setDateCreated(date);
    					userTransaction.setDescription("Approved");
    					currency=currencyRepository.findByCoinName(b.getCoinname());
    					userTransaction.setFees(currency.getFees());
    					Integer netAmount1=b.getPrice()*b.getCoinQuantity();
    					
    				    Integer grossAmount1=(netAmount1+((netAmount1*currency.getFees())/100));
    					userTransaction.setGrossAmount(grossAmount1);
    					userTransaction.setBuyer_id(b.getOrderid());
    					userTransaction.setNetAmount(netAmount1);
    					userTransaction.setSeller_id(s.getOrderid());
    					userTransaction.setTransactionstatus(OrderStatus.APPROVE);
    					
    					
    					
    				8}else if(b.getCoinQuantity()>s.getCoinQuantity()) 9{
    				
    				9}
    				else 10{
    					
    				10}
    					 	
    			7}
    		12}
    				 
    		 4}
    	 3}
    2 }else
     11{
    	 //Admin se karwao
    	 
     11}*/
     
	
	
	
	
	
	
	




}
	


