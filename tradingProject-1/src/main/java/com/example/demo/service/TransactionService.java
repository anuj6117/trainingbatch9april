package com.example.demo.service;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TransactionService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    WalletRepository walletRepository;


    @Autowired
    CurrencyRepository currencyRepository;

    public String makeTransaction() {

        List<OrderDetails> bitcoinBuyers = orderRepository.findByOrderTypeAndCoinName(OrderType.BUYORDER, "bitcoin");
        List<OrderDetails> bitcoinSellers = orderRepository.findByOrderTypeAndCoinName(OrderType.SELLORDER, "bitcoin");
        Integer maxprice = -1;
        Integer minPrice = 2147483647;
        OrderDetails maxPriceBuyerUser;
        OrderDetails minPriceSellerUser;
        //        if(buyerOrders!=null) {
//            int counter=0;
//            Integer maxPrice;
//            for (OrderDetails singlebuyerDetail : buyerOrders) {
//                if (singlebuyerDetail.getCoinName().equalsIgnoreCase("bitcoin")) {
                    /*Integer coinQuantity = singlebuyerDetail.getCoinQuantity();
                    Integer userId = singlebuyerDetail.getUser().getId();
                    Integer amount = singlebuyerDetail.getPrice() * singlebuyerDetail.getCoinQuantity();
                    if (bitcoinbuyersMap.containsKey(coinQuantity)) {
                        Map<Integer, Integer> userIdWithPrice = new HashMap<>();
                        userIdWithPrice.put(userId, amount);
                        bitcoinbuyersMap.get(coinQuantity).add(userIdWithPrice);
                    } else {
                        List<Map<Integer, Integer>> differentPriceList = new ArrayList<>();
                        Map<Integer, Integer> userId_Price = new HashMap<>();
                        userId_Price.put(userId, amount);
                        differentPriceList.add(userId_Price);
                        bitcoinbuyersMap.put(coinQuantity, differentPriceList);
                    }

                    //admins control
                    if(bitcoinadminsMap.containsKey(coinQuantity)){
                        bitcoinadminsMap.get(coinQuantity).add(amount);
                    }else{
                        List<Integer> priceList=new ArrayList<>();
                        priceList.add(amount);
                        bitcoinadminsMap.put(coinQuantity,priceList);
                    }*/

        //seller control

        if (bitcoinBuyers != null) {
            for (OrderDetails bitcoinbuyerorders : bitcoinBuyers) {
                if (bitcoinbuyerorders.getOrderStatus() != OrderStatus.COMPLETED) {
                    Integer price = bitcoinbuyerorders.getPrice();
                    if (price > maxprice) {
                        maxprice = price;
                    }
                }
            }
            maxPriceBuyerUser = orderRepository.findOneByPriceAndOrderType(maxprice, OrderType.BUYORDER);
        } else {
            return "no bitcoin buyer available right now";
        }

        if (bitcoinSellers != null) {
            for (OrderDetails bitcoinSellerOrders : bitcoinSellers) {
                Integer price = bitcoinSellerOrders.getPrice();
                if (price < minPrice) {
                    minPrice = price;
                }
            }
            minPriceSellerUser = orderRepository.findOneByPriceAndOrderType(minPrice, OrderType.SELLORDER);

        } else {
            return "no bitcoin seller found";
        }
//       Currency currency= currencyRepository.findOneByCoinName("bitcoin");
//       if(currency.getInitialSupply() > maxPriceBuyerUser.getCoinQuantity()){
//
//       }
//        }


            if (maxPriceBuyerUser.getCoinQuantity() < minPriceSellerUser.getCoinQuantity()) {
                Integer coinQuantity = maxPriceBuyerUser.getCoinQuantity();
                User buyer = userRepository.findOneById(maxPriceBuyerUser.getUser().getId());
                Wallet buyerInrWallet = walletRepository.findByCoinNameAndUserId("INR", buyer.getId());
                buyerInrWallet.setBalance(buyerInrWallet.getShadowBalance());
                Wallet buyerbitcoinWallet = walletRepository.findByCoinNameAndUserId("bitcoin", buyer.getId());
                buyerbitcoinWallet.setBalance(buyerbitcoinWallet.getShadowBalance());
//          OrderDetails approvedorder=orderRepository.findOneByPriceAndOrderType(maxPriceBuyerUser.getPrice(),OrderType.BUYORDER);
                List<OrderDetails> ordersList = buyer.getOrderDetailsList();
                for (OrderDetails orders : ordersList) {
                    if (orders.getCoinName().equalsIgnoreCase("bitcoin")) {
                        orders.setOrderStatus(OrderStatus.COMPLETED);
                    }
                }
//        approvedorder.setOrderStatus(OrderStatus.COMPLETED);
//          orderRepository.save(approvedorder);
                userRepository.save(buyer);

                User seller = userRepository.findOneById(minPriceSellerUser.getUser().getId());
                Wallet sellerinrWallet = walletRepository.findByCoinNameAndUserId("INR", seller.getId());
                sellerinrWallet.setBalance(sellerinrWallet.getShadowBalance());
                Wallet sellerbitcoinwallet = walletRepository.findByCoinNameAndUserId("bitcoin", seller.getId());
                sellerbitcoinwallet.setBalance((sellerbitcoinwallet.getShadowBalance()));
                List<OrderDetails> sellerordersList = seller.getOrderDetailsList();
                for (OrderDetails ordres : sellerordersList) {
                    if (ordres.getCoinName().equalsIgnoreCase("bitcoin")) {
                        ordres.setCoinQuantity(ordres.getCoinQuantity() - coinQuantity);
                    }
                }

                userRepository.save(seller);
            }

        return null;
        }
}
