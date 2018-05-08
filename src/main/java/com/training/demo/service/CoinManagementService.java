package com.training.demo.service;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.training.demo.enums.WalletType;
import com.training.demo.model.CoinManagement;
import com.training.demo.repository.CoinManagementRepository;

@Service
public class CoinManagementService {
	
	@Autowired
	private CoinManagementRepository coinManagementRepository;

	public String addAllCoin(CoinManagement coinManagement) {
			String coinName = coinManagement.getCoinName();
			Double price = coinManagement.getPrice();
			String symbol = coinManagement.getSymbol();
			Double initSupply = coinManagement.getInitialSupply();
			Double fees = coinManagement.getFees();
			
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

			if(!(coinManagementRepository.findByCoinName(coinName) == null)) 
			{
				return "Already Existing Coin Name.";
			}
	
			if(!(coinManagementRepository.findBySymbol(symbol)==null))
			{
				return "Already Existing Symbol Type";
			}
			
	//PRICE VALIDATION
			if(price == null || price == 0)
			{
				return  "Price Can Not Be Null or Zero.";
			}
			if(price<0)
			{
				return "Price Can Not Be In Negative.";
			}
			
	//INITIAL SUPPLY VALIDATION
			if(initSupply == null || initSupply == 0)
			{
				return  "Initial Supply Can Not Be Null or Zero.";
			}
			if(initSupply<0)
			{
				return "Initial Supply Can Not Be Negative.";
			}			
			
	//FEES VALIDATION		
			if(fees == null || fees == 0)
			{
				return  "Fees Can Not Be Null or Zero.";
			}
			if(fees<0)
			{
				return "Fees Can Not Be Negative.";
			}			
			
			if(!(coinName.matches("^[a-zA-Z]{2,}$"))){
				return "Coin Name Can Not Be Numeric.";
			}
			
				coinManagement.setCoinType(WalletType.CRYPTO);
				coinManagement.setProfit(0.0);
				coinManagement.setCoinInINR(0.0);
				coinManagementRepository.save(coinManagement);
				return "Your Coin Has Been Added Successfully.";
			
	}

	public List<CoinManagement> getCurrencies() {
		return coinManagementRepository.findAll();
	}

	public String update(CoinManagement coinManagement) {
		
		CoinManagement coinManagementData = null;		
		String coinName = coinManagement.getCoinName();
		Double price = coinManagement.getPrice();
		String symbol = coinManagement.getSymbol();
		Double initSupply = coinManagement.getInitialSupply();
		Double fees = coinManagement.getFees();
		
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

		if(!(coinManagementRepository.findByCoinName(coinName) == null)) 
		{
			return "Already Existing Coin Name.";
		}

		if(!(coinManagementRepository.findBySymbol(symbol)==null))
		{
			return "Already Existing Symbol";
		}

//INITIAL SUPPLY VALIDATION
		if(initSupply == null || initSupply == 0)
		{
			return  "Initial Supply Can Not Be Null or Zero.";
		}
		if(initSupply<0)
		{
			return "Initial Supply Can Not Be Negative.";
		}			
				
//PRICE VALIDATION
		if(price == null || price == 0)
		{
			return  "Price Can Not Be Null or Zero.";
		}
		if(price<0)
		{
			return "Price Can Not Be In Negative.";
		}
		
//FEES VALIDATION		
		if(fees == null || fees == 0)
		{
			return  "Fees Can Not Be Null or Zero.";
		}
		if(fees<0)
		{
			return "Fees Can Not Be Negative.";
		}			

		if(!(coinName.matches("^[a-zA-Z]{2,}$"))){
			return "Coin Name Can Not Be Numeric.";
		}
		
		if(!(coinManagementRepository.findOneByCoinId(coinManagement.getCoinId()) == null))
		{
			coinManagementData = coinManagementRepository.findOneByCoinId(coinManagement.getCoinId());
			coinManagementData.setCoinName(coinManagement.getCoinName());
			coinManagementData.setSymbol(coinManagement.getSymbol());
			coinManagementData.setInitialSupply(coinManagement.getInitialSupply());
			coinManagementData.setPrice(coinManagement.getPrice());	
			coinManagementData.setCoinType(WalletType.CRYPTO);
			coinManagementData.setFees(coinManagement.getFees());
			coinManagementData=coinManagementRepository.save(coinManagementData);
			System.out.println("Your Coin Has Been Updated Successfully.");
			return "Your Coin Has Been Updated Successfully.";
		}
		else
		{
			return "invalid coin id";
		}
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