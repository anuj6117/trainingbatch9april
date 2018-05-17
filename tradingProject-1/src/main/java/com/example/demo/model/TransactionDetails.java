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

    private  Integer coinQuantity;
    private String coinName;
    private OrderStatus status;
    private Date transactionCompletedOn;
    private Double netAmount;
    private  Double transationFee;
    private  Double exchangeRate;
    private Double grossAmount;
    private Integer buyerId;
    private Integer sellerId;
    private String description;

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

    public String getCoinName() {
        return coinName;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Date getTransactionCompletedOn() {
        return transactionCompletedOn;
    }

    public void setTransactionCompletedOn(Date transactionCompletedOn) {
        this.transactionCompletedOn = transactionCompletedOn;
    }

    public Double getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(Double netAmount) {
        this.netAmount = netAmount;
    }

    public Double getTransationFee() {
        return transationFee;
    }

    public void setTransationFee(Double transationFee) {
        this.transationFee = transationFee;
    }

    public Double getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(Double exchangeRate) {
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
