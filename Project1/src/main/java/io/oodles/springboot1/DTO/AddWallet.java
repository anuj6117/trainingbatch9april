package io.oodles.springboot1.DTO;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import io.oodles.springboot1.enums.WalletType;

public class AddWallet {
	
	Integer userId;
	@Enumerated(EnumType.STRING)
	WalletType coinType;
	String coinName;
	
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public WalletType getCoinType() {
		return coinType;
	}
	public void setCoinType(WalletType coinType) {
		this.coinType = coinType;
	}
	public String getCoinName() {
		return coinName;
	}
	public void setCoinName(String coinName) {
		this.coinName = coinName;
	}
	
	
	

}
