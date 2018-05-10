package io.oodles.springboot1.model;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import io.oodles.springboot1.enums.WalletType;

public class BuyOrder {
	private Integer userid;
	private Double coinQuantity=0.0;
	private Double price=0.0;
	private String coinname;
	@Enumerated(EnumType.STRING)
	private WalletType coinType;
	
	public WalletType getCoinType() {
		return coinType;
	}
	public void setCoinType(WalletType coinType) {
		this.coinType = coinType;
	}
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	public Double getCoinQuantity() {
		return coinQuantity;
	}
	public void setCoinQuantity(Double coinQuantity) {
		this.coinQuantity = coinQuantity;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getCoinname() {
		return coinname;
	}
	public void setCoinname(String coinname) {
		this.coinname = coinname;
	}
	
	

}
