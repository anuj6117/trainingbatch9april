package com.example.demo.enums;

public enum OrderStatus {
    PENDING("Pending"),APPROVED("Approved"),REJECTED("Rejected");

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    private String value;

    OrderStatus(String value){
        this.value=value;
    }


}
