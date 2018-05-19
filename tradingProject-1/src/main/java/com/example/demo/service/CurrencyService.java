package com.example.demo.service;

import com.example.demo.enums.CoinType;
import com.example.demo.model.Currency;
import com.example.demo.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurrencyService {

    @Autowired
    CurrencyRepository currencyRepository;
    public String addCurrency(Currency currency){

        if(currency.getCoinName().length()==0)
            return "please enter coin name";

        if(currency.getSymbol().length()==0)
            return " coin symbol can not be blank";

        if(currency.getFees()==null | currency.getFees()<0)
            return  " fee can not be blank or negative";

        if(currency.getInitialSupply()==null | currency.getInitialSupply()<0)
            return " initialsupply can not be blank or negative";

        if(currency.getPrice()==null | currency.getPrice()<0){
            return " price can not be blank or negative";
        }

//        if(currency.getCoinType()==null )
//            return "coin type can not be null";

//        if(!currency.getCoinType().equals(CoinType.CRYPTO))
//            return "coin type is not correct";
        if(currencyRepository.findOneBySymbol(currency.getSymbol())!=null)
            return "currency symbol already exist";

        if(currencyRepository.findOneByCoinName(currency.getCoinName())!=null)
            return "coin name already exist";


        currency.setCoinInINR(0);
        currency.setProfit(0.0);
        currencyRepository.save(currency);
        return "currency added successfully";
    }

    //getting all currency
    public List<Currency> getAllCurrency(){
        return  currencyRepository.findAll();
    }

    //updating currency value

    public String updateCurrency(Currency updatedCurrency){

        Currency existingCurrency=currencyRepository.findOneByCoinId(updatedCurrency.getCoinId());
        if(existingCurrency!=null) {
            if (updatedCurrency.getCoinName() == null) {
                return "coin name can not be null";
            }

            Currency currencyName = currencyRepository.findOneByCoinName(updatedCurrency.getCoinName());
            Currency currencySymbol=currencyRepository.findOneBySymbol(updatedCurrency.getSymbol());
            if (currencyName != null ) {
                if (currencyName.getSymbol().equals(updatedCurrency.getSymbol()) && existingCurrency.getCoinType().equals(updatedCurrency.getCoinType())) {
                    existingCurrency.setInitialSupply(updatedCurrency.getInitialSupply());
                    existingCurrency.setFees(updatedCurrency.getFees());
                    existingCurrency.setPrice(updatedCurrency.getPrice());
                } else {
                    return "coin name and symbol mismatch";
                }
            } else {
                return "No such coin exist";
            }

//            if(updatedCurrency.getCoinType()==null){
//                return "coin type can not be blank";
//            }
//            if(!updatedCurrency.getCoinType().equals(CoinType.CRYPTO))
//                return "coin type is not crypto";

            if(updatedCurrency.getSymbol()==null)
                return "symbol can not be blank";

           /* if(currencySymbol!=null){

            }
                return "coin symbol already exists";
*/
            if(updatedCurrency.getPrice()==null | updatedCurrency.getPrice()<0)
                return "price can't be null or negative";

            if(updatedCurrency.getInitialSupply()==null | updatedCurrency.getInitialSupply()<0)
                return "initial suppy can not be null or negative";

            if(updatedCurrency.getFees()==null | updatedCurrency.getFees()<0)
                return "fees can not be null or negative";

            else{
                existingCurrency.setCoinInINR(existingCurrency.getCoinInINR());
                existingCurrency.setProfit(existingCurrency.getProfit());
                currencyRepository.save(existingCurrency);
                return "currency succesfully updated";
            }
        }else{
            return  "coin id does not exist";
        }
    }

    public String deleteCurrency(Integer coinId){
        if(currencyRepository.findOneByCoinId(coinId)!=null){
            currencyRepository.deleteById(coinId);
            return "succefully deleted currency";
        }else{
            return "id does not exist to delete";
        }
    }

    //getcurrency by id
    public Currency getCurrencyById(Integer coinId){
        if(currencyRepository.findOneByCoinId(coinId)!=null){
            return currencyRepository.findOneByCoinId(coinId);
        }else{
            return  null;
        }
    }
}
