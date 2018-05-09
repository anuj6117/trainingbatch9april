package com.example.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.enums.WalletType;
import com.example.model.Currency;
import com.example.repository.CurrencyRepository;
import com.example.repository.UserRepository;
import com.example.service.CurrencyService;

@RestController
public class CurrencyController
{
@Autowired
private CurrencyService currencyservice;
@Autowired
private UserRepository userRepository;

@Autowired
private CurrencyRepository currencyrepository;

@RequestMapping(value="/addcurrency",method=RequestMethod.POST)	
 public String addCurrency(@RequestBody Currency currency)
 {
	
	
	Currency currency1=null;
	Currency currency2=null;
	System.out.println("rest");
	try
    {
	 
	  currency1=currencyrepository.findBySymbol(currency.getSymbol());
	  
      
	  currency2=currencyrepository.findByCoinName(currency.getCoinName());
	  System.out.println(".........currency1 "+currency1);
	  System.out.println(".........currency2 "+currency2); 
	  
	  if(currency1!=null || currency1!=null)
	  {
		  return "Coin Name or Symbol already exist";  
	  }
	  else
	  {
		  currency1.getCoinId();
		  currency2.getCoinId();  
		  return "";
	  }
	 
	  
    }
	catch(NullPointerException e)
	{
		String pattern="(?=.*[@#$%^&+=*])(?=\\S+$).{1,10}";
		
	
		//here null pointer maeans we can insert
		if(currency1==null && currency2==null)
		{
			System.out.println("numeber 1");
			
			if(currency.getCoinName().trim().length()!=0)
			{	
				System.out.println("numeber 2");
				if(currency.getSymbol().trim().length()!=0 && currency.getSymbol().matches(pattern))
				{
					System.out.println("numeber 3");
					//System.out.println("value of currency " + currency.getInitialSupply());
					try {
					if(currency.getPrice()>0 && currency.getInitialSupply()>0 && currency.getInitialSupply()!=null && currency.getPrice()!=null)
					{
						System.out.println("numeber 4");
		    	     return currencyservice.addCurrency(currency);	
					} 
					else
					 return "Initial supply or price can't be null ";
					}
					catch(NullPointerException r)
					{
						return "Initial supply or price can't be null";
					}
				}
				else
				   return "Symbol has  null value or incorrect value, enter correct one"; 
			}
			else
				return "Coin Name can't be null";
		}
		else
			return "coinName or symbol already exist";
	}
	//return "";
			
    		
    /*if( (currency2==null)&&  (currencyrepository.existsById(currency2.getCoinId())))
    {
    	if
    	return "Symbol can not be duplicate";
    }
    else
    {	
       if( (currency1==null)&&(currencyrepository.existsById(currency1.getCoinId())))
      {
	   return "coinName already exist";
      }
      else
	 
    }*/
 }
  

@GetMapping("/getallcurrency")
 public List<Currency> getallcurrency()
 {
	return currencyrepository.findAll();
	
 }


@RequestMapping(value="/updatecurrency",method=RequestMethod.POST)
 public String updatecurrency(@RequestBody Currency currency)
 {
	Currency currency1=currencyrepository.findByCoinId(currency.getCoinId());
	if(currency1!=null)
	{
		String coinName=currency.getCoinName().trim();
		if(coinName.length()!=0)
		{
			currency.setCoinType(WalletType.CRYPTO);
	     currencyrepository.save(currency);
	     return "Your currency has been updated successfully";
	    }else
	    	return "coin name is null";
	}else
		return "Coin Id do not exist";
 }

@GetMapping("/deletecurrency")
public String deletecurrency(@RequestParam("coinId") Integer coinid)
{
	 //currency=currencyrepository.findByCoinId(coinid);
	Currency currency=currencyrepository.findByCoinId(coinid);
	if(currency!=null)
	{
	currencyrepository.deleteById(coinid);
	return "Your currency has been deleted successfully";
	}
	else return "invalid coinId";
	 
}

@GetMapping("/getcurrencybyid")
public Currency getCurrenyById(@RequestParam("coinId") Integer coinid)
{
	Currency currency=currencyrepository.findByCoinId(coinid);
	if(currency!=null)
	{
		return currency;
	}
	else
		return null;
	
	
}

}
