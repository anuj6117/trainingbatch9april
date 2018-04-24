package com.example.demo.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.example.demo.dto.UserOrderDTO;
import com.example.demo.enums.OrderEnum;
import com.example.demo.enums.OrderStatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "userorder")
public class UserOrder {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer orderId;
	
	@Enumerated(EnumType.STRING)
	private OrderEnum orderType;

	@Enumerated(EnumType.STRING)
	private OrderStatusEnum orderStatusEnum;

	private Integer coinQuantity;
	private Integer price;
	private Date dateCreated;
	private String coinName;

	@JsonIgnore
	@ManyToOne
	private User user;

	public UserOrder() {

	}

	public UserOrder(UserOrderDTO userOrderDTO) {
		this.coinName = userOrderDTO.getCoinName();
		this.coinQuantity = userOrderDTO.getCoinQuantity();
		this.price = userOrderDTO.getPrice();
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public OrderEnum getOrderType() {
		return orderType;
	}

	public void setOrderType(OrderEnum orderType) {
		this.orderType = orderType;
	}

	public OrderStatusEnum getOrderStatusEnum() {
		return orderStatusEnum;
	}

	public void setOrderStatusEnum(OrderStatusEnum orderStatusEnum) {
		this.orderStatusEnum = orderStatusEnum;
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

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
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
