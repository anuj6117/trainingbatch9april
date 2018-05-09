package com.example.demo.dto;

import com.example.demo.enumeration.CoinType;

public class DepositAmountDTO {
	
	
		private Integer userId;
		private CoinType coinType;
		private Integer amount;
		private String coinName;
		public String getCoinName() {
			return coinName;
		}
		public void setCoinName(String coinName) {
			this.coinName = coinName;
		}
		public Integer getUserId() {
			return userId;
		}
		public void setUserId(Integer userId) {
			this.userId = userId;
		}
		
		public CoinType getCoinType() {
			return coinType;
		}
		public void setCoinType(CoinType coinType) {
			this.coinType = coinType;
		}
		public Integer getAmount() {
			return amount;
		}
		public void setAmount(Integer amount) {
			this.amount = amount;
		}



}