package io.oodles.springboot1.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.oodles.springboot1.enums.OrderStatus;
import io.oodles.springboot1.enums.OrderType;
import io.oodles.springboot1.model.BuyOrder;
import io.oodles.springboot1.model.Currency;
import io.oodles.springboot1.model.UserOrder;
import io.oodles.springboot1.model.Users;
import io.oodles.springboot1.repository.CurrencyRepository;
import io.oodles.springboot1.repository.OrderRepository;
import io.oodles.springboot1.repository.UsersRepository;
@Service
public class OrderService {
	Users user;
	Date date=new Date();
	Currency currency=new Currency();
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
		List<UserOrder> list1=new ArrayList<UserOrder>();
		list1.add(orderRepository.findByOrdertype(OrderType.BUY));
		System.out.println(list1.size());

	}
	

}
