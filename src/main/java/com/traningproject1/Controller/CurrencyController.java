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
    if(currency.getInitialSupply()==0&&currency.getPrice()==0)
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
	else
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
