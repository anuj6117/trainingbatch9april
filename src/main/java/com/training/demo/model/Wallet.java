package com.training.demo.model;

import java.util.List;

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
	private WalletType walletType;
	
	private Double balance;
	private Double shadowBalance;

	@ManyToOne
	@JoinColumn(name = "userId", referencedColumnName = "userId")
	@JsonIgnore
	private User user;
	
	public String toString() {
		return walletId+"\t"+balance+"\t"+shadowBalance+"\t"+walletType;
	}
	
	public Wallet() {
		super();
	}
		
	public Wallet(Integer walletId, String walletType) {	
		super();
		this.walletId = walletId;
		this.walletType = WalletType.valueOf(walletType);
	}

	public Integer getWalletId() {
		return walletId;
	}
	
	public void setWalletId(Integer walletId) {
		this.walletId = walletId;
	}
	
	public String getWalletType() {
		return walletType.toString();
	}
	
	
	public void setWalletType(WalletType walletType) {
		this.walletType = walletType;
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

	public void setUser(User user) {
		this.user = user;
	}
	
	public User getUser() {
		return user;
	}
}