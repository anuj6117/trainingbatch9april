package com.example.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.example.enums.OrderType;
import com.example.enums.StatusType;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="orders")
public class Order
{

	@Id
	private Integer id;
	private OrderType orderType;
	private String coinName;
	private Integer coinQuantity;
	private Integer price;
	private Date orderCcreatedOn;
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

	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public Date getOrderCcreatedOn() {
		return orderCcreatedOn;
	}
	public void setOrderCcreatedOn(Date orderCcreatedOn) {
		this.orderCcreatedOn = orderCcreatedOn;
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
