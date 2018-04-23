package com.trainingproject.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.trainingproject.enums.WalletType;

@Entity
@Table(name="wallet")
public class Wallet {

  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer walletId;
    
     @Enumerated(EnumType.STRING)
	private WalletType walletType;
	private long balance;
	private long shadowBal;
	//private Long amount;
	
	@ManyToOne
	@JsonIgnore
	private User user;
	
	public Integer getWalletId() {
		return walletId;
	}

	public void setWalletId(Integer walletId) {
		this.walletId = walletId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Wallet() {
		balance=0l;
		shadowBal=0l;
	}
	
	public Integer getId() {
		return walletId;
	}
	public void setId(Integer id) {
		this.walletId = id;
	}
//
	public WalletType getWalletType() {
		return walletType;
	}
	public void setWalletType(WalletType walletType) {
		
		this.walletType = walletType;
	}
	public long getBalance() {
		return balance;
	}
	public void setBalance(long balance) {
		this.balance = balance;
	}
	public long getShadowBal() {
		return shadowBal;
	}
	public void setShadowBal(long shadowBal) {
		this.shadowBal = shadowBal;
	}
	

}
