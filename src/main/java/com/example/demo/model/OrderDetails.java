package com.example.demo.model;

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

import com.example.demo.dto.UserDepositWithdrawDTO;
import com.example.demo.dto.UserOrderDTO;
import com.example.demo.enums.CoinType;
import com.example.demo.enums.OrderStatus;
import com.example.demo.enums.OrderType;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="orderdetails")
public class OrderDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer orderId;
	
	@Enumerated(EnumType.STRING)
	private OrderType orderType;
	
	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus; 
	
	
	private String coinName;
	
	@Enumerated(EnumType.STRING)
	private CoinType coinType;
	
	private Integer coinQuantity;
	private Double price;	
	private Date date;
	private Integer fee;
	private Double exchangeRate;
	private Double profit; 
	private Double amount;
	
	

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "user12", referencedColumnName = "userId")
	private User user;
	
		
	
	public OrderDetails() {}
	
	public OrderDetails(UserDepositWithdrawDTO userDeposit, User user) {
		this.price = userDeposit.getAmount();
		this.date = new Date();
		this.orderStatus = OrderStatus.PENDING;
		this.coinName = "INR";
		this.coinType = CoinType.FIAT;
		this.orderType = userDeposit.getOrderType();
		this.fee = 0;
		this.user = user;
		this.coinQuantity = 0;		
	}
	
	public OrderDetails(UserOrderDTO userOrderDto) {
		this.coinQuantity = userOrderDto.getCoinQuantity();
		this.price = userOrderDto.getPrice();
		this.coinName =userOrderDto.getCoinName();
		this.date = new Date();		
	}
	
	public OrderDetails(CurrencyModel currency) {
		this.price = currency.getPrice();
		this.date = new Date();
		this.orderStatus = OrderStatus.PENDING;
		this.coinName = currency.getCoinName();
		this.coinType = currency.getCoinType();
		this.orderType = OrderType.SELLER;
		this.fee = currency.getFee();
		//this.user = user;
		this.coinQuantity = currency.getInitialSupply();		
	}
	
	public Double getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(Double exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
	
	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getProfit() {
		return profit;
	}

	public void setProfit(Double profit) {
		this.profit = profit;
	}

	public void setFee(int fee) {
		this.fee = fee;
	}

	public CoinType getCoinType() {
		return coinType;
	}

	public void setCoinType(CoinType coinType) {
		this.coinType = coinType;
	}

	
	public Integer getFee() {
		return fee;
	}

	public void setFee(Integer fee) {
		this.fee = fee;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getOrderId() {
		return orderId;
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

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
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

}
