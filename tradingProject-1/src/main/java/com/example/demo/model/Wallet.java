package com.example.demo.model;

import javax.persistence.*;

import com.example.demo.enums.CoinType;
import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name="wallet")
public class Wallet {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Enumerated(EnumType.STRING)
	private CoinType coinType;

	private String coinName;

	private Double balance;
	private Double shadowBalance;

	@ManyToOne
	@JoinColumn(name = "userId")
	@JsonIgnore
	private User user;


	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Wallet(){
		balance=0.0;
		shadowBalance=0.0;
	}

	public Wallet(CoinType coinType) {
		this.coinType=coinType;
		balance=0.0;
		shadowBalance=0.0;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public CoinType getCoinType() {
		return coinType;
	}
	public void setCoinType(CoinType coinType) {
		this.coinType = coinType;
	}
	public String getCoinName() {
		return coinName;
	}
	public void setCoinName(String coinName) {
		this.coinName = coinName;
	}
	public Double getShadowBalance() {
		return shadowBalance;
	}
	public void setShadowBalance(Double shadowBalance) {
		this.shadowBalance = shadowBalance;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}

}
