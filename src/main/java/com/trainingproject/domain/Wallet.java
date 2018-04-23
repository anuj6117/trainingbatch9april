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
import com.trainingproject.enums.WalletType;

@Entity
@Table(name = "wallet")
public class Wallet {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Integer walletId;
	@Enumerated(EnumType.STRING)
	private WalletType walletType;//=WalletType.FIAT;
	private Long balance;
	private Long shadowBalance;
	@ManyToOne
	@JsonIgnore
	private User user;
	
	public Wallet() {
		this.balance = 0l;
		this.shadowBalance = 0l;
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
	public WalletType getWalletType() {
		return walletType;
	}
	public void setWalletType(WalletType walletType) {
		this.walletType = walletType;
	}

	

	public Long getBalance() {
		return balance;
	}

	public void setBalance(Long balance) {
		this.balance = balance;
	}

	public Long getShadowBalance() {
		return shadowBalance;
	}

	public void setShadowBalance(Long shadowBalance) {
		this.shadowBalance = shadowBalance;
	}
}
