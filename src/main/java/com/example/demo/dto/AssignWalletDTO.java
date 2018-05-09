package com.example.demo.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.example.demo.enumeration.CoinType;

public class AssignWalletDTO {
	private Integer userId;
	private String coinName;
	@Enumerated(value=EnumType.STRING)
	private CoinType coinType;
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	public String getCoinName() {
		return coinName;
	}
	public void setCoinName(String coinName) {
		this.coinName = coinName;
	}
	public CoinType getCoinType() {
		return coinType;
	}
	public void setCoinType(CoinType coinType) {
		this.coinType = coinType;
	}
}
