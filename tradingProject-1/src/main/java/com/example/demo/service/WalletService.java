package com.example.demo.service;

import com.example.demo.dto.UserWalletDto;
import com.example.demo.model.User;
import com.example.demo.model.Wallet;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class WalletService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    WalletRepository walletRepository;

    public String addWallet(UserWalletDto userWalletDto){
        User user=userRepository.findOneById(userWalletDto.getUserId());
        if(user!=null){
            Set<Wallet> existingwallet= user.getWallets();

            for(Wallet wallet:existingwallet){
                if(wallet.getCoinName().equalsIgnoreCase(userWalletDto.getCoinName())){
                    return "wallet already exists with this coin name";
                }
            }
            Wallet wallet=new Wallet();
            wallet.setCoinName(userWalletDto.getCoinName());
            wallet.setCoinType(userWalletDto.getCoinType());
            existingwallet.add(wallet);
            wallet.setUser(user);
            userRepository.save(user);
            return "wallet added succesfully";
        }else{
            return "user does not exist";
        }
    }

    public List<Wallet> getAllWallets(){
        return walletRepository.findAll();
    }

}
