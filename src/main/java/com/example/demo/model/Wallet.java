package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.example.demo.enums.CoinType;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Wallet")
public class Wallet {

	@Id
	@GeneratedValue
	private Integer id;

	@Enumerated(EnumType.STRING)
	private CoinType coinType;

	private String coinName;
	private Double balance;
	private Double shadowBalance;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "userId")
	private User user;

	public Wallet() {
		balance = 0.0;
		shadowBalance = 0.0;
	}
	
	public Wallet(CoinType coinType) {
		this.coinType = coinType;
		this.balance = 0.0;
		this.shadowBalance = 0.0;
	}
	
	public String getCoinName() {
		return coinName;
	}

	public void setCoinName(String coinName) {
		this.coinName = coinName;
	}
	
	public CoinType getCoinType() {
		return coinType;
	}

	public void setCoinType(CoinType coinType) {
		this.coinType = coinType;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(double d) {
		this.balance = d;
	}

	public Double getShadowBalance() {
		return shadowBalance;
	}

	public void setShadowBalance(Double shadowBalance) {
		this.shadowBalance = shadowBalance;
	}

	public String toString() {
		return "id : " + id + "\tcoinType" + coinType;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
