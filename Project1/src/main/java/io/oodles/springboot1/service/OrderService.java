package io.oodles.springboot1.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.oodles.springboot1.comparator.Sortbyprice;
import io.oodles.springboot1.enums.OrderStatus;
import io.oodles.springboot1.enums.OrderType;
import io.oodles.springboot1.enums.Status;
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
	UserOrder userOrder;
	Currency currency;
	Wallet wallet;
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
            Users user=usersRepository.findByUserId(buyOrder.getUserid());
            Wallet wallet=walletRepository.findByUsersAndCoinTypeAndCoinName(user, WalletType.FIAT, "INR");
            if(user.getStatus().equals(Status.ACTIVE))
            {
		Date date=new Date();
		currency=currencyRepository.findByCoinName(buyOrder.getCoinname());
		
		
	
		
		
		Double netAmount=buyOrder.getPrice()*buyOrder.getCoinQuantity();
		
	    Double grossAmount=(netAmount+((netAmount*currency.getFees())/100));
	    Double sb=wallet.getBalance()-grossAmount;
	    
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
	    //System.out.println("????????????????");
	    listBuyOrder.add(userOrder);
	    wallet.setShadowbalance(sb);
	    walletRepository.save(wallet);
	    //System.out.println("<<<<<<<<<<<<<<<"+listBuyOrder);
	    return "Success";}
            else {
            	return "User Not Active";
            }
		
		
		
	}
	public String sell(BuyOrder buyOrder) {
		// TODO Auto-generated method stub
		Users user=usersRepository.findByUserId(buyOrder.getUserid());
		if(user.getStatus().equals(Status.ACTIVE)) {
        currency=currencyRepository.findByCoinName(buyOrder.getCoinname());
        Date date=new Date();
		
		
		
		
		Double netAmount=buyOrder.getPrice()*buyOrder.getCoinQuantity();
		
	    Double grossAmount=(netAmount+((netAmount*currency.getFees())/100));
	    
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
	   // System.out.println(">>>>>>>>>>>>>>>>>>>>>>>");
	   // System.out.println("<<<<<<<<<<<<<<<<"+listSellOrder);
	    return "Success";}
		else {
			return "User Not Active";
		}
		
	}
	public Optional<UserOrder> get(int id) {
		// TODO Auto-generated method stub
	     System.out.println("''''''''''''''");
	   
	   
		return orderRepository.findById(id);

	}
	
	
	public String transaction() {
		Date date=new Date();
	 List<UserOrder> buyerlist=orderRepository.findByOrdertype1();
	 //System.out.println("?????????????"+buyerlist.size());
	 Collections.sort(buyerlist,new Sortbyprice());
	 Collections.reverse(buyerlist);
	 
	 
	 List<UserOrder> sellerlist=orderRepository.findByOrdertype();
		
     Collections.sort(sellerlist,new Sortbyprice());
   
     
     if(sellerlist.size()!=0) {
    	 //System.out.println(">>>>>>>>>>>>>>>>>>>");
    	 for(UserOrder b:buyerlist) {
    		// System.out.println("------------");
    	for(UserOrder s:sellerlist) {	 
    	 
    	 
    	 currency=currencyRepository.findByCoinNameAndCoinType(b.getCoinname(),b.getCoinType());
    	 
    	 if(b.getPrice()>=s.getPrice() && s.getPrice()<currency.getPrice()) {
    		 System.out.println("111111111111");
    		 if(b.getCoinQuantity()>=s.getCoinQuantity() && s.getCoinQuantity()>0) {
    			 if(b.getCoinname().equals(currency.getCoinName())&& b.getCoinType().equals(currency.getCoinType())) {
        			 UserTransaction userTransaction=new UserTransaction();
         	    	userTransaction.setBuyer_id(b.getOrderid());
         	    	userTransaction.setSeller_id(s.getOrderid());
         	    	     		userTransaction.setCoinName(b.getCoinname());
         	    	     		userTransaction.setCoinType(b.getCoinType());
         	    	     		userTransaction.setDateCreated(date);
         	    	     		userTransaction.setDescription("Approved");
         	    	     		userTransaction.setFees(b.getFee());
         	    	     		Double netAmount1=b.getPrice()*b.getCoinQuantity();
         	    				
         	    			    Double grossAmount1=(netAmount1+((netAmount1*b.getFee())/100));
         	    	     		userTransaction.setGrossAmount(grossAmount1);
         	    	     		userTransaction.setExchangeRate(s.getCoinQuantity()*b.getPrice());
         	    	     		userTransaction.setNetAmount(netAmount1);
         	    	     		userTransaction.setTransactionstatus(OrderStatus.APPROVE);
         	    	     		transactionRepository.save(userTransaction);
         	    	     		
         	    	     		
         	    	     		
         	    	     		
         	    				
         	    				
         	    				//For FIAT
         	    				Users user=b.getUsersorder();
         	    				//System.out.println(">>>>>>>>>>>>>"+user.getUserId());
         	    				wallet=walletRepository.findByUsersAndCoinTypeAndCoinName(user,WalletType.FIAT,"INR");;
         	    				Double bal=wallet.getBalance()-b.getGrossAmount();
         	    				System.out.println(bal);
         	    				wallet.setBalance(bal);
         	    				wallet.setShadowbalance(bal);
         	    				walletRepository.save(wallet);
         	    				
         	    				//For CRYPTO
         	    				
         	    				
         	    				//String s1=b.getCoinname();
         	    				//System.out.println(">>>>>>>>>>"+s1);
          wallet=walletRepository.findByUsersAndCoinTypeAndCoinName(user, WalletType.CRYPTO,b.getCoinname());
         	    				//System.out.println(">>>>>>>>>>"+wallet1.getId()+wallet1.getCoinName());
          wallet.setShadowbalance(wallet.getBalance()+s.getCoinQuantity());
         	    				wallet.setBalance(wallet.getBalance()+s.getCoinQuantity());
         	    				
         	    				walletRepository.save(wallet);
         	    				
         	    				//For FIAT
         	    				Users user1=s.getUsersorder();
         	    				//System.out.println(">>>>>>>>>>>>>"+user.getUserId());
         	    				wallet=walletRepository.findByUsersAndCoinTypeAndCoinName(user1,WalletType.FIAT,"INR");
         	    				Double bal1=wallet.getBalance()+s.getGrossAmount();
         	    				System.out.println(bal1);
         	    				wallet.setBalance(bal1);
         	    				wallet.setShadowbalance(bal1);
         	    				walletRepository.save(wallet);
         	    				
         	    				//For CRYPTO
         	    				//Users user3=s.getUsersorder();
         	    				//System.out.println(user1.getUserId());
         	    				//String s2=b.getCoinname()
         	    				//System.out.println(">>>>>>>>>>"+s1);
         wallet=walletRepository.findByUsersAndCoinTypeAndCoinName(user1, WalletType.CRYPTO,s.getCoinname());
         	    				//System.out.println(">>>>>>>>>>"+wallet1.getId()+wallet1.getCoinName());
         wallet.setShadowbalance(wallet.getBalance()-s.getCoinQuantity());
         	    				wallet.setBalance(wallet.getBalance()-s.getCoinQuantity());
         	    				
         	    				walletRepository.save(wallet);
         	    				
         	    				Double inrconversion=currency.getINRconversion()+((b.getPrice()*s.getCoinQuantity())-s.getPrice()*s.getCoinQuantity());
         	    				//System.out.println(inrconversion);
         	    	     		currency.setINRconversion(inrconversion);
         	    				Double initial=currency.getInitialSupply()-b.getCoinQuantity();
         	    				//System.out.println(initial);
         	    				currency.setInitialSupply(initial);
         	    				Double profit=b.getGrossAmount()-b.getNetAmount();
         	    				//System.out.println(profit);
         	    				currency.setProfit(profit);
         	    				currencyRepository.save(currency);
         	    				if(b.getCoinQuantity() == s.getCoinQuantity()) {
        							b.setOrderStatus(OrderStatus.APPROVE);
        						}
        						  else {
        							  b.setCoinQuantity(b.getCoinQuantity() - s.getCoinQuantity());
        							  b.setOrderStatus(OrderStatus.PENDING);
        						}
         	    				
         	    				Double sellercoin=s.getCoinQuantity()-b.getCoinQuantity();
         	    	     		Double currencycoin=currency.getInitialSupply()-b.getCoinQuantity();
         	    	     		//System.out.println("//////////"+currencycoin);
         	    	     		Double buyercoin=currency.getInitialSupply()-currencycoin;
         	    	     		//System.out.println(">>>>>>>>>>"+ buyercoin+b.getCoinQuantity());
         	    	     		
         	    				s.setCoinQuantity(0.0);
         	    				s.setOrderStatus(OrderStatus.APPROVE);
         	    				orderRepository.save(b);
         	    				orderRepository.save(s);
         	    				return "Success";

        		 }
    			 
    		 }else {if(b.getCoinQuantity()<s.getCoinQuantity() && b.getCoinQuantity()>0.0) { 
    			 if(b.getCoinname().equals(currency.getCoinName())&& b.getCoinType().equals(currency.getCoinType())) {
    			 UserTransaction userTransaction=new UserTransaction();
      	    	userTransaction.setBuyer_id(b.getOrderid());
      	    	userTransaction.setSeller_id(s.getOrderid());
      	    	     		userTransaction.setCoinName(b.getCoinname());
      	    	     		userTransaction.setCoinType(b.getCoinType());
      	    	     		userTransaction.setDateCreated(date);
      	    	     		userTransaction.setDescription("Approved");
      	    	     		userTransaction.setFees(b.getFee());
      	    	     		Double netAmount1=b.getPrice()*b.getCoinQuantity();
      	    				userTransaction.setExchangeRate(b.getCoinQuantity()*b.getPrice());
      	    			    Double grossAmount1=(netAmount1+((netAmount1*b.getFee())/100));
      	    	     		userTransaction.setGrossAmount(grossAmount1);
      	    	     		userTransaction.setNetAmount(netAmount1);
      	    	     		userTransaction.setTransactionstatus(OrderStatus.APPROVE);
      	    	     		transactionRepository.save(userTransaction);
      	    	     		
      	    	     		
      	    	     		
      	    	     		
      	    				
      	    				
      	    				//For FIAT
      	    				Users user=b.getUsersorder();
      	    				//System.out.println(">>>>>>>>>>>>>"+user.getUserId());
      	    				wallet=walletRepository.findByUsersAndCoinTypeAndCoinName(user,WalletType.FIAT,"INR");;
      	    				Double bal=wallet.getBalance()-b.getGrossAmount();
      	    				System.out.println(bal);
      	    				wallet.setBalance(bal);
      	    				wallet.setShadowbalance(bal);
      	    				walletRepository.save(wallet);
      	    				
      	    				//For CRYPTO
      	    				
      	    				
      	    				String s1=b.getCoinname();
      	    				//System.out.println(">>>>>>>>>>"+s1);
       wallet=walletRepository.findByUsersAndCoinTypeAndCoinName(user, WalletType.CRYPTO,b.getCoinname());
      	    				//System.out.println(">>>>>>>>>>"+wallet1.getId()+wallet1.getCoinName());
       wallet.setShadowbalance(wallet.getBalance()+b.getCoinQuantity());
      	    				wallet.setBalance(wallet.getBalance()+b.getCoinQuantity());
      	    				
      	    				walletRepository.save(wallet);
      	    				
      	    				//For FIAT
      	    				Users user1=s.getUsersorder();
      	    				//System.out.println(">>>>>>>>>>>>>"+user.getUserId());
      	    				wallet=walletRepository.findByUsersAndCoinTypeAndCoinName(user1,WalletType.FIAT,"INR");;
      	    				Double bal1=wallet.getBalance()+s.getGrossAmount();
      	    				System.out.println(bal1);
      	    				wallet.setBalance(bal1);
      	    				wallet.setShadowbalance(bal1);
      	    				walletRepository.save(wallet);
      	    				
      	    				//For CRYPTO
      	    				//Users user3=s.getUsersorder();
      	    				//System.out.println(user1.getUserId());
      	    				//String s2=b.getCoinname()
      	    				//System.out.println(">>>>>>>>>>"+s1);
      wallet=walletRepository.findByUsersAndCoinTypeAndCoinName(user1, WalletType.CRYPTO,s.getCoinname());
      	    				//System.out.println(">>>>>>>>>>"+wallet1.getId()+wallet1.getCoinName());
      wallet.setShadowbalance(wallet.getBalance()-b.getCoinQuantity());
      	    				wallet.setBalance(wallet.getBalance()-b.getCoinQuantity());
      	    				
      	    				walletRepository.save(wallet);
      	    				
      	    				Double inrconversion=currency.getINRconversion()+((b.getPrice()*b.getCoinQuantity())-s.getPrice()*b.getCoinQuantity());
      	    				//System.out.println(inrconversion);
      	    	     		currency.setINRconversion(inrconversion);
      	    				Double initial=currency.getInitialSupply()-b.getCoinQuantity();
      	    				//System.out.println(initial);
      	    				currency.setInitialSupply(initial);
      	    				Double profit=b.getGrossAmount()-b.getNetAmount();
      	    				//System.out.println(profit);
      	    				currency.setProfit(profit);
      	    				currencyRepository.save(currency);
      	    				
      	    				Double sellercoin=s.getCoinQuantity()-b.getCoinQuantity();
      	    	     		Double currencycoin=currency.getInitialSupply()-b.getCoinQuantity();
      	    	     		//System.out.println("//////////"+currencycoin);
      	    	     		Double buyercoin=currency.getInitialSupply()-currencycoin;
      	    	     		//System.out.println(">>>>>>>>>>"+ buyercoin+b.getCoinQuantity());
      	    	     		b.setCoinQuantity(0.0);
      	    				b.setOrderStatus(OrderStatus.APPROVE);
      	    				s.setCoinQuantity(sellercoin);
      	    				s.setOrderStatus(OrderStatus.PENDING);
      	    				orderRepository.save(b);
      	    				orderRepository.save(s);
      	    				return "Success";

     		 }
    			 
    			 
    		 }}
    		 
    		 
    	 }else {if(currency.getPrice()<s.getPrice() && currency.getPrice()<=b.getPrice()) {
    		 if(currency.getInitialSupply()>=b.getCoinQuantity() && b.getCoinQuantity()>0) {
         
        	 System.out.println("2222222222222");
        	 if(b.getCoinname().equals(currency.getCoinName())&& b.getCoinType().equals(currency.getCoinType())) {
    			 UserTransaction userTransaction=new UserTransaction();
     	    	userTransaction.setBuyer_id(b.getOrderid());
     	    	userTransaction.setSeller_id(s.getOrderid());
     	    	     		userTransaction.setCoinName(b.getCoinname());
     	    	     		userTransaction.setCoinType(b.getCoinType());
     	    	     		userTransaction.setDateCreated(date);
     	    	     		userTransaction.setDescription("Approved");
     	    	     		userTransaction.setFees(b.getFee());
     	    	     		Double netAmount1=b.getPrice()*b.getCoinQuantity();
     	    				
     	    			    Double grossAmount1=(netAmount1+((netAmount1*b.getFee())/100));
     	    	     		userTransaction.setGrossAmount(grossAmount1);
     	    	     		userTransaction.setNetAmount(netAmount1);
     	    	     		userTransaction.setTransactionstatus(OrderStatus.APPROVE);
     	    	     		userTransaction.setExchangeRate(b.getCoinQuantity()*b.getPrice());
     	    	     		transactionRepository.save(userTransaction);
     	    	     		
     	    	     		
     	    	     		
     	    	     		
     	    				
     	    				
     	    				//For FIAT
     	    				Users user=b.getUsersorder();
     	    				//System.out.println(">>>>>>>>>>>>>"+user.getUserId());
     	    				Wallet wallet=walletRepository.findByUsersAndCoinTypeAndCoinName(user,WalletType.FIAT,"INR");;
     	    				Double bal=wallet.getBalance()-b.getGrossAmount();
     	    				//System.out.println(bal);
     	    				wallet.setBalance(bal);
     	    				wallet.setShadowbalance(bal);
     	    				walletRepository.save(wallet);
     	    				
     	    				//For CRYPTO
     	    				//Users user1=b.getUsersorder();
     	    				//System.out.println(user1.getUserId());
     	    				//String s1=b.getCoinname();
     	    				//System.out.println(">>>>>>>>>>"+s1);
      wallet=walletRepository.findByUsersAndCoinTypeAndCoinName(user, WalletType.CRYPTO,b.getCoinname());
     	    				//System.out.println(">>>>>>>>>>"+wallet1.getId()+wallet1.getCoinName());
      wallet.setShadowbalance(wallet.getBalance()+b.getCoinQuantity());	    				
      wallet.setBalance(wallet.getBalance()+b.getCoinQuantity());
     	    				
     	    				walletRepository.save(wallet);
     	    				
     	    				
     	    				
     	    				Double inrconversion=currency.getINRconversion()+((b.getPrice()*b.getCoinQuantity())-currency.getPrice()*b.getCoinQuantity());
     	    				//System.out.println(inrconversion);
     	    	     		currency.setINRconversion(inrconversion);
     	    				Double initial=currency.getInitialSupply()-b.getCoinQuantity();
     	    				//System.out.println(initial);
     	    				currency.setInitialSupply(initial);
     	    				Double profit=b.getGrossAmount()-b.getNetAmount();
     	    				//System.out.println(profit);
     	    				currency.setProfit(profit);
     	    				currencyRepository.save(currency);
     	    				
     	    				Double sellercoin=s.getCoinQuantity()-b.getCoinQuantity();
     	    	     		Double currencycoin=currency.getInitialSupply()-b.getCoinQuantity();
     	    	     		//System.out.println("//////////"+currencycoin);
     	    	     		Double buyercoin=currency.getInitialSupply()-currencycoin;
     	    	     		//System.out.println(">>>>>>>>>>"+ buyercoin+b.getCoinQuantity());
     	    	     		b.setCoinQuantity(0.0);
     	    				b.setOrderStatus(OrderStatus.APPROVE);
     	    				
     	    				orderRepository.save(b);
     	    				return "Success";
     	    				

    		 }}else if(currency.getInitialSupply()<b.getCoinQuantity() && currency.getInitialSupply()>0) {
    			 if(b.getCoinname().equals(currency.getCoinName())&& b.getCoinType().equals(currency.getCoinType())) {
        			 UserTransaction userTransaction=new UserTransaction();
         	    	userTransaction.setBuyer_id(b.getOrderid());
         	    	userTransaction.setSeller_id(s.getOrderid());
         	    	     		userTransaction.setCoinName(b.getCoinname());
         	    	     		userTransaction.setCoinType(b.getCoinType());
         	    	     		userTransaction.setDateCreated(date);
         	    	     		userTransaction.setDescription("Approved");
         	    	     		userTransaction.setFees(b.getFee());
         	    	     		Double netAmount1=b.getPrice()*b.getCoinQuantity();
         	    				
         	    			    Double grossAmount1=(netAmount1+((netAmount1*b.getFee())/100));
         	    	     		userTransaction.setGrossAmount(grossAmount1);
         	    	     		userTransaction.setNetAmount(netAmount1);
         	    	     		userTransaction.setTransactionstatus(OrderStatus.APPROVE);
         	    	     		userTransaction.setExchangeRate(currency.getInitialSupply()*b.getPrice());
         	    	     		transactionRepository.save(userTransaction);
         	    	     		
         	    	     		
         	    	     		
         	    	     		
         	    				
         	    				
         	    				//For FIAT
         	    				Users user=b.getUsersorder();
         	    				//System.out.println(">>>>>>>>>>>>>"+user.getUserId());
         	    				Wallet wallet=walletRepository.findByUsersAndCoinTypeAndCoinName(user,WalletType.FIAT,"INR");;
         	    				Double bal=wallet.getBalance()-b.getGrossAmount();
         	    				//System.out.println(bal);
         	    				wallet.setBalance(bal);
         	    				wallet.setShadowbalance(bal);
         	    				walletRepository.save(wallet);
         	    				
         	    				//For CRYPTO
         	    				//Users user1=b.getUsersorder();
         	    				//System.out.println(user1.getUserId());
         	    				//String s1=b.getCoinname();
         	    				//System.out.println(">>>>>>>>>>"+s1);
          wallet=walletRepository.findByUsersAndCoinTypeAndCoinName(user, WalletType.CRYPTO,b.getCoinname());
         	    				//System.out.println(">>>>>>>>>>"+wallet1.getId()+wallet1.getCoinName());
          wallet.setShadowbalance(wallet.getBalance()+b.getCoinQuantity());	    				
          wallet.setBalance(wallet.getBalance()+b.getCoinQuantity());
         	    				
         	    				walletRepository.save(wallet);
         	    				
         	    				
         	    				
         	    				Double inrconversion=currency.getINRconversion()+((b.getPrice()*currency.getInitialSupply())-currency.getPrice()*currency.getInitialSupply());
         	    				//System.out.println(inrconversion);
         	    	     		currency.setINRconversion(inrconversion);
         	    				Double initial=currency.getInitialSupply()-b.getCoinQuantity();
         	    				//System.out.println(initial);
         	    				currency.setInitialSupply(0.0);
         	    				Double profit=b.getGrossAmount()-b.getNetAmount();
         	    				//System.out.println(profit);
         	    				currency.setProfit(profit);
         	    				currencyRepository.save(currency);
         	    				
         	    				Double sellercoin=s.getCoinQuantity()-b.getCoinQuantity();
         	    	     		Double currencycoin=currency.getInitialSupply()-b.getCoinQuantity();
         	    	     		//System.out.println("//////////"+currencycoin);
         	    	     		Double buyercoin=currency.getInitialSupply()-currencycoin;
         	    	     		//System.out.println(">>>>>>>>>>"+ buyercoin+b.getCoinQuantity());
         	    	     		b.setCoinQuantity(b.getCoinQuantity()-currency.getInitialSupply());
         	    				b.setOrderStatus(OrderStatus.PENDING);
         	    				
         	    				orderRepository.save(b);
         	    				return "success";
    		 }
        	 
    			 
    		 }

        		 }
    			 
    	 }}}} 
    	 
    	 
     else {
    	 //admin transaction
    	 System.out.println("5555555555555");
    	 
    	 
    	 for(UserOrder b:buyerlist) {
    		 System.out.println(b.getOrderid());
    		 
    		 currency=currencyRepository.findByCoinNameAndCoinType(b.getCoinname(),b.getCoinType());
    		 System.out.println(currency.getCoinId());
    		 if(currency.getInitialSupply()>=b.getCoinQuantity() && b.getCoinQuantity()>0.0) {
    		 if(b.getCoinname().equals(currency.getCoinName())&& b.getCoinType().equals(currency.getCoinType())) {
    			 UserTransaction userTransaction=new UserTransaction();
    			 System.out.println(b.getOrderid());
     	    	userTransaction.setBuyer_id(b.getOrderid());
     	    	userTransaction.setSeller_id(currency.getCoinId());
     	    	     		userTransaction.setCoinName(b.getCoinname());
     	    	     		userTransaction.setCoinType(b.getCoinType());
     	    	     		userTransaction.setDateCreated(date);
     	    	     		userTransaction.setDescription("Approved");
     	    	     		userTransaction.setFees(b.getFee());
     	    	     		Double netAmount1=b.getPrice()*b.getCoinQuantity();
     	    				
     	    			    Double grossAmount1=(netAmount1+((netAmount1*b.getFee())/100));
     	    	     		userTransaction.setGrossAmount(grossAmount1);
     	    	     		userTransaction.setNetAmount(netAmount1);
     	    	     		userTransaction.setTransactionstatus(OrderStatus.APPROVE);
     	    	     		userTransaction.setExchangeRate(b.getCoinQuantity()*b.getPrice());
     	    	     		transactionRepository.save(userTransaction);
     	    	     		
     	    	     		
     	    	     		
     	    	     		
     	    	     		
     	    				
     	    				
     	    				//For FIAT
     	    				Users user=b.getUsersorder();
     	    				System.out.println(">>>>>>>>>>>>>"+user.getUserId());
          	    				Wallet wallet=walletRepository.findByUsersAndCoinTypeAndCoinName(user,WalletType.FIAT,"INR");;
     	    				Double bal=wallet.getBalance()-b.getGrossAmount();
     	    				System.out.println(bal);
     	    				wallet.setBalance(bal);
     	    				wallet.setShadowbalance(bal);
     	    				walletRepository.save(wallet);
     	    				
     	    				//For CRYPTO
     	    				//Users user1=b.getUsersorder();
     	    				//System.out.println(user1.getUserId());
     	    				String s1=b.getCoinname();
     	    				System.out.println(">>>>>>>>>>"+s1);
     Wallet wallet1=walletRepository.findByUsersAndCoinTypeAndCoinName(user, WalletType.CRYPTO,b.getCoinname());
     	    				System.out.println(">>>>>>>>>>"+wallet1.getId()+wallet1.getCoinName());
     	    				wallet1.setShadowbalance(wallet1.getShadowbalance()+b.getCoinQuantity());
     	    				wallet1.setBalance(wallet1.getBalance()+b.getCoinQuantity());
     	    				
     	    				walletRepository.save(wallet1);
     	    				
     	    				Double inrconversion=currency.getINRconversion()+((b.getPrice()*b.getCoinQuantity())-currency.getPrice()*b.getCoinQuantity());
     	    				//System.out.println(inrconversion);
     	    	     		currency.setINRconversion(inrconversion);
     	    				Double initial=currency.getInitialSupply()-b.getCoinQuantity();
     	    				//System.out.println(initial);
     	    				currency.setInitialSupply(initial);
     	    				Double profit=b.getGrossAmount()-b.getNetAmount();
     	    				//System.out.println(profit);
     	    				currency.setProfit(profit);
     	    				currencyRepository.save(currency);
     	    				
     	    				
     	    				Double currencycoin=currency.getInitialSupply()-b.getCoinQuantity();
     	    	     		System.out.println("//////////"+currencycoin);
     	    	     		Double buyercoin=currency.getInitialSupply()-currencycoin;
     	    	     		System.out.println(">>>>>>>>>>"+ buyercoin+b.getCoinQuantity());
     	    	     		b.setCoinQuantity(0.0);
     	    				b.setOrderStatus(OrderStatus.APPROVE);
     	    				orderRepository.save(b);
     	    				return "Success";

    		 }
    	 }else if(currency.getInitialSupply()<b.getCoinQuantity() && currency.getInitialSupply()>0.0) {
    		 if(b.getCoinname().equals(currency.getCoinName())&& b.getCoinType().equals(currency.getCoinType())) {
    			 UserTransaction userTransaction=new UserTransaction();
    			 System.out.println(b.getOrderid());
     	    	userTransaction.setBuyer_id(b.getOrderid());
     	    	userTransaction.setSeller_id(currency.getCoinId());
     	    	     		userTransaction.setCoinName(b.getCoinname());
     	    	     		userTransaction.setCoinType(b.getCoinType());
     	    	     		userTransaction.setDateCreated(date);
     	    	     		userTransaction.setDescription("Approved");
     	    	     		userTransaction.setFees(b.getFee());
     	    	     		Double netAmount1=b.getPrice()*b.getCoinQuantity();
     	    				
     	    			    Double grossAmount1=(netAmount1+((netAmount1*b.getFee())/100));
     	    	     		userTransaction.setGrossAmount(grossAmount1);
     	    	     		userTransaction.setNetAmount(netAmount1);
     	    	     		userTransaction.setExchangeRate(currency.getInitialSupply()*b.getPrice());
     	    	     		userTransaction.setTransactionstatus(OrderStatus.APPROVE);
     	    	     		transactionRepository.save(userTransaction);
     	    	     		
     	    	     		
     	    	     		
     	    	     		
     	    	     		
     	    				
     	    				
     	    				//For FIAT
     	    				Users user=b.getUsersorder();
     	    				System.out.println(">>>>>>>>>>>>>"+user.getUserId());
          	    				Wallet wallet=walletRepository.findByUsersAndCoinTypeAndCoinName(user,WalletType.FIAT,"INR");;
     	    				Double bal=wallet.getBalance()-b.getGrossAmount();
     	    				System.out.println(bal);
     	    				wallet.setBalance(bal);
     	    				wallet.setShadowbalance(bal);
     	    				walletRepository.save(wallet);
     	    				
     	    				//For CRYPTO
     	    				//Users user1=b.getUsersorder();
     	    				//System.out.println(user1.getUserId());
     	    				//String s1=b.getCoinname();
     	    				//System.out.println(">>>>>>>>>>"+s1);
     Wallet wallet1=walletRepository.findByUsersAndCoinTypeAndCoinName(user, WalletType.CRYPTO,b.getCoinname());
     	    				//System.out.println(">>>>>>>>>>"+wallet1.getId()+wallet1.getCoinName());
     	    				wallet1.setShadowbalance(wallet.getShadowbalance()+currency.getInitialSupply());
     	    				wallet1.setBalance(wallet.getBalance()+currency.getInitialSupply());
     	    				
     	    				walletRepository.save(wallet1);
     	    				
     	    				Double inrconversion=currency.getINRconversion()+((b.getPrice()*currency.getInitialSupply())-currency.getPrice()*currency.getInitialSupply());
     	    				//System.out.println(inrconversion);
     	    	     		currency.setINRconversion(inrconversion);
     	    				Double initial=currency.getInitialSupply()-b.getCoinQuantity();
     	    				//System.out.println(initial);
     	    				currency.setInitialSupply(0.0);
     	    				Double profit=b.getGrossAmount()-b.getNetAmount();
     	    				//System.out.println(profit);
     	    				currency.setProfit(profit);
     	    				currencyRepository.save(currency);
     	    				
     	    				
     	    				Double currencycoin=currency.getInitialSupply()-b.getCoinQuantity();
     	    	     		System.out.println("//////////"+currencycoin);
     	    	     		Double buyercoin=currency.getInitialSupply()-currencycoin;
     	    	     		System.out.println(">>>>>>>>>>"+ buyercoin+b.getCoinQuantity());
     	    	     		b.setCoinQuantity(b.getCoinQuantity()-currency.getInitialSupply());
     	    				b.setOrderStatus(OrderStatus.PENDING);
     	    				orderRepository.save(b);
     	    				return "Success";

    		 }
    	 }
    		 }
     }
     return "Success";
     }
	public List<UserTransaction> show() {
		// TODO Auto-generated method stub
		return transactionRepository.findAll();
	}
	public List<UserOrder> showorder() {
		// TODO Auto-generated method stub
		return orderRepository.findAll();
	}
     }
	


