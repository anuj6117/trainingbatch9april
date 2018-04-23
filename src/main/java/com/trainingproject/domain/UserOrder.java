package com.trainingproject.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.trainingproject.enums.UserOrderStatus;

@Entity
@Table(name = "userrder")
public class UserOrder {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer userOrderId;
    private String coinName;
    private Long coinQuantity;
    private Long quote;
    private Date createdOn;
    private UserOrderStatus userOrderStatus;
    private Long fees;
	public Integer getUserOrderId() {
		return userOrderId;
	}
	public void setUserOrderId(Integer userOrderId) {
		this.userOrderId = userOrderId;
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
	public Long getQuote() {
		return quote;
	}
	public void setQuote(Long quote) {
		this.quote = quote;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public UserOrderStatus getUserOrderStatus() {
		return userOrderStatus;
	}
	public void setUserOrderStatus(UserOrderStatus userOrderStatus) {
		this.userOrderStatus = userOrderStatus;
	}
	public Long getFees() {
		return fees;
	}
	public void setFees(Long fees) {
		this.fees = fees;
	}
    
}
