package com.trading.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trading.domain.Currency;
import com.trading.repository.CurrencyRepository;

@Service
public class CurrencyService {

	@Autowired
	private CurrencyRepository currencyrepository;

	public Map<String, Object> addCurrency(Currency currency) {
		Map<String, Object> result = new HashMap<String, Object>();

		if (currency.getCoinName() == null || currency.getCoinName() == "") {
			result.put("isSuccess", false);
			result.put("message", "Coin Name cannot be null");
			return result;
		}

		if (currencyrepository.findBySymbol(currency.getSymbol()) != null) {
			result.put("isSuccess", false);
			result.put("message", "Symbol already exists");
			return result;
		}
		if (currency.getInitialSupply() == 0L) {
			result.put("isSuccess", false);
			result.put("message", "Initial supply can not be null");
			return result;
		}
		if (currency.getPrice() == 0L) {
			result.put("isSuccess", false);
			result.put("message", "Price can not be null");
			return result;
		}

		if (currencyrepository.findBycoinName(currency.getCoinName()) == null) {
			if (currencyrepository.save(currency) != null) {
				result.put("isSuccess", true);
				result.put("message", "Your currency has been added");
				return result;
			} else {
				result.put("isSuccess", false);
				result.put("message", "Failed to add currency");
				return result;
			}
		} else {
			result.put("isSuccess", false);
			result.put("message", "Currency Type already exists");
			return result;
		}
	}

	public Iterable<Currency> getDetails() {
		return currencyrepository.findAll();
	}

	public Map<String, Object> updateDetails(Currency currency) {
		Map<String, Object> result = new HashMap<String, Object>();
		if (currency.getCoinName() == null || currency.getCoinName() == "") {
			result.put("isSuccess", false);
			result.put("message", "Coin Name cannot be null");
			return result;
		}

		if (currencyrepository.findBySymbol(currency.getSymbol()) != null) {
			result.put("isSuccess", false);
			result.put("message", "Symbol already exists");
			return result;
		}
		if (currency.getInitialSupply() == 0L) {
			result.put("isSuccess", false);
			result.put("message", "Initial supply can not be null");
			return result;
		}
		if (currency.getPrice() == 0L) {
			result.put("isSuccess", false);
			result.put("message", "Price can not be null");
			return result;
		}
		if (currencyrepository.findBycoinName(currency.getCoinName()) != null) {
			result.put("isSuccess", false);
			result.put("message", "Coin Name exists");
			return result;
		}

		if (currencyrepository.findOneByCoinId(currency.getCoinId()) != null) {
			currencyrepository.save(currency);
			result.put("isSuccess", true);
			result.put("message", "Successfully updated");
			return result;
		} else {
			result.put("isSuccess", false);
			result.put("message", "Coin Id does not exist");
			return result;
		}

	}

	public Map<String, Object> deleteById(long coinId) {
		Map<String, Object> result = new HashMap<String, Object>();
		if (currencyrepository.findOneByCoinId(coinId) != null) {
			currencyrepository.deleteById(coinId);
			result.put("isSuccess", true);
			result.put("message", "Deleted");
			return result;
		} else {
			result.put("isSuccess", false);
			result.put("message", "Coin Id does not exist");
			return result;
		}
	}

	public Currency getCurrencyById(long coinId) {
		if (currencyrepository.findOneByCoinId(coinId) != null) {
			return currencyrepository.findOneByCoinId(coinId);
		} else {
			return null;
		}

	}
}
