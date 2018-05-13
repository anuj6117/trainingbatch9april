package com.example.demo.enums;

public enum CoinNameEnum {

    BITCOIN("Bitcoin"),ETHEREUM("Ethereum"),INR("inr");

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    private String value;
    CoinNameEnum(String value){this.value=value;}
}
