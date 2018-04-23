package com.trainingproject.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.trainingproject.enums.OrderType;

@Entity
@Table(name="userorder")
public class UserOrder {

 @Id
 @GeneratedValue
 private Integer userorderId;
 @Enumerated(EnumType.STRING)
 private OrderType orderType;
 private String coinName;
 private long quantity;
 private long price;
 private Integer coinQuantity;
 private Date date;

 
 @ManyToOne
 private User user;
 
 
 public Integer getCoinQuantity() {
	return coinQuantity;
}
public void setCoinQuantity(Integer coinQuantity) {
	this.coinQuantity = coinQuantity;
}

public User getUser() {
	return user;
}
public void setUser(User user) {
	this.user = user;
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
public long getQuantity() {
	return quantity;
}
public void setQuantity(long quantity) {
	this.quantity = quantity;
}
public long getPrice() {
	return price;
}
public void setPrice(long price) {
	this.price = price;
}
public Date getDate() {
	return date;
}
public void setDate(Date date) {
	this.date = date;
}
 
 
}
