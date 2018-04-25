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
import com.trainingproject.enums.WalletType;

@Entity
@Table(name = "userorder")
public class UserOrder {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer orderId;
	@Enumerated(EnumType.STRING)
	private OrderType orderType;
	private WalletType coinType;
    private String coinName;
    private Long coinQuantity;
    private Long price;
    private Long netAmount;
    private Long grossAmount;
    private Date orderCreatedOn;
    @Enumerated(EnumType.STRING)
    private Status status;
    private Long fee;
    @ManyToOne
    @JoinColumn(name = "userId")
	@JsonIgnore
	private User user;
    
    
	public WalletType getCoinType() {
		return coinType;
	}
	public void setCoinType(WalletType coinType) {
		this.coinType = coinType;
	}
	public Long getNetAmount() {
		return netAmount;
	}
	public void setNetAmount(Long netAmount) {
		this.netAmount = netAmount;
	}
	public Long getGrossAmount() {
		return grossAmount;
	}
	public void setGrossAmount(Long grossAmount) {
		this.grossAmount = grossAmount;
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
	
	public Long getCoinQuantity() {
		return coinQuantity;
	}
	public void setCoinQuantity(Long coinQuantity) {
		this.coinQuantity = coinQuantity;
	}
	
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
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
	public Long getFee() {
		return fee;
	}
	public void setFee(Long fee) {
		this.fee = fee;
	}
	
    
}
