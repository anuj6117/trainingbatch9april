package com.example.demo.service;

import com.example.demo.dto.BuyAndSellOrderDto;
import com.example.demo.enums.OrderStatus;
import com.example.demo.enums.OrderType;
import com.example.demo.model.Currency;
import com.example.demo.model.OrderDetails;
import com.example.demo.model.User;
import com.example.demo.model.Wallet;
import com.example.demo.repository.CurrencyRepository;
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

    @Autowired
    CurrencyRepository currencyRepository;

    //during buy order the tax on coin should be according to admin defined fee
    public String createBuyOrder(BuyAndSellOrderDto buyorder){
        if(buyorder.getUserId()==null) return "user id is blank";
        if(buyorder.getCoinName()==null | buyorder.getCoinQuantity()==null | buyorder.getPrice()==null){
            return "one of the field is empty";
        }
        int counter=0;
        User user=userRepository.findOneById(buyorder.getUserId());
        if(user!=null){
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
                        int netAmount=buyorder.getCoinQuantity()*buyorder.getPrice();
                        Double fees=currencyRepository.findOneByCoinName(buyorder.getCoinName()).getFees();
                        Double grossAmount=netAmount+(netAmount*fees)/100;
                        orderDetails.setNetAmount(netAmount);
                        orderDetails.setGrossAmount(grossAmount);
                        Currency currency=currencyRepository.findOneByCoinName(buyorder.getCoinName());
                        if(currency ==null){
                            return "admin don't have currency of " + buyorder.getCoinName();
                        }
                        Double fee=currency.getFees();
                        orderDetails.setFee(currency.getFees());
                        orderDetails.setPrice(buyorder.getPrice());
                        orderDetails.setCoinQuantity(buyorder.getCoinQuantity());
                        user.getOrderDetailsList().add(orderDetails);
                        Wallet inrwallet=walletRepository.findByCoinNameAndUserId("INR",user.getId());
                        Wallet bitcoinWallet=walletRepository.findByCoinNameAndUserId("bitcoin",user.getId());
                        Wallet etherWallet=walletRepository.findByCoinNameAndUserId("ethereum",user.getId());

                        Double moneytoBeDeducted;
                        Integer amount;
                        amount=buyorder.getCoinQuantity()*buyorder.getPrice();
                        moneytoBeDeducted=amount+(amount*fee)/100;
                        if(inrwallet.getShadowBalance()<moneytoBeDeducted){
                            return "Lack of money in your account to make an order";
                        }else{
                            if(inrwallet.getBalance()-inrwallet.getShadowBalance() != 0.0) {
                                inrwallet.setShadowBalance(inrwallet.getShadowBalance() - moneytoBeDeducted);
                            }else{
                                inrwallet.setShadowBalance(inrwallet.getShadowBalance() - moneytoBeDeducted);
                            }
                        }
//                        if(buyorder.getCoinName().equalsIgnoreCase("Bitcoin")) bitcoinWallet.setShadowBalance(bitcoinWallet.getBalance()+buyorder.getCoinQuantity());
//                        if(buyorder.getCoinName().equalsIgnoreCase("ethereum")) etherWallet.setShadowBalance(etherWallet.getBalance()+buyorder.getCoinQuantity());
                        walletRepository.save(inrwallet);
                        orderRepository.save(orderDetails);
                        return "order succesfully created and waiting for transaction  to be approved";
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


    //create sell order
    public String createSellOrder(BuyAndSellOrderDto sellOrder){
        //decrease the crypto currency
        if(sellOrder.getUserId()==null) return "user id is blank";
        if(sellOrder.getCoinName()==null | sellOrder.getCoinQuantity()==null | sellOrder.getPrice()==null){
            return "one of the field is empty";
        }
        int counter=0;
        User user=userRepository.findOneById(sellOrder.getUserId());
        if(user!=null){
            if(sellOrder.getCoinName().equalsIgnoreCase("Bitcoin") | sellOrder.getCoinName().equalsIgnoreCase("Ethereum")){
                Set<Wallet> wallets=user.getWallets();
                for(Wallet existingWallet:wallets){
                    if(existingWallet.getCoinName().equalsIgnoreCase(sellOrder.getCoinName())){
                        counter=1;
                        if(existingWallet.getBalance()<sellOrder.getCoinQuantity()){
                            return "You don't have enough coins to sell";
                        }
                        OrderDetails orderDetails=new OrderDetails();
                        orderDetails.setOrderStatus(OrderStatus.PENDING);
                        orderDetails.setUser(user);
                        orderDetails.setCoinName(existingWallet.getCoinName());
                        orderDetails.setOrderCreatedOn(new Date());
                        orderDetails.setOrderType(OrderType.SELLORDER);
                        orderDetails.setPrice(sellOrder.getPrice());
                        Integer netAmount=sellOrder.getCoinQuantity()*sellOrder.getPrice();
                        orderDetails.setNetAmount(netAmount);
                        orderDetails.setGrossAmount(netAmount+0.0);
                        orderDetails.setCoinQuantity(sellOrder.getCoinQuantity());
                        user.getOrderDetailsList().add(orderDetails);
//                        Wallet inrwallet=walletRepository.findByCoinNameAndUserId("INR",user.getId());
//                        Integer amount;
//                        amount=sellOrder.getCoinQuantity()*sellOrder.getPrice();
//                        inrwallet.setShadowBalance(inrwallet.getShadowBalance()+amount);
                        Wallet cryptoWallet=walletRepository.findByCoinNameAndUserId(sellOrder.getCoinName(),user.getId());
                        cryptoWallet.setShadowBalance(cryptoWallet.getBalance()-sellOrder.getCoinQuantity());
                        walletRepository.save(cryptoWallet);
                        orderRepository.save(orderDetails);
                        return "order succesfully created and waiting for transaction to be approved";
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
