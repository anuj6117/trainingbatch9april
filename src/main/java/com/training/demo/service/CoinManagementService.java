package com.training.demo.service;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
			String symbol = coinManagement.getSymbol();
			
			Double initial= coinManagement.getInitialSupply();
			String initSupply = initial.toString();
			
			//COINNAME VALIDATION
			if(coinName.equals("") || coinName.isEmpty() || coinName == null || coinName.trim().length() == 0)
			{
				return  "Coin Name Can Not Be Null.";
			}
			
			//SYMBOL VALIDATION
			if(symbol.equals("") || symbol.isEmpty() || symbol == null || symbol.trim().length() == 0)
			{
				return  "Symbol Can Not Be Null.";
			}
			
			Set<CoinManagement> allCoins= coinManagementRepository.findAllBySymbol(symbol);
			Iterator<CoinManagement> coinIterator = allCoins.iterator();
			while(coinIterator.hasNext())
			{
				if(coinIterator.next().getSymbol().equals(symbol))
				{
					return "Symbol Already Exist";
				}
			}
			while(coinIterator.hasNext())
			{
				if(coinIterator.next().getCoinName().equals(coinName))
				{
					return "Coin Name Already Exist";
				}
			}			
			
			
			//PRICE VALIDATION
			if(convertedPrice.equals("") || convertedPrice == null || convertedPrice.isEmpty() || convertedPrice.trim().length() == 0 ||convertedPrice.equals("0"))
			{
				return  "Price Can Not Be Null.";
			}
			if(price<0)
			{
				return "Price Can Not Be Negative.";
			}
			
			//INITIAL SUPPLY VALIDATION
			if(initSupply.equals("") || initSupply == null || initSupply.isEmpty() || initSupply.trim().length() == 0 ||initSupply.equals("0"))
			{
				return  "Initial Supply Can Not Be Null.";
			}
			if(initial<0)
			{
				return "Initial Supply Can Not Be Negative.";
			}			
			
			//CREATING COIN MANAGEMENT OBJECT
			tempCoinManagement = coinManagementRepository.findOneByCoinName(coinManagement.getCoinName());			
			if(tempCoinManagement == null)
			{
				coinManagement.setExchangeRate(0.0);
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

	public String delete(Integer id) 
	{
		try {
				coinManagementRepository.deleteById(id);
				return "Your Coin Has Been Deleted Successfully.";
			}
			catch(Exception e)
			{
				System.out.println("Exception :::::::::::: "+e);
				return "invalid coin id";
			}	
	}
}