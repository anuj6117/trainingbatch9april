package com.example.demo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.CoinManagement;
import com.example.demo.repository.CoinManagementRepository;

@Service
public class CoinManagementService {
	
	@Autowired
	private CoinManagementRepository coinManagementRepository;
	
	public Object addCurrency(CoinManagement coinManagement)
	{
		String coinName=coinManagement.getCoinName();
		Double initialSupply = coinManagement.getInitialSupply();
		Double price = coinManagement.getPrice();
		String symbol=coinManagement.getSymbol();
		Double fee = coinManagement.getFee();
		
		CoinManagement coinManagementObject = coinManagementRepository.findByCoinName(coinName);
		
		if(coinManagementObject == null)
		{
			if(coinName.length() == 0)
			{
				return "Coin Name should not be empty.";
			}
			if(coinName.startsWith(" "))
			{
				return "Coin Name should not have leading space.";
			}
			if(coinName.endsWith(" "))
			{
				return "Coin Name should not have trailing space.";
			}
			if(!(Pattern.compile("^[a-zA-Z\\s]{3,15}$").matcher(coinName).matches())) 
			{
				return "Space and special character is not allowed in Coin Name or length should not exceed 15 character.";

			}	
			if(symbol.length() == 0)
			{
				return "symbol should not be empty.";
			}
			if(symbol.startsWith(" "))
			{
				return "symbol should not have leading space.";
			}
			if(symbol.endsWith(" "))
			{
				return "symbol should not have trailing space.";
			}
			
			if(!(symbol.matches("^([a-zA-Z0-9[#?!@$%^&*-<>~`)(_+=}[{]':;/]]{2,}$)")))
			{
				return "Special character is not allowed in symbol or length should not exceed 15 character.";
			}
			
			if(initialSupply == null)
			{
				return "initialSuppy should not be null.";
			}
			if(initialSupply < 0)
			{
				return "initialSuppy should not less than zero.";
			}
			if(price == null)
			{
				return "price should not be null.";
			}
			if(price < 0)
			{
				return "price should not less than zero.";
			}
			coinManagement.setCoinName(coinName);
			coinManagement.setSymbol(symbol);
			coinManagement.setPrice(price);
			coinManagement.setInitialSupply(initialSupply);
			coinManagement.setFee(fee);
			coinManagement.setProfit(0.0);
			coinManagement.setExchangeRate(0.0);
			coinManagement.setCoinInInr(0.0);
			coinManagementRepository.save(coinManagement);
			
			return "coin added successfully.";
		}
		else
		{
			return "Coin already exist.";
		}
	}
	
	/*public Map<String, Object> addCurrency(CoinManagement coinManagement)
	{
		Map<String, Object> result = new HashMap<String, Object>();
		
		String coinName=coinManagement.getCoinName();
		Double initialSupply = coinManagement.getInitialSupply();
		Double price = coinManagement.getPrice();
		String symbol=coinManagement.getSymbol();
		Double fee = coinManagement.getFee();
		
		CoinManagement coinManagementObject = coinManagementRepository.findByCoinName(coinName);
		
		if(coinManagementObject == null)
		{
			if(coinName.length() == 0)
			{
				result.put("isSuccess", false);
				result.put("message", "Coin Name should not be empty.");
				return result;
			}
			if(coinName.startsWith(" "))
			{
				result.put("isSuccess", false);
				result.put("message", "Coin Name should not have leading space.");
				return result;
			}
			if(coinName.endsWith(" "))
			{
				result.put("isSuccess", false);
				result.put("message", "Coin Name should not have trailing space.");
				return result;
			}
			if(!(Pattern.compile("^[a-zA-Z\\s]{3,15}$").matcher(coinName).matches())) 
			{
				result.put("isSuccess", false);
				result.put("message", "Space and special character is not allowed in Coin Name or length should not exceed 15 character.");
				return result;
			}	
			if(symbol.length() == 0)
			{
				result.put("isSuccess", false);
				result.put("message", "symbol should not be empty.");
				return result;
			}
			if(symbol.startsWith(" "))
			{
				result.put("isSuccess", false);
				result.put("message", "symbol should not have leading space.");
				return result;
			}
			if(symbol.endsWith(" "))
			{
				result.put("isSuccess", false);
				result.put("message", "symbol should not have trailing space.");
				return result;
			}
			
			if(!(symbol.matches("^([a-zA-Z0-9[#?!@$%^&*-<>~`)(_+=}[{]':;/]]{2,}$)")))
			{
				result.put("isSuccess", false);
				result.put("message", "Special character is not allowed in symbol or length should not exceed 15 character.");
				return result;
			}
			
			if(initialSupply == null)
			{
				result.put("isSuccess", false);
				result.put("message", "initialSuppy should not be null.");
				return result;
			}
			if(initialSupply < 0)
			{
				result.put("isSuccess", false);
				result.put("message", "initialSuppy should not less than zero.");
				return result;
			}
			if(price == null)
			{
				result.put("isSuccess", false);
				result.put("message", "price should not be null.");
				return result;
			}
			if(price < 0)
			{
				result.put("isSuccess", false);
				result.put("message", "price should not less than zero.");
				return result;
			}
			coinManagement.setCoinName(coinName);
			coinManagement.setSymbol(symbol);
			coinManagement.setPrice(price);
			coinManagement.setInitialSupply(initialSupply);
			coinManagement.setFee(fee);
			coinManagement.setProfit(0.0);
			coinManagement.setExchangeRate(0.0);
			coinManagement.setCoinInInr(0.0);
			coinManagementRepository.save(coinManagement);
			
			result.put("isSuccess", true);
			result.put("message", "coin added successfully.");
			return result;
		}
		else
		{
			result.put("isSuccess", false);
			result.put("message", "Coin already exist.");
			return result;
		}
	}*/
		
	public Map<String, Object> updateCurrency(CoinManagement coinManagement)
	{
		Map<String, Object> result = new HashMap<String, Object>();
		
		CoinManagement newCoinManagement= new CoinManagement();
		
		newCoinManagement=coinManagementRepository.findOneByCoinId(coinManagement.getCoinId());
		
		if(newCoinManagement != null)
		{
			String coinName=coinManagement.getCoinName();
			if(coinName.equals("") || coinName.isEmpty() || coinName == null)
			{
				result.put("isSuccess", false);
				result.put("message", "Coin Name can't be null.");
				return result;
			}
			if(coinName.trim().length() == 0)
			{
				result.put("isSuccess", false);
				result.put("message", "Coin Name must contain characters.");
				return result;
			}
			newCoinManagement.setCoinName(coinName);
			newCoinManagement.setFee(coinManagement.getFee());
			newCoinManagement.setInitialSupply(coinManagement.getInitialSupply());
			newCoinManagement.setPrice(coinManagement.getPrice());
			newCoinManagement.setProfit(coinManagement.getProfit());
			
			String symbol=coinManagement.getSymbol();
			if(symbol.equals("") || symbol.isEmpty() || symbol == null)
			{
				result.put("isSuccess", false);
				result.put("message", "Symbol can't be null.");
				return result;
			}
			if(symbol.trim().length() == 0)
			{
				result.put("isSuccess", false);
				result.put("message", "Symbol must contain characters.");
				return result;
			}
			
			newCoinManagement.setSymbol(symbol);
			newCoinManagement.setCoinInInr(coinManagement.getCoinInInr());
			
			coinManagementRepository.save(newCoinManagement);
			
			result.put("isSuccess", true);
			result.put("message", "success");
			return result;
		}
		else
		{
			result.put("isSuccess", false);
			result.put("message", "fail");
			return result;
		}
	}

	public Map<String, Object> deleteCurrency(Integer coinId)
	{
		Map<String,Object> result = new HashMap<String, Object>();
		
		try
		{
			coinManagementRepository.deleteById(coinId);
			result.put("isSuccess", true);
			result.put("message", "coin deleted successfully.");
			return result;
		}
		catch(Exception e)
		{
			result.put("isSuccess", true);
			result.put("message", "coin does not exist of given id.");
			return result;
		}
	}
	
	public List<CoinManagement> getAllCurrencies() 
	{
		return coinManagementRepository.findAll();
	}

	public Object getCurrencyById(Integer coinId) 
	{
		return coinManagementRepository.findOneByCoinId(coinId);
	}
	
	

}
