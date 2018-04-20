package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import com.example.demo.enums.WalletEnum;

@Entity
@Table(name="wallet")
public class Wallet {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer walletId;
	private Double balance=0.0;
	private Double shadowBalance=0.0;
	@NotEmpty(message = "walletType  must not be empty")
	@NotBlank(message = "Space Not Accepted")
	private String walletType=WalletEnum.BITCOIN.toString();
	
	@ManyToOne
	@JoinColumn(name="userId", referencedColumnName="userId")
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


	public String getWalletType() {
		return walletType;
	}


	public void setWalletType(String walletType) {
		this.walletType = walletType;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}
}
