package com.training.demo.model;

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
import com.training.demo.enums.OrderType;
import com.training.demo.enums.UserOrderStatus;
import com.training.demo.enums.WalletType;

import ch.qos.logback.core.status.Status;

@Entity
@Table(name = "userorder")
public class UserOrder {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer userOrderId;
	@Enumerated(EnumType.STRING)
	private OrderType orderType;
	@Enumerated(EnumType.STRING)
	private WalletType coinType;
	private String coinName;
	private Long coinQuantity;
	private Long price;
	private Date orderCreatedOn;
	private Long netAmmount;
	private Long grossAmmount;
	@Enumerated(EnumType.STRING)
	private UserOrderStatus status;
	
	public WalletType getCoinType() {
		return coinType;
	}

	public void setCoinType(WalletType coinType) {
		this.coinType = coinType;
	}

	public Long getNetAmmount() {
		return netAmmount;
	}

	public void setNetAmmount(Long netAmmount) {
		this.netAmmount = netAmmount;
	}

	public Long getGrossAmmount() {
		return grossAmmount;
	}

	public void setGrossAmmount(Long grossAmmount) {
		this.grossAmmount = grossAmmount;
	}
	

	public String getCoinName() {
		return coinName;
	}

	public void setCoinName(String coinName) {
		this.coinName = coinName;
	}


	private Long fee;
	 @ManyToOne
	 @JoinColumn(name = "userId")
	 @JsonIgnore
      private User user;

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

	public Integer getUserOrderId() {
		return userOrderId;
	}

	public void setUserOrderId(Integer userOrderId) {
		this.userOrderId = userOrderId;
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

	public UserOrderStatus getStatus() {
		return status;
	}

	public void setStatus(UserOrderStatus status) {
		this.status = status;
	}

	public Long getFee() {
		return fee;
	}

	public void setFee(Long fee) {
		this.fee = fee;
	}

}