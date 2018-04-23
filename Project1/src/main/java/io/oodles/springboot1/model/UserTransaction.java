package io.oodles.springboot1.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import io.oodles.springboot1.enums.TransactionStatus;
import io.oodles.springboot1.enums.WalletType;

@Entity
public class UserTransaction {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	Integer id;
	Integer netBalance;
	WalletType walletType;
	Integer fee;
	Integer exchangeRate;
	Integer grossAmount;
	String buyer;
	String seller;
	TransactionStatus transactionstatus;
	Date dateCreated;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getNetBalance() {
		return netBalance;
	}
	public void setNetBalance(Integer netBalance) {
		this.netBalance = netBalance;
	}
	public WalletType getWalletType() {
		return walletType;
	}
	public void setWalletType(WalletType walletType) {
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
	public TransactionStatus getTransactionstatus() {
		return transactionstatus;
	}
	public void setTransactionstatus(TransactionStatus transactionstatus) {
		this.transactionstatus = transactionstatus;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	

}
