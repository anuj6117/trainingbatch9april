package com.traningproject1.service;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.traningproject1.demo.dto.BuySellOrderDTO;
import com.traningproject1.domain.CurrencyClass;
import com.traningproject1.domain.User;
import com.traningproject1.domain.UserOrder;
import com.traningproject1.domain.Wallet;
import com.traningproject1.enumsclass.CoinType;
import com.traningproject1.enumsclass.UserOrderStatus;
import com.traningproject1.enumsclass.UserOrderType;
import com.traningproject1.repository.CurrencyRepository;
import com.traningproject1.repository.UserOrderRepository;
import com.traningproject1.repository.UserRepository;
import com.traningproject1.repository.WalletRepository;

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
    	      List<CurrencyClass>coinNameValue=currencyRepository.findAll();
    	      Iterator<CurrencyClass>itr1=coinNameValue.iterator();
    	      while(itr1.hasNext())
    	    	{
    	    		CurrencyClass cc=itr1.next();
    	    		if(!(cc.getCoinName().equals(buysellorderdto.getCoinName())))
    	    		{
    	    			return "Invalid Coin Name";
    	    		}
    	    	}
    	      
    	      double tempamount=buysellorderdto.getCoinQuantity()*buysellorderdto.getPrice();
    	      System.out.println("Wallet user id"+wallet.getBalance());
    	      if(wallet.getShadowBalance()>=tempamount)
    	      {
    	       UserOrder userorder=new UserOrder();
               userorder.setCoinName(buysellorderdto.getCoinName());
    	       userorder.setCoinQuantity(buysellorderdto.getCoinQuantity());
    	       userorder.setPrice(buysellorderdto.getPrice());
    	       userorder.setDateCreated(new Date());
    	       userorder.setOrderType(UserOrderType.SELLER);
    	       userorder.setStatus(UserOrderStatus.PENDING);
               userorderRepository.save(userorder);
              return "Seller Order SuccessFully Submit";
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
    	
    	List<CurrencyClass>coinNameValue=currencyRepository.findAll();
    	Iterator<CurrencyClass>itr1=coinNameValue.iterator();
    	while(itr1.hasNext())
    	{
    		CurrencyClass cc=itr1.next();
    		if(!(cc.getCoinName().equals(buysellorderdto.getCoinName())))
    		{
    			return "Invalid Coin Name";
    		}
    	}
    
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
    	    userorder.setFees(currency.getFees());
    	    userorder.setUser(user);
            userorder.setDateCreated(new Date());
    	    userorderRepository.save(userorder);  
    	    return "Buyer Order SuccessFully Submit";
    	}
	       return "Insufficent Fund";
    }
}
