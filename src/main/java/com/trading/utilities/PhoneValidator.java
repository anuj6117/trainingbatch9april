package com.trading.utilities;

public class PhoneValidator {
	

		public static boolean isValid(String phoneNumber) {
		      String pattern = "^[7-9][0-9]{9}\"$";
		      return phoneNumber.matches(pattern);
		   }	
	}



