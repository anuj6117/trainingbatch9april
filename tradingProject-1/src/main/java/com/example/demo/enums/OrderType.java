package com.example.demo.enums;

public enum OrderType {
    WITHDRAW("Withdraw"),DEPOSIT("Deposit"),BUYORDER("Buyorder"),SELLORDER("Sellorder");

    public String value;

      OrderType(String value){
        this.value=value;
    }
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


}
