package com.trading.utilities;

public class NameValidator {

	public static boolean isValid(String userName) {
	      String pattern = "^(?! )[\\w-\\s]*(?<! )$";
	      return userName.matches(pattern);
	   }	
}
