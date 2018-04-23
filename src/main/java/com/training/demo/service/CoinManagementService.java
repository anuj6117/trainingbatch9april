package com.training.demo.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.training.demo.model.CoinManagement;
import com.training.demo.repository.CoinManagementRepository;

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

	public String update(CoinManagement data) {
		CoinManagement coinManagementData=null;
		
		coinManagementData=coinManagementRepository.findOneByCoinId(data.getCoinId());
		coinManagementData.setCoinName(data.getCoinName());
		coinManagementData.setSymbol(data.getSymbol());
		coinManagementData.setInitialSupply(data.getInitialSupply());
		coinManagementData.setPrice(data.getPrice());	
		coinManagementData=coinManagementRepository.save(coinManagementData);
		if(coinManagementData != null)
		{
			return "coin updated";
		}
		return "Coin not updated";
	}

	public void delete(Integer id) {
		
		 coinManagementRepository.deleteById(id.longValue());
	}
}