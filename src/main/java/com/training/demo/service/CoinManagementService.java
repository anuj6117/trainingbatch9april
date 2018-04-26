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

	public String addAllCoin(CoinManagement coinManagement) {
			coinManagementRepository.save(coinManagement);
			return "Your Coin Has Been Added Successfully.";
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
		return "Your Coin Has Been Updated Successfully.";
	}

	public String delete(Integer id) {
		
		 coinManagementRepository.deleteById(id);
		 return "Your Coin Has Been Deleted Successfully.";
	}
}