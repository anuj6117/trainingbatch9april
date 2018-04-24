package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.example.demo.enums.WalletEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name="Wallet")
public class Wallet {

	@Id
	@GeneratedValue
	private Integer id;
	
	@Enumerated(EnumType.STRING)
	private WalletEnum walletType;
	
	private Integer balance;	
	private Integer shadowBalance;
	
	@JsonIgnore
	@ManyToOne	
	@JoinColumn(name="user_id" , referencedColumnName="id")
	private User user;
	
	public Wallet() {
		balance = 0;
		shadowBalance = 0;
	}
	
	public Wallet(WalletEnum walletType) {
		balance = 0;
		shadowBalance = 0;
		this.walletType = walletType;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public WalletEnum getWalletType() {
		return walletType;
	}

	public void setWalletType(WalletEnum walletType) {
		this.walletType = walletType;
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
	
	public String toString() {
		return "id : "+id+"\twalletType"+walletType;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
		
}
