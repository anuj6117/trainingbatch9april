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
import com.training.demo.enums.OrderStatus;
import com.training.demo.enums.OrderType;

	@Entity
	@Table(name = "orderTable")
	public class OrderTable {
		
		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		private Integer orderId;
		
		//@NotNull(message = "Amount can't be null")
		private Double netAmount;
		
		private Double fee;
		
		//@NotNull(message = "price can't be null")
		private Double price;
		
		private Date orderCreatedOn;
		
		@Enumerated(EnumType.STRING)
		private OrderType orderType;
		
		//@NotNull(message = "Coin name can't be null")
		//@NotBlank(message = "Coin name can't be null")
		private String coinName;
		
		private Double grossAmount;
		
		@Enumerated(EnumType.STRING)
		private OrderStatus orderStatus;
		
		private Double coinQuantity;
		
		@ManyToOne
		@JoinColumn(name="fk_user_id", referencedColumnName="userId")
		@JsonIgnore
		private User user;
				
		public Integer getOrderId() {
			return orderId;
		}

		public void setOrderId(Integer orderId) {
			this.orderId = orderId;
		}

		public Double getAmount() {
			return netAmount;
		}

		public void setAmount(Double amount) {
			this.netAmount = amount;
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

		public OrderStatus getOrderStatus() {
			return orderStatus;
		}

		public void setOrderStatus(OrderStatus orderStatus) {
			this.orderStatus = orderStatus;
		}

		public void setCoinQuantity(Double coinQuantity) {
			this.coinQuantity = coinQuantity;
			
		}

		public Double getCoinQuantity() {
			return coinQuantity;
		}	
		
}	