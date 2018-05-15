/*
package com.example.demo.service;

import com.example.demo.dto.DepositAmountDto;
import com.example.demo.enums.CoinType;
import com.example.demo.enums.OrderStatus;
import com.example.demo.enums.OrderType;
import com.example.demo.enums.UserStatus;
import com.example.demo.model.OrderDetails;
import com.example.demo.model.User;
import com.example.demo.model.Wallet;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Set;

@Service
public class WithDrawNDepositService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    OrderRepository orderRepository;

    */
/*public String depositAmount(DepositAmountDto depositAmountDto) {
        User user=userRepository.findOneById(depositAmountDto.getUserId());
        int counter=0;
        if(user!=null) {
            if(user.getStatus().equals(UserStatus.ACTIVE)){
            if (depositAmountDto.getWalletType().equals(CoinType.FIAT)) {
                Set<Wallet> wallets=user.getWallets();
                for(Wallet wallet:wallets){
                    if(wallet.getCoinType().equals(CoinType.FIAT)){
                        counter=1;
                        OrderDetails orderDetails=new OrderDetails();
                        orderDetails.setOrderType(OrderType.DEPOSIT);
                        orderDetails.setAmount(depositAmountDto.getAmount());
                        orderDetails.setOrderStatus(OrderStatus.PENDING);
                        orderDetails.setCoinName("INR");
                        orderDetails.setFee(0.0);
                        orderDetails.setOrderCreatedOn(new Date());
//                        orderDetails.setUserId(user.getId());
                        user.getOrderDetailsList().add(orderDetails);
                        orderDetails.setUser(user);
                        orderRepository.save(orderDetails);
//                        userRepository.save(user);
                    }
                }
                if(counter==0){
                    return "user don't have any wallet of cointype fiat";
                }
            } else {
                return "withdraw and deposit functionality should be applied on fiat wallettype";
            }
        }else{
             return  "user is not verified";
            }
        }else {
            return "user id does not exist";
        }
        return "deposit amount request submitted";
    }*//*

}
*/
