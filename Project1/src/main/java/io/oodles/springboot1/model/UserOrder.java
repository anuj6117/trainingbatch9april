package io.oodles.springboot1.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.oodles.springboot1.enums.OrderStatus;
import io.oodles.springboot1.enums.OrderType;
import io.oodles.springboot1.enums.WalletType;

@Entity
public class UserOrder {
	@Id @GeneratedValue(strategy=GenerationType.AUTO) 
   	Integer orderid;
	@Enumerated(EnumType.STRING)
	OrderType ordertype;
	@Enumerated(EnumType.STRING)
	WalletType coinType;
	String coinname;
	Double coinQuantity=0.0;
	Double price=0.0;
	Date orderCreatedOn;
	@Enumerated(EnumType.STRING)
	OrderStatus orderStatus;
	Double fee=0.0;
	@ManyToOne
	@JoinColumn(name="user_id")
	@JsonIgnore
	Users usersorder;
	Double netAmount=0.0;
	Double grossAmount=0.0;
	
	
	

	

	

	public WalletType getCoinType() {
		return coinType;
	}

	public void setCoinType(WalletType coinType) {
		this.coinType = coinType;
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

	public UserOrder() {
		// TODO Auto-generated constructor stub
	}

	

	public Integer getOrderid() {
		return orderid;
	}

	public void setOrderid(Integer orderid) {
		this.orderid = orderid;
	}

	public OrderType getOrdertype() {
		return ordertype;
	}

	public void setOrdertype(OrderType ordertype) {
		this.ordertype = ordertype;
	}

	

	

	public String getCoinname() {
		return coinname;
	}

	public void setCoinname(String coinname) {
		this.coinname = coinname;
	}

	public Double getCoinQuantity() {
		return coinQuantity;
	}

	public void setCoinQuantity(Double coinQuantity) {
		this.coinQuantity = coinQuantity;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
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

	public Double getFee() {
		return fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
	}

	public Users getUsersorder() {
		return usersorder;
	}

	public void setUsersorder(Users usersorder) {
		this.usersorder = usersorder;
	}

	
	
	

}
