package com.example.demo.controller;

import com.example.demo.dto.UserWalletDto;
import com.example.demo.model.Wallet;
import com.example.demo.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/trading")

public class WalletController {
    @Autowired
    WalletService walletService;

    @RequestMapping(value = "/addwallet",method = RequestMethod.POST)
    public String addWallet(@RequestBody UserWalletDto userWalletDto){
        return walletService.addWallet(userWalletDto);
    }

    @RequestMapping(value="/wallethistory",method = RequestMethod.GET)
    public List<Wallet> getAllWallets(){
        return walletService.getAllWallets();
    }

    @RequestMapping(value="/getvalues")
    public void getValues(){

    }
}
