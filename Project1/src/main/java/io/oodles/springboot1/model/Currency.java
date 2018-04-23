package io.oodles.springboot1.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Currency {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	private String coinname;
	private String symbol;
	private Integer initialtype;
	private Integer price;
	public String getCoinname() {
		return coinname;
	}
	public void setCoinname(String coinname) {
		this.coinname = coinname;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public Integer getInitialtype() {
		return initialtype;
	}
	public void setInitialtype(Integer initialtype) {
		this.initialtype = initialtype;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	
	
	

}
