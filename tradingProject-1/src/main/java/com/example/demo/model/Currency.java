package com.example.demo.model;

import com.example.demo.enums.CoinType;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;

@Entity
@Table(name="Currency")
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer coinId;
    private String coinName;
    private String symbol;
    private Double fees;
    private Double initialSupply;
    private Double price;

    @Enumerated(EnumType.STRING)
    private CoinType coinType;

    private Double coinInINR;
    private Double profit;

    public Currency(){}

    public Integer getCoinId() {
        return coinId;
    }

    public void setCoinId(Integer coinId) {
        this.coinId = coinId;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Double getFees() {
        return fees;
    }

    public void setFees(Double fees) {
        this.fees = fees;
    }

    public Double getInitialSupply() {
        return initialSupply;
    }

    public void setInitialSupply(Double initialSupply) {
        this.initialSupply = initialSupply;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public CoinType getCoinType() {
        return coinType;
    }

    public void setCoinType(CoinType coinType) {
        this.coinType = coinType;
    }

    public Double getCoinInINR() {
        return coinInINR;
    }

    public void setCoinInINR(Double coinInINR) {
        this.coinInINR = coinInINR;
    }

    public Double getProfit() {
        return profit;
    }

    public void setProfit(Double profit) {
        this.profit = profit;
    }

    public String getCoinName() {
        return coinName;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }
}
