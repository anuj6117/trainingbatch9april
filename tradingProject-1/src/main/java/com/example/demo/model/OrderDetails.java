package com.example.demo.model;

import com.example.demo.enums.OrderStatus;
import com.example.demo.enums.OrderType;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="orders")
public class OrderDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer orderId;

    @Enumerated(EnumType.STRING)
    private OrderType orderType;

    private Double amount;

    private Double price;
    private OrderStatus orderStatus;
    private Double fee;
    private Date orderCreatedOn;
    //    private Integer userId;
    private String coinName;

    @ManyToOne
    @JoinColumn(name = "userid")
    @JsonIgnore
    private User user;


    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public Date getOrderCreatedOn() {
        return orderCreatedOn;
    }

    public void setOrderCreatedOn(Date orderCreatedOn) {
        this.orderCreatedOn = orderCreatedOn;
    }
/*
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }*/

    public String getCoinName() {
        return coinName;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }



    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
