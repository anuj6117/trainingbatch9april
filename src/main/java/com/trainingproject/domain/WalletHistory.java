package com.trainingproject.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.trainingproject.enums.UserOrderStatus;

@Entity
@Table(name="walletHistory")
public class WalletHistory {

	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer amount;
    
	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public UserOrderStatus getStatus() {
		return status;
	}

	public void setStatus(UserOrderStatus status) {
		this.status = status;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	private UserOrderStatus status;
    
	private String comment;
	
	
}
