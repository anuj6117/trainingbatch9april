package com.trading.utilities;

public class PhoneValidator {
	

		public static boolean isValid(String phoneNumber) {
		      String pattern = "^[789]\\d{9}$";
		      return phoneNumber.matches(pattern);
		   }	
	}



