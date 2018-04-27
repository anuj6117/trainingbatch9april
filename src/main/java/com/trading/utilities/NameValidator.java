package com.trading.utilities;

public class NameValidator {

	public static boolean isValid(String userName) {
	      String pattern = "[A-Z][a-zA-Z]*";
	      return userName.matches(pattern);
	   }	
}
