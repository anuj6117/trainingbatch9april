package com.trading.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.trading.Enum.WalletType;

@Entity
public class Wallet {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long walletId;

	@Enumerated(EnumType.STRING)
	private WalletType coinType;

	private String coinName;
	public String getCoinName() {
		return coinName;
	}

	public void setCoinName(String coinName) {
		this.coinName = coinName;
	}

	long balance;
	long shadowBalance;

	@JsonIgnore
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "userId", referencedColumnName = "userId")

	private User user;

	public long getWalletId() {
		return walletId;
	}

	public void setWalletId(long walletId) {
		this.walletId = walletId;
	}

	public User getuser() {
		return user;
	}

	public void setuser(User user) {
		this.user = user;
	}

	

	public WalletType getCoinType() {
		return coinType;
	}

	public void setCoinType(WalletType coinType) {
		this.coinType = coinType;
	}

	public long getBalance() {
		return balance;
	}

	public void setBalance(long balance) {
		this.balance = balance;
	}

	public long getShadowBalance() {
		return shadowBalance;
	}

	public void setShadowBalance(long shadowBalance) {
		this.shadowBalance = shadowBalance;
	}
}
