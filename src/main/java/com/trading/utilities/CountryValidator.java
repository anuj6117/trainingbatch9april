package com.trading.utilities;

public class CountryValidator {
	public static boolean isValid(String country) {
	      String pattern = "[A-Z][a-zA-Z]*";
	      return country.matches(pattern);
	   }	
}

