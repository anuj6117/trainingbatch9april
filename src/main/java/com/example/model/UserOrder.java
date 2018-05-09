
package com.example.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.example.enums.OrderType;
import com.example.enums.StatusType;
import com.example.enums.WalletType;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="orders")
public class UserOrder
{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer orderId;
	@Enumerated(EnumType.STRING)
	private OrderType orderType;
	private String coinName;
	@Enumerated(EnumType.STRING)
	private WalletType coinType;
	private Double coinQuantity;
	private Double price;
	private Double fees;
	private Double netAmount;
	private Double grossAmount;
	private String orderCreatedOn;
	@Enumerated(EnumType.STRING)
	private StatusType statusType;
	

  public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
@ManyToOne
  @JoinColumn(name="userId")
  @JsonIgnore
  User user;

	
	public WalletType getCoinType() {
	return coinType;
}
public void setCoinType(WalletType coinType) {
	this.coinType = coinType;
}
	


	public Double getCoinQuantity() {
	return coinQuantity;
}
public Double getPrice() {
	return price;
}
	public Double getFees() {
	return fees;
}
public void setFees(Double fees) {
	this.fees = fees;
}
public Double getNetAmount() {
	return netAmount;
}
public void setNetAmount(Double netAmount) {
	this.netAmount = netAmount;
}
public Double getGrossAmount() {
	return grossAmount;
}
public void setGrossAmount(Double grossAmount) {
	this.grossAmount = grossAmount;
}
public void setCoinQuantity(Double coinQuantity) {
	this.coinQuantity = coinQuantity;
}
public void setPrice(Double price) {
	this.price = price;
}
	public Integer getOrderId() {
	return orderId;
}
public void setOrderId(Integer orderId) {
	this.orderId = orderId;
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
	

	
	
	public String getOrderCreatedOn() {
		return orderCreatedOn;
	}
	public void setOrderCreatedOn(String orderCreatedOn) {
		this.orderCreatedOn = orderCreatedOn;
	}
	
	/*public Date getOrderCcreatedOn() {
		return orderCcreatedOn;
	}
	public void setOrderCcreatedOn(Date orderCcreatedOn) {
		this.orderCcreatedOn = orderCcreatedOn;
	}*/
	public StatusType getStatusType() {
		return statusType;
	}
	public void setStatusType(StatusType statusType) {
		this.statusType = statusType;
	}
}
