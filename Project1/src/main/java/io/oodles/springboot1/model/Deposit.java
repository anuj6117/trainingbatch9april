package io.oodles.springboot1.model;

import io.oodles.springboot1.enums.WalletType;

public class Deposit {
	Integer userid;
	WalletType walletType;
	Integer Balance;
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	public WalletType getWalletType() {
		return walletType;
	}
	public void setWalletType(WalletType walletType) {
		this.walletType = walletType;
	}
	public Integer getBalance() {
		return Balance;
	}
	public void setBalance(Integer balance) {
		Balance = balance;
	}
	

}
