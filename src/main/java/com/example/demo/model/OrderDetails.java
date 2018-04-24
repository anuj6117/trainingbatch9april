package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.example.demo.dto.UserOrderDTO;
import com.example.demo.enums.OrderType;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="orderdetails")
public class OrderDetails {
	
	@Id
	@GeneratedValue
	private Integer OrderId;
	@Enumerated(EnumType.STRING)
	private OrderType orderType;
	private Integer coinQuantity;
	private Integer price;
	private String coinName;
	
	@JsonIgnore
	@ManyToOne()
	private User user;
	
	public OrderDetails() {}
	
	public OrderDetails(UserOrderDTO userOrderDto) {
		this.coinQuantity = userOrderDto.getCoinQuantity();
		this.price = userOrderDto.getPrice();
		this.coinName = userOrderDto.getCoinName();
	}

	public Integer getOrderId() {
		return OrderId;
	}

	public OrderType getOrderType() {
		return orderType;
	}

	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}

	public Integer getCoinQuantity() {
		return coinQuantity;
	}

	public void setCoinQuantity(Integer coinQuantity) {
		this.coinQuantity = coinQuantity;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getCoinName() {
		return coinName;
	}

	public void setCoinName(String coinName) {
		this.coinName = coinName;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}	

}
