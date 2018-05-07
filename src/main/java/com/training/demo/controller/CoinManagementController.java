package com.training.demo.controller;

import java.util.ArrayList;
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
	List<CoinManagement> list = new ArrayList<>();

	@RequestMapping(value = "/addcurrency", method = RequestMethod.POST)
	public String addCoin(@RequestBody CoinManagement data) {
	//	String result = coinManagementService.addAllCoin(data);
		String sm=data.getSymbol();
		//list.add(coinmanagementrepository.findByCoinName(data.getCoinName()));
		//if(!list.isEmpty())
			//return "qwerty";
		String s=data.getCoinName();
		coinmanagement=coinmanagementrepository.findByCoinName(s);
		coin=coinmanagementrepository.findBySymbol(data.getSymbol());
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
			if(coinmanagement!=null)
			{
				return"coin Name already exist";
			}
			if(coin!=null)
			{
				return "symbol already exist";
			}
			
			
			String result = coinManagementService.addAllCoin(data);
			
			
			return "success";
		} else {
			return "coin name can not be null ";
		}
		//List<CoinManagement>currency=coinmanagementrepository.findAll();
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
		System.out.println(id);
		if ((id != null)) {
			System.out.println(id);
			coinManagementService.delete(id);
			System.out.println(id);
			return "success";
		}
		return "fail";
		
		
		
	}
}