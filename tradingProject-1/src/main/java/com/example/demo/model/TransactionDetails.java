package com.example.demo.model;


import com.example.demo.enums.OrderStatus;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="transactions")
public class TransactionDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer transactionId;

    private Integer coinQuantity;
    private String cointype;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    private Date transactionCreatedOn;
    private Integer netAmount;
    private Double transationfee;
    private Integer exchangeRate;
    private Double grossAmount;
    private Integer buyerId;
    private Integer sellerId;
    private String description;


    public TransactionDetails( Integer coinQuantity, String cointype, OrderStatus status, Date transactionCreatedOn, Integer netAmount, Double transationfee, Integer exchangeRate, Double grossAmount, Integer buyerId, Integer sellerId, String description) {
        this.coinQuantity = coinQuantity;
        this.cointype = cointype;
        this.status = status;
        this.transactionCreatedOn = transactionCreatedOn;
        this.netAmount = netAmount;
        this.transationfee = transationfee;
        this.exchangeRate = exchangeRate;
        this.grossAmount = grossAmount;
        this.buyerId = buyerId;
        this.sellerId = sellerId;
        this.description = description;
    }

    public TransactionDetails(){}
    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public Integer getCoinQuantity() {
        return coinQuantity;
    }

    public void setCoinQuantity(Integer coinQuantity) {
        this.coinQuantity = coinQuantity;
    }

    public String getCointype() {
        return cointype;
    }

    public void setCointype(String cointype) {
        this.cointype = cointype;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Date getTransactionCreatedOn() {
        return transactionCreatedOn;
    }

    public void setTransactionCreatedOn(Date transactionCreatedOn) {
        this.transactionCreatedOn = transactionCreatedOn;
    }

    public Integer getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(Integer netAmount) {
        this.netAmount = netAmount;
    }

    public Double getTransationfee() {
        return transationfee;
    }

    public void setTransationfee(Double transationfee) {
        this.transationfee = transationfee;
    }

    public Integer getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(Integer exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public Double getGrossAmount() {
        return grossAmount;
    }

    public void setGrossAmount(Double grossAmount) {
        this.grossAmount = grossAmount;
    }

    public Integer getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Integer buyerId) {
        this.buyerId = buyerId;
    }

    public Integer getSellerId() {
        return sellerId;
    }

    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
