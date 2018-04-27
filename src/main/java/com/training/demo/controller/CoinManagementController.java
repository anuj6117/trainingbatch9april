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

	@RequestMapping(value = "/addcurrency", method = RequestMethod.POST)
	public String addCoin(@RequestBody CoinManagement data) {
		String result = coinManagementService.addAllCoin(data);
		String s=data.getCoinName();
		int l=s.length();

		if ((result != null)&&(l>0)) {
			return "From controller coin added";
		} else {
			return "data not found";
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