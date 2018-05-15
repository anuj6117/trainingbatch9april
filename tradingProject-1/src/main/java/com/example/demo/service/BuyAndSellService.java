package com.example.demo.service;

import com.example.demo.dto.BuyAndSellOrderDto;
import com.example.demo.enums.OrderStatus;
import com.example.demo.enums.OrderType;
import com.example.demo.model.OrderDetails;
import com.example.demo.model.User;
import com.example.demo.model.Wallet;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Set;

@Service
public class BuyAndSellService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    WalletRepository walletRepository;

    //during buying any currency we are applying tax of 2% so keeping fee=2%
    public String createBuyOrder(BuyAndSellOrderDto buyorder){
//        Double fee=
        if(buyorder.getUserId()==null) return "user id is blank";
        int counter=0;
        User user=userRepository.findOneById(buyorder.getUserId());
        if(user!=null){
            if(buyorder.getCoinName()==null | buyorder.getCoinQuantity()==null | buyorder.getPrice()==null){
                return "one of the field is empty";
            }
            if(buyorder.getCoinName().equalsIgnoreCase("Bitcoin") | buyorder.getCoinName().equalsIgnoreCase("Ethereum")){
                Set<Wallet> wallets=user.getWallets();
                for(Wallet existingWallet:wallets){
                    if(existingWallet.getCoinName().equalsIgnoreCase(buyorder.getCoinName())){
                        counter=1;
                        OrderDetails orderDetails=new OrderDetails();
                        orderDetails.setOrderStatus(OrderStatus.PENDING);
                        orderDetails.setUser(user);
                        orderDetails.setCoinName(existingWallet.getCoinName());
                        orderDetails.setOrderCreatedOn(new Date());
                        orderDetails.setOrderType(OrderType.BUYORDER);
                        orderDetails.setFee(2.0);
                        orderDetails.setPrice(buyorder.getPrice());
                        orderDetails.setCoinQuantity(buyorder.getCoinQuantity());
                        user.getOrderDetailsList().add(orderDetails);
                        Wallet inrwallet=walletRepository.findByCoinName(buyorder.getCoinName().toUpperCase());
                        Double moneytoBeDeducted;
                        Double amount;
                        amount=buyorder.getCoinQuantity()*buyorder.getPrice();
                        moneytoBeDeducted=amount+(amount*2)/100;
                        if(inrwallet.getShadowBalance()<moneytoBeDeducted){
                            return "Lack of money in your account to make an order";
                        }

                        orderRepository.save(orderDetails);
                    }
                }
                if(counter==0){
                    return "no such wallet found for user";
                }
            }else{
                return "coin name is not valid";
            }

        }else{
            return "user doesnot exist";
        }
        return null;
    }
}
