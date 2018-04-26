
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
	private WalletType coinType;
	private Integer coinQuantity;
	private Integer price;
	private Integer fees;
	private Integer netAmount;
	private Integer grossAmount;
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
public Integer getFees() {
	return fees;
}
public void setFees(Integer fees) {
	this.fees = fees;
}
public Integer getNetAmount() {
	return netAmount;
}
public void setNetAmount(Integer netAmount) {
	this.netAmount = netAmount;
}
public Integer getGrossAmount() {
	return grossAmount;
}
public void setGrossAmount(Integer grossAmount) {
	this.grossAmount = grossAmount;
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
	
	public Integer getCoinQuantity() {
		return coinQuantity;
	}
	public void setCoinQuantity(Integer coinQuantity) {
		this.coinQuantity = coinQuantity;
	}
	
	
	public String getOrderCreatedOn() {
		return orderCreatedOn;
	}
	public void setOrderCreatedOn(String orderCreatedOn) {
		this.orderCreatedOn = orderCreatedOn;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
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
