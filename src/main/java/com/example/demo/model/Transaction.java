package com.example.demo.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.example.demo.enums.TransactionStatus;
import com.example.demo.enums.WalletEnum;


@Entity
@Table(name="Transaction")
public class Transaction {
	@Id
	@GeneratedValue
	private Integer id;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private TransactionStatus transactionStatus;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private WalletEnum walletType;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private User user;
	@ManyToOne
	private Wallet wallet;
	
	private Integer depositAmount;
	private Integer withdrawAmount;
	private Integer netAmount;	
	private Integer fee;
	private Integer exchangeRate;
	private Integer grossAmount;
	private String buyer;
	private String seller;		
	private Date date;
	
	public Transaction() {
		
	}
	
	public Transaction(User user, Wallet wallet, Integer depositAmount, Integer withdrawAmount,TransactionStatus status) {
		this.user = user;
		this.wallet = wallet;
		this.walletType = wallet.getWalletType();
		this.transactionStatus = TransactionStatus.PENDING;
		this.depositAmount = depositAmount;
		this.withdrawAmount = withdrawAmount;
		this.date = new Date();
	}
	public Integer getId() {
		return id;
	}
	
	public Integer getNetAmount() {
		return netAmount;
	}
	
	public void setNetAmount(Integer netAmount) {
		this.netAmount = netAmount;
	}
	
	public WalletEnum getWalletType() {
		return walletType;
	}
	
	public void setWalletType(WalletEnum walletType) {
		this.walletType = walletType;
	}
	
	public Integer getFee() {
		return fee;
	}
	
	public void setFee(Integer fee) {
		this.fee = fee;
	}
	
	public Integer getExchangeRate() {
		return exchangeRate;
	}
	
	public void setExchangeRate(Integer exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
	
	public Integer getGrossAmount() {
		return grossAmount;
	}
	
	public void setGrossAmount(Integer grossAmount) {
		this.grossAmount = grossAmount;
	}
	
	public String getBuyer() {
		return buyer;
	}
	
	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}
	
	public String getSeller() {
		return seller;
	}
	
	public void setSeller(String seller) {
		this.seller = seller;
	}
	
	public TransactionStatus getTransactionStatus() {
		return transactionStatus;
	}
	
	public void setTransactionStatus(TransactionStatus transactionStatus) {
		this.transactionStatus = transactionStatus;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getDepositAmount() {
		return depositAmount;
	}

	public void setDepositAmount(Integer depositAmount) {
		this.depositAmount = depositAmount;
	}

	public Integer getWithdrawAmount() {
		return withdrawAmount;
	}

	public void setWithdrawAmount(Integer withdrawAmount) {
		this.withdrawAmount = withdrawAmount;
	}

		
}
