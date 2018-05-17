package com.example.demo.dto;

public class BuyAndSellOrderDto {

    private Integer userId;
    private Integer coinQuantity;
    private Integer price;
    private String coinName;


    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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
}
