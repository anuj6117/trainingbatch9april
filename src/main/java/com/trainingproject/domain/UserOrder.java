package com.trainingproject.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.trainingproject.enums.CoinType;
import com.trainingproject.enums.OrderType;
import com.trainingproject.enums.UserOrderStatus;

@Entity
@Table(name="userorder")
public class UserOrder {

 @Id
 @GeneratedValue
 private Integer userorderId;
 @Enumerated(EnumType.STRING)
 private OrderType orderType;
 private String coinName;
 @Enumerated(EnumType.STRING)
 private UserOrderStatus orderStatus;
 @Enumerated(EnumType.STRING)
  private CoinType coinType;
 private double price;

private double grossAmount;
 private double coinQuantity;
 private Date date;
 private double fee=2l;
 private Integer userId;
 

 public double getGrossAmount() {
	return grossAmount;
}

public void setGrossAmount(double grossAmount) {
	this.grossAmount = grossAmount;
}
 @ManyToOne
 @JsonIgnore
 private User user;
 
 
 public CoinType getCoinType() {
	return coinType;
}

public void setCoinType(CoinType coinType) {
	this.coinType = coinType;
}

 public UserOrderStatus getOrderStatus() {
	return orderStatus;
}

public void setOrderStatus(UserOrderStatus orderStatus) {
	this.orderStatus = orderStatus;
}

public double getCoinQuantity() {
	return coinQuantity;
}
 
public void setCoinQuantity(double coinQuantity) {
	this.coinQuantity = coinQuantity;
}

public User getUser() {
	return user;
}
public void setUser(User user) {
	this.user = user;
}

public Integer getUserId() {
	return userId;
}

public void setUserId(Integer userId) {
	this.userId = userId;
}

public double getFee() {
	return fee;
}

public void setFee(double fee) {
	this.fee = fee;
}

public Integer getUserorderId() {
	return userorderId;
}
public void setUserorderId(Integer userorderId) {
	this.userorderId = userorderId;
}
public OrderType getOrderType() {
	return orderType;
}
public void setOrderType(OrderType orderType) {
	this.orderType = orderType;
}
public String getCoinName() {
	return coinName;
}
public void setCoinName(String coinName) {
	this.coinName = coinName;
}

public double getPrice() {
	return price;
}
public void setPrice(double price) {
	this.price = price;
}
public Date getDate() {
	return date;
}
public void setDate(Date date) {
	this.date = date;
}
 
 
}
