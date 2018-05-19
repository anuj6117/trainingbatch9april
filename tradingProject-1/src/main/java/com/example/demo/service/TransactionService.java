package com.example.demo.service;

import com.example.demo.enums.CoinType;
import com.example.demo.enums.OrderStatus;
import com.example.demo.enums.OrderType;
import com.example.demo.model.*;
import com.example.demo.model.Currency;
import com.example.demo.repository.*;
import com.example.demo.utilities.BuyerIncreasingPriceComparator;
import com.example.demo.utilities.SellerDecreasingPriceComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TransactionService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    WalletRepository walletRepository;

    @Autowired
    CurrencyRepository currencyRepository;

    @Autowired
    TransactionRepository transactionRepository;

    Currency adminCurrency;

    public String makeTransaction() {

        List<OrderDetails>  buyersDetails=orderRepository.findByOrderTypeAndOrderStatus(OrderType.BUYORDER,OrderStatus.PENDING);
        List<OrderDetails> sellerDetails=orderRepository.findByOrderTypeAndOrderStatus(OrderType.SELLORDER,OrderStatus.PENDING);

        if(buyersDetails.isEmpty()==true){
            return "no buyer available";
        }

        //sort the buyers in decreasing price list
        Collections.sort(buyersDetails,new BuyerIncreasingPriceComparator());
        Collections.reverse(buyersDetails);//buyersdetail with inc-->dec order
        if(sellerDetails.isEmpty()!=true)
            Collections.sort(sellerDetails,new SellerDecreasingPriceComparator()); //sellers list with dec-->inc order


        if(sellerDetails.isEmpty()!=true){
            //seller is available
            //compare seller and admin price and make transaction on the  behalf of it
            for(OrderDetails singleBuyer:buyersDetails) {
                // buyer will  not switch to next untill first buyer transaction complete
                //if first buyer transaction will complete then break from inner loop
                for (OrderDetails singleSeller : sellerDetails) {
                    if (singleBuyer.getCoinName().equalsIgnoreCase(singleSeller.getCoinName())) {
                        adminCurrency = currencyRepository.findOneByCoinName(singleSeller.getCoinName());
                        Double fee=adminCurrency.getFees();
                        String cointobedeal=singleBuyer.getCoinName();

                        if (adminCurrency.getPrice() < singleSeller.getPrice()) {
                            //deal with admin currency
                            //and make deal with the no of lesser coins of either buyer and admin
                            Integer admincurrencyQuantity = adminCurrency.getInitialSupply();
                            Integer buyerquantity = singleBuyer.getCoinQuantity();
                            if (buyerquantity < admincurrencyQuantity) {
                                //complete the transaction of buyer and break the  inner for loop
                                //update buyer inr and crypto wallet  and chnage the status to completed
                                //update admin  crypto wallet
                                System.out.println("buyer have lesser no of coins than admins");
                                Integer buyeruserId=singleBuyer.getUser().getId();
//                                Integer selleruserId=singleSeller.getUser().getId();
                                Integer finalcoinquantitiy=buyerquantity;
                                //updating buyer  crypto wallet
                                Wallet cryptoWallet= walletRepository.findByCoinNameAndUserId(cointobedeal,buyeruserId);
                                cryptoWallet.setShadowBalance(cryptoWallet.getShadowBalance()+finalcoinquantitiy);
                                cryptoWallet.setBalance(cryptoWallet.getShadowBalance());
                                walletRepository.save(cryptoWallet);

                                //updating buyer inr wallet
                                Wallet inrWallet=walletRepository.findByCoinNameAndUserId("INR",buyeruserId);
                                Integer amountdeal=finalcoinquantitiy*singleBuyer.getPrice();
                                Double taxondeal=(amountdeal*fee)/100;

                                inrWallet.setBalance(inrWallet.getShadowBalance());
                                walletRepository.save(inrWallet);

                                singleBuyer.setOrderStatus(OrderStatus.COMPLETED);
                                orderRepository.save(singleBuyer);


                                //update admin coinininr and profit and dec its supply

                                adminCurrency.setCoinInINR(adminCurrency.getCoinInINR() + amountdeal);
                                adminCurrency.setProfit(adminCurrency.getProfit()+taxondeal);
                                adminCurrency.setInitialSupply(adminCurrency.getInitialSupply()-finalcoinquantitiy);

                                currencyRepository.save(adminCurrency);
//                                userRepository.save(singleBuyer.getUser());

                                // add the transaction details object so transaction can be showed in table
                                TransactionDetails transactionDetails=new TransactionDetails( finalcoinquantitiy, cointobedeal, OrderStatus.COMPLETED, new Date(), amountdeal, taxondeal, amountdeal, amountdeal+taxondeal, singleBuyer.getUser().getId(),null, "Transaction successful with admin");
                                transactionRepository.save(transactionDetails);
                                return "Transaction succesfull";
//                                break;   //break from inner for loop


                            } else {
                                // deal with whatever coins admins have and update the coins in buyer
                                System.out.println("admins have lesser no of coins than buyer coins");
                                Integer finalcoinquantity=adminCurrency.getInitialSupply();
                                Integer buyeruserId=singleBuyer.getUser().getId();

                                //updating buyer  crypto wallet
                                Wallet cryptoWallet= walletRepository.findByCoinNameAndUserId(cointobedeal,buyeruserId);
                                cryptoWallet.setShadowBalance(cryptoWallet.getBalance()+finalcoinquantity);
                                cryptoWallet.setBalance(cryptoWallet.getShadowBalance());
                                walletRepository.save(cryptoWallet);

                                //updating buyer inr wallet
                                Wallet inrWallet=walletRepository.findByCoinNameAndUserId("INR",buyeruserId);
                                Integer amountdeal=finalcoinquantity*singleBuyer.getPrice();
                                Double taxondeal=(amountdeal*fee)/100;
                                Double totalamountdedeductedAfterTax=amountdeal+taxondeal;
                                inrWallet.setShadowBalance(inrWallet.getBalance()-totalamountdedeductedAfterTax);
                                inrWallet.setBalance(inrWallet.getShadowBalance());
                                walletRepository.save(inrWallet);

                                singleBuyer.setOrderStatus(OrderStatus.PENDING);
                                singleBuyer.setCoinQuantity(singleBuyer.getCoinQuantity()-finalcoinquantity);
                                orderRepository.save(singleBuyer);

                                //update admin coinininr and profit and dec its supply

                                adminCurrency.setCoinInINR(adminCurrency.getCoinInINR() + amountdeal);
                                adminCurrency.setProfit(adminCurrency.getProfit()+taxondeal);
                                adminCurrency.setInitialSupply(adminCurrency.getInitialSupply()-finalcoinquantity);

                                currencyRepository.save(adminCurrency);
                                TransactionDetails transactionDetails=new TransactionDetails(finalcoinquantity, cointobedeal, OrderStatus.COMPLETED, new Date(), amountdeal, taxondeal, amountdeal, totalamountdedeductedAfterTax, singleBuyer.getUser().getId(), null, "Transaction succesfull with admin");
                                transactionRepository.save(transactionDetails);
                                return "Transaction succesful";
//                                userRepository.save(singleBuyer.getUser());

// add transaction details to show transaction in transaction table
                            }
                        } else {
                            //deal with seller and with lesser no of coins
                            Integer sellercoinQuantity = singleSeller.getCoinQuantity();
                            Integer buyerCoinQuantity = singleBuyer.getCoinQuantity();
                            Integer buyeruserid=singleBuyer.getUser().getId();
                            Integer selleruserid=singleSeller.getUser().getId();
                            if(singleBuyer.getPrice() < singleSeller.getPrice()){
                                return "Transaction unsuccesful";
                            }

                            if (buyerCoinQuantity < sellercoinQuantity) {
                                //update the buyer coins and exist from inner for looop
                                //update buyer inr wallet n crypto wallet
                                //update seller inr n crypto wallet
                                //update the profit in admin currency with the deal
                                //change orderstatus to complete and break from inner loop


                                Integer finalCoinQuantity=buyerCoinQuantity;
//                                Integer buyeruserid=singleBuyer.getUser().getId();
//                                Integer selleruserid=singleSeller.getUser().getId();

                                //buyer crypto wallet
                                Wallet cryptowallet=walletRepository.findByCoinNameAndUserId(cointobedeal,buyeruserid);
                                cryptowallet.setShadowBalance(cryptowallet.getShadowBalance()+finalCoinQuantity);
                                cryptowallet.setBalance(cryptowallet.getShadowBalance());
                                walletRepository.save(cryptowallet);
                                //bueyr inr wallet
                                Wallet inrwallet=walletRepository.findByCoinNameAndUserId("INR",buyeruserid);
                                inrwallet.setBalance(inrwallet.getShadowBalance());
                                walletRepository.save(inrwallet);

                                singleBuyer.setOrderStatus(OrderStatus.COMPLETED);
                                orderRepository.save(singleBuyer);


                                //seller crypto wallet
                                Wallet sellercryptoWallet=walletRepository.findByCoinNameAndUserId(cointobedeal,selleruserid);
                                sellercryptoWallet.setShadowBalance(sellercryptoWallet.getBalance()-finalCoinQuantity);
                                sellercryptoWallet.setBalance(sellercryptoWallet.getShadowBalance());
                                walletRepository.save(sellercryptoWallet);

                                //seller inr wallet
                                Wallet sellerinrwallet=walletRepository.findByCoinNameAndUserId("INR",selleruserid);
                                sellerinrwallet.setShadowBalance(sellerinrwallet.getBalance()+finalCoinQuantity*singleSeller.getPrice());
                                sellerinrwallet.setBalance(sellerinrwallet.getShadowBalance());
                                walletRepository.save(sellerinrwallet);

                                singleSeller.setOrderStatus(OrderStatus.PENDING);
                                singleSeller.setCoinQuantity(singleSeller.getCoinQuantity()-finalCoinQuantity);
                                orderRepository.save(singleSeller);

                                Integer amountdeal=finalCoinQuantity*singleBuyer.getPrice();
                                Double taxondeal=(amountdeal*fee)/100;
                                Integer differnceofamountbetweensellernbuyer=amountdeal-finalCoinQuantity*singleSeller.getPrice();


                                //update admin currency
                                adminCurrency.setCoinInINR(adminCurrency.getCoinInINR()+differnceofamountbetweensellernbuyer);
                                adminCurrency.setProfit(adminCurrency.getProfit()+taxondeal);
                                currencyRepository.save(adminCurrency);
//                                userRepository.save(singleBuyer.getUser());
//                                userRepository.save(singleSeller.getUser());
                                TransactionDetails transactionDetails=new TransactionDetails( finalCoinQuantity, cointobedeal, OrderStatus.COMPLETED, new Date(), amountdeal, taxondeal, differnceofamountbetweensellernbuyer, amountdeal+taxondeal, singleBuyer.getUser().getId(),singleSeller.getUser().getId(), "Transaction succefull");
                                transactionRepository.save(transactionDetails);
                                return "Transaction succesful";


//add transaction detail to transaction table
//                                break;  //as buyer demand finish

                            } else if(buyerCoinQuantity>sellercoinQuantity) {
                                System.out.println("buyer have more no of coins than seller");
                                //deal with whatever coins seller have and update the coins in both buyer and seller
                                Integer finalCoinQuantity=sellercoinQuantity;

                                //buyer crypto wallet
                                Wallet cryptowallet=walletRepository.findByCoinNameAndUserId(cointobedeal,buyeruserid);
                                cryptowallet.setShadowBalance(cryptowallet.getBalance()+finalCoinQuantity);
                                cryptowallet.setBalance(cryptowallet.getShadowBalance());
                                walletRepository.save(cryptowallet);
                                //buyer inr wallet
                                Wallet inrwallet=walletRepository.findByCoinNameAndUserId("INR",buyeruserid);
                                Integer amountdeal=finalCoinQuantity*singleBuyer.getPrice();
                                Double taxondeal=(amountdeal*fee)/100;
                                Double finalamountdeducted=amountdeal+taxondeal;
                                Integer differnceofamountbetweensellernbuyer=amountdeal-finalCoinQuantity*singleSeller.getPrice();

                                inrwallet.setShadowBalance(inrwallet.getBalance()-finalamountdeducted);
                                inrwallet.setBalance(inrwallet.getShadowBalance());
                                walletRepository.save(inrwallet);

                                singleBuyer.setOrderStatus(OrderStatus.PENDING);
                                singleBuyer.setCoinQuantity(singleBuyer.getCoinQuantity()-finalCoinQuantity);
                                orderRepository.save(singleBuyer);


                                //seller inr and crypto wallet update
                                Wallet sellercryptowallet=walletRepository.findByCoinNameAndUserId(cointobedeal,selleruserid);
                                sellercryptowallet.setBalance(sellercryptowallet.getShadowBalance());
                                walletRepository.save(sellercryptowallet);

                                Wallet sellerinrwallet=walletRepository.findByCoinNameAndUserId("INR",selleruserid);
                                sellerinrwallet.setShadowBalance(sellerinrwallet.getBalance()+finalCoinQuantity*singleSeller.getPrice());
                                sellerinrwallet.setBalance(sellerinrwallet.getShadowBalance());
                                walletRepository.save(sellerinrwallet);


                                singleSeller.setOrderStatus(OrderStatus.COMPLETED);
                                orderRepository.save(singleSeller);
                                //admin currency change
                                adminCurrency.setCoinInINR(adminCurrency.getCoinInINR()+differnceofamountbetweensellernbuyer);
                                adminCurrency.setProfit(adminCurrency.getProfit()+taxondeal);
                                currencyRepository.save(adminCurrency);
//                                singleSeller.setOrderStatus(OrderStatus.COMPLETED);
//                                userRepository.save(singleBuyer.getUser());
//                                userRepository.save(singleSeller.getUser());
//save transaction detail to table
                                TransactionDetails transactionDetails=new TransactionDetails( finalCoinQuantity, cointobedeal, OrderStatus.COMPLETED, new Date(), amountdeal, taxondeal, differnceofamountbetweensellernbuyer, amountdeal+taxondeal, singleBuyer.getUser().getId(), singleSeller.getUser().getId(), "Transaction succesfull");
                                transactionRepository.save(transactionDetails);
                                return "Transaction successful";
                            }
                            else{
                                Integer finalcoinquantitiy=singleBuyer.getCoinQuantity();
Currency admincurrency=currencyRepository.findOneByCoinName(cointobedeal);
 fee=admincurrency.getFees();
                                //bueyr inr and crypto wallet

                                Wallet cryptowallet=walletRepository.findByCoinNameAndUserId(cointobedeal,singleBuyer.getUser().getId());
                                cryptowallet.setShadowBalance(cryptowallet.getBalance()+finalcoinquantitiy);
                                cryptowallet.setBalance(cryptowallet.getShadowBalance());
                                walletRepository.save(cryptowallet);

                                Wallet inrwallet=walletRepository.findByCoinNameAndUserId("INR",singleBuyer.getUser().getId());
                                inrwallet.setBalance(inrwallet.getShadowBalance());
                                walletRepository.save(inrwallet);

                                singleBuyer.setOrderStatus(OrderStatus.COMPLETED);
                                orderRepository.save(singleBuyer);

                                //buyer inr and crypto wallet
                                Wallet sellercryptowallet=walletRepository.findByCoinNameAndUserId(cointobedeal,singleSeller.getUser().getId());
                                sellercryptowallet.setBalance(sellercryptowallet.getShadowBalance());
                                walletRepository.save(sellercryptowallet);

                                Wallet sellerinrWallet=walletRepository.findByCoinNameAndUserId("INR",singleSeller.getUser().getId());
                                sellerinrWallet.setShadowBalance(sellerinrWallet.getBalance()+finalcoinquantitiy*singleSeller.getPrice());
                                sellerinrWallet.setBalance(sellerinrWallet.getShadowBalance());
                                walletRepository.save(sellerinrWallet);

                                singleSeller.setOrderStatus(OrderStatus.COMPLETED);
                                orderRepository.save(singleSeller);

                                Integer amountdeal=finalcoinquantitiy*singleBuyer.getPrice();
                                Double taxondeal=(amountdeal*fee)/100;
                                Double amountaftertax=amountdeal+taxondeal;
                                Integer differnceinamount=amountdeal-finalcoinquantitiy*singleSeller.getPrice();

                                admincurrency.setCoinInINR(admincurrency.getCoinInINR()+differnceinamount);
                                admincurrency.setProfit(admincurrency.getProfit()+taxondeal);
                                currencyRepository.save(admincurrency);


                                TransactionDetails transactionDetails=new TransactionDetails( finalcoinquantitiy, cointobedeal, OrderStatus.COMPLETED, new Date(), amountdeal, taxondeal, differnceinamount, amountaftertax, singleBuyer.getUser().getId(), singleSeller.getUser().getId(), "Transaction succesfull");
                                transactionRepository.save(transactionDetails);
                                return "Transacton succesful";
                            }
                        }
                    }
                }
            }
        }else{
            //deal with admin currency and make sure that admin price is less than buyer price
            int counter=0;
            for(OrderDetails singlebuyer:buyersDetails){
                Currency admincurrency=currencyRepository.findOneByCoinName(singlebuyer.getCoinName());
                String cointobedeal=singlebuyer.getCoinName();
                if(admincurrency != null){
                    if(admincurrency.getPrice() < singlebuyer.getPrice()){
                        if(admincurrency.getInitialSupply() > singlebuyer.getCoinQuantity()){
                            //mke a deal and sell the buyer status completed after transaction done
                            Integer finalcoinquantity=singlebuyer.getCoinQuantity();

                            //make change of inr and crypto wallet
                            Wallet buyerinrwallet=walletRepository.findByCoinNameAndUserId("INR",singlebuyer.getUser().getId());
                            buyerinrwallet.setBalance(buyerinrwallet.getShadowBalance());
                            walletRepository.save(buyerinrwallet);

                            Wallet buyercryptowalelt=walletRepository.findByCoinNameAndUserId(cointobedeal,singlebuyer.getUser().getId());
                            buyercryptowalelt.setShadowBalance(buyercryptowalelt.getShadowBalance()+finalcoinquantity);
                            buyercryptowalelt.setBalance(buyercryptowalelt.getShadowBalance());
                            walletRepository.save(buyercryptowalelt);
                            singlebuyer.setOrderStatus(OrderStatus.COMPLETED);

                            orderRepository.save(singlebuyer);
                            //sub currency frm admin wallet and add money and profit in admin coin in  inr
                            Integer amountDeal=finalcoinquantity*singlebuyer.getPrice();
                            Double profit=(amountDeal*admincurrency.getFees())/100;

                            admincurrency.setCoinInINR(admincurrency.getCoinInINR()+amountDeal);
                            admincurrency.setProfit(admincurrency.getProfit()+profit);
                            admincurrency.setInitialSupply(admincurrency.getInitialSupply()-finalcoinquantity);
                            currencyRepository.save(admincurrency);
                            TransactionDetails transactionDetails=new TransactionDetails( finalcoinquantity,cointobedeal, OrderStatus.COMPLETED, new Date(), amountDeal, profit, amountDeal, amountDeal+profit, singlebuyer.getUser().getId(),null, "Transaction succesfull");
                            transactionRepository.save(transactionDetails);
                            return "Transaction succesfull";
                        }else{
                            //deal with whatever coins admin have and make coin less in buyer status and make status pending
                            Integer finalcoinQuantity=admincurrency.getInitialSupply();
                            Integer amountdeal=finalcoinQuantity*singlebuyer.getPrice();
                            Double profit=(amountdeal*admincurrency.getFees())/100;
                            Double taxincludedamount=amountdeal+profit;

                            //buyer inr waller
                            Wallet buyerinrwallet=walletRepository.findByCoinNameAndUserId("INR",singlebuyer.getUser().getId());
                            buyerinrwallet.setShadowBalance(buyerinrwallet.getBalance()-taxincludedamount);
                            buyerinrwallet.setBalance(buyerinrwallet.getShadowBalance());
                            walletRepository.save(buyerinrwallet);

                            //buyer crypto wallet
                            Wallet buyercryptowallet=walletRepository.findByCoinNameAndUserId(cointobedeal,singlebuyer.getUser().getId());
                            buyercryptowallet.setShadowBalance(buyercryptowallet.getShadowBalance()+finalcoinQuantity);
                            buyercryptowallet.setBalance(buyercryptowallet.getShadowBalance());
                            walletRepository.save(buyercryptowallet);

                            singlebuyer.setOrderStatus(OrderStatus.PENDING);
                            singlebuyer.setCoinQuantity(singlebuyer.getCoinQuantity()-finalcoinQuantity);
                            orderRepository.save(singlebuyer);

                            //admin currency
                            admincurrency.setCoinInINR(admincurrency.getCoinInINR()+singlebuyer.getPrice()*finalcoinQuantity);
                            admincurrency.setProfit(admincurrency.getProfit()+profit);
                            admincurrency.setInitialSupply(admincurrency.getInitialSupply()-finalcoinQuantity);
                            currencyRepository.save(admincurrency);
                            TransactionDetails transactionDetails=new  TransactionDetails(finalcoinQuantity, cointobedeal, OrderStatus.COMPLETED, new Date(), amountdeal, profit, amountdeal, taxincludedamount, singlebuyer.getUser().getId(), null, "Transaction succesfull with admin");
                            transactionRepository.save(transactionDetails);
                            return "Transaction succesfull";
                        }
                    }else{
                        counter=1;
                    }
                }else{
                    return "Transaction unsuccesful";
                }
            }
            if(counter==1){
                return "Transaction unsuccesful";
            }
        }
        return "Transaction succesful";
    }





    public List<TransactionDetails> getallTransactions(){
        return  transactionRepository.findAll();
    }
}
