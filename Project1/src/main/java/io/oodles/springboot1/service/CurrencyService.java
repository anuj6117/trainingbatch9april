package io.oodles.springboot1.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	/*@Autowired
	RoleRepository roleRepository;

	public List<Role> getallroles() {
		// TODO Auto-generated method stub
		return roleRepository.findAll();
	}
	public Optional<Role> searchbyid(Integer roleid) {
		// TODO Auto-generated method stub
		 return roleRepository.findById(roleid);
	     
	}

	public Role update(Role role, int id) {
		// TODO Auto-generated method stud
		Role role1=roleRepository.getOne(id);
		
		role1.setRoletype(role.getRoletype());
		return roleRepository.save(role1);
	}
	

	public void delete(int id) {coinname
		// TODO Auto-generated method stub
		 roleRepository.deleteById(id);;
	}
	public Role create(Currency currency) {
		// TODO Auto-generated method stub
		//System.out.println("Done2");
		return roleRepository.save(currency);
	}
	public List<Currency> getallcurrency() {
		// TODO Auto-generated method stub
		return null;
	}
*/

	public String create(Currency currency) {
		// TODO Auto-generated method stub
		Currency currency1=currencyRepository.findByCoinName(currency.getCoinName());
		Currency currencySymbol=currencyRepository.findBySymbol(currency.getSymbol());
		//Currency currencyType=currencyRepository.findByCoinType(currency.getCoinType());
		Currency currencypresent=currencyRepository.findByCoinNameAndCoinType(currency.getCoinName(), currency.getCoinType());
		if(currency.getCoinName().length()==0) {
			return "Coin Name can't be null.";
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
		
		
		
		
		
		else {
		currencyRepository.save(currency);
		return "Currency Added";}
	}

	public Optional<Currency> searchbyid(Integer id) {
		// TODO Auto-generated method stub
		return currencyRepository.findById(id); 
		
	}

	public String update(Currency currency) {
		// TODO Auto-generated method stub
		Currency currency1=currencyRepository.findById(currency.getCoinId()).get();
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
