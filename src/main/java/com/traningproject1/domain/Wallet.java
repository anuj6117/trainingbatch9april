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
import com.traningproject1.enumsclass.WalletType;

@Entity
@Table(name="Wallet")
public class Wallet {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
private Integer wId;  
@Enumerated(EnumType.STRING)
private WalletType walletType;

private long balance;
private long shadowBalance;

@ManyToOne
@JsonIgnore
private User user;

public Wallet()
{
 balance=0l;
 shadowBalance=0l;
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
