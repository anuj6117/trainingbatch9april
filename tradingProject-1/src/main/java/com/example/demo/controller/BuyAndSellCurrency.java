package com.example.demo.controller;


import com.example.demo.dto.BuyAndSellOrderDto;
import com.example.demo.service.BuyAndSellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BuyAndSellCurrency {

    @Autowired
    BuyAndSellService buyAndSellService;

    @RequestMapping(value="/createbuyorder",method = RequestMethod.POST)
    public String createBuyOrder(@RequestBody BuyAndSellOrderDto buyAndSell){
        return buyAndSellService.createBuyOrder(buyAndSell);
    }
}
