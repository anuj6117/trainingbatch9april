package io.oodles.springboot1.model;

public class BuyOrder {
	private Integer userid;
	private Integer coinQuantity;
	private Integer price;
	private String coinName;
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
	public String getCoinName() {
		return coinName;
	}
	public void setCoinName(String coinName) {
		this.coinName = coinName;
	}
	

}
