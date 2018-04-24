package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.example.demo.enums.WalletEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "wallet")
public class Wallet {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer walletId;
	private Double balance ;
	private Double shadowBalance;
	//@NotEmpty(message = "walletType  must not be empty")
    //@NotBlank(message = "Space Not Accepted")
	@Enumerated(EnumType.STRING)
	private WalletEnum walletType;
	
	@ManyToOne
	@JoinColumn(name = "userId", referencedColumnName = "userId")
	@JsonIgnore
	private User user;

	public Integer getWalletId() {
		return walletId;
	}

	public void setWalletId(Integer walletId) {
		this.walletId = walletId;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Double getShadowBalance() {
		return shadowBalance;
	}

	public void setShadowBalance(Double shadowBalance) {
		this.shadowBalance = shadowBalance;
	}

		public WalletEnum getWalletType() {
		return walletType;
	}

	public void setWalletType(WalletEnum walletType) {
		this.walletType = walletType;
	}

		public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
