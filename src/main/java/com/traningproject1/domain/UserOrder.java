package com.traningproject1.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.traningproject1.enumsclass.UserOrderStatus;
import com.traningproject1.enumsclass.UserOrderType;

@Entity
@Table(name="UserOrder")
public class UserOrder {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer userorderId;


@ManyToOne
@JsonIgnore
private User user;



public User getUser() {
	return user;
}
public void setUser(User user) {
	this.user = user;
}

@Enumerated(EnumType.STRING)
private UserOrderType orderType;

@Enumerated(EnumType.STRING)
private UserOrderStatus status;

private String coinName;

private Double coinQuantity; 
 
private Double netAmount;
 


private Double price;

private Double grossAmount;
private Double fees;
private Date dateCreated;



public Integer getUserorderId() {
	return userorderId;
}
public void setUserorderId(Integer userorderId) {
	this.userorderId = userorderId;
}
public UserOrderType getOrderType() {
	return orderType;
}
public void setOrderType(UserOrderType orderType) {
	this.orderType = orderType;
}
public UserOrderStatus getStatus() {
	return status;
}
public void setStatus(UserOrderStatus status) {
	this.status = status;
}
public String getCoinName() {
	return coinName;
}
public void setCoinName(String coinName) {
	this.coinName = coinName;
}
public Double getCoinQuantity() {
	return coinQuantity;
}
public void setCoinQuantity(Double coinQuantity) {
	this.coinQuantity = coinQuantity;
}
public Double getNetAmount() {
	return netAmount;
}
public void setNetAmount(Double netAmount) {
	this.netAmount = netAmount;
}
public Double getPrice() {
	return price;
}
public void setPrice(Double price) {
	this.price = price;
}
public Double getGrossAmount() {
	return grossAmount;
}
public void setGrossAmount(Double grossAmount) {
	this.grossAmount = grossAmount;
}
public Double getFees() {
	return fees;
}
public void setFees(Double fees) {
	this.fees = fees;
}
public Date getDateCreated() {
	return dateCreated;
}
public void setDateCreated(Date dateCreated) {
	this.dateCreated = dateCreated;
}




}
