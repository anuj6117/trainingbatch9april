package com.trading.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.trading.Enum.WalletType;

@Entity
public class Wallet {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long walletId;
	@Enumerated(EnumType.STRING)
	private WalletType walletType;
	long balance;
	long shadowBalance;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName="userId")
    private User user;
	
	
	public long getWalletId() {
		return walletId;
	}
	public void setWalletId(long walletId) {
		this.walletId = walletId;
	}
	public WalletType getWalletType() {
		return walletType;
	}
	public void setWalletType(WalletType walletType) {
		this.walletType = walletType;
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
	
	
	