package com.example.demo.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.example.demo.enumeration.UserOrderStatus;
import com.example.demo.enumeration.UserOrderType;
import com.fasterxml.jackson.annotation.JsonIgnore;

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

private Integer coinQuantity; 
 
private double netAmount;
 


private long price;

private double grossAmount;
private double fees;
private Date dateCreated;

public UserOrderType getOrderType() {
	return orderType;
}

public void setOrderType(UserOrderType orderType) {
	this.orderType = orderType;
}

public String getCoinName() {
	return coinName;
}

public void setCoinName(String coinName) {
	this.coinName = coinName;
}

public double getGrossAmount() {
	return grossAmount;
}

public void setGrossAmount(double grossAmount) {
	this.grossAmount = grossAmount;

}
public Date getDateCreated() {
	return dateCreated;
}

public void setDateCreated(Date dateCreated) {
	this.dateCreated = dateCreated;
}



public double getFees() {
	return fees;
}
public void setFees(double fees) {
	this.fees = fees;
}
public UserOrderStatus getStatus() {
	return status;
}

public void setStatus(UserOrderStatus status) {
	this.status = status;
}

public Integer getUserorderId() {
	return userorderId;
}

public void setUserorderId(Integer userorderId) {
	this.userorderId = userorderId;
}

public Integer getCoinQuantity() {
	return coinQuantity;
}

public void setCoinQuantity(Integer coinQuantity) {
	this.coinQuantity = coinQuantity;
}

public long getPrice() {
	return price;
}

public void setPrice(long price) {
	this.price = price;
}
public double getNetAmount() {
	return netAmount;
}
public void setNetAmount(double netAmount) {
	this.netAmount = netAmount;
}

}
