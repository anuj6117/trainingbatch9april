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
public class WalletController {
    @Autowired
    WalletService walletService;

    @RequestMapping(value = "/addwallet",method = RequestMethod.POST)
    public String addWallet(@RequestBody UserWalletDto userWalletDto){
           return walletService.addWallet(userWalletDto);
        }

    @RequestMapping(value="/getallwallets")
    public List<Wallet> getAllWallets(){
       return walletService.getAllWallets();
    }
}
