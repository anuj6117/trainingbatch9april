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

	//CoinManagement coinManagementData;
	CoinManagement coin;

	public String addAllCoin(CoinManagement data) {
		// String s=coinManagementRepository.save(data);

		if (coinManagementRepository.save(data) != null) {
			return "Coin Added Successfully";
		} else {
			return "Not Added Successfully";

		}
	}

	public List<CoinManagement> getCurrencies() {
		return coinManagementRepository.findAll();
	}

	public String update(CoinManagement data) {

		 CoinManagement coinManagementData = null;
		 
		 if(data.getCoinType()==WalletType.FIAT)
		 {
			 return"coin name can not be FIAT";
		 }
		String s = data.getCoinName();
		String sm = data.getSymbol();
		//coinManagementData = coinManagementRepository.findByCoinName(s);
		//coin = coinManagementRepository.findBySymbol(data.getSymbol());
		int sml = sm.length();
		int l = s.length();
		System.out.print("1111111111111111111111111111111111111///////////// "+l);
		double d = data.getInitialSupply();
		double t = data.getPrice();
		int sk = String.valueOf(t).length();
		CoinManagement coinname=coinManagementRepository.findBycoinName(data.getCoinName());
		if(!(coinManagementRepository.findBycoinName(data.getCoinName())==null))
		if(coinname.getCoinId()!=data.getCoinId())
		{
			return"coin name is already exist";
		
		}
		CoinManagement sym=coinManagementRepository.findBySymbol(data.getSymbol());
		if(!(coinManagementRepository.findBySymbol(data.getSymbol())==null))
		if(sym.getCoinId()!=data.getCoinId())
		{
			return"symbol already exist";
		}
		
		coinManagementData = coinManagementRepository.findOneByCoinId(data.getCoinId());
		if (l > 0) {

			if (sml == 0) {
				return "Symbol cant be null";

			}

		
			if (sk == 0) {
				return "price can not be null";
			}
			if (coinManagementData != null) {

				coinManagementData.setCoinName(data.getCoinName());
				//coinManagementData.setCoinType(data.getCoinType());
				coinManagementData.setSymbol(data.getSymbol());
				
				
				coinManagementData.setInitialSupply(data.getInitialSupply());
				coinManagementData.setPrice(data.getPrice());
				coinManagementData.setFee(data.getFee());
				coinManagementData = coinManagementRepository.save(coinManagementData);

				return "coin updated";
			} else {
				return "Coin not exist";
			}
		} else {
			return "coin name can not be null";
		}
	}

	public void delete(Integer id) {
		coinManagementRepository.deleteById(id);
	}
}