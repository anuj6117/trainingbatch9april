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
	private WalletType wallet;
	private Integer balance=0;
	private Integer shadowbalance=0;
	@ManyToOne
	@JoinColumn(name="user_id")
	@JsonIgnore
	Users users;
	
	
	 public Wallet(){}
	
	public Wallet(WalletType wallet) {
		super();
		this.wallet = wallet;
	}
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
	public WalletType getWallet() {
		return wallet;
	}
	public void setWallet(WalletType wallet) {
		this.wallet = wallet;
	}
	public Integer getBalance() {
		return balance;
	}
	public void setBalance(Integer balance) {
		this.balance = balance;
	}
	public Integer getShadowbalance() {
		return shadowbalance;
	}
	public void setShadowbalance(Integer shadowbalance) {
		this.shadowbalance = shadowbalance;
	}
	

}
