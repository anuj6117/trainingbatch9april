package io.oodles.springboot1.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.oodles.springboot1.enums.WalletType;

@Entity
public class Wallet {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	@Enumerated(EnumType.STRING)
	private WalletType coinType;
	private String coinName;
	private Double balance=0.0;
			private Double shadowbalance=0.0;
	@ManyToOne
	@JoinColumn(name="user_id")
	@JsonIgnore
	Users users;
	
	
	 public Wallet(){}
	
	
	public Users getUsers() {
		return users;
	}
	public void setUsers(Users users) {
		this.users = users;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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


	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	public Double getShadowbalance() {
		return shadowbalance;
	}
	public void setShadowbalance(Double shadowbalance) {
		this.shadowbalance = shadowbalance;
	}
	

}
