package com.example.demo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.CoinManagement;
import com.example.demo.repository.CoinManagementRepository;

@Service
public class CoinManagementService {
	
	@Autowired
	private CoinManagementRepository coinManagementRepository;
	
	public Map<String, Object> addCurrency(CoinManagement coinManagement)
	{
		Map<String, Object> result = new HashMap<String, Object>();
		
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
		else
		{
			result.put("isSuccess", true);
			result.put("message", "success");
			coinManagement.setCoinName(coinName);
			coinManagement.setSymbol(symbol);
			coinManagementRepository.save(coinManagement);
			return result;
			
		}
	}
	
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
		
		if(coinId != null && coinId != 0)
		{
			coinManagementRepository.deleteById(coinId);
			result.put("isSuccess", true);
			result.put("message", "success");
			return result;
		}
		else
		{
			result.put("isSuccess", false);
			result.put("message", "coinId does not exist.");
			return result;
		}
	}
	
	public List<CoinManagement> getAllCurrencies() 
	{
		return coinManagementRepository.findAll();
	}
	
	

}
