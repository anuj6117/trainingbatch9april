package com.example.demo.dto;

import com.example.demo.enums.CoinType;

public class DepositAmountDto {

    private int userId;
    private CoinType walletType;
    private Integer amount;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public CoinType getWalletType() {
        return walletType;
    }

    public void setWalletType(CoinType walletType) {
        this.walletType = walletType;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
