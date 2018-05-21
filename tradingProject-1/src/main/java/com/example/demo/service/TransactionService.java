package com.example.demo.service;

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
        Integer sellerElements;

        if(buyersDetails.isEmpty()==true){ return "no buyer available"; }

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
                int counter=0;
                sellerDetails=orderRepository.findByOrderTypeAndOrderStatus(OrderType.SELLORDER,OrderStatus.PENDING);
                Collections.sort(sellerDetails,new SellerDecreasingPriceComparator()); //sellers list with dec-->inc order
                sellerElements=sellerDetails.size();
                for (OrderDetails singleSeller : sellerDetails) {
                    if (singleBuyer.getCoinName().equalsIgnoreCase(singleSeller.getCoinName())) {
                        adminCurrency = currencyRepository.findOneByCoinName(singleSeller.getCoinName());
                        Double fee=adminCurrency.getFees();
                        String cointobedeal=singleBuyer.getCoinName();

                        if (adminCurrency.getPrice() < singleSeller.getPrice()) {
                            //deal with admin currency and make deal with the no of lesser coins of either buyer and admin
                            Integer admincurrencyQuantity = adminCurrency.getInitialSupply();
                            Integer buyerquantity = singleBuyer.getCoinQuantity();
                            if (buyerquantity < admincurrencyQuantity) {
                                //complete the transaction of buyer and break the  inner for loop
                                //update buyer inr and crypto wallet  and chnage the status to completed
                                //update admin  crypto wallet
                                System.out.println("buyer have lesser no of coins than admins");
                                Integer finalcoinquantitiy=buyerquantity;
                                buyerQuantityIsLesser(cointobedeal,finalcoinquantitiy,singleBuyer);

                                Integer amountdeal=finalcoinquantitiy*singleBuyer.getPrice();
                                Double taxondeal=(amountdeal*fee)/100;
                                setAdminCurrencyValue( amountdeal, taxondeal, finalcoinquantitiy,"admin",adminCurrency);
                                setValuesInTransactionTable(finalcoinquantitiy, cointobedeal, OrderStatus.COMPLETED, amountdeal, taxondeal, amountdeal, amountdeal+taxondeal, singleBuyer,null, "Transaction successful with admin");
                                break;
                            } else {
                                // deal with whatever coins admins have and update the coins in buyer
                                System.out.println("admins have lesser no of coins than buyer coins");
                                Integer finalcoinquantity=adminCurrency.getInitialSupply();

                                //updating buyer  crypto wallet
                                Integer amountdeal=finalcoinquantity*singleBuyer.getPrice();
                                Double taxondeal=(amountdeal*fee)/100;
                                Double totalamountdedeductedAfterTax=amountdeal+taxondeal;

                                buyerQuantityIsGreater(cointobedeal,singleBuyer,finalcoinquantity,fee);
                                //update admin coinininr and profit and dec its supply
                                setAdminCurrencyValue(amountdeal,taxondeal,finalcoinquantity,"admin",adminCurrency);
                                setValuesInTransactionTable(finalcoinquantity, cointobedeal, OrderStatus.COMPLETED, amountdeal, taxondeal, amountdeal, totalamountdedeductedAfterTax, singleBuyer, null, "Transaction succesfull with admin");
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
                                Integer finalCoinQuantity=buyerCoinQuantity;
                                buyerQuantityIsLesser(cointobedeal,finalCoinQuantity,singleBuyer);
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
                                setAdminCurrencyValue(differnceofamountbetweensellernbuyer,taxondeal,0,"seller",adminCurrency);
                                setValuesInTransactionTable(finalCoinQuantity, cointobedeal, OrderStatus.COMPLETED, amountdeal, taxondeal, differnceofamountbetweensellernbuyer, amountdeal+taxondeal, singleBuyer,singleSeller, "Transaction succefull");
                                break;
                            } else if(buyerCoinQuantity>sellercoinQuantity) {
                                System.out.println("buyer have more no of coins than seller");
                                //deal with whatever coins seller have and update the coins in both buyer and seller
                                Integer finalCoinQuantity=sellercoinQuantity;

                                //buyer crypto wallet
                                Integer amountdeal=finalCoinQuantity*singleBuyer.getPrice();
                                Double taxondeal=(amountdeal*fee)/100;
                                Integer differnceofamountbetweensellernbuyer=amountdeal-finalCoinQuantity*singleSeller.getPrice();
                                buyerQuantityIsGreater(cointobedeal,singleBuyer,finalCoinQuantity,fee);

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

                                setAdminCurrencyValue(differnceofamountbetweensellernbuyer,taxondeal,0,"seller",adminCurrency);
                                setValuesInTransactionTable(finalCoinQuantity, cointobedeal, OrderStatus.COMPLETED, amountdeal, taxondeal, differnceofamountbetweensellernbuyer, amountdeal+taxondeal, singleBuyer, singleSeller, "Transaction succesfull");
                            }
                            else{
                                Integer finalcoinquantitiy=singleBuyer.getCoinQuantity();
                                Currency admincurrency=currencyRepository.findOneByCoinName(cointobedeal);
                                fee=admincurrency.getFees();
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
                            }
                        }
                    }else{
                        counter++;
                    }
                }
                if(counter==sellerElements){
                    //deal with admin
                    dealWithAdmin(buyersDetails);
                }
            }
        }else{
            //deal with admin currency and make sure that admin price is less than buyer price
            dealWithAdmin(buyersDetails);
        }
        return "Transaction succesful";
    }


    public void buyerQuantityIsLesser( String cointobedeal,Integer finalcoinquantitiy, OrderDetails singleBuyer){
        Integer buyeruserId=singleBuyer.getUser().getId();
        Wallet cryptoWallet= walletRepository.findByCoinNameAndUserId(cointobedeal,buyeruserId);
        cryptoWallet.setShadowBalance(cryptoWallet.getShadowBalance()+finalcoinquantitiy);
        cryptoWallet.setBalance(cryptoWallet.getShadowBalance());
        walletRepository.save(cryptoWallet);

        //updating buyer inr wallet
        Wallet inrWallet=walletRepository.findByCoinNameAndUserId("INR",buyeruserId);
        inrWallet.setBalance(inrWallet.getShadowBalance());
        walletRepository.save(inrWallet);

        singleBuyer.setOrderStatus(OrderStatus.COMPLETED);
        orderRepository.save(singleBuyer);
    }


    public void buyerQuantityIsGreater(String cointobedeal,OrderDetails singleBuyer,Integer finalCoinQuantity,Double fee){
        Integer buyeruserid=singleBuyer.getUser().getId();
        Wallet cryptowallet=walletRepository.findByCoinNameAndUserId(cointobedeal,buyeruserid);
        cryptowallet.setShadowBalance(cryptowallet.getBalance()+finalCoinQuantity);
        cryptowallet.setBalance(cryptowallet.getShadowBalance());
        walletRepository.save(cryptowallet);
        //buyer inr wallet
        Wallet inrwallet=walletRepository.findByCoinNameAndUserId("INR",buyeruserid);
        Integer amountdeal=finalCoinQuantity*singleBuyer.getPrice();
        Double taxondeal=(amountdeal*fee)/100;
        Double finalamountdeducted=amountdeal+taxondeal;

        inrwallet.setShadowBalance(inrwallet.getBalance()-finalamountdeducted);
        inrwallet.setBalance(inrwallet.getShadowBalance());
        walletRepository.save(inrwallet);

        singleBuyer.setOrderStatus(OrderStatus.PENDING);
        singleBuyer.setCoinQuantity(singleBuyer.getCoinQuantity()-finalCoinQuantity);
        orderRepository.save(singleBuyer);
    }


    public String dealWithAdmin( List<OrderDetails>  buyersDetails){
        int counter=0;
        List<OrderDetails> pendingBuyers=new ArrayList<>();
        for(OrderDetails order:buyersDetails){
            if(order.getOrderStatus().equals(OrderStatus.PENDING)){
                pendingBuyers.add(order);
            }
        }
        //sort the buyers in decreasing price list
        Collections.sort(pendingBuyers,new BuyerIncreasingPriceComparator());
        Collections.reverse(pendingBuyers);
        for(OrderDetails singlebuyer:pendingBuyers){
            Currency admincurrency=currencyRepository.findOneByCoinName(singlebuyer.getCoinName());
            String cointobedeal=singlebuyer.getCoinName();
            if(admincurrency != null){
                if(admincurrency.getPrice() < singlebuyer.getPrice()){
                    if(admincurrency.getInitialSupply() > singlebuyer.getCoinQuantity()){
                        //mke a deal and sell the buyer status completed after transaction done
                        Integer finalcoinquantity=singlebuyer.getCoinQuantity();
                        //make change of inr and crypto wallet
                        buyerQuantityIsLesser(cointobedeal,finalcoinquantity,singlebuyer);
                        //sub currency frm admin wallet and add money and profit in admin coin in  inr
                        Integer amountDeal=finalcoinquantity*singlebuyer.getPrice();
                        Double profit=(amountDeal*admincurrency.getFees())/100;

                        setAdminCurrencyValue(amountDeal,profit,finalcoinquantity,"admin",admincurrency);
                        setValuesInTransactionTable(finalcoinquantity,cointobedeal, OrderStatus.COMPLETED, amountDeal, profit, amountDeal, amountDeal+profit, singlebuyer,null, "Transaction succesfull with admin");
                        break;
                    }else{
                        //deal with whatever coins admin have and make coin less in buyer status and make status pending
                        Integer finalcoinQuantity=admincurrency.getInitialSupply();
                        Integer amountdeal=finalcoinQuantity*singlebuyer.getPrice();
                        Double profit=(amountdeal*admincurrency.getFees())/100;
                        Double taxincludedamount=amountdeal+profit;

                        //buyer inr waller
                        buyerQuantityIsGreater(cointobedeal,singlebuyer,finalcoinQuantity,admincurrency.getFees());

                        //admin currency
                        setAdminCurrencyValue(singlebuyer.getPrice()*finalcoinQuantity,profit,finalcoinQuantity,"admin",admincurrency);
                        TransactionDetails transactionDetails=new  TransactionDetails(finalcoinQuantity, cointobedeal, OrderStatus.COMPLETED, new Date(), amountdeal, profit, amountdeal, taxincludedamount, singlebuyer.getUser().getId(), null, "Transaction succesfull with admin");
                        transactionRepository.save(transactionDetails);
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
        return "Transaction unsuccesful";
    }


    public void setAdminCurrencyValue(Integer amountdeal,Double taxondeal,Integer finalcoinquantitiy,String dealer,Currency adminCurrency){
        if(dealer.equalsIgnoreCase("admin")){
            adminCurrency.setInitialSupply(adminCurrency.getInitialSupply()-finalcoinquantitiy);
        }
        adminCurrency.setCoinInINR(adminCurrency.getCoinInINR() + amountdeal);
        adminCurrency.setProfit(adminCurrency.getProfit()+taxondeal);
        currencyRepository.save(adminCurrency);
    }


    public void setValuesInTransactionTable(Integer finalcoinquantitiy, String cointobedeal,OrderStatus orderStatus, Integer amountdeal, Double taxondeal,Integer exchangerate
            , Double grossAmount, OrderDetails singlebuyer,OrderDetails singleSelelr, String description){
        TransactionDetails transactionDetails=null;
        if(singleSelelr != null){
            Integer sellerid= singleSelelr.getUser().getId();
            transactionDetails=new TransactionDetails(finalcoinquantitiy,cointobedeal,orderStatus,new Date(),amountdeal,taxondeal,exchangerate,grossAmount,singlebuyer.getUser().getId(),sellerid,description);
        }else{
            transactionDetails=new TransactionDetails(finalcoinquantitiy,cointobedeal,orderStatus,new Date(),amountdeal,taxondeal,exchangerate,grossAmount,singlebuyer.getUser().getId(),null,description);
        }
        transactionRepository.save(transactionDetails);
    }

    public List<TransactionDetails> getallTransactions(){
        return  transactionRepository.findAll();
    }
}
