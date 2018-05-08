package com.training.demo.model;

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
import com.training.demo.enums.WalletType;

@Entity
@Table(name="wallet")
public class Wallet {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer walletId;
	
	@Enumerated(EnumType.STRING)
	private WalletType coinType;
	
	private Double balance;
	private Double shadowBalance;
	private String coinName;
	
	@ManyToOne
	@JoinColumn(name = "userId", referencedColumnName = "userId")
	@JsonIgnore
	private User user;
	
	public String toString() {
		return walletId+"\t"+balance+"\t"+shadowBalance+"\t"+coinType;
	}
	
	public Wallet() {
		super();
		balance = 0d;
		shadowBalance = 0d;
	}
	

	public Wallet(Integer walletId, WalletType walletType, Double balance, Double shadowBalance, String coinName, User user) {
		super();
		this.walletId = walletId;
		this.coinType = walletType;
		this.balance = balance;
		this.shadowBalance = shadowBalance;
		this.coinName = coinName;
		this.user = user;
	}

	public Integer getWalletId() {
		return walletId;
	}
	
	public void setWalletId(Integer walletId) {
		this.walletId = walletId;
	}
	
	public String getCoinType() {
		return coinType.toString();
	}
	
	
	public void setCoinType(WalletType coinType) {
		this.coinType = coinType;
	}
	
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	
	public Double getBalance() {
		return balance;
	}

	public void setShadowBalance(Double shadowBalance) {
		this.shadowBalance = shadowBalance;
	}
	
	public Double getShadowBalance() {
		return shadowBalance;
	}
	
	public String getCoinName() {
		return coinName;
	}

	public void setCoinName(String coinName) {
		this.coinName = coinName;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public User getUser() {
		return user;
	}
}