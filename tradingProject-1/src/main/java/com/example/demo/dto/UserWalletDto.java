package com.example.demo.dto;

import com.example.demo.enums.CoinNameEnum;
import com.example.demo.enums.CoinType;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public class UserWalletDto {
    private Integer userId;

    @Enumerated(EnumType.STRING)
    private CoinType coinType;

    private String coinName;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public CoinType getCoinType() {
        return coinType;
    }

    public void setCoinType(CoinType coinType) {
        this.coinType = coinType;
    }

    public String getCoinName() {
        return coinName;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }
}
