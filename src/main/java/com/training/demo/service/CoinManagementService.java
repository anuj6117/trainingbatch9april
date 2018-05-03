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
			CoinManagement tempCoinManagement;
			String coinName = coinManagement.getCoinName();
			Double price = coinManagement.getPrice();
			String convertedPrice = Double.toString(price);
			if(coinName.equals("") || coinName.isEmpty() || coinName == null || coinName.trim().length() == 0)
			{
				return  "Coin Name Can Not Be Null Or Empty.";
			}
			if(convertedPrice.equals("") || price == null || convertedPrice.isEmpty() || convertedPrice.trim().length() == 0 ||convertedPrice.equals("0"))
			{
				return  "price Can Not Be Null Or Empty or 0.";
			}
			if(price<0)
			{
				return "Price Can Not Be Negative.";
			}
			tempCoinManagement = coinManagementRepository.findOneByCoinName(coinManagement.getCoinName());			
			if(tempCoinManagement == null)
			{
				coinManagement.setProfit(0.0);
				coinManagement.setCoinInINR(0.0);
				coinManagementRepository.save(coinManagement);
				return "Your Coin Has Been Added Successfully.";
			}
			else
			{
				return "Already existing coin name.";
			}
	}

	public List<CoinManagement> getCurrencies() {
		return coinManagementRepository.findAll();
	}

	public String update(CoinManagement coinManagement) {
		CoinManagement coinManagementData=null;		
		String coinName = coinManagement.getCoinName();
		Double price = coinManagement.getPrice();
		String convertedPrice = Double.toString(price);
		if(coinName.equals("") || coinName.isEmpty() || coinName == null || coinName.trim().length() == 0)
		{
			return  "Coin Name Can Not Be Null Or Empty.";
		}
		if(convertedPrice.equals("") || price == null || convertedPrice.isEmpty() || convertedPrice.trim().length() == 0 ||convertedPrice.equals("0"))
		{
			return  "price Can Not Be Null Or Empty or 0.";
		}
		if(price<0)
		{
			return "Price Can Not Be Negative.";
		}
				
		try 
		{
			coinManagementData=coinManagementRepository.findOneByCoinId(coinManagement.getCoinId());
		}
		catch(Exception e)
		{
			return "invalid coinId.";
		}
		
		coinManagementData.setCoinName(coinManagement.getCoinName());
		coinManagementData.setSymbol(coinManagement.getSymbol());
		coinManagementData.setInitialSupply(coinManagement.getInitialSupply());
		coinManagementData.setPrice(coinManagement.getPrice());	
		coinManagementData.setExchangeRate(coinManagement.getExchangeRate());
		coinManagementData.setFees(coinManagement.getFees());
		coinManagementData=coinManagementRepository.save(coinManagementData);
		return "Your Coin Has Been Updated Successfully.";
	}

	public String delete(Integer id) {
		String convertedId = id.toString();
		if(convertedId.equals("") || convertedId.isEmpty() || convertedId == null || convertedId.trim().length() == 0 || convertedId.equals("0"))
		{
			return  "Coin Name Can Not Be Null Or Empty Or Zero.";
		}
			coinManagementRepository.deleteById(id);
		 return "Your Coin Has Been Deleted Successfully.";
	}
}