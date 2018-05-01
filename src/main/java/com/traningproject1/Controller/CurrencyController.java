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
	boolean flag=false;
	boolean flag1=false;
	String coin=currency.getCoinName().trim();
	String sym=currency.getSymbol().trim();
	CurrencyClass currency1=currencyRepository.findByCoinName(coin);
	CurrencyClass currency2=currencyRepository.findBySymbol(sym);
	

	try
	{
		currency1.getCoinId();
		
		return "Coin name already exist";
	}
	catch(Exception e)
	{
		flag=true;
	}
	
	try
	{
		currency2.getSymbol();
		
		return "Symbol already exist";
	}
	catch(Exception e)
	{
		flag1=true;
	}
	if(flag&&flag1)
	{
     if(currency.getCoinName().equals(""))
    {
    	return "put coin Name";
    }
    if(currency.getCoinName().equals(" "))
    {
    	return "put Valid coin Name";
    }
    if(currency.getSymbol().equals(""))
    {
    	return "Please enter the coin Symbol";
    }
    if(currency.getSymbol().equals(" "))
    {
    	return "Please enter the coin Symbol";
    }
    if(currency.getInitialSupply()==null&&currency.getPrice()==null)
    {
    	return "Provide Initial Supply Or Provide Some Price ";
    }
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
	
	if(currencyService.addCurrency(currency)!=null)
	return"Your Coin Has been Added Successfully";
	}
		return "fail";
	}
@RequestMapping(value="/getallcurrency",method=RequestMethod.GET)
public List<CurrencyClass> getAllCurrency()
{
	return currencyService.getAllCurrency();
	
}
/*@RequestMapping(value="/getbyuserid",method=RequestMethod.GET)
public Optional<User> getUserById( Integer id)
{
 return serviceClass.getByUserId(id);	
}*/
@RequestMapping(value="/deletecurrency",method=RequestMethod.GET)
public String deleteCurrcency(@RequestParam("coinId") Integer coinId)
{
  return currencyService.deleteCurrency(coinId);
}
@RequestMapping(value="/updatecurrency",method=RequestMethod.POST)
public  String updateCurrency(@RequestBody CurrencyClass currency)
{
 return  currencyService.updateCurrency(currency);	
}	
}
