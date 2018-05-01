package com.example.model;


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

import com.example.enums.WalletType;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="Wallet")
public class Wallet
{
@Id
@GeneratedValue(strategy=GenerationType.AUTO)
private Integer id;
@Enumerated(EnumType.STRING)
private WalletType walletType;

private String walletName;
private Integer balance;

private Integer shadowbalance;

@ManyToOne
@JoinColumn(name="userId")
@JsonIgnore
 User user;



public String getWalletName() {
	return walletName;
}

public void setWalletName(String walletName) {
	this.walletName = walletName;
}

public Integer getId() {
	return id;
}

public void setId(Integer id) {
	this.id = id;
}


public WalletType getWalletType() {
	return walletType;
}

public void setWalletType(WalletType walletType) {
	this.walletType = walletType;
}

public Integer getBalance() {
	return balance;
}

public void setBalance(Integer balance) {
	this.balance = balance;
}

public Integer getShadowbalance() {
	return shadowbalance;
}

public void setShadowbalance(Integer shadowbalance) {
	this.shadowbalance = shadowbalance;
}

public User getUser() {
	return user;
}

public void setUser(User user) {
	this.user = user;
}

	

}

