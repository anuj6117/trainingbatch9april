package com.example.demo.controller;

import com.example.demo.model.Currency;
import com.example.demo.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CurrrencyController {

    @Autowired
    CurrencyService currencyService;

    @RequestMapping(value = "/addcurrency",method = RequestMethod.POST)
    public String addCurrency(@RequestBody  Currency currency){
        return  currencyService.addCurrency(currency);
    }

    @RequestMapping(value="/getallcurrency")
    public List<Currency> getAllCurrency(){
        return currencyService.getAllCurrency();
    }

    @RequestMapping(value="/updatecurrency",method = RequestMethod.POST)
    public String updateCurrency(@RequestBody Currency updatedCurrency){
        return  currencyService.updateCurrency(updatedCurrency);
    }

    @RequestMapping(value="/deletecurrency" ,method = RequestMethod.GET)
    public String deleteCurrency(@RequestParam("coinId") Integer coinId){
        return  currencyService.deleteCurrency(coinId);
    }

    @RequestMapping(value="/getcurrencybyid")
    public Currency getCurrencyById(@RequestParam("coinId") Integer coinId){
        Currency currency= currencyService.getCurrencyById(coinId);
        if(currency !=null) return currency;
        else return new Currency();
    }
}
