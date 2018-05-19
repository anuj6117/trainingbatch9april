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

    private Integer price;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private Double fee;
    private Date orderCreatedOn;
    private String coinName;
    private Integer coinQuantity;

    private Integer netAmount;
    private Double grossAmount;


    @ManyToOne
    @JoinColumn(name = "userid")
    @JsonIgnore
    private User user;

    public Integer getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(Integer netAmount) {
        this.netAmount = netAmount;
    }

    public Double getGrossAmount() {
        return grossAmount;
    }

    public void setGrossAmount(Double grossAmount) {
        this.grossAmount = grossAmount;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getCoinQuantity() {
        return coinQuantity;
    }

    public void setCoinQuantity(Integer coinQuantity) {
        this.coinQuantity = coinQuantity;
    }

}
