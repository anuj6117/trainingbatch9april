package com.example.demo.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.CurrencyClass;
import com.example.demo.domain.User;
import com.example.demo.domain.UserOrder;
import com.example.demo.domain.Wallet;
import com.example.demo.dto.BuySellOrderDTO;
import com.example.demo.enumeration.CoinType;
import com.example.demo.enumeration.UserOrderStatus;
import com.example.demo.enumeration.UserOrderType;
import com.example.demo.repository.CurrencyRepository;
import com.example.demo.repository.UserOrderRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.WalletRepository;

@Service
public class UserOrderService {
	@Autowired
	UserRepository userRepository;
	@Autowired
	UserOrderRepository userorderRepository;
	@Autowired
	CurrencyRepository currencyRepository;
	@Autowired
	WalletRepository walletRepository;
    public String createSellOrder(BuySellOrderDTO buysellorderdto)
    {  
    	      User user=userRepository.findByuserId(buysellorderdto.getUserId());
    	      if(user==null)
    	      {
    	    	  return "Invalid User";
    	      }
    	      Wallet wallet=walletRepository.findByUserAndCoinTypeAndCoinName(user,CoinType.CRYPTO,buysellorderdto.getCoinName());
    	      
   	    		if(currencyRepository.findByCoinName(buysellorderdto.getCoinName()).getCoinName().equals(buysellorderdto.getCoinName()))
    	    		{
   	    			
    	    		   	      
    	      double tempamount=buysellorderdto.getCoinQuantity();
    	      System.out.println("Wallet user id"+wallet.getBalance());
    	      if(wallet.getShadowBalance()>=tempamount)
    	      {
    	       wallet.setShadowBalance(wallet.getShadowBalance()-buysellorderdto.getCoinQuantity());
    	       UserOrder userorder=new UserOrder();
               userorder.setCoinName(buysellorderdto.getCoinName());
    	       userorder.setCoinQuantity(buysellorderdto.getCoinQuantity());
    	       userorder.setPrice(buysellorderdto.getPrice());
    	       userorder.setGrossAmount(buysellorderdto.getCoinQuantity()*buysellorderdto.getPrice());
    	       userorder.setNetAmount(buysellorderdto.getCoinQuantity()*buysellorderdto.getPrice());
    	       userorder.setFees(0);
    	       userorder.setUser(user);
    	       userorder.setDateCreated(new Date());
    	       userorder.setOrderType(UserOrderType.SELLER);
    	       userorder.setStatus(UserOrderStatus.PENDING);
               userorderRepository.save(userorder);
               walletRepository.save(wallet);
               
              return "Seller Order SuccessFully Submit";
    	      }
    	     }
    	      return "Insufficient Fund in Cypto Wallet";
    }
    public String createBuyOrder(BuySellOrderDTO buysellorderdto)
    {
        
    	User user=userRepository.findByuserId(buysellorderdto.getUserId());
    	Wallet wallet=walletRepository.findByUserAndCoinType(user,CoinType.FIATE);
    	if(user.getUserId()==null)
    	{
    		return "Invalid User";
    	}
    	
    		boolean f=(currencyRepository.findByCoinName(buysellorderdto.getCoinName()).getCoinName().equals(buysellorderdto.getCoinName()));
    		if(f)
    		{
    			 double cal=buysellorderdto.getPrice()*buysellorderdto.getCoinQuantity();
    		       
    		       CurrencyClass currency=currencyRepository.findByCoinName(buysellorderdto.getCoinName());
    		      
    		       double fee=currency.getFees();
    		       
    		       double gross=(cal*fee)/100;
    		       double calc=gross+cal;
    		       
    		       double sbalance=wallet.getShadowBalance();
    		       
    		       if(sbalance>=calc)
    		       {
    		        wallet.setShadowBalance(sbalance-calc);
    		        UserOrder userorder=new UserOrder();
    		        userorder.setCoinName(buysellorderdto.getCoinName());
    	    	    userorder.setCoinQuantity(buysellorderdto.getCoinQuantity());
    	    	    userorder.setPrice(buysellorderdto.getPrice());
    	    	    userorder.setOrderType(UserOrderType.BUYER);
    	    	    userorder.setStatus(UserOrderStatus.PENDING);
    	    	    userorder.setGrossAmount(calc);
    	    	    userorder.setNetAmount(buysellorderdto.getCoinQuantity()*buysellorderdto.getPrice());
    	    	    userorder.setFees(currency.getFees());
    	    	    userorder.setUser(user);
    	            userorder.setDateCreated(new Date());
    	    	    userorderRepository.save(userorder); 
    	    	    walletRepository.save(wallet);
    	    	    
    	    	    return "Buyer Order SuccessFully Submit";
    	    	  
    		      }
    		      return "Insufficent Fund";
    		}
    
    
	       return "Invalid Coin Name";
    }
    public List<UserOrder> getAllOrder()
    {
    	return userorderRepository.findAll();
    }
	public UserOrder getOrderByUserId(Integer userId) {
		User user=userRepository.findByUserId(userId);
		try
		{
			if(user==null)
			{
				throw new Exception("Invalid User");
			}
		}
		catch(Exception e)
		{
		  	
		}
		return userorderRepository.findByuserorderId(user.getUserId());
	}
}
