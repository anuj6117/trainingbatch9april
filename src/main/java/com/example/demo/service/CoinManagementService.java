package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.CoinManagement;
import com.example.demo.repository.CoinManagementRepository;

@Service
public class CoinManagementService {
	
	@Autowired
	private CoinManagementRepository coinManagementRepository;

	public String addAllCoin(CoinManagement data) {
		
		if(coinManagementRepository.save(data) != null)
		{
			return "Coin Added Successfully";
		}
		else
		{
			return "Not Added Successfully";
		}
	}

	public List<CoinManagement> getCurrencies() {
		return coinManagementRepository.findAll();
	}
	
	

}
