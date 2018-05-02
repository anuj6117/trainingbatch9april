package com.training.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.training.demo.model.CoinManagement;
import com.training.demo.repository.CoinManagementRepository;
import com.training.demo.service.CoinManagementService;

@RestController
public class CoinManagementController {

	@Autowired
	private CoinManagementService coinManagementService;
	@Autowired
	private CoinManagementRepository coinmanagementrepository;
	
	
	CoinManagement coinmanagement;
	CoinManagement coin;


	@RequestMapping(value = "/addcurrency", method = RequestMethod.POST)
	public String addCoin(@RequestBody CoinManagement data) {
	//	String result = coinManagementService.addAllCoin(data);
		String sm=data.getSymbol();
		coinmanagement=coinmanagementrepository.findByCoinName(data.getCoinName());
		coin=coinmanagementrepository.findBySymbol(data.getSymbol());
		String s=data.getCoinName();
		//String sm=data.getSymbol();
		int sml=sm.length();
		int l=s.length();
		double d=data.getInitialSupply();
		int t=data.getPrice();
		int sk = String.valueOf(t).length();
		 // int b=sk.length();

		if (l>0) {
			
			if(sml==0)
			{
				return "Symbol cant be null";
				
				
			}
			if(sk==0)
			{
				return "price can not be null";
			}
			if((coinmanagement!=null)&&(coin!=null))
			{
				return"coin or symbol already exist";
			}
			
			
			String result = coinManagementService.addAllCoin(data);
			
			
			return "success";
		} else {
			return "coin name can not be null ";
		}
	}

	@RequestMapping(value = "getallcurrency", method = RequestMethod.GET)
	public List<CoinManagement> getAllCurrency() {
		List<CoinManagement> list = coinManagementService.getCurrencies();
		return list;
	}

	@RequestMapping(value = "/updatecurrency", method = RequestMethod.POST)
	public String updateCurrency(@RequestBody CoinManagement data) {
		String result = coinManagementService.update(data);
		if (result != null) {
			return "Coin Updated";
		} else {
			return "Coin not updated";
		}
	}

	@RequestMapping(value = "/deletecurrency", method = RequestMethod.GET)
	public String delete(@RequestParam("coinId") Integer id) {
		if ((id != null)) {
			coinManagementService.delete(id);
			return "success";
		}
		return "fail";
		
		
		
	}
}