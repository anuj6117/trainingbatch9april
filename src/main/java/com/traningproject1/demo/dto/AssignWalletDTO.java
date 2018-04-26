package com.traningproject1.demo.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.traningproject1.enumsclass.CoinType;

public class AssignWalletDTO {
	private Integer userId;
	@Enumerated(value=EnumType.STRING)
	private CoinType coinType;
	private String coinName;
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public CoinType getCoinType() {
		return coinType;
	}
	public String getCoinName() {
		return coinName;
	}
	public void setCoinName(String coinName) {
		this.coinName = coinName;
	}
	public void setCoinType(CoinType coinType) {
		this.coinType = coinType;
	}
	
	

}
