package com.trainingproject.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.trainingproject.enums.CoinType;

@Entity
@Table(name = "wallet")
public class Wallet {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Integer walletId;
	@Enumerated(EnumType.STRING)
	private CoinType coinType;
	//@Column(unique = true)
	private String coinName;
	private Integer balance;
	private Integer shadowBalance;
	@ManyToOne
	@JoinColumn(name = "userId")
	@JsonIgnore
	private User user;
	
	public Wallet() {
		this.balance = 0;
		this.shadowBalance = 0;
	}
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Integer getWalletId() {
		return walletId;
	}
	public void setWalletId(Integer walletId) {
		this.walletId = walletId;
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

	public Integer getBalance() {
		return balance;
	}

	public void setBalance(Integer balance) {
		this.balance = balance;
	}

	public Integer getShadowBalance() {
		return shadowBalance;
	}

	public void setShadowBalance(Integer shadowBalance) {
		this.shadowBalance = shadowBalance;
	}
}
