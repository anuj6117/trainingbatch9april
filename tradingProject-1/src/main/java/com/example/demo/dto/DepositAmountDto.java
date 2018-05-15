package com.example.demo.dto;

import com.example.demo.enums.CoinType;

public class DepositAmountDto {

    private int userId;
    private CoinType walletType;
    private Double amount;

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

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
