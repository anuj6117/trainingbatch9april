package com.training.demo.model;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.training.demo.enums.OrderType;

	@Entity
	@Table(name = "orderTable")
	public class Order {
		
		@Id
		@GeneratedValue
		private Integer OrderId;
		@Enumerated(EnumType.STRING)
		private OrderType orderType;
		private Integer coinQuantity;
		private Integer price;
		private String coinName;
		private Date date;
		private Integer fees;
		private OrderType orderStatus;
		
		@JsonIgnore
		@ManyToOne()
		private User user;
		
		public Integer getOrderId() {
			return OrderId;
		}
		
		public void setOrderId(Integer orderId) {
			OrderId = orderId;
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
		
		public Date getDate() {
			return date;
		}
		
		public void setDate(Date date) {
			this.date = date;
		}
		
		public Integer getFees() {
			return fees;
		}
		
		public void setFees(Integer fees) {
			this.fees = fees;
		}

		public OrderType getOrderStatus() {
			return orderStatus;
		}

		public void setOrderStatus(OrderType orderStatus) {
			this.orderStatus = orderStatus;
		}

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}
		
		

	}