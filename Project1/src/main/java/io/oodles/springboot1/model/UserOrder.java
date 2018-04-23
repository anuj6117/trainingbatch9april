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
	Integer quantity;
	Integer quote;
	Date dateCreated;
	OrderStatus orderStatus;
	Integer fee;
	@ManyToOne
	@JoinColumn(name="user_id")
	@JsonIgnore
	Users userorders;
	
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
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Integer getQuote() {
		return quote;
	}
	public void setQuote(Integer quote) {
		this.quote = quote;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
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
	
	

}
