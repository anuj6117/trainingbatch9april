package com.traningproject1.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.traningproject1.domain.CurrencyClass;
import com.traningproject1.service.CurrencyService;

@RestController
public class CurrencyController {

	@Autowired
	CurrencyService currencyService;
@RequestMapping(value="/addcurrency",method=RequestMethod.POST)
public String addCurrency(@RequestBody CurrencyClass currency)
{
	CurrencyClass currencyCreated=currencyService.addCurrency(currency);
	if(currencyCreated!=null)
	return"success";
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
public void deleteCurrcency(Integer id)
{
  currencyService.deleteCurrency(id);
}
@RequestMapping(value="/updatecurrency",method=RequestMethod.POST)
public  CurrencyClass updateCurrency(@RequestBody CurrencyClass currency)
{
 return  currencyService.updateCurrency(currency);	
}	
}
