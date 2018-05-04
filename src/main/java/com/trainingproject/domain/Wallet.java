package com.trainingproject.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.trainingproject.enums.CoinType;

@Entity
@Table(name="wallet")
public class Wallet {

  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer walletId;
    
     @Enumerated(EnumType.STRING)
	private CoinType coinType;
     private String coinName;
	private double balance;
	private double shadowBal;
	private double coinQuantity;
	

	@ManyToOne
	@JsonIgnore
	private User user;
	
	public String getCoinName() {
		return coinName;
	}

	public void setCoinName(String coinName) {
		this.coinName = coinName;
	}
	
	public Integer getWalletId() {
		return walletId;
	}

	public double getCoinQuantity() {
		return coinQuantity;
	}

	public void setCoinQuantity(double coinQuantity) {
		this.coinQuantity = coinQuantity;
	}

	public void setWalletId(Integer walletId) {
		this.walletId = walletId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Wallet() {
		balance=0l;
		shadowBal=0l;
		coinQuantity=0;
	}
	
//
	public CoinType getCoinType() {
		return coinType;
	}
	public void setCoinType(CoinType coinType) {
		
		this.coinType = coinType;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public double getShadowBal() {
		return shadowBal;
	}
	public void setShadowBal(double shadowBal) {
		this.shadowBal = shadowBal;
	}
	

}
