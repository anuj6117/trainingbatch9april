package com.trading.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.trading.Enum.OrderType;
import com.trading.Enum.TransactionOrderStatus;
import com.trading.Enum.WalletType;

@Entity
public class UserOrder {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long orderId;

	@Enumerated(EnumType.STRING)
	private OrderType orderType;

	@NotNull
	@Enumerated(EnumType.STRING)
	private WalletType coinType;

	private String coinName;

	public WalletType getCoinType() {
		return coinType;
	}

	public void setCoinType(WalletType coinType) {
		this.coinType = coinType;
	}

	public void setCoinName(String coinName) {
		this.coinName = coinName;
	}

	@NotNull
	private Integer coinQuantity;

	@NotNull
	private Integer price;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date orderCreatedOn;

	@Enumerated(EnumType.STRING)
	private TransactionOrderStatus status;

	private long fee;

	@JsonIgnore
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "userId", referencedColumnName = "userId")
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public long getFee() {
		return fee;
	}

	public void setFee(long fee) {
		this.fee = fee;
	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
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

	public String getCoinName() {
		return coinName;
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

	public Date getOrderCreatedOn() {
		return orderCreatedOn;
	}

	public void setOrderCreatedOn(Date orderCreatedOn) {
		this.orderCreatedOn = orderCreatedOn;
	}

	public TransactionOrderStatus getStatus() {
		return status;
	}

	public void setStatus(TransactionOrderStatus status) {
		this.status = status;
	}
}
