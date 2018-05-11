package com.traningproject1.Controller;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.traningproject1.domain.CurrencyClass;
import com.traningproject1.repository.CurrencyRepository;
import com.traningproject1.service.CurrencyService;

@RestController
public class CurrencyController {

	@Autowired
	CurrencyService currencyService;
	@Autowired
	CurrencyRepository currencyRepository;
@RequestMapping(value="/addcurrency",method=RequestMethod.POST)
public String addCurrency(@RequestBody CurrencyClass currency)
{
	String coin=currency.getCoinName().trim();
	String sym=currency.getSymbol().trim();
	

   	List<CurrencyClass>getcurrency=currencyRepository.findAll();
	   Iterator<CurrencyClass>itr=getcurrency.iterator();
	   while(itr.hasNext())
	   {
		CurrencyClass c=itr.next();
		if((c.getCoinName().equalsIgnoreCase(currency.getCoinName()))||(c.getSymbol().equalsIgnoreCase(currency.getSymbol())))
		{
			return "CoinName Already Exist Or Coin Sysmbol Already exist";
		}
	   }
	   if(!(currency.getCoinName().matches("^([a-zA-Z]{2,}$)"))){
			return "Coin Name not valid";
		}
	   if(!(currency.getSymbol().matches("^([a-zA-Z0-9[#?!@$%^&*-<>~`)(_+=}[{]':;/]]{2,}$)"))){
			return "Symbol not valid";
		}
		if(currency.getCoinName().length()!=coin.length())
		{
			return "please remove the space from the coin name";
		}
		if(currency.getSymbol().length()!=sym.length())
		{
			return "please remove the space from the coin symbol";
		}
     if(sym.equals(""))
	 {
	  return "Please enter the coin Symbol";
     }
    else if(sym.equals(" "))
	{
		return "Please enter the coin Symbol";
     }
    else if(coin.equals(""))
    {
    	return "Enter the valid coin Name";
    }
    else if(coin.equals(" "))
    {
    	return "enter valid coin Name";
    }
     if(currency.getPrice()<0)
    {
    	return "Price Can't be negative";
    }
    else if(currency.getInitialSupply()<0)
    {
    	return "InitialSupply Can't be negative";
    }
    
    else if(currency.getFees()<0)
    {
    	return "Fees Can't be negative";
    }
     
	if(currencyService.addCurrency(currency)!=null)
	return"Your Coin Has been Added Successfully";
	//}
		return "fail";
	}
@RequestMapping(value="/getallcurrency",method=RequestMethod.GET)
public List<CurrencyClass> getAllCurrency()
{
	return currencyService.getAllCurrency();
	
}

@RequestMapping(value="/deletecurrency",method=RequestMethod.GET)
public String deleteCurrcency(@RequestParam("coinId") Integer coinId)
{
	CurrencyClass currency=currencyRepository.findByCoinId(coinId);
	
	if(currency==null)
	{
		return "Invalid Coin Id";
	}
  return currencyService.deleteCurrency(coinId);
}
@RequestMapping(value="/updatecurrency",method=RequestMethod.POST)
public  String updateCurrency(@RequestBody CurrencyClass currency)
{
	CurrencyClass currencyid=currencyRepository.findByCoinId(currency.getCoinId());
	if(currencyid==null)
	{
		return "invalid Coin Id";
	}
	//String coinname=currency.getCoinName();
	String sym=currency.getSymbol().trim();
	
	String coin=currency.getCoinName().trim();
	//String sym1=currency.getSymbol().trim();
	
	
	
	
	CurrencyClass cc = currencyRepository.findByCoinName(coin);
	if(cc.getCoinId() != currency.getCoinId())
	{
		return "already existing coin name";
	}
	
	cc = currencyRepository.findBySymbol(sym);
	if(cc.getCoinId() != currency.getCoinId())
	{
		return "already existing symbol";
	}
	if(currency.getCoinName().length()!=coin.length())
	{
		return "please remove the space from the coin name";
	}
	if(currency.getSymbol().length()!=sym.length())
	{
		return "please remove the space from the coin symbol";
	}
 if(sym.equals(""))
 {
  return "Please enter the coin Symbol";
 }
else if(sym.equals(" "))
{
	return "Please enter the coin Symbol";
 }
else if(coin.equals(""))
{
	return "put coin Name";
}
else if(coin.equals(" "))
{
	return "put Valid coin Name";
}
 if(currency.getPrice()<0)
{
	return "Price Can't be negative";
}
else if(currency.getInitialSupply()<0)
{
	return "InitialSupply Can't be negative";
}

else if(currency.getFees()<0)
{
	return "Fees Can't be negative";
}
  return  currencyService.updateCurrency(currency);	
}	
}
