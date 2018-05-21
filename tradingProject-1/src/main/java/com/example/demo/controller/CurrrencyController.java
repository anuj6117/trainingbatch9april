package com.example.demo.controller;

import com.example.demo.model.Currency;
import com.example.demo.service.CurrencyService;
import com.example.demo.utilities.DataObj;
import com.example.demo.utilities.ResponseFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/trading")

public class CurrrencyController {

    @Autowired
    CurrencyService currencyService;

    @RequestMapping(value = "/addcurrency",method = RequestMethod.POST)
    public ResponseFormatter addCurrency(@RequestBody  Currency currency){
        try {
            String result = currencyService.addCurrency(currency);
            if(result.equalsIgnoreCase("currency added successfully"))
                return 	new ResponseFormatter(result,new Date(),HttpStatus.OK,true,new DataObj("suceess"));
            else
                return 	new ResponseFormatter(result,new Date(),HttpStatus.INTERNAL_SERVER_ERROR,false,new DataObj("failure"));
        }catch (Exception e){
            System.out.println(e.getMessage());
            return 	new ResponseFormatter(e.getMessage(),new Date(),HttpStatus.BAD_REQUEST,false,new DataObj("failure"));
        }
    }

    @RequestMapping(value="/getallcurrency",method = RequestMethod.GET)
    public List<Currency> getAllCurrency(){
        return currencyService.getAllCurrency();
    }

    @RequestMapping(value="/updatecurrency",method = RequestMethod.POST)
    public ResponseFormatter updateCurrency(@RequestBody Currency updatedCurrency){
        try {
            String result = currencyService.updateCurrency(updatedCurrency);
            if(result.equalsIgnoreCase("currency updated succesfully"))
                return 	new ResponseFormatter(result,new Date(),HttpStatus.OK,true,new DataObj("suceess"));
            else
                return 	new ResponseFormatter(result,new Date(),HttpStatus.INTERNAL_SERVER_ERROR,false,new DataObj("failure"));
        }catch (Exception e){
            System.out.println(e.getMessage());
            return 	new ResponseFormatter(e.getMessage(),new Date(),HttpStatus.BAD_REQUEST,false,new DataObj("failure"));
        }
    }

    @RequestMapping(value="/deletecurrency" ,method = RequestMethod.GET)
    public ResponseFormatter deleteCurrency(@RequestParam("coinId") Integer coinId){
        try {
            String result = currencyService.deleteCurrency(coinId);
            if(result.equalsIgnoreCase("succefully deleted currency"))
                return 	new ResponseFormatter(result,new Date(),HttpStatus.OK,true,new DataObj("suceess"));
            else
                return 	new ResponseFormatter(result,new Date(),HttpStatus.INTERNAL_SERVER_ERROR,false,new DataObj("failure"));
        }catch (Exception e){
            System.out.println(e.getMessage());
            return 	new ResponseFormatter(e.getMessage(),new Date(),HttpStatus.BAD_REQUEST,false,new DataObj("failure"));
        }
    }

    @RequestMapping(value="/getcurrencybyid",method = RequestMethod.GET)
    public Currency getCurrencyById(@RequestParam("coinId") Integer coinId){
        Currency currency= currencyService.getCurrencyById(coinId);
        if(currency !=null) return currency;
        else return new Currency();
    }
}
