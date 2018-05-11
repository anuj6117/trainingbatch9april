package com.traningproject1.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.traningproject1.enumsclass.CoinType;

@Entity
@Table(name="Wallet")
public class Wallet {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
private Integer wId;
	
@Enumerated(EnumType.STRING)
private CoinType coinType;

private String coinName;
//@Enumerated(EnumType.STRING)
//private WalletType walletType;


private Double balance;
private Double shadowBalance;

@ManyToOne
@JsonIgnore
private User user;

public Wallet()
{
 balance=0.0;
 shadowBalance=0.0;
}
public User getUser() {
	return user;
}
public void setUser(User user) {
	this.user = user;
}
public Integer getwId() {
	return wId;
}
public void setwId(Integer wId) {
	this.wId = wId;
}
 
//public WalletType getWalletType() {
//	return walletType;
//}
//public void setWalletType(WalletType walletType) {
//	this.walletType = walletType;
//}



public CoinType getCoinType() {
	return coinType;
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
public void setCoinType(CoinType coinType) {
	this.coinType = coinType;
}

public String getCoinName() {
	return coinName;
}
public void setCoinName(String coinName) {
	this.coinName = coinName;
}
}
