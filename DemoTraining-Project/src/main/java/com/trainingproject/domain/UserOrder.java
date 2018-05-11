package com.trainingproject.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.trainingproject.enums.OrderType;
import com.trainingproject.enums.Status;
import com.trainingproject.enums.CoinType;

@Entity
@Table(name = "userorder")
public class UserOrder {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer orderId;
	@Enumerated(EnumType.STRING)
	private OrderType orderType;
	@Enumerated(EnumType.STRING)
	private CoinType coinType;
    private String coinName;
    private Double coinQuantity;
    private Double fee;
    private Double price;
    private Double netAmount;
    private Double grossAmount;
    private Date orderCreatedOn;
    @Enumerated(EnumType.STRING)
    private Status status;
    
    @ManyToOne
    @JoinColumn(name = "userId")
	@JsonIgnore
	private User user;
    
    public UserOrder() {
    	super();
    }


	public UserOrder(Integer orderId, OrderType orderType, CoinType coinType, String coinName, Double coinQuantity,
			Double fee, Double price, Double netAmount, Double grossAmount, Date orderCreatedOn, Status status,
			User user) {
		super();
		this.orderId = orderId;
		this.orderType = orderType;
		this.coinType = coinType;
		this.coinName = coinName;
		this.coinQuantity = coinQuantity;
		this.fee = fee;
		this.price = price;
		this.netAmount = netAmount;
		this.grossAmount = grossAmount;
		this.orderCreatedOn = orderCreatedOn;
		this.status = status;
		this.user = user;
	}

	public CoinType getCoinType() {
		return coinType;
	}
	public void setCoinType(CoinType coinType) {
		this.coinType = coinType;
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
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public String getCoinName() {
		return coinName;
	}
	public void setCoinName(String coinName) {
		this.coinName = coinName;
	}
	
	public Date getOrderCreatedOn() {
		return orderCreatedOn;
	}
	public void setOrderCreatedOn(Date orderCreatedOn) {
		this.orderCreatedOn = orderCreatedOn;
	}
	
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}


	public Double getCoinQuantity() {
		return coinQuantity;
	}


	public void setCoinQuantity(Double coinQuantity) {
		this.coinQuantity = coinQuantity;
	}


	public Double getFee() {
		return fee;
	}


	public void setFee(Double fee) {
		this.fee = fee;
	}


	public Double getPrice() {
		return price;
	}


	public void setPrice(Double price) {
		this.price = price;
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
	
}
