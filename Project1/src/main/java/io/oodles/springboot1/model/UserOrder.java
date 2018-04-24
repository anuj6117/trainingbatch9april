package io.oodles.springboot1.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.oodles.springboot1.enums.OrderStatus;
import io.oodles.springboot1.enums.OrderType;

@Entity
public class UserOrder {
	@Id @GeneratedValue(strategy=GenerationType.AUTO) 
	Integer id;
	OrderType ordertype;
	String coinName;
	Integer coinQuantity;
	Integer price;
	Date orderCreatedOn;
	OrderStatus orderStatus;
	Integer fee;
	@ManyToOne
	@JoinColumn(name="user_id")
	@JsonIgnore
	Users usersorder;
	
	public UserOrder() {
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public OrderType getOrdertype() {
		return ordertype;
	}

	public void setOrdertype(OrderType ordertype) {
		this.ordertype = ordertype;
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

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Integer getFee() {
		return fee;
	}

	public void setFee(Integer fee) {
		this.fee = fee;
	}

	public Users getUsersorder() {
		return usersorder;
	}

	public void setUsersorder(Users usersorder) {
		this.usersorder = usersorder;
	}
	
	
	

}
