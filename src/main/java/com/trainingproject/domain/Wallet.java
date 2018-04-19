package com.trainingproject.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.trainingproject.enums.WalletType;

@Entity
@Table(name="wallet")
public class Wallet {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Integer walletId;
	@Enumerated(EnumType.STRING)
	private WalletType walletType;
	private Long amount;
	private Long shadowAmount;
	public Integer getWalletId() {
		return walletId;
	}
	public void setWalletId(Integer walletId) {
		this.walletId = walletId;
	}
	public WalletType getWalletType() {
		return walletType;
	}
	public void setWalletType(WalletType walletType) {
		this.walletType = walletType;
	}
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	public Long getShadowAmount() {
		return shadowAmount;
	}
	public void setShadowAmount(Long shadowAmount) {
		this.shadowAmount = shadowAmount;
	}


}
