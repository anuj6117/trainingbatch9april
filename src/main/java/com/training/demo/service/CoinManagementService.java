package com.training.demo.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.training.demo.enums.WalletType;
import com.training.demo.model.CoinManagement;
import com.training.demo.repository.CoinManagementRepository;

@Service
public class CoinManagementService {
	
	@Autowired
	private CoinManagementRepository coinManagementRepository;

	
	
//Corrected and Validated	
	public String addAllCoin(CoinManagement coinManagement) {
			String coinName = coinManagement.getCoinName().toUpperCase();
			Double price = coinManagement.getPrice();
			String symbol = coinManagement.getSymbol();
			Double initSupply = coinManagement.getInitialSupply();
			Double fees = coinManagement.getFees();
			
	//COINNAME VALIDATION
			if(coinName.equals("") || coinName.isEmpty() || coinName == null || coinName.trim().length() == 0)
			{
				return  "Coin Name Can Not Be Null.";
			}

			if(!(coinManagementRepository.findByCoinName(coinName) == null)) 
			{
				return "Already Existing Coin Name.";
			}
			
			if(!(coinName.matches("^([a-zA-Z]{2,}$)"))){
				return "Invalid coin name, coin name can not contain spaces and digits.";
			}
					
			
	//SYMBOL VALIDATION
			if(symbol.equals("") || symbol.isEmpty() || symbol == null || symbol.trim().length() == 0)
			{
				return  "Symbol Can Not Be Null.";
			}

			if(!(symbol.matches("^([a-zA-Z0-9[#?!@$%^&*-<>~`)(_+=}[{]':;/]]{2,}$)"))){
					return "Invalid symbol, symbol can not contain spaces.";
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
			if(fees == null)
			{
				return  "Fees Can Not Be Null.";
			}
			if(fees<0)
			{
				return "Fees Can Not Be Negative.";
			}			
				coinManagement.setCoinName(coinName);
				coinManagement.setCoinType(WalletType.CRYPTO);
				coinManagement.setProfit(0.0);
				coinManagement.setCoinInINR(0.0);
				coinManagementRepository.save(coinManagement);
				return "Your Coin Has Been Added Successfully.";
			
	}

//Corrected and Validated
	public Object getCurrencies() {
		List l = coinManagementRepository.findAll();
		if(l == null)
		{
			return "There are no any currencies available for now.";
		}
		return l;
	}

//Corrected and Validated	
	public String update(CoinManagement coinManagement) {
		
		CoinManagement coinManagementData = null;		
		String coinName = coinManagement.getCoinName().toUpperCase();
		Double price = coinManagement.getPrice();
		String symbol = coinManagement.getSymbol();
		Double initSupply = coinManagement.getInitialSupply();
		Double fees = coinManagement.getFees();
//CoinId VALIDATION
		CoinManagement cc ;
		if((cc = coinManagementRepository.findOneByCoinId(coinManagement.getCoinId()))==null){
			return "Invalid coin id";
		}
		
		coinManagementData = cc;
		
//COINNAME VALIDATION
		if(coinName.equals("") || coinName.isEmpty() || coinName == null || coinName.trim().length() == 0)
		{
			return  "Coin Name Can Not Be Null.";
		}
		cc = null;
		if((cc = coinManagementRepository.findByCoinName(coinName)) == null)
		{
			return "invalid coin name";
		}
		if(cc.getCoinId() != coinManagement.getCoinId())
		{
			return "already existing coin name";
		}
				
		if(!(coinName.matches("^([a-zA-Z]{2,}$)"))){
			return "Invalid coin name, coin name can not contain spaces and digits.";
		}
				
		
//SYMBOL VALIDATION
		if(symbol.equals("") || symbol.isEmpty() || symbol == null || symbol.trim().length() == 0)
		{
			return  "Symbol Can Not Be Null.";
		}
		
		if(!(symbol.matches("^([a-zA-Z0-9[#?!@$%^&*-<>~`)(_+=}[{]':;/]]{2,}$)"))){
			return "Invalid symbol, symbol can not contain spaces.";
		}
		cc = null;
		if((cc = coinManagementRepository.findBySymbol(symbol)) != null){
			if(cc.getCoinId() != coinManagement.getCoinId())
				{
					return "already existing symbol";
				}
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
		if(fees == null)
		{
			return  "Fees Can Not Be Null";
		}
		if(fees<0)
		{
			return "Fees Can Not Be Negative.";
		}			

			//coinManagementData = coinManagementRepository.findOneByCoinId(coinManagement.getCoinId());
			coinManagementData.setCoinName(coinName);
			coinManagementData.setSymbol(coinManagement.getSymbol());
			coinManagementData.setInitialSupply(coinManagement.getInitialSupply());
			coinManagementData.setPrice(coinManagement.getPrice());	
			coinManagementData.setCoinType(WalletType.CRYPTO);
			coinManagementData.setFees(coinManagement.getFees());
			coinManagementData=coinManagementRepository.save(coinManagementData);
			System.out.println("Your Coin Has Been Updated Successfully.");
			return "Your Coin Has Been Updated Successfully.";

	}

//Corrected and Validated	
	public String delete(Integer id) 
	{
		try {
				coinManagementRepository.deleteById(id);
				return "Your Coin Has Been Deleted Successfully.";
			}
			catch(Exception e)
			{
				return "invalid coin id";
			}	
	}
}