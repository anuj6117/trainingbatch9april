package com.training.demo.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.training.demo.enums.OrderStatus;
import com.training.demo.enums.OrderType;
import com.training.demo.enums.WalletType;

	@Entity
	@Table(name = "orderTable")
	public class OrderTable {
		
		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		private Integer orderId;
		
		//@NotNull(message = "Amount can't be null")
		private Double amount;
		
		private Double fee;
		
		//@NotNull(message = "price can't be null")
		private Double price;
		
		private Date orderCreatedOn;
		
		private OrderType orderType;
		
		//@NotNull(message = "Coin name can't be null")
		//@NotBlank(message = "Coin name can't be null")
		private WalletType coinName;
		
		private Double grossAmount;
		
		private OrderStatus orderStatus;
		
		private Integer coinQuantity;
		
		@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
		@JoinColumn(name = "user_order_fk")
		@JsonIgnore
		private User user;
		
		public Integer getOrderId() {
			return orderId;
		}

		public void setOrderId(Integer orderId) {
			this.orderId = orderId;
		}

		public Double getAmount() {
			return amount;
		}

		public void setAmount(Double amount) {
			this.amount = amount;
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

		public Double getGrossAmount() {
			return grossAmount;
		}

		public void setGrossAmount(Double grossAmount) {
			this.grossAmount = grossAmount;
		}

		public OrderType getOrderType() {
			return orderType;
		}

		public void setOrderType(OrderType orderType) {
			this.orderType = orderType;
		}

		public Date getOrderCreatedOn() {
			return orderCreatedOn;
		}

		public void setOrderCreatedOn(Date orderCreatedOn) {
			this.orderCreatedOn = orderCreatedOn;
		}

		public WalletType getCoinName() {
			return coinName;
		}

		public void setCoinName(WalletType coinName) {
			this.coinName = coinName;
		}

		public User getUser() {
			return user;
		}

		public void setUser(User use) {
			this.user = user;
		}

		public OrderStatus getOrderStatus() {
			return orderStatus;
		}

		public void setOrderStatus(OrderStatus orderStatus) {
			this.orderStatus = orderStatus;
		}

		public void setCoinQuantity(Integer coinQuantity) {
			this.coinQuantity = coinQuantity;
			
		}

		public Integer getCoinQuantity() {
			return coinQuantity;
		}	
		
		
}	