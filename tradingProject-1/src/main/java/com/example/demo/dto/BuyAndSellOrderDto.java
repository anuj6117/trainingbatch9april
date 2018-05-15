package com.example.demo.dto;

public class BuyAndSellOrderDto {

    private Integer userId;
    private Integer coinQuantity;
    private Double price;
    private String coinName;

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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCoinName() {
        return coinName;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }
}
