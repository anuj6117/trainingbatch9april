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

    public String addCurrency(Currency currency) throws Exception {

        if (currency.getCoinName().length() == 0)
            return "please enter coin name";

        if (currency.getSymbol().length() == 0)
            return " coin symbol can not be blank";

        if (currency.getFees() == null | currency.getFees() < 0)
            return " fee can not be blank or negative";

        if (currency.getInitialSupply() == null | currency.getInitialSupply() < 0)
            return " initialsupply can not be blank or negative";

        if (currency.getPrice() == null | currency.getPrice() < 0) {
            return " price can not be blank or negative";
        }
        if (currencyRepository.findOneBySymbol(currency.getSymbol()) != null)
            return "currency symbol already exist";

        if (currencyRepository.findOneByCoinName(currency.getCoinName()) != null)
            return "coin name already exist";


        currency.setCoinInINR(0);
        currency.setProfit(0.0);
        currencyRepository.save(currency);
        return "currency added successfully";
    }

    //getting all currency
    public List<Currency> getAllCurrency() {
        return currencyRepository.findAll();
    }

    //updating currency value

    public String updateCurrency(Currency updatedCurrency) throws Exception {

        if (updatedCurrency.getCoinName() == null) {
            return "coin name can not be null";
        }

        if (updatedCurrency.getSymbol() == null)
            return "symbol can not be blank";

        if (updatedCurrency.getPrice() == null || updatedCurrency.getPrice() < 0)
            return "price can't be null or negative";

        if (updatedCurrency.getInitialSupply() == null || updatedCurrency.getInitialSupply() < 0)
            return "initial suppy can not be null or negative";

        if (updatedCurrency.getFees() == null || updatedCurrency.getFees() < 0)
            return "fees can not be null or negative";


        Currency existingCurrency = currencyRepository.findOneByCoinId(updatedCurrency.getCoinId());
        if (existingCurrency == null) {
            return "coinid does not exist";
        }

        if (existingCurrency.getCoinName().equals(updatedCurrency.getCoinName()) && existingCurrency.getSymbol().equals(updatedCurrency.getSymbol())) {
            existingCurrency.setProfit(existingCurrency.getProfit());
            existingCurrency.setCoinInINR(existingCurrency.getCoinInINR());
            existingCurrency.setPrice(updatedCurrency.getPrice());
            existingCurrency.setFees(updatedCurrency.getFees());
            existingCurrency.setInitialSupply(updatedCurrency.getInitialSupply());
            currencyRepository.save(existingCurrency);
            return "currency updated succesfully";
        }


        if (existingCurrency != null) {
            if (!existingCurrency.getCoinName().equals(updatedCurrency.getCoinName())) {
                if (currencyRepository.findOneByCoinName(updatedCurrency.getCoinName()) == null) {
                    if (currencyRepository.findOneBySymbol(updatedCurrency.getSymbol()) == null) {
                        existingCurrency.setCoinName(updatedCurrency.getCoinName());
                        existingCurrency.setSymbol(updatedCurrency.getSymbol());
                        existingCurrency.setPrice(updatedCurrency.getPrice());
                        existingCurrency.setFees(updatedCurrency.getFees());
                        existingCurrency.setInitialSupply(updatedCurrency.getInitialSupply());
                        currencyRepository.save(existingCurrency);
                        return "currency updated succesfully";
                    } else {
                        return "symbol already exist";
                    }
                } else {
                    return "coin name already exist";
                }
            } else {
                return "coin name already exist";
            }
        }
        return null;
    }


    public String deleteCurrency (Integer coinId){
        if (currencyRepository.findOneByCoinId(coinId) != null) {
            currencyRepository.deleteById(coinId);
            return "succefully deleted currency";
        } else {
            return "id does not exist to delete";
        }
    }

    //getcurrency by id
    public Currency getCurrencyById (Integer coinId){
        if (currencyRepository.findOneByCoinId(coinId) != null) {
            return currencyRepository.findOneByCoinId(coinId);
        } else {
            return null;
        }
    }
}

