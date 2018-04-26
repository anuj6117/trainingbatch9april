package com.trading.utilities;

public class NameValidator {

	public static boolean isValid(String userName) {
	      String pattern = "[A-Z][a-z]+( [A-Z][a-z]+). {0,25}";
	      return userName.matches(pattern);
	   }	
}
