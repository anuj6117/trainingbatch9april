package com.trainingproject.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Generator {
	private static MessageDigest md;
	public static String encrypt(String plainText) {
		try {
			md = MessageDigest.getInstance("MD5");
	      byte[] plainTextBytes =	plainText.getBytes();
	      md.reset();
	   byte[] encryptBytes =  md.digest(plainTextBytes);
			
			StringBuilder sb = new StringBuilder();
			for(int i = 0; i < encryptBytes.length; i++) {
				sb.append(Integer.toHexString(0xff & encryptBytes[i]));
			}
			
		  return sb.toString();
		  
	}catch(NoSuchAlgorithmException ex) {
		
	}
		return null;
	}
}
