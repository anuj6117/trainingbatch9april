package io.oodles.springboot1.service;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.oodles.springboot1.enums.WalletType;
import io.oodles.springboot1.model.Currency;
import io.oodles.springboot1.repository.CurrencyRepository;

@Service
public class CurrencyService {
	
	Currency currency;
	@Autowired
	CurrencyRepository currencyRepository;

	public List<Currency> getallcurrency() {
		// TODO Auto-generated method stub
		return currencyRepository.findAll();
	}
	

	public String create(Currency currency) {
		// TODO Auto-generated method stub
		Currency currency1=currencyRepository.findByCoinName(currency.getCoinName());
		Currency currencySymbol=currencyRepository.findBySymbol(currency.getSymbol());
		Currency currencyname=currencyRepository.findByCoinName(currency.getCoinName());
		
		if(currencyname!=null) {
			return "name already present";
		}
		Currency currencypresent=currencyRepository.findByCoinNameAndCoinType(currency.getCoinName(), currency.getCoinType());
		if(currency.getCoinName().length()==0) {
			return " can't be null.";
		}
		if(currency.getCoinType() == null) {
			return "can't be null";
		}
		if(currency.getCoinType()!=WalletType.CRYPTO) {
			return "invalid coinType";
		}
		
		 if(currency.getSymbol().length()==0) {
			return "Symbol can't be null";
		} if(currency1!=null) {
			return "Coin Name already present";
		} if(currencySymbol!=null) {
			return "Symbol already present";
		}
		if(currencypresent!=null) {
			return "Already Present";
		}
		if(currency.getFees()==null || currency.getFees()<0) {
			return "invalid fees";
		}
		if(currency.getInitialSupply()==null || currency.getInitialSupply()<0) {
			return "invalid initial supply";
		}
		if(currency.getPrice()==null || currency.getPrice()<0) {
			return "invalid price";
		}
		if(!Pattern.matches("^[a-zA-Z0-9_-]{1,25}$", currency.getCoinName())) {
			
			return "invalid currency name";
		}
		
			currencyRepository.save(currency);
			return "Currency Added";
		
		
	}

	public Currency searchbyid(Integer id) {
		// TODO Auto-generated method stub
		Currency currency=currencyRepository.findByCoinId(id);
		if(currency!=null) {
			return currency;
		}
		else {throw new NullPointerException("currency does not exist");}
		 
		
	}

	public String update(Currency currency) {
		// TODO Auto-generated method stub
		if(currency.getCoinName().length()==0) {
			return "coin name cant be null";
		}
		if(currency.getFees()==null || currency.getFees()<0) {
			return "fees invalid";
		}
		if(currency.getInitialSupply()==null || currency.getInitialSupply()<0) {
			return "invalid initial supply";
		}
		if(currency.getPrice()==null || currency.getPrice()<0) {
			return "invalid price";
		}
		 if(currency.getSymbol().length()==0) {
			return "Symbol can't be null";
		}
		 if(!Pattern.matches("^[a-zA-Z0-9_-]{1,25}$", currency.getCoinName())) {
				
				return "invalid currency name";
			}
		Currency currency1=currencyRepository.findById(currency.getCoinId()).get();
		Currency currencypresent=currencyRepository.findBySymbol(currency.getSymbol());
		Currency currencyname=currencyRepository.findByCoinName(currency.getCoinName());
		if(currencypresent!=null) {
			return "symbol already present";
		}
		if(currencyname!=null) {
			return "name already present";
		}
		System.out.println(currency.getCoinId());
		currency.setCoinInINR(currency1.getCoinInINR());
		currency.setProfit(currency1.getProfit());
		
		currencyRepository.save(currency);
		return "Currency Updated";
		
	}

	public String delete(int id) {
		// TODO Auto-generated method stub
		Currency currency=currencyRepository.findByCoinId(id);
		if(currency!=null) {
		currencyRepository.deleteById(id);
		return "Currency has been Deleted";}
		 return "currency not present";
	}
}
