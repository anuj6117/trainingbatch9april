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
import io.oodles.springboot1.model.BuyOrder;
import io.oodles.springboot1.model.Currency;
import io.oodles.springboot1.model.UserOrder;
import io.oodles.springboot1.model.UserTransaction;
import io.oodles.springboot1.model.Users;
import io.oodles.springboot1.repository.CurrencyRepository;
import io.oodles.springboot1.repository.OrderRepository;
import io.oodles.springboot1.repository.UsersRepository;
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
	 System.out.println("?????????????"+buyerlist.size());
	 Collections.sort(buyerlist,new Sortbyprice());
	 Collections.reverse(buyerlist);
	 for(UserOrder u:buyerlist) {
	 System.out.println(u.getPrice());}
	 
	 List<UserOrder> sellerlist=orderRepository.findByOrdertype();
	//System.out.println(sellerlist.size());	
     Collections.sort(sellerlist,new Sortbyprice());
     for(UserOrder s:sellerlist) {
    	 System.out.println(s.getPrice());}
     
     for(UserOrder b: buyerlist) {
    	 for(UserOrder s:sellerlist ) {
    		
    			if(b.getCoinname().equals(s.getCoinname())) {
    				if(b.getCoinQuantity()==s.getCoinQuantity()) {
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
    					
    					
    					
    				}else if(b.getCoinQuantity()>s.getCoinQuantity()) {
    				
    				}
    				else {
    					
    				}
    					
    			}
    		}
    	 }
     }
	}
	


