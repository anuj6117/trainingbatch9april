package com.example.demo.utility;

import com.example.demo.model.User;
import com.example.demo.model.Wallet;

public class TransactionUtility {

	private static double transactionAmount;
	private static Wallet sellerWallet;
	private static Wallet buyerWallet;
	public static boolean buyerLessSeller(User buyer, User seller, String coinName, Integer coinQuantity, Integer sellingPrice) {
		for(Wallet buyerWallet : buyer.getWallet()) {
			for(Wallet sellerWallet : seller.getWallet()) {
				if((buyerWallet.getCoinName().equals(coinName))&&(sellerWallet.getCoinName().equals(coinName))) {
					TransactionUtility.buyerWallet = buyerWallet;
					TransactionUtility.sellerWallet = sellerWallet;
					transactionAmount = (coinQuantity*sellingPrice); 
					buyerWallet.setShadowBalance(buyerWallet.getShadowBalance()+coinQuantity);
					buyerWallet.setBalance(buyerWallet.getShadowBalance());
					sellerWallet.setShadowBalance(sellerWallet.getBalance()-coinQuantity);
					sellerWallet.setBalance(sellerWallet.getShadowBalance());					
				}
				if(buyerWallet.getCoinName().equalsIgnoreCase("INR")&&sellerWallet.getCoinName().equalsIgnoreCase("INR")) {
					buyerWallet.setBalance(buyerWallet.getShadowBalance());
					sellerWallet.setShadowBalance(sellerWallet.getBalance()+transactionAmount);
					sellerWallet.setBalance(sellerWallet.getShadowBalance());
				}
					
			}
		}
		
		buyer.getWallet().add(buyerWallet);
		seller.getWallet().add(sellerWallet);
		
		return false;
	}
}
