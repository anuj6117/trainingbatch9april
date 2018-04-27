package io.oodles.springboot1.model;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import io.oodles.springboot1.enums.WalletType;

public class BuyOrder {
	private Integer userid;
	private Integer coinQuantity;
	private Integer price;
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
	public Integer getCoinQuantity() {
		return coinQuantity;
	}
	public void setCoinQuantity(Integer coinQuantity) {
		this.coinQuantity = coinQuantity;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public String getCoinname() {
		return coinname;
	}
	public void setCoinname(String coinname) {
		this.coinname = coinname;
	}
	
	

}
