package com.traningproject1.demo.dto;

import com.traningproject1.enumsclass.CoinType;

public class DepositAmountDTO {
	
	
		private Integer userId;
		private CoinType coinType;
		private Double amount;
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
		public Double getAmount() {
			return amount;
		}
		public void setAmount(Double amount) {
			this.amount = amount;
		}
		



}